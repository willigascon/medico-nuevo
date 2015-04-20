package controlador.medico;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.net.URL;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.imageio.ImageIO;

import modelo.medico.maestro.EstadoCivil;
import modelo.medico.maestro.Medicina;
import modelo.medico.maestro.Paciente;
import modelo.organizacion.Area;
import modelo.organizacion.Cargo;
import modelo.organizacion.Ciudad;
import modelo.organizacion.Empresa;
import modelo.security.Arbol;

import org.zkoss.image.AImage;
import org.zkoss.util.media.Media;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.UploadEvent;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Button;
import org.zkoss.zul.Checkbox;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Div;
import org.zkoss.zul.Doublespinner;
import org.zkoss.zul.Image;
import org.zkoss.zul.Include;
import org.zkoss.zul.Label;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Radio;
import org.zkoss.zul.Radiogroup;
import org.zkoss.zul.Row;
import org.zkoss.zul.Spinner;
import org.zkoss.zul.Tab;
import org.zkoss.zul.Tabbox;
import org.zkoss.zul.Textbox;

import componente.Botonera;
import componente.Buscar;
import componente.Catalogo;
import componente.Mensaje;
import componente.Validador;

import controlador.security.CArbol;
import controlador.utils.CGenerico;

public class CPaciente extends CGenerico {

	private static final long serialVersionUID = -8967604751368729529L;

	@Wire
	private Div divCatalogoModeloFamiliar;
	@Wire
	private Tab tabDatosBasicos;
	@Wire
	private Tab tabDatosContacto;
	@Wire
	private Tab tabDatosCronico;
	@Wire
	private Button btnBuscarPaciente;
	@Wire
	private Button fudImagenPaciente;
	@Wire
	private Media media;
	@Wire
	private Image imagenPaciente;
	@Wire
	private Div divPaciente;
	@Wire
	private Div botoneraPaciente;
	@Wire
	private Div catalogoPaciente;
	@Wire
	private Textbox txtNombre1Paciente;
	@Wire
	private Textbox txtApellido1Paciente;
	@Wire
	private Textbox txtNombre2Paciente;
	@Wire
	private Textbox txtApellido2Paciente;
	@Wire
	private Textbox txtCedulaPaciente;
	@Wire
	private Radiogroup rdgEstatus;
	@Wire
	private Radio rdoActivo;
	@Wire
	private Radio rdoInactivo;
	@Wire
	private Radio rdoMuerte;
	@Wire
	private Radiogroup rdgAlergia;
	@Wire
	private Radio rdoSiAlergico;
	@Wire
	private Radio rdoNoAlergico;
	@Wire
	private Radiogroup rdgLentes;
	@Wire
	private Radio rdoSiLentes;
	@Wire
	private Radio rdoNoLentes;
	@Wire
	private Radiogroup rdgDiscapacidad;
	@Wire
	private Radio rdoSiDiscapacidad;
	@Wire
	private Radio rdoNoDiscapacidad;
	@Wire
	private Textbox txtFichaPaciente;
	@Wire
	private Datebox dtbFechaNac;
	@Wire
	private Textbox txtLugarNacimiento;
	@Wire
	private Textbox txtAlergia;
	@Wire
	private Textbox txtRif;
	@Wire
	private Label lblEdad;
	@Wire
	private Combobox cmbSexo;
	@Wire
	private Combobox cmbEstadoCivil;
	@Wire
	private Combobox cmbGrupoSanguineo;
	@Wire
	private Combobox cmbMano;
	@Wire
	private Doublespinner dspEstatura;
	@Wire
	private Doublespinner dspPeso;
	@Wire
	private Combobox cmbOrigen;
	@Wire
	private Combobox cmbTipoDiscapacidad;
	@Wire
	private Textbox txtOtras;
	@Wire
	private Textbox txtObservacionEstatus;
	@Wire
	private Combobox cmbCargo;
	@Wire
	private Combobox cmbArea;
	@Wire
	private Combobox cmbEmpresa;
	@Wire
	private Combobox cmbNomina;
	@Wire
	private Combobox cmbCiudad;
	@Wire
	private Textbox txtDireccion;
	@Wire
	private Textbox txtTelefono1;
	@Wire
	private Textbox txtTelefono2;
	@Wire
	private Textbox txtCorreo;
	@Wire
	private Textbox txtNombresEmergencia;
	@Wire
	private Textbox txtApellidosEmergencia;
	@Wire
	private Combobox cmbParentescoEmergencia;
	@Wire
	private Textbox txtTelefono1Emergencia;
	@Wire
	private Textbox txtTelefono2Emergencia;
	@Wire
	private Button btnBuscarTrabajadores;
	@Wire
	private Row rowCargoyEmpresa;
	@Wire
	private Row rowAreayNomina;
	//
	@Wire
	private Textbox txtProfesion;
	@Wire
	private Combobox cmbNivelEducativo;
	@Wire
	private Spinner spnCarga;
	@Wire
	private Datebox dtbFechaIngreso;
	@Wire
	private Datebox dtbFechaMuerte;
	@Wire
	private Radio rdoV;
	@Wire
	private Radio rdoE;
	@Wire
	private Textbox txtNroInpsasel;
	@Wire
	private Combobox cmbTurno;
	@Wire
	private Datebox dtbInscripcionIVSS;
	@Wire
	private Textbox txtRetiroIVSS;
	@Wire
	private Datebox dtbFechaEgreso;
	@Wire
	private Button btnVer;
	@Wire
	private Label lblFichaI;
	@Wire
	private Label lblFecha;
	@Wire
	private Radio rdoSiBrigadista;
	@Wire
	private Radio rdoNoBrigadista;
	@Wire
	private Radio rdoSiPreempleado;
	@Wire
	private Radio rdoNoPreempleado;
	@Wire
	private Radio rdoSiComite;
	@Wire
	private Radio rdoNoComite;
	@Wire
	private Label lblEstado;
	@Wire
	private Textbox txtUrb;
	@Wire
	private Textbox txtAvCalle;
	@Wire
	private Textbox txtSector;
	@Wire
	private Textbox txtPuntoReferencia;
	@Wire
	private Textbox txtParroquia;
	@Wire
	private Textbox txtMunicipio;
	@Wire
	private Textbox txtNro;
	@Wire
	private Textbox txtOtroTransporte;
	@Wire
	private Checkbox chkPublico;
	@Wire
	private Checkbox chkOtro;
	@Wire
	private Checkbox chkPrivado;
	@Wire
	private Checkbox chkDusa;
	@Wire
	private Checkbox chkMoto;
	@Wire
	private Checkbox chkBicicleta;
	URL url = getClass().getResource("/controlador/utils/usuario.png");
	private CArbol cArbol = new CArbol();
	String id = "";
	String cedTrabajador = "";
	Catalogo<Paciente> catalogo;
	Catalogo<Paciente> catalogoFamiliar;

