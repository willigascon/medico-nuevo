package controlador.medico;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import modelo.medico.historia.HistoriaIntervencion;
import modelo.medico.maestro.Intervencion;

import org.zkoss.zk.ui.Sessions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Div;
import org.zkoss.zul.Include;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Tab;
import org.zkoss.zul.Tabbox;
import org.zkoss.zul.Textbox;

import componente.Botonera;
import componente.Catalogo;
import componente.Mensaje;
import controlador.utils.CGenerico;

public class CIntervencion extends CGenerico {

	private static final long serialVersionUID = -5263743713163591765L;
	@Wire
	private Div botoneraIntervencion;
	@Wire
	private Textbox txtNombre;
	@Wire
	private Div catalogoIntervencion;
	@Wire
	private Div divIntervencion;
	private long id = 0;
	Catalogo<Intervencion> catalogo;
	private boolean consulta = false;
	private CHistoria cConsulta = new CHistoria();
	List<Intervencion> interConsulta = new ArrayList<Intervencion>();
	Listbox listaConsulta;

	@Override
	public void inicializar() throws IOException {
		contenido = (Include) divIntervencion.getParent();
		Tabbox tabox = (Tabbox) divIntervencion.getParent().getParent()
				.getParent().getParent();
		tabBox = tabox;
		tab = (Tab) tabox.getTabs().getLastChild();
		HashMap<String, Object> mapa = (HashMap<String, Object>) Sessions
				.getCurrent().getAttribute("mapaGeneral");
		if (mapa != null) {
			if (mapa.get("tabsGenerales") != null) {
				titulo = (String) mapa.get("titulo");
				tabs = (List<Tab>) mapa.get("tabsGenerales");
				mapa.clear();
				mapa = null;
			}
		}
		HashMap<String, Object> map = (HashMap<String, Object>) Sessions
				.getCurrent().getAttribute("itemsCatalogo");
		if (map != null) {
			if (map.get("id") != null) {
				consulta = true;
				titulo = (String) map.get("titulo");
				interConsulta = (List<Intervencion>) map.get("lista");
				listaConsulta = (Listbox) map.get("listbox");
				map.clear();
				map = null;
			}
		}
		Botonera botonera = new Botonera() {

			@Override
			public void salir() {
				cerrarVentana(divIntervencion, titulo, tabs);
			}

			@Override
			public void limpiar() {
				txtNombre.setValue("");
				limpiarColores(txtNombre);
				listaConsulta = null;
				interConsulta = null;
				consulta = false;
				id = 0;
			}

			@Override
			public void guardar() {
				if (validar()) {
					String nombre = txtNombre.getValue();
					Intervencion intervencion = new Intervencion(id, nombre,
							fechaHora, horaAuditoria, nombreUsuarioSesion());
					servicioIntervencion.guardar(intervencion);
					if (consulta) {
						if (id != 0)
							intervencion = servicioIntervencion.buscar(id);
						else {
							intervencion = servicioIntervencion.buscarUltimo();
							interConsulta.add(intervencion);
						}
						cConsulta.recibirIntervencion(interConsulta,
								listaConsulta);
					}
					msj.mensajeInformacion(Mensaje.guardado);
					limpiar();
				}
			}

			@Override
			public void eliminar() {
				if (id != 0 && txtNombre.getText().compareTo("") != 0) {
					Messagebox.show(
							"¿Esta Seguro de Eliminar la Intervencion?",
							"Alerta", Messagebox.OK | Messagebox.CANCEL,
							Messagebox.QUESTION,
							new org.zkoss.zk.ui.event.EventListener<Event>() {
								public void onEvent(Event evt)
										throws InterruptedException {
									if (evt.getName().equals("onOK")) {
										Intervencion intervencion = servicioIntervencion
												.buscar(id);
										List<HistoriaIntervencion> estados = servicioHistoriaIntervencion
												.buscarPorIntervencion(intervencion);
										if (!estados.isEmpty()) {
											msj.mensajeError(Mensaje.noEliminar);
										} else {
											servicioIntervencion
													.eliminar(intervencion);
											limpiar();
											msj.mensajeInformacion(Mensaje.eliminado);
										}
									}
								}
							});
				} else {
					msj.mensajeAlerta(Mensaje.noSeleccionoRegistro);
				}
			}
		};
		botoneraIntervencion.appendChild(botonera);
	}

	protected boolean validar() {
		if (txtNombre.getText().compareTo("") == 0) {
			aplicarColores(txtNombre);
			msj.mensajeError(Mensaje.camposVacios);
			return false;
		} else
			return true;
	}

	@Listen("onClick = #btnBuscarIntervencion")
	public void mostrarCatalogo() {
		final List<Intervencion> paises = servicioIntervencion.buscarTodos();
		catalogo = new Catalogo<Intervencion>(catalogoIntervencion,
				"Catalogo de Intervenciones", paises, false, "Nombre") {

			@Override
			protected List<Intervencion> buscar(String valor, String combo) {
				if (combo.equals("Nombre"))
					return servicioIntervencion.filtroNombre(valor);
				else
					return paises;
			}

			@Override
			protected String[] crearRegistros(Intervencion estado) {
				String[] registros = new String[1];
				registros[0] = estado.getNombre();
				return registros;
			}
		};
		catalogo.setParent(catalogoIntervencion);
		catalogo.doModal();
	}

	@Listen("onSeleccion = #catalogoIntervencion")
	public void seleccinar() {
		Intervencion intervencion = catalogo.objetoSeleccionadoDelCatalogo();
		llenarCampos(intervencion);
		catalogo.setParent(null);
	}

	@Listen("onChange = #txtNombre")
	public void buscarPorNombre() {
		Intervencion intervencion = servicioIntervencion
				.buscarPorNombre(txtNombre.getValue());
		if (intervencion != null)
			llenarCampos(intervencion);
	}

	private void llenarCampos(Intervencion pais) {
		txtNombre.setValue(pais.getNombre());
		id = pais.getIdIntervencion();
	}

}
