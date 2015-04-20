package controlador.medico;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import modelo.medico.maestro.Cita;
import modelo.medico.maestro.DoctorInterno;
import modelo.medico.maestro.MotivoCita;
import modelo.medico.maestro.Paciente;
import modelo.security.Arbol;
import modelo.security.Usuario;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.JasperRunManager;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.export.ooxml.JRXlsxExporter;
import net.sf.jasperreports.engine.util.JRLoader;

import org.zkoss.zk.ui.Sessions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zk.ui.util.Clients;
import org.zkoss.zul.Button;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Div;
import org.zkoss.zul.Include;
import org.zkoss.zul.Label;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Tab;
import org.zkoss.zul.Tabbox;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Timebox;

import componente.Botonera;
import componente.Buscar;
import componente.Catalogo;
import componente.Mensaje;
import componente.Validador;
import controlador.security.CArbol;
import controlador.utils.CGenerico;

public class CCita extends CGenerico {

	private static final long serialVersionUID = 6527893397646342573L;
	@Wire
	private Div botoneraCita;
	@Wire
	private Div catalogoUsuarios;
	@Wire
	private Div divCatalogoPacientes;
	@Wire
	private Div divCita;
	@Wire
	private Button btnSiguientePestanna;
	@Wire
	private Button btnAnteriorPestanna;
	@Wire
	private Tab tabCita;
	@Wire
	private Tab tabConsultar;
	@Wire
	private Label lblCedulaDoctor;
	@Wire
	private Label lblNombreDoctor;
	@Wire
	private Label lblApellidoDoctor;
	@Wire
	private Button btnBuscarDoctor;
	@Wire
	private Button btnBuscarPaciente;
	@Wire
	private Textbox txtCedulaPaciente;
	@Wire
	private Label lblNombrePaciente;
	@Wire
	private Label lblApellidoPaciente;
	@Wire
	private Textbox txtObservacion;
	@Wire
	private Timebox tmbHoraCita;
	@Wire
	private Combobox cmbMotivo;
	@Wire
	private Datebox dtbFechaCita;
	@Wire
	private Listbox ltbCitas;
	@Wire
	private Button btnAnularCita;
	@Wire
	private Button btnCancelarCita;
	long id = 0;
	String idDoctor = "";
	String idPaciente = "";
	Catalogo<DoctorInterno> catalogo;
	Catalogo<Paciente> catalogoPaciente;

	private CArbol cArbol = new CArbol();
	Buscar<Cita> buscador;
	List<Cita> citas = new ArrayList<Cita>();
	@Wire
	private Textbox txtBuscador;
	@Wire
	private Combobox cmbBuscador;
	private String[] valores = { "Paciente", "Empresa", "Fecha", "Motivo" };

	@Override
	public void inicializar() throws IOException {
		Usuario usuario = usuarioSesion(nombreUsuarioSesion());
		contenido = (Include) divCita.getParent();
		Tabbox tabox = (Tabbox) divCita.getParent().getParent().getParent()
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
		buscar();
		llenarComboMotivo();
		Botonera botonera = new Botonera() {

			@Override
			public void salir() {
				cerrarVentana(divCita, titulo, tabs);

			}

			@Override
			public void limpiar() {
				lblApellidoDoctor.setValue("");
				lblNombreDoctor.setValue("");
				lblCedulaDoctor.setValue("");
				idDoctor = "";
				ltbCitas.getItems().clear();
				tabCita.setSelected(true);
				limpiar2();

			}

			@Override
			public void guardar() {
				if (validar()) {
					String observacion;
					Date fechaCreacion;
					observacion = txtObservacion.getValue();
					fechaCreacion = dtbFechaCita.getValue();
					Timestamp fechaCrea = new Timestamp(fechaCreacion.getTime());
					String idMotivo = cmbMotivo.getSelectedItem().getContext();
					MotivoCita motivo = servicioMotivoCita.buscar(Long
							.parseLong(idMotivo));
					Paciente paciente = servicioPaciente.buscarPorCedula(String
							.valueOf(idPaciente));
					DoctorInterno usuario = servicioDoctor
							.buscarPorCedula(String.valueOf(idDoctor));
					String estado = "Pendiente";
					Date hora = tmbHoraCita.getValue();
					String horaCita = df.format(hora);
					Cita cita = new Cita(id, estado, metodoFecha(), fechaCrea,
							metodoFecha(), metodoHora(), horaCita, usuario,
							observacion, nombreUsuarioSesion(), motivo,
							paciente);

					servicioCita.guardar(cita);
					llenarListaCitas(usuario);
					limpiar2();
					Mensaje.mensajeInformacion(Mensaje.guardado);
					llenarListaCitas(usuario);

				}

			}

			@Override
			public void eliminar() {
			}
		};
		botonera.getChildren().get(1).setVisible(false);
		botoneraCita.appendChild(botonera);

	}