	List<Medicina> medicinasDisponibles = new ArrayList<Medicina>();
	private String idBoton = "";

	private String ficha = "";

	@Override
	public void inicializar() throws IOException {

		fudImagenPaciente.setClass("btn");

		contenido = (Include) divPaciente.getParent();
		Tabbox tabox = (Tabbox) divPaciente.getParent().getParent().getParent()
				.getParent();
		tabBox = tabox;
		tab = (Tab) tabox.getTabs().getLastChild();
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
		llenarComboCiudad();
		llenarComboEmpresa();
		llenarComboArea();
		llenarComboCargo();
		llenarComboCivil();
		rdoActivo.setChecked(true);
		Botonera botonera = new Botonera() {

			@Override
			public void salir() {
				cerrarVentana(divPaciente, titulo, tabs);

			}

			@Override
			public void limpiar() {
				limpiarCampos();
			}

			@Override
			public void guardar() {
				if (validar()) {

					byte[] imagen = null;
					if (media instanceof org.zkoss.image.Image) {
						imagen = imagenPaciente.getContent().getByteData();

					} else {
						try {
							imagenPaciente.setContent(new AImage(url));
						} catch (IOException e) {
							e.printStackTrace();
						}
						imagen = imagenPaciente.getContent().getByteData();
					}

					String profesion, nacionalidad, nivelEducativo, turno, retiroIVSS, nroInpsasel, nombre1, apellido1, cedula, nombre2, apellido2, ficha, detalleAlergia, lugarNac, sexo, grupoSanguineo, mano, origen, tipoDiscapacidad, otrasDiscapacidad, direccion, telefono1, telefono2, correo, nombresE, apellidosE, telefono1E, telefono2E, parentescoE, parentescoFamiliar;
					int edad, carga;
					boolean alergia = false, discapacidad = false, lentes = false;
					double estatura, peso;

					Timestamp fechaIngreso = null;
					Timestamp fechaEgreso = null;
					Timestamp fechaInscripcion = null;
					Timestamp fechaMuerte = null;

					if (dtbFechaIngreso.getValue() != null)
						fechaIngreso = new Timestamp(dtbFechaIngreso.getValue()
								.getTime());

					if (dtbFechaEgreso.getValue() != null)
						fechaEgreso = new Timestamp(dtbFechaEgreso.getValue()
								.getTime());

					if (dtbInscripcionIVSS.getValue() != null)
						fechaInscripcion = new Timestamp(dtbInscripcionIVSS
								.getValue().getTime());

					if (rdoV.isChecked())
						nacionalidad = "V";
					else
						nacionalidad = "E";

					boolean estatus = true;
					if (!rdoActivo.isChecked())
						estatus = false;
					else
						estatus = true;

					boolean muerte = false;
					if (rdoMuerte.isChecked()) {
						muerte = true;
						if (dtbFechaMuerte.getValue() != null)
							fechaMuerte = new Timestamp(dtbFechaMuerte
									.getValue().getTime());
					}

					profesion = txtProfesion.getValue();
					nivelEducativo = cmbNivelEducativo.getValue();
					turno = cmbTurno.getValue();
					retiroIVSS = txtRetiroIVSS.getValue();
					nroInpsasel = txtNroInpsasel.getValue();
					carga = spnCarga.getValue();
					nombre1 = txtNombre1Paciente.getValue();
					apellido1 = txtApellido1Paciente.getValue();
					nombre2 = txtNombre2Paciente.getValue();
					apellido2 = txtApellido2Paciente.getValue();
					cedula = txtCedulaPaciente.getValue();
					ficha = txtFichaPaciente.getValue();
					detalleAlergia = txtAlergia.getValue();
					lugarNac = txtLugarNacimiento.getValue();
					sexo = cmbSexo.getValue();

					grupoSanguineo = cmbGrupoSanguineo.getValue();
					mano = cmbMano.getValue();
					origen = cmbOrigen.getValue();
					tipoDiscapacidad = cmbTipoDiscapacidad.getValue();
					otrasDiscapacidad = txtOtras.getValue();
					direccion = txtDireccion.getValue();
					telefono1 = txtTelefono1.getValue();
					telefono2 = txtTelefono2.getValue();
					correo = txtCorreo.getValue();
					nombresE = txtNombresEmergencia.getValue();
					apellidosE = txtApellidosEmergencia.getValue();
					telefono1E = txtTelefono1Emergencia.getValue();
					telefono2E = txtTelefono2Emergencia.getValue();
					parentescoE = cmbParentescoEmergencia.getValue();
					String observacionEstatus = txtObservacionEstatus
							.getValue();

					edad = calcularEdad(dtbFechaNac.getValue());
					String cedulaFamiliar = "";
					Empresa empresa = null;
					Area area = null;
					Cargo cargo = null;
					EstadoCivil estadoCivil = null;
					if (rdoSiAlergico.isChecked())
						alergia = true;
					if (cmbEstadoCivil.getSelectedItem() != null) {
						if (cmbEstadoCivil.getSelectedItem().getContext() != null) {
							estadoCivil = servicioEstadoCivil.buscar(Long
									.parseLong(cmbEstadoCivil.getSelectedItem()
											.getContext()));
						}
					}
					if (cmbCargo.getSelectedItem().getContext() != null)
						cargo = servicioCargo.buscar(Long.parseLong(cmbCargo
								.getSelectedItem().getContext()));
					if (cmbArea.getSelectedItem().getContext() != null)
						area = servicioArea.buscar(Long.parseLong(cmbArea
								.getSelectedItem().getContext()));
					if (cmbEmpresa.getSelectedItem().getContext() != null)
						empresa = servicioEmpresa.buscar(Long
								.parseLong(cmbEmpresa.getSelectedItem()
										.getContext()));
					String nomina = "";
					if (cmbNomina.getValue() != null)
						nomina = cmbNomina.getValue();

					if (rdoSiDiscapacidad.isChecked())
						discapacidad = true;
					if (rdoSiLentes.isChecked())
						lentes = true;

					estatura = dspEstatura.getValue();
					peso = dspPeso.getValue();
					Timestamp fechaNac = new Timestamp(dtbFechaNac.getValue()
							.getTime());

					Ciudad ciudad = servicioCiudad
							.buscar(Long.parseLong(cmbCiudad.getSelectedItem()
									.getContext()));
					Boolean brigadista = false;

					if (rdoSiBrigadista.isChecked())
						brigadista = true;
					Boolean comite = false;
					if (rdoSiComite.isChecked())
						comite = true;
					Boolean trabajador = true;
					if (rdoSiPreempleado.isChecked())
						trabajador = false;

					Paciente paciente = new Paciente(cedula, ficha, apellido1,
							nombre1, apellido2, nombre2, trabajador,
							discapacidad, alergia, lentes, fechaNac, lugarNac,
							sexo, edad, grupoSanguineo, detalleAlergia, mano,
							estatura, peso, origen, tipoDiscapacidad,
							otrasDiscapacidad, fechaHora, horaAuditoria,
							nombreUsuarioSesion(), imagen, direccion, correo,
							telefono1, telefono2, nombresE, apellidosE,
							parentescoE, telefono1E, telefono2E,
							cedulaFamiliar, "", empresa, ciudad, cargo, area,
							nomina);
					paciente.setBrigadista(brigadista);
					paciente.setEstadoCivil(estadoCivil);
					paciente.setNacionalidad(nacionalidad);
					paciente.setCarga(carga);
					paciente.setNivelEducativo(nivelEducativo);
					paciente.setProfesion(profesion);
					paciente.setNroInpsasel(nroInpsasel);
					paciente.setRetiroIVSS(retiroIVSS);
					paciente.setFechaIngreso(fechaIngreso);
					paciente.setFechaInscripcionIVSS(fechaInscripcion);
					paciente.setFechaEgreso(fechaEgreso);
					paciente.setTurno(turno);
					paciente.setNomina(nomina);
					paciente.setEstatus(estatus);
					paciente.setObservacionEstatus(observacionEstatus);
					paciente.setMuerte(muerte);
					paciente.setFechaMuerte(fechaMuerte);
					paciente.setDelegadoPrevencion(comite);
					paciente.setRif(txtRif.getValue());
					paciente.setMunicipio(txtMunicipio.getValue());
					paciente.setParroquia(txtParroquia.getValue());
					paciente.setSector(txtSector.getValue());
					paciente.setPuntoReferencia(txtPuntoReferencia.getValue());
					paciente.setUrb(txtUrb.getValue());
					paciente.setAvCalle(txtAvCalle.getValue());

					paciente.setNro(txtNro.getValue());
					paciente.setOtroTransporte(txtOtroTransporte.getValue());
					String transporte = "";
					if (chkPublico.isChecked())
						transporte = transporte + "," + chkPublico.getLabel();
					if (chkOtro.isChecked())
						transporte = transporte + "," + chkOtro.getLabel();
					if (chkPrivado.isChecked())
						transporte = transporte + "," + chkPrivado.getLabel();
					if (chkDusa.isChecked())
						transporte = transporte + "," + chkDusa.getLabel();
					if (chkMoto.isChecked())
						transporte = transporte + "," + chkMoto.getLabel();
					if (chkBicicleta.isChecked())
						transporte = transporte + "," + chkBicicleta.getLabel();

					paciente.setTransporte(transporte);
					servicioPaciente.guardar(paciente);
					if (!rdoActivo.isChecked()) {
						paciente = servicioPaciente.buscarPorCedula(cedula);
						inhabilitarTrabajadorYTodosFamiliares(paciente);
					}

					limpiar();
					Mensaje.mensajeInformacion(Mensaje.guardado);
				}
			}

			@Override
			public void eliminar() {
			}
		};
		botonera.getChildren().get(1).setVisible(false);
		botoneraPaciente.appendChild(botonera);
	}

