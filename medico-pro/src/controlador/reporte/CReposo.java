package controlador.reporte;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import modelo.medico.consulta.Consulta;
import modelo.medico.consulta.ConsultaDiagnostico;
import modelo.medico.maestro.Diagnostico;
import modelo.medico.maestro.DoctorInterno;
import modelo.medico.maestro.Paciente;
import modelo.organizacion.Area;
import modelo.organizacion.Cargo;
import modelo.organizacion.Empresa;
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

import org.json.JSONException;
import org.json.JSONObject;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zk.ui.util.Clients;
import org.zkoss.zul.Button;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Div;
import org.zkoss.zul.Hbox;
import org.zkoss.zul.Label;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Row;
import org.zkoss.zul.Tab;
import org.zkoss.zul.Textbox;

import componente.Botonera;
import componente.Buscar;
import componente.Catalogo;
import componente.Mensaje;
import controlador.utils.CGenerico;

public class CReposo extends CGenerico {

	@Wire
	private Textbox txtBuscadorDiagnostico;
	@Wire
	private Div catalogoUsuarios;
	@Wire
	private Combobox cmbArea;
	@Wire
	private Datebox dtbDesde;
	@Wire
	private Datebox dtbHasta;
	@Wire
	private Div divReposo;
	@Wire
	private Div botoneraReposo;
	@Wire
	private Row rowArea;
	@Wire
	private Row rowPaciente;
	@Wire
	private Row rowDiagnostico;
	@Wire
	private Row rowDoctor;
	@Wire
	private Button btnBuscarPaciente;
	@Wire
	private Button btnBuscarDoctor;
	@Wire
	private Combobox cmbDiagnostico;
	@Wire
	private Label lblPaciente;
	@Wire
	private Label lblNombreDoctor;
	@Wire
	private Div divCatalogoPaciente;
	@Wire
	private Combobox cmbTipo;
	@Wire
	private Hbox box;
	@Wire
	private Hbox box2;
	@Wire
	private Listbox ltbDiagnosticos;
	@Wire
	private Listbox ltbDiagnosticosAgregados;
	@Wire
	private Row rowCargo;
	@Wire
	private Row rowEmpresa;
	@Wire
	private Row rowNomina;
	@Wire
	private Combobox cmbCargo;
	@Wire
	private Combobox cmbEmpresa;
	@Wire
	private Combobox cmbNomina;

	List<Diagnostico> diagnosticosDisponibles = new ArrayList<Diagnostico>();
	List<Diagnostico> diagnosticosAgregados = new ArrayList<Diagnostico>();
	Buscar<Diagnostico> buscarDiagnostico;
	private String tipo = "";
	private String titulo = "";
	private String idPaciente = "";
	private String idDoctor = "";
	Catalogo<Paciente> catalogo;
	Catalogo<DoctorInterno> catalogoDoctor;

	public String getTitulo() {
		return titulo;
	}

	@Override
	public void inicializar() throws IOException {
		cargarCombos();
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

		switch (titulo) {
		case "Reposos Por Nomina":
			rowArea.setVisible(false);
			rowDiagnostico.setVisible(false);
			rowDoctor.setVisible(false);
			rowPaciente.setVisible(false);
			rowEmpresa.setVisible(false);
			rowNomina.setVisible(true);
			rowCargo.setVisible(false);
			tipo = "nomina";
			break;
		case "Reposos Por Empresa":
			rowArea.setVisible(false);
			rowDiagnostico.setVisible(false);
			rowDoctor.setVisible(false);
			rowPaciente.setVisible(false);
			rowEmpresa.setVisible(true);
			rowNomina.setVisible(false);
			rowCargo.setVisible(false);
			tipo = "empresa";
			break;
		case "Reposos Por Cargo":
			rowArea.setVisible(false);
			rowDiagnostico.setVisible(false);
			rowDoctor.setVisible(false);
			rowPaciente.setVisible(false);
			rowEmpresa.setVisible(false);
			rowNomina.setVisible(false);
			rowCargo.setVisible(true);
			tipo = "cargo";
			break;
		case "Reposos Por Area":
			rowArea.setVisible(true);
			rowDiagnostico.setVisible(false);
			rowDoctor.setVisible(false);
			rowPaciente.setVisible(false);
			rowEmpresa.setVisible(false);
			rowNomina.setVisible(false);
			rowCargo.setVisible(false);
			tipo = "area";
			break;
		case "Reposos Por Diagnostico":
			buscadorDiagnostico();
			rowArea.setVisible(false);
			rowDiagnostico.setVisible(true);
			rowDoctor.setVisible(false);
			rowPaciente.setVisible(false);
			rowEmpresa.setVisible(false);
			rowNomina.setVisible(false);
			rowCargo.setVisible(false);
			box.setVisible(true);
			box2.setVisible(true);
			cargarLista();
			tipo = "diagnostico";
			break;
		case "Reposos Por Doctor":
			rowArea.setVisible(false);
			rowDiagnostico.setVisible(false);
			rowDoctor.setVisible(true);
			rowPaciente.setVisible(false);
			rowEmpresa.setVisible(false);
			rowNomina.setVisible(false);
			rowCargo.setVisible(false);
			tipo = "doctor";
			break;
		case "Reposos Por Paciente":
			rowArea.setVisible(false);
			rowDiagnostico.setVisible(false);
			rowDoctor.setVisible(false);
			rowPaciente.setVisible(true);
			rowEmpresa.setVisible(false);
			rowNomina.setVisible(false);
			rowCargo.setVisible(false);
			tipo = "paciente";
			break;
		}

		Botonera botonera = new Botonera() {

			@Override
			public void salir() {
				// Pasar el nombre del arbol por el CARBOL
				cerrarVentana(divReposo, titulo, tabs);
			}

			@Override
			public void limpiar() {
				dtbDesde.setValue(fecha);
				dtbHasta.setValue(fecha);

				switch (tipo) {
				case "area":
					cmbArea.setValue("TODAS");
					break;
				case "diagnostico":
					cmbDiagnostico.setValue("TODOS");
					cargarLista();
					break;
				case "doctor":
					idDoctor = "";
					lblNombreDoctor.setValue("");
					break;
				case "paciente":
					idPaciente = "";
					lblPaciente.setValue("");
					break;
				case "empresa":
					cmbEmpresa.setValue("TODAS");
					break;
				case "nomina":
					cmbNomina.setValue("TODAS");
					break;
				case "cargo":
					cmbCargo.setValue("TODOS");
					break;
				}
			}

			@Override
			public void guardar() {
				if (validar()) {
					switch (tipo) {
					case "area":
						if (validarArea())
							reporteArea();
						break;
					case "diagnostico":
						if (validarDiagnostico())
							reporteDiagnostico();
						break;
					case "doctor":
						if (validarDoctor())
							reporteDoctor();
						break;
					case "paciente":
						if (!idPaciente.equals(""))
							reportePaciente();
						break;
					case "cargo":
						if (validarCargo())
							reporteCargo();
						break;
					case "empresa":
						if (validarEmpresa())
							reporteEmpresa();
						break;
					case "nomina":
						if (validarNomina())
							reporteNomina();
						break;
					}
				}
			}

			@Override
			public void eliminar() {
				// TODO Auto-generated method stub

			}
		};
		Button guardar = (Button) botonera.getChildren().get(0);
		guardar.setLabel("Reporte");
		guardar.setTooltiptext("Generar Reporte");
		guardar.setImage("/public/imagenes/botones/reporte.png");
		botonera.getChildren().get(1).setVisible(false);
		botoneraReposo.appendChild(botonera);
	}

