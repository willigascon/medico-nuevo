package controlador.organizacion;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import modelo.medico.maestro.Paciente;
import modelo.medico.maestro.Proveedor;
import modelo.medico.maestro.ServicioExterno;
import modelo.organizacion.Ciudad;
import modelo.organizacion.Estado;
import modelo.security.Arbol;

import org.zkoss.zk.ui.Sessions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Button;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Comboitem;
import org.zkoss.zul.Div;
import org.zkoss.zul.Include;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Tab;
import org.zkoss.zul.Tabbox;
import org.zkoss.zul.Textbox;

import componente.Botonera;
import componente.Catalogo;
import componente.Mensaje;
import controlador.security.CArbol;
import controlador.utils.CGenerico;

public class CCiudad extends CGenerico {

	private static final long serialVersionUID = 84966503677381446L;

	@Wire
	private Textbox txtNombreCiudad;
	@Wire
	private Div botoneraCiudad;
	@Wire
	private Div catalogoCiudad;
	@Wire
	private Div divCiudad;
	@Wire
	private Button btnBuscarCiudad;
	@Wire
	private Combobox cmbEstado;

	private CArbol cArbol = new CArbol();
	Catalogo<Ciudad> catalogo;
	long id = 0;

	@Override
	public void inicializar() throws IOException {
		contenido = (Include) divCiudad.getParent();
		Tabbox tabox = (Tabbox) divCiudad.getParent().getParent().getParent()
				.getParent();
		tabBox = tabox;
		tab = (Tab) tabox.getTabs().getLastChild();
		HashMap<String, Object> map = (HashMap<String, Object>) Sessions
				.getCurrent().getAttribute("mapaGeneral");
		if (map != null) {
			if (map.get("tabsGenerales") != null) {
				tabs = (List<Tab>) map.get("tabsGenerales");
				titulo = (String) map.get("titulo");
				map.clear();
				map = null;
			}
		}
		HashMap<String, Object> mapa = (HashMap<String, Object>) Sessions
				.getCurrent().getAttribute("itemsCatalogo");
		if (mapa != null) {
			if (mapa.get("titulo") != null) {
				titulo = (String) mapa.get("titulo");
				mapa.clear();
				mapa = null;
			}
		}
		llenarCombo();
		Botonera botonera = new Botonera() {

			@Override
			public void salir() {
				cerrarVentana(divCiudad, titulo, tabs);
			}

			@Override
			public void limpiar() {
				txtNombreCiudad.setText("");
				cmbEstado.setValue("");
				cmbEstado.setPlaceholder("Seleccione un Estado");
				limpiarColores(txtNombreCiudad, cmbEstado);
				id = 0;
			}

			@Override
			public void guardar() {
				if (validar()) {
					String nombre = txtNombreCiudad.getValue();
					long idEstado = Long.valueOf(cmbEstado.getSelectedItem()
							.getContext());
					Estado estado = servicioEstado.buscar(idEstado);
					Ciudad ciudad = new Ciudad(id, fechaHora, horaAuditoria,
							nombre, nombreUsuarioSesion(), estado);
					servicioCiudad.guardar(ciudad);
					Mensaje.mensajeInformacion(Mensaje.guardado);
					limpiar();
				}

			}

			@Override
			public void eliminar() {
				if (id != 0) {
					Messagebox.show("�Esta Seguro de Eliminar la Ciudad?",
							"Alerta", Messagebox.OK | Messagebox.CANCEL,
							Messagebox.QUESTION,
							new org.zkoss.zk.ui.event.EventListener<Event>() {
								public void onEvent(Event evt)
										throws InterruptedException {
									if (evt.getName().equals("onOK")) {
										Ciudad ciudad = servicioCiudad
												.buscar(id);
										List<Paciente> pacientes = servicioPaciente
												.buscarPorCiudad(ciudad);
										List<Proveedor> proveedores = servicioProveedor
												.buscarPorCiudad(ciudad);
										if (!pacientes.isEmpty()
												|| !proveedores.isEmpty()) {
											Mensaje.mensajeError(Mensaje.noEliminar);
										} else {
											servicioCiudad.eliminar(ciudad);
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
		botoneraCiudad.appendChild(botonera);
	}

	/* Validaciones de pantalla para poder realizar el guardar */
	public boolean validar() {

		if (cmbEstado.getText().compareTo("") == 0
				|| txtNombreCiudad.getText().compareTo("") == 0) {
			aplicarColores(txtNombreCiudad, cmbEstado);
			Mensaje.mensajeError(Mensaje.camposVacios);
			return false;
		} else
			return true;
	}

	/* Muestra un catalogo de ciudades */
	@Listen("onClick = #btnBuscarCiudad")
	public void mostrarCatalogo() throws IOException {
		final List<Ciudad> ciudades = servicioCiudad.buscarTodas();
		catalogo = new Catalogo<Ciudad>(catalogoCiudad, "Catalogo de Ciudades",
				ciudades, false, "Nombre", "Estado", "Pais") {

			@Override
			protected String[] crearRegistros(Ciudad ciudad) {
				String[] registros = new String[3];
				registros[0] = ciudad.getNombre();
				registros[1] = ciudad.getEstado().getNombre();
				registros[2] = ciudad.getEstado().getPais().getNombre();
				return registros;
			}

			@Override
			protected List<Ciudad> buscar(String valor, String combo) {

				switch (combo) {
				case "Nombre":
					return servicioCiudad.filtroNombre(valor);
				case "Estado":
					return servicioCiudad.filtroEstado(valor);
				case "Pais":
					return servicioCiudad.filtroPais(valor);
				default:
					return ciudades;
				}
			}
		};
		catalogo.setParent(catalogoCiudad);
		catalogo.doModal();
	}

	/* Busca si existe una ciudad con el mismo nombre escrito */
	@Listen("onChange = #txtNombreCiudad")
	public void buscarPorNombre() {
		Ciudad ciudad = servicioCiudad.buscarPorNombre(txtNombreCiudad
				.getValue());
		if (ciudad != null)
			llenarCampos(ciudad);
	}

	/* Llena el combo de estado cada vez que se abre */
	@Listen("onOpen = #cmbEstado")
	public void llenarCombo() {
		List<Estado> estados = servicioEstado.buscarTodos();
		cmbEstado.setModel(new ListModelList<Estado>(estados));
	}

	/*
	 * Selecciona una ciudad del catalogo y llena los campos con la informacion
	 */
	@Listen("onSeleccion = #catalogoCiudad")
	public void seleccion() {
		Ciudad ciudad = catalogo.objetoSeleccionadoDelCatalogo();
		llenarCampos(ciudad);
		catalogo.setParent(null);
	}

	/* LLena los campos del formulario dada una ciudad */
	public void llenarCampos(Ciudad ciudad) {
		txtNombreCiudad.setValue(ciudad.getNombre());
		cmbEstado.setValue(ciudad.getEstado().getNombre());
		Comboitem item = cmbEstado.appendItem(ciudad.getEstado().getNombre());
		item.setContext(String.valueOf(ciudad.getEstado().getIdEstado()));
		cmbEstado.setSelectedItem(item);
		id = ciudad.getIdCiudad();
	}

	/* Abre la vista de Estado */
	@Listen("onClick = #btnAbrirEstado")
	public void abrirEstado() {
		final HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("titulo", "Estado");
		Sessions.getCurrent().setAttribute("itemsCatalogo", map);
		List<Arbol> arboles = servicioArbol.buscarPorNombreArbol("Estado");
		if (!arboles.isEmpty()) {
			Arbol arbolItem = arboles.get(0);
			cArbol.abrirVentanas(arbolItem, tabBox, contenido, tab, tabs);
		}
	}

}