	/* Permite validar que todos los campos esten completos */
	public boolean validar() {
		if (txtApellido1Paciente.getText().compareTo("") == 0
				|| txtNombre1Paciente.getText().compareTo("") == 0
				|| txtCedulaPaciente.getText().compareTo("") == 0
				|| dtbFechaNac.getText().compareTo("") == 0
				|| spnCarga.getValue() == null
				|| cmbEstadoCivil.getText().compareTo("") == 0
				|| cmbGrupoSanguineo.getText().compareTo("") == 0
				|| cmbMano.getText().compareTo("") == 0
				|| cmbSexo.getText().compareTo("") == 0
				|| dspPeso.getValue() == 0
				|| dspEstatura.getValue() == 0
				|| cmbCiudad.getText().compareTo("") == 0
				|| txtTelefono2.getText().compareTo("") == 0
				|| (!rdoSiAlergico.isChecked() && !rdoNoAlergico.isChecked())
				|| (!rdoSiPreempleado.isChecked() && !rdoNoPreempleado
						.isChecked())
				|| (!rdoE.isChecked() && !rdoV.isChecked())
				|| (!rdoNoDiscapacidad.isChecked() && !rdoSiDiscapacidad
						.isChecked())
				|| cmbCargo.getText().compareTo("") == 0
				|| cmbEmpresa.getText().compareTo("") == 0
				|| cmbArea.getText().compareTo("") == 0
				|| cmbNomina.getText().compareTo("") == 0
				|| txtFichaPaciente.getText().compareTo("") == 0
				|| (!rdoSiLentes.isChecked() && !rdoNoLentes.isChecked())
				|| (rdoMuerte.isChecked() && (dtbFechaMuerte.getText()
						.compareTo("") == 0))
				|| (rdoInactivo.isChecked() && (dtbFechaEgreso.getText()
						.compareTo("") == 0))) {
			Mensaje.mensajeError(Mensaje.camposVacios);
			aplicarColores(txtApellido1Paciente, txtNombre1Paciente,
					txtCedulaPaciente, spnCarga, cmbEstadoCivil,
					cmbGrupoSanguineo, cmbMano, cmbSexo, dspPeso, dspEstatura,
					cmbCiudad, cmbCargo, txtTelefono2, cmbEmpresa, cmbArea,
					cmbNomina, txtFichaPaciente, txtRif);
			return false;
		} else {
			if (rdoSiAlergico.isChecked()
					&& txtAlergia.getText().compareTo("") == 0) {
				Mensaje.mensajeError("Debe Especificar la Informacion de la Alergia");
				return false;
			} else {
				if (rdoSiDiscapacidad.isChecked()
						&& (cmbOrigen.getText().compareTo("") == 0 || cmbTipoDiscapacidad
								.getText().compareTo("") == 0)) {
					Mensaje.mensajeError("Debe Especificar la Informacion de la Discapacidad");
					return false;
				} else {
					if (!Validador.validarTelefono(txtTelefono2.getValue())
							|| (txtTelefono1.getText().compareTo("") != 0 && !Validador
									.validarTelefono(txtTelefono1.getValue()))
							|| (txtTelefono2Emergencia.getText().compareTo("") != 0 && !Validador
									.validarTelefono(txtTelefono2Emergencia
											.getValue()))
							|| (txtTelefono1Emergencia.getText().compareTo("") != 0 && !Validador
									.validarTelefono(txtTelefono1Emergencia
											.getValue()))) {
						Mensaje.mensajeError(Mensaje.telefonoInvalido);
						return false;
					} else {
						if (!validarFicha())
							return false;
						else {
							if (!Validador.validarRif(txtRif.getValue())) {
								Mensaje.mensajeError(Mensaje.rifInvalido);
								return false;
							}
							return true;

						}
					}

				}
			}
		}
	}