	/*
	 * Borra los datos referentes a la cita, brindando la opcion de seguir
	 * agregando citas al doctor actual
	 */
	private void limpiar2() {
		Date dt = new Date();
		txtCedulaPaciente.setValue("");
		lblNombrePaciente.setValue("");
		lblApellidoPaciente.setValue("");
		txtObservacion.setValue("");
		tmbHoraCita.setValue(dt);
		dtbFechaCita.setValue(fecha);
		cmbMotivo.setValue("");
		cmbMotivo.setPlaceholder("Seleccione un Motivo");
		id = 0;
		idPaciente = "";
		limpiarColores(txtCedulaPaciente, cmbMotivo);

	}

	/* Muestra un catalogo de Usuarios */
	@Listen("onClick = #btnBuscarDoctor")
	public void mostrarCatalogo() throws IOException {
		final List<DoctorInterno> usuarios = servicioDoctor.buscarTodos();
		catalogo = new Catalogo<DoctorInterno>(catalogoUsuarios,
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
		catalogo.setParent(catalogoUsuarios);
		catalogo.doModal();
	}

	/* Permite la seleccion de un item del catalogo de doctores */
	@Listen("onSeleccion = #catalogoUsuarios")
	public void seleccionarDoctor() {
		DoctorInterno usuario = catalogo.objetoSeleccionadoDelCatalogo();
		lblApellidoDoctor.setValue(usuario.getPrimerApellido() + " "
				+ usuario.getSegundoApellido());
		lblNombreDoctor.setValue(usuario.getPrimerNombre() + " "
				+ usuario.getSegundoNombre());
		lblCedulaDoctor.setValue(usuario.getCedula());
		idDoctor = usuario.getCedula();
		llenarListaCitas(usuario);
		limpiar2();
		catalogo.setParent(null);
	}

	/* Llena la lista de citas segun un usuario determinado */
	public void llenarListaCitas(DoctorInterno usuario) {
		List<Cita> citasDoctor = servicioCita.buscarPorUsuarioYEstadoYFecha(
				usuario, "Pendiente", dtbFechaCita.getValue());
		citas = citasDoctor;
		for (int i = 0; i < citasDoctor.size(); i++) {

			String nombre = citasDoctor.get(i).getPaciente().getPrimerNombre();
			String apellido = citasDoctor.get(i).getPaciente()
					.getPrimerApellido();
			Paciente paciente = citasDoctor.get(i).getPaciente();
			paciente.setHoraAuditoria(nombre + " " + apellido);
		}
		ltbCitas.setModel(new ListModelList<Cita>(citasDoctor));
		ltbCitas.setMultiple(false);
		ltbCitas.setCheckmark(false);
		ltbCitas.setMultiple(true);
		ltbCitas.setCheckmark(true);
	}

	/* Muestra un catalogo de Pacientes */
	@Listen("onClick = #btnBuscarPaciente")
	public void mostrarCatalogoPaciente() throws IOException {
		List<Paciente> pacientesBuscar = new ArrayList<Paciente>();
		pacientesBuscar = servicioPaciente.buscarTodosActivos();
		final List<Paciente> pacientes = pacientesBuscar;
		catalogoPaciente = new Catalogo<Paciente>(divCatalogoPacientes,
				"Catalogo de Pacientes", pacientes, false, "Cedula", "Ficha",
				"Primer Nombre", "Segundo Nombre", "Primer Apellido",
				"Segundo Apellido") {

			@Override
			protected List<Paciente> buscar(String valor, String combo) {
				switch (combo) {
				case "Ficha":
					return servicioPaciente.filtroFichaActivos(valor);
				case "Primer Nombre":
					return servicioPaciente.filtroNombre1Activos(valor);
				case "Segundo Nombre":
					return servicioPaciente.filtroNombre2Activos(valor);
				case "Cedula":
					return servicioPaciente.filtroCedulaActivos(valor);
				case "Primer Apellido":
					return servicioPaciente.filtroApellido1Activos(valor);
				case "Segundo Apellido":
					return servicioPaciente.filtroApellido2Activos(valor);
				default:
					return pacientes;
				}

			}

			@Override
			protected String[] crearRegistros(Paciente objeto) {
				String[] registros = new String[6];
				registros[0] = objeto.getCedula();
				registros[1] = objeto.getFicha();
				registros[2] = objeto.getPrimerNombre();
				registros[3] = objeto.getSegundoNombre();
				registros[4] = objeto.getPrimerApellido();
				registros[5] = objeto.getSegundoApellido();
				return registros;
			}

		};
		catalogoPaciente.setParent(divCatalogoPacientes);
		catalogoPaciente.doModal();
	}

	/* Permite la seleccion de un item del catalogo de pacientes */
	@Listen("onSeleccion = #divCatalogoPacientes")
	public void seleccionarPaciente() {
		Paciente paciente = catalogoPaciente.objetoSeleccionadoDelCatalogo();
		llenarCamposPaciente(paciente);
		catalogoPaciente.setParent(null);
	}

	public void llenarCamposPaciente(Paciente paciente) {
		if (!paciente.isTrabajador()
				&& paciente.getParentescoFamiliar().equals("Hijo(a)")) {
			Paciente representante = servicioPaciente.buscarPorCedula(paciente
					.getCedulaFamiliar());
			if (representante.isMuerte()) {
				if (calcularEdad(representante.getFechaMuerte()) >= 1)
					Mensaje.mensajeAlerta(Mensaje.pacienteFallecido);
			} else {
				if (calcularEdad(paciente.getFechaNacimiento()) >= 18)
					Mensaje.mensajeAlerta(Mensaje.pacienteMayor);
			}
		}
		txtCedulaPaciente.setValue(paciente.getCedula());
		lblNombrePaciente.setValue(paciente.getPrimerNombre() + " "
				+ paciente.getSegundoNombre());
		lblApellidoPaciente.setValue(paciente.getPrimerApellido() + " "
				+ paciente.getSegundoApellido());
		idPaciente = paciente.getCedula();
	}

	/* Llena el combo de Motivos cada vez que se abre */
	@Listen("onOpen = #cmbMotivo")
	public void llenarComboMotivo() {
		List<MotivoCita> motivoCitas = servicioMotivoCita.buscarTodos();
		cmbMotivo.setModel(new ListModelList<MotivoCita>(motivoCitas));
	}

	/* Permite validar que todos los campos esten completos */
	public boolean validar() {
		if (cmbMotivo.getText().compareTo("") == 0
				|| tmbHoraCita.getText().compareTo("") == 0
				|| dtbFechaCita.getText().compareTo("") == 0
				|| idDoctor.equals("") || idPaciente.equals("")) {
			aplicarColores(txtCedulaPaciente, cmbMotivo);
			Mensaje.mensajeError(Mensaje.camposVacios);
			return false;
		} else
			return true;
	}

	/* Abre la pestanna de consultar citas */
	@Listen("onClick = #btnSiguientePestanna")
	public void siguientePestanna() {
		tabConsultar.setSelected(true);
	}

	/* Abre la pestanna de crear cita */
	@Listen("onClick = #btnAnteriorPestanna")
	public void anteriorPestanna() {
		tabCita.setSelected(true);
	}

	/*
	 * Se ejecuta al darle click al boton de cancelar en la pestanna de
	 * consultar citas, cancela la cita seleccionada
	 */
	@Listen("onClick = #btnCancelarCita")
	public void cancelarCita() {
		Boolean hayCitas = false;
		if (ltbCitas.getItemCount() != 0) {
			final List<Listitem> list1 = ltbCitas.getItems();
			for (int i = 0; i < list1.size(); i++) {
				if (list1.get(i).isSelected())
					hayCitas = true;
			}
			if (hayCitas) {
				hayCitas = false;
				Messagebox.show("¿Esta Seguro de Cancelar la(s) Citas(s)?",
						"Alerta", Messagebox.OK | Messagebox.CANCEL,
						Messagebox.QUESTION, new EventListener<Event>() {
							public void onEvent(Event evt) {
								switch (((Integer) evt.getData()).intValue()) {
								case Messagebox.OK:
									respuestaOk();
									break;
								case Messagebox.CANCEL:
									respuestaCancelar();
									break;
								}
							}

							private void respuestaCancelar() {
							}

							private void respuestaOk() {
								for (int i = 0; i < list1.size(); i++) {
									if (list1.get(i).isSelected()) {
										Cita cita = list1.get(i).getValue();
										cita.setEstado("Cancelada");
										servicioCita.guardar(cita);
									}
								}
								DoctorInterno usuario = servicioDoctor
										.buscarPorCedula(String
												.valueOf(idDoctor));
								llenarListaCitas(usuario);
								Mensaje.mensajeInformacion(Mensaje.citasCanceladas);
							}
						});
			} else
				Mensaje.mensajeAlerta(Mensaje.seleccioneCitaCancelar);
		} else
			Mensaje.mensajeAlerta(Mensaje.noCitasCancelacion);
	}

	@Listen("onClick = #btnFinalizarCita")
	public void finalizarCita() {
		Boolean hayCitas = false;
		if (ltbCitas.getItemCount() != 0) {
			final List<Listitem> list1 = ltbCitas.getItems();
			for (int i = 0; i < list1.size(); i++) {
				if (list1.get(i).isSelected())
					hayCitas = true;
			}
			if (hayCitas) {
				hayCitas = false;
				Messagebox.show("¿Esta Seguro de Finalizar la(s) Citas(s)?",
						"Alerta", Messagebox.OK | Messagebox.CANCEL,
						Messagebox.QUESTION, new EventListener<Event>() {
							public void onEvent(Event evt) {
								switch (((Integer) evt.getData()).intValue()) {
								case Messagebox.OK:
									respuestaOk();
									break;
								case Messagebox.CANCEL:
									respuestaCancelar();
									break;
								}
							}

							private void respuestaCancelar() {
							}

							private void respuestaOk() {
								for (int i = 0; i < list1.size(); i++) {
									if (list1.get(i).isSelected()) {
										Cita cita = list1.get(i).getValue();
										cita.setEstado("Finalizada");
										servicioCita.guardar(cita);
									}
								}
								DoctorInterno usuario = servicioDoctor
										.buscarPorCedula(String
												.valueOf(idDoctor));
								llenarListaCitas(usuario);
								Mensaje.mensajeInformacion("Se ha(n) Finalizado la(s) Cita(s)");
							}
						});
			} else
				Mensaje.mensajeAlerta("Seleccione al menos una Cita para Finalizar");
		} else
			Mensaje.mensajeAlerta("Actualmente No hay Citas para su Finalizacion");
	}

	/*
	 * Se ejecuta al darle click al boton de cancelar en la pestanna de
	 * consultar citas, anula la cita seleccionada
	 */
	@Listen("onClick = #btnAnularCita")
	public void anularCita() {
		Boolean hayCitas = false;
		if (ltbCitas.getItemCount() != 0) {
			final List<Listitem> list1 = ltbCitas.getItems();
			for (int i = 0; i < list1.size(); i++) {
				if (list1.get(i).isSelected())
					hayCitas = true;
			}
			if (hayCitas) {
				hayCitas = false;
				Messagebox.show("¿Esta Seguro de Anular la(s) Citas(s)?",
						"Alerta", Messagebox.OK | Messagebox.CANCEL,
						Messagebox.QUESTION, new EventListener<Event>() {
							public void onEvent(Event evt) {
								switch (((Integer) evt.getData()).intValue()) {
								case Messagebox.OK:
									respuestaOk();
									break;
								case Messagebox.CANCEL:
									respuestaCancelar();
									break;
								}
							}

							private void respuestaCancelar() {
							}

							private void respuestaOk() {
								for (int i = 0; i < list1.size(); i++) {
									if (list1.get(i).isSelected()) {
										Cita cita = list1.get(i).getValue();
										cita.setEstado("Anulada");
										servicioCita.guardar(cita);
									}
								}
								DoctorInterno usuario = servicioDoctor
										.buscarPorCedula(String
												.valueOf(idDoctor));
								llenarListaCitas(usuario);
								Mensaje.mensajeInformacion(Mensaje.citasAnuladas);
							}
						});
			} else
				Mensaje.mensajeAlerta(Mensaje.seleccioneCitaAnular);
		} else
			Mensaje.mensajeAlerta(Mensaje.noCitasAnulacion);
	}

	public void buscar() {
		cmbBuscador.setModel(new ListModelList<String>(valores));
		buscador = new Buscar<Cita>(ltbCitas, txtBuscador) {

			@Override
			protected List<Cita> buscar(String valor) {
				switch (cmbBuscador.getValue()) {
				case "Paciente":
					return recorrer(servicioCita
							.filtroPaciente(valor, idDoctor));
				case "Empresa":
					return recorrer(servicioCita.filtroEmpresa(valor, idDoctor));
				case "Fecha":
					if (Validador.validarFormato(valor)) {
						Calendar calendario = Calendar.getInstance();
						Date fecha1 = fecha;
						try {
							fecha1 = formatoFecha.parse(valor);
						} catch (ParseException e) {
							e.printStackTrace();
						}
						calendario.setTime(fecha1);
						calendario.set(Calendar.HOUR, 0);
						calendario.set(Calendar.HOUR_OF_DAY, 0);
						calendario.set(Calendar.SECOND, 0);
						calendario.set(Calendar.MILLISECOND, 0);
						calendario.set(Calendar.MINUTE, 0);
						fecha1 = calendario.getTime();
						Timestamp fecha2 = new Timestamp(fecha1.getTime());
						return recorrer(servicioCita.filtroFecha(fecha2,
								fecha2, idDoctor));
					}
					return citas;
				case "Motivo":
					return recorrer(servicioCita.filtroMotivo(valor, idDoctor));
				default:
					return citas;
				}

			}
		};
	}

	public List<Cita> recorrer(List<Cita> lis) {
		for (int i = 0; i < lis.size(); i++) {
			String nombre = lis.get(i).getPaciente().getPrimerNombre();
			String apellido = lis.get(i).getPaciente().getPrimerApellido();
			Paciente paciente = lis.get(i).getPaciente();
			paciente.setHoraAuditoria(nombre + " " + apellido);
		}
		return lis;
	}

	/* Abre la vista de Motivo */
	@Listen("onClick = #btnAbrirMotivo")
	public void abrirMotivo() {
		List<Arbol> arboles = servicioArbol.buscarPorNombreArbol("Motivo");
		final HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("titulo", "Motivo");
		Sessions.getCurrent().setAttribute("itemsCatalogo", map);
		if (!arboles.isEmpty()) {
			Arbol arbolItem = arboles.get(0);
			cArbol.abrirVentanas(arbolItem, tabBox, contenido, tab, tabs);
		}
	}

	@Listen("onChange = #dtbFechaCita")
	public void validarFecha() {
		if (!idDoctor.equals("")) {
			DoctorInterno usuario = servicioDoctor.buscarPorCedula(String
					.valueOf(idDoctor));
			llenarListaCitas(usuario);
		}
	}

	@Listen("onOK = #txtCedulaPaciente")
	public void buscarCedula() {
		Paciente paciente = new Paciente();
		paciente = servicioPaciente.buscarPorCedulaActivo(txtCedulaPaciente
				.getValue());
		if (paciente != null) {
			llenarCamposPaciente(paciente);
		} else {
			limpiar2();
			Mensaje.mensajeError(Mensaje.pacienteNoExiste);
		}
	}

	@Listen("onClick = #btnPdf, #btnExcel")
	public void exportar(Event e) {
		String fechaControl = formatoReporte.format(dtbFechaCita.getValue());
		String idBoton = e.getTarget().getId();
		if (idBoton.equals("btnExcel"))
			idBoton = "EXCEL";
		if (!citas.isEmpty()) {
			Clients.evalJavaScript("window.open('"
					+ damePath()
					+ "Reportero?valor6="
					+ fechaControl
					+ "&valor="
					+ 47
					+ "&valor7="
					+ idDoctor
					+ "&valor20="
					+ idBoton
					+ "','','top=100,left=200,height=600,width=800,scrollbars=1,resizable=1')");

		} else
			Mensaje.mensajeAlerta(Mensaje.noHayRegistros);

	}

	public byte[] jasperCitas(String par6, String idDoctor, String tipo) {

		byte[] fichero = null;
		Date fecha1 = null;
		try {
			fecha1 = formatoReporte.parse(par6);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		DoctorInterno doctorHouse = getServicioDoctor().buscarPorCedula(
				idDoctor);
		List<Cita> especialistas = getServicioCita()
				.buscarPorUsuarioYEstadoYFecha(doctorHouse, "%", fecha1);

		Map<String, Object> p = new HashMap<String, Object>();
		p.put("fecha", new Timestamp(fecha1.getTime()));

		JasperReport reporte = null;
		try {
			reporte = (JasperReport) JRLoader
					.loadObject(getClass()
							.getResource(
									"/reporte/medico/general/RSolicitudesConsulta.jasper"));
		} catch (JRException e) {
			Mensaje.mensajeError("Recurso no Encontrado");
		}
		if (tipo.equals("EXCEL")) {

			JasperPrint jasperPrint = null;
			try {
				jasperPrint = JasperFillManager.fillReport(reporte, p,
						new JRBeanCollectionDataSource(especialistas));
			} catch (JRException e) {
				e.printStackTrace();
			}
			ByteArrayOutputStream xlsReport = new ByteArrayOutputStream();
			JRXlsxExporter exporter = new JRXlsxExporter();
			exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
			exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, xlsReport);
			try {
				exporter.exportReport();
			} catch (JRException e) {
				e.printStackTrace();
			}
			return xlsReport.toByteArray();
		} else {
			try {
				fichero = JasperRunManager.runReportToPdf(reporte, p,
						new JRBeanCollectionDataSource(especialistas));
			} catch (JRException e) {
				Mensaje.mensajeError("Error en Reporte");
			}
			return fichero;
		}
	}
}
