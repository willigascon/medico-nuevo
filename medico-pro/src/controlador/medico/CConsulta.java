package controlador.medico;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.imageio.ImageIO;

import modelo.generico.DetalleAccidente;
import modelo.medico.consulta.Consulta;
import modelo.medico.consulta.ConsultaDiagnostico;
import modelo.medico.consulta.ConsultaEspecialista;
import modelo.medico.consulta.ConsultaExamen;
import modelo.medico.consulta.ConsultaMedicina;
import modelo.medico.consulta.ConsultaParteCuerpo;
import modelo.medico.consulta.ConsultaServicioExterno;
import modelo.medico.consulta.Recipe;
import modelo.medico.maestro.Cita;
import modelo.medico.maestro.Diagnostico;
import modelo.medico.maestro.DoctorInterno;
import modelo.medico.maestro.Especialista;
import modelo.medico.maestro.Examen;
import modelo.medico.maestro.Medicina;
import modelo.medico.maestro.Paciente;
import modelo.medico.maestro.ParteCuerpo;
import modelo.medico.maestro.Proveedor;
import modelo.medico.maestro.ProveedorExamen;
import modelo.medico.maestro.ProveedorServicio;
import modelo.medico.maestro.ServicioExterno;
import modelo.organizacion.Area;
import modelo.organizacion.Cargo;
import modelo.security.Arbol;
import modelo.security.Usuario;
import modelo.seguridad.Accidente;
import modelo.seguridad.GrupoInspectores;
import modelo.seguridad.Informe;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.JasperRunManager;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.util.JRLoader;

import org.json.JSONException;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zk.ui.util.Clients;
import org.zkoss.zul.Button;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Div;
import org.zkoss.zul.Doublespinner;
import org.zkoss.zul.Groupbox;
import org.zkoss.zul.Image;
import org.zkoss.zul.Include;
import org.zkoss.zul.Label;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.ListitemRenderer;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Radio;
import org.zkoss.zul.Row;
import org.zkoss.zul.Spinner;
import org.zkoss.zul.Tab;
import org.zkoss.zul.Tabbox;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.West;
import org.zkoss.zul.Window;

import servicio.medico.maestro.SParteCuerpo;
import componente.Botonera;
import componente.Buscar;
import componente.Catalogo;
import componente.Mensaje;
import componente.Validador;
import controlador.security.CArbol;
import controlador.utils.CGenerico;

public class CConsulta extends CGenerico {

	private static final long serialVersionUID = -6277014704105198573L;
	@Wire
	private Datebox dtbFechaConsulta;
	@Wire
	private Textbox txtBuscadorExamen;
	@Wire
	private Textbox txtBuscadorEspecialista;
	@Wire
	private Textbox txtBuscadorMedicina;
	@Wire
	private Textbox txtBuscadorServicioExterno;
	@Wire
	private Textbox txtBuscadorDiagnostico;
	@Wire
	private Textbox txtCedula;
	@Wire
	private Combobox cmbPrioridad;
	@Wire
	private Datebox dtbValido;
	@Wire
	private Groupbox gpxResumen;
	@Wire
	private Div botoneraConsulta;
	@Wire
	private Button btnBuscarPaciente;
	@Wire
	private Div divCatalogoPacientes;
	@Wire
	private Div divConsulta;
	@Wire
	private Listbox ltbMedicinas;
	@Wire
	private Listbox ltbMedicinasAgregadas;
	@Wire
	private Listbox ltbExamenes;
	@Wire
	private Listbox ltbExamenesAgregados;
	@Wire
	private Listbox ltbDiagnosticos;
	@Wire
	private Listbox ltbDiagnosticosAgregados;
	@Wire
	private Listbox ltbServicioExterno;
	@Wire
	private Listbox ltbServicioExternoAgregados;
	@Wire
	private Listbox ltbEspecialistas;
	@Wire
	private Listbox ltbEspecialistasAgregados;
	@Wire
	private Listbox ltbResumenMedicinas;
	@Wire
	private Listbox ltbResumenDiagnosticos;
	@Wire
	private Listbox ltbResumenExamenes;
	@Wire
	private Listbox ltbResumenEspecialistas;
	@Wire
	private Listbox ltbResumenServicios;
	@Wire
	private Listbox ltbConsultas;
	// .---------------------------
	@Wire
	private Image imagenPaciente;
	@Wire
	private Label lblNombres;
	@Wire
	private Label lblEstado;
	@Wire
	private Label lblCedula;
	@Wire
	private Label lblApellidos;
	@Wire
	private Label lblFicha;
	@Wire
	private Label lblTrabajador;
	@Wire
	private Label lblFechaNac;
	@Wire
	private Label lblLugarNac;
	@Wire
	private Label lblAlergias;
	@Wire
	private Label lblAlergico;
	@Wire
	private Label lblLentes;
	@Wire
	private Label lblEdad;
	@Wire
	private Label lblSexo;
	@Wire
	private Label lblEstadoCivil;
	@Wire
	private Label lblGrupoSanguineo;
	@Wire
	private Label lblMano;
	@Wire
	private Label lblEstatura;
	@Wire
	private Label lblPeso;
	@Wire
	private Label lblDiscapacidad;
	@Wire
	private Label lblOrigen;
	@Wire
	private Label lblTipoDiscapacidad;
	@Wire
	private Label lblObservacionDiscapacidad;
	@Wire
	private Label lblCiudad;
	@Wire
	private Label lblDireccion;
	@Wire
	private Label lblTelefono1;
	@Wire
	private Label lblTelefono2;
	@Wire
	private Label lblCorreo;
	@Wire
	private Label lblEtiquetaOrigen;
	@Wire
	private Label lblEtiquetaTipo;
	@Wire
	private Label lblEtiquetaAlergias;
	@Wire
	private Label lblEtiquetaObservacion;
	@Wire
	private Combobox cmbTipoConsulta;
	@Wire
	private Combobox cmbTipoPreventiva;
	@Wire
	private Textbox txtMotivo;
	@Wire
	private Textbox txtEnfermedad;
	@Wire
	private Label lblPreventiva;
	@Wire
	private Label lblConsulta;
	@Wire
	private Listbox ltbExamenFisico;
	@Wire
	private Label lblIndice;
	@Wire
	private Doublespinner spnPeso;
	@Wire
	private Doublespinner spnEstatura;
	@Wire
	private Doublespinner spnPlena;
	@Wire
	private Doublespinner spnForzada;
	@Wire
	private Doublespinner spnOmbligo;
	@Wire
	private Spinner spnCardiaca;
	@Wire
	private Radio rdoSiRitmico;
	@Wire
	private Radio rdoNoRitmico;
	@Wire
	private Spinner frecuencia11;
	@Wire
	private Spinner frecuencia12;
	@Wire
	private Spinner frecuencia13;
	@Wire
	private Spinner s11;
	@Wire
	private Spinner s12;
	@Wire
	private Spinner s13;
	@Wire
	private Spinner d11;
	@Wire
	private Spinner d12;
	@Wire
	private Spinner d13;
	@Wire
	private Spinner ex11;
	@Wire
	private Spinner ex12;
	@Wire
	private Spinner ex13;
	@Wire
	private Radio rdoSiRitmicoF1;
	@Wire
	private Radio rdoNoRitmicoF1;
	@Wire
	private Radio rdoSiRitmicoF2;
	@Wire
	private Radio rdoNoRitmicoF2;
	@Wire
	private Radio rdoSiRitmicoF3;
	@Wire
	private Radio rdoNoRitmicoF3;
	@Wire
	private Spinner spnFrecuenciaRespitatoria;
	@Wire
	private Radio rdoSiRespiratoria;
	@Wire
	private Radio rdoNoRespiratoria;
	@Wire
	private Radio rdoSiLaboral;
	@Wire
	private Radio rdoNoLaboral;
	// --------------------------
	@Wire
	private Row rowReposoDias;
	@Wire
	private Radio rdoSiReposoDias;
	@Wire
	private Radio rdoNoReposoHoras;
	@Wire
	private Label lblReposo;
	@Wire
	private Row rowEmbarazo;
	@Wire
	private Radio rdoSiMaternal;
	@Wire
	private Radio rdoNoMaternal;
	@Wire
	private Row rowEmbarazo2;
	@Wire
	private Combobox cmbReposo;
	@Wire
	private Row rowReposoFecha;
	@Wire
	private Datebox dtbReposo;
	//
	@Wire
	private Radio rdoSiReposo;
	@Wire
	private Radio rdoNoReposo;
	@Wire
	private Radio rdoSiApto;
	@Wire
	private Radio rdoNoApto;
	@Wire
	private Row row;
	@Wire
	private Row rowPromocion;
	@Wire
	private Textbox txtExamenPreempleo;
	@Wire
	private Combobox cmbCargo;
	@Wire
	private Combobox cmbArea;
	@Wire
	private Spinner spnReposo;
	@Wire
	private Row rowReposo;
	@Wire
	private Row rowPostVacacional;
	@Wire
	private Datebox dtbFechaPostVacacional;
	@Wire
	private Row rowValido;
	@Wire
	private Label lblCargo1;
	@Wire
	private Label lblArea;
	@Wire
	private Tab tabResumen;
	@Wire
	private Tab tabConsulta;
	@Wire
	private Row rowApto;
	@Wire
	private Row rowEspecialista;
	@Wire
	private Row rowAsociada;
	@Wire
	private Label lblEnfermedadAsociada;
	@Wire
	private Label lblEnfermedadAsociada2;
	@Wire
	private Label lblDiagnosticoAsociado;
	@Wire
	private Label lblDiagnosticoAsociado2;
	@Wire
	private Row rowApto2;
	@Wire
	private Textbox txtCondicionado;
	@Wire
	private Label lblPreventivaArea;
	@Wire
	private Combobox cmbTratamiento;
	//
	Botonera botonera;
	@Wire
	private Div botoneraConsultaGeneral;
	@Wire
	private Div catalogoConsulta;
	@Wire
	private Div divCatalogoOrden;
	@Wire
	private Div divCatalogoPacientes2;
	@Wire
	private Button btnGenerarRecipe;
	@Wire
	private Button btnGenerarOrden;
	@Wire
	private Button btnGenerarReferencia;
	@Wire
	private Button btnGenerarOrdenServicios;
	@Wire
	private Button btnGenerarReposo;
	@Wire
	private Button btnPreEmpleo;
	@Wire
	private Combobox cmbPrioridadServicio;
	@Wire
	private Combobox cmbPrioridadEspecialista;
	@Wire
	private Combobox cmbPrioridadExamen;
	@Wire
	private Combobox cmbEspecialista;
	@Wire
	private Button btnConstancia;
	@Wire
	private Textbox txtCedulaCita;
	@Wire
	private Button btnBuscarPacienteCita;
	// -----------------------------
	@Wire("#wdwRegistro")
	private Window wdwRegistro;
	@Wire
	private Tab tabIdentificacion;
	//
	List<Listbox> listas = new ArrayList<Listbox>();

	List<Medicina> medicinasDisponibles = new ArrayList<Medicina>();
	List<ConsultaMedicina> medicinasAgregadas = new ArrayList<ConsultaMedicina>();
	List<ConsultaMedicina> medicinasResumen = new ArrayList<ConsultaMedicina>();

	List<Diagnostico> diagnosticosDisponibles = new ArrayList<Diagnostico>();
	List<ConsultaDiagnostico> diagnosticosAgregados = new ArrayList<ConsultaDiagnostico>();
	List<ConsultaDiagnostico> diagnosticosResumen = new ArrayList<ConsultaDiagnostico>();

	List<Examen> examenesDisponibles = new ArrayList<Examen>();
	List<ConsultaExamen> examenesAgregado = new ArrayList<ConsultaExamen>();
	List<ConsultaExamen> examenesResumen = new ArrayList<ConsultaExamen>();

	List<Especialista> especialistasDisponibles = new ArrayList<Especialista>();
	List<ConsultaEspecialista> especialistasAgregados = new ArrayList<ConsultaEspecialista>();
	List<ConsultaEspecialista> especialistasResumen = new ArrayList<ConsultaEspecialista>();

	List<ServicioExterno> serviciosDisponibles = new ArrayList<ServicioExterno>();
	List<ConsultaServicioExterno> serviciosAgregados = new ArrayList<ConsultaServicioExterno>();
	List<ConsultaServicioExterno> serviciosResumen = new ArrayList<ConsultaServicioExterno>();

	String idPaciente = "";
	long idConsulta = 0;
	long idConsultaAsociada = 0;

	Catalogo<Paciente> catalogoPaciente;
	Catalogo<Paciente> catalogoPaciente2;
	Catalogo<Consulta> catalogo;
	ListModelList<Proveedor> proveedores;
	ListModelList<ParteCuerpo> modelFisico;
	ListitemRenderer<?> renderer;
	Buscar<Medicina> buscarMedicina;
	Buscar<Diagnostico> buscarDiagnostico;
	Buscar<Examen> buscarExamen;
	Buscar<Especialista> buscarEspecialista;
	Buscar<ServicioExterno> buscarServicio;
	private CArbol cArbol = new CArbol();
	String cambio;
	private String[] consultaPreventiva = { "Pre-Empleo", "Pre-Vacacional",
			"Post-Vacacional", "Egreso", "Cambio de Puesto", "Promocion",
			"Reintegro", "Rutina Anual" };
	private String[] consultaCurativa = { "Primera", "Control", "IC" };
	private List<DetalleAccidente> listaDetalle = new ArrayList<DetalleAccidente>();
	private String idBoton = "";
	private long idCita = 0;

	@Override
	public void inicializar() throws IOException {
		contenido = (Include) divConsulta.getParent();
		Tabbox tabox = (Tabbox) divConsulta.getParent().getParent().getParent()
				.getParent();
		tabBox = tabox;
		tab = (Tab) tabox.getTabs().getLastChild();
		HashMap<String, Object> mapa = (HashMap<String, Object>) Sessions
				.getCurrent().getAttribute("mapaGeneral");
		if (mapa != null) {
			if (mapa.get("tabsGenerales") != null) {
				tabs = (List<Tab>) mapa.get("tabsGenerales");
				west = (West) mapa.get("west");
				titulo = (String) mapa.get("titulo");
				mapa.clear();
				mapa = null;
			}
		}
		listas.add(ltbConsultas);
		listas.add(ltbDiagnosticos);
		listas.add(ltbDiagnosticosAgregados);
		listas.add(ltbEspecialistas);
		listas.add(ltbEspecialistasAgregados);
		listas.add(ltbExamenes);
		listas.add(ltbExamenesAgregados);
		listas.add(ltbMedicinas);
		listas.add(ltbMedicinasAgregadas);
		listas.add(ltbServicioExterno);
		listas.add(ltbServicioExternoAgregados);
		listas.add(ltbExamenFisico);
		llenarListas();
		listasMultiples();
		buscadorMedicina();
		buscadorDiagnostico();
		buscadorServicio();
		buscadorExamen();
		buscadorEspecialista();
		cmbArea.setModel(new ListModelList<Area>(servicioArea.buscarTodos()));
		cmbCargo.setModel(new ListModelList<>(servicioCargo.buscarTodos()));
		botonera = new Botonera() {

			@Override
			public void salir() {
			}

			@Override
			public void limpiar() {
			}

			@Override
			public void guardar() {
				if (validar()) {
					if (idConsulta != 0) {
						Consulta consulta = servicioConsulta.buscar(idConsulta);
						servicioConsultaExamen
								.borrarExamenesDeConsulta(consulta);
						servicioConsultaMedicina
								.borrarMedicinasDeConsulta(consulta);
						servicioConsultaDiagnostico
								.borrarDiagnosticosDeConsulta(consulta);
						servicioConsultaEspecialista
								.borrarEspecialistasDeConsulta(consulta);
						servicioConsultaServicioExterno
								.borrarServiciosDeConsulta(consulta);
					}
					if (!rowAsociada.isVisible()) {
						idConsultaAsociada = 0;
					}
					Date fechaCon = dtbFechaConsulta.getValue();
					Timestamp fechaConsulta = new Timestamp(fechaCon.getTime());
					DoctorInterno usuario = usuarioSesion(nombreUsuarioSesion())
							.getDoctorInterno();
					Paciente paciente = servicioPaciente
							.buscarPorCedula(idPaciente);
					boolean accidente = false;
					if (rdoSiLaboral.isChecked())
						accidente = true;
					String motivo = txtMotivo.getValue();
					String enfermedad = txtEnfermedad.getValue();
					String tipo = "";
					String condicionApto = txtCondicionado.getValue();
					if (cmbTipoConsulta.getValue().equals("Preventiva"))
						tipo = cmbTipoConsulta.getValue();
					else
						tipo = cmbTipoConsulta.getValue();
					double peso = spnPeso.getValue();
					double estatura = spnEstatura.getValue();
					double ombligo = spnOmbligo.getValue();
					double plena = spnPlena.getValue();
					double forzada = spnForzada.getValue();
					int frecuencia = spnCardiaca.getValue();
					int frecuencia1 = frecuencia11.getValue();
					int frecuencia2 = frecuencia12.getValue();
					int frecuencia3 = frecuencia13.getValue();
					int sistolica1 = s11.getValue();
					int sistolica2 = s12.getValue();
					int sistolica3 = s13.getValue();
					int diastolica1 = d11.getValue();
					int diastolica2 = d12.getValue();
					int diastolica3 = d13.getValue();
					int extra1 = ex11.getValue();
					int extra2 = ex12.getValue();
					int extra3 = ex13.getValue();
					boolean ritmico = false;
					if (rdoSiRitmico.isChecked())
						ritmico = true;
					boolean ritmico1 = false;
					if (rdoSiRitmicoF1.isChecked())
						ritmico1 = true;
					boolean ritmico2 = false;
					if (rdoSiRitmicoF2.isChecked())
						ritmico2 = true;
					boolean ritmico3 = false;
					if (rdoSiRitmicoF3.isChecked())
						ritmico3 = true;
					boolean reposo = false;
					if (rdoSiReposo.isChecked())
						reposo = true;
					boolean apto = false;
					if (rdoSiApto.isChecked())
						apto = true;

					String tipoSecundaria = cmbTipoPreventiva.getValue();
					String examenesPre = txtExamenPreempleo.getValue();
					Cargo cargoDeseado = new Cargo();
					if (cmbCargo.getSelectedItem() != null) {
						cargoDeseado = servicioCargo.buscar(Long
								.parseLong(cmbCargo.getSelectedItem()
										.getContext()));
					} else
						cargoDeseado = null;

					Area areaDeseado = new Area();
					if (cmbArea.getSelectedItem() != null) {
						areaDeseado = servicioArea.buscar(Long
								.parseLong(cmbArea.getSelectedItem()
										.getContext()));
					} else
						areaDeseado = null;

					Especialista especialista = new Especialista();
					if (cmbEspecialista.getSelectedItem() != null) {
						especialista = servicioEspecialista
								.buscar(cmbEspecialista.getSelectedItem()
										.getContext());
					} else
						especialista = null;

					int dias = spnReposo.getValue();
					if (tipoSecundaria.equals("Egreso"))
						inhabilitarTrabajadorYTodosFamiliares(paciente);
					String doctorGuardar = "Elaborado por usuario No Doctor";
					if (usuario != null)
						doctorGuardar = usuario.getPrimerNombre() + " "
								+ usuario.getPrimerApellido();
					if (cmbTipoPreventiva.getValue().equals("IC"))
						doctorGuardar = cmbEspecialista.getValue();
					String tipoReposo = "";
					if (rdoNoReposoHoras.isChecked())
						tipoReposo = "Horas";
					if (rdoSiReposoDias.isChecked())
						tipoReposo = "Dias";

					Timestamp fechaReposo = new Timestamp(dtbReposo.getValue()
							.getTime());

					Integer frecuenciaRespiratoria = spnFrecuenciaRespitatoria
							.getValue();
					boolean ritmicaRespiratoria = true;
					if (rdoNoRespiratoria.isChecked())
						ritmicaRespiratoria = false;
					String reposoEmbarazo = cmbReposo.getValue();
					Consulta consulta = new Consulta(idConsulta, paciente,
							usuario, fechaConsulta, metodoHora(), metodoHora(),
							metodoFecha(), nombreUsuarioSesion(), accidente,
							motivo, tipo, enfermedad, idConsultaAsociada,
							estatura, peso, ombligo, plena, forzada,
							frecuencia, frecuencia1, frecuencia2, frecuencia3,
							sistolica1, sistolica2, sistolica3, diastolica1,
							diastolica2, diastolica3, extra1, extra2, extra3,
							ritmico, ritmico1, ritmico2, ritmico3,
							paciente.getCargoReal(), cargoDeseado,
							paciente.getArea(), areaDeseado, apto, reposo,
							tipoSecundaria, examenesPre, dias, condicionApto,
							doctorGuardar, especialista, tipoReposo,
							reposoEmbarazo, fechaReposo,
							frecuenciaRespiratoria, ritmicaRespiratoria,
							paciente.getArea().getNombre(), paciente
									.getCargoReal().getNombre());
					Timestamp fechaPost = new Timestamp(dtbFechaPostVacacional
							.getValue().getTime());
					consulta.setFechaPostVacacional(fechaPost);
					servicioConsulta.guardar(consulta);
					Consulta consultaDatos = new Consulta();
					if (idConsulta != 0)
						consultaDatos = servicioConsulta.buscar(idConsulta);
					else
						consultaDatos = servicioConsulta.buscarUltima();
					guardarMedicinas(consultaDatos);
					guardarDiagnosticos(consultaDatos);
					guardarExamenes(consultaDatos);
					guardarEspecialistas(consultaDatos);
					guardarServicios(consultaDatos);
					guardarExamenFisico(consultaDatos);
					Mensaje.mensajeInformacion("Registro Guardado Exitosamente, Ahora puede Generar las Ordenes Respectivas");
					idConsulta = consultaDatos.getIdConsulta();
					tabConsulta.setSelected(true);
					tabResumen.setSelected(true);
					if (consultaDatos.getReposo())
						btnGenerarReposo.setVisible(true);
					btnGenerarOrden.setVisible(true);
					btnGenerarReferencia.setVisible(true);
					btnGenerarOrdenServicios.setVisible(true);
					btnGenerarRecipe.setVisible(true);
					botonera.getChildren().get(0).setVisible(false);
					actualizarConsultas(paciente);
					btnConstancia.setVisible(true);
					if (consulta.getTipoConsultaSecundaria().equals(
							"Pre-Empleo"))
						btnPreEmpleo.setVisible(true);
					if (paciente.getFechaNacimiento() != null)
						paciente.setEdad(calcularEdad(paciente
								.getFechaNacimiento()));
					servicioPaciente.guardar(paciente);
					if (idCita != 0) {
						Cita cita = servicioCita.buscar(idCita);
						cita.setEstado("Finalizada");
						servicioCita.guardar(cita);
					}
					if (tipoSecundaria.equals("Pre-Empleo"))
						if (paciente.getEmail() != null)
							enviarEmailNotificacion(
									paciente.getEmail(),
									"Se le recuerda que debe practicarse la consulta Post-Vacacional, "
											+ "luego de reintegrarse de las vacaciones");
				}
			}

			@Override
			public void eliminar() {
			}
		};

		botonera.getChildren().get(1).setVisible(false);
		botonera.getChildren().get(2).setVisible(false);
		botonera.getChildren().get(3).setVisible(false);
		botoneraConsulta.appendChild(botonera);

		Botonera botoneraGeneral = new Botonera() {

			@Override
			public void salir() {
				cerrarVentana(divConsulta, titulo, tabs);
				west.setOpen(true);
			}

			@Override
			public void limpiar() {
				limpiarCampos();
			}

			@Override
			public void guardar() {
			}

			@Override
			public void eliminar() {
			}
		};
		botoneraGeneral.getChildren().get(0).setVisible(false);
		botoneraGeneral.getChildren().get(1).setVisible(false);
		botoneraConsultaGeneral.appendChild(botoneraGeneral);
		txtCedula.setFocus(true);
	}