	/* Metodo que valida el formmato del telefono ingresado */
	@Listen("onChange = #txtTelefono1")
	public void validarTelefono() throws IOException {
		if (Validador.validarTelefono(txtTelefono1.getValue()) == false) {
			Mensaje.mensajeAlerta(Mensaje.telefonoInvalido);
		}
	}

	/* Metodo que valida el formmato del telefono ingresado */
	@Listen("onChange = #txtTelefono2")
	public void validarTelefono2() throws IOException {
		if (Validador.validarTelefono(txtTelefono2.getValue()) == false) {
			Mensaje.mensajeAlerta(Mensaje.telefonoInvalido);
		}
	}

	/* Metodo que valida el formmato del telefono ingresado */
	@Listen("onChange = #txtTelefono1Emergencia")
	public void validarTelefonoE() throws IOException {
		if (Validador.validarTelefono(txtTelefono1Emergencia.getValue()) == false) {
			Mensaje.mensajeAlerta(Mensaje.telefonoInvalido);
		}
	}

	/* Metodo que valida el formmato del telefono ingresado */
	@Listen("onChange = #txtTelefono2Emergencia")
	public void validarTelefono2E() throws IOException {
		if (Validador.validarTelefono(txtTelefono2Emergencia.getValue()) == false) {
			Mensaje.mensajeAlerta(Mensaje.telefonoInvalido);
		}
	}

	/* Metodo que valida el formmato del correo ingresado */
	@Listen("onChange = #txtCorreo")
	public void validarCorreo() throws IOException {
		if (Validador.validarCorreo(txtCorreo.getValue()) == false) {
			Mensaje.mensajeAlerta(Mensaje.correoInvalido);
		}
	}

