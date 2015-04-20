package controlador.security;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.imageio.ImageIO;

import modelo.medico.maestro.Cita;
import modelo.medico.maestro.DoctorInterno;
import modelo.medico.maestro.Especialidad;
import modelo.security.Arbol;
import modelo.security.Grupo;
import modelo.security.Usuario;

import org.zkoss.image.AImage;
import org.zkoss.util.media.Media;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.UploadEvent;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Button;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Div;
import org.zkoss.zul.Groupbox;
import org.zkoss.zul.Image;
import org.zkoss.zul.Include;
import org.zkoss.zul.Label;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Radio;
import org.zkoss.zul.Radiogroup;
import org.zkoss.zul.Spinner;
import org.zkoss.zul.Tab;
import org.zkoss.zul.Tabbox;
import org.zkoss.zul.Textbox;

import componente.Botonera;
import componente.Catalogo;
import componente.Mensaje;
import componente.Validador;
import controlador.utils.CGenerico;

public class CUsuario extends CGenerico {

	private static final long serialVersionUID = 7879830599305337459L;
	@Wire
	private Div divUsuario;
	@Wire
	private Div botoneraUsuario;
	@Wire
	private Div catalogoUsuario;
	@Wire
	private Div catalogoDoctores;
	@Wire
	private Label lblNombreDoctor;
	@Wire
	private Textbox txtNombre;
	@Wire
	private Textbox txtApellido;
	@Wire
	private Textbox txtCorreo;
	@Wire
	private Textbox txtLoginUsuario;
	@Wire
	private Textbox txtPasswordUsuario;
	@Wire
	private Textbox txtPassword2Usuario;
	@Wire
	private Button btnBuscarUsuario;
	@Wire
	private Listbox ltbGruposDisponibles;
	@Wire
	private Listbox ltbGruposAgregados;
	@Wire
	private Image imagen;
	@Wire
	private Button fudImagenUsuario;
	@Wire
	private Media media;
	String id = "";
	Catalogo<Usuario> catalogo;
	Catalogo<DoctorInterno> catalogoDoctor;
	List<Grupo> gruposDisponibles = new ArrayList<Grupo>();
	List<Grupo> gruposOcupados = new ArrayList<Grupo>();
	URL url = getClass().getResource("/controlador/utils/usuario.png");
	CArbol cArbol = new CArbol();
	private String idDoctor = null;

	@Override
	public void inicializar() throws IOException {
		contenido = (Include) divUsuario.getParent();
		Tabbox tabox = (Tabbox) divUsuario.getParent().getParent().getParent()
				.getParent();
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
		llenarListas(null);
		try {
			imagen.setContent(new AImage(url));
		} catch (IOException e1) {
			e1.printStackTrace();
		}

		Botonera botonera = new Botonera() {

			@Override
			public void salir() {
				cerrarVentana(divUsuario, titulo, tabs);
			}

			@Override
			public void limpiar() {
				idDoctor = null;
				gruposDisponibles.clear();
				gruposOcupados.clear();
				ltbGruposAgregados.getItems().clear();
				ltbGruposDisponibles.getItems().clear();
				txtLoginUsuario.setValue("");
				txtPasswordUsuario.setValue("");
				txtPassword2Usuario.setValue("");
				txtApellido.setValue("");
				txtNombre.setValue("");
				txtCorreo.setValue("");
				id = "";
				llenarListas(null);
				limpiarColores(txtApellido, txtCorreo, txtNombre,
						txtPassword2Usuario, txtPasswordUsuario,
						txtLoginUsuario);
			}

			@Override
			public void guardar() {
				if (validar()) {
					if (buscarPorLogin()) {
						Set<Grupo> gruposUsuario = new HashSet<Grupo>();
						for (int i = 0; i < ltbGruposAgregados.getItemCount(); i++) {
							Grupo grupo = ltbGruposAgregados.getItems().get(i)
									.getValue();
							gruposUsuario.add(grupo);
						}

						String correo = txtCorreo.getValue();
						String login = txtLoginUsuario.getValue();
						String password = txtPasswordUsuario.getValue();
						String nombre = txtNombre.getValue();
						String apellido = txtApellido.getValue();
						byte[] imagenUsuario = null;
						if (media instanceof org.zkoss.image.Image) {
							imagenUsuario = imagen.getContent().getByteData();

						} else {
							try {
								imagen.setContent(new AImage(url));
							} catch (IOException e) {
								e.printStackTrace();
							}
							imagenUsuario = imagen.getContent().getByteData();
						}
						long licencia = 0;
						DoctorInterno doctor = null;
						if (idDoctor != null)
							doctor = servicioDoctor.buscarPorCedula(idDoctor);
						Usuario usuario = new Usuario(login, doctor, correo,
								password, imagenUsuario, true, nombre,
								apellido, fechaHora, horaAuditoria,
								nombreUsuarioSesion(), gruposUsuario);
						servicioUsuario.guardar(usuario);
						limpiar();
						Mensaje.mensajeInformacion(Mensaje.guardado);
					}
				}
			}

			@Override
			public void eliminar() {
				if (!id.equals("")) {
					Messagebox.show("¿Esta Seguro de Eliminar el Usuario?",
							"Alerta", Messagebox.OK | Messagebox.CANCEL,
							Messagebox.QUESTION,
							new org.zkoss.zk.ui.event.EventListener<Event>() {
								public void onEvent(Event evt)
										throws InterruptedException {
									if (evt.getName().equals("onOK")) {
										Usuario usuario = servicioUsuario
												.buscarPorLogin(id);
										List<Grupo> citas = servicioGrupo
												.buscarGruposDelUsuario(usuario);
										if (!citas.isEmpty())
											Mensaje.mensajeError(Mensaje.noEliminar);
										else {
											servicioUsuario.eliminar(usuario);
											limpiar();
											Mensaje.mensajeInformacion(Mensaje.eliminado);
										}
									}
								}
							});
				} else
					Mensaje.mensajeAlerta(Mensaje.noSeleccionoRegistro);
			}
		};
		botoneraUsuario.appendChild(botonera);
	}

