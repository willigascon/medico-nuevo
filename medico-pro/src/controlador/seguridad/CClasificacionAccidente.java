package controlador.seguridad;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import modelo.seguridad.Accidente;
import modelo.seguridad.ClasificacionAccidente;
import modelo.seguridad.Informe;

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

public class CClasificacionAccidente extends CGenerico {

	@Wire
	private Div botoneraClasificacionAccidente;
	@Wire
	private Textbox txtNombreClasificacionAccidente;
	@Wire
	private Button btnBuscarClasificacionAccidente;
	@Wire
	private Div catalogoClasificacionAccidente;
	@Wire
	private Div divClasificacionAccidente;
	private long id = 0;
	Catalogo<ClasificacionAccidente> catalogo;

	@Override
	public void inicializar() throws IOException {
		HashMap<String, Object> mapa = (HashMap<String, Object>) Sessions
				.getCurrent().getAttribute("mapaGeneral");
		if (mapa != null) {
			if (mapa.get("tabsGenerales") != null) {
				tabs = (List<Tab>) mapa.get("tabsGenerales");
				titulo = (String) mapa.get("titulo");
				mapa.clear();
				mapa = null;
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
		txtNombreClasificacionAccidente.setFocus(true);
		Botonera botonera = new Botonera() {

			@Override
			public void salir() {
				cerrarVentana(divClasificacionAccidente,
						titulo, tabs);

			}

			@Override
			public void limpiar() {
				txtNombreClasificacionAccidente.setValue("");
				txtNombreClasificacionAccidente.setFocus(true);
				limpiarColores(txtNombreClasificacionAccidente);
				id = 0;

			}

			@Override
			public void guardar() {
				if (validar()) {
					String nombre = txtNombreClasificacionAccidente.getValue();
					ClasificacionAccidente clasificacionAccidente = new ClasificacionAccidente(
							id, nombre, fechaHora, horaAuditoria,
							nombreUsuarioSesion());
					servicioClasificacionAccidente
							.guardar(clasificacionAccidente);
					msj.mensajeInformacion(Mensaje.guardado);
					limpiar();
				}
			}

			@Override
			public void eliminar() {
				if (id != 0
						&& txtNombreClasificacionAccidente.getText().compareTo(
								"") != 0) {
					Messagebox
							.show("�Esta Seguro de Eliminar la Clasificacion de Accidente?",
									"Alerta",
									Messagebox.OK | Messagebox.CANCEL,
									Messagebox.QUESTION,
									new org.zkoss.zk.ui.event.EventListener<Event>() {
										public void onEvent(Event evt)
												throws InterruptedException {
											if (evt.getName().equals("onOK")) {
												ClasificacionAccidente clasificacionAccidente = servicioClasificacionAccidente
														.buscar(id);
												List<Accidente> accidentes = servicioAccidente
														.buscarPorClasificacion(clasificacionAccidente);
												if (!accidentes.isEmpty()) {
													msj.mensajeError(Mensaje.noEliminar);
												} else {
													servicioClasificacionAccidente
															.eliminar(clasificacionAccidente);
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

		botoneraClasificacionAccidente.appendChild(botonera);
	}

	/* Permite validar que todos los campos esten completos */
	public boolean validar() {
		if (txtNombreClasificacionAccidente.getText().compareTo("") == 0) {
			aplicarColores(txtNombreClasificacionAccidente);
			msj.mensajeError(Mensaje.camposVacios);
			return false;
		} else
			return true;
	}

	/* Muestra el catalogo de los roles */
	@Listen("onClick = #btnBuscarClasificacionAccidente")
	public void mostrarCatalogo() {
		final List<ClasificacionAccidente> clasificaciones = servicioClasificacionAccidente
				.buscarTodos();
		catalogo = new Catalogo<ClasificacionAccidente>(
				catalogoClasificacionAccidente,
				"Catalogo de Clasificaciones de Accidente", clasificaciones,false,
				"Nombre") {

			@Override
			protected List<ClasificacionAccidente> buscar(String valor,
					String combo) {

				switch (combo) {
				case "Nombre":
					return servicioClasificacionAccidente.filtroNombre(valor);
				default:
					return clasificaciones;
				}
			}

			@Override
			protected String[] crearRegistros(ClasificacionAccidente rol) {
				String[] registros = new String[1];
				registros[0] = rol.getNombre();
				return registros;
			}
		};
		catalogo.getListbox().setPageSize(6);
		catalogo.setParent(catalogoClasificacionAccidente);
		catalogo.doModal();
	}

	/* Permite la seleccion de un item del catalogo */
	@Listen("onSeleccion = #catalogoClasificacionAccidente")
	public void seleccionar() {
		ClasificacionAccidente clasificacionAccidente = catalogo
				.objetoSeleccionadoDelCatalogo();
		llenarCampos(clasificacionAccidente);
		catalogo.setParent(null);
	}

	/* Busca si existe un pais con el mismo nombre escrito */
	@Listen("onChange = #txtNombreClasificacionAccidente")
	public void buscarPorNombre() {
		ClasificacionAccidente clasificacionAccidente = servicioClasificacionAccidente
				.buscarPorNombre(txtNombreClasificacionAccidente.getValue());
		if (clasificacionAccidente != null)
			llenarCampos(clasificacionAccidente);
	}

	/* LLena los campos del formulario dado un pais */
	private void llenarCampos(ClasificacionAccidente clasificacionAccidente) {
		txtNombreClasificacionAccidente.setValue(clasificacionAccidente
				.getNombre());
		id = clasificacionAccidente.getIdClasificacionAccidente();
	}

}