	/* Muestra el catalogo de los Pacientes */
	@Listen("onClick = #btnBuscarPaciente, #btnVer")
	public void mostrarCatalogo(Event evento) {
		idBoton = evento.getTarget().getId();
		List<Paciente> pacientes2 = new ArrayList<Paciente>();
		String segundo = "Ficha";
		String titulo = "Catalogo";
		if (idBoton.equals("btnBuscarPaciente")) {
			pacientes2 = servicioPaciente.buscarTodosTrabajadores();
			titulo = "Catalogo de Trabajadores Pacientes";
		} else {
			if (!txtCedulaPaciente.getValue().equals(""))
				pacientes2 = servicioPaciente.buscarParientes(txtCedulaPaciente
						.getValue());
			segundo = "Parentesco";
			titulo = "Catalogo de Familiares que aplican a Servicios Medicos";
		}
		final List<Paciente> pacientes = pacientes2;
		catalogo = new Catalogo<Paciente>(catalogoPaciente, titulo, pacientes,
				false, "Cedula", segundo, "Primer Nombre", "Segundo Nombre",
				"Primer Apellido", "Segundo Apellido", "Estado") {

			@Override
			protected List<Paciente> buscar(String valor, String combo) {

				switch (combo) {
				case "Primer Nombre":
					if (!idBoton.equals("btnBuscarPaciente"))
						return servicioPaciente.filtroNombre1C(valor,
								txtCedulaPaciente.getValue());
					return servicioPaciente.filtroNombre1T(valor);
				case "Segundo Nombre":
					if (!idBoton.equals("btnBuscarPaciente"))
						return servicioPaciente.filtroNombre2C(valor,
								txtCedulaPaciente.getValue());
					return servicioPaciente.filtroNombre2T(valor);
				case "Parentesco":
					return servicioPaciente.filtroParentescoC(valor,
							txtCedulaPaciente.getValue());
				case "Cedula":
					if (!idBoton.equals("btnBuscarPaciente"))
						return servicioPaciente.filtroCedulaC(valor,
								txtCedulaPaciente.getValue());
					return servicioPaciente.filtroCedulaT(valor);
				case "Ficha":
					return servicioPaciente.filtroFichaT(valor);
				case "Primer Apellido":
					if (!idBoton.equals("btnBuscarPaciente"))
						return servicioPaciente.filtroApellido1C(valor,
								txtCedulaPaciente.getValue());
					return servicioPaciente.filtroApellido1T(valor);
				case "Segundo Apellido":
					if (!idBoton.equals("btnBuscarPaciente"))
						return servicioPaciente.filtroApellido2C(valor,
								txtCedulaPaciente.getValue());
					return servicioPaciente.filtroApellido2T(valor);
				default:
					return pacientes;
				}
			}

			@Override
			protected String[] crearRegistros(Paciente objeto) {
				String valor = objeto.getFicha();
				if (!idBoton.equals("btnBuscarPaciente")) {
					valor = objeto.getParentescoFamiliar();
				}
				String activo = "Activo";
				if (!objeto.isEstatus())
					activo = "Inactivo";
				String[] registros = new String[7];
				registros[0] = objeto.getCedula();
				registros[1] = valor;
				registros[2] = objeto.getPrimerNombre();
				registros[3] = objeto.getSegundoNombre();
				registros[4] = objeto.getPrimerApellido();
				registros[5] = objeto.getSegundoApellido();
				registros[6] = activo;
				return registros;
			}

		};
		catalogo.setParent(catalogoPaciente);
		catalogo.doModal();
	}

	/* Valida la Ficha */
	@Listen("onChange = #txtFichaPaciente")
	public boolean validarFicha() {
		List<Paciente> validador = servicioPaciente
				.buscarPorFicha(txtFichaPaciente.getValue());
		if (!validador.isEmpty()) {
			if (!id.equals("")) {
				if (ficha.equals(validador.get(0).getFicha()))
					return true;
			}
			Mensaje.mensajeAlerta(Mensaje.fichaExistente);
			return false;
		}
		return true;
	}

	/* Llena el combo de Empresas cada vez que se abre */
	@Listen("onOpen = #cmbEmpresa")
	public void llenarComboEmpresa() {
		List<Empresa> empresas = servicioEmpresa.buscarTodas();
		cmbEmpresa.setModel(new ListModelList<Empresa>(empresas));
	}

	/* Llena el combo de Empresas cada vez que se abre */
	@Listen("onOpen = #cmbCiudad")
	public void llenarComboCiudad() {
		List<Ciudad> ciudades = servicioCiudad.buscarTodas();
		cmbCiudad.setModel(new ListModelList<Ciudad>(ciudades));
	}

	/* Llena el combo de Empresas cada vez que se abre */
	@Listen("onSelect = #cmbCiudad")
	public void selectComboCiudad() {
		Ciudad ciudad = servicioCiudad.buscar(Long.parseLong(cmbCiudad
				.getSelectedItem().getContext()));
		if (ciudad != null)
			lblEstado.setValue(ciudad.getEstado().getNombre());
	}

	/* Llena el combo de Estado cada vez que se abre */
	@Listen("onOpen = #cmbEstadoCivil")
	public void llenarComboCivil() {
		List<EstadoCivil> ciudades = servicioEstadoCivil.buscarTodas();
		cmbEstadoCivil.setModel(new ListModelList<EstadoCivil>(ciudades));
	}

	/* Llena el combo de Cargos cada vez que se abre */
	@Listen("onOpen = #cmbCargo")
	public void llenarComboCargo() {
		List<Cargo> cargos = servicioCargo.buscarTodos();
		cmbCargo.setModel(new ListModelList<Cargo>(cargos));
	}

	/* Llena el combo de Areas cada vez que se abre */
	@Listen("onOpen = #cmbArea")
	public void llenarComboArea() {
		List<Area> areas = servicioArea.buscarTodos();
		cmbArea.setModel(new ListModelList<Area>(areas));
	}

	@Listen("onClick =#rdoMuerte")
	public void muerte() {
		dtbFechaMuerte.setVisible(true);
		dtbFechaEgreso.setVisible(false);
	}

	@Listen("onClick =#rdoActivo")
	public void muerte1() {
		dtbFechaMuerte.setVisible(false);
		dtbFechaEgreso.setVisible(false);
	}

	@Listen("onClick =#rdoInactivo")
	public void muerte2() {
		dtbFechaMuerte.setVisible(false);
		dtbFechaEgreso.setVisible(true);
	}