	/* Validaciones de pantalla para poder realizar el guardar */
	public boolean validar() {
		if (txtApellido.getText().compareTo("") == 0
				|| txtCorreo.getText().compareTo("") == 0
				|| txtLoginUsuario.getText().compareTo("") == 0
				|| txtNombre.getText().compareTo("") == 0
				|| txtPassword2Usuario.getText().compareTo("") == 0
				|| txtPasswordUsuario.getText().compareTo("") == 0) {
			Mensaje.mensajeError(Mensaje.camposVacios);
			aplicarColores(txtApellido, txtCorreo, txtNombre,
					txtPassword2Usuario, txtPasswordUsuario, txtLoginUsuario);
			return false;
		} else {
			if (!Validador.validarCorreo(txtCorreo.getValue())) {
				Mensaje.mensajeError(Mensaje.correoInvalido);
				return false;
			} else {
				if (!txtPasswordUsuario.getValue().equals(
						txtPassword2Usuario.getValue())) {
					Mensaje.mensajeError(Mensaje.contrasennasNoCoinciden);
					return false;
				} else
					return true;
			}
		}
	}

	/* Valida que los passwords sean iguales */
	@Listen("onChange = #txtPassword2Usuario")
	public void validarPassword() {
		if (!txtPasswordUsuario.getValue().equals(
				txtPassword2Usuario.getValue())) {
			Mensaje.mensajeAlerta(Mensaje.contrasennasNoCoinciden);
		}
	}

	/* Valida el correo electronico */
	@Listen("onChange = #txtCorreoUsuario")
	public void validarCorreo() {
		if (!Validador.validarCorreo(txtCorreo.getValue())) {
			Mensaje.mensajeAlerta(Mensaje.correoInvalido);
		}
	}

	/* LLena las listas dado un usario */
	public void llenarListas(Usuario usuario) {
		gruposDisponibles = servicioGrupo.buscarTodos();
		if (usuario == null) {
			ltbGruposDisponibles.setModel(new ListModelList<Grupo>(
					gruposDisponibles));
		} else {
			gruposOcupados = servicioGrupo.buscarGruposDelUsuario(usuario);
			ltbGruposAgregados
					.setModel(new ListModelList<Grupo>(gruposOcupados));
			if (!gruposOcupados.isEmpty()) {
				List<Long> ids = new ArrayList<Long>();
				for (int i = 0; i < gruposOcupados.size(); i++) {
					long id = gruposOcupados.get(i).getIdGrupo();
					ids.add(id);
				}
				gruposDisponibles = servicioGrupo.buscarGruposDisponibles(ids);
				ltbGruposDisponibles.setModel(new ListModelList<Grupo>(
						gruposDisponibles));
			}
		}
		ltbGruposAgregados.setMultiple(false);
		ltbGruposAgregados.setCheckmark(false);
		ltbGruposAgregados.setMultiple(true);
		ltbGruposAgregados.setCheckmark(true);

		ltbGruposDisponibles.setMultiple(false);
		ltbGruposDisponibles.setCheckmark(false);
		ltbGruposDisponibles.setMultiple(true);
		ltbGruposDisponibles.setCheckmark(true);
	}

