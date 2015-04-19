package controlador.reporte;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import modelo.medico.consulta.Consulta;
import modelo.medico.consulta.ConsultaDiagnostico;
import modelo.medico.maestro.Paciente;
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
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zk.ui.util.Clients;
import org.zkoss.zul.Button;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Div;
import org.zkoss.zul.Groupbox;
import org.zkoss.zul.Label;
import org.zkoss.zul.Radio;
import org.zkoss.zul.Radiogroup;
import org.zkoss.zul.Row;
import org.zkoss.zul.Spinner;
import org.zkoss.zul.Tab;

import componente.Botonera;
import componente.Catalogo;
import componente.Mensaje;
import controlador.utils.CGenerico;

public class CHistorial extends CGenerico {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Wire
	private Div divHistorial;
	@Wire
	private Div botoneraHistorial;
	@Wire
	private Label lblTrabajador;
	@Wire
	private Div divCatalogoTrabajador;
	@Wire
	private Combobox cmbTipo;
	@Wire
	private Datebox dtbDesde;
	@Wire
	private Datebox dtbHasta;
	@Wire
	private Groupbox grx;

	private String tipo = "";
	private String idTrabajador = null;
	Catalogo<Paciente> catalogo;

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
		switch (titulo) {
		case "Historial de Peso":
			tipo = "1";
			break;
		case "Historial de Cargos y Areas":
			tipo = "2";
			break;
		}
		grx.setTitle(titulo);
		Botonera botonera = new Botonera() {

			@Override
			public void salir() {
				cerrarVentana(divHistorial, titulo, tabs);
			}

			@Override
			public void limpiar() {
				dtbDesde.setValue(fecha);
				dtbHasta.setValue(fecha);
				cmbTipo.setValue("PDF");
				idTrabajador = null;
				lblTrabajador.setValue("");
			}

			@Override
			public void guardar() {
				if (validar()) {
					Date desde = dtbDesde.getValue();
					Date hasta = dtbHasta.getValue();
					String tipoReporte = cmbTipo.getValue();
					String fecha1 = formatoFecha.format(desde);
					String fecha2 = formatoFecha.format(hasta);
					new ArrayList<Consulta>();
					int reporte = 0;
					switch (tipo) {
					// Reporte 1
					case "1":
						reporte = 48;
						break;
					// Reporte 2
					case "2":
						reporte = 49;
						break;
					}
					if (idTrabajador.equals("TODOS"))
						idTrabajador = "%";
					List<Consulta> consultas = servicioConsulta
							.buscarEntreFechasYPacienteLike(desde, hasta,
									idTrabajador);
					if (!consultas.isEmpty()) {
						if (idTrabajador.equals("%"))
							idTrabajador = "TODOS";
						Clients.evalJavaScript("window.open('"
								+ damePath()
								+ "Reportero?valor="
								+ String.valueOf(reporte)
								+ "&valor6="
								+ fecha1
								+ "&valor7="
								+ fecha2
								+ "&valor8="
								+ idTrabajador
								+ "&valor20="
								+ tipoReporte
								+ "','','top=100,left=200,height=600,width=800,scrollbars=1,resizable=1')");
					} else
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
		guardar.setImage("/public/imagenes/botones/reporte.png");
		botonera.getChildren().get(1).setVisible(false);
		botoneraHistorial.appendChild(botonera);
	}

	protected boolean validar() {
		if (idTrabajador == null) {
			Mensaje.mensajeError(Mensaje.camposVacios);
			return false;
		}
		return true;
	}

	@Listen("onClick = #btnBuscarTrabajador")
	public void mostrarCatalogoFamiliar() {
		final List<Paciente> pacientes = new ArrayList<Paciente>();
		Paciente pacienteT = new Paciente();
		pacienteT.setCedula("TODOS");
		pacienteT.setFicha("TODOS");
		pacienteT.setPrimerNombre("TODOS");
		pacienteT.setPrimerApellido("TODOS");
		pacientes.add(pacienteT);
		pacientes.addAll(servicioPaciente.buscarTodosTrabajadores());

		catalogo = new Catalogo<Paciente>(divCatalogoTrabajador,
				"Catalogo de Pacientes", pacientes, false, "Cedula", "Ficha",
				"Primer Nombre", "Segundo Nombre", "Primer Apellido",
				"Primer Apellido") {

			@Override
			protected List<Paciente> buscar(String valor, String combo) {

				switch (combo) {
				case "Primer Nombre":
					return servicioPaciente.filtroNombre1T(valor);
				case "Segundo Nombre":
					return servicioPaciente.filtroNombre2T(valor);
				case "Cedula":
					return servicioPaciente.filtroCedulaT(valor);
				case "Ficha":
					return servicioPaciente.filtroFichaT(valor);
				case "Primer Apellido":
					return servicioPaciente.filtroApellido1T(valor);
				case "Segundo Apellido":
					return servicioPaciente.filtroApellido2T(valor);
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
		catalogo.setParent(divCatalogoTrabajador);
		catalogo.doModal();
	}

	@Listen("onSeleccion = #divCatalogoTrabajador")
	public void seleccinarTrabajador() {
		Paciente trabajador = catalogo.objetoSeleccionadoDelCatalogo();
		idTrabajador = trabajador.getCedula();
		lblTrabajador.setValue(trabajador.getPrimerNombre() + " "
				+ trabajador.getPrimerApellido());
		catalogo.setParent(null);
	}

	public byte[] jasperPeso(String par6, String par7, String par8, String tipo2) {
		byte[] fichero = null;
		Date fecha1 = null;
		try {
			fecha1 = formatoFecha.parse(par6);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		Date fecha2 = null;
		try {
			fecha2 = formatoFecha.parse(par7);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		fecha2 = agregarDia(fecha2);

		if (par8.equals("TODOS"))
			par8 = "%";
		List<Consulta> consultas = getServicioConsulta()
				.buscarEntreFechasYPacienteLike(fecha1, fecha2, par8);
		for (Iterator<Consulta> iterator = consultas.iterator(); iterator
				.hasNext();) {
			Consulta consulta = (Consulta) iterator.next();
			Paciente paciente = consulta.getPaciente();
			paciente.setEdad(calcularEdad(paciente.getFechaNacimiento()));
			double peso = consulta.getPeso();
			double estatura = consulta.getEstatura();
			double imc = 0;
			if (estatura != 0) {
				imc = (double) Math.round((peso / (estatura * estatura)) * 100) / 100;
				if (imc < 18.5)
					consulta.setUsuarioAuditoria(imc + " Bajo Peso");
				else {
					if (imc >= 18.5 && imc < 25)
						consulta.setUsuarioAuditoria(imc + " Normal");
					else {
						if (imc >= 25 && imc < 30)
							consulta.setUsuarioAuditoria(imc + " Sobre Peso");
						else {
							if (imc >= 30 && imc < 35)
								consulta.setUsuarioAuditoria(imc
										+ " Obesidad I");
							else {
								if (imc >= 35 && imc < 40)
									consulta.setUsuarioAuditoria(imc
											+ " Obesidad II");
								else
									consulta.setUsuarioAuditoria(imc
											+ " Obesidad III");
							}
						}
					}
				}
			} else
				consulta.setUsuarioAuditoria("Sin Informacion");
		}
		Map<String, Object> p = new HashMap<String, Object>();
		p.put("desde", par6);
		p.put("hasta", par7);
		JasperReport reporte = null;
		try {
			reporte = (JasperReport) JRLoader.loadObject(getClass()
					.getResource(
							"/reporte/medico/historial/RVariacionPeso.jasper"));
		} catch (JRException e1) {
			e1.printStackTrace();
		}
		if (tipo.equals("EXCEL")) {

			JasperPrint jasperPrint = null;
			try {
				jasperPrint = JasperFillManager.fillReport(reporte, p,
						new JRBeanCollectionDataSource(consultas));
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
						new JRBeanCollectionDataSource(consultas));
			} catch (JRException e) {
				e.printStackTrace();
			}
			return fichero;
		}
	}

	public byte[] jasperCargo(String par6, String par7, String par8,
			String tipo2) {
		byte[] fichero = null;
		Date fecha1 = null;
		try {
			fecha1 = formatoFecha.parse(par6);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		Date fecha2 = null;
		try {
			fecha2 = formatoFecha.parse(par7);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		fecha2 = agregarDia(fecha2);

		if (par8.equals("TODOS"))
			par8 = "%";
		List<Consulta> consultas = getServicioConsulta()
				.buscarEntreFechasYPacienteLike(fecha1, fecha2, par8);
		String area = consultas.get(0).getAreaActual();
		String cargo = consultas.get(0).getCargoActual();
		String paciente = consultas.get(0).getPaciente().getCedula();
		for (int i = 1; i < consultas.size(); i++) {
			Consulta consulta = consultas.get(i);
			if (paciente.equals(consulta.getPaciente().getCedula())) {
				if (area.equals(consulta.getAreaActual())
						&& cargo.equals(consulta.getCargoActual())) {
					consultas.remove(i);
					i--;
				} else {
					area = consulta.getAreaActual();
					cargo = consulta.getCargoActual();
				}
			} else {
				paciente = consulta.getPaciente().getCedula();
				area = consulta.getAreaActual();
				cargo = consulta.getCargoActual();
			}
		}
		Map<String, Object> p = new HashMap<String, Object>();
		p.put("desde", par6);
		p.put("hasta", par7);
		JasperReport reporte = null;
		try {
			reporte = (JasperReport) JRLoader
					.loadObject(getClass().getResource(
							"/reporte/medico/historial/RVariacionCargo.jasper"));
		} catch (JRException e1) {
			e1.printStackTrace();
		}
		if (tipo.equals("EXCEL")) {

			JasperPrint jasperPrint = null;
			try {
				jasperPrint = JasperFillManager.fillReport(reporte, p,
						new JRBeanCollectionDataSource(consultas));
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
						new JRBeanCollectionDataSource(consultas));
			} catch (JRException e) {
				e.printStackTrace();
			}
			return fichero;
		}
	}

}