	/* Permite la seleccion de un item del catalogo */
	@Listen("onSeleccion = #catalogoPaciente")
	public void seleccinar() {
		Paciente paciente = catalogo.objetoSeleccionadoDelCatalogo();
		if (!idBoton.equals("btnVer")) {
			llenarCampos(paciente);
			ficha = paciente.getFicha();
		}
		catalogo.setParent(null);
	}

	/* Busca si existe un diagnostico con el mismo codigo escrito */
	@Listen("onChange = #txtCedulaPaciente")
	public void buscarPorCedula() {
		Paciente paciente = servicioPaciente.buscarPorCedula(txtCedulaPaciente
				.getValue());
		if (paciente != null) {
			llenarCampos(paciente);
			ficha = paciente.getFicha();
		}
	}

	/* LLena los campos del formulario dado un paciente */
	private void llenarCampos(Paciente paciente) {

		txtCedulaPaciente.setValue(paciente.getCedula());
		txtObservacionEstatus.setValue(paciente.getObservacionEstatus());
		txtNombre1Paciente.setValue(paciente.getPrimerNombre());
		txtApellido1Paciente.setValue(paciente.getPrimerApellido());
		txtNombre2Paciente.setValue(paciente.getSegundoNombre());
		txtApellido2Paciente.setValue(paciente.getSegundoApellido());
		txtCedulaPaciente.setDisabled(true);
		// txtFichaPaciente.setDisabled(true);
		id = paciente.getCedula();
		txtFichaPaciente.setValue(paciente.getFicha());
		txtRif.setValue(paciente.getRif());
		txtAlergia.setValue(paciente.getObservacionAlergias());
		txtLugarNacimiento.setValue(paciente.getLugarNacimiento());
		cmbSexo.setValue(paciente.getSexo());
		if (paciente.getEstadoCivil() != null)
			cmbEstadoCivil.setValue(paciente.getEstadoCivil().getNombre());
		cmbGrupoSanguineo.setValue(paciente.getGrupoSanguineo());
		cmbMano.setValue(paciente.getMano());
		cmbOrigen.setValue(paciente.getOrigenDiscapacidad());
		cmbTipoDiscapacidad.setValue(paciente.getTipoDiscapacidad());
		txtOtras.setValue(paciente.getOrigenDiscapacidad());
		txtDireccion.setValue(paciente.getDireccion());
		txtTelefono1.setValue(paciente.getTelefono1());
		txtTelefono2.setValue(paciente.getTelefono2());
		txtCorreo.setValue(paciente.getEmail());
		txtNombresEmergencia.setValue(paciente.getNombresEmergencia());
		txtApellidosEmergencia.setValue(paciente.getApellidosEmergencia());
		txtTelefono1Emergencia.setValue(paciente.getTelefono1Emergencia());
		txtTelefono2Emergencia.setValue(paciente.getTelefono2Emergencia());
		cmbParentescoEmergencia.setValue(paciente.getParentescoEmergencia());
		lblEdad.setValue(String.valueOf(calcularEdad(paciente
				.getFechaNacimiento())));
		dspEstatura.setValue(paciente.getEstatura());
		dspPeso.setValue(paciente.getPeso());
		cmbCiudad.setValue(paciente.getCiudadVivienda().getNombre());
		spnCarga.setValue(paciente.getCarga());
		txtNroInpsasel.setValue(paciente.getNroInpsasel());
		txtProfesion.setValue(paciente.getProfesion());
		txtRetiroIVSS.setValue(paciente.getRetiroIVSS());
		cmbNivelEducativo.setValue(paciente.getNivelEducativo());
		cmbTurno.setValue(paciente.getTurno());
		dtbFechaEgreso.setValue(paciente.getFechaEgreso());
		dtbFechaIngreso.setValue(paciente.getFechaIngreso());
		dtbInscripcionIVSS.setValue(paciente.getFechaInscripcionIVSS());
		dtbFechaNac.setValue(paciente.getFechaNacimiento());

		if (paciente.getNacionalidad() != null) {
			if (paciente.getNacionalidad().equals("V"))
				rdoV.setChecked(true);
			else
				rdoE.setChecked(true);
		}
		if (paciente.isAlergia())
			rdoSiAlergico.setChecked(true);
		else
			rdoNoAlergico.setChecked(true);

		if (paciente.isMuerte()) {
			rdoMuerte.setChecked(true);
			dtbFechaMuerte.setVisible(true);
			dtbFechaMuerte.setValue(paciente.getFechaMuerte());
		} else {
			if (!paciente.isEstatus()) {
				rdoInactivo.setChecked(true);
				dtbFechaEgreso.setVisible(true);
				dtbFechaEgreso.setValue(paciente.getFechaEgreso());
			} else
				rdoActivo.setChecked(true);
		}

		if (paciente.getCargoReal() != null)
			cmbCargo.setValue(paciente.getCargoReal().getNombre());
		if (paciente.getArea() != null)
			cmbArea.setValue(paciente.getArea().getNombre());
		if (paciente.getEmpresa() != null)
			cmbEmpresa.setValue(paciente.getEmpresa().getNombre());
		if (paciente.getNomina() != null)
			cmbNomina.setValue(paciente.getNomina());

		if (paciente.isDiscapacidad())
			rdoSiDiscapacidad.setChecked(true);
		else
			rdoNoDiscapacidad.setChecked(true);

		if (paciente.isLentes())
			rdoSiLentes.setChecked(true);
		else
			rdoNoLentes.setChecked(true);

		BufferedImage imag;
		if (paciente.getImagen() != null) {
			imagenPaciente.setVisible(true);
			try {
				imag = ImageIO.read(new ByteArrayInputStream(paciente
						.getImagen()));
				imagenPaciente.setContent(imag);
			} catch (IOException e) {
				e.printStackTrace();
			}
		} else
			imagenPaciente.setVisible(false);
		if (paciente.getBrigadista() != null) {
			if (paciente.getBrigadista())
				rdoSiBrigadista.setChecked(true);
			else
				rdoNoBrigadista.setChecked(true);
		} else {
			rdoNoBrigadista.setChecked(false);
			rdoSiBrigadista.setChecked(false);
		}
		if (!paciente.isTrabajador())
			rdoSiPreempleado.setChecked(true);
		else
			rdoNoPreempleado.setChecked(true);
		if (paciente.isDelegadoPrevencion())
			rdoSiComite.setChecked(true);
		else
			rdoNoComite.setChecked(true);
		txtUrb.setValue(paciente.getUrb());
		txtParroquia.setValue(paciente.getParroquia());
		txtMunicipio.setValue(paciente.getMunicipio());
		txtAvCalle.setValue(paciente.getAvCalle());
		txtPuntoReferencia.setValue(paciente.getPuntoReferencia());
		txtSector.setValue(paciente.getSector());
		txtNro.setValue(paciente.getNro());
		txtOtroTransporte.setValue(paciente.getOtroTransporte());
		lblEstado
				.setValue(paciente.getCiudadVivienda().getEstado().getNombre());
		if (paciente.getTransporte() != null) {
			if (!paciente.getTransporte().equals("")) {
				String valores[] = paciente.getTransporte().split(",");
				int j = 0;
				while (j < valores.length) {
					if (valores[j].equals("Transporte Dusa"))
						chkDusa.setChecked(true);
					if (valores[j].equals("Transporte Publico"))
						chkPublico.setChecked(true);
					if (valores[j].equals("Vehiculo Propio"))
						chkPrivado.setChecked(true);
					if (valores[j].equals("Bicicleta"))
						chkBicicleta.setChecked(true);
					if (valores[j].equals("Moto"))
						chkMoto.setChecked(true);
					if (valores[j].equals("Otro"))
						chkOtro.setChecked(true);
					j++;
				}
			}
		}

	}