	public void actualizarConsultas(Paciente paciente) {
		List<Consulta> consultas = servicioConsulta.buscarPorPaciente(paciente);
		for (int i = 0; i < consultas.size(); i++) {
			Consulta consultaj = consultas.get(i);
			String no = "No";
			if (consultaj.isAccidenteLaboral())
				no = "Si";
			consultaj.setUsuarioAuditoria(no);
		}
		ltbConsultas.setModel(new ListModelList<Consulta>(consultas));
		// ltbConsultas.setMultiple(false);
		ltbConsultas.setCheckmark(false);
		// ltbConsultas.setMultiple(true);
		ltbConsultas.setCheckmark(true);
	}

	private void buscadorEspecialista() {
		buscarEspecialista = new Buscar<Especialista>(ltbEspecialistas,
				txtBuscadorEspecialista) {
			@Override
			protected List<Especialista> buscar(String valor) {
				List<Especialista> presentacionesFiltradas = new ArrayList<Especialista>();
				List<Especialista> presentaciones = servicioEspecialista
						.filtroTodo(valor);
				for (int i = 0; i < especialistasDisponibles.size(); i++) {
					Especialista especialista = especialistasDisponibles.get(i);
					for (int j = 0; j < presentaciones.size(); j++) {
						if (especialista.getCedula().equals(
								presentaciones.get(j).getCedula()))
							presentacionesFiltradas.add(especialista);
					}
				}
				return presentacionesFiltradas;
			}
		};
	}

	private void buscadorExamen() {
		buscarExamen = new Buscar<Examen>(ltbExamenes, txtBuscadorExamen) {

			@Override
			protected List<Examen> buscar(String valor) {
				List<Examen> examenesFiltradas = new ArrayList<Examen>();
				List<Examen> examenes = servicioExamen.filtroNombre(valor);
				for (int i = 0; i < examenesDisponibles.size(); i++) {
					Examen examen = examenesDisponibles.get(i);
					for (int j = 0; j < examenes.size(); j++) {
						if (examen.getIdExamen() == examenes.get(j)
								.getIdExamen())
							examenesFiltradas.add(examen);
					}
				}
				return examenesFiltradas;
			}
		};
	}

	private void buscadorServicio() {
		buscarServicio = new Buscar<ServicioExterno>(ltbServicioExterno,
				txtBuscadorServicioExterno) {

			@Override
			protected List<ServicioExterno> buscar(String valor) {
				List<ServicioExterno> serviciosFiltradas = new ArrayList<ServicioExterno>();
				List<ServicioExterno> servicios = servicioServicioExterno
						.filtroNombre(valor);
				for (int i = 0; i < serviciosDisponibles.size(); i++) {
					ServicioExterno servicio = serviciosDisponibles.get(i);
					for (int j = 0; j < servicios.size(); j++) {
						if (servicio.getIdServicioExterno() == servicios.get(j)
								.getIdServicioExterno())
							serviciosFiltradas.add(servicio);
					}
				}
				return serviciosFiltradas;
			}
		};
	}

