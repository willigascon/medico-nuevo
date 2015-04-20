package controlador.security;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import modelo.security.Arbol;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Div;
import org.zkoss.zul.Longbox;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Tab;
import org.zkoss.zul.Textbox;

import componente.Botonera;
import componente.Catalogo;
import componente.Mensaje;
import controlador.utils.CGenerico;

public class CMenu extends CGenerico {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Wire
	private Textbox txtNombre;
	@Wire
	private Longbox txtPadre;
	@Wire
	private Textbox txtUrl;
	@Wire
	private Div divVMenuArbol;
	@Wire
	private Div botoneraMenuArbol;
	@Wire
	private Div divCatalogoMenuArbol;
	Catalogo<Arbol> catalogo;
	long clave = 0;

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
		Botonera botonera = new Botonera() {

			@Override
			public void salir() {
				cerrarVentana(divVMenuArbol, titulo, tabs);
			}

			@Override
			public void limpiar() {
				clave = 0;
				txtUrl.setValue("");
				txtNombre.setValue("");
				txtPadre.setValue(null);
				limpiarColores(txtPadre, txtUrl, txtNombre);
			}

			@Override
			public void guardar() {
				if (validar()) {
					String url = txtUrl.getValue();
					String nombre = txtNombre.getValue();
					Long padre = txtPadre.getValue();
					Arbol arbol = new Arbol();
					arbol.setNombre(nombre);
					arbol.setPadre(padre);
					arbol.setUrl(url);
					arbol.setIdArbol(clave);
					servicioArbol.guardar(arbol);
					Mensaje.mensajeInformacion(Mensaje.guardado);
					limpiar();
				}
			}

			@Override
			public void eliminar() {
				if (clave != 0) {
					Messagebox.show("¿Esta Seguro de Eliminar el Menu?",
							"Alerta", Messagebox.OK | Messagebox.CANCEL,
							Messagebox.QUESTION,
							new org.zkoss.zk.ui.event.EventListener<Event>() {
								public void onEvent(Event evt)
										throws InterruptedException {
									if (evt.getName().equals("onOK")) {
										Arbol arbol = servicioArbol
												.buscar(clave);
										List<Arbol> arboles = new ArrayList<Arbol>();
										arboles.add(arbol);
										if (servicioGrupo
												.buscarArboles(arboles)
												.isEmpty()) {
											servicioArbol.eliminar(clave);
											Mensaje.mensajeInformacion(Mensaje.eliminado);
											limpiar();
										} else
											Mensaje.mensajeError("El menu ha sido asignado a uno o mas grupos, por favor, verifique que este no haya sido asignado a ningun grupo");
									}
								}
							});
				} else
					Mensaje.mensajeAlerta(Mensaje.noSeleccionoRegistro);
			}
		};
		botoneraMenuArbol.appendChild(botonera);
	}

	protected boolean validar() {
		if (txtPadre.getText().compareTo("") == 0
				|| txtUrl.getText().compareTo("") == 0
				|| txtNombre.getText().compareTo("") == 0) {
			Mensaje.mensajeError(Mensaje.camposVacios);
			aplicarColores(txtPadre, txtUrl, txtNombre);
			return false;
		} else
			return true;
	}

	/* Muestra un catalogo de Usuarios */
	@Listen("onClick = #btnBuscarMenu")
	public void mostrarCatalogo() throws IOException {
		final List<Arbol> usuarios = servicioArbol.listarArbol();
		catalogo = new Catalogo<Arbol>(divCatalogoMenuArbol,
				"Catalogo de Items", usuarios, false, "Nombre", "Padre", "Url") {

			@Override
			protected List<Arbol> buscar(String valor, String combo) {
				switch (combo) {
				case "Nombre":
					return servicioArbol.filtroNombre(valor);
				case "Padre":
					return servicioArbol.filtroPadre(valor);
				case "Url":
					return servicioArbol.filtroUrl(valor);
				default:
					return usuarios;
				}
			}

			@Override
			protected String[] crearRegistros(Arbol objeto) {
				String[] registros = new String[3];
				registros[0] = objeto.getNombre();
				registros[1] = String.valueOf(objeto.getPadre());
				registros[2] = objeto.getUrl();
				return registros;
			}

		};
		catalogo.setParent(divCatalogoMenuArbol);
		catalogo.doModal();
	}

	/*
	 * Selecciona un usuario del catalogo y llena los campos con la informacion
	 */
	@Listen("onSeleccion = #divCatalogoMenuArbol")
	public void seleccion() {
		Arbol usuario = catalogo.objetoSeleccionadoDelCatalogo();
		llenarCampos(usuario);
		catalogo.setParent(null);
	}

	/* Busca si existe un usuario con la misma cedula nombre escrita */
	@Listen("onChange = #txtNombre; onOK= #txtNombre")
	public void buscarPorCedula() {
		Arbol usuario = servicioArbol.buscarPorNombre(txtNombre.getValue());
		if (usuario != null)
			llenarCampos(usuario);
	}

	/* LLena los campos del formulario dado un usuario */
	public void llenarCampos(Arbol usuario) {
		txtPadre.setValue(usuario.getPadre());
		txtUrl.setValue(usuario.getUrl());
		txtNombre.setValue(usuario.getNombre());
		clave = usuario.getIdArbol();
	}

}