	/* Permite subir una imagen a la vista */
	@Listen("onUpload = #fudImagenPaciente")
	public void processMedia(UploadEvent event) {
		media = event.getMedia();
		imagenPaciente.setContent((org.zkoss.image.Image) media);

	}

	/* Abre la vista de Empresa */
	@Listen("onClick = #btnAbrirEmpresa")
	public void abrirEmpresa() {
		List<Arbol> arboles = servicioArbol.buscarPorNombreArbol("Empresa");
		final HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("titulo", "Empresa");
		Sessions.getCurrent().setAttribute("itemsCatalogo", map);
		if (!arboles.isEmpty()) {
			Arbol arbolItem = arboles.get(0);
			cArbol.abrirVentanas(arbolItem, tabBox, contenido, tab, tabs);
		}
	}

	/* Abre la vista de Cargo */
	@Listen("onClick = #btnAbrirCargo")
	public void abrirCargo() {
		List<Arbol> arboles = servicioArbol.buscarPorNombreArbol("Cargo");
		final HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("titulo", "Cargo");
		Sessions.getCurrent().setAttribute("itemsCatalogo", map);
		if (!arboles.isEmpty()) {
			Arbol arbolItem = arboles.get(0);
			cArbol.abrirVentanas(arbolItem, tabBox, contenido, tab, tabs);
		}
	}

	/* Abre la vista de Area */
	@Listen("onClick = #btnAbrirArea")
	public void abrirArea() {
		List<Arbol> arboles = servicioArbol.buscarPorNombreArbol("Area");
		final HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("titulo", "Area");
		Sessions.getCurrent().setAttribute("itemsCatalogo", map);
		if (!arboles.isEmpty()) {
			Arbol arbolItem = arboles.get(0);
			cArbol.abrirVentanas(arbolItem, tabBox, contenido, tab, tabs);
		}
	}

	/* Abre la vista de Ciudad */
	@Listen("onClick = #btnAbrirCiudad")
	public void abrirCiudad() {
		List<Arbol> arboles = servicioArbol.buscarPorNombreArbol("Ciudad");
		final HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("titulo", "Ciudad");
		Sessions.getCurrent().setAttribute("itemsCatalogo", map);
		if (!arboles.isEmpty()) {
			Arbol arbolItem = arboles.get(0);
			cArbol.abrirVentanas(arbolItem, tabBox, contenido, tab, tabs);
		}
	}

	/* Abre la vista de Ciudad */
	@Listen("onClick = #btnAbrirEstadoCivil")
	public void abrirCivil() {
		List<Arbol> arboles = servicioArbol
				.buscarPorNombreArbol("Estado Civil");
		final HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("titulo", "Estado Civil");
		Sessions.getCurrent().setAttribute("itemsCatalogo", map);
		if (!arboles.isEmpty()) {
			Arbol arbolItem = arboles.get(0);
			cArbol.abrirVentanas(arbolItem, tabBox, contenido, tab, tabs);
		}
	}

	/* Abre la pestanna de Datos contacto */
	@Listen("onClick = #btnSiguientePestanna")
	public void siguientePestanna() {
		tabDatosContacto.setSelected(true);
	}

	/* Abre la pestanna de datos basicos */
	@Listen("onClick = #btnAnteriorPestanna")
	public void anteriorPestanna() {
		tabDatosBasicos.setSelected(true);
	}

	@Listen("onClick = #btnAnterior2")
	public void anteriorPestanna2() {
		tabDatosContacto.setSelected(true);
	}

