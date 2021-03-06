package controlador.organizacion;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import modelo.medico.consulta.Consulta;
import modelo.medico.maestro.Paciente;
import modelo.organizacion.Cargo;

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

public class CCargo extends CGenerico {

	@Wire
	private Div botoneraCargo;
	@Wire
	private Textbox txtNombreCargo;
	@Wire
	private Button btnBuscarCargo;
	@Wire
	private Div catalogoCargo;
	@Wire
	private Div divCargo;

	private long id = 0;
	Catalogo<Cargo> catalogo;

	@Override
	public void inicializar() throws IOException {
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
		txtNombreCargo.setFocus(true);

		Botonera botonera = new Botonera() {

			@Override
			public void salir() {
				cerrarVentana(divCargo,titulo, tabs);

			}

			@Override
			public void limpiar() {
				txtNombreCargo.setValue("");
				id = 0;
				txtNombreCargo.setFocus(true);
				limpiarColores(txtNombreCargo);

			}

			@Override
			public void guardar() {
				if (validar()) {
					String nombre = txtNombreCargo.getValue();
					Cargo cargo = new Cargo(id, nombre, fechaHora,
							horaAuditoria, nombreUsuarioSesion());
					servicioCargo.guardar(cargo);
					Mensaje.mensajeInformacion(Mensaje.guardado);
					limpiar();
				}

			}

			@Override
			public void eliminar() {
				if (id != 0 && txtNombreCargo.getText().compareTo("") != 0) {
					Messagebox.show("�Esta Seguro de Eliminar el Cargo?",
							"Alerta", Messagebox.OK | Messagebox.CANCEL,
							Messagebox.QUESTION,
							new org.zkoss.zk.ui.event.EventListener<Event>() {
								public void onEvent(Event evt)
										throws InterruptedException {
									if (evt.getName().equals("onOK")) {
										Cargo cargo = servicioCargo.buscar(id);
										List<Paciente> pacientes = servicioPaciente
												.buscarPorCargo(cargo);
										List<Consulta> consultas1 = servicioConsulta.buscarPorCargo(cargo);
										if (!pacientes.isEmpty() || !consultas1.isEmpty()) {
											Mensaje.mensajeError(Mensaje.noEliminar);
										} else {
											servicioCargo.eliminar(cargo);
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

		botoneraCargo.appendChild(botonera);
	}

	/* Permite validar que todos los campos esten completos */
	public boolean validar() {
		if (txtNombreCargo.getText().compareTo("") == 0) {
			aplicarColores(txtNombreCargo);
			Mensaje.mensajeError(Mensaje.camposVacios);
			return false;
		} else
			return true;
	}

	/* Muestra el catalogo de los areas */
	@Listen("onClick = #btnBuscarCargo")
	public void mostrarCatalogo() {
		final List<Cargo> cargos = servicioCargo.buscarTodos();
		catalogo = new Catalogo<Cargo>(catalogoCargo, "Catalogo de Cargos",
				cargos,false, "Nombre") {

			@Override
			protected List<Cargo> buscar(String valor, String combo) {
				switch (combo) {
				case "Nombre":
					return servicioCargo.filtroNombre(valor);
				default:
					return cargos;
				}
			}

			@Override
			protected String[] crearRegistros(Cargo cargo) {
				String[] registros = new String[1];
				registros[0] = cargo.getNombre();
				return registros;
			}
		};
		catalogo.setParent(catalogoCargo);
		catalogo.doModal();
	}

	/* Permite la seleccion de un item del catalogo */
	@Listen("onSeleccion = #catalogoCargo")
	public void seleccinar() {
		Cargo cargo = catalogo.objetoSeleccionadoDelCatalogo();
		llenarCampos(cargo);
		catalogo.setParent(null);
	}

	/* Busca si existe un area con el mismo nombre escrito */
	@Listen("onChange = #txtNombreCargo")
	public void buscarPorNombre() {
		Cargo cargo = servicioCargo.buscarPorNombre(txtNombreCargo.getValue());
		if (cargo != null)
			llenarCampos(cargo);
	}

	/* LLena los campos del formulario dado un area */
	private void llenarCampos(Cargo cargo) {
		txtNombreCargo.setValue(cargo.getNombre());
		id = cargo.getIdCargo();
	}

}
