package controlador.medico;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.imageio.ImageIO;

import modelo.medico.consulta.Consulta;
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
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Button;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Comboitem;
import org.zkoss.zul.Div;
import org.zkoss.zul.Groupbox;
import org.zkoss.zul.Image;
import org.zkoss.zul.Include;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;
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
import controlador.security.CArbol;
import controlador.utils.CGenerico;

public class CDoctorInterno extends CGenerico {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Wire
	private Div divDoctor;
	@Wire
	private Div botoneraDoctor;
	@Wire
	private Div catalogoDoctor;
	@Wire
	private Textbox txtNombreUsuario;
	@Wire
	private Textbox txtCedulaUsuario;
	@Wire
	private Textbox txtApellidoUsuario;
	@Wire
	private Textbox txtNombre2Usuario;
	@Wire
	private Textbox txtApellido2Usuario;
	@Wire
	private Textbox txtFichaUsuario;
	@Wire
	private Textbox txtTelefonoUsuario;
	@Wire
	private Textbox txtDireccionUsuario;
	@Wire
	private Textbox txtLicenciaCUsuario;
	@Wire
	private Textbox txtLicenciaMUsuario;
	@Wire
	private Textbox txtLicenciaIUsuario;
	@Wire
	private Combobox cmbEspecialidad;
	@Wire
	private Button btnBuscarUsuario;
	@Wire
	private Radiogroup rdbSexoUsuario;
	@Wire
	private Radio rdoSexoFUsuario;
	@Wire
	private Radio rdoSexoMUsuario;
	String id = "";
	Catalogo<DoctorInterno> catalogo;
	private CArbol cArbol = new CArbol();