	private void buscadorDiagnostico() {
		buscarDiagnostico = new Buscar<Diagnostico>(ltbDiagnosticos,
				txtBuscadorDiagnostico) {

			@Override
			protected List<Diagnostico> buscar(String valor) {
				List<Diagnostico> diagnosticosFiltradas = new ArrayList<Diagnostico>();
				List<Diagnostico> diagnosticos = servicioDiagnostico
						.filtroNombre(valor);
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

	private void buscadorMedicina() {
		buscarMedicina = new Buscar<Medicina>(ltbMedicinas, txtBuscadorMedicina) {

			@Override
			protected List<Medicina> buscar(String valor) {
				List<Medicina> medicinasFiltradas = new ArrayList<Medicina>();
				List<Medicina> medicinas = servicioMedicina.filtroNombre(valor);
				for (int i = 0; i < medicinasDisponibles.size(); i++) {
					Medicina medicina = medicinasDisponibles.get(i);
					for (int j = 0; j < medicinas.size(); j++) {
						if (medicina.getIdMedicina() == medicinas.get(j)
								.getIdMedicina())
							medicinasFiltradas.add(medicina);
					}
				}
				return medicinasFiltradas;
			}
		};
	}

	protected void guardarExamenFisico(Consulta consultaDatos) {
		List<ConsultaParteCuerpo> examenFisico = new ArrayList<ConsultaParteCuerpo>();
		servicioConsultaParteCuerpo.borrarExamenAnterior(consultaDatos);
		if (ltbExamenFisico.getItemCount() != 0) {
			for (int i = 0; i < ltbExamenFisico.getItemCount(); i++) {
				Listitem listItem = ltbExamenFisico.getItemAtIndex(i);
				if (listItem.isSelected()) {
					ParteCuerpo organo = listItem.getValue();
					Textbox text = (Textbox) listItem.getChildren().get(1)
							.getChildren().get(0);
					String observacion = text.getValue();
					ConsultaParteCuerpo examen = new ConsultaParteCuerpo(
							consultaDatos, organo, observacion);
					examenFisico.add(examen);
				}
			}
			servicioConsultaParteCuerpo.guardar(examenFisico);
		}
	}

	public void guardarServicios(Consulta consultaDatos) {
		List<ConsultaServicioExterno> listaServicioExterno = new ArrayList<ConsultaServicioExterno>();
		ProveedorServicio proveedorServicio = new ProveedorServicio();
		for (int i = 0; i < ltbServicioExternoAgregados.getItemCount(); i++) {
			Listitem listItem = ltbServicioExternoAgregados.getItemAtIndex(i);
			Integer id = ((Spinner) ((listItem.getChildren().get(4)))
					.getFirstChild()).getValue();
			ServicioExterno servicioExterno = servicioServicioExterno
					.buscar(id);
			String idProveedor = ((Combobox) ((listItem.getChildren().get(2)))
					.getFirstChild()).getSelectedItem().getContext();
			Proveedor proveedor = servicioProveedor.buscar(Long
					.parseLong(idProveedor));
			String observacion = ((Textbox) ((listItem.getChildren().get(1)))
					.getFirstChild()).getValue();
			proveedorServicio = servicioProveedorServicio
					.buscarPorCodigoDeAmbos(proveedor.getIdProveedor(), id);
			double valor = proveedorServicio.getCosto();
			String prioridad = cmbPrioridadServicio.getValue();
			ConsultaServicioExterno consultaServicio = new ConsultaServicioExterno(
					consultaDatos, servicioExterno, proveedor, valor,
					observacion, prioridad);
			listaServicioExterno.add(consultaServicio);
		}
		servicioConsultaServicioExterno.guardar(listaServicioExterno);
	}

	public void guardarEspecialistas(Consulta consultaDatos) {
		List<ConsultaEspecialista> listaConsultaEspecialista = new ArrayList<ConsultaEspecialista>();
		for (int i = 0; i < ltbEspecialistasAgregados.getItemCount(); i++) {
			Listitem listItem = ltbEspecialistasAgregados.getItemAtIndex(i);
			Integer id = ((Spinner) ((listItem.getChildren().get(4)))
					.getFirstChild()).getValue();
			Especialista especialista = servicioEspecialista.buscar(String
					.valueOf(id));
			double valor = ((Doublespinner) ((listItem.getChildren().get(3)))
					.getFirstChild()).getValue();
			String observacion = ((Textbox) ((listItem.getChildren().get(2)))
					.getFirstChild()).getValue();
			String prioridad = cmbPrioridadEspecialista.getValue();
			ConsultaEspecialista consultaEspecialista = new ConsultaEspecialista(
					consultaDatos, especialista, valor, observacion, prioridad);
			listaConsultaEspecialista.add(consultaEspecialista);
		}
		servicioConsultaEspecialista.guardar(listaConsultaEspecialista);
	}

	public void guardarExamenes(Consulta consultaDatos) {
		List<ConsultaExamen> listaConsultaExamen = new ArrayList<ConsultaExamen>();
		Proveedor proveedor = new Proveedor();
		ProveedorExamen proveedorExamen = new ProveedorExamen();
		for (int i = 0; i < ltbExamenesAgregados.getItemCount(); i++) {
			Listitem listItem = ltbExamenesAgregados.getItemAtIndex(i);
			Integer idExamen = ((Spinner) ((listItem.getChildren().get(3)))
					.getFirstChild()).getValue();
			Examen examen = servicioExamen.buscar(idExamen);
			String valor = ((Textbox) ((listItem.getChildren().get(1)))
					.getFirstChild()).getValue();
			String idProveedor = ((Combobox) ((listItem.getChildren().get(2)))
					.getFirstChild()).getSelectedItem().getContext();
			proveedor = servicioProveedor.buscar(Long.parseLong(idProveedor));
			proveedorExamen = servicioProveedorExamen
					.buscarPorProveedoryExamen(proveedor, examen);
			double precio = proveedorExamen.getCosto();
			String prioridad = cmbPrioridadExamen.getValue();
			ConsultaExamen consultaExamen = new ConsultaExamen(consultaDatos,
					examen, valor, proveedor, precio, prioridad);
			listaConsultaExamen.add(consultaExamen);
		}
		servicioConsultaExamen.guardar(listaConsultaExamen);
	}

	public void guardarDiagnosticos(Consulta consultaDatos) {
		List<ConsultaDiagnostico> listaDiagnostico = new ArrayList<ConsultaDiagnostico>();
		boolean accidenteBol = false;
		for (int i = 0; i < ltbDiagnosticosAgregados.getItemCount(); i++) {
			Listitem listItem = ltbDiagnosticosAgregados.getItemAtIndex(i);
			Integer idDiagnostico = ((Spinner) ((listItem.getChildren().get(4)))
					.getFirstChild()).getValue();
			String motivo = "";
			String lugar = "";
			String clasificacion = "";
			Timestamp fecha = null;
			Accidente accidente = new Accidente();
			accidente = null;
			for (int j = 0; j < listaDetalle.size(); j++) {
				if (Long.valueOf(idDiagnostico) == listaDetalle.get(j)
						.getDiagnostico()) {
					motivo = listaDetalle.get(j).getMotivo();
					lugar = listaDetalle.get(j).getLugar();
					fecha = listaDetalle.get(j).getFecha();
					clasificacion = listaDetalle.get(j).getClasificacion();
					accidente = listaDetalle.get(j).getAccidente();
					j = listaDetalle.size();
				}
			}
			Diagnostico diagnostico = servicioDiagnostico.buscar(idDiagnostico);
			String tipo = ((Combobox) ((listItem.getChildren().get(1)))
					.getFirstChild()).getValue();
			String valor = ((Textbox) ((listItem.getChildren().get(2)))
					.getFirstChild()).getValue();
			ConsultaDiagnostico consultaDiagnostico = new ConsultaDiagnostico(
					consultaDatos, diagnostico, accidente, tipo, valor, lugar,
					motivo, fecha, clasificacion);
			listaDiagnostico.add(consultaDiagnostico);
			if (!accidenteBol) {
				accidenteBol = true;
				if (tipo.equals("Accidente Laboral"))
					reportarSha(consultaDiagnostico);
			}
		}
		servicioConsultaDiagnostico.guardar(listaDiagnostico);
	}

	private void reportarSha(ConsultaDiagnostico consultaDiagnostico) {
		Informe informe = new Informe();
		informe.setIdInforme(0);
		// if(consultaDiagnostico.getConsulta()!=null)
		// informe.setCodigo(String.valueOf(consultaDiagnostico.getConsulta().getIdConsulta()));
		informe.setPacienteA(consultaDiagnostico.getConsulta().getPaciente());
		if (consultaDiagnostico.getConsulta().getPaciente().getEmpresa() != null)
			informe.setEmpresaA(consultaDiagnostico.getConsulta().getPaciente()
					.getEmpresa());
		informe.setFa(consultaDiagnostico.getFecha());
		Date semanaInforme = new Date();
		if (consultaDiagnostico.getFecha() != null) {
			semanaInforme.setTime(consultaDiagnostico.getFecha().getTime());
			calendario2.setTime(semanaInforme);
			informe.setFb(diaSemanaString(calendario2));
		}
		if (consultaDiagnostico.getAccidente() != null) {
			informe.setAccidente(consultaDiagnostico.getAccidente());
		}
		informe.setFf(consultaDiagnostico.getLugar());
		String reposo = "No Amerito";
		if (consultaDiagnostico.getConsulta().getReposo()) {
			reposo = consultaDiagnostico.getConsulta().getDiasReposo() + " "
					+ consultaDiagnostico.getConsulta().getTipoReposo();
		}
		informe.setFgj(reposo);
		informe.setFgad(consultaDiagnostico.getMotivo());
		String c = servicioInforme.buscarMaxCodigo();
		if (c == null)
			c = "10";
		int n = c.length();
		char car = c.charAt(n - 1);
		String nro = Character.toString(car);
		Integer co = Integer.parseInt(nro);
		String finaal = String.valueOf(co + 1);
		informe.setCodigo(finaal);
		servicioInforme.guardar(informe);
		String correo = "";
		GrupoInspectores g = servicioGrupoInspectores.buscar(1);
		if (g != null) {
			correo = g.getGrupo();
			enviarEmailNotificacion(
					correo,
					"Se ha reportado un Nuevo Accidente en el Sistema Medico Integral y de Seguridad");
		}
	}

	public void guardarMedicinas(Consulta consultaDatos) {
		Recipe recipe = new Recipe();
		if (ltbMedicinasAgregadas.getItemCount() != 0) {
			Date vali = dtbValido.getValue();
			Timestamp validez = new Timestamp(vali.getTime());
			recipe = new Recipe(0, cmbPrioridad.getValue(), validez,
					metodoFecha(), metodoHora(), nombreUsuarioSesion(),
					cmbTratamiento.getValue());
			servicioRecipe.guardar(recipe);
			recipe = servicioRecipe.buscarUltimo();
		}
		List<ConsultaMedicina> listaMedicina = new ArrayList<ConsultaMedicina>();
		for (int i = 0; i < ltbMedicinasAgregadas.getItemCount(); i++) {
			Listitem listItem = ltbMedicinasAgregadas.getItemAtIndex(i);
			Integer idMedicina = ((Spinner) ((listItem.getChildren().get(3)))
					.getFirstChild()).getValue();
			Integer costo = ((Spinner) ((listItem.getChildren().get(1)))
					.getFirstChild()).getValue();
			Medicina medicina = servicioMedicina.buscar(idMedicina);
			String valor = ((Textbox) ((listItem.getChildren().get(2)))
					.getFirstChild()).getValue();
			double precio = 0;
			if (medicina.getPrecio() != null)
				precio = medicina.getPrecio();
			ConsultaMedicina consultaMedicina = new ConsultaMedicina(
					consultaDatos, medicina, valor, recipe, costo, precio);
			listaMedicina.add(consultaMedicina);
		}
		servicioConsultaMedicina.guardar(listaMedicina);
	}

	@Listen("onSelect=#cmbTratamiento")
	public void validarTratamiento() {
		if (cmbTratamiento.getValue().equals("Cronico"))
			rowValido.setVisible(false);
		else
			rowValido.setVisible(true);
	}

	public boolean validar() {
		if (txtCedula.getText().compareTo("") == 0) {
			Mensaje.mensajeError("Debe Seleccionar un Paciente");
			return false;
		} else {
			if (dtbFechaConsulta.getText().compareTo("") == 0
					|| cmbTipoConsulta.getText().compareTo("") == 0
					|| dtbValido.getText().compareTo("") == 0
					|| cmbTipoPreventiva.getText().compareTo("") == 0) {
				Mensaje.mensajeError(Mensaje.camposVacios);
				return false;
			} else {
				// if (!validarDoctor())
				// return false;
				// else {
				if (txtMotivo.getText().compareTo("") == 0
						|| txtEnfermedad.getText().compareTo("") == 0
						|| (!rdoSiReposo.isChecked() && !rdoNoReposo
								.isChecked())
						|| (!rdoSiLaboral.isChecked() && !rdoNoLaboral
								.isChecked())) {
					Mensaje.mensajeError("Debe Llenar los campos secundarios de la Consulta (Motivo de la Consulta, Enfermedad Actual, si hubo accidente laboral o si Amerita o no Reposo)");
					return false;
				} else {
					if (rdoSiReposo.isChecked()
							&& ((!rdoSiReposoDias.isChecked() && !rdoNoReposoHoras
									.isChecked()) || (!rdoSiMaternal
									.isChecked() && !rdoNoMaternal.isChecked()))) {
						Mensaje.mensajeError("Debe indicar las opciones del reposo");
						return false;
					} else {
						if (!validarAccidente()) {
							Mensaje.mensajeError("Si indica que la consulta fue de tipo accidente laboral, entonces debe agregar al menos un diagnostico de tipo accidente laboral");
							return false;
						} else {

							if (rdoSiMaternal.isChecked()
									&& cmbReposo.getText().compareTo("") == 0) {
								Mensaje.mensajeError("Debe indicar el tipo del reposo maternal");
								return false;
							} else {
								if (!agregarMedicina()) {
									Mensaje.mensajeError("Debe Llenar Todos los Campos de la Lista de Medicinas");
									return false;
								} else {
									if (!agregarDiagnostico()) {
										Mensaje.mensajeError("Debe Llenar Todos los Campos de la Lista de Diagnosticos");
										return false;
									} else {
										if (!agregarExamen()) {
											Mensaje.mensajeError("Debe Llenar Todos los Campos de la Lista de Examenes");
											return false;
										} else {
											// if (!validarProveedor()) {
											// return false;
											// } else {
											if (!agregarEspecialista()) {
												Mensaje.mensajeError("Debe Llenar Todos los Campos de la Lista de Especialistas");
												return false;
											} else {
												if (!agregarServicio()) {
													Mensaje.mensajeError("Debe Llenar Todos los Campos de la Lista de Servicios Externos");
													return false;
												} else {
													if (cmbTipoPreventiva
															.getValue().equals(
																	"Control")
															&& (idConsultaAsociada == 0)) {
														Mensaje.mensajeError("Debe Seleccionar la Consulta Asociada al Control que se esta Realizando");
														return false;
													} else {
														if (cmbTipoPreventiva
																.getValue()
																.equals("IC")
																&& (idConsultaAsociada == 0)) {
															Mensaje.mensajeError("Debe Seleccionar la ConsultanAsociada a la Inter-Consulta");
															return false;
														} else {
															if (cmbTipoPreventiva
																	.getValue()
																	.equals("IC")
																	&& cmbEspecialista
																			.getText()
																			.compareTo(
																					"") == 0) {
																Mensaje.mensajeError("Debe Seleccionar el Especialista al cual asistio el paciente");
																return false;
															} else {
																if (ltbMedicinasAgregadas
																		.getItemCount() != 0
																		&& cmbPrioridad
																				.getText()
																				.compareTo(
																						"") == 0) {
																	Mensaje.mensajeError("Debe Seleccionar la Prioridad del Recipe");
																	return false;
																} else {
																	if (ltbDiagnosticosAgregados
																			.getItemCount() == 0) {
																		Mensaje.mensajeError("Debe seleccionar al menos un diagnostico");
																		return false;
																	} else {
																		if ((cmbTipoPreventiva
																				.getValue()
																				.equals("Pre-Empleo")
																				|| cmbTipoPreventiva
																						.getValue()
																						.equals("Cambio de Puesto") || cmbTipoPreventiva
																				.getValue()
																				.equals("Promocion"))
																				&& (cmbArea
																						.getText()
																						.compareTo(
																								"") == 0 || cmbCargo
																						.getText()
																						.compareTo(
																								"") == 0)) {
																			Mensaje.mensajeError("Debe Seleccionar el Cargo y el Area a la cual Aspira el Paciente");
																			return false;
																		} else {
																			if ((cmbTipoPreventiva
																					.getValue()
																					.equals("Pre-Empleo")
																					|| cmbTipoPreventiva
																							.getValue()
																							.equals("Cambio de Puesto") || cmbTipoPreventiva
																					.getValue()
																					.equals("Promocion"))
																					&& (!rdoSiApto
																							.isChecked() && !rdoNoApto
																							.isChecked())) {
																				Mensaje.mensajeError("Debe Indicar si el Paciente es Apto, o no para el Cargo que Aspira");
																				return false;
																			} else {
																				if (ltbServicioExternoAgregados
																						.getItemCount() != 0
																						&& cmbPrioridadServicio
																								.getText()
																								.compareTo(
																										"") == 0) {
																					Mensaje.mensajeError("Debe Seleccionar la Prioridad de la orden de los Estudios Externos");
																					return false;
																				} else {
																					if (ltbExamenesAgregados
																							.getItemCount() != 0
																							&& cmbPrioridadExamen
																									.getText()
																									.compareTo(
																											"") == 0) {
																						Mensaje.mensajeError("Debe Seleccionar la Prioridad de la orden de los Examenes");
																						return false;
																					} else {
																						if (ltbEspecialistasAgregados
																								.getItemCount() != 0
																								&& cmbPrioridadEspecialista
																										.getText()
																										.compareTo(
																												"") == 0) {
																							Mensaje.mensajeError("Debe Seleccionar la Prioridad de la orden de los Especialistas");
																							return false;
																						} else {
																							return true;
																						}
																					}
																				}
																			}
																		}
																	}
																}
															}
														}
													}
												}
											}

											// }
											// }
										}
									}
								}
							}
						}
					}
				}
			}
		}
	}

	private boolean validarAccidente() {
		if (rdoSiLaboral.isChecked()) {
			for (int i = 0; i < ltbDiagnosticosAgregados.getItemCount(); i++) {
				Listitem listItem = ltbDiagnosticosAgregados.getItemAtIndex(i);
				String tipo = ((Combobox) ((listItem.getChildren().get(1)))
						.getFirstChild()).getValue();
				if (tipo.equals("Accidente Laboral"))
					return true;
			}
			return false;
		} else
			return true;
	}

	/* Abre la vista de Pais */
	@Listen("onClick = #btnAbrirProveedor")
	public void abrirPais() {
		List<Arbol> arboles = servicioArbol.buscarPorNombreArbol("Proveedor");
		if (!arboles.isEmpty()) {
			Arbol arbolItem = arboles.get(0);
			cArbol.abrirVentanas(arbolItem, tabBox, contenido, tab, tabs);
		}
	}

	/* Llena la listas al iniciar con todo lo existente */
	private void llenarListas() {
		Consulta consulta = servicioConsulta.buscar(idConsulta);
		medicinasDisponibles = servicioMedicina.buscarDisponibles(consulta);
		ltbMedicinas
				.setModel(new ListModelList<Medicina>(medicinasDisponibles));
		medicinasAgregadas = servicioConsultaMedicina
				.buscarPorConsulta(consulta);
		ltbMedicinasAgregadas.setModel(new ListModelList<ConsultaMedicina>(
				medicinasAgregadas));
		medicinasResumen = medicinasAgregadas;
		ltbResumenMedicinas.setModel(new ListModelList<ConsultaMedicina>(
				medicinasResumen));
		if (!medicinasAgregadas.isEmpty())
			cmbPrioridad.setValue(medicinasAgregadas.get(0).getRecipe()
					.getPrioridad());

		diagnosticosDisponibles = servicioDiagnostico
				.buscarDisponibles(consulta);
		ltbDiagnosticos.setModel(new ListModelList<Diagnostico>(
				diagnosticosDisponibles));
		diagnosticosAgregados = servicioConsultaDiagnostico
				.buscarPorConsulta(consulta);
		ltbDiagnosticosAgregados
				.setModel(new ListModelList<ConsultaDiagnostico>(
						diagnosticosAgregados));
		diagnosticosResumen = diagnosticosAgregados;
		ltbResumenDiagnosticos.setModel(new ListModelList<ConsultaDiagnostico>(
				diagnosticosResumen));

		ltbDiagnosticosAgregados.renderAll();
		if (idConsulta != 0) {
			listaDetalle = new ArrayList<DetalleAccidente>();
			for (int i = 0; i < diagnosticosAgregados.size(); i++) {
				if (diagnosticosAgregados.get(i).getTipo()
						.equals("Accidente Laboral")
						|| diagnosticosAgregados.get(i).getTipo()
								.equals("Accidente Comun")
						|| diagnosticosAgregados.get(i).getTipo()
								.equals("Incidente")) {
					DetalleAccidente detalle = new DetalleAccidente(
							diagnosticosAgregados.get(i).getDiagnostico()
									.getIdDiagnostico(), diagnosticosAgregados
									.get(i).getLugar(), diagnosticosAgregados
									.get(i).getMotivo(), diagnosticosAgregados
									.get(i).getClasificacion(),
							diagnosticosAgregados.get(i).getFecha(),
							diagnosticosAgregados.get(i).getAccidente());
					listaDetalle.add(detalle);
					Listitem listItem = ltbDiagnosticosAgregados
							.getItemAtIndex(i);
					((Button) ((listItem.getChildren().get(3))).getFirstChild())
							.setVisible(true);
				}
			}
		}

		examenesDisponibles = servicioExamen.buscarDisponibles(consulta);
		ltbExamenes.setModel(new ListModelList<Examen>(examenesDisponibles));
		examenesAgregado = servicioConsultaExamen.buscarPorConsulta(consulta);
		ltbExamenesAgregados.setModel(new ListModelList<ConsultaExamen>(
				examenesAgregado));
		examenesResumen = examenesAgregado;
		ltbResumenExamenes.setModel(new ListModelList<ConsultaExamen>(
				examenesResumen));
		if (!examenesAgregado.isEmpty()) {
			cmbPrioridadExamen.setValue(examenesAgregado.get(0).getPrioridad());
		}

		especialistasDisponibles = servicioEspecialista
				.buscarDisponibles(consulta);
		for (int i = 0; i < especialistasDisponibles.size(); i++) {

			String nombre = especialistasDisponibles.get(i).getNombre();
			String apellido = especialistasDisponibles.get(i).getApellido();
			Especialista especialista = especialistasDisponibles.get(i);
			especialista.setNombre(nombre + " " + apellido);
		}
		ltbEspecialistas.setModel(new ListModelList<Especialista>(
				especialistasDisponibles));
		especialistasAgregados = servicioConsultaEspecialista
				.buscarPorConsulta(consulta);
		ltbEspecialistasAgregados
				.setModel(new ListModelList<ConsultaEspecialista>(
						especialistasAgregados));
		especialistasResumen = especialistasAgregados;
		ltbResumenEspecialistas
				.setModel(new ListModelList<ConsultaEspecialista>(
						especialistasResumen));
		if (!especialistasAgregados.isEmpty())
			cmbPrioridadEspecialista.setValue(especialistasAgregados.get(0)
					.getPrioridad());

		serviciosDisponibles = servicioServicioExterno
				.buscarDisponibles(consulta);
		ltbServicioExterno.setModel(new ListModelList<ServicioExterno>(
				serviciosDisponibles));
		serviciosAgregados = servicioConsultaServicioExterno
				.buscarPorConsulta(consulta);
		ltbServicioExternoAgregados
				.setModel(new ListModelList<ConsultaServicioExterno>(
						serviciosAgregados));
		serviciosResumen = serviciosAgregados;
		ltbResumenServicios
				.setModel(new ListModelList<ConsultaServicioExterno>(
						serviciosResumen));
		if (!serviciosAgregados.isEmpty())
			cmbPrioridadServicio.setValue(serviciosAgregados.get(0)
					.getPrioridad());

		List<ConsultaParteCuerpo> examenFisicoConsulta = servicioConsultaParteCuerpo
				.buscarPorConsulta(consulta);

		listasMultiples();

		if (!examenFisicoConsulta.isEmpty()) {
			for (int i = 0; i < examenFisicoConsulta.size(); i++) {
				long id = examenFisicoConsulta.get(i).getParte().getIdParte();
				for (int j = 0; j < ltbExamenFisico.getItemCount(); j++) {
					Listitem listItem = ltbExamenFisico.getItemAtIndex(j);
					ParteCuerpo a = listItem.getValue();
					long id2 = a.getIdParte();
					if (id == id2) {
						listItem.setSelected(true);
						Textbox tex2 = (Textbox) listItem.getChildren().get(1)
								.getChildren().get(0);
						tex2.setValue(examenFisicoConsulta.get(i)
								.getObservacion());
						j = ltbExamenFisico.getItemCount();
					}
				}

			}
		}
		colorIzquierda();
		colorDerecha();
	}

	private void listasMultiples() {
		for (int i = 0; i < listas.size(); i++) {
			if (!listas.get(i).getId().equals("ltbConsultas")) {
				listas.get(i).setMultiple(false);
				listas.get(i).setCheckmark(false);
				listas.get(i).setMultiple(true);
				listas.get(i).setCheckmark(true);
			} else {
				listas.get(i).setCheckmark(false);
				listas.get(i).setCheckmark(true);
			}
		}
	}

	/* Muestra un catalogo de Pacientes */
	@Listen("onClick = #btnBuscarPaciente, #btnBuscarPacienteCita")
	public void mostrarCatalogoPaciente(Event evento) throws IOException {
		final DoctorInterno usuario = servicioUsuario.buscarPorLogin(
				nombreUsuarioSesion()).getDoctorInterno();
		idBoton = evento.getTarget().getId();
		List<Paciente> pacientesBuscar = new ArrayList<Paciente>();
		if (idBoton.equals("btnBuscarPaciente"))
			pacientesBuscar = servicioPaciente.buscarTodosActivos();
		else
			pacientesBuscar = servicioPaciente.buscarPacientesDeCita(usuario,
					fecha);

		final List<Paciente> pacientes = pacientesBuscar;
		catalogoPaciente = new Catalogo<Paciente>(divCatalogoPacientes,
				"Catalogo de Pacientes", pacientes, false, "Cedula", "Ficha",
				"Primer Nombre", "Segundo Nombre", "Primer Apellido",
				"Segundo Apellido") {

			@Override
			protected List<Paciente> buscar(String valor, String combo) {
				if (idBoton.equals("btnBuscarPaciente")) {
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
				} else
					switch (combo) {
					case "Ficha":
						return servicioPaciente.filtroFichaCitaActivos(usuario,
								valor, fecha);
					case "Primer Nombre":
						return servicioPaciente.filtroNombreCitaActivos(
								usuario, valor, fecha);
					case "Segundo Nombre":
						return servicioPaciente.filtroNombre2CitaActivos(
								usuario, valor, fecha);
					case "Cedula":
						return servicioPaciente.filtroCedulaCitaActivos(
								usuario, valor, fecha);
					case "Primer Apellido":
						return servicioPaciente.filtroApellidoCitaActivos(
								usuario, valor, fecha);
					case "Segundo Apellido":
						return servicioPaciente.filtroApellido2CitaActivos(
								usuario, valor, fecha);
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
		Listbox lsita = (Listbox) catalogoPaciente.getChildren().get(5);
		lsita.setEmptyMessage("Utilice el filtro para buscar el paciente que desea buscar");
		catalogoPaciente.doModal();
	}

	@Listen("onClick = #btnConsultaAsociada")
	public void mostrarCatalogo() {
		Paciente paciente = servicioPaciente.buscarPorCedula(String
				.valueOf(idPaciente));
		final List<Consulta> consultasPaciente = servicioConsulta
				.buscarPorPaciente(paciente);
		catalogo = new Catalogo<Consulta>(catalogoConsulta,
				"Catalogo de Consultas", consultasPaciente, false, "Fecha",
				"Doctor", "Motivo", "Enfermedad", "Tipo", "Subtipo") {

			@Override
			protected List<Consulta> buscar(String valor, String combo) {

				switch (combo) {
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
						return servicioConsulta.filtroFecha(fecha2,
								fecha2);
					}
					return consultasPaciente;
				case "Doctor":
					if (valor.length() != 0)
						return servicioConsulta.filtroDoctor(valor);
				case "Motivo":
					return servicioConsulta.filtroMotivo(valor);
				case "Enfermedad":
					return servicioConsulta.filtroEnfermedad(valor);
				case "Tipo":
					return servicioConsulta.filtroTipo(valor);
				case "Subtipo":
					return servicioConsulta.filtroTipoSecundaria(valor);
				default:
					return consultasPaciente;
				}
			}

			@Override
			protected String[] crearRegistros(Consulta objeto) {
				String[] registros = new String[6];
				registros[0] = formatoFecha.format(objeto.getFechaConsulta());
				registros[1] = objeto.getDoctor();
				registros[2] = objeto.getMotivoConsulta();
				registros[3] = objeto.getEnfermedadActual();
				registros[4] = objeto.getTipoConsulta();
				registros[5] = objeto.getTipoConsultaSecundaria();
				return registros;
			}

		};
		catalogo.setParent(catalogoConsulta);
		catalogo.doModal();
	}

	@Listen("onSeleccion = #catalogoConsulta")
	public void seleccionar() {
		Consulta consulta = catalogo.objetoSeleccionadoDelCatalogo();
		idConsultaAsociada = consulta.getIdConsulta();
		lblEnfermedadAsociada2.setValue(consulta.getEnfermedadActual());
		List<ConsultaDiagnostico> lista = servicioConsultaDiagnostico
				.buscarPorConsulta(consulta);
		if (!lista.isEmpty())
			lblDiagnosticoAsociado2.setValue(lista.get(0).getDiagnostico()
					.getNombre());
		if (cmbTipoPreventiva.getValue().equals("IC")) {
			List<ConsultaEspecialista> consultaEspecialistas = servicioConsultaEspecialista
					.buscarPorConsulta(consulta);
			List<Especialista> especialistas = new ArrayList<Especialista>();
			for (int i = 0; i < consultaEspecialistas.size(); i++) {
				especialistas.add(consultaEspecialistas.get(i)
						.getEspecialista());
			}
			cmbEspecialista.setModel(new ListModelList<Especialista>(
					especialistas));
		}
		catalogo.setParent(null);
	}

	/* Permite la seleccion de un item del catalogo de pacientes */
	@Listen("onSeleccion = #divCatalogoPacientes")
	public void seleccionarPaciente() {
		limpiarCampos();
		Paciente paciente = catalogoPaciente.objetoSeleccionadoDelCatalogo();
		if (idBoton.equals("btnBuscarPacienteCita")) {
			txtCedulaCita.setValue(paciente.getCedula());
			idCita = Long.valueOf(paciente.getUsuarioAuditoria());
		} else
			txtCedulaCita.setValue("");

		llenarCampos(paciente);
		idPaciente = paciente.getCedula();
		List<Consulta> consultas = servicioConsulta.buscarPorPaciente(paciente);
		if (!consultas.isEmpty())
			if (consultas.get(0).getTipoConsultaSecundaria()
					.equals("Pre-Vacacional"))
				Mensaje.mensajeAlerta("La ultima consulta de este paciente fue de tipo Pre-Vacacional, por lo tanto la consulta actual debe ser de tipo Post-Vacacional");
		for (int i = 0; i < consultas.size(); i++) {
			Consulta consulta = consultas.get(i);
			String no = "No";
			if (consulta.isAccidenteLaboral())
				no = "Si";
			consulta.setUsuarioAuditoria(no);
		}
		ltbConsultas.setModel(new ListModelList<Consulta>(consultas));
		llenarListas();
		catalogoPaciente.setParent(null);
	}

	@Listen("onClick = #btnVerConsulta")
	public void seleccionarConsulta() {
		if (ltbConsultas.getItemCount() != 0) {
			if (ltbConsultas.getSelectedItems().size() == 1) {
				Listitem listItem = ltbConsultas.getSelectedItem();
				if (listItem != null) {
					btnConstancia.setVisible(true);
					botonera.getChildren().get(0).setVisible(false);
					btnPreEmpleo.setVisible(false);
					Consulta consulta = listItem.getValue();
					if (consulta.getTipoConsultaSecundaria().equals(
							"Pre-Empleo")) {
						btnPreEmpleo.setVisible(true);
						txtExamenPreempleo.setValue(consulta
								.getExamenPreempleo());
					}
					idConsulta = consulta.getIdConsulta();
					idPaciente = consulta.getPaciente().getCedula();
					llenarCamposConsulta(consulta);
					llenarCampos(consulta.getPaciente());
					llenarListas();
					tabConsulta.setSelected(true);
					tabResumen.setSelected(true);
					if (consulta.getReposo())
						btnGenerarReposo.setVisible(true);
					btnGenerarOrden.setVisible(true);
					btnGenerarReferencia.setVisible(true);
					btnGenerarOrdenServicios.setVisible(true);
					btnGenerarRecipe.setVisible(true);
				}
			} else
				Mensaje.mensajeError("Debe Seleccionar una Consulta");
		} else
			Mensaje.mensajeError("No existen Regitros de Consulta");
	}

	private void llenarCamposConsulta(Consulta consulta) {
		limpiarCamposConsulta();
		lblPreventiva.setVisible(true);
		txtCondicionado.setValue(consulta.getCondicionApto());
		cmbTipoConsulta.setValue(consulta.getTipoConsulta());
		cmbTipoPreventiva.setVisible(true);
		cmbTipoPreventiva.setValue(consulta.getTipoConsultaSecundaria());
		if (consulta.getReposo()) {
			rowReposoDias.setVisible(true);
			rowEmbarazo.setVisible(true);
			rowReposo.setVisible(true);
			rowReposoFecha.setVisible(true);
			if (consulta.getFechaReposo() != null)
				dtbReposo.setValue(consulta.getFechaReposo());
		}
		if (consulta.getConsultaAsociada() != 0) {
			idConsultaAsociada = consulta.getConsultaAsociada();
			Consulta consultaAsociada = servicioConsulta
					.buscar(idConsultaAsociada);
			rowAsociada.setVisible(true);
			List<ConsultaDiagnostico> dia = servicioConsultaDiagnostico
					.buscarPorConsulta(consultaAsociada);
			lblDiagnosticoAsociado2.setValue(dia.get(0).getDiagnostico()
					.getNombre());
			lblEnfermedadAsociada2.setValue(consultaAsociada
					.getEnfermedadActual());
		}
		if (consulta.getTipoConsultaSecundaria().equals("Cambio de Puesto")
				|| consulta.getTipoConsultaSecundaria().equals("Promocion")
				|| consulta.getTipoConsultaSecundaria().equals("Pre-Empleo")) {
			rowPromocion.setVisible(true);
			cmbArea.setValue(consulta.getAreaDeseada().getNombre());
			cmbCargo.setValue(consulta.getCargoDeseado().getNombre());
			rowApto.setVisible(true);
			rowApto2.setVisible(true);
		}
		if (consulta.getTipoConsultaSecundaria().equals("Rutina Anual"))
			lblPreventivaArea.setVisible(true);
		if (consulta.getTipoConsultaSecundaria().equals("Pre-Empleo"))
			row.setVisible(true);

		if (consulta.getTipoConsultaSecundaria().equals("Pre-Vacacional")) {
			rowPostVacacional.setVisible(true);
			if (consulta.getFechaPostVacacional() != null)
				dtbFechaPostVacacional.setValue(consulta
						.getFechaPostVacacional());
			else
				dtbFechaPostVacacional.setValue(null);
		} else
			rowPostVacacional.setVisible(false);

		txtMotivo.setValue(consulta.getMotivoConsulta());
		txtEnfermedad.setValue(consulta.getEnfermedadActual());
		spnReposo.setValue(consulta.getDiasReposo());
		if (consulta.getTipoReposo() != null) {
			if (consulta.getTipoReposo().equals("Horas"))
				rdoNoReposoHoras.setChecked(true);
			else
				rdoSiReposoDias.setChecked(true);
		} else
			rdoSiReposoDias.setChecked(true);
		if (consulta.getReposoEmbarazo() != null) {
			rdoSiMaternal.setChecked(true);
			cmbReposo.setValue(consulta.getReposoEmbarazo());
			if (consulta.getReposoEmbarazo().equals("")) {
				rdoNoMaternal.setChecked(true);
				rowEmbarazo2.setVisible(false);
			}
		} else
			rdoNoMaternal.setChecked(true);
		spnPeso.setValue(consulta.getPeso());
		spnEstatura.setValue(consulta.getEstatura());
		spnOmbligo.setValue(consulta.getPerimetroOmbligo());
		spnPlena.setValue(consulta.getPerimetroPlena());
		spnForzada.setValue(consulta.getPerimetroForzada());
		spnCardiaca.setValue(consulta.getFrecuencia());
		frecuencia11.setValue(consulta.getFrecuenciaReposo());
		frecuencia12.setValue(consulta.getFrecuenciaEsfuerzo());
		frecuencia13.setValue(consulta.getFrecuenciaPost());
		s11.setValue(consulta.getSistolicaPrimera());
		s12.setValue(consulta.getSistolicaSegunda());
		s13.setValue(consulta.getSistolicaTercera());
		d11.setValue(consulta.getDiastolicaPrimera());
		d12.setValue(consulta.getDiastolicaSegunda());
		d13.setValue(consulta.getDiastolicaTercera());
		ex11.setValue(consulta.getExtraReposo());
		ex12.setValue(consulta.getExtraEsfuerzo());
		ex13.setValue(consulta.getExtraPost());
		if (consulta.getApto() != null) {
			boolean apto = consulta.getApto();
			if (apto)
				rdoSiApto.setChecked(true);
			else
				rdoNoApto.setChecked(true);
		}
		boolean ritmico = consulta.getRitmico();
		if (ritmico)
			rdoSiRitmico.setChecked(true);
		else
			rdoNoRitmico.setChecked(true);
		boolean reposo = consulta.getReposo();
		if (reposo)
			rdoSiReposo.setChecked(true);
		else
			rdoNoReposo.setChecked(true);
		boolean ritmico1 = consulta.getRitmicoReposo();
		if (ritmico1)
			rdoSiRitmicoF1.setChecked(true);
		else
			rdoNoRitmicoF1.setChecked(true);
		boolean ritmico2 = consulta.getRitmicoEsfuerzo();
		if (ritmico2)
			rdoSiRitmicoF2.setChecked(true);
		else
			rdoNoRitmicoF2.setChecked(true);
		boolean ritmico3 = consulta.getRitmicoPost();
		if (ritmico3)
			rdoSiRitmicoF3.setChecked(true);
		else
			rdoNoRitmicoF3.setChecked(true);
		if (consulta.getFrecuenciaRespiratoria() != null)
			spnFrecuenciaRespitatoria.setValue(consulta
					.getFrecuenciaRespiratoria());
		if (consulta.getRespiratoriaRitmica() != null) {
			if (consulta.getRespiratoriaRitmica())
				rdoSiRespiratoria.setChecked(true);
			else
				rdoNoRespiratoria.setChecked(true);
		}
		if (consulta.isAccidenteLaboral())
			rdoSiLaboral.setChecked(true);
		else
			rdoNoLaboral.setChecked(true);
		calcularIMC();
		dtbFechaConsulta.setValue(consulta.getFechaConsulta());
	}

	private void llenarCampos(Paciente paciente) {
		if (paciente.getCargoReal() != null)
			lblCargo1.setValue(paciente.getCargoReal().getNombre());
		if (paciente.getArea() != null)
			lblArea.setValue(paciente.getArea().getNombre());
		txtCedula.setValue(paciente.getCedula());
		lblNombres.setValue(paciente.getPrimerNombre() + " "
				+ paciente.getSegundoNombre());
		lblApellidos.setValue(paciente.getPrimerApellido() + " "
				+ paciente.getSegundoApellido());
		lblCedula.setValue(paciente.getCedula());
		lblCiudad.setValue(paciente.getCiudadVivienda().getNombre());
		lblFicha.setValue(paciente.getFicha());
		lblAlergias.setValue(paciente.getObservacionAlergias());
		lblFechaNac.setValue(String.valueOf(formatoFecha.format(paciente
				.getFechaNacimiento())));
		lblLugarNac.setValue(paciente.getLugarNacimiento());
		lblSexo.setValue(paciente.getSexo());
		if (paciente.getEstadoCivil() != null)
			lblEstadoCivil.setValue(paciente.getEstadoCivil().getNombre());
		lblGrupoSanguineo.setValue(paciente.getGrupoSanguineo());
		lblMano.setValue(paciente.getMano());
		lblOrigen.setValue(paciente.getOrigenDiscapacidad());
		lblTipoDiscapacidad.setValue(paciente.getTipoDiscapacidad());
		lblObservacionDiscapacidad.setValue(paciente
				.getObservacionDiscapacidad());
		lblDireccion.setValue(paciente.getDireccion());
		lblTelefono1.setValue(paciente.getTelefono1());
		lblTelefono2.setValue(paciente.getTelefono2());
		lblCorreo.setValue(paciente.getEmail());
		lblEdad.setValue(String.valueOf(calcularEdad(paciente
				.getFechaNacimiento())));
		lblEstatura.setValue(String.valueOf(paciente.getEstatura()));
		lblPeso.setValue(String.valueOf(paciente.getPeso()));
		lblEstado
				.setValue(paciente.getCiudadVivienda().getEstado().getNombre());

		if (paciente.isAlergia()) {
			lblAlergico.setValue("SI");
			lblEtiquetaAlergias.setVisible(true);
		} else
			lblAlergico.setValue("NO");

		if (!paciente.isTrabajador()) {
			lblTrabajador.setValue("SI");
			lblArea.setValue(paciente.getArea().getNombre());
		} else
			lblTrabajador.setValue("NO");

		if (paciente.isDiscapacidad()) {
			lblDiscapacidad.setValue("SI");
			lblEtiquetaObservacion.setVisible(true);
			lblEtiquetaOrigen.setVisible(true);
			lblEtiquetaTipo.setVisible(true);
		} else
			lblDiscapacidad.setValue("NO");

		if (paciente.isLentes())
			lblLentes.setValue("SI");
		else
			lblLentes.setValue("NO");

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
		idPaciente = paciente.getCedula();
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
	}

	@Listen("onClick = #pasar1Medicina")
	public void derechaMedicina() {
		List<Listitem> listitemEliminar = new ArrayList<Listitem>();
		List<Listitem> listItem = ltbMedicinas.getItems();
		if (listItem.size() != 0) {
			for (int i = 0; i < listItem.size(); i++) {
				if (listItem.get(i).isSelected()) {
					Medicina medicina = listItem.get(i).getValue();
					medicinasDisponibles.remove(medicina);
					ConsultaMedicina consultaMedicina = new ConsultaMedicina();
					consultaMedicina.setMedicina(medicina);
					medicinasAgregadas.clear();
					for (int j = 0; j < ltbMedicinasAgregadas.getItemCount(); j++) {
						Listitem listItemj = ltbMedicinasAgregadas
								.getItemAtIndex(j);
						Integer idMedicina = ((Spinner) ((listItemj
								.getChildren().get(3))).getFirstChild())
								.getValue();
						Medicina medicinaj = servicioMedicina
								.buscar(idMedicina);
						String valor = ((Textbox) ((listItemj.getChildren()
								.get(2))).getFirstChild()).getValue();
						Integer costo = ((Spinner) ((listItemj.getChildren()
								.get(1))).getFirstChild()).getValue();
						ConsultaMedicina consultaMedicinaj = new ConsultaMedicina(
								null, medicinaj, valor, null, costo,
								medicina.getPrecio());
						medicinasAgregadas.add(consultaMedicinaj);
					}
					medicinasAgregadas.add(consultaMedicina);
					ltbMedicinasAgregadas
							.setModel(new ListModelList<ConsultaMedicina>(
									medicinasAgregadas));
					ltbMedicinasAgregadas.renderAll();
					listitemEliminar.add(listItem.get(i));
				}
			}
		}
		for (int i = 0; i < listitemEliminar.size(); i++) {
			ltbMedicinas.removeItemAt(listitemEliminar.get(i).getIndex());
		}
		listasMultiples();
	}

	@Listen("onClick = #pasar2Medicina")
	public void izquierdaMedicina() {
		List<Listitem> listitemEliminar = new ArrayList<Listitem>();
		List<Listitem> listItem2 = ltbMedicinasAgregadas.getItems();
		if (listItem2.size() != 0) {
			for (int i = 0; i < listItem2.size(); i++) {
				if (listItem2.get(i).isSelected()) {
					ConsultaMedicina consultaMedicina = listItem2.get(i)
							.getValue();
					medicinasAgregadas.remove(consultaMedicina);
					medicinasDisponibles.add(consultaMedicina.getMedicina());
					ltbMedicinas.setModel(new ListModelList<Medicina>(
							medicinasDisponibles));
					listitemEliminar.add(listItem2.get(i));
				}
			}
		}
		for (int i = 0; i < listitemEliminar.size(); i++) {
			ltbMedicinasAgregadas.removeItemAt(listitemEliminar.get(i)
					.getIndex());
		}
		listasMultiples();
	}

	@Listen("onClick = #pasar1Diagnostico")
	public void derechaDiagnostico() {
		List<Listitem> listitemEliminar = new ArrayList<Listitem>();
		List<Listitem> listItem = ltbDiagnosticos.getItems();
		if (listItem.size() != 0) {
			for (int i = 0; i < listItem.size(); i++) {
				if (listItem.get(i).isSelected()) {
					Diagnostico diagnostico = listItem.get(i).getValue();
					diagnosticosDisponibles.remove(diagnostico);
					ConsultaDiagnostico consultaDiagnostico = new ConsultaDiagnostico();
					consultaDiagnostico.setDiagnostico(diagnostico);
					diagnosticosAgregados.clear();
					for (int j = 0; j < ltbDiagnosticosAgregados.getItemCount(); j++) {
						Listitem listItemj = ltbDiagnosticosAgregados
								.getItemAtIndex(j);
						Integer idDiagnostico = ((Spinner) ((listItemj
								.getChildren().get(4))).getFirstChild())
								.getValue();
						String motivo = "";
						String lugar = "";
						String clasificacion = "";
						Timestamp fecha = null;
						Accidente accidente = new Accidente();
						accidente = null;
						for (int w = 0; w < listaDetalle.size(); w++) {
							if (Long.valueOf(idDiagnostico) == listaDetalle
									.get(w).getDiagnostico()) {
								motivo = listaDetalle.get(w).getMotivo();
								lugar = listaDetalle.get(w).getLugar();
								fecha = listaDetalle.get(w).getFecha();
								clasificacion = listaDetalle.get(w)
										.getClasificacion();
								accidente = listaDetalle.get(w).getAccidente();
								w = listaDetalle.size();
							}
						}
						Diagnostico diagnosticoj = servicioDiagnostico
								.buscar(idDiagnostico);
						String tipo = ((Combobox) ((listItemj.getChildren()
								.get(1))).getFirstChild()).getValue();
						if (tipo.equals("Accidente Laboral")
								|| tipo.equals("Accidente Comun")
								|| tipo.equals("Incidente")) {
							Button boton = ((Button) ((listItemj.getChildren()
									.get(3))).getFirstChild());
							boton.setVisible(true);
						}
						String valor = ((Textbox) ((listItemj.getChildren()
								.get(2))).getFirstChild()).getValue();
						ConsultaDiagnostico consultaDiagnosticoj = new ConsultaDiagnostico(
								null, diagnosticoj, accidente, tipo, valor,
								lugar, motivo, fecha, clasificacion);
						diagnosticosAgregados.add(consultaDiagnosticoj);
					}
					diagnosticosAgregados.add(consultaDiagnostico);
					ltbDiagnosticosAgregados
							.setModel(new ListModelList<ConsultaDiagnostico>(
									diagnosticosAgregados));
					ltbDiagnosticosAgregados.renderAll();
					for (int j = 0; j < ltbDiagnosticosAgregados.getItemCount(); j++) {
						Listitem listItemj = ltbDiagnosticosAgregados
								.getItemAtIndex(j);
						String tipo = ((Combobox) ((listItemj.getChildren()
								.get(1))).getFirstChild()).getValue();
						if (tipo.equals("Accidente Laboral")
								|| tipo.equals("Accidente Comun")
								|| tipo.equals("Incidente")) {
							Button boton = ((Button) ((listItemj.getChildren()
									.get(3))).getFirstChild());
							boton.setVisible(true);
						}
					}
					ltbDiagnosticosAgregados.renderAll();
					listitemEliminar.add(listItem.get(i));
				}
			}
		}
		for (int i = 0; i < listitemEliminar.size(); i++) {
			ltbDiagnosticos.removeItemAt(listitemEliminar.get(i).getIndex());
		}
		listasMultiples();

		colorDerecha();
	}

	@Listen("onClick = #pasar2Diagnostico")
	public void izquierdaDiagnostico() {
		List<Listitem> listitemEliminar = new ArrayList<Listitem>();
		List<Listitem> listItem2 = ltbDiagnosticosAgregados.getItems();
		if (listItem2.size() != 0) {
			for (int i = 0; i < listItem2.size(); i++) {
				if (listItem2.get(i).isSelected()) {
					ConsultaDiagnostico consultaDiagnostico = listItem2.get(i)
							.getValue();
					diagnosticosAgregados.remove(consultaDiagnostico);
					diagnosticosDisponibles.add(consultaDiagnostico
							.getDiagnostico());
					ltbDiagnosticos.setModel(new ListModelList<Diagnostico>(
							diagnosticosDisponibles));
					listitemEliminar.add(listItem2.get(i));
				}
			}
		}
		for (int i = 0; i < listitemEliminar.size(); i++) {
			ltbDiagnosticosAgregados.removeItemAt(listitemEliminar.get(i)
					.getIndex());
		}
		listasMultiples();

		colorIzquierda();
	}

	@Listen("onClick = #pasar1Examen")
	public void derechaExamen() {
		List<Listitem> listitemEliminar = new ArrayList<Listitem>();
		List<Listitem> listItem = ltbExamenes.getItems();
		if (listItem.size() != 0) {
			for (int i = 0; i < listItem.size(); i++) {
				if (listItem.get(i).isSelected()) {
					Examen examen = listItem.get(i).getValue();
					// examenesDisponibles.remove(examen);
					ConsultaExamen consultaExamen = new ConsultaExamen();
					consultaExamen.setExamen(examen);
					examenesAgregado.clear();
					for (int j = 0; j < ltbExamenesAgregados.getItemCount(); j++) {
						Listitem listItemj = ltbExamenesAgregados
								.getItemAtIndex(j);
						Integer idExamen = ((Spinner) ((listItemj.getChildren()
								.get(3))).getFirstChild()).getValue();
						String idProveedor = "0";
						if (((Combobox) ((listItemj.getChildren().get(2)))
								.getFirstChild()).getSelectedItem() != null)
							idProveedor = ((Combobox) ((listItemj.getChildren()
									.get(2))).getFirstChild())
									.getSelectedItem().getContext();
						Proveedor proveedor = servicioProveedor.buscar(Long
								.parseLong(idProveedor));
						Examen examenj = servicioExamen.buscar(idExamen);
						String valor = ((Textbox) ((listItemj.getChildren()
								.get(1))).getFirstChild()).getValue();
						double precio = 0;
						ConsultaExamen consultaExamenj = new ConsultaExamen(
								null, examenj, valor, proveedor, precio, "");
						examenesAgregado.add(consultaExamenj);
					}
					examenesAgregado.add(consultaExamen);
					ltbExamenesAgregados
							.setModel(new ListModelList<ConsultaExamen>(
									examenesAgregado));
					ltbExamenesAgregados.renderAll();
					listitemEliminar.add(listItem.get(i));
				}
			}
		}
		listasMultiples();
	}

	@Listen("onClick = #pasar2Examen")
	public void izquierdaExamen() {
		List<Listitem> listitemEliminar = new ArrayList<Listitem>();
		List<Listitem> listItem2 = ltbExamenesAgregados.getItems();
		if (listItem2.size() != 0) {
			for (int i = 0; i < listItem2.size(); i++) {
				if (listItem2.get(i).isSelected()) {
					ConsultaExamen consultaExamen = listItem2.get(i).getValue();
					examenesAgregado.remove(consultaExamen);
					examenesDisponibles.add(consultaExamen.getExamen());
					ltbExamenes.setModel(new ListModelList<Examen>(
							examenesDisponibles));
					listitemEliminar.add(listItem2.get(i));
				}
			}
		}
		for (int i = 0; i < listitemEliminar.size(); i++) {
			ltbExamenesAgregados.removeItemAt(listitemEliminar.get(i)
					.getIndex());
		}
		listasMultiples();
	}

	@Listen("onClick = #pasar1Especialista")
	public void derechaEspecialista() {
		List<Listitem> listitemEliminar = new ArrayList<Listitem>();
		List<Listitem> listItem = ltbEspecialistas.getItems();
		if (listItem.size() != 0) {
			for (int i = 0; i < listItem.size(); i++) {
				if (listItem.get(i).isSelected()) {
					Especialista especialista = listItem.get(i).getValue();
					especialistasDisponibles.remove(especialista);
					ConsultaEspecialista consultaEspecialista = new ConsultaEspecialista();
					consultaEspecialista.setEspecialista(especialista);
					consultaEspecialista.setCosto(especialista.getCosto());
					especialistasAgregados.clear();
					for (int j = 0; j < ltbEspecialistasAgregados
							.getItemCount(); j++) {
						Listitem listItemj = ltbEspecialistasAgregados
								.getItemAtIndex(j);
						Integer id = ((Spinner) ((listItemj.getChildren()
								.get(4))).getFirstChild()).getValue();
						Especialista especialistaj = servicioEspecialista
								.buscar(String.valueOf(id));
						String nombre = especialistaj.getNombre();
						String apellido = especialistaj.getApellido();
						especialistaj.setNombre(nombre + " " + apellido);
						double valor = ((Doublespinner) ((listItemj
								.getChildren().get(3))).getFirstChild())
								.getValue();
						String observacion = ((Textbox) ((listItemj
								.getChildren().get(2))).getFirstChild())
								.getValue();
						ConsultaEspecialista consultaEspecialistaj = new ConsultaEspecialista(
								null, especialistaj, valor, observacion, "");
						especialistasAgregados.add(consultaEspecialistaj);
					}
					especialistasAgregados.add(consultaEspecialista);
					ltbEspecialistasAgregados
							.setModel(new ListModelList<ConsultaEspecialista>(
									especialistasAgregados));
					ltbEspecialistasAgregados.renderAll();
					listitemEliminar.add(listItem.get(i));
				}
			}
		}
		for (int i = 0; i < listitemEliminar.size(); i++) {
			ltbEspecialistas.removeItemAt(listitemEliminar.get(i).getIndex());
		}
		listasMultiples();
	}

	@Listen("onClick = #pasar2Especialista")
	public void izquierdaEspecialista() {
		List<Listitem> listitemEliminar = new ArrayList<Listitem>();
		List<Listitem> listItem2 = ltbEspecialistasAgregados.getItems();
		if (listItem2.size() != 0) {
			for (int i = 0; i < listItem2.size(); i++) {
				if (listItem2.get(i).isSelected()) {
					ConsultaEspecialista consultaEspecialista = listItem2
							.get(i).getValue();
					especialistasAgregados.remove(consultaEspecialista);
					especialistasDisponibles.add(consultaEspecialista
							.getEspecialista());
					ltbEspecialistas.setModel(new ListModelList<Especialista>(
							especialistasDisponibles));
					listitemEliminar.add(listItem2.get(i));
				}
			}
		}
		for (int i = 0; i < listitemEliminar.size(); i++) {
			ltbEspecialistasAgregados.removeItemAt(listitemEliminar.get(i)
					.getIndex());
		}
		listasMultiples();
	}

	@Listen("onClick = #pasar1ServicioExterno")
	public void derechaServicioExterno() {
		List<Listitem> listitemEliminar = new ArrayList<Listitem>();
		List<Listitem> listItem = ltbServicioExterno.getItems();
		if (listItem.size() != 0) {
			for (int i = 0; i < listItem.size(); i++) {
				if (listItem.get(i).isSelected()) {
					ServicioExterno servicio = listItem.get(i).getValue();
					ConsultaServicioExterno consultaServicio = new ConsultaServicioExterno();
					consultaServicio.setServicioExterno(servicio);
					serviciosAgregados.clear();
					for (int j = 0; j < ltbServicioExternoAgregados
							.getItemCount(); j++) {
						Listitem listItemj = ltbServicioExternoAgregados
								.getItemAtIndex(j);
						Integer id = ((Spinner) ((listItemj.getChildren()
								.get(4))).getFirstChild()).getValue();
						ServicioExterno servicioExterno = servicioServicioExterno
								.buscar(id);
						double valor = ((Doublespinner) ((listItemj
								.getChildren().get(3))).getFirstChild())
								.getValue();
						String idProveedor = "";
						Proveedor proveedor = null;
						if (((Combobox) ((listItemj.getChildren().get(2)))
								.getFirstChild()).getSelectedItem() != null) {
							idProveedor = ((Combobox) ((listItemj.getChildren()
									.get(2))).getFirstChild())
									.getSelectedItem().getContext();
							proveedor = servicioProveedor.buscar(Long
									.parseLong(idProveedor));
						}
						String observacion = ((Textbox) ((listItemj
								.getChildren().get(1))).getFirstChild())
								.getValue();
						ConsultaServicioExterno consultaServicioj = new ConsultaServicioExterno(
								null, servicioExterno, proveedor, valor,
								observacion, "");
						serviciosAgregados.add(consultaServicioj);
					}
					serviciosAgregados.add(consultaServicio);
					ltbServicioExternoAgregados
							.setModel(new ListModelList<ConsultaServicioExterno>(
									serviciosAgregados));
					ltbServicioExternoAgregados.renderAll();
					listitemEliminar.add(listItem.get(i));
				}
			}
		}
		listasMultiples();
	}

	@Listen("onClick = #pasar2ServicioExterno")
	public void izquierdaServicioExterno() {
		List<Listitem> listitemEliminar = new ArrayList<Listitem>();
		List<Listitem> listItem2 = ltbServicioExternoAgregados.getItems();
		if (listItem2.size() != 0) {
			for (int i = 0; i < listItem2.size(); i++) {
				if (listItem2.get(i).isSelected()) {
					ConsultaServicioExterno consultaServicio = listItem2.get(i)
							.getValue();
					serviciosAgregados.remove(consultaServicio);
					serviciosDisponibles.add(consultaServicio
							.getServicioExterno());
					ltbServicioExterno
							.setModel(new ListModelList<ServicioExterno>(
									serviciosDisponibles));
					listitemEliminar.add(listItem2.get(i));
				}
			}
		}
		for (int i = 0; i < listitemEliminar.size(); i++) {
			ltbServicioExternoAgregados.removeItemAt(listitemEliminar.get(i)
					.getIndex());
		}
		listasMultiples();
	}

	@Listen("onClick = #btnAgregarMedicinas")
	public boolean agregarMedicina() {
		boolean falta = false;
		medicinasResumen.clear();
		if (ltbMedicinasAgregadas.getItemCount() != 0) {
			ConsultaMedicina consultaMedicina = new ConsultaMedicina();
			List<Listitem> listItem2 = ltbMedicinasAgregadas.getItems();
			for (int i = 0; i < ltbMedicinasAgregadas.getItemCount(); i++) {
				Listitem listItem = ltbMedicinasAgregadas.getItemAtIndex(i);
				consultaMedicina = new ConsultaMedicina();
				consultaMedicina = listItem2.get(i).getValue();
				String valor = ((Textbox) ((listItem.getChildren().get(2)))
						.getFirstChild()).getValue();
				Integer cantidad = ((Spinner) ((listItem.getChildren().get(1)))
						.getFirstChild()).getValue();
				if (valor.equals("") || cantidad == 0) {
					falta = true;
				}
				consultaMedicina.setDosis(valor);
				medicinasResumen.add(consultaMedicina);
			}
			ltbResumenMedicinas.setModel(new ListModelList<ConsultaMedicina>(
					medicinasResumen));
		}
		if (falta)
			return false;
		else
			return true;
	}

	@Listen("onClick = #btnAgregarDiagnosticos")
	public boolean agregarDiagnostico() {
		boolean falta = false;
		diagnosticosResumen.clear();
		if (ltbDiagnosticosAgregados.getItemCount() != 0) {
			ConsultaDiagnostico consultaDiagnostico = new ConsultaDiagnostico();
			List<Listitem> listItem2 = ltbDiagnosticosAgregados.getItems();
			for (int i = 0; i < ltbDiagnosticosAgregados.getItemCount(); i++) {
				Listitem listItem = ltbDiagnosticosAgregados.getItemAtIndex(i);
				consultaDiagnostico = new ConsultaDiagnostico();
				consultaDiagnostico = listItem2.get(i).getValue();
				String valor = ((Textbox) ((listItem.getChildren().get(2)))
						.getFirstChild()).getValue();
				String valor2 = ((Combobox) ((listItem.getChildren().get(1)))
						.getFirstChild()).getValue();
				if (valor2.equals("")) {
					falta = true;
				}
				consultaDiagnostico.setTipo(valor2);
				diagnosticosResumen.add(consultaDiagnostico);
			}
			ltbResumenDiagnosticos
					.setModel(new ListModelList<ConsultaDiagnostico>(
							diagnosticosResumen));
		}
		if (falta)
			return false;
		else
			return true;
	}

	public void llenarProveedor(Combobox a) {
		if (a.isOpen()) {
			Examen examen = new Examen();
			List<Proveedor> proveedorExamen = new ArrayList<Proveedor>();
			Spinner spin = (Spinner) a.getParent().getParent().getChildren()
					.get(3).getFirstChild();
			examen = servicioExamen.buscar(spin.getValue());
			proveedorExamen = servicioProveedor
					.buscarPorProveedoresPorExamen(examen);
			if (!proveedorExamen.isEmpty())
				a.setModel(new ListModelList<Proveedor>(proveedorExamen));
			else
				Messagebox.show(
						"El examen no es realizado por ningun Proveedor",
						"Alerta", Messagebox.OK, Messagebox.EXCLAMATION);

		}
	}

	public void llenarProveedorServicio(Combobox a) {

		if (a.isOpen()) {

			ServicioExterno servicio = new ServicioExterno();
			List<Proveedor> proveedorServicio = new ArrayList<Proveedor>();
			Spinner spin = (Spinner) a.getParent().getParent().getChildren()
					.get(4).getFirstChild();
			servicio = servicioServicioExterno.buscar(spin.getValue());
			proveedorServicio = servicioProveedor
					.buscarPorProveedoresPorServicio(servicio);
			if (!proveedorServicio.isEmpty())
				a.setModel(new ListModelList<Proveedor>(proveedorServicio));
			else
				Messagebox.show(
						"El estudio no es realizado por ningun Proveedor",
						"Alerta", Messagebox.OK, Messagebox.EXCLAMATION);

		}
	}

	@Listen("onClick = #btnAgregarExamenes")
	public boolean agregarExamen() {
		boolean falta = false;
		examenesResumen.clear();
		if (ltbExamenesAgregados.getItemCount() != 0) {
			ConsultaExamen consulta = new ConsultaExamen();
			List<Listitem> listItem2 = ltbExamenesAgregados.getItems();
			for (int i = 0; i < ltbExamenesAgregados.getItemCount(); i++) {
				Listitem listItem = ltbExamenesAgregados.getItemAtIndex(i);
				consulta = new ConsultaExamen();
				consulta = listItem2.get(i).getValue();
				String valor = ((Textbox) ((listItem.getChildren().get(1)))
						.getFirstChild()).getValue();
				consulta.setObservacion(valor);
				String proveedor = "";
				if (((Combobox) ((listItem.getChildren().get(2)))
						.getFirstChild()).getSelectedItem() == null) {
					falta = true;
				} else
					proveedor = ((Combobox) ((listItem.getChildren().get(2)))
							.getFirstChild()).getSelectedItem().getContext();
				examenesResumen.add(consulta);
			}
			ltbResumenExamenes.setModel(new ListModelList<ConsultaExamen>(
					examenesResumen));
		}
		if (falta)
			return false;
		else
			return true;
	}

	@Listen("onClick = #btnAgregarEspecialistas")
	public boolean agregarEspecialista() {
		boolean falta = false;
		especialistasResumen.clear();
		if (ltbEspecialistasAgregados.getItemCount() != 0) {
			ConsultaEspecialista consulta = new ConsultaEspecialista();
			List<Listitem> listItem2 = ltbEspecialistasAgregados.getItems();
			for (int i = 0; i < ltbEspecialistasAgregados.getItemCount(); i++) {
				Listitem listItem = ltbEspecialistasAgregados.getItemAtIndex(i);
				consulta = new ConsultaEspecialista();
				consulta = listItem.getValue();
				especialistasResumen.add(consulta);
			}
			ltbResumenEspecialistas
					.setModel(new ListModelList<ConsultaEspecialista>(
							especialistasResumen));
		}
		if (falta)
			return false;
		else
			return true;
	}

	@Listen("onClick = #btnAgregarServicios")
	public boolean agregarServicio() {
		boolean falta = false;
		serviciosResumen.clear();
		if (ltbServicioExternoAgregados.getItemCount() != 0) {
			ConsultaServicioExterno consulta = new ConsultaServicioExterno();
			List<Listitem> listItem2 = ltbServicioExternoAgregados.getItems();
			for (int i = 0; i < ltbServicioExternoAgregados.getItemCount(); i++) {
				Listitem listItem = ltbServicioExternoAgregados
						.getItemAtIndex(i);
				consulta = new ConsultaServicioExterno();
				consulta = listItem2.get(i).getValue();
				String valor = ((Textbox) ((listItem.getChildren().get(1)))
						.getFirstChild()).getValue();
				String proveedor = "";
				if (((Combobox) ((listItem.getChildren().get(2)))
						.getFirstChild()).getSelectedItem() == null) {
					falta = true;
				} else
					proveedor = ((Combobox) ((listItem.getChildren().get(2)))
							.getFirstChild()).getSelectedItem().getContext();

				consulta.setObservacion(valor);
				serviciosResumen.add(consulta);
			}
			ltbResumenServicios
					.setModel(new ListModelList<ConsultaServicioExterno>(
							serviciosResumen));
		}
		if (falta)
			return false;
		else
			return true;
	}

	public void limpiarListas() {
		List<List<?>> limpiador = new ArrayList<List<?>>();
		limpiador.add(diagnosticosAgregados);
		limpiador.add(diagnosticosDisponibles);
		limpiador.add(diagnosticosResumen);
		limpiador.add(especialistasAgregados);
		limpiador.add(especialistasDisponibles);
		limpiador.add(especialistasResumen);
		limpiador.add(examenesAgregado);
		limpiador.add(examenesDisponibles);
		limpiador.add(examenesResumen);
		limpiador.add(medicinasAgregadas);
		limpiador.add(medicinasDisponibles);
		limpiador.add(medicinasResumen);
		limpiador.add(serviciosAgregados);
		limpiador.add(serviciosDisponibles);
		limpiador.add(serviciosResumen);
		for (int q = 0; q < limpiador.size(); q++) {
			limpiador.get(q).clear();
		}
	}

	public void limpiarListBox() {
		for (int i = 0; i < listas.size(); i++) {
			if (!listas.get(i).getId().equals("ltbLaborales")) {
				if (!listas.get(i).getId().equals("ltbMedicos")) {
					if (!listas.get(i).getId().equals("ltbFamiliares")) {
						if (!listas.get(i).getId().equals("ltbVacunas")) {
							if (!listas.get(i).getId()
									.equals("ltbExamenFisico")) {
								listas.get(i).getItems().clear();
							}
						}
					}
				}

			}
		}
	}

	private void limpiarCamposConsulta() {
		txtCondicionado.setValue("");
		lblPreventivaArea.setVisible(false);
		cmbCargo.setValue("");
		cmbArea.setValue("");
		spnReposo.setValue(0);
		txtMotivo.setValue("");
		txtEnfermedad.setValue("");
		txtCedula.setValue("");
		dtbFechaConsulta.setValue(fecha);
		dtbValido.setValue(fecha);
		cmbPrioridad.setValue("");
		cmbTipoConsulta.setValue("");
		cmbTipoPreventiva.setValue("");
		if (rdoSiReposo.isChecked())
			rdoSiReposo.setChecked(false);
		if (rdoNoReposo.isChecked())
			rdoNoReposo.setChecked(false);
		if (rdoSiApto.isChecked())
			rdoSiApto.setChecked(false);
		if (rdoNoApto.isChecked())
			rdoNoApto.setChecked(false);
		rowAsociada.setVisible(false);
		rowEspecialista.setVisible(false);
		rowApto.setVisible(false);
		rowApto2.setVisible(false);
		rowPromocion.setVisible(false);
		cmbTipoPreventiva.setVisible(false);
		rdoSiReposoDias.setChecked(false);
		dtbReposo.setValue(fecha);
		rdoNoReposoHoras.setChecked(false);
		lblReposo.setValue("");
		rowEmbarazo.setVisible(false);
		rdoSiMaternal.setChecked(false);
		rdoNoMaternal.setChecked(false);
		rowEmbarazo2.setVisible(false);
		rowReposoDias.setVisible(false);
		rowReposoFecha.setVisible(false);
		rowReposo.setVisible(false);
	}

	public void limpiarCampos() {
		rowPostVacacional.setVisible(false);
		if (!botonera.getChildren().get(0).isVisible()) {
			botonera.getChildren().get(0).setVisible(true);
		}
		idCita = 0;
		btnPreEmpleo.setVisible(false);
		cmbReposo.setValue("");
		rowValido.setVisible(true);
		btnConstancia.setVisible(false);
		btnGenerarOrden.setVisible(false);
		btnGenerarRecipe.setVisible(false);
		btnGenerarReferencia.setVisible(false);
		btnGenerarOrdenServicios.setVisible(false);
		btnGenerarReposo.setVisible(false);
		listaDetalle = new ArrayList<DetalleAccidente>();
		idPaciente = "";
		idConsulta = 0;
		idConsultaAsociada = 0;
		limpiarListBox();
		limpiarListas();
		llenarListas();
		limpiarCamposConsulta();
		for (int i = 0; i < ltbExamenFisico.getItemCount(); i++) {
			Listitem listItem = ltbExamenFisico.getItemAtIndex(i);
			if (listItem.isSelected()) {
				Textbox tex = (Textbox) listItem.getChildren().get(1)
						.getChildren().get(0);
				tex.setValue("");
				tex.setPlaceholder("Ingrese una Observacion");
				listItem.setSelected(false);
			}
		}
		lblNombres.setValue("");
		lblCedula.setValue("");
		lblApellidos.setValue("");
		imagenPaciente.setVisible(false);
		lblFicha.setValue("");
		lblAlergico.setValue("");
		lblFechaNac.setValue("");
		lblArea.setValue("");
		lblCargo1.setValue("");
		lblLugarNac.setValue("");
		lblSexo.setValue("");
		lblEstadoCivil.setValue("");
		lblGrupoSanguineo.setValue("");
		lblMano.setValue("");
		lblOrigen.setValue("");
		lblTipoDiscapacidad.setValue("");
		lblObservacionDiscapacidad.setValue("");
		lblDireccion.setValue("");
		lblTelefono1.setValue("");
		lblTelefono2.setValue("");
		lblCorreo.setValue("");
		lblEstado.setValue("");
		lblPeso.setValue("");
		lblEdad.setValue("");
		lblEstatura.setValue("");
		lblCiudad.setValue("");
		lblAlergias.setValue("");
		lblTrabajador.setValue("");
		lblDiscapacidad.setValue("");
		lblLentes.setValue("");
		lblIndice.setValue("");
		spnPeso.setValue((double) 0);
		spnEstatura.setValue((double) 0);
		spnOmbligo.setValue((double) 0);
		spnPlena.setValue((double) 0);
		spnForzada.setValue((double) 0);
		spnCardiaca.setValue(0);
		frecuencia11.setValue(0);
		frecuencia12.setValue(0);
		frecuencia13.setValue(0);
		s11.setValue(0);
		s12.setValue(0);
		s13.setValue(0);
		d11.setValue(0);
		d12.setValue(0);
		d13.setValue(0);
		ex11.setValue(0);
		ex12.setValue(0);
		ex13.setValue(0);
		if (rdoSiRitmico.isChecked())
			rdoSiRitmico.setChecked(true);
		if (rdoNoRitmico.isChecked())
			rdoNoRitmico.setChecked(true);
		if (rdoSiRitmicoF1.isChecked())
			rdoSiRitmicoF1.setChecked(true);
		if (rdoNoRitmicoF1.isChecked())
			rdoNoRitmicoF1.setChecked(true);
		if (rdoSiRitmicoF2.isChecked())
			rdoSiRitmicoF2.setChecked(true);
		if (rdoNoRitmicoF2.isChecked())
			rdoNoRitmicoF2.setChecked(true);
		if (rdoSiRitmicoF3.isChecked())
			rdoSiRitmicoF3.setChecked(true);
		if (rdoSiRespiratoria.isChecked())
			rdoSiRespiratoria.setChecked(false);
		if (rdoNoRespiratoria.isChecked())
			rdoNoRespiratoria.setChecked(false);
		if (rdoNoLaboral.isChecked())
			rdoNoLaboral.setChecked(false);
		if (rdoSiLaboral.isChecked())
			rdoSiLaboral.setChecked(false);
		if (rdoNoRitmicoF3.isChecked())
			rdoNoRitmicoF3.setChecked(true);
		tabIdentificacion.setSelected(true);
		txtExamenPreempleo.setValue("");
		tabIdentificacion.setSelected(false);
		lblEtiquetaObservacion.setVisible(false);
		lblEtiquetaOrigen.setVisible(false);
		lblEtiquetaTipo.setVisible(false);
		lblEtiquetaAlergias.setVisible(false);
	}

	@Listen("onClick = #btnAbrirExamen")
	public void divExamen() {
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("id", "consulta");
		map.put("lista", examenesDisponibles);
		map.put("listbox", ltbExamenes);
		map.put("titulo", "Examen");
		Sessions.getCurrent().setAttribute("itemsCatalogo", map);
		List<Arbol> arboles = servicioArbol.buscarPorNombreArbol("Examen");
		if (!arboles.isEmpty()) {
			Arbol arbolItem = arboles.get(0);
			cArbol.abrirVentanas(arbolItem, tabBox, contenido, tab, tabs);
		}
	}

	@Listen("onClick = #btnAbrirCuerpo")
	public void divCuerpo() {
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("id", "consulta");
		map.put("lista", modelFisico);
		map.put("listbox", ltbExamenFisico);
		map.put("titulo", "Parte del Cuerpo");
		Sessions.getCurrent().setAttribute("itemsCatalogo", map);
		List<Arbol> arboles = servicioArbol.buscarPorNombreArbol("Parte del Cuerpo");
		if (!arboles.isEmpty()) {
			Arbol arbolItem = arboles.get(0);
			cArbol.abrirVentanas(arbolItem, tabBox, contenido, tab, tabs);
		}
	}

	@Listen("onClick = #btnAbrirDiagnostico")
	public void divDiagnostico() {
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("id", "consulta");
		map.put("lista", diagnosticosDisponibles);
		map.put("listbox", ltbDiagnosticos);
		map.put("titulo", "Diagnostico");
		Sessions.getCurrent().setAttribute("itemsCatalogo", map);
		List<Arbol> arboles = servicioArbol.buscarPorNombreArbol("Diagnostico");

		if (!arboles.isEmpty()) {
			Arbol arbolItem = arboles.get(0);
			cArbol.abrirVentanas(arbolItem, tabBox, contenido, tab, tabs);
		}
	}

	@Listen("onClick = #btnAbrirEspecialista")
	public void divEspecialista() {
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("id", "consulta");
		map.put("lista", especialistasDisponibles);
		map.put("listbox", ltbEspecialistas);
		map.put("titulo", "Especialista");
		Sessions.getCurrent().setAttribute("itemsCatalogo", map);
		List<Arbol> arboles = servicioArbol
				.buscarPorNombreArbol("Especialista");
		
		if (!arboles.isEmpty()) {
			Arbol arbolItem = arboles.get(0);
			cArbol.abrirVentanas(arbolItem, tabBox, contenido, tab, tabs);
		}
	}

	@Listen("onClick = #btnAbrirServicioExterno")
	public void divServicio() {
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("id", "consulta");
		map.put("lista", serviciosDisponibles);
		map.put("listbox", ltbServicioExterno);
		map.put("titulo", "Estudios");
		Sessions.getCurrent().setAttribute("itemsCatalogo", map);
		List<Arbol> arboles = servicioArbol
				.buscarPorNombreArbol("Estudios");
		if (!arboles.isEmpty()) {
			Arbol arbolItem = arboles.get(0);
			cArbol.abrirVentanas(arbolItem, tabBox, contenido, tab, tabs);
		}
	}

	@Listen("onClick = #btnAbrirMedicina")
	public void divMedicina() {
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("id", "consulta");
		map.put("lista", medicinasDisponibles);
		map.put("listbox", ltbMedicinas);
		map.put("titulo", "Medicina");
		Sessions.getCurrent().setAttribute("itemsCatalogo", map);

		List<Arbol> arboles = servicioArbol.buscarPorNombreArbol("Medicina");
		if (!arboles.isEmpty()) {
			Arbol arbolItem = arboles.get(0);
			cArbol.abrirVentanas(arbolItem, tabBox, contenido, tab, tabs);
		}
	}

	@Listen("onOK = #txtCedula")
	public void buscarCedula() {
		Paciente paciente = new Paciente();
		paciente = servicioPaciente.buscarPorCedulaActivo(txtCedula.getValue());
		limpiarCampos();
		if (paciente != null) {
			txtCedulaCita.setValue("");
			llenarCampos(paciente);
			idPaciente = paciente.getCedula();
			List<Consulta> consultas = servicioConsulta
					.buscarPorPaciente(paciente);
			if (!consultas.isEmpty())
				if (consultas.get(0).getTipoConsultaSecundaria()
						.equals("Pre-Vacacional"))
					Mensaje.mensajeAlerta("La ultima consulta de este paciente fue de tipo Pre-Vacacional, por lo tanto la consulta actual debe ser de tipo Post-Vacacional");
			ltbConsultas.setModel(new ListModelList<Consulta>(consultas));
			llenarListas();
		} else {
			Mensaje.mensajeError(Mensaje.pacienteNoExiste);
		}
	}

	@Listen("onOK = #txtCedulaCita")
	public void buscarCedulaCita() {
		Usuario usuario = servicioUsuario.buscarPorLogin(nombreUsuarioSesion());
		Paciente paciente = servicioPaciente.buscarPorCitaActivo(
				usuario.getDoctorInterno(), txtCedulaCita.getValue(), fecha);
		limpiarCampos();
		if (paciente != null) {
			txtCedula.setValue("");
			idCita = Long.valueOf(paciente.getUsuarioAuditoria());
			txtCedulaCita.setValue(paciente.getCedula());
			llenarCampos(paciente);
			idPaciente = paciente.getCedula();
			List<Consulta> consultas = servicioConsulta
					.buscarPorPaciente(paciente);
			if (!consultas.isEmpty())
				if (consultas.get(0).getTipoConsultaSecundaria()
						.equals("Pre-Vacacional"))
					Mensaje.mensajeAlerta("La ultima consulta de este paciente fue de tipo Pre-Vacacional, por lo tanto la consulta actual debe ser de tipo Post-Vacacional");
			ltbConsultas.setModel(new ListModelList<Consulta>(consultas));
			llenarListas();
		} else {
			Mensaje.mensajeError("El paciente que selecciono no posee citas programadas para hoy");
		}
	}

	@Listen("onSelect = #cmbTipoPreventiva")
	public void buscarExamenesPreempleo() {
		if (cmbTipoPreventiva.getValue().equals("Pre-Vacacional"))
			rowPostVacacional.setVisible(true);
		else
			rowPostVacacional.setVisible(false);
		if (cmbTipoPreventiva.getValue().equals("Rutina Anual"))
			lblPreventivaArea.setVisible(true);
		else
			lblPreventivaArea.setVisible(false);
		if (cmbTipoPreventiva.getValue().equals("Pre-Empleo")) {
			row.setVisible(true);
			rowPromocion.setVisible(true);
			rowApto.setVisible(true);
			rowApto2.setVisible(true);
			txtCondicionado.setValue("");
			rowEspecialista.setVisible(false);
			rowAsociada.setVisible(false);
		} else {
			if (cmbTipoPreventiva.getValue().equals("Control")) {
				rowAsociada.setVisible(true);
				txtCondicionado.setValue("");
				rowApto2.setVisible(false);
				rowEspecialista.setVisible(false);
			} else {
				if (cmbTipoPreventiva.getValue().equals("IC")) {
					rowEspecialista.setVisible(true);
					txtCondicionado.setValue("");
					rowApto2.setVisible(false);
					rowAsociada.setVisible(true);
					txtCondicionado.setValue("");
					rowApto2.setVisible(false);
				} else {
					if (cmbTipoPreventiva.getValue().equals("Cambio de Puesto")
							|| cmbTipoPreventiva.getValue().equals("Promocion")) {
						rowApto2.setVisible(true);
						txtCondicionado.setValue("");
						rowApto.setVisible(true);
						rowPromocion.setVisible(true);
					} else {
						rowApto2.setVisible(false);
						txtCondicionado.setValue("");
						rowApto.setVisible(false);
						rowPromocion.setVisible(false);
					}
					rowEspecialista.setVisible(false);
					rowEspecialista.setVisible(false);
					rowAsociada.setVisible(false);
					lblDiagnosticoAsociado2.setValue("");
					lblEnfermedadAsociada2.setValue("");
				}
			}
			row.setVisible(false);
		}
		// validarDoctor();
	}

	// public boolean validarDoctor() {
	// Usuario usuario = servicioUsuario.buscarPorLogin(nombreUsuarioSesion());
	// if (!cmbTipoPreventiva.getValue().equals("IC") && !usuario.isDoctor()) {
	// Mensaje.mensajeError("Solo puede crear consultas de tipo IC");
	// return false;
	// } else
	// return true;
	// }

	@Listen("onSelect = #cmbTipoConsulta")
	public void buscarPreventiva() {
		if (cmbTipoConsulta.getValue().equals("Preventiva")) {
			cmbTipoPreventiva.setModel(new ListModelList<String>(
					consultaPreventiva));
			lblPreventiva.setValue("Tipo de Consulta Preventiva");
		} else {
			if (cmbTipoConsulta.getValue().equals("Curativa")) {
				cmbTipoPreventiva.setModel(new ListModelList<String>(
						consultaCurativa));
				lblPreventiva.setValue("Tipo de Consulta Curativa");
			}
		}
		row.setVisible(false);
		rowPromocion.setVisible(false);
		cmbTipoPreventiva.setVisible(true);
		rowAsociada.setVisible(false);
		lblDiagnosticoAsociado2.setValue("");
		lblEnfermedadAsociada2.setValue("");
		lblPreventiva.setVisible(true);
		lblPreventivaArea.setVisible(false);
		cmbTipoPreventiva.setValue("");

	}

	public ListModelList<Proveedor> getProveedores() {
		proveedores = new ListModelList<Proveedor>(
				servicioProveedor.buscarTodos());
		return proveedores;
	}

	public ListModelList<ParteCuerpo> getModelFisico() {
		modelFisico = new ListModelList<ParteCuerpo>(
				servicioParteCuerpo.buscarTodos());
		return modelFisico;
	}

	@Listen("onChange = #spnEstatura, #spnPeso")
	public void calcularIMC() {
		double peso = spnPeso.getValue();
		double estatura = spnEstatura.getValue();
		double imc = 0;
		if (estatura != 0) {
			imc = (double) Math.round((peso / (estatura * estatura)) * 100) / 100;
			if (imc < 18.5)
				lblIndice.setValue(imc + " Bajo Peso");
			else {
				if (imc >= 18.5 && imc < 25)
					lblIndice.setValue(imc + " Normal");
				else {
					if (imc >= 25 && imc < 30)
						lblIndice.setValue(imc + " Sobre Peso");
					else {
						if (imc >= 30 && imc < 35)
							lblIndice.setValue(imc + " Obesidad I");
						else {
							if (imc >= 35 && imc < 40)
								lblIndice.setValue(imc + " Obesidad II");
							else
								lblIndice.setValue(imc + " Obesidad III");
						}
					}
				}
			}
		}
	}

	public String getCambio() {
		if (ltbServicioExternoAgregados.getItemCount() != 0) {
			for (int i = 0; i < ltbServicioExternoAgregados.getItemCount(); i++) {
				Listitem listItem = ltbServicioExternoAgregados
						.getItemAtIndex(i);
				if (listItem != null) {
					if (((Combobox) ((listItem.getChildren().get(2)))
							.getFirstChild()).getSelectedItem() != null) {
						String proveedor = ((Combobox) ((listItem.getChildren()
								.get(2))).getFirstChild()).getSelectedItem()
								.getContext();
						long id = ((Spinner) ((listItem.getChildren().get(4)))
								.getFirstChild()).getValue();
						ProveedorServicio proveedorServicio = servicioProveedorServicio
								.buscarPorCodigoDeAmbos(
										Long.parseLong(proveedor), id);
						if (proveedorServicio != null) {
							double precioUnitario = proveedorServicio
									.getCosto();
							((Doublespinner) ((listItem.getChildren().get(3)))
									.getFirstChild()).setValue(precioUnitario);
						} else {

							Mensaje.mensajeAlerta("Este proveedor no posee este estudio asignado, por favor seleccione otro o remuevalo de la lista");
							((Doublespinner) ((listItem.getChildren().get(3)))
									.getFirstChild()).setValue((double) 0);
							((Combobox) ((listItem.getChildren().get(2)))
									.getFirstChild()).setFocus(true);
							((Combobox) ((listItem.getChildren().get(2)))
									.getFirstChild()).setValue("");
						}
					}
				}
			}
		}
		return cambio;
	}

	public void recibir(List<Especialista> lista, Listbox l) {
		ltbEspecialistas = l;
		especialistasDisponibles = lista;
		ltbEspecialistas.setModel(new ListModelList<Especialista>(
				especialistasDisponibles));
		ltbEspecialistas.setMultiple(false);
		ltbEspecialistas.setCheckmark(false);
		ltbEspecialistas.setMultiple(true);
		ltbEspecialistas.setCheckmark(true);
	}

	public void recibirMedicina(List<Medicina> lista, Listbox l) {
		ltbMedicinas = l;
		medicinasDisponibles = lista;
		ltbMedicinas
				.setModel(new ListModelList<Medicina>(medicinasDisponibles));
		ltbMedicinas.setMultiple(false);
		ltbMedicinas.setCheckmark(false);
		ltbMedicinas.setMultiple(true);
		ltbMedicinas.setCheckmark(true);
	}

	public void recibirDiagnostico(List<Diagnostico> lista, Listbox l) {
		ltbDiagnosticos = l;
		diagnosticosDisponibles = lista;
		ltbDiagnosticos.setModel(new ListModelList<Diagnostico>(
				diagnosticosDisponibles));
		ltbDiagnosticos.setMultiple(false);
		ltbDiagnosticos.setCheckmark(false);
		ltbDiagnosticos.setMultiple(true);
		ltbDiagnosticos.setCheckmark(true);
	}

	public void recibirExamen(List<Examen> lista, Listbox l) {
		ltbExamenes = l;
		examenesDisponibles = lista;
		ltbExamenes.setModel(new ListModelList<Examen>(examenesDisponibles));
		ltbExamenes.setMultiple(false);
		ltbExamenes.setCheckmark(false);
		ltbExamenes.setMultiple(true);
		ltbExamenes.setCheckmark(true);
	}

	public void recibirServicio(List<ServicioExterno> lista, Listbox l) {
		ltbServicioExterno = l;
		serviciosDisponibles = lista;
		ltbServicioExterno.setModel(new ListModelList<ServicioExterno>(
				serviciosDisponibles));
		ltbServicioExterno.setMultiple(false);
		ltbServicioExterno.setCheckmark(false);
		ltbServicioExterno.setMultiple(true);
		ltbServicioExterno.setCheckmark(true);
	}

	public void recibirCuerpo(List<ParteCuerpo> partes, Listbox listaConsulta,
			SParteCuerpo s) {
		servicioParteCuerpo = s;
		ltbExamenFisico = listaConsulta;
		ltbExamenFisico.setModel(new ListModelList<ParteCuerpo>(
				servicioParteCuerpo.buscarTodos()));
		ltbExamenFisico.renderAll();
		ltbExamenFisico.setMultiple(false);
		ltbExamenFisico.setCheckmark(false);
		ltbExamenFisico.setMultiple(true);
		ltbExamenFisico.setCheckmark(true);
	}

	public void colorIzquierda() {
		if (ltbDiagnosticos.getItemCount() != 0) {
			ltbDiagnosticos.renderAll();
			for (int j = 0; j < ltbDiagnosticos.getItemCount(); j++) {
				Listitem list2 = ltbDiagnosticos.getItemAtIndex(j);
				Diagnostico diagnostico = list2.getValue();
				if (diagnostico.getEpi() != null) {
					if (diagnostico.getEpi())
						list2.setStyle("font-weight:bold; background:#F8E0E0; font-color:white");
				}
			}
		}
	}

	public void colorDerecha() {
		if (ltbDiagnosticosAgregados.getItemCount() != 0) {
			ltbDiagnosticosAgregados.renderAll();
			for (int j = 0; j < ltbDiagnosticosAgregados.getItemCount(); j++) {
				Listitem list2 = ltbDiagnosticosAgregados.getItemAtIndex(j);
				ConsultaDiagnostico consultaD = list2.getValue();
				if (consultaD.getDiagnostico().getEpi() != null) {
					if (consultaD.getDiagnostico().getEpi())
						list2.setStyle("font-weight:bold; background:#F8E0E0; font-color:white");
				}
			}
		}
	}

	@Listen("onCheck = #rdoSiReposoDias")
	public void checkSiDias() {
		lblReposo.setValue("Cantidad de Dias");
		rowReposo.setVisible(true);
	}

	@Listen("onCheck = #rdoNoReposoHoras")
	public void checkNoHoras() {
		lblReposo.setValue("Cantidad de Horas");
		rowReposo.setVisible(true);
	}

	@Listen("onCheck = #rdoSiMaternal")
	public void checkSiMaternal() {
		rowEmbarazo2.setVisible(true);
	}

	@Listen("onCheck = #rdoNoMaternal")
	public void checkNoMaternal() {
		rowEmbarazo2.setVisible(false);
		cmbReposo.setValue("");
	}

	@Listen("onCheck = #rdoSiReposo")
	public void checkSi() {
		rowReposoFecha.setVisible(true);
		rowReposoDias.setVisible(true);
		rowEmbarazo.setVisible(true);
	}

	@Listen("onCheck = #rdoNoReposo")
	public void checkNo() {
		rowReposoDias.setVisible(false);
		rowReposoFecha.setVisible(false);
		lblReposo.setValue("");
		rowEmbarazo.setVisible(false);
		rdoSiMaternal.setChecked(false);
		rdoNoMaternal.setChecked(false);
		rdoSiReposoDias.setChecked(false);
		rdoNoReposoHoras.setChecked(false);
		rowEmbarazo2.setVisible(false);
		spnReposo.setValue(0);
		cmbReposo.setValue("");
		rowReposo.setVisible(false);
	}

	@Listen("onClick = #btnRefrescarServicio")
	public void refrescarServicio() {

		Consulta consulta = servicioConsulta.buscar(idConsulta);
		serviciosDisponibles = servicioServicioExterno
				.buscarDisponibles(consulta);
		ltbServicioExterno.setModel(new ListModelList<ServicioExterno>(
				serviciosDisponibles));
		serviciosAgregados = servicioConsultaServicioExterno
				.buscarPorConsulta(consulta);
		ltbServicioExternoAgregados
				.setModel(new ListModelList<ConsultaServicioExterno>(
						serviciosAgregados));
		serviciosResumen = serviciosAgregados;
		ltbResumenServicios
				.setModel(new ListModelList<ConsultaServicioExterno>(
						serviciosResumen));
		listasMultiples();
	}

	@Listen("onClick = #btnRefrescarMedicina")
	public void refrescarMedicina() {
		Consulta consulta = servicioConsulta.buscar(idConsulta);
		medicinasDisponibles = servicioMedicina.buscarDisponibles(consulta);
		ltbMedicinas
				.setModel(new ListModelList<Medicina>(medicinasDisponibles));
		medicinasAgregadas = servicioConsultaMedicina
				.buscarPorConsulta(consulta);
		ltbMedicinasAgregadas.setModel(new ListModelList<ConsultaMedicina>(
				medicinasAgregadas));
		medicinasResumen = medicinasAgregadas;
		ltbResumenMedicinas.setModel(new ListModelList<ConsultaMedicina>(
				medicinasResumen));
		listasMultiples();

	}

	@Listen("onClick = #btnRefrescarDiagnostico")
	public void refrescarDiagnostico() {
		Consulta consulta = servicioConsulta.buscar(idConsulta);
		diagnosticosDisponibles = servicioDiagnostico
				.buscarDisponibles(consulta);
		ltbDiagnosticos.setModel(new ListModelList<Diagnostico>(
				diagnosticosDisponibles));
		diagnosticosAgregados = servicioConsultaDiagnostico
				.buscarPorConsulta(consulta);
		ltbDiagnosticosAgregados
				.setModel(new ListModelList<ConsultaDiagnostico>(
						diagnosticosAgregados));
		diagnosticosResumen = diagnosticosAgregados;
		ltbResumenDiagnosticos.setModel(new ListModelList<ConsultaDiagnostico>(
				diagnosticosResumen));
		listaDetalle.clear();
		listasMultiples();
	}

	@Listen("onClick = #btnRefrescarExamen")
	public void refrescarExamen() {
		Consulta consulta = servicioConsulta.buscar(idConsulta);
		examenesDisponibles = servicioExamen.buscarDisponibles(consulta);
		ltbExamenes.setModel(new ListModelList<Examen>(examenesDisponibles));
		examenesAgregado = servicioConsultaExamen.buscarPorConsulta(consulta);
		ltbExamenesAgregados.setModel(new ListModelList<ConsultaExamen>(
				examenesAgregado));
		examenesResumen = examenesAgregado;
		ltbResumenExamenes.setModel(new ListModelList<ConsultaExamen>(
				examenesResumen));
		listasMultiples();
	}

	@Listen("onClick = #btnRefrescarEspecialista")
	public void refrescarEspecialista() {
		Consulta consulta = servicioConsulta.buscar(idConsulta);
		especialistasDisponibles = servicioEspecialista
				.buscarDisponibles(consulta);
		for (int i = 0; i < especialistasDisponibles.size(); i++) {

			String nombre = especialistasDisponibles.get(i).getNombre();
			String apellido = especialistasDisponibles.get(i).getApellido();
			Especialista especialista = especialistasDisponibles.get(i);
			especialista.setNombre(nombre + " " + apellido);
		}
		ltbEspecialistas.setModel(new ListModelList<Especialista>(
				especialistasDisponibles));
		especialistasAgregados = servicioConsultaEspecialista
				.buscarPorConsulta(consulta);
		ltbEspecialistasAgregados
				.setModel(new ListModelList<ConsultaEspecialista>(
						especialistasAgregados));
		especialistasResumen = especialistasAgregados;
		ltbResumenEspecialistas
				.setModel(new ListModelList<ConsultaEspecialista>(
						especialistasResumen));
		listasMultiples();
	}

	// VENTANA DE ACCIDENTE

	public void ventana(Combobox a) {
		Spinner spin = (Spinner) a.getParent().getParent().getChildren().get(4)
				.getFirstChild();
		Combobox combo = (Combobox) a.getParent().getParent().getChildren()
				.get(1).getFirstChild();
		String tipoDiagnostico = combo.getValue();
		long diagnositco = spin.getValue();
		Button boton = (Button) a.getParent().getParent().getChildren().get(3)
				.getFirstChild();
		if (a.getValue().equals("Accidente Laboral")
				|| a.getValue().equals("Accidente Comun")
				|| a.getValue().equals("Incidente")) {
			abrirVentana(diagnositco, tipoDiagnostico);
			boton.setVisible(true);
		} else
			boton.setVisible(false);
	}

	public void ventanaBoton(Button a) {
		Spinner spin = (Spinner) a.getParent().getParent().getChildren().get(4)
				.getFirstChild();
		Combobox combo = (Combobox) a.getParent().getParent().getChildren()
				.get(1).getFirstChild();
		String tipoDiagnostico = combo.getValue();
		long diagnositco = spin.getValue();
		abrirVentana(diagnositco, tipoDiagnostico);
	}

	public void abrirVentana(long diagnostico, String tipo) {
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("idDiagnostico", diagnostico);
		map.put("tipoDiagnostico", tipo);
		map.put("lista", listaDetalle);
		map.put("div", divConsulta);
		map.put("tabsGenerales", tabs);
		Sessions.getCurrent().setAttribute("consulta", map);
		Window window = (Window) Executions.createComponents(
				"/vistas/medico/transacciones/VRegistroAccidente.zul", null,
				map);
		window.doModal();
	}

	public void recibirLista(List<DetalleAccidente> lista) {
		listaDetalle = lista;
	}

	// Ventana de resultados

	@Listen("onClick = #btnVerResultado")
	public void abrirVentanaResultado() {
		if (ltbConsultas.getItemCount() != 0) {
			if (ltbConsultas.getSelectedItems().size() == 1) {
				Listitem listItem = ltbConsultas.getSelectedItem();
				if (listItem != null) {
					Consulta consulta = listItem.getValue();
					HashMap<String, Object> mapiin = new HashMap<String, Object>();
					mapiin.put("idConsulta", consulta.getIdConsulta());
					Sessions.getCurrent().setAttribute("consultaResultado",
							mapiin);
					Window window = (Window) Executions.createComponents(
							"/vistas/medico/transacciones/VResultado.zul",
							null, mapiin);
					window.doModal();
				}
			} else
				Mensaje.mensajeError("Debe Seleccionar una Consulta");
		} else
			Mensaje.mensajeError("No existen Regitros de Consulta");
	}

	// Reporte Recipe Medicinas

	@Listen("onClick = #btnGenerarRecipe")
	public void generarRecipe() throws JSONException {
		if (ltbMedicinasAgregadas.getItemCount() != 0) {
			Long id = idConsulta;
			Clients.evalJavaScript("window.open('"
					+ damePath()
					+ "Reportero?valor=1&valor2="
					+ id
					+ "','','top=100,left=200,height=600,width=800,scrollbars=1,resizable=1')");
		} else
			Mensaje.mensajeAlerta(Mensaje.noHayRegistros);

	}

	public byte[] reporteRecipe(Long id) throws JRException, IOException {
		byte[] fichero = null;
		Consulta consuta = getServicioConsulta().buscar(id);
		List<ConsultaMedicina> listaMedicinas = getServicioConsultaMedicina()
				.buscarPorConsulta(consuta);

		Date fechaRecipe = listaMedicinas.get(0).getRecipe().getValidez();

		String dias = "";
		dias = formatoFecha.format(fechaRecipe);

		Paciente paciente = consuta.getPaciente();
		DoctorInterno user = consuta.getDoctorInterno();
		Map<String, Object> p = new HashMap<String, Object>();
		String nombreEmpresa = "Sin Empresa Asociada";
		String direccionEmpresa = "Direccion Faltante";
		String rifEmpresa = "Rif Faltante";
		if (paciente.getEmpresa() != null) {
			nombreEmpresa = paciente.getEmpresa().getNombre();
			direccionEmpresa = paciente.getEmpresa().getDireccionCentro();
			rifEmpresa = paciente.getEmpresa().getRif();
		}

		String nombre = paciente.getPrimerNombre() + "   "
				+ paciente.getSegundoNombre();
		String tratamiento = "";
		if (listaMedicinas.get(0).getRecipe().getTratamiento() == null)
			tratamiento = "Sin Informacion";
		else
			tratamiento = listaMedicinas.get(0).getRecipe().getTratamiento();
		p.put("tratamiento", tratamiento);
		p.put("numero", consuta.getIdConsulta());
		p.put("empresaNombre", nombreEmpresa);
		p.put("empresaDireccion", direccionEmpresa);
		p.put("empresaRif", rifEmpresa);
		p.put("pacienteNombre", nombre);
		p.put("pacienteApellido", paciente.getPrimerApellido() + "   "
				+ paciente.getSegundoApellido());
		p.put("pacienteCedula", paciente.getCedula());
		p.put("doctorNombre", consuta.getDoctor());
		p.put("doctorApellido",
				user.getPrimerApellido() + "   " + user.getSegundoApellido());

		if (tratamiento.equals("Agudo"))
			p.put("impresion", "si");
		else
			p.put("impresion", "no");

		p.put("mostrar", "no");

		String ced = "";
		if (consuta.getTipoConsultaSecundaria().equals("IC")) {
			if (consuta.getEspecialista() != null)
				ced = consuta.getEspecialista().getCedula();
		} else
			ced = user.getCedula();

		p.put("doctorCedula", ced);

		// Restar Dias
		p.put("dias", dias);
		String ms = "";
		ms = user.getLicenciaMsds();
		p.put("msds", ms);

		String cm = "";
		cm = user.getLicenciaCm();
		p.put("msds", ms);
		p.put("comelar", cm);
		p.put("edad",
				String.valueOf(calcularEdad(paciente.getFechaNacimiento())));
		p.put("pacienteNacimiento", paciente.getFechaNacimiento());

		JasperReport reporte = (JasperReport) JRLoader.loadObject(getClass()
				.getResource("/reporte/medico/consulta/RRecipe.jasper"));
		fichero = JasperRunManager.runReportToPdf(reporte, p,
				new JRBeanCollectionDataSource(listaMedicinas));
		return fichero;
	}

	// Reporte Especialista

	@Listen("onClick = #btnGenerarReferencia")
	public void generarEspecialista() {
		if (ltbEspecialistasAgregados.getItemCount() != 0) {
			for (int i = 0; i < ltbEspecialistasAgregados.getItemCount(); i++) {
				Listitem listItem = ltbEspecialistasAgregados.getItemAtIndex(i);
				Integer idEs = ((Spinner) ((listItem.getChildren().get(4)))
						.getFirstChild()).getValue();
				Long id = idConsulta;
				String idEspecialista = String.valueOf(idEs);
				Clients.evalJavaScript("window.open('"
						+ damePath()
						+ "Reportero?valor=2&valor2="
						+ id
						+ "&valor3="
						+ idEspecialista
						+ "','','top=100,left=200,height=600,width=800,scrollbars=1,resizable=1')");
			}
		} else
			Mensaje.mensajeAlerta(Mensaje.noHayRegistros);

	}

	public byte[] reporteEspecialista(Long part2, String par3)
			throws JRException {
		byte[] fichero = null;
		Consulta consuta = getServicioConsulta().buscar(part2);
		ConsultaEspecialista especialistaConsulta = getServicioConsultaEspecialista()
				.buscarPorConsultaYIdEspecialista(consuta, par3);
		List<ConsultaEspecialista> lista = getServicioConsultaEspecialista()
				.buscarPorConsulta(consuta);
		Paciente paciente = consuta.getPaciente();
		DoctorInterno user = consuta.getDoctorInterno();
		Map<String, Object> p = new HashMap<String, Object>();
		String nombreEmpresa = "Sin Empresa Asociada";
		String direccionEmpresa = "Direccion Faltante";
		String rifEmpresa = "Rif Faltante";
		if (paciente.getEmpresa() != null) {
			nombreEmpresa = paciente.getEmpresa().getNombre();
			direccionEmpresa = paciente.getEmpresa().getDireccionCentro();
			rifEmpresa = paciente.getEmpresa().getRif();
		}
		p.put("mostrar", "no");
		p.put("empresaNombre", nombreEmpresa);
		p.put("empresaDireccion", direccionEmpresa);
		p.put("empresaRif", rifEmpresa);
		p.put("pacienteNombre",
				paciente.getPrimerNombre() + "   "
						+ paciente.getSegundoNombre());
		p.put("pacienteApellido", paciente.getPrimerApellido() + "   "
				+ paciente.getSegundoApellido());
		p.put("pacienteCedula", paciente.getCedula());
		p.put("edad",
				String.valueOf(calcularEdad(paciente.getFechaNacimiento())));
		p.put("pacienteSexo", paciente.getSexo());
		p.put("doctorNombre", consuta.getDoctor());
		p.put("doctorApellido",
				user.getPrimerApellido() + "   " + user.getSegundoApellido());
		p.put("doctorCedula", user.getCedula());
		p.put("especialidad", especialistaConsulta.getEspecialista()
				.getEspecialidad().getDescripcion());
		p.put("especialistaDireccion", especialistaConsulta.getEspecialista()
				.getDireccion());
		p.put("especialistaTelefono", especialistaConsulta.getEspecialista()
				.getTelefono());
		p.put("empresaDireccion", direccionEmpresa);
		p.put("especialistaNombre", especialistaConsulta.getEspecialista()
				.getNombre());
		p.put("especialistaApellido", especialistaConsulta.getEspecialista()
				.getApellido());
		// p.put("especialistaTelefono", especialistaConsulta.getEspecialista()
		// .getTelefono());
		p.put("enfermedad", especialistaConsulta.getObservacion());
		p.put("observacion", especialistaConsulta.getObservacion());
		p.put("prioridad", especialistaConsulta.getPrioridad());
		p.put("cedula", paciente.getCedula());

		JasperReport reporte = (JasperReport) JRLoader.loadObject(getClass()
				.getResource(
						"/reporte/medico/consulta/RRecipeEspecialista.jasper"));
		fichero = JasperRunManager.runReportToPdf(reporte, p,
				new JRBeanCollectionDataSource(lista));
		return fichero;
	}

	// Reporte Servicio

	@Listen("onClick = #btnGenerarOrdenServicios")
	public void generarServicio() {
		if (ltbServicioExternoAgregados.getItemCount() != 0) {
			// for (int i = 0; i < ltbServicioExternoAgregados.getItemCount();
			// i++) {
			// Listitem listItem = ltbServicioExternoAgregados
			// .getItemAtIndex(i);
			// Integer idSe = ((Spinner) ((listItem.getChildren().get(4)))
			// .getFirstChild()).getValue();
			// String idPr = ((Combobox) ((listItem.getChildren().get(2)))
			// .getFirstChild()).getSelectedItem().getContext();
			// Long id = idConsulta;
			// Long idServicio = Long.valueOf(idSe);
			// Long idProveedor = Long.valueOf(idPr);
			// Clients.evalJavaScript("window.open('"
			// + damePath()
			// + "Reportero?valor=3&valor2="
			// + id
			// + "&valor4="
			// + idServicio
			// + "&valor5="
			// + idProveedor
			// +
			// "','','top=100,left=200,height=600,width=800,scrollbars=1,resizable=1')");
			// }
			Long id = idConsulta;
			Consulta consulta = servicioConsulta.buscar(id);
			List<ConsultaServicioExterno> listaMedicinas = servicioConsultaServicioExterno
					.buscarPorConsulta(consulta);
			List<Long> idsProveedor = new ArrayList<Long>();
			long provedor = listaMedicinas.get(0).getProveedor()
					.getIdProveedor();
			idsProveedor.add(provedor);
			for (int i = 0; i < listaMedicinas.size(); i++) {
				if (provedor != listaMedicinas.get(i).getProveedor()
						.getIdProveedor()) {
					idsProveedor.add(listaMedicinas.get(i).getProveedor()
							.getIdProveedor());
					provedor = listaMedicinas.get(i).getProveedor()
							.getIdProveedor();
				}
			}
			for (int i = 0; i < idsProveedor.size(); i++) {
				Clients.evalJavaScript("window.open('"
						+ damePath()
						+ "Reportero?valor=3&valor2="
						+ id
						+ "&valor5="
						+ idsProveedor.get(i)
						+ "','','top=100,left=200,height=600,width=800,scrollbars=1,resizable=1')");
			}
		} else
			Mensaje.mensajeAlerta(Mensaje.noHayRegistros);

	}

	public byte[] reporteServicio(Long part2, Long part5) throws JRException {
		byte[] fichero = null;
		Consulta consuta = getServicioConsulta().buscar(part2);
		// ConsultaServicioExterno servicioConsultas =
		// getServicioConsultaServicioExterno()
		// .buscarPorConsultaYIdServicio(consuta, part4);
		List<ConsultaServicioExterno> listaMedicinas = getServicioConsultaServicioExterno()
				.buscarPorConsultaYProveedor(consuta, part5);
		String servicio = "";
		for (int i = 0; i < listaMedicinas.size(); i++) {
			servicio += listaMedicinas.get(i).getServicioExterno().getNombre()
					+ "; ";
		}
		Paciente paciente = consuta.getPaciente();
		DoctorInterno user = consuta.getDoctorInterno();
		Map<String, Object> p = new HashMap<String, Object>();
		String nombreEmpresa = "Sin Empresa Asociada";
		String direccionEmpresa = "Direccion Faltante";
		String rifEmpresa = "Rif Faltante";
		if (paciente.getEmpresa() != null) {
			nombreEmpresa = paciente.getEmpresa().getNombre();
			direccionEmpresa = paciente.getEmpresa().getDireccionCentro();
			rifEmpresa = paciente.getEmpresa().getRif();
		}

		p.put("mostrar", "no");
		p.put("cedula", paciente.getCedula());
		p.put("empresaNombre", nombreEmpresa);
		p.put("empresaDireccion", direccionEmpresa);
		p.put("empresaRif", rifEmpresa);
		p.put("pacienteNombre",
				paciente.getPrimerNombre() + "  " + paciente.getSegundoNombre());
		p.put("pacienteApellido", paciente.getPrimerApellido() + "   "
				+ paciente.getSegundoApellido());
		p.put("pacienteCedula", paciente.getCedula());
		p.put("pacienteEdad", paciente.getEdad());
		p.put("pacienteSexo", paciente.getSexo());
		p.put("doctorNombre", consuta.getDoctor());
		p.put("doctorApellido",
				user.getPrimerApellido() + "   " + user.getSegundoApellido());
		p.put("doctorCedula", user.getCedula());
		p.put("servicio", servicio);
		p.put("centro", listaMedicinas.get(0).getProveedor().getNombre());
		p.put("direccion", listaMedicinas.get(0).getProveedor().getDireccion());
		p.put("prioridad", listaMedicinas.get(0).getPrioridad());
		p.put("edad",
				String.valueOf(calcularEdad(paciente.getFechaNacimiento())));

		JasperReport reporte = (JasperReport) JRLoader
				.loadObject(getClass().getResource(
						"/reporte/medico/consulta/RRecipeServicio.jasper"));
		fichero = JasperRunManager.runReportToPdf(reporte, p,
				new JRBeanCollectionDataSource(listaMedicinas));
		return fichero;
	}

	// Generar Orden Examenes

	@Listen("onClick = #btnGenerarOrden")
	public void generarExamenes() {
		if (ltbExamenesAgregados.getItemCount() != 0) {
			Long id = idConsulta;
			Consulta consulta = servicioConsulta.buscar(idConsulta);
			List<ConsultaExamen> listaMedicinas = servicioConsultaExamen
					.buscarPorConsulta(consulta);
			List<Long> idsProveedor = new ArrayList<Long>();
			long provedor = 0;
			if (listaMedicinas.get(0).getProveedor() != null)
				provedor = listaMedicinas.get(0).getProveedor()
						.getIdProveedor();
			idsProveedor.add(provedor);
			for (int i = 0; i < listaMedicinas.size(); i++) {
				if (listaMedicinas.get(0).getProveedor() != null) {
					if (provedor != listaMedicinas.get(i).getProveedor()
							.getIdProveedor()) {
						idsProveedor.add(listaMedicinas.get(i).getProveedor()
								.getIdProveedor());
						provedor = listaMedicinas.get(i).getProveedor()
								.getIdProveedor();
					}
				}
			}
			for (int i = 0; i < idsProveedor.size(); i++) {
				Clients.evalJavaScript("window.open('"
						+ damePath()
						+ "Reportero?valor=4&valor2="
						+ id
						+ "&valor5="
						+ idsProveedor.get(i)
						+ "','','top=100,left=200,height=600,width=800,scrollbars=1,resizable=1')");
			}
		} else
			Mensaje.mensajeAlerta(Mensaje.noHayRegistros);

	}

	public byte[] reporteExamen(Long part2, Long part5) throws JRException {
		byte[] fichero = null;
		Consulta consuta = getServicioConsulta().buscar(part2);
		List<ConsultaExamen> listaMedicinas = getServicioConsultaExamen()
				.buscarPorConsultaYProveedor(consuta, part5);
		if (part5 == 0)
			listaMedicinas = getServicioConsultaExamen().buscarPorConsulta(
					consuta);
		else
			listaMedicinas = getServicioConsultaExamen()
					.buscarPorConsultaYProveedor(consuta, part5);
		Paciente paciente = consuta.getPaciente();
		DoctorInterno user = consuta.getDoctorInterno();
		Map<String, Object> p = new HashMap<String, Object>();
		String nombreEmpresa = "Sin Empresa Asociada";
		String direccionEmpresa = "Direccion Faltante";
		String rifEmpresa = "Rif Faltante";
		if (paciente.getEmpresa() != null) {
			nombreEmpresa = paciente.getEmpresa().getNombre();
			direccionEmpresa = paciente.getEmpresa().getDireccionCentro();
			rifEmpresa = paciente.getEmpresa().getRif();
		}

		p.put("mostrar", "no");
		p.put("empresaNombre", nombreEmpresa);
		p.put("empresaDireccion", direccionEmpresa);
		p.put("empresaRif", rifEmpresa);
		p.put("pacienteNombre",
				paciente.getPrimerNombre() + "  " + paciente.getSegundoNombre());
		p.put("pacienteApellido", paciente.getPrimerApellido() + "   "
				+ paciente.getSegundoApellido());
		p.put("pacienteCedula", paciente.getCedula());
		p.put("doctorNombre", consuta.getDoctor());
		p.put("doctorApellido",
				user.getPrimerApellido() + "   " + user.getSegundoApellido());
		p.put("doctorCedula", user.getCedula());
		if (part5 == 0) {
			p.put("prioridad", "N/A");
			p.put("proveedor", "N/A");
		} else {
			if (!listaMedicinas.isEmpty()) {
				p.put("prioridad", listaMedicinas.get(0).getPrioridad());
				p.put("proveedor", listaMedicinas.get(0).getProveedor()
						.getNombre());
			}
		}
		p.put("edad",
				String.valueOf(calcularEdad(paciente.getFechaNacimiento())));
		p.put("pacienteNacimiento", paciente.getFechaNacimiento());

		JasperReport reporte = (JasperReport) JRLoader.loadObject(getClass()
				.getResource("/reporte/medico/consulta/RRecipeExamen.jasper"));
		fichero = JasperRunManager.runReportToPdf(reporte, p,
				new JRBeanCollectionDataSource(listaMedicinas));
		return fichero;
	}

	// Reporte de Consulta

	@Listen("onClick = #btnReporteConsulta")
	public void generarConsulta() {
		if (ltbConsultas.getItemCount() != 0) {
			if (ltbConsultas.getSelectedItems().size() == 1) {
				Listitem listItem = ltbConsultas.getSelectedItem();
				if (listItem != null) {
					Consulta consulta = listItem.getValue();
					Long id = consulta.getIdConsulta();
					Clients.evalJavaScript("window.open('"
							+ damePath()
							+ "Reportero?valor=5&valor2="
							+ id
							+ "','','top=100,left=200,height=600,width=800,scrollbars=1,resizable=1')");
				}
			} else
				Mensaje.mensajeError("Debe Seleccionar una Consulta");
		} else
			Mensaje.mensajeError("No existen Regitros de Consulta");

	}

	public byte[] reporteConsulta(Long part2) throws JRException {
		byte[] fichero = null;
		Consulta consuta = getServicioConsulta().buscar(part2);
		List<ConsultaDiagnostico> diagnosticoConsulta = getServicioConsultaDiagnostico()
				.buscarPorConsulta(consuta);

		List<ConsultaMedicina> medi = getServicioConsultaMedicina()
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
			diagnosticoConsulta.remove(0);
		}

		Paciente paciente = consuta.getPaciente();
		DoctorInterno user = consuta.getDoctorInterno();
		Map<String, Object> p = new HashMap<String, Object>();
		String nombreEmpresa = "Sin Empresa Asociada";
		String direccionEmpresa = "Direccion Faltante";
		String rifEmpresa = "Rif Faltante";
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
		if (diagnosticoConsulta.size() > 1)
			p.put("imprime", "Si");
		else
			p.put("imprime", "No");
		p.put("doctorNombre", consuta.getDoctor());
		p.put("fechaConsulta", consuta.getFechaConsulta());
		p.put("tipoConsulta", consuta.getTipoConsultaSecundaria());
		p.put("enfermedad", consuta.getEnfermedadActual());
		p.put("motivo", consuta.getMotivoConsulta());
		p.put("diagnostico", nombreDiagnostico);
		p.put("tipoDiagnostico", tipoDiagnostico);
		p.put("edad",
				String.valueOf(calcularEdad(paciente.getFechaNacimiento())));
		p.put("data", new JRBeanCollectionDataSource(medi));
		p.put("dataExamen", new JRBeanCollectionDataSource(examenes));
		p.put("dataEspecialista", new JRBeanCollectionDataSource(especialistas));
		p.put("dataEstudio", new JRBeanCollectionDataSource(estudis));

		JasperReport reporte = (JasperReport) JRLoader.loadObject(getClass()
				.getResource("/reporte/medico/consulta/RConsulta.jasper"));

		fichero = JasperRunManager.runReportToPdf(reporte, p,
				new JRBeanCollectionDataSource(diagnosticoConsulta));
		return fichero;
	}

	// Reporte historico de Consulta

	@Listen("onClick = #btnVerHistoria")
	public void generarHistoricoConsulta() {
		if (!idPaciente.equals("")) {
			String id = idPaciente;
			if (!servicioConsulta.buscarPorIdPacienteOrdenado(idPaciente)
					.isEmpty()) {
				Clients.evalJavaScript("window.open('"
						+ damePath()
						+ "Reportero?valor=6&valor3="
						+ id
						+ "','','top=100,left=200,height=600,width=800,scrollbars=1,resizable=1')");
			} else
				Mensaje.mensajeError("Este paciente no posee consultas");
		} else
			Mensaje.mensajeError("Debe Seleccionar un Paciente");
	}

	public byte[] reporteConsultaHistorico(String part2) throws JRException {
		byte[] fichero = null;
		List<Consulta> listaConsultas = getServicioConsulta()
				.buscarPorIdPacienteOrdenado(part2);
		for (int i = 0; i < listaConsultas.size(); i++) {

			String nombre = listaConsultas.get(i).getDoctorInterno()
					.getPrimerNombre();
			String apellido = listaConsultas.get(i).getDoctorInterno()
					.getPrimerApellido();
			Consulta consulta = listaConsultas.get(i);
			listaConsultas.get(i).setExamenPreempleo(nombre + " " + apellido);
			// String no = "No";
			// if (listaConsultas.get(i).isAccidenteLaboral())
			// no = "Si";
			// listaConsultas.get(i).setUsuarioAuditoria(no);
			List<ConsultaDiagnostico> diagnosticos = getServicioConsultaDiagnostico()
					.buscarPorConsulta(listaConsultas.get(i));

			// For de los diagnosticos

			String nombresDiagnosticos = "";
			String tipoDiagnosticos = "";
			for (int j = 0; j < diagnosticos.size(); j++) {
				String diag = diagnosticos.get(j).getDiagnostico().getNombre();
				String tipo = diagnosticos.get(j).getTipo();
				nombresDiagnosticos += "-" + diag + "\n";
				tipoDiagnosticos += "-" + tipo + "\n";
			}

			listaConsultas.get(i).setObservacion(nombresDiagnosticos);
			listaConsultas.get(i).setCondicionApto(tipoDiagnosticos);
		}

		Map<String, Object> p = new HashMap<String, Object>();
		p.put("pacienteNombre", listaConsultas.get(0).getPaciente()
				.getPrimerNombre()
				+ "   "
				+ listaConsultas.get(0).getPaciente().getSegundoNombre());
		p.put("pacienteApellido", listaConsultas.get(0).getPaciente()
				.getPrimerApellido()
				+ "   "
				+ listaConsultas.get(0).getPaciente().getSegundoApellido());
		p.put("pacienteCedula", listaConsultas.get(0).getPaciente().getCedula());
		p.put("pacienteNacimiento", listaConsultas.get(0).getPaciente()
				.getFechaNacimiento());
		p.put("edad",
				String.valueOf(calcularEdad(listaConsultas.get(0).getPaciente()
						.getFechaNacimiento())));
		JasperReport reporte = (JasperReport) JRLoader.loadObject(getClass()
				.getResource(
						"/reporte/medico/consulta/RConsultaHistorico.jasper"));
		fichero = JasperRunManager.runReportToPdf(reporte, p,
				new JRBeanCollectionDataSource(listaConsultas));
		return fichero;
	}

	// Reporte reposo

	@Listen("onClick = #btnGenerarReposo")
	public void generarReposo() {
		if (idConsulta != 0) {
			Long id = idConsulta;
			Consulta consulta = servicioConsulta.buscar(idConsulta);
			if (consulta.getReposo()) {
				Clients.evalJavaScript("window.open('"
						+ damePath()
						+ "Reportero?valor=7&valor2="
						+ id
						+ "','','top=100,left=200,height=600,width=800,scrollbars=1,resizable=1')");
			} else
				Mensaje.mensajeAlerta("En la Consulta no se indico reposo");
		} else
			Mensaje.mensajeAlerta("Debe registrar la Consulta");
	}

	public byte[] reporteReposo(Long part2) throws JRException {
		byte[] fichero = null;
		Consulta consuta = getServicioConsulta().buscar(part2);
		List<ConsultaDiagnostico> diagnosticoConsulta = getServicioConsultaDiagnostico()
				.buscarPorConsulta(consuta);
		List<ConsultaMedicina> medicinas = getServicioConsultaMedicina()
				.buscarPorConsulta(consuta);
		Paciente paciente = consuta.getPaciente();
		DoctorInterno user = consuta.getDoctorInterno();
		Map<String, Object> p = new HashMap<String, Object>();
		String nombreEmpresa = "Sin Empresa Asociada";
		String direccionEmpresa = "Direccion Faltante";
		String rifEmpresa = "Rif Faltante";
		String area = "";
		if (paciente.getEmpresa() != null) {
			nombreEmpresa = paciente.getEmpresa().getNombre();
			direccionEmpresa = paciente.getEmpresa().getDireccionCentro();
			rifEmpresa = paciente.getEmpresa().getRif();
		}
		if (paciente.getArea() != null) {
			area = paciente.getArea().getNombre();
		}
		Calendar c = Calendar.getInstance();
		if (consuta.getFechaReposo() != null)
			c.setTime(consuta.getFechaReposo());
		else
			c.setTime(consuta.getFechaConsulta());
		c.add(Calendar.DAY_OF_YEAR, consuta.getDiasReposo());
		Date fechaHasta = c.getTime();
		c.setTime(fechaHasta);
		c.add(Calendar.DAY_OF_YEAR, -1);
		fechaHasta = c.getTime();
		// Incluyendo fines de semana
		// if (c.get(Calendar.DAY_OF_WEEK) == 7)
		// c.add(Calendar.DAY_OF_YEAR, 2);
		// else {
		// if (c.get(Calendar.DAY_OF_WEEK) == 6)
		// c.add(Calendar.DAY_OF_YEAR, 3);
		// else
		// c.add(Calendar.DAY_OF_YEAR, 1);
		// }
		c.add(Calendar.DAY_OF_YEAR, 1);
		Date fechaReintegro = c.getTime();
		p.put("empresaNombre", nombreEmpresa);
		p.put("empresaDireccion", direccionEmpresa);
		p.put("empresaRif", rifEmpresa);
		p.put("pacienteNombre", paciente.getPrimerNombre());
		p.put("pacienteApellido", paciente.getPrimerApellido());
		if (paciente.isTrabajador()) {
			p.put("pacienteCedula", paciente.getFicha());
			p.put("tipoPaciente", "Trabajador");
		} else {
			p.put("pacienteCedula", paciente.getCedula());
			p.put("tipoPaciente", "Familiar");
		}
		p.put("doctorNombre", consuta.getDoctor());
		p.put("doctorApellido", user.getPrimerApellido());
		p.put("doctorCedula", user.getCedula());
		if (consuta.getFechaReposo() != null)
			p.put("fechaDesde", consuta.getFechaReposo());
		else
			p.put("fechaDesde", consuta.getFechaConsulta());
		// p.put("fechaDesde", consuta.getFechaConsulta());
		if (consuta.getTipoReposo() != null) {
			if (consuta.getTipoReposo().equals("Dias")) {
				p.put("tipoReposo", "Dias");
				p.put("fechaHasta", fechaHasta);
				p.put("fechaReintegro", fechaReintegro);
			} else {
				p.put("tipoReposo", "Horas");
				p.put("fechaHasta", consuta.getFechaReposo());
				p.put("fechaReintegro", consuta.getFechaReposo());
			}
		} else {
			p.put("tipoReposo", "Dias");
			p.put("fechaHasta", fechaHasta);
			p.put("fechaReintegro", fechaReintegro);
		}
		p.put("diasReposo", consuta.getDiasReposo());
		p.put("area", area);
		if (!diagnosticoConsulta.isEmpty()) {
			p.put("diag", diagnosticoConsulta.get(0).getDiagnostico()
					.getNombre());
			p.put("diagnostico", diagnosticoConsulta.get(0).getTipo());
		}

		JasperReport reporte = (JasperReport) JRLoader.loadObject(getClass()
				.getResource("/reporte/medico/consulta/RReposo.jasper"));
		fichero = JasperRunManager.runReportToPdf(reporte, p);
		return fichero;
	}

	@Listen("onClick = #btnConstancia")
	public void generarConstancia() {
		if (idConsulta != 0) {
			Long id = idConsulta;
			Clients.evalJavaScript("window.open('"
					+ damePath()
					+ "Reportero?valor=8&valor2="
					+ id
					+ "','','top=100,left=200,height=600,width=800,scrollbars=1,resizable=1')");
		} else
			Mensaje.mensajeAlerta("Debe registrar la Consulta");
	}

	public byte[] reporteConstancia(Long part2) throws JRException {
		byte[] fichero = null;
		Consulta consuta = getServicioConsulta().buscar(part2);
		Paciente paciente = consuta.getPaciente();
		DoctorInterno user = consuta.getDoctorInterno();
		Map<String, Object> p = new HashMap<String, Object>();
		String nombreEmpresa = "Sin Empresa Asociada";
		String direccionEmpresa = "Direccion Faltante";
		String rifEmpresa = "Rif Faltante";
		String area = "";
		if (paciente.getEmpresa() != null) {
			nombreEmpresa = paciente.getEmpresa().getNombre();
			direccionEmpresa = paciente.getEmpresa().getDireccionCentro();
			rifEmpresa = paciente.getEmpresa().getRif();
		}

		if (paciente.getArea() != null) {
			area = paciente.getArea().getNombre();
		}

		p.put("empresaNombre", nombreEmpresa);
		p.put("empresaDireccion", direccionEmpresa);
		p.put("empresaRif", rifEmpresa);
		p.put("pacienteNombre", paciente.getPrimerNombre());
		p.put("pacienteApellido", paciente.getPrimerApellido());
		p.put("doctorNombre", consuta.getDoctor());
		p.put("fecha", consuta.getFechaConsulta());
		p.put("area", area);
		p.put("pacienteCedula", paciente.getFicha());

		JasperReport reporte = (JasperReport) JRLoader.loadObject(getClass()
				.getResource("/reporte/medico/consulta/RConstancia.jasper"));
		fichero = JasperRunManager.runReportToPdf(reporte, p);
		return fichero;
	}

	@Listen("onClick = #btnPreEmpleo")
	public void reportePreempleo() {
		if (idConsulta != 0) {
			Long id = idConsulta;
			Clients.evalJavaScript("window.open('"
					+ damePath()
					+ "Reportero?valor=30&valor6="
					+ id
					+ "','','top=100,left=200,height=600,width=800,scrollbars=1,resizable=1')");
		} else
			Mensaje.mensajeAlerta("Seleccionar la Consulta");

	}

	public byte[] reporteroPreempleo(Long id) throws JRException {
		byte[] fichero = null;
		Consulta consuta = getServicioConsulta().buscar(id);
		Paciente paciente = consuta.getPaciente();

		String nombreEmpresa = "Sin Empresa Asociada";
		String direccionEmpresa = "Direccion Faltante";
		String rifEmpresa = "Rif Faltante";
		if (paciente.getEmpresa() != null)
			nombreEmpresa = paciente.getEmpresa().getNombre();
		Map<String, Object> p = new HashMap<String, Object>();
		p.put("empresaNombre", nombreEmpresa);
		p.put("pacienteNombre",
				paciente.getPrimerNombre() + " " + paciente.getPrimerApellido());
		p.put("fecha", consuta.getFechaConsulta());
		p.put("edad",
				String.valueOf(calcularEdad(paciente.getFechaNacimiento())));
		p.put("cedula", paciente.getCedula());
		p.put("sexo", paciente.getSexo());
		if (paciente.isDiscapacidad())
			p.put("discapacidad",
					"Si, Discapacidad" + " " + paciente.getTipoDiscapacidad());
		else
			p.put("discapacidad", "No");
		if (consuta.getCargoDeseado() != null)
			p.put("cargo", consuta.getCargoDeseado().getNombre());
		else
			p.put("cargo", "Sin Informacion");
		if (consuta.getAreaDeseada() != null)
			p.put("area", consuta.getAreaDeseada().getNombre());
		else
			p.put("area", "Sin Informacion");
		p.put("enfermedad", consuta.getEnfermedadActual());
		if (consuta.getEstatura() != null)
			p.put("estatura", consuta.getEstatura());
		if (consuta.getPeso() != null)
			p.put("peso", consuta.getPeso());

		Double imc = (double) 0;
		if (consuta.getEstatura() != 0 && consuta.getEstatura() != null
				&& consuta.getPeso() != null) {
			imc = (double) Math.round((consuta.getPeso() / (consuta
					.getEstatura() * consuta.getEstatura())) * 100) / 100;
		}
		if (imc != null)
			p.put("masa", imc);
		else
			p.put("masa", "");
		p.put("examenes", consuta.getExamenPreempleo());
		if (consuta.getApto())
			p.put("apto", "SI");
		else
			p.put("apto", "NO");

		List<ConsultaParteCuerpo> partes = new ArrayList<ConsultaParteCuerpo>();
		partes = getServicioConsultaParteCuerpo().buscarPorConsulta(consuta);

		if (partes.isEmpty())
			p.put("imprime", "no");
		else
			p.put("imprime", "si");

		if (consuta.getExamenPreempleo().equals(""))
			p.put("imprime2", "no");
		else
			p.put("imprime2", "si");

		JasperReport reporte = (JasperReport) JRLoader.loadObject(getClass()
				.getResource("/reporte/medico/consulta/RPreempleo.jasper"));
		fichero = JasperRunManager.runReportToPdf(reporte, p,
				new JRBeanCollectionDataSource(partes));
		return fichero;

	}

	@Listen("onClick = #btnFaltantes")
	public void mostrarCatalogoConsultasFaltantes() {

		final List<Paciente> pacientes = servicioPaciente
				.buscarPostVacacionalPendiente();
		catalogoPaciente2 = new Catalogo<Paciente>(divCatalogoPacientes2,
				"Catalogo de Pacientes con consulta post-vacacional pendiente",
				pacientes, false, "Cedula", "Ficha", "Primer Nombre",
				"Segundo Nombre", "Primer Apellido", "Segundo Apellido",
				"Trabajador Asociado") {

			@Override
			protected List<Paciente> buscar(String valor, String combo) {

				return pacientes;

			}

			@Override
			protected String[] crearRegistros(Paciente objeto) {
				String[] registros = new String[7];
				registros[0] = objeto.getCedula();
				registros[1] = objeto.getFicha();
				registros[2] = objeto.getPrimerNombre();
				registros[3] = objeto.getSegundoNombre();
				registros[4] = objeto.getPrimerApellido();
				registros[5] = objeto.getSegundoApellido();
				registros[6] = objeto.getCedulaFamiliar();
				return registros;
			}

		};
		catalogoPaciente2.setParent(divCatalogoPacientes2);
		Listbox lsita = (Listbox) catalogoPaciente2.getChildren().get(5);
		lsita.setEmptyMessage("Utilice el filtro para buscar el paciente que desea buscar");
		catalogoPaciente2.doModal();
	}
}