	/* Permite subir una imagen a la vista */
	@Listen("onUpload = #fudImagenUsuario")
	public void processMedia(UploadEvent event) {
		media = event.getMedia();
		imagen.setContent((org.zkoss.image.Image) media);

	}

	/*
	 * Permite mover uno o varios elementos seleccionados desde la lista de la
	 * izquierda a la lista de la derecha
	 */
	@Listen("onClick = #pasar1")
	public void moverDerecha() {
		// gruposDisponibles = servicioGrupo.buscarTodos();
		List<Listitem> listitemEliminar = new ArrayList<Listitem>();
		List<Listitem> listItem = ltbGruposDisponibles.getItems();
		if (listItem.size() != 0) {
			for (int i = 0; i < listItem.size(); i++) {
				if (listItem.get(i).isSelected()) {
					Grupo grupo = listItem.get(i).getValue();
					gruposDisponibles.remove(grupo);
					gruposOcupados.add(grupo);
					ltbGruposAgregados.setModel(new ListModelList<Grupo>(
							gruposOcupados));
					listitemEliminar.add(listItem.get(i));
				}
			}
		}
		for (int i = 0; i < listitemEliminar.size(); i++) {
			ltbGruposDisponibles.removeItemAt(listitemEliminar.get(i)
					.getIndex());
		}
		ltbGruposAgregados.setMultiple(false);
		ltbGruposAgregados.setCheckmark(false);
		ltbGruposAgregados.setMultiple(true);
		ltbGruposAgregados.setCheckmark(true);
	}

	/*
	 * Permite mover uno o varios elementos seleccionados desde la lista de la
	 * derecha a la lista de la izquierda
	 */
	@Listen("onClick = #pasar2")
	public void moverIzquierda() {
		List<Listitem> listitemEliminar = new ArrayList<Listitem>();
		List<Listitem> listItem2 = ltbGruposAgregados.getItems();
		if (listItem2.size() != 0) {
			for (int i = 0; i < listItem2.size(); i++) {
				if (listItem2.get(i).isSelected()) {
					Grupo grupo = listItem2.get(i).getValue();
					gruposOcupados.remove(grupo);
					gruposDisponibles.add(grupo);
					ltbGruposDisponibles.setModel(new ListModelList<Grupo>(
							gruposDisponibles));
					listitemEliminar.add(listItem2.get(i));
				}
			}
		}
		for (int i = 0; i < listitemEliminar.size(); i++) {
			ltbGruposAgregados.removeItemAt(listitemEliminar.get(i).getIndex());
		}
		ltbGruposDisponibles.setMultiple(false);
		ltbGruposDisponibles.setCheckmark(false);
		ltbGruposDisponibles.setMultiple(true);
		ltbGruposDisponibles.setCheckmark(true);
	}

	/* Muestra un catalogo de Usuarios */
	@Listen("onClick = #btnBuscarUsuario")
	public void mostrarCatalogo() throws IOException {
		final List<Usuario> usuarios = servicioUsuario.buscarTodos();
		catalogo = new Catalogo<Usuario>(catalogoUsuario,
				"Catalogo de Usuarios", usuarios, false, "Login", "Nombre",
				"Apellido") {

			@Override
			protected List<Usuario> buscar(String valor, String combo) {
				switch (combo) {
				case "Nombre":
					return servicioUsuario.filtroNombre(valor);
				case "Login":
					return servicioUsuario.filtroLogin(valor);
				case "Apellido":
					return servicioUsuario.filtroApellido(valor);
				default:
					return usuarios;
				}
			}

			@Override
			protected String[] crearRegistros(Usuario objeto) {
				String[] registros = new String[3];
				registros[0] = objeto.getLogin();
				registros[1] = objeto.getNombre();
				registros[2] = objeto.getApellido();
				return registros;
			}

		};
		catalogo.setParent(catalogoUsuario);
		catalogo.doModal();
	}

	/* Busca si existe un usuario con el mismo login */
	@Listen("onChange = #txtLoginUsuario")
	public boolean buscarPorLogin() {
		Usuario usuario = servicioUsuario.buscarPorLogin(txtLoginUsuario
				.getValue());
		if (usuario == null)
			return true;
		else {
			if (usuario.getLogin().equals(id))
				return true;
			else {
				Mensaje.mensajeAlerta(Mensaje.loginUsado);
				txtLoginUsuario.setValue("");
				txtLoginUsuario.setFocus(true);
				return false;
			}
		}
	}

