package controlador.medico;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import modelo.medico.consulta.Consulta;
import modelo.medico.consulta.ConsultaDiagnostico;
import modelo.medico.consulta.ConsultaEspecialista;
import modelo.medico.consulta.ConsultaExamen;
import modelo.medico.consulta.ConsultaServicioExterno;
import modelo.medico.maestro.Especialista;
import modelo.medico.maestro.Paciente;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.JasperRunManager;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.util.JRLoader;

import org.zkoss.zk.ui.Sessions;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zk.ui.util.Clients;
import org.zkoss.zul.Button;
import org.zkoss.zul.Div;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

import componente.Botonera;
import componente.Mensaje;
import controlador.utils.CGenerico;

public class CResultado extends CGenerico {

	private static final long serialVersionUID = 4445427066505752688L;
	@Wire
	private Window wdwResultado;
	@Wire
	private Listbox ltbExamenes;
	@Wire
	private Listbox ltbEspecialistas;
	@Wire
	private Listbox ltbServicio;
	@Wire
	private Textbox txtObservacion;
	@Wire
	private Div botoneraResultado;
	@Wire
	private Button btnReporteResultados;
	long idConsulta = 0;
	Consulta consulta = new Consulta();
	List<ConsultaServicioExterno> listaConsultaServicio = new ArrayList<ConsultaServicioExterno>();
	List<ConsultaExamen> listaConsultaExamen = new ArrayList<ConsultaExamen>();
	List<ConsultaEspecialista> listaEspecialistas = new ArrayList<ConsultaEspecialista>();

	@Override
	public void inicializar() throws IOException {
		HashMap<String, Object> map = (HashMap<String, Object>) Sessions
				.getCurrent().getAttribute("consultaResultado");
		if (map != null) {
			if (map.get("idConsulta") != null) {
				idConsulta = (long) map.get("idConsulta");
				consulta = servicioConsulta.buscar(idConsulta);
				txtObservacion.setValue(consulta.getObservacion());
				List<ConsultaEspecialista> listaEspecialista = servicioConsultaEspecialista
						.buscarPorConsulta(consulta);
				listaEspecialistas.addAll(listaEspecialista);
				boolean resultado = false;
				for (int i = 0; i < listaEspecialista.size(); i++) {
					if (listaEspecialista.get(i).getResultado() != null)
						resultado = true;
				}
				ltbEspecialistas
						.setModel(new ListModelList<ConsultaEspecialista>(
								listaEspecialista));
				listaConsultaExamen = servicioConsultaExamen
						.buscarPorConsulta(consulta);
				for (int i = 0; i < listaConsultaExamen.size(); i++) {
					if (listaConsultaExamen.get(i).getResultado() != null)
						resultado = true;
				}
				ltbExamenes.setModel(new ListModelList<ConsultaExamen>(
						listaConsultaExamen));
				listaConsultaServicio = servicioConsultaServicioExterno
						.buscarPorConsulta(consulta);
				for (int i = 0; i < listaConsultaServicio.size(); i++) {
					if (listaConsultaServicio.get(i).getResultado() != null)
						resultado = true;
				}
				ltbServicio
						.setModel(new ListModelList<ConsultaServicioExterno>(
								listaConsultaServicio));
				if (resultado)
					btnReporteResultados.setVisible(true);
				map.clear();
				map = null;
			}
		}

		Botonera botonera = new Botonera() {

			@Override
			public void salir() {
				limpiar();
				wdwResultado.onClose();
			}

			@Override
			public void limpiar() {
				listaConsultaExamen.clear();
				listaConsultaServicio.clear();
				listaEspecialistas.clear();
			}

			@Override
			public void guardar() {
				if (!listaConsultaExamen.isEmpty()
						|| !listaConsultaServicio.isEmpty()
						|| !listaEspecialistas.isEmpty()) {
					for (int i = 0; i < listaConsultaExamen.size(); i++) {
						Listitem listItem = ltbExamenes.getItemAtIndex(i);
						String resultado = ((Textbox) ((listItem.getChildren()
								.get(1))).getFirstChild()).getValue();
						listaConsultaExamen.get(i).setResultado(resultado);
					}
					servicioConsultaExamen.guardar(listaConsultaExamen);
					for (int i = 0; i < listaConsultaServicio.size(); i++) {
						Listitem listItem = ltbServicio.getItemAtIndex(i);
						String resultado = ((Textbox) ((listItem.getChildren()
								.get(2))).getFirstChild()).getValue();
						listaConsultaServicio.get(i).setResultado(resultado);
					}
					servicioConsultaServicioExterno
							.guardar(listaConsultaServicio);
					for (int i = 0; i < listaEspecialistas.size(); i++) {
						Listitem listItem = ltbEspecialistas.getItemAtIndex(i);
						String resultado = ((Textbox) ((listItem.getChildren()
								.get(2))).getFirstChild()).getValue();
						listaEspecialistas.get(i).setResultado(resultado);
					}
					servicioConsultaEspecialista.guardar(listaEspecialistas);
					consulta.setObservacion(txtObservacion.getValue());
					servicioConsulta.guardar(consulta);
					Mensaje.mensajeInformacion("Resultados guardados correctamente, ahora puede generar el reporte respectivo de los resultados");
					btnReporteResultados.setVisible(true);
				} else
					Mensaje.mensajeAlerta("No existen resultados que guardar");

			}

			@Override
			public void eliminar() {
				// TODO Auto-generated method stub

			}
		};
		botonera.getChildren().get(1).setVisible(false);
		botonera.getChildren().get(2).setVisible(false);
		botoneraResultado.appendChild(botonera);
	}

