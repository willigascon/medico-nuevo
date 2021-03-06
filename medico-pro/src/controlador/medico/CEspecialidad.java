package controlador.medico;

import java.util.HashMap;
import java.util.List;

import modelo.medico.maestro.DoctorInterno;
import modelo.medico.maestro.Especialidad;
import modelo.medico.maestro.Especialista;
import modelo.security.Usuario;

import org.zkoss.zk.ui.Sessions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Button;
import org.zkoss.zul.Div;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Tab;
import org.zkoss.zul.Textbox;

import componente.Botonera;
import componente.Catalogo;
import componente.Mensaje;
import controlador.utils.CGenerico;

public class CEspecialidad extends CGenerico {

	private static final long serialVersionUID = 2377150704906523958L;
	@Wire
	private Div botoneraEspecialidad;
	@Wire
	private Textbox txtDescripcionEspecialidad;
	@Wire
	private Button btnBuscarEspecialidad;
	@Wire
	private Div catalogoEspecialidad;
	@Wire
	private Div divEspecialidad;
	private long id = 0;
	Catalogo<Especialidad> catalogo;

	@Override
	public void inicializar() {
		HashMap<String, Object> map = (HashMap<String, Object>) Sessions
				.getCurrent().getAttribute("mapaGeneral");
		if (map != null) {
			if (map.get("tabsGenerales") != null) {
				titulo = (String) map.get("titulo");
				tabs = (List<Tab>) map.get("tabsGenerales");
				map.clear();
				map = null;
			}
		}
		
		
		HashMap<String, Object> mapaa = (HashMap<String, Object>) Sessions
				.getCurrent().getAttribute("itemsCatalogo");
		if (mapaa != null) {
			if (mapaa.get("titulo") != null) {
				titulo = (String) mapaa.get("titulo");
				mapaa.clear();
				mapaa = null;
			}
		}
		Botonera botonera = new Botonera() {

			@Override
			public void guardar() {
				if (validar()) {
					String nombre = txtDescripcionEspecialidad.getValue();
					Especialidad especialidad = new Especialidad(id, nombre,
							fechaHora, horaAuditoria, nombreUsuarioSesion());
					servicioEspecialidad.guardar(especialidad);
					Mensaje.mensajeInformacion(Mensaje.guardado);
					limpiar();
				}
			}

			@Override
			public void limpiar() {
				txtDescripcionEspecialidad.setValue("");
				limpiarColores(txtDescripcionEspecialidad);
				id = 0;
			}

			@Override
			public void salir() {
				cerrarVentana(divEspecialidad, titulo, tabs);
			}

			@Override
			public void eliminar() {
				if (id != 0
						&& txtDescripcionEspecialidad.getText().compareTo("") != 0) {
					Messagebox.show(
							"�Esta Seguro de Eliminar la Especialidad?",
							"Alerta", Messagebox.OK | Messagebox.CANCEL,
							Messagebox.QUESTION,
							new org.zkoss.zk.ui.event.EventListener<Event>() {
								public void onEvent(Event evt)
										throws InterruptedException {
									if (evt.getName().equals("onOK")) {
										Especialidad especialidad = servicioEspecialidad
												.buscar(id);
										List<DoctorInterno> usuarios = servicioDoctor
												.buscarPorEspecialidad(especialidad);
										List<Especialista> especialistas = servicioEspecialista
												.buscarPorEspecialidad(especialidad);
										if (!usuarios.isEmpty()
												|| !especialistas.isEmpty()) {
											Mensaje.mensajeError(Mensaje.noEliminar);
										} else {
											servicioEspecialidad
													.eliminar(especialidad);
											limpiar();
											Mensaje.mensajeInformacion(Mensaje.eliminado);
										}
									}
								}
							});
				} else {
					Mensaje.mensajeAlerta(Mensaje.noSeleccionoRegistro);
				}
			}

		};
		botoneraEspecialidad.appendChild(botonera);
	}

	/* Permite validar que todos los campos esten completos */
	public boolean validar() {
		if (txtDescripcionEspecialidad.getText().compareTo("") == 0) {
			aplicarColores(txtDescripcionEspecialidad);
			Mensaje.mensajeError(Mensaje.camposVacios);
			return false;
		} else
			return true;
	}

	/* Muestra el catalogo de las especialidades */
	@Listen("onClick = #btnBuscarEspecialidad")
	public void mostrarCatalogo() {
		List<Especialidad> especialidades = servicioEspecialidad.buscarTodas();
		catalogo = new Catalogo<Especialidad>(catalogoEspecialidad,
				"Catalogo de Especialidades", especialidades,false, "Nombre") {

			@Override
			protected List<Especialidad> buscar(String valor, String combo) {
				if (combo.equals("Nombre"))
					return servicioEspecialidad.filtroNombre(valor);
				else
					return servicioEspecialidad.buscarTodas();
			}

			@Override
			protected String[] crearRegistros(Especialidad objeto) {
				String[] registros = new String[1];
				registros[0] = objeto.getDescripcion();
				return registros;
			}

		};
		catalogo.setParent(catalogoEspecialidad);
		catalogo.doModal();
	}

	/* Permite la seleccion de un item del catalogo */
	@Listen("onSeleccion = #catalogoEspecialidad")
	public void seleccinar() {
		Especialidad especialidad = catalogo.objetoSeleccionadoDelCatalogo();
		llenarCampos(especialidad);
		catalogo.setParent(null);
	}

	/* Busca si existe una especialidad con el mismo nombre escrito */
	@Listen("onChange = #txtDescripcionEspecialidad")
	public void buscarPorNombre() {
		Especialidad especialidad = servicioEspecialidad
				.buscarPorDescripcion(txtDescripcionEspecialidad.getValue());
		if (especialidad != null)
			llenarCampos(especialidad);
	}

	/* LLena los campos del formulario dada una especialidad */
	private void llenarCampos(Especialidad especialidad) {
		txtDescripcionEspecialidad.setValue(especialidad.getDescripcion());
		id = especialidad.getIdEspecialidad();
	}
}