	/* Busca si existe un usuario con la misma cedula nombre escrita */
	@Listen("onChange = #txtCedulaUsuario")
	public void buscarPorCedula() {
		Usuario usuario = servicioUsuario.buscarPorLogin(txtLoginUsuario
				.getValue());
		if (usuario != null)
			llenarCampos(usuario);
	}

	/*
	 * Selecciona un usuario del catalogo y llena los campos con la informacion
	 */
	@Listen("onSeleccion = #catalogoUsuario")
	public void seleccion() {
		Usuario usuario = catalogo.objetoSeleccionadoDelCatalogo();
		llenarCampos(usuario);
		catalogo.setParent(null);
	}

	/* LLena los campos del formulario dado un usuario */
	public void llenarCampos(Usuario usuario) {
		txtCorreo.setValue(usuario.getEmail());
		txtLoginUsuario.setValue(usuario.getLogin());
		txtPasswordUsuario.setValue(usuario.getPassword());
		txtPassword2Usuario.setValue(usuario.getPassword());
		txtNombre.setValue(usuario.getNombre());
		txtApellido.setValue(usuario.getApellido());
		BufferedImage imag;
		if (usuario.getImagen() != null) {
			try {
				imag = ImageIO.read(new ByteArrayInputStream(usuario
						.getImagen()));
				imagen.setContent(imag);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		id = usuario.getLogin();
		llenarListas(usuario);
	}

	/* Abre la vista de Grupos */
	@Listen("onClick = #btnAbrirGrupo")
	public void abrirGrupo() {
		final HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("id", "medicina");
		map.put("lista", gruposDisponibles);
		map.put("listbox", ltbGruposDisponibles);
		map.put("titulo", "Grupo");
		Sessions.getCurrent().setAttribute("itemsCatalogo", map);
		List<Arbol> arboles = servicioArbol.buscarPorNombreArbol("Grupo");
		if (!arboles.isEmpty()) {
			Arbol arbolItem = arboles.get(0);
			cArbol.abrirVentanas(arbolItem, tabBox, contenido, tab, tabs);
		}
	}

	public void recibirGrupo(List<Grupo> lista, Listbox l) {
		ltbGruposDisponibles = l;
		gruposDisponibles = lista;
		ltbGruposDisponibles.setModel(new ListModelList<Grupo>(
				gruposDisponibles));
		ltbGruposDisponibles.setMultiple(false);
		ltbGruposDisponibles.setCheckmark(false);
		ltbGruposDisponibles.setMultiple(true);
		ltbGruposDisponibles.setCheckmark(true);
	}

	@Listen("onClick = #btnBuscarDoctor")
	public void mostrarCatalogo2() {
		final List<DoctorInterno> usuarios = servicioDoctor.buscarTodos();
		catalogoDoctor = new Catalogo<DoctorInterno>(catalogoDoctores,
				"Catalogo de Doctores", usuarios, false, "Cedula", "Ficha",
				"Nombre", "Apellido") {

			@Override
			protected List<DoctorInterno> buscar(String valor, String combo) {
				switch (combo) {
				case "Cedula":
					return servicioDoctor.filtroCedula(valor);
				case "Ficha":
					return servicioDoctor.filtroFicha(valor);
				case "Nombre":
					return servicioDoctor.filtroNombre(valor);
				case "Apellido":
					return servicioDoctor.filtroApellido(valor);
				default:
					return usuarios;
				}
			}

			@Override
			protected String[] crearRegistros(DoctorInterno objeto) {
				String[] registros = new String[4];
				registros[0] = objeto.getCedula();
				registros[1] = objeto.getFicha();
				registros[2] = objeto.getPrimerNombre();
				registros[3] = objeto.getPrimerApellido();
				return registros;
			}

		};
		catalogoDoctor.setParent(catalogoDoctores);
		catalogoDoctor.doModal();
	}

	/* Permite la seleccion de un item del catalogo de doctores */
	@Listen("onSeleccion = #catalogoDoctores")
	public void seleccionarDoctor() {
		DoctorInterno usuario = catalogoDoctor.objetoSeleccionadoDelCatalogo();
		lblNombreDoctor.setValue(usuario.getPrimerNombre() + " "
				+ usuario.getPrimerApellido());
		idDoctor = usuario.getCedula();
		catalogoDoctor.setParent(null);
	}
}