	@Listen("onClick = #btnReporteResultados")
	public void generarReporteResultadoConsulta() {
		Long id = consulta.getIdConsulta();
		Clients.evalJavaScript("window.open('"
				+ damePath()
				+ "Reportero?valor=50&valor2="
				+ id
				+ "','','top=100,left=200,height=600,width=800,scrollbars=1,resizable=1')");
	}

	public byte[] reporteResultado(Long part2) throws JRException {
		byte[] fichero = null;
		Consulta consuta = getServicioConsulta().buscar(part2);
		List<ConsultaDiagnostico> diagnosticoConsulta = getServicioConsultaDiagnostico()
				.buscarPorConsulta(consuta);

		List<ConsultaExamen> examenes = getServicioConsultaExamen()
				.buscarPorConsulta(consuta);
		List<ConsultaEspecialista> especialistas = getServicioConsultaEspecialista()
				.buscarPorConsulta(consuta);
		List<ConsultaServicioExterno> estudis = getServicioConsultaServicioExterno()
				.buscarPorConsulta(consuta);
		String nombreDiagnostico = "";
		String tipoDiagnostico = "";
		if (!diagnosticoConsulta.isEmpty()) {
			nombreDiagnostico = diagnosticoConsulta.get(0).getDiagnostico()
					.getNombre();
			tipoDiagnostico = diagnosticoConsulta.get(0).getTipo();
		}

		Paciente paciente = consuta.getPaciente();
		Map<String, Object> p = new HashMap<String, Object>();
		String nombreEmpresa = paciente.getEmpresa().getNombre();
		String direccionEmpresa = paciente.getEmpresa().getDireccionCentro();
		String rifEmpresa = paciente.getEmpresa().getRif();
		if (paciente.getEmpresa() != null) {
			nombreEmpresa = paciente.getEmpresa().getNombre();
			direccionEmpresa = paciente.getEmpresa().getDireccionCentro();
			rifEmpresa = paciente.getEmpresa().getRif();
		}
		p.put("empresaNombre", nombreEmpresa);
		p.put("empresaDireccion", direccionEmpresa);
		p.put("empresaRif", rifEmpresa);
		p.put("pacienteNombre",
				paciente.getPrimerNombre() + "   "
						+ paciente.getSegundoNombre());
		p.put("pacienteApellido", paciente.getPrimerApellido() + "   "
				+ paciente.getSegundoApellido());
		p.put("pacienteCedula", paciente.getCedula());
		p.put("pacienteNacimiento", paciente.getFechaNacimiento());
		p.put("doctorNombre", consuta.getDoctor());
		p.put("fechaConsulta", consuta.getFechaConsulta());
		p.put("tipoConsulta", consuta.getTipoConsultaSecundaria());
		p.put("enfermedad", consuta.getEnfermedadActual());
		p.put("diagnostico", nombreDiagnostico);
		p.put("tipoDiagnostico", tipoDiagnostico);
		p.put("edad",
				String.valueOf(calcularEdad(paciente.getFechaNacimiento())));
		p.put("dataExamen", new JRBeanCollectionDataSource(examenes));
		p.put("dataEspecialista", new JRBeanCollectionDataSource(especialistas));
		p.put("dataEstudio", new JRBeanCollectionDataSource(estudis));

		JasperReport reporte = (JasperReport) JRLoader
				.loadObject(getClass()
						.getResource(
								"/reporte/medico/consulta/resultado/RResultadosConsulta.jasper"));

		fichero = JasperRunManager.runReportToPdf(reporte, p,
				new JRBeanCollectionDataSource(diagnosticoConsulta));
		return fichero;
	}

}