	public void reporteNomina() {
		Date desde = dtbDesde.getValue();
		Date hasta = dtbHasta.getValue();
		DateFormat fecha = new SimpleDateFormat("dd-MM-yyyy");
		String fecha1 = fecha.format(desde);
		String fecha2 = fecha.format(hasta);
		String nomina = "";
		String tipoReporte = cmbTipo.getValue();
		if (cmbNomina.getValue().equals("TODAS"))
			nomina = "";
		else
			nomina = cmbNomina.getValue();

		if ((nomina.equals("") && servicioConsulta
				.buscarEntreFechasReposoyTrabajadores(desde, hasta).isEmpty())
				|| (!nomina.equals("") && servicioConsulta
						.buscarEntreFechasReposoNominayTrabajadores(desde,
								hasta, nomina).isEmpty()))
			Mensaje.mensajeAlerta(Mensaje.noHayRegistros);
		else {

			Clients.evalJavaScript("window.open('"
					+ damePath()
					+ "Reportero?valor=39&valor6="
					+ fecha1
					+ "&valor7="
					+ fecha2
					+ "&valor8="
					+ nomina
					+ "&valor20="
					+ tipoReporte
					+ "','','top=100,left=200,height=600,width=800,scrollbars=1,resizable=1')");
		}

	}

