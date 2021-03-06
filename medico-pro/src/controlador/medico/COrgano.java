package controlador.medico;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import modelo.medico.consulta.ConsultaParteCuerpo;
import modelo.medico.maestro.ParteCuerpo;

import org.zkoss.zk.ui.Sessions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Div;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Tab;
import org.zkoss.zul.Textbox;

import componente.Botonera;
import componente.Catalogo;
import componente.Mensaje;

import controlador.utils.CGenerico;

public class COrgano extends CGenerico {

	private static final long serialVersionUID = -2045885360473336651L;
	@Wire
	private Div botoneraOrgano;
	@Wire
	private Textbox txtNombre;
	@Wire
	private Div catalogoOrgano;
	@Wire
	private Div divOrgano;
	private long id = 0;
	Catalogo<ParteCuerpo> catalogo;
	private boolean consulta = false;
	private CConsulta cConsulta = new CConsulta();
	List<ParteCuerpo> partes = new ArrayList<ParteCuerpo>();
	Listbox listaConsulta;

	@Override
	public void inicializar() throws IOException {
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
				listaConsulta = (Listbox) map.get("listbox");
				titulo = (String) map.get("titulo");
				map.clear();
				map = null;
			}
		}
		Botonera botonera = new Botonera() {

			@Override
			public void salir() {
				cerrarVentana(divOrgano, titulo, tabs);
			}

			@Override
			public void limpiar() {
				txtNombre.setValue("");
				limpiarColores(txtNombre);
				id = 0;
			}

			@Override
			public void guardar() {
				if (validar()) {
					String nombre = txtNombre.getValue();
					ParteCuerpo parte = new ParteCuerpo(id, nombre, fechaHora,
							horaAuditoria, nombreUsuarioSesion());
					servicioParteCuerpo.guardar(parte);
					if (consulta) {
						if (id != 0)
							parte = servicioParteCuerpo.buscar(id);
						else {
							parte = servicioParteCuerpo.buscarUltimo();
							partes.add(parte);
						}
						cConsulta.recibirCuerpo(partes, listaConsulta,
								servicioParteCuerpo);
					}
					msj.mensajeInformacion(Mensaje.guardado);
					limpiar();
				}
			}

			@Override
			public void eliminar() {
				if (id != 0 && txtNombre.getText().compareTo("") != 0) {
					Messagebox.show("�Esta Seguro de Eliminar el Organo?",
							"Alerta", Messagebox.OK | Messagebox.CANCEL,
							Messagebox.QUESTION,
							new org.zkoss.zk.ui.event.EventListener<Event>() {
								public void onEvent(Event evt)
										throws InterruptedException {
									if (evt.getName().equals("onOK")) {
										ParteCuerpo parte = servicioParteCuerpo
												.buscar(id);
										List<ConsultaParteCuerpo> estados = servicioConsultaParteCuerpo
												.buscarPorParte(parte);
										if (!estados.isEmpty()) {
											msj.mensajeError(Mensaje.noEliminar);
										} else {
											servicioParteCuerpo.eliminar(parte);
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
		botoneraOrgano.appendChild(botonera);
	}

	protected boolean validar() {
		if (txtNombre.getText().compareTo("") == 0) {
			aplicarColores(txtNombre);
			msj.mensajeError(Mensaje.camposVacios);
			return false;
		} else
			return true;
	}

	@Listen("onClick = #btnBuscarOrgano")
	public void mostrarCatalogo() {
		final List<ParteCuerpo> paises = servicioParteCuerpo.buscarTodos();
		catalogo = new Catalogo<ParteCuerpo>(catalogoOrgano,
				"Catalogo de Partes del Cuerpo", paises, false,"Nombre") {

			@Override
			protected List<ParteCuerpo> buscar(String valor, String combo) {
				if (combo.equals("Nombre"))
					return servicioParteCuerpo.filtroNombre(valor);
				else
					return paises;
			}

			@Override
			protected String[] crearRegistros(ParteCuerpo estado) {
				String[] registros = new String[1];
				registros[0] = estado.getNombre();
				return registros;
			}
		};
		catalogo.setParent(catalogoOrgano);
		catalogo.doModal();
	}

	@Listen("onSeleccion = #catalogoOrgano")
	public void seleccinar() {
		ParteCuerpo parte = catalogo.objetoSeleccionadoDelCatalogo();
		llenarCampos(parte);
		catalogo.setParent(null);
	}

	@Listen("onChange = #txtNombre")
	public void buscarPorNombre() {
		ParteCuerpo parte = servicioParteCuerpo.buscarPorNombre(txtNombre
				.getValue());
		if (parte != null)
			llenarCampos(parte);
	}

	private void llenarCampos(ParteCuerpo parte) {
		txtNombre.setValue(parte.getNombre());
		id = parte.getIdParte();
	}
}
