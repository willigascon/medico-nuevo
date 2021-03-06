package controlador.security;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.imageio.ImageIO;

import modelo.security.Grupo;
import modelo.security.Usuario;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.zkoss.image.AImage;
import org.zkoss.util.media.Media;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zk.ui.event.UploadEvent;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Div;
import org.zkoss.zul.Fileupload;
import org.zkoss.zul.Image;
import org.zkoss.zul.Tab;
import org.zkoss.zul.Textbox;

import componente.Botonera;
import componente.Mensaje;
import componente.Validador;
import controlador.utils.CGenerico;

public class CEditarUsuario extends CGenerico {

	@Wire
	private Textbox txtNombreUsuarioEditar;
	@Wire
	private Textbox txtNombre;
	@Wire
	private Textbox txtApellido;
	@Wire
	private Textbox txtCorreo;
	@Wire
	private Textbox txtClaveUsuarioNueva;
	@Wire
	private Textbox txtClaveUsuarioConfirmar;
	@Wire
	private Image imgUsuario;
	@Wire
	private Fileupload fudImagenUsuario;
	@Wire
	private Div botoneraEditarUsuario;
	@Wire
	private Div divEditarUsuario;
	private String id = "";
	private Media media;
	PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
	URL url = getClass().getResource("/controlador/utils/usuario.png");
	private static final long serialVersionUID = 2439502647179786175L;

	@Override
	public void inicializar() throws IOException {
		HashMap<String, Object> mapa = (HashMap<String, Object>) Sessions
				.getCurrent().getAttribute("mapaGeneral");
		if (mapa != null) {
			if (mapa.get("tabsGenerales") != null) {
				tabs = (List<Tab>) mapa.get("tabsGenerales");
				mapa.clear();
				mapa = null;
			}
		}
		Usuario usuario = servicioUsuario.buscarPorLogin(nombreUsuarioSesion());
		id = nombreUsuarioSesion();
		txtNombreUsuarioEditar.setValue(usuario.getLogin());
		txtNombre.setValue(usuario.getNombre());
		txtApellido.setValue(usuario.getApellido());
		txtCorreo.setValue(usuario.getEmail());
		txtClaveUsuarioConfirmar.setValue(usuario.getPassword());
		txtClaveUsuarioNueva.setValue(usuario.getPassword());
		if (usuario.getImagen() == null) {
			imgUsuario.setContent(new AImage(url));
		} else {
			try {
				BufferedImage imag;
				imag = ImageIO.read(new ByteArrayInputStream(usuario
						.getImagen()));
				imgUsuario.setContent(imag);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		Botonera botonera = new Botonera() {

			@Override
			public void salir() {
				cerrarVentana(divEditarUsuario, "Editar Usuario", tabs);
			}

			@Override
			public void limpiar() {
				Usuario usuario = servicioUsuario
						.buscarPorLogin(nombreUsuarioSesion());
				id = nombreUsuarioSesion();
				txtNombreUsuarioEditar.setValue(usuario.getLogin());
				txtNombre.setValue(usuario.getNombre());
				txtApellido.setValue(usuario.getApellido());
				txtCorreo.setValue(usuario.getEmail());
				txtClaveUsuarioConfirmar.setValue(usuario.getPassword());
				txtClaveUsuarioNueva.setValue(usuario.getPassword());
				if (usuario.getImagen() == null) {
					try {
						imgUsuario.setContent(new AImage(url));
					} catch (IOException e) {

						e.printStackTrace();
					}
				} else {
					try {
						BufferedImage imag;
						imag = ImageIO.read(new ByteArrayInputStream(usuario
								.getImagen()));
						imgUsuario.setContent(imag);
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
				limpiarColores(txtClaveUsuarioConfirmar,txtClaveUsuarioNueva,txtApellido,txtCorreo,txtNombre);
			}

			@Override
			public void guardar() {
				if (validar()) {
					if (txtClaveUsuarioNueva.getValue().equals(
							txtClaveUsuarioConfirmar.getValue())) {
						Usuario usuario = servicioUsuario.buscarPorLogin(id);
						byte[] imagenUsuario = null;
						imagenUsuario = imgUsuario.getContent().getByteData();
						String password = txtClaveUsuarioConfirmar.getValue();
						usuario.setPassword(password);
						usuario.setImagen(imagenUsuario);
						usuario.setNombre(txtNombre.getValue());
						usuario.setApellido(txtApellido.getValue());
						usuario.setEmail(txtCorreo.getValue());
						servicioUsuario.guardar(usuario);
						Mensaje.mensajeInformacion(Mensaje.guardado);
						limpiar();
					} else {
						Mensaje.mensajeError(Mensaje.contrasennasNoCoinciden);
					}
				}
			}

			@Override
			public void eliminar() {

			}
		};
		botonera.getChildren().get(1).setVisible(false);
		botoneraEditarUsuario.appendChild(botonera);
	}

	/* Valida que los passwords sean iguales */
	@Listen("onChange = #txtClaveUsuarioConfirmar")
	public void validarPassword() {
		if (!txtClaveUsuarioNueva.getValue().equals(
				txtClaveUsuarioConfirmar.getValue())) {
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

	protected boolean validar() {
		if (txtClaveUsuarioConfirmar.getValue().equals("")
				|| txtClaveUsuarioNueva.getValue().equals("")
				|| txtApellido.getText().compareTo("") == 0
				|| txtCorreo.getText().compareTo("") == 0
				|| txtNombre.getText().compareTo("") == 0) {
			aplicarColores(txtClaveUsuarioConfirmar,txtClaveUsuarioNueva,txtApellido,txtCorreo,txtNombre);
			Mensaje.mensajeError(Mensaje.camposVacios);
			return false;
		} else {
			if (!Validador.validarCorreo(txtCorreo.getValue())) {
				Mensaje.mensajeError(Mensaje.correoInvalido);
				return false;
			} else {
				if (!txtClaveUsuarioConfirmar.getValue().equals(
						txtClaveUsuarioNueva.getValue())) {
					Mensaje.mensajeError(Mensaje.contrasennasNoCoinciden);
					return false;
				} else
					return true;
			}
		}
	}

	@Listen("onUpload = #fudImagenUsuario")
	public void processMedia(UploadEvent event) throws IOException {
		media = event.getMedia();
		imgUsuario.setContent(new AImage(url));
		if (Validador.validarTipoImagen(media)) {
			if (Validador.validarTamannoImagen(media)) {
				imgUsuario.setHeight("150px");
				imgUsuario.setWidth("150px");
				imgUsuario.setContent((org.zkoss.image.Image) media);
				imgUsuario.setVisible(true);
			} else {
				Mensaje.mensajeError(Mensaje.tamanioMuyGrande);
				imgUsuario.setContent(new AImage(url));
			}
		} else {
			Mensaje.mensajeError(Mensaje.formatoImagenNoValido);
			imgUsuario.setContent(new AImage(url));
		}
	}

}