	/* Busca si existe un diagnostico con el mismo codigo escrito */
	@Listen("onChange = #dtbFechaNac")
	public void cambioEdad() {
		lblEdad.setValue(String.valueOf(calcularEdad(dtbFechaNac.getValue())));
	}

	@Listen("onOK = #txtCedulaPaciente")
	public void buscarCedula() {
		Paciente paciente = servicioPaciente.buscarPorCedula(txtCedulaPaciente
				.getValue());
		if (paciente != null) {
			llenarCampos(paciente);
			ficha = paciente.getFicha();
		} else {
			limpiarCampos();
			Mensaje.mensajeError(Mensaje.cedulaNoExiste);
		}
	}

	public void limpiarCampos() {
		limpiarColores(txtApellido1Paciente, txtNombre1Paciente,
				txtCedulaPaciente, spnCarga, cmbEstadoCivil, cmbGrupoSanguineo,
				cmbMano, cmbSexo, dspPeso, dspEstatura, cmbCiudad, cmbCargo,
				txtTelefono2, cmbEmpresa, cmbArea, cmbNomina, txtFichaPaciente,
				txtRif);
		txtNro.setValue("");
		txtOtroTransporte.setValue("");
		txtUrb.setValue("");
		txtParroquia.setValue("");
		txtMunicipio.setValue("");
		txtAvCalle.setValue("");
		txtPuntoReferencia.setValue("");
		txtSector.setValue("");
		cedTrabajador = "";
		idBoton = "";
		txtObservacionEstatus.setValue("");
		txtNombre1Paciente.setValue("");
		txtCedulaPaciente.setValue("");
		txtCedulaPaciente.setDisabled(false);
		txtFichaPaciente.setDisabled(false);
		txtApellido1Paciente.setValue("");
		txtNombre2Paciente.setValue("");
		txtApellido2Paciente.setValue("");
		cmbEmpresa.setValue("");
		cmbEmpresa.setPlaceholder("Seleccione una Empresa");
		cmbNomina.setValue("");
		cmbNomina.setPlaceholder("Seleccione un Tipo de Nomina");
		imagenPaciente.setVisible(false);
		id = "";
		txtFichaPaciente.setValue("");
		txtRif.setValue("");
		txtAlergia.setValue("");
		txtLugarNacimiento.setValue("");
		cmbSexo.setValue("");
		cmbSexo.setPlaceholder("Seleccione el Sexo");
		cmbEstadoCivil.setValue("");
		cmbEstadoCivil.setPlaceholder("Seleccione el Estado Civil");
		cmbGrupoSanguineo.setValue("");
		cmbGrupoSanguineo.setPlaceholder("Seleccione el Grupo");
		cmbMano.setValue("");
		cmbMano.setPlaceholder("Seleccione el Valor");
		cmbOrigen.setValue("");
		cmbOrigen.setPlaceholder("Seleccione el Origen");
		cmbTipoDiscapacidad.setValue("");
		cmbTipoDiscapacidad.setPlaceholder("Seleccione un Tipo");
		txtOtras.setValue("");
		cmbCargo.setValue("");
		cmbCargo.setPlaceholder("Seleccione un Cargo");
		cmbArea.setValue("");
		cmbArea.setPlaceholder("Seleccione un Area");
		txtDireccion.setValue("");
		txtTelefono1.setValue("");
		txtTelefono2.setValue("");
		txtCorreo.setValue("");
		txtNombresEmergencia.setValue("");
		txtApellidosEmergencia.setValue("");
		txtTelefono1Emergencia.setValue("");
		txtTelefono2Emergencia.setValue("");
		cmbParentescoEmergencia.setValue("");
		cmbParentescoEmergencia.setPlaceholder("Seleccione el Parentesco");
		lblEdad.setValue("");
		lblEstado.setValue("");
		dspEstatura.setValue((double) 0);
		dspPeso.setValue((double) 0);
		cmbCiudad.setValue("");
		cmbCiudad.setPlaceholder("Seleccione una Ciudad");
		rdoSiAlergico.setValue(null);
		rdoNoAlergico.setValue(null);
		txtNroInpsasel.setValue("");
		txtProfesion.setValue("");
		txtRetiroIVSS.setValue("");
		cmbNivelEducativo.setValue("");
		cmbNivelEducativo.setPlaceholder("Seleccione un Nivel");
		cmbTurno.setValue("");
		cmbTurno.setPlaceholder("Seleccione un Turno");
		spnCarga.setValue(0);
		dtbFechaEgreso.setValue(fecha);
		dtbFechaIngreso.setValue(fecha);
		rdoNoBrigadista.setChecked(false);
		rdoSiBrigadista.setChecked(false);
		rdoSiComite.setChecked(false);
		rdoNoComite.setChecked(false);
		rdoSiPreempleado.setChecked(false);
		rdoNoPreempleado.setChecked(false);
		dtbInscripcionIVSS.setValue(fecha);
		dtbFechaMuerte.setValue(fecha);
		dtbFechaMuerte.setVisible(false);
		rdoActivo.setChecked(true);
		rdoInactivo.setValue(null);
		rdoSiDiscapacidad.setValue(null);
		rdoNoDiscapacidad.setValue(null);
		rdoSiLentes.setValue(null);
		rdoNoLentes.setValue(null);
		rdoE.setDisabled(false);
		rdoV.setDisabled(false);
		tabDatosBasicos.setSelected(true);
		chkBicicleta.setChecked(false);
		chkDusa.setChecked(false);
		chkMoto.setChecked(false);
		chkOtro.setChecked(false);
		chkPrivado.setChecked(false);
		chkPublico.setChecked(false);
	}

	@Listen("onChange = #txtRif")
	public void validarRif() {
		if (!Validador.validarRif(txtRif.getValue())) {
			Mensaje.mensajeAlerta(Mensaje.rifInvalido);
		}
	}

}