	public byte[] reporteReposoPorNomina(String part1, String part2,
			String nomina, String tipoReporte) throws JRException {
		byte[] fichero = null;
		SimpleDateFormat formato = new SimpleDateFormat("dd-MM-yyyy");
		Date fecha1 = null;
		try {
			fecha1 = formato.parse(part1);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		Date fecha2 = null;
		try {
			fecha2 = formato.parse(part2);
		} catch (ParseException e) {
			e.printStackTrace();
		}

		fecha2 = agregarDia(fecha2);
		List<Consulta> consuta = new ArrayList<Consulta>();

		if (nomina.equals(""))
			consuta = getServicioConsulta()
					.buscarEntreFechasReposoyTrabajadores(fecha1, fecha2);
		else
			consuta = getServicioConsulta()
					.buscarEntreFechasReposoNominayTrabajadores(fecha1, fecha2,
							nomina);

		Map<String, Object> p = new HashMap<String, Object>();
		p.put("desde", part1);
		p.put("hasta", part2);

		for (int i = 0; i < consuta.size(); i++) {
			Consulta cons = consuta.get(i);
			List<ConsultaDiagnostico> dig = getServicioConsultaDiagnostico()
					.buscarPorConsulta(cons);
			Calendar c = Calendar.getInstance();
			if (cons.getFechaReposo() != null)
				c.setTime(cons.getFechaReposo());
			else {
				cons.setFechaReposo(cons.getFechaConsulta());
				c.setTime(cons.getFechaConsulta());
			}

			if (cons.getTipoReposo() != null) {
				if (cons.getTipoReposo().equals("Dias")) {
					c.add(Calendar.DAY_OF_YEAR, cons.getDiasReposo());
					cons.setUsuarioAuditoria(cons.getDiasReposo() + " Dias");
				} else
					cons.setUsuarioAuditoria(cons.getDiasReposo() + " Horas");
			} else {
				c.add(Calendar.DAY_OF_YEAR, cons.getDiasReposo());
				cons.setUsuarioAuditoria(cons.getDiasReposo() + " Dias");
			}
			Date fechaHasta = c.getTime();
			Timestamp fechaHasta2 = new Timestamp(fechaHasta.getTime());
			cons.setFechaAuditoria(fechaHasta2);
			if (!dig.isEmpty()) {
				if (dig.get(0) != null) {

					cons.setEnfermedadActual(dig.get(0).getDiagnostico()
							.getNombre());
					cons.setMotivoConsulta(dig.get(0).getTipo());

				}
			} else {
				cons.setEnfermedadActual("");
				cons.setMotivoConsulta("");
			}
		}
		p.put("data", new JRBeanCollectionDataSource(consuta));

		JasperReport reporte = (JasperReport) JRLoader.loadObject(getClass()
				.getResource("/reporte/medico/reposo/RRepososPorNomina.jasper"));
		if (tipoReporte.equals("EXCEL")) {

			JasperPrint jasperPrint = null;
			try {
				jasperPrint = JasperFillManager.fillReport(reporte, p,
						new JRBeanCollectionDataSource(consuta));
			} catch (JRException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			ByteArrayOutputStream xlsReport = new ByteArrayOutputStream();
			JRXlsxExporter exporter = new JRXlsxExporter();
			exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
			exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, xlsReport);
			try {
				exporter.exportReport();
			} catch (JRException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return xlsReport.toByteArray();
		} else {

			fichero = JasperRunManager.runReportToPdf(reporte, p,
					new JRBeanCollectionDataSource(consuta));
			return fichero;
		}
	}

	private void buscadorDiagnostico() {
		buscarDiagnostico = new Buscar<Diagnostico>(ltbDiagnosticos,
				txtBuscadorDiagnostico) {
			@Override
			protected List<Diagnostico> buscar(String valor) {
				List<Diagnostico> diagnosticosFiltradas = new ArrayList<Diagnostico>();
				List<Diagnostico> diagnosticos = servicioDiagnostico
						.filtroNombre(valor);
				Diagnostico diag = new Diagnostico();
				diag.setNombre("TODOS");
				diag.setIdDiagnostico(0);
				diagnosticosFiltradas.add(diag);
				for (int i = 0; i < diagnosticosDisponibles.size(); i++) {
					Diagnostico diagnostico = diagnosticosDisponibles.get(i);
					for (int j = 0; j < diagnosticos.size(); j++) {
						if (diagnostico.getIdDiagnostico() == diagnosticos.get(
								j).getIdDiagnostico())
							diagnosticosFiltradas.add(diagnostico);
					}
				}
				return diagnosticosFiltradas;
			}
		};
	}

	private void cargarCombos() {
		String todos = "TODAS";
		Area area = new Area();
		area.setNombre(todos);
		area.setIdArea(0);
		List<Area> areas = new ArrayList<Area>();
		areas.add(area);
		areas.addAll(servicioArea.buscarTodos());
		cmbArea.setModel(new ListModelList<Area>(areas));
		Empresa empresa = new Empresa();
		empresa.setNombre(todos);
		empresa.setIdEmpresa(0);
		List<Empresa> empresas = new ArrayList<Empresa>();
		empresas.add(empresa);
		empresas.addAll(servicioEmpresa.buscarTodas());
		cmbEmpresa.setModel(new ListModelList<Empresa>(empresas));
		Cargo cargo = new Cargo();
		cargo.setNombre("TODOS");
		cargo.setIdCargo(0);
		List<Cargo> cargos = new ArrayList<Cargo>();
		cargos.add(cargo);
		cargos.addAll(servicioCargo.buscarTodos());
		cmbCargo.setModel(new ListModelList<Cargo>(cargos));
	}

	public boolean validar() {
		if (dtbDesde.getText().compareTo("") == 0
				|| dtbHasta.getText().compareTo("") == 0) {
			Mensaje.mensajeError(Mensaje.camposVacios);
			return false;
		} else {
			if (dtbDesde.getValue().after(dtbHasta.getValue())) {
				Mensaje.mensajeError(Mensaje.fechaPosterior);
				return false;

			} else
				return true;
		}

	}

	public boolean validarArea() {
		if (cmbArea.getText().compareTo("") == 0) {
			Mensaje.mensajeError(Mensaje.camposVacios);
			return false;
		}
		return true;
	}

	public boolean validarCargo() {
		if (cmbCargo.getText().compareTo("") == 0) {
			Mensaje.mensajeError(Mensaje.camposVacios);
			return false;
		}
		return true;
	}

	public boolean validarEmpresa() {
		if (cmbEmpresa.getText().compareTo("") == 0) {
			Mensaje.mensajeError(Mensaje.camposVacios);
			return false;
		}
		return true;
	}

	public boolean validarNomina() {
		if (cmbNomina.getText().compareTo("") == 0) {
			Mensaje.mensajeError(Mensaje.camposVacios);
			return false;
		}
		return true;
	}

	public boolean validarDiagnostico() {
		if (cmbDiagnostico.getText().compareTo("") == 0) {
			Mensaje.mensajeError(Mensaje.camposVacios);
			return false;
		} else {
			if (ltbDiagnosticosAgregados.getItemCount() == 0) {
				Mensaje.mensajeError("Debe seleccionar al menos un diagnostico");
				return false;
			} else
				return true;
		}
	}

	public boolean validarDoctor() {
		if (idDoctor.equals("")) {
			Mensaje.mensajeError(Mensaje.camposVacios);
			return false;
		}
		return true;
	}

	public void reporteArea() {
		Date desde = dtbDesde.getValue();
		Date hasta = dtbHasta.getValue();
		DateFormat fecha = new SimpleDateFormat("dd-MM-yyyy");
		String fecha1 = fecha.format(desde);
		String fecha2 = fecha.format(hasta);
		String area = "";
		String tipoReporte = cmbTipo.getValue();
		Area area2 = new Area();
		if (cmbArea.getValue().equals("TODAS"))
			area = "";
		else {
			area = cmbArea.getSelectedItem().getContext();
			area2 = getServicioArea().buscar(Long.parseLong(area));
		}

		if ((area.equals("") && servicioConsulta
				.buscarEntreFechasReposoyTrabajadores(desde, hasta).isEmpty())
				|| (!area.equals("") && servicioConsulta
						.buscarEntreFechasReposoAreayTrabajadores(desde, hasta,
								servicioArea.buscar(Long.parseLong(area)))
						.isEmpty()))
			Mensaje.mensajeAlerta(Mensaje.noHayRegistros);
		else {

			Clients.evalJavaScript("window.open('"
					+ damePath()
					+ "Reportero?valor=13&valor6="
					+ fecha1
					+ "&valor7="
					+ fecha2
					+ "&valor8="
					+ area
					+ "&valor20="
					+ tipoReporte
					+ "','','top=100,left=200,height=600,width=800,scrollbars=1,resizable=1')");
		}

	}

	public byte[] reporteReposoPorArea(String part1, String part2, String area,
			String tipoReporte) throws JRException {
		byte[] fichero = null;
		SimpleDateFormat formato = new SimpleDateFormat("dd-MM-yyyy");
		Date fecha1 = null;
		try {
			fecha1 = formato.parse(part1);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		Date fecha2 = null;
		try {
			fecha2 = formato.parse(part2);
		} catch (ParseException e) {
			e.printStackTrace();
		}

		fecha2 = agregarDia(fecha2);
		List<Consulta> consuta = new ArrayList<Consulta>();

		if (area.equals(""))
			consuta = getServicioConsulta()
					.buscarEntreFechasReposoyTrabajadores(fecha1, fecha2);
		else {
			Area area2 = getServicioArea().buscar(Long.parseLong(area));
			consuta = getServicioConsulta()
					.buscarEntreFechasReposoAreayTrabajadores(fecha1, fecha2,
							area2);
		}

		Map<String, Object> p = new HashMap<String, Object>();
		p.put("desde", part1);
		p.put("hasta", part2);

		for (int i = 0; i < consuta.size(); i++) {
			Consulta cons = consuta.get(i);
			List<ConsultaDiagnostico> dig = getServicioConsultaDiagnostico()
					.buscarPorConsulta(cons);
			Calendar c = Calendar.getInstance();
			if (cons.getFechaReposo() != null)
				c.setTime(cons.getFechaReposo());
			else {
				cons.setFechaReposo(cons.getFechaConsulta());
				c.setTime(cons.getFechaConsulta());
			}

			if (cons.getTipoReposo() != null) {
				if (cons.getTipoReposo().equals("Dias")) {
					c.add(Calendar.DAY_OF_YEAR, cons.getDiasReposo());
					cons.setUsuarioAuditoria(cons.getDiasReposo() + " Dias");
				} else
					cons.setUsuarioAuditoria(cons.getDiasReposo() + " Horas");
			} else {
				c.add(Calendar.DAY_OF_YEAR, cons.getDiasReposo());
				cons.setUsuarioAuditoria(cons.getDiasReposo() + " Dias");
			}
			Date fechaHasta = c.getTime();
			Timestamp fechaHasta2 = new Timestamp(fechaHasta.getTime());
			cons.setFechaAuditoria(fechaHasta2);
			if (!dig.isEmpty()) {
				if (dig.get(0) != null) {

					cons.setEnfermedadActual(dig.get(0).getDiagnostico()
							.getNombre());
					cons.setMotivoConsulta(dig.get(0).getTipo());

				}
			} else {
				cons.setEnfermedadActual("");
				cons.setMotivoConsulta("");
			}
		}
		p.put("data", new JRBeanCollectionDataSource(consuta));

		JasperReport reporte = (JasperReport) JRLoader.loadObject(getClass()
				.getResource("/reporte/medico/reposo/RRepososPorArea.jasper"));
		if (tipoReporte.equals("EXCEL")) {

			JasperPrint jasperPrint = null;
			try {
				jasperPrint = JasperFillManager.fillReport(reporte, p,
						new JRBeanCollectionDataSource(consuta));
			} catch (JRException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			ByteArrayOutputStream xlsReport = new ByteArrayOutputStream();
			JRXlsxExporter exporter = new JRXlsxExporter();
			exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
			exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, xlsReport);
			try {
				exporter.exportReport();
			} catch (JRException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return xlsReport.toByteArray();
		} else {

			fichero = JasperRunManager.runReportToPdf(reporte, p,
					new JRBeanCollectionDataSource(consuta));
			return fichero;
		}
	}

	public void reporteDiagnostico() {
		Date desde = dtbDesde.getValue();
		Date hasta = dtbHasta.getValue();
		DateFormat fecha = new SimpleDateFormat("dd-MM-yyyy");
		String fecha1 = fecha.format(desde);
		String fecha2 = fecha.format(hasta);
		String diagnostico = "";
		String tipoReporte = cmbTipo.getValue();
		if (cmbDiagnostico.getValue().equals("TODOS"))
			diagnostico = "";
		else
			diagnostico = cmbDiagnostico.getValue();
		String diagnosticoReal = "";
		JSONObject json = new JSONObject();
		List<Long> ids = new ArrayList<Long>();
		for (int i = 0; i < diagnosticosAgregados.size(); i++) {
			Diagnostico object = diagnosticosAgregados.get(i);
			ids.add(object.getIdDiagnostico());
			try {
				json.put("valor" + i, object.getIdDiagnostico());
			} catch (JSONException e) {
				e.printStackTrace();
			}
			if (object.getIdDiagnostico() == 0) {
				diagnosticoReal = "TODOS";
				json = new JSONObject();
				try {
					json.put("valor", 0);
				} catch (JSONException e) {
					e.printStackTrace();
				}
				i = diagnosticosAgregados.size();
			}
		}
		if ((diagnostico.equals("") && diagnosticoReal.equals("TODOS") && servicioConsultaDiagnostico
				.buscarEntreFechasOrdenadoPorDiagnostico(desde, hasta)
				.isEmpty())
				|| (diagnostico.equals("") && diagnosticoReal.equals("") && servicioConsultaDiagnostico
						.buscarEntreFechasOrdenadoPorDiagnosticoYDiagnosticos(
								desde, hasta, ids).isEmpty())
				|| (!diagnostico.equals("") && diagnosticoReal.equals("TODOS") && servicioConsultaDiagnostico
						.buscarEntreFechasyTipoDiagnostico(desde, hasta,
								diagnostico).isEmpty())
				|| (!diagnostico.equals("") && diagnosticoReal.equals("") && servicioConsultaDiagnostico
						.buscarEntreFechasyTipoDiagnosticoYDiagnosticos(desde,
								hasta, diagnostico, ids).isEmpty()))
			Mensaje.mensajeAlerta(Mensaje.noHayRegistros);
		else {
			Clients.evalJavaScript("window.open('"
					+ damePath()
					+ "Reportero?valor=15&valor6="
					+ fecha1
					+ "&valor7="
					+ fecha2
					+ "&valor8="
					+ diagnostico
					+ "&valor40="
					+ json.toString()
					+ "&valor20="
					+ tipoReporte
					+ "','','top=100,left=200,height=600,width=800,scrollbars=1,resizable=1')");
		}

	}

	public byte[] reporteReposoPorDiagnostico(String part1, String part2,
			String diagnostico, String tipoReporte, JSONObject jObj)
			throws JRException {
		byte[] fichero = null;
		SimpleDateFormat formato = new SimpleDateFormat("dd-MM-yyyy");
		Date fecha1 = null;
		try {
			fecha1 = formato.parse(part1);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		Date fecha2 = null;
		try {
			fecha2 = formato.parse(part2);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		fecha2 = agregarDia(fecha2);
		List<Long> ids = new ArrayList<Long>();
		String diagnosticoReal = "";
		Iterator<?> it = jObj.keys();
		while (it.hasNext()) {
			String key = (String) it.next();
			Integer o;
			try {
				o = (Integer) jObj.get(key);
				ids.add(Long.valueOf(o));
				if (o == 0)
					diagnosticoReal = "TODOS";
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
		List<ConsultaDiagnostico> consutaDiag = new ArrayList<ConsultaDiagnostico>();

		if (diagnostico.equals("")) {
			if (diagnosticoReal.equals("TODOS"))
				consutaDiag = getServicioConsultaDiagnostico()
						.buscarEntreFechasOrdenadoPorDiagnostico(fecha1, fecha2);
			else
				consutaDiag = getServicioConsultaDiagnostico()
						.buscarEntreFechasOrdenadoPorDiagnosticoYDiagnosticos(
								fecha1, fecha2, ids);
		} else {
			if (diagnosticoReal.equals("TODOS"))
				consutaDiag = getServicioConsultaDiagnostico()
						.buscarEntreFechasyTipoDiagnostico(fecha1, fecha2,
								diagnostico);
			else
				consutaDiag = getServicioConsultaDiagnostico()
						.buscarEntreFechasyTipoDiagnosticoYDiagnosticos(fecha1,
								fecha2, diagnostico, ids);
		}

		Map<String, Object> p = new HashMap<String, Object>();
		p.put("desde", part1);
		p.put("hasta", part2);

		// List<Long> consuta = getServicioConsultaDiagnostico()
		// .cantidadConsultas(consutaDiag);
		// p.put("total", consuta.size());

		for (int i = 0; i < consutaDiag.size(); i++) {
			Consulta cons = consutaDiag.get(i).getConsulta();
			Calendar c = Calendar.getInstance();
			if (cons.getFechaReposo() != null)
				c.setTime(cons.getFechaReposo());
			else {
				cons.setFechaReposo(cons.getFechaConsulta());
				c.setTime(cons.getFechaConsulta());
			}
			if (cons.getTipoReposo() != null) {
				if (cons.getTipoReposo().equals("Dias")) {
					c.add(Calendar.DAY_OF_YEAR, cons.getDiasReposo());
					consutaDiag
							.get(i)
							.getConsulta()
							.setUsuarioAuditoria(cons.getDiasReposo() + " Dias");
				} else
					consutaDiag
							.get(i)
							.getConsulta()
							.setUsuarioAuditoria(
									cons.getDiasReposo() + " Horas");
			} else {
				c.add(Calendar.DAY_OF_YEAR, cons.getDiasReposo());
				consutaDiag.get(i).getConsulta()
						.setUsuarioAuditoria(cons.getDiasReposo() + " Dias");
			}
			Date fechaHasta = c.getTime();
			Timestamp fechaHasta2 = new Timestamp(fechaHasta.getTime());
			cons.setFechaAuditoria(fechaHasta2);
		}
		p.put("data", new JRBeanCollectionDataSource(consutaDiag));

		JasperReport reporte = (JasperReport) JRLoader
				.loadObject(getClass().getResource(
						"/reporte/medico/reposo/RRepososPorDiagnostico.jasper"));
		if (tipoReporte.equals("EXCEL")) {

			JasperPrint jasperPrint = null;
			try {
				jasperPrint = JasperFillManager.fillReport(reporte, p,
						new JRBeanCollectionDataSource(consutaDiag));
			} catch (JRException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			ByteArrayOutputStream xlsReport = new ByteArrayOutputStream();
			JRXlsxExporter exporter = new JRXlsxExporter();
			exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
			exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, xlsReport);
			try {
				exporter.exportReport();
			} catch (JRException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return xlsReport.toByteArray();
		} else {
			fichero = JasperRunManager.runReportToPdf(reporte, p,
					new JRBeanCollectionDataSource(consutaDiag));

			return fichero;
		}
	}

	public void reporteDoctor() {
		Date desde = dtbDesde.getValue();
		Date hasta = dtbHasta.getValue();
		DateFormat fecha = new SimpleDateFormat("dd-MM-yyyy");
		String fecha1 = fecha.format(desde);
		String fecha2 = fecha.format(hasta);
		String unidad = "";
		String tipoReporte = cmbTipo.getValue();
			unidad = "";

		if ((unidad.equals("") && idDoctor.equals("TODOS") && servicioConsulta
				.buscarEntreFechasOrdenadasPorUnidadReposoyTrabajadores(desde,
						hasta).isEmpty())
				// || (!unidad.equals("") && idDoctor.equals("TODOS") &&
				// servicioConsulta
				// .buscarEntreFechasPorUnidadReposoyTrabajadores(desde,
				// hasta, unidad).isEmpty())
				|| (unidad.equals("") && !idDoctor.equals("TODOS") && servicioConsulta
						.buscarEntreFechasPorDoctorReposoyTrabajadores(desde,
								hasta,
								getServicioDoctor().buscarPorCedula(idDoctor))
						.isEmpty())
				|| (!unidad.equals("") && !idDoctor.equals("TODOS") && servicioConsulta
						.buscarEntreFechasPorDoctorReposoyTrabajadores(desde,
								hasta,
								getServicioDoctor().buscarPorCedula(idDoctor))
						.isEmpty()))
			Mensaje.mensajeAlerta(Mensaje.noHayRegistros);
		else
			Clients.evalJavaScript("window.open('"
					+ damePath()
					+ "Reportero?valor=14&valor6="
					+ fecha1
					+ "&valor7="
					+ fecha2
					+ "&valor8="
					+ unidad
					+ "&valor9="
					+ idDoctor
					+ "&valor20="
					+ tipoReporte
					+ "','','top=100,left=200,height=600,width=800,scrollbars=1,resizable=1')");

	}

	private void reportePaciente() {
		Date desde = dtbDesde.getValue();
		Date hasta = dtbHasta.getValue();
		DateFormat fecha = new SimpleDateFormat("dd-MM-yyyy");
		String fecha1 = fecha.format(desde);
		String fecha2 = fecha.format(hasta);
		String tipoReporte = cmbTipo.getValue();

		if ((idPaciente.equals("TODOS") && getServicioConsulta()
				.buscarEntreFechasOrdenadasPorPacienteReposoyTrabajadores(
						desde, hasta).isEmpty())
				|| !idPaciente.equals("TODOS")
				&& getServicioConsulta()
						.buscarEntreFechasPorPacienteReposoyTrabajadores(desde,
								hasta, idPaciente).isEmpty())
			Mensaje.mensajeAlerta(Mensaje.noHayRegistros);
		else {
			Clients.evalJavaScript("window.open('"
					+ damePath()
					+ "Reportero?valor=25&valor6="
					+ fecha1
					+ "&valor7="
					+ fecha2
					+ "&valor8="
					+ idPaciente
					+ "&valor20="
					+ tipoReporte
					+ "','','top=100,left=200,height=600,width=800,scrollbars=1,resizable=1')");
		}

	}

	public byte[] reporteReposoPorPaciente(String part1, String part2,
			String idPaciente, String tipoReporte) throws JRException {
		byte[] fichero = null;
		SimpleDateFormat formato = new SimpleDateFormat("dd-MM-yyyy");
		Date fecha1 = null;
		try {
			fecha1 = formato.parse(part1);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		Date fecha2 = null;
		try {
			fecha2 = formato.parse(part2);
		} catch (ParseException e) {
			e.printStackTrace();
		}

		fecha2 = agregarDia(fecha2);
		List<Consulta> consuta = new ArrayList<Consulta>();

		if (idPaciente.equals("TODOS"))
			consuta = getServicioConsulta()
					.buscarEntreFechasOrdenadasPorPacienteReposoyTrabajadores(
							fecha1, fecha2);
		else {
			consuta = getServicioConsulta()
					.buscarEntreFechasPorPacienteReposoyTrabajadores(fecha1,
							fecha2, idPaciente);
		}

		Map<String, Object> p = new HashMap<String, Object>();
		p.put("desde", part1);
		p.put("hasta", part2);

		for (int i = 0; i < consuta.size(); i++) {
			Consulta cons = consuta.get(i);
			List<ConsultaDiagnostico> dig = getServicioConsultaDiagnostico()
					.buscarPorConsulta(cons);
			Calendar c = Calendar.getInstance();
			if (cons.getFechaReposo() != null)
				c.setTime(cons.getFechaReposo());
			else {
				cons.setFechaReposo(cons.getFechaConsulta());
				c.setTime(cons.getFechaConsulta());
			}
			if (cons.getTipoReposo() != null) {
				if (cons.getTipoReposo().equals("Dias")) {
					c.add(Calendar.DAY_OF_YEAR, cons.getDiasReposo());
					cons.setUsuarioAuditoria(cons.getDiasReposo() + " Dias");
				} else
					cons.setUsuarioAuditoria(cons.getDiasReposo() + " Horas");
			} else {
				c.add(Calendar.DAY_OF_YEAR, cons.getDiasReposo());
				cons.setUsuarioAuditoria(cons.getDiasReposo() + " Dias");
			}
			Date fechaHasta = c.getTime();
			Timestamp fechaHasta2 = new Timestamp(fechaHasta.getTime());
			// Timestamp fechaHasta = (Timestamp) c.getTime();
			cons.setFechaAuditoria(fechaHasta2);
			if (!dig.isEmpty()) {
				if (dig.get(0) != null) {
					cons.setEnfermedadActual(dig.get(0).getDiagnostico()
							.getNombre());
					cons.setMotivoConsulta(dig.get(0).getTipo());

				}
			} else {
				cons.setEnfermedadActual("");
				cons.setMotivoConsulta("");
			}
		}
		p.put("data", new JRBeanCollectionDataSource(consuta));

		JasperReport reporte = (JasperReport) JRLoader.loadObject(getClass()
				.getResource(
						"/reporte/medico/reposo/RRepososPorPaciente.jasper"));
		if (tipoReporte.equals("EXCEL")) {

			JasperPrint jasperPrint = null;
			try {
				jasperPrint = JasperFillManager.fillReport(reporte, p,
						new JRBeanCollectionDataSource(consuta));
			} catch (JRException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			ByteArrayOutputStream xlsReport = new ByteArrayOutputStream();
			JRXlsxExporter exporter = new JRXlsxExporter();
			exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
			exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, xlsReport);
			try {
				exporter.exportReport();
			} catch (JRException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return xlsReport.toByteArray();
		} else {

			fichero = JasperRunManager.runReportToPdf(reporte, p,
					new JRBeanCollectionDataSource(consuta));
			return fichero;
		}

	}

	public byte[] reporteReposoPorDoctor(String part1, String part2,
			String unidad, String doctor, String tipoReporte)
			throws JRException {
		byte[] fichero = null;
		SimpleDateFormat formato = new SimpleDateFormat("dd-MM-yyyy");
		Date fecha1 = null;
		try {
			fecha1 = formato.parse(part1);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		Date fecha2 = null;
		try {
			fecha2 = formato.parse(part2);
		} catch (ParseException e) {
			e.printStackTrace();
		}

		fecha2 = agregarDia(fecha2);
		List<Consulta> consuta = new ArrayList<Consulta>();

		if (unidad.equals("") && doctor.equals("TODOS"))
			consuta = getServicioConsulta()
					.buscarEntreFechasOrdenadasPorUnidadReposoyTrabajadores(
							fecha1, fecha2);
		else {
			// if (!unidad.equals("") && doctor.equals("TODOS"))
			// consuta = getServicioConsulta()
			// .buscarEntreFechasPorUnidadReposoyTrabajadores(fecha1,
			// fecha2, unidad);
			// else {
			if (unidad.equals("") && !doctor.equals("TODOS")) {
				DoctorInterno doc = getServicioDoctor().buscarPorCedula(doctor);
				consuta = getServicioConsulta()
						.buscarEntreFechasPorDoctorReposoyTrabajadores(fecha1,
								fecha2, doc);
			} else {
				if (!unidad.equals("") && !doctor.equals("TODOS")) {
					DoctorInterno doc = getServicioDoctor().buscarPorCedula(
							doctor);
					consuta = getServicioConsulta()
							.buscarEntreFechasPorDoctorReposoyTrabajadores(
									fecha1, fecha2, doc);
				}
				// }
			}
		}

		Map<String, Object> p = new HashMap<String, Object>();
		p.put("desde", part1);
		p.put("hasta", part2);
		if (unidad.equals(""))
			p.put("unidad", "TODAS");
		else
			p.put("unidad", unidad);

		for (int i = 0; i < consuta.size(); i++) {
			Consulta cons = consuta.get(i);
			List<ConsultaDiagnostico> dig = getServicioConsultaDiagnostico()
					.buscarPorConsulta(cons);
			Calendar c = Calendar.getInstance();
			if (cons.getFechaReposo() != null)
				c.setTime(cons.getFechaReposo());
			else {
				cons.setFechaReposo(cons.getFechaConsulta());
				c.setTime(cons.getFechaConsulta());
			}
			if (cons.getTipoReposo() != null) {
				if (cons.getTipoReposo().equals("Dias")) {
					c.add(Calendar.DAY_OF_YEAR, cons.getDiasReposo());
					cons.setUsuarioAuditoria(cons.getDiasReposo() + " Dias");
				} else
					cons.setUsuarioAuditoria(cons.getDiasReposo() + " Horas");
			} else {
				c.add(Calendar.DAY_OF_YEAR, cons.getDiasReposo());
				cons.setUsuarioAuditoria(cons.getDiasReposo() + " Dias");
			}
			Date fechaHasta = c.getTime();
			Timestamp fechaHasta2 = new Timestamp(fechaHasta.getTime());
			// Timestamp fechaHasta = (Timestamp) c.getTime();
			cons.setFechaAuditoria(fechaHasta2);
			if (!dig.isEmpty()) {
				if (dig.get(0) != null) {
					cons.setEnfermedadActual(dig.get(0).getDiagnostico()
							.getNombre());
					cons.setMotivoConsulta(dig.get(0).getTipo());

				}
			} else {
				cons.setEnfermedadActual("");
				cons.setMotivoConsulta("");
			}
		}
		p.put("data", new JRBeanCollectionDataSource(consuta));

		JasperReport reporte = (JasperReport) JRLoader
				.loadObject(getClass().getResource(
						"/reporte/medico/reposo/RRepososPorDoctor.jasper"));
		if (tipoReporte.equals("EXCEL")) {

			JasperPrint jasperPrint = null;
			try {
				jasperPrint = JasperFillManager.fillReport(reporte, p,
						new JRBeanCollectionDataSource(consuta));
			} catch (JRException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			ByteArrayOutputStream xlsReport = new ByteArrayOutputStream();
			JRXlsxExporter exporter = new JRXlsxExporter();
			exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
			exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, xlsReport);
			try {
				exporter.exportReport();
			} catch (JRException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return xlsReport.toByteArray();
		} else {

			fichero = JasperRunManager.runReportToPdf(reporte, p,
					new JRBeanCollectionDataSource(consuta));
			return fichero;
		}

	}

	public void reporteEmpresa() {
		Date desde = dtbDesde.getValue();
		Date hasta = dtbHasta.getValue();
		DateFormat fecha = new SimpleDateFormat("dd-MM-yyyy");
		String fecha1 = fecha.format(desde);
		String fecha2 = fecha.format(hasta);
		String empresa = "";
		String tipoReporte = cmbTipo.getValue();
		Empresa empresa2 = new Empresa();
		if (cmbEmpresa.getValue().equals("TODAS"))
			empresa = "";
		else {
			empresa = cmbEmpresa.getSelectedItem().getContext();
			empresa2 = getServicioEmpresa().buscar(Long.parseLong(empresa));
		}

		if ((empresa.equals("") && servicioConsulta
				.buscarEntreFechasReposoyTrabajadores(desde, hasta).isEmpty())
				|| (!empresa.equals("") && servicioConsulta
						.buscarEntreFechasReposoEmpresayTrabajadores(desde,
								hasta,
								servicioEmpresa.buscar(Long.parseLong(empresa)))
						.isEmpty()))
			Mensaje.mensajeAlerta(Mensaje.noHayRegistros);
		else {

			Clients.evalJavaScript("window.open('"
					+ damePath()
					+ "Reportero?valor=40&valor6="
					+ fecha1
					+ "&valor7="
					+ fecha2
					+ "&valor8="
					+ empresa
					+ "&valor20="
					+ tipoReporte
					+ "','','top=100,left=200,height=600,width=800,scrollbars=1,resizable=1')");
		}

	}

	public byte[] reporteReposoPorEmpresa(String part1, String part2,
			String empresa, String tipoReporte) throws JRException {
		byte[] fichero = null;
		SimpleDateFormat formato = new SimpleDateFormat("dd-MM-yyyy");
		Date fecha1 = null;
		try {
			fecha1 = formato.parse(part1);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		Date fecha2 = null;
		try {
			fecha2 = formato.parse(part2);
		} catch (ParseException e) {
			e.printStackTrace();
		}

		fecha2 = agregarDia(fecha2);
		List<Consulta> consuta = new ArrayList<Consulta>();

		if (empresa.equals(""))
			consuta = getServicioConsulta()
					.buscarEntreFechasReposoyTrabajadores(fecha1, fecha2);
		else {
			Empresa empresa2 = getServicioEmpresa().buscar(
					Long.parseLong(empresa));
			consuta = getServicioConsulta()
					.buscarEntreFechasReposoEmpresayTrabajadores(fecha1,
							fecha2, empresa2);
		}

		Map<String, Object> p = new HashMap<String, Object>();
		p.put("desde", part1);
		p.put("hasta", part2);

		for (int i = 0; i < consuta.size(); i++) {
			Consulta cons = consuta.get(i);
			List<ConsultaDiagnostico> dig = getServicioConsultaDiagnostico()
					.buscarPorConsulta(cons);
			Calendar c = Calendar.getInstance();
			if (cons.getFechaReposo() != null)
				c.setTime(cons.getFechaReposo());
			else {
				cons.setFechaReposo(cons.getFechaConsulta());
				c.setTime(cons.getFechaConsulta());
			}

			if (cons.getTipoReposo() != null) {
				if (cons.getTipoReposo().equals("Dias")) {
					c.add(Calendar.DAY_OF_YEAR, cons.getDiasReposo());
					cons.setUsuarioAuditoria(cons.getDiasReposo() + " Dias");
				} else
					cons.setUsuarioAuditoria(cons.getDiasReposo() + " Horas");
			} else {
				c.add(Calendar.DAY_OF_YEAR, cons.getDiasReposo());
				cons.setUsuarioAuditoria(cons.getDiasReposo() + " Dias");
			}
			Date fechaHasta = c.getTime();
			Timestamp fechaHasta2 = new Timestamp(fechaHasta.getTime());
			cons.setFechaAuditoria(fechaHasta2);
			if (!dig.isEmpty()) {
				if (dig.get(0) != null) {

					cons.setEnfermedadActual(dig.get(0).getDiagnostico()
							.getNombre());
					cons.setMotivoConsulta(dig.get(0).getTipo());

				}
			} else {
				cons.setEnfermedadActual("");
				cons.setMotivoConsulta("");
			}
		}
		p.put("data", new JRBeanCollectionDataSource(consuta));

		JasperReport reporte = (JasperReport) JRLoader
				.loadObject(getClass().getResource(
						"/reporte/medico/reposo/RRepososPorEmpresa.jasper"));
		if (tipoReporte.equals("EXCEL")) {

			JasperPrint jasperPrint = null;
			try {
				jasperPrint = JasperFillManager.fillReport(reporte, p,
						new JRBeanCollectionDataSource(consuta));
			} catch (JRException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			ByteArrayOutputStream xlsReport = new ByteArrayOutputStream();
			JRXlsxExporter exporter = new JRXlsxExporter();
			exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
			exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, xlsReport);
			try {
				exporter.exportReport();
			} catch (JRException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return xlsReport.toByteArray();
		} else {

			fichero = JasperRunManager.runReportToPdf(reporte, p,
					new JRBeanCollectionDataSource(consuta));
			return fichero;
		}
	}

	public void reporteCargo() {
		Date desde = dtbDesde.getValue();
		Date hasta = dtbHasta.getValue();
		DateFormat fecha = new SimpleDateFormat("dd-MM-yyyy");
		String fecha1 = fecha.format(desde);
		String fecha2 = fecha.format(hasta);
		String cargo = "";
		String tipoReporte = cmbTipo.getValue();
		Cargo cargo2 = new Cargo();
		if (cmbCargo.getValue().equals("TODOS"))
			cargo = "";
		else {
			cargo = cmbCargo.getSelectedItem().getContext();
			cargo2 = getServicioCargo().buscar(Long.parseLong(cargo));
		}

		if ((cargo.equals("") && servicioConsulta
				.buscarEntreFechasReposoyTrabajadores(desde, hasta).isEmpty())
				|| (!cargo.equals("") && servicioConsulta
						.buscarEntreFechasReposoCargoyTrabajadores(desde,
								hasta,
								servicioCargo.buscar(Long.parseLong(cargo)))
						.isEmpty()))
			Mensaje.mensajeAlerta(Mensaje.noHayRegistros);
		else {

			Clients.evalJavaScript("window.open('"
					+ damePath()
					+ "Reportero?valor=38&valor6="
					+ fecha1
					+ "&valor7="
					+ fecha2
					+ "&valor8="
					+ cargo
					+ "&valor20="
					+ tipoReporte
					+ "','','top=100,left=200,height=600,width=800,scrollbars=1,resizable=1')");
		}

	}

	public byte[] reporteReposoPorCargo(String part1, String part2,
			String cargo, String tipoReporte) throws JRException {
		byte[] fichero = null;
		SimpleDateFormat formato = new SimpleDateFormat("dd-MM-yyyy");
		Date fecha1 = null;
		try {
			fecha1 = formato.parse(part1);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		Date fecha2 = null;
		try {
			fecha2 = formato.parse(part2);
		} catch (ParseException e) {
			e.printStackTrace();
		}

		fecha2 = agregarDia(fecha2);
		List<Consulta> consuta = new ArrayList<Consulta>();

		if (cargo.equals(""))
			consuta = getServicioConsulta()
					.buscarEntreFechasReposoyTrabajadores(fecha1, fecha2);
		else {
			Cargo cargo2 = getServicioCargo().buscar(Long.parseLong(cargo));
			consuta = getServicioConsulta()
					.buscarEntreFechasReposoCargoyTrabajadores(fecha1, fecha2,
							cargo2);
		}

		Map<String, Object> p = new HashMap<String, Object>();
		p.put("desde", part1);
		p.put("hasta", part2);

		for (int i = 0; i < consuta.size(); i++) {
			Consulta cons = consuta.get(i);
			List<ConsultaDiagnostico> dig = getServicioConsultaDiagnostico()
					.buscarPorConsulta(cons);
			Calendar c = Calendar.getInstance();
			if (cons.getFechaReposo() != null)
				c.setTime(cons.getFechaReposo());
			else {
				cons.setFechaReposo(cons.getFechaConsulta());
				c.setTime(cons.getFechaConsulta());
			}

			if (cons.getTipoReposo() != null) {
				if (cons.getTipoReposo().equals("Dias")) {
					c.add(Calendar.DAY_OF_YEAR, cons.getDiasReposo());
					cons.setUsuarioAuditoria(cons.getDiasReposo() + " Dias");
				} else
					cons.setUsuarioAuditoria(cons.getDiasReposo() + " Horas");
			} else {
				c.add(Calendar.DAY_OF_YEAR, cons.getDiasReposo());
				cons.setUsuarioAuditoria(cons.getDiasReposo() + " Dias");
			}
			Date fechaHasta = c.getTime();
			Timestamp fechaHasta2 = new Timestamp(fechaHasta.getTime());
			cons.setFechaAuditoria(fechaHasta2);
			if (!dig.isEmpty()) {
				if (dig.get(0) != null) {

					cons.setEnfermedadActual(dig.get(0).getDiagnostico()
							.getNombre());
					cons.setMotivoConsulta(dig.get(0).getTipo());

				}
			} else {
				cons.setEnfermedadActual("");
				cons.setMotivoConsulta("");
			}
		}
		p.put("data", new JRBeanCollectionDataSource(consuta));

		JasperReport reporte = (JasperReport) JRLoader.loadObject(getClass()
				.getResource("/reporte/medico/reposo/RRepososPorCargo.jasper"));
		if (tipoReporte.equals("EXCEL")) {

			JasperPrint jasperPrint = null;
			try {
				jasperPrint = JasperFillManager.fillReport(reporte, p,
						new JRBeanCollectionDataSource(consuta));
			} catch (JRException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			ByteArrayOutputStream xlsReport = new ByteArrayOutputStream();
			JRXlsxExporter exporter = new JRXlsxExporter();
			exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
			exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, xlsReport);
			try {
				exporter.exportReport();
			} catch (JRException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return xlsReport.toByteArray();
		} else {

			fichero = JasperRunManager.runReportToPdf(reporte, p,
					new JRBeanCollectionDataSource(consuta));
			return fichero;
		}
	}

	@Listen("onClick = #btnBuscarDoctor")
	public void mostrarCatalogo() throws IOException {

		final List<DoctorInterno> usuarios = new ArrayList<DoctorInterno>();
		DoctorInterno usuarioT = new DoctorInterno();
		usuarioT.setCedula("TODOS");
		usuarioT.setFicha("TODOS");
		usuarioT.setPrimerNombre("TODOS");
		usuarioT.setPrimerApellido("TODOS");
		usuarios.add(usuarioT);
		usuarios.addAll(servicioDoctor.buscarTodos());
		catalogoDoctor = new Catalogo<DoctorInterno>(catalogoUsuarios,
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
		catalogoDoctor.setParent(catalogoUsuarios);
		catalogoDoctor.doModal();
	}

	/* Permite la seleccion de un item del catalogo de doctores */
	@Listen("onSeleccion = #catalogoUsuarios")
	public void seleccionarDoctor() {
		DoctorInterno usuario = catalogoDoctor.objetoSeleccionadoDelCatalogo();
		lblNombreDoctor.setValue(usuario.getPrimerNombre() + " "
				+ usuario.getPrimerApellido());
		idDoctor = usuario.getCedula();
		catalogoDoctor.setParent(null);
	}

	// /* Muestra el catalogo de los Pacientes */
	@Listen("onClick = #btnBuscarPaciente")
	public void mostrarCatalogoPaciente() {
		final List<Paciente> pacientes = new ArrayList<Paciente>();

		Paciente pacienteT = new Paciente();
		pacienteT.setCedula("TODOS");
		pacienteT.setFicha("TODOS");
		pacienteT.setPrimerNombre("TODOS");
		pacienteT.setPrimerApellido("TODOS");
		pacientes.add(pacienteT);
		pacientes.addAll(servicioPaciente.buscarTodosActivos());

		catalogo = new Catalogo<Paciente>(divCatalogoPaciente,
				"Catalogo de Pacientes", pacientes, false, "Cedula", "Ficha",
				"Primer Nombre", "Segundo Nombre", "Primer Apellido",
				"Segundo Apellido") {

			@Override
			protected List<Paciente> buscar(String valor, String combo) {

				switch (combo) {
				case "Primer Nombre":
					return servicioPaciente.filtroNombre1Activos(valor);
				case "Segundo Nombre":
					return servicioPaciente.filtroNombre2Activos(valor);
				case "Cedula":
					return servicioPaciente.filtroCedulaActivos(valor);
				case "Ficha":
					return servicioPaciente.filtroFichaActivos(valor);
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
		catalogo.setParent(divCatalogoPaciente);
		catalogo.doModal();
	}

	@Listen("onSeleccion = #divCatalogoPaciente")
	public void seleccionar() {
		Paciente paciente = catalogo.objetoSeleccionadoDelCatalogo();
		lblPaciente.setValue(paciente.getPrimerNombre() + " "
				+ paciente.getPrimerApellido());
		idPaciente = paciente.getCedula();
		catalogo.setParent(null);
	}

	@Listen("onClick = #pasar1")
	public void derechaDiagnostico() {
		List<Listitem> listitemEliminar = new ArrayList<Listitem>();
		List<Listitem> listItem = ltbDiagnosticos.getItems();
		if (listItem.size() != 0) {
			for (int i = 0; i < listItem.size(); i++) {
				if (listItem.get(i).isSelected()) {
					Diagnostico diagnostico = listItem.get(i).getValue();
					diagnosticosDisponibles.remove(diagnostico);
					diagnosticosAgregados.add(diagnostico);
					ltbDiagnosticosAgregados
							.setModel(new ListModelList<Diagnostico>(
									diagnosticosAgregados));
					ltbDiagnosticosAgregados.renderAll();
					listitemEliminar.add(listItem.get(i));
				}
			}
		}
		for (int i = 0; i < listitemEliminar.size(); i++) {
			ltbDiagnosticos.removeItemAt(listitemEliminar.get(i).getIndex());
			ltbDiagnosticos.renderAll();
		}
		listasMultiples();
	}

	@Listen("onClick = #pasar2")
	public void izquierdaDiagnostico() {
		List<Listitem> listitemEliminar = new ArrayList<Listitem>();
		List<Listitem> listItem2 = ltbDiagnosticosAgregados.getItems();
		if (listItem2.size() != 0) {
			for (int i = 0; i < listItem2.size(); i++) {
				if (listItem2.get(i).isSelected()) {
					Diagnostico diagnostico = listItem2.get(i).getValue();
					diagnosticosAgregados.remove(diagnostico);
					diagnosticosDisponibles.add(diagnostico);
					ltbDiagnosticos.setModel(new ListModelList<Diagnostico>(
							diagnosticosDisponibles));
					ltbDiagnosticos.renderAll();
					listitemEliminar.add(listItem2.get(i));
				}
			}
		}
		for (int i = 0; i < listitemEliminar.size(); i++) {
			ltbDiagnosticosAgregados.removeItemAt(listitemEliminar.get(i)
					.getIndex());
			ltbDiagnosticosAgregados.renderAll();
		}
		listasMultiples();
	}

	private void listasMultiples() {
		ltbDiagnosticosAgregados.setMultiple(false);
		ltbDiagnosticosAgregados.setCheckmark(false);
		ltbDiagnosticosAgregados.setMultiple(true);
		ltbDiagnosticosAgregados.setCheckmark(true);
		ltbDiagnosticos.setMultiple(false);
		ltbDiagnosticos.setCheckmark(false);
		ltbDiagnosticos.setMultiple(true);
		ltbDiagnosticos.setCheckmark(true);
	}

	private void cargarLista() {
		if (box.isVisible()) {
			diagnosticosDisponibles.clear();
			Diagnostico diag = new Diagnostico();
			diag.setNombre("TODOS");
			diag.setIdDiagnostico(0);
			diagnosticosDisponibles.add(diag);
			diagnosticosDisponibles.addAll(servicioConsultaDiagnostico
					.buscarDiagnosticosExistentes(dtbDesde.getValue(),
							dtbHasta.getValue()));
			ltbDiagnosticos.setModel(new ListModelList<Diagnostico>(
					diagnosticosDisponibles));
			diagnosticosAgregados.clear();
			ltbDiagnosticosAgregados.getItems().clear();
			listasMultiples();
		}
	}

	@Listen("onChange = #dtbDesde, #dtbHasta")
	public void changeList() {
		cargarLista();
	}

}