	@Override
	public void inicializar() throws IOException {
		contenido = (Include) divDoctor.getParent();
		Tabbox tabox = (Tabbox) divDoctor.getParent().getParent().getParent()
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
		llenarComboEspecialidad();
		Botonera botonera = new Botonera() {

			@Override
			public void salir() {
				cerrarVentana(divDoctor, titulo, tabs);
			}

			@Override
			public void limpiar() {
				limpiarColores(txtApellidoUsuario, txtCedulaUsuario,
						txtDireccionUsuario, txtFichaUsuario, txtNombreUsuario,
						txtTelefonoUsuario, cmbEspecialidad, rdbSexoUsuario);
				cmbEspecialidad.setValue("");
				txtApellidoUsuario.setValue("");
				txtApellido2Usuario.setValue("");
				txtCedulaUsuario.setValue("");
				txtCedulaUsuario.setDisabled(false);
				txtDireccionUsuario.setValue("");
				txtFichaUsuario.setValue("");
				txtLicenciaCUsuario.setValue("");
				txtLicenciaIUsuario.setValue("");
				txtLicenciaMUsuario.setValue("");
				txtNombreUsuario.setValue("");
				txtNombre2Usuario.setValue("");
				txtTelefonoUsuario.setValue("");
				rdoSexoFUsuario.setChecked(false);
				rdoSexoMUsuario.setChecked(false);
				id = "";
			}

			@Override
			public void guardar() {
				if (validar()) {
					Especialidad especialidad = null;
					String licenciaI = "";
					String cedula = txtCedulaUsuario.getValue();
					String direccion = txtDireccionUsuario.getValue();
					String ficha = txtFichaUsuario.getValue();
					String licenciaC = txtLicenciaCUsuario.getValue();
					if (txtLicenciaIUsuario.getText().compareTo("") != 0)
						licenciaI = txtLicenciaIUsuario.getValue();
					String licenciaM = txtLicenciaMUsuario.getValue();
					String nombre = txtNombreUsuario.getValue();
					String apellido = txtApellidoUsuario.getValue();
					String nombre2 = txtNombre2Usuario.getValue();
					String apellido2 = txtApellido2Usuario.getValue();
					String telefono = txtTelefonoUsuario.getValue();
					String sexo = "";
					if (rdoSexoFUsuario.isChecked())
						sexo = "F";
					else
						sexo = "M";
					especialidad = servicioEspecialidad.buscar(Long
							.parseLong(cmbEspecialidad.getSelectedItem()
									.getContext()));
					long licencia = 0;
					if (!licenciaI.equals(""))
						licencia = Long.parseLong(licenciaI);
					DoctorInterno doctor = new DoctorInterno(cedula, direccion,
							ficha, licenciaC, licencia, licenciaM, apellido,
							nombre, apellido2, nombre2, sexo, telefono,
							especialidad, fechaHora, horaAuditoria,
							nombreUsuarioSesion());
					servicioDoctor.guardar(doctor);
					limpiar();
					Mensaje.mensajeInformacion(Mensaje.guardado);
				}

			}

			@Override
			public void eliminar() {
				if (!id.equals("")) {
					Messagebox.show("¿Esta Seguro de Eliminar el Doctor?",
							"Alerta", Messagebox.OK | Messagebox.CANCEL,
							Messagebox.QUESTION,
							new org.zkoss.zk.ui.event.EventListener<Event>() {
								public void onEvent(Event evt)
										throws InterruptedException {
									if (evt.getName().equals("onOK")) {
										DoctorInterno usuario = servicioDoctor
												.buscarPorCedula(id);
										List<Cita> citas = servicioCita
												.buscarPorDoctor(usuario);
										List<Consulta> consultas = servicioConsulta
												.buscarPorDoctor(usuario);
										if (!citas.isEmpty()
												&& !consultas.isEmpty())
											Mensaje.mensajeError(Mensaje.noEliminar);
										else {
											servicioDoctor.eliminar(usuario);
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
		botoneraDoctor.appendChild(botonera);
	}

	public boolean validar() {
		if (txtApellidoUsuario.getText().compareTo("") == 0
				|| txtCedulaUsuario.getText().compareTo("") == 0
				|| txtDireccionUsuario.getText().compareTo("") == 0
				|| txtFichaUsuario.getText().compareTo("") == 0
				|| txtNombreUsuario.getText().compareTo("") == 0
				|| txtTelefonoUsuario.getText().compareTo("") == 0
				|| cmbEspecialidad.getText().compareTo("") == 0
				|| (!rdoSexoFUsuario.isChecked() && !rdoSexoMUsuario
						.isChecked())) {
			aplicarColores(txtApellidoUsuario, txtCedulaUsuario,
					txtDireccionUsuario, txtFichaUsuario, txtNombreUsuario,
					txtTelefonoUsuario, cmbEspecialidad, rdbSexoUsuario);
			Mensaje.mensajeError(Mensaje.camposVacios);
			return false;
		} else {
			if (!Validador.validarTelefono(txtTelefonoUsuario.getValue())) {
				Mensaje.mensajeError(Mensaje.telefonoInvalido);
				return false;
			} else {
				if (!Validador.validarNumero(txtCedulaUsuario.getValue())) {
					Mensaje.mensajeError(Mensaje.cedulaInvalida);
					return false;
				} else {
					if (txtLicenciaIUsuario.getText().compareTo("") != 0
							&& !Validador.validarNumero(txtLicenciaIUsuario
									.getValue())) {
						Mensaje.mensajeError("Licencia Invalida");
						return false;
					} else
						return true;
				}
			}
		}
	}

	/* Valida el numero telefonico */
	@Listen("onChange = #txtTelefonoUsuario")
	public void validarTelefono() {
		if (!Validador.validarTelefono(txtTelefonoUsuario.getValue())) {
			Mensaje.mensajeAlerta(Mensaje.telefonoInvalido);
		}
	}

	/* Valida la cedula */
	@Listen("onChange = #txtCedulaUsuario")
	public void validarCedula() {
		if (!Validador.validarNumero(txtCedulaUsuario.getValue())) {
			Mensaje.mensajeAlerta(Mensaje.cedulaInvalida);
		}
	}

	/* Valida licencia de ipsasel */
	@Listen("onChange = #txtLicenciaIUsuario")
	public void validarLicencia() {
		if (!Validador.validarNumero(txtLicenciaIUsuario.getValue())) {
			Mensaje.mensajeAlerta("Licencia Invalida");
		}
	}

	/* Llena el combo de Especialidades cada vez que se abre */
	@Listen("onOpen = #cmbEspecialidad")
	public void llenarComboEspecialidad() {
		List<Especialidad> especialidades = servicioEspecialidad.buscarTodas();
		cmbEspecialidad
				.setModel(new ListModelList<Especialidad>(especialidades));
	}

	@Listen("onClick = #btnBuscarUsuario")
	public void mostrarCatalogo() throws IOException {
		final List<DoctorInterno> usuarios = servicioDoctor.buscarTodos();
		catalogo = new Catalogo<DoctorInterno>(catalogoDoctor,
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
		catalogo.setParent(catalogoDoctor);
		catalogo.doModal();
	}

	/* Busca si existe un usuario con la misma cedula nombre escrita */
	@Listen("onChange = #txtCedulaUsuario")
	public void buscarPorCedula() {
		DoctorInterno usuario = servicioDoctor.buscarPorCedula(txtCedulaUsuario
				.getValue());
		if (usuario != null)
			llenarCampos(usuario);
	}

	/*
	 * Selecciona un usuario del catalogo y llena los campos con la informacion
	 */
	@Listen("onSeleccion = #catalogoDoctor")
	public void seleccion() {
		DoctorInterno usuario = catalogo.objetoSeleccionadoDelCatalogo();
		llenarCampos(usuario);
		catalogo.setParent(null);
	}

	/* LLena los campos del formulario dado un usuario */
	public void llenarCampos(DoctorInterno usuario) {
		txtCedulaUsuario.setValue(usuario.getCedula());
		txtDireccionUsuario.setValue(usuario.getDireccion());
		txtFichaUsuario.setValue(usuario.getFicha());
		txtLicenciaCUsuario.setValue(usuario.getLicenciaCm());
		txtLicenciaIUsuario.setValue(String.valueOf(usuario
				.getLicenciaInpsasel()));
		txtLicenciaMUsuario.setValue(usuario.getLicenciaMsds());
		txtNombreUsuario.setValue(usuario.getPrimerNombre());
		txtNombre2Usuario.setValue(usuario.getSegundoNombre());
		txtApellidoUsuario.setValue(usuario.getPrimerApellido());
		txtApellido2Usuario.setValue(usuario.getSegundoApellido());
		txtTelefonoUsuario.setValue(usuario.getTelefono());
		String sexo = usuario.getSexo();
		if (sexo.equals("F"))
			rdoSexoFUsuario.setChecked(true);
		else
			rdoSexoMUsuario.setChecked(true);
		cmbEspecialidad.setValue(usuario.getEspecialidad().getDescripcion());
		Comboitem item = cmbEspecialidad.appendItem(usuario.getEspecialidad()
				.getDescripcion());
		item.setContext(String.valueOf(usuario.getEspecialidad()
				.getIdEspecialidad()));
		cmbEspecialidad.setSelectedItem(item);
		txtCedulaUsuario.setDisabled(true);
		id = usuario.getCedula();
	}

	/* Abre la vista de Estado */
	@Listen("onClick = #btnAbrirEspecialidad")
	public void abrirEstado() {
		List<Arbol> arboles = servicioArbol
				.buscarPorNombreArbol("Especialidad");
		final HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("titulo", "Especialidad");
		Sessions.getCurrent().setAttribute("itemsCatalogo", map);
		if (!arboles.isEmpty()) {
			Arbol arbolItem = arboles.get(0);
			cArbol.abrirVentanas(arbolItem, tabBox, contenido, tab, tabs);
		}
	}
}
