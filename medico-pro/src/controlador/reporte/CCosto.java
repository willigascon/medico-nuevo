package controlador.reporte;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import modelo.medico.consulta.Consulta;
import modelo.medico.consulta.ConsultaDiagnostico;
import modelo.organizacion.Area;
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
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zk.ui.util.Clients;
import org.zkoss.zul.Button;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Div;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Tab;

import componente.Botonera;
import componente.Mensaje;

import controlador.utils.CGenerico;

public class CCosto extends CGenerico {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Wire
	private Combobox cmbArea;
	@Wire
	private Combobox cmbDiagnostico;
	@Wire
	private Datebox dtbDesde;
	@Wire
	private Datebox dtbHasta;
	@Wire
	private Div divCosto;
	@Wire
	private Div botoneraCosto;
	@Wire
	private Combobox cmbTipo;

	@Override
	public void inicializar() throws IOException {
		cargarCombo();
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
				cerrarVentana(divCosto, titulo, tabs);
			}

			@Override
			public void limpiar() {
				dtbDesde.setValue(fecha);
				dtbHasta.setValue(fecha);
				cmbArea.setValue("TODOS");
				cmbDiagnostico.setValue("TODOS");
			}

			@Override
			public void guardar() {
				if (validar()) {
					Date desde = dtbDesde.getValue();
					Date hasta = dtbHasta.getValue();
					DateFormat fecha = new SimpleDateFormat("dd-MM-yyyy");
					String fecha1 = fecha.format(desde);
					String fecha2 = fecha.format(hasta);
					String diagnostico = cmbDiagnostico.getValue();
					String tipoReporte = cmbTipo.getValue();
					Area area = null;
					if (!cmbArea.getValue().equals("TODOS"))
						area = servicioArea.buscar(Long.parseLong(cmbArea
								.getSelectedItem().getContext()));
					List<ConsultaDiagnostico> consultas = new ArrayList<ConsultaDiagnostico>();
					if (diagnostico.equals("TODOS"))
						if (area == null)
							consultas = servicioConsultaDiagnostico
									.buscarAccidenteEntreFechas(desde, hasta);
						else
							consultas = servicioConsultaDiagnostico
									.buscarAccidenteEntreFechasYArea(desde,
											hasta, area);
					else {
						if (area == null)
							consultas = servicioConsultaDiagnostico
									.buscarAccidenteEntreFechasYTipo(desde,
											hasta, diagnostico);
						else
							consultas = servicioConsultaDiagnostico
									.buscarAccidenteEntreFechasAreaYTipo(desde,
											hasta, area, diagnostico);
					}
					long idArea = 0;
					if (area != null)
						idArea = area.getIdArea();
					if (!consultas.isEmpty())
						Clients.evalJavaScript("window.open('"
								+ damePath()
								+ "Reportero?valor=19&valor6="
								+ fecha1
								+ "&valor7="
								+ fecha2
								+ "&valor2="
								+ String.valueOf(idArea)
								+ "&valor9="
								+ cmbDiagnostico.getValue()
								+ "&valor20="
								+ tipoReporte
								+ "','','top=100,left=200,height=600,width=800,scrollbars=1,resizable=1')");
					else
						Mensaje.mensajeAlerta(Mensaje.noHayRegistros);
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
		botoneraCosto.appendChild(botonera);

	}

	protected boolean validar() {
		if (cmbArea.getText().compareTo("") == 0
				|| cmbDiagnostico.getText().compareTo("") == 0) {
			Mensaje.mensajeError(Mensaje.camposVacios);
			return false;
		} else
			return true;
	}

	private void cargarCombo() {
		String todos = "TODOS";
		Area area = new Area();
		area.setNombre(todos);
		area.setIdArea(0);
		List<Area> areas = new ArrayList<Area>();
		areas.add(area);
		areas.addAll(servicioArea.buscarTodos());
		cmbArea.setModel(new ListModelList<Area>(areas));
	}

	public byte[] reporteCosto(String par6, String par7, Long part2, String par9, String tipoReporte) {

		byte[] fichero = null;
		SimpleDateFormat formato = new SimpleDateFormat("dd-MM-yyyy");
		Date fecha1 = null;
		try {
			fecha1 = formato.parse(par6);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Date fecha2 = null;
		try {
			fecha2 = formato.parse(par7);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		fecha2 = agregarDia(fecha2);
		Area area = getServicioArea().buscar(part2);
		String diagnostico = par9;
		List<ConsultaDiagnostico> consultas = new ArrayList<ConsultaDiagnostico>();
		if (diagnostico.equals("TODOS"))
			if (area == null)
				consultas = getServicioConsultaDiagnostico()
						.buscarAccidenteEntreFechas(fecha1, fecha2);
			else
				consultas = getServicioConsultaDiagnostico()
						.buscarAccidenteEntreFechasYArea(fecha1, fecha2, area);
		else {
			if (area == null)
				consultas = getServicioConsultaDiagnostico()
						.buscarAccidenteEntreFechasYTipo(fecha1, fecha2,
								diagnostico);
			else
				consultas = getServicioConsultaDiagnostico()
						.buscarAccidenteEntreFechasAreaYTipo(fecha1, fecha2,
								area, diagnostico);
		}
		List<ConsultaDiagnostico> consultasFinales = new ArrayList<ConsultaDiagnostico>();
		long codigoConsulta = consultas.get(0).getConsulta().getIdConsulta();
		boolean primero = true;
		for (int i = 0; i < consultas.size(); i++) {
			if (codigoConsulta != consultas.get(i).getConsulta()
					.getIdConsulta()) {
				codigoConsulta = consultas.get(i).getConsulta().getIdConsulta();
				Consulta consulta = consultas.get(i).getConsulta();
				double costoMedicinas = 0, costoExamenes, costoEspecialistas, costoEstudios, costoConsultas;
				costoExamenes = getServicioConsultaExamen().sumPorConsulta(
						consulta);
				costoMedicinas = getServicioConsultaMedicina().costoPorConsulta(consulta);
				costoEspecialistas = getServicioConsultaEspecialista()
						.sumPorConsulta(consulta);
				costoEstudios = getServicioConsultaServicioExterno()
						.sumPorConsulta(consulta);
				consultas.get(i).getConsulta().setEstatura(costoMedicinas);
				consultas.get(i).getConsulta().setPeso(costoExamenes);
				consultas.get(i).getConsulta()
						.setPerimetroForzada(costoEspecialistas);
				consultas.get(i).getConsulta()
						.setPerimetroOmbligo(costoEstudios);
				consultasFinales.add(consultas.get(i));
			}
			if (primero) {
				primero = false;
				Consulta consulta = consultas.get(i).getConsulta();
				double costoMedicinas = 0, costoExamenes, costoEspecialistas, costoEstudios, costoConsultas;
				costoExamenes = getServicioConsultaExamen().sumPorConsulta(
						consulta);
				costoEspecialistas = getServicioConsultaEspecialista()
						.sumPorConsulta(consulta);
				costoEstudios = getServicioConsultaServicioExterno()
						.sumPorConsulta(consulta);
				costoMedicinas = getServicioConsultaMedicina().costoPorConsulta(consulta);
				consultas.get(i).getConsulta().setEstatura(costoMedicinas);
				consultas.get(i).getConsulta().setPeso(costoExamenes);
				consultas.get(i).getConsulta()
						.setPerimetroForzada(costoEspecialistas);
				consultas.get(i).getConsulta()
						.setPerimetroOmbligo(costoEstudios);
				consultasFinales.add(consultas.get(i));
			}

		}

		Map p = new HashMap();
		if (area == null)
			p.put("area", "Todas");
		else
			p.put("area", area.getNombre());
		p.put("diagnostico", diagnostico);
		p.put("desde", par6);
		p.put("hasta", par7);
		JasperReport reporte = null;
		try {
			reporte = (JasperReport) JRLoader.loadObject(getClass()
					.getResource("/reporte/medico/resumen/RResumenCosto.jasper"));
		} catch (JRException e) {
			Mensaje.mensajeError("Recurso no Encontrado");
		}
		if (tipoReporte.equals("EXCEL")) {

			JasperPrint jasperPrint = null;
			try {
				jasperPrint = JasperFillManager.fillReport(reporte, p,
						new JRBeanCollectionDataSource(consultasFinales));
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
		try {
			fichero = JasperRunManager.runReportToPdf(reporte, p,
					new JRBeanCollectionDataSource(consultasFinales));
		} catch (JRException e) {
			Mensaje.mensajeError("Error en Reporte");
		}
		return fichero;
		}
	}
}
