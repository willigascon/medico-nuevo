package controlador.medico;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.ArrayList;
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
import modelo.medico.historia.Antecedente;
import modelo.medico.historia.Historia;
import modelo.medico.historia.HistoriaAccidente;
import modelo.medico.historia.HistoriaIntervencion;
import modelo.medico.historia.HistoriaVacuna;
import modelo.medico.historia.PacienteAntecedente;
import modelo.medico.maestro.Diagnostico;
import modelo.medico.maestro.DoctorInterno;
import modelo.medico.maestro.Especialista;
import modelo.medico.maestro.Examen;
import modelo.medico.maestro.Intervencion;
import modelo.medico.maestro.Medicina;
import modelo.medico.maestro.Paciente;
import modelo.medico.maestro.ParteCuerpo;
import modelo.medico.maestro.Proveedor;
import modelo.medico.maestro.ServicioExterno;
import modelo.medico.maestro.Vacuna;
import modelo.seguridad.Accidente;

import org.zkoss.zk.ui.Sessions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Button;
import org.zkoss.zul.Checkbox;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Div;
import org.zkoss.zul.Doublespinner;
import org.zkoss.zul.GroupsModel;
import org.zkoss.zul.Image;
import org.zkoss.zul.Label;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.ListitemRenderer;
import org.zkoss.zul.Radio;
import org.zkoss.zul.Radiogroup;
import org.zkoss.zul.Row;
import org.zkoss.zul.SimpleGroupsModel;
import org.zkoss.zul.Spinner;
import org.zkoss.zul.Tab;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.West;

import componente.Botonera;
import componente.Buscar;
import componente.Catalogo;
import componente.Mensaje;
import controlador.utils.CGenerico;

public class CHistoria extends CGenerico {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Wire
	private Div botoneraHistoria;
	@Wire
	private Button btnBuscarPaciente;
	@Wire
	private Div divCatalogoPacientes;
	@Wire
	private Div divHistoria;
	@Wire
	private Tab tabIdentificacion;
	//
	@Wire
	private Image imagenPaciente;
	@Wire
	private Label lblArea;
	@Wire
	private Label lblNombres;
	@Wire
	private Label lblCedula;
	@Wire
	private Label lblApellidos;
	@Wire
	private Label lblEmpresa;
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
	private Label lblCargo;
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
	private Label lblNombresE;
	@Wire
	private Label lblApellidosE;
	@Wire
	private Label lblParentesco;
	@Wire
	private Label lblTelefono1E;
	@Wire
	private Label lblTelefono2E;
	@Wire
	private Label lblCargo1;
	//
	@Wire
	private Radio rdoSiPeso;
	@Wire
	private Radio rdoNoPeso;
	@Wire
	private Textbox txtCantidadPeso;
	@Wire
	private Textbox txtCausasPeso;
	@Wire
	private Radio rdoSiCafe;
	@Wire
	private Radio rdoNoCafe;
	@Wire
	private Spinner spnCantidadCafe;
	@Wire
	private Radio rdoSiDuerme;
	@Wire
	private Radio rdoNoDuerme;
	@Wire
	private Radio rdoSiCabeza;
	@Wire
	private Radio rdoNoCabeza;
	@Wire
	private Radio rdoSiFisica;
	@Wire
	private Radio rdoNoFisica;
	@Wire
	private Textbox txtTipoFisica;
	@Wire
	private Textbox txtFrecuenciaFisica;
	@Wire
	private Textbox txtTiempoFisica;
	@Wire
	private Radio rdoSiExtra;
	@Wire
	private Radio rdoNoExtra;
	@Wire
	private Combobox cmbExtra;
	@Wire
	private Radio rdoSiCigarro;
	@Wire
	private Radio rdoNoCigarro;
	@Wire
	private Radio rdoSiFumaActualmente;
	@Wire
	private Radio rdoNoFumaActualmente;
	@Wire
	private Spinner spnNumeroCigarro;
	@Wire
	private Datebox dtbFechaFinCigarro;
	@Wire
	private Datebox dtbFechaInicioCigarro;
	@Wire
	private Textbox txtRazonCigarro;
	@Wire
	private Radio rdoSiAlcohol;
	@Wire
	private Radio rdoNoAlcohol;
	@Wire
	private Radio rdoSiMesAlcohol;
	@Wire
	private Radio rdoNoMesAlcohol;
	@Wire
	private Radiogroup rdgFrecuenciaAlcohol;
	@Wire
	private Textbox txtTipoAlcohol;
	@Wire
	private Spinner spnCantidadAlcohol;
	@Wire
	private Radio rdoSiBorracho;
	@Wire
	private Radio rdoNoBorracho;
	@Wire
	private Radio rdoSiAccidenteAlcohol;
	@Wire
	private Radio rdoNoAccidenteAlcohol;
	@Wire
	private Radio rdoSiTratamientoAlcohol;
	@Wire
	private Radio rdoNoTratamientoAlcohol;
	@Wire
	private Radio rdoSiRehabilitacionAlcohol;
	@Wire
	private Radio rdoNoRehabilitacionAlcohol;
	@Wire
	private Radio rdoSiDrogas;
	@Wire
	private Radio rdoNoDrogas;
	@Wire
	private Radio rdoSiTratamientoDrogas;
	@Wire
	private Radio rdoNoTratamientoDrogas;
	@Wire
	private Textbox txtExplicacionDrogas;
	@Wire
	private Radio rdoSiRehabilitacion;
	@Wire
	private Radio rdoNoRehabilitacion;
	@Wire
	private Radio rdoSiMedicamentoDrogas;
	@Wire
	private Radio rdoNoMedicamentoDrogas;
	@Wire
	private Textbox txtMedicamentoDroga;
	@Wire
	private Textbox txtCantidadMedicamento;
	@Wire
	private Datebox dtbFechaMedicamento;
	@Wire
	private Radio rdoSiEnfermedad;
	@Wire
	private Radio rdoNoEnfermedad;
	@Wire
	private Textbox txtAlgunaEnfermedad;
	@Wire
	private Radio rdoSiMedico;
	@Wire
	private Radio rdoNoMedico;
	@Wire
	private Radio rdoSiTratamiento;
	@Wire
	private Radio rdoNoTratamiento;
	@Wire
	private Radio rdoSiTransfusiones;
	@Wire
	private Radio rdoNoTransfusiones;
	@Wire
	private Radio rdoSiETS;
	@Wire
	private Radio rdoNoETS;
	@Wire
	private Textbox txtVIH;
	@Wire
	private Radio rdoSiFlujo;
	@Wire
	private Radio rdoNoFlujo;
	@Wire
	private Radio rdoSiPezones;
	@Wire
	private Radio rdoNoPezones;
	@Wire
	private Radio rdoSiMenstrual;
	@Wire
	private Radio rdoNoMenstrual;
	@Wire
	private Radio rdoSiEndurecimiento;
	@Wire
	private Radio rdoNoEndurecimiento;
	@Wire
	private Radio rdoSiInfeccion;
	@Wire
	private Radio rdoNoInfeccion;
	@Wire
	private Radio rdoSiAnticonceptivos;
	@Wire
	private Radio rdoNoAnticonceptivos;
	@Wire
	private Radio rdoSiDolor;
	@Wire
	private Radio rdoNoDolor;
	@Wire
	private Radio rdoSiEsterilizacion;
	@Wire
	private Radio rdoNoEsterilizacion;
	@Wire
	private Radio rdoSiIntra;
	@Wire
	private Radio rdoNoIntra;
	@Wire
	private Radio rdoSiVIH;
	@Wire
	private Radio rdoNoVIH;
	@Wire
	private Spinner spnEdadDesarrollo;
	@Wire
	private Datebox dtbFechaUltima;
	@Wire
	private Spinner spnEmbarazos;
	@Wire
	private Spinner spnPartos;
	@Wire
	private Spinner spnCesareas;
	@Wire
	private Spinner spnAbortos;
	@Wire
	private Datebox dtbFechaUltimaCito;
	@Wire
	private Radio rdoSiOvarios;
	@Wire
	private Radio rdoNoOvarios;
	@Wire
	private Radio rdoSiEmbarazada;
	@Wire
	private Radio rdoNoEmbarazada;
	@Wire
	private Spinner spnGestacion;
	@Wire
	private Radio rdoSiEco;
	@Wire
	private Radio rdoNoEco;
	@Wire
	private Radio rdoSiMamografia;
	@Wire
	private Radio rdoNoMamografia;
	@Wire
	private Textbox txtResultadoEco;
	@Wire
	private Textbox txtResultadoMamografia;
	@Wire
	private Listbox ltbVacunas;
	@Wire
	private Listbox ltbAccidentesLaborales;
	@Wire
	private Listbox ltbAccidentesLaboralesAgregados;
	@Wire
	private Listbox ltbAccidentesComunes;
	@Wire
	private Listbox ltbAccidentesComunesAgregados;
	@Wire
	private Listbox ltbIntervenciones;
	@Wire
	private Listbox ltbIntervencionesAgregadas;
	// --------------------------
	@Wire
	private Combobox cmbDiente1;
	@Wire
	private Combobox cmbDiente2;
	@Wire
	private Combobox cmbDiente3;
	@Wire
	private Combobox cmbDiente4;
	@Wire
	private Combobox cmbDiente5;
	@Wire
	private Combobox cmbDiente6;
	@Wire
	private Combobox cmbDiente7;
	@Wire
	private Combobox cmbDiente8;
	@Wire
	private Combobox cmbDiente9;
	@Wire
	private Combobox cmbDiente10;
	@Wire
	private Combobox cmbDiente11;
	@Wire
	private Combobox cmbDiente12;
	@Wire
	private Combobox cmbDiente13;
	@Wire
	private Combobox cmbDiente14;
	@Wire
	private Combobox cmbDiente15;
	@Wire
	private Combobox cmbDiente16;
	@Wire
	private Combobox cmbDiente17;
	@Wire
	private Combobox cmbDiente18;
	@Wire
	private Combobox cmbDiente19;
	@Wire
	private Combobox cmbDiente20;
	@Wire
	private Combobox cmbDiente21;
	@Wire
	private Combobox cmbDiente22;
	@Wire
	private Combobox cmbDiente23;
	@Wire
	private Combobox cmbDiente24;
	@Wire
	private Combobox cmbDiente25;
	@Wire
	private Combobox cmbDiente26;
	@Wire
	private Combobox cmbDiente27;
	@Wire
	private Combobox cmbDiente28;
	@Wire
	private Combobox cmbDiente29;
	@Wire
	private Combobox cmbDiente30;
	@Wire
	private Combobox cmbDiente31;
	@Wire
	private Combobox cmbDiente32;
	@Wire
	private Combobox cmbEspecialista;
	@Wire
	private Textbox txtTelefonoDoc;
	@Wire
	private Combobox cmbCarta;
	@Wire
	private Radio rdoSiColores;
	@Wire
	private Radio rdoNoColores;
	@Wire
	private Doublespinner spnAlturaHombro;
	@Wire
	private Doublespinner spnAnchuraHombro;
	@Wire
	private Doublespinner spnAlturaCodo;
	@Wire
	private Doublespinner spnLongIzquierdo;
	@Wire
	private Doublespinner spnLongDerecho;
	@Wire
	private Doublespinner spnPoplitea;
	@Wire
	private Doublespinner spnSillaOjo;
	@Wire
	private Doublespinner spnCodoSilla;
	@Wire
	private Doublespinner spnCircunferenciaA;
	@Wire
	private Doublespinner spnManoPiso;
	@Wire
	private Doublespinner spnCircunferenciaC;
	@Wire
	private Doublespinner spnCaderaAbdominal;
	@Wire
	private Spinner spnEdadPediatrica;
	@Wire
	private Spinner spnGestacionPediatrica;
	@Wire
	private Spinner spnSemanasPediatrica;
	@Wire
	private Radio rdoSiComplicacion;
	@Wire
	private Radio rdoNoComplicacion;
	@Wire
	private Textbox txtResultadoComplicacion;
	@Wire
	private Checkbox chkVdrl;
	@Wire
	private Checkbox chkVih;
	@Wire
	private Checkbox chkTox;
	@Wire
	private Textbox txtSerologia;
	@Wire
	private Radio rdoSiVagina;
	@Wire
	private Radio rdoNoVagina;
	@Wire
	private Textbox txtCausaCesarea;
	@Wire
	private Doublespinner spnPesoPediatrica;
	@Wire
	private Doublespinner spnTallaPediatrica;
	@Wire
	private Radio rdoSiComplicacionNeo;
	@Wire
	private Radio rdoNoComplicacionNeo;
	@Wire
	private Textbox txtResultadoComplicacionNeo;
	@Wire
	private Textbox txtObservacionPrenatal;
	//
	@Wire
	private Button btnAbrirAntecedente3;
	@Wire
	private Button btnAbrirAntecedente2;
	@Wire
	private Button btnAbrirAntecedente1;
	@Wire
	private Textbox txtBuscadorIntervencion;
	@Wire
	private Textbox txtBuscadorAccidenteComun;
	@Wire
	private Textbox txtBuscadorAccidenteLaboral;
	@Wire
	private Textbox txtCedula;
	//
	@Wire
	private Listbox ltbMedicos;
	@Wire
	private Listbox ltbLaborales;
	@Wire
	private Listbox ltbFamiliares;
	List<Listbox> listas = new ArrayList<Listbox>();

	List<Intervencion> intervencionesDisponibles = new ArrayList<Intervencion>();
	List<HistoriaIntervencion> intervencionesAgregadas = new ArrayList<HistoriaIntervencion>();

	List<Accidente> accidentesLaboralesDisponibles = new ArrayList<Accidente>();
	List<HistoriaAccidente> accidentesLaboralesAgregadas = new ArrayList<HistoriaAccidente>();

	List<Accidente> accidentesComunesDisponibles = new ArrayList<Accidente>();
	List<HistoriaAccidente> accidentesComunesAgregadas = new ArrayList<HistoriaAccidente>();

	String idPaciente = "";
	Catalogo<Paciente> catalogoPaciente;
	private String[] tipoDiente = { "Normal", "Protesis", "Amalgama",
			"Ausencia" };
	GroupsModel<Antecedente, Object, Antecedente> model;
	GroupsModel<Antecedente, Object, Antecedente> modelo;
	GroupsModel<Antecedente, Object, Antecedente> modelFamiliares;
	ListModelList<Vacuna> modelVacunas;
	ListModelList<String> dientes;
	ListitemRenderer<?> renderer;
	Buscar<Accidente> buscarAccidenteLaboral;
	Buscar<Accidente> buscarAccidenteComun;
	Buscar<Intervencion> buscarIntervencion;

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
		listas.add(ltbLaborales);
		listas.add(ltbFamiliares);
		listas.add(ltbMedicos);
		listas.add(ltbVacunas);
		listas.add(ltbAccidentesComunes);
		listas.add(ltbAccidentesComunesAgregados);
		listas.add(ltbAccidentesLaborales);
		listas.add(ltbAccidentesLaboralesAgregados);
		listas.add(ltbIntervenciones);
		listas.add(ltbIntervencionesAgregadas);
		llenarListas();
		listasMultiples();
		buscadorLaborales();
		buscadorComunes();
		buscadorIntervenciones();
		Botonera botonera = new Botonera() {

			@Override
			public void salir() {
				cerrarVentana(divHistoria, titulo, tabs);
			}

			@Override
			public void limpiar() {
				limpiarCampos();
			}

			@Override
			public void guardar() {
				if (validarHistoria()) {
					Paciente paciente = servicioPaciente
							.buscarPorCedula(idPaciente);
					guardarHistoria(paciente);
					limpiar();
					Mensaje.mensajeInformacion(Mensaje.guardado);
				}
			}

			@Override
			public void eliminar() {
			}
		};
		botonera.getChildren().get(1).setVisible(false);
		botoneraHistoria.appendChild(botonera);
	}

	public GroupsModel<Antecedente, Object, Antecedente> getModelFamiliares() {
		List<Antecedente> antecedentesLaborales = servicioAntecedente
				.buscarFamiliares();
		Map<String, Object> map = new HashMap<String, Object>();
		List<Antecedente> tipos = new ArrayList<Antecedente>();
		List<List<Antecedente>> ante = new ArrayList<List<Antecedente>>();
		map = listasModelo(antecedentesLaborales);
		tipos = (List<Antecedente>) map.get("Tipos");
		ante = (List<List<Antecedente>>) map.get("ListaDoble");
		modelFamiliares = new SimpleGroupsModel<Antecedente, Antecedente, Antecedente, Antecedente>(
				ante, tipos);
		return modelFamiliares;
	}

	public GroupsModel<?, ?, ?> getModel() {
		List<Antecedente> antecedentesLaborales = servicioAntecedente
				.buscarLaborales();
		Map<String, Object> map = new HashMap<String, Object>();
		List<Antecedente> tipos = new ArrayList<Antecedente>();
		List<List<Antecedente>> ante = new ArrayList<List<Antecedente>>();
		map = listasModelo(antecedentesLaborales);
		tipos = (List<Antecedente>) map.get("Tipos");
		ante = (List<List<Antecedente>>) map.get("ListaDoble");
		model = new SimpleGroupsModel<Antecedente, Antecedente, Antecedente, Antecedente>(
				ante, tipos);
		return model;
	}

	public GroupsModel<?, ?, ?> getModelo() {
		List<Antecedente> antecedentesMedicos = servicioAntecedente
				.buscarMedicos();
		Map<String, Object> map = new HashMap<String, Object>();
		List<Antecedente> tipos = new ArrayList<Antecedente>();
		List<List<Antecedente>> ante = new ArrayList<List<Antecedente>>();
		map = listasModelo(antecedentesMedicos);
		tipos = (List<Antecedente>) map.get("Tipos");
		ante = (List<List<Antecedente>>) map.get("ListaDoble");
		modelo = new SimpleGroupsModel<Antecedente, Antecedente, Antecedente, Antecedente>(
				ante, tipos);
		return modelo;
	}

	public Map<String, Object> listasModelo(List<Antecedente> antecedentes) {
		List<Antecedente> tipos = new ArrayList<Antecedente>();
		List<List<Antecedente>> ante = new ArrayList<List<Antecedente>>();
		long id = 0;
		for (int i = 0; i < antecedentes.size(); i++) {
			long id2 = antecedentes.get(i).getAntecedenteTipo()
					.getIdAntecedenteTipo();
			if (id2 != id) {
				id = id2;
				tipos.add(antecedentes.get(i));
				List<Antecedente> lista = new ArrayList<Antecedente>();
				ante.add(lista);
			}
		}
		for (int i = 0; i < tipos.size(); i++) {
			long a = tipos.get(i).getAntecedenteTipo().getIdAntecedenteTipo();
			for (int j = 0; j < antecedentes.size(); j++) {
				if (a == antecedentes.get(j).getAntecedenteTipo()
						.getIdAntecedenteTipo()) {
					ante.get(i).add(antecedentes.get(j));
				}
			}
		}
		final HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("Tipos", tipos);
		map.put("ListaDoble", ante);
		return map;
	}

	public ListitemRenderer<?> getRenderer() {
		renderer = new ListitemRenderer<Antecedente>() {
			long id = 0;

			@Override
			public void render(Listitem arg0, Antecedente arg1, int arg2)
					throws Exception {
				boolean tipoAntecedente = false;
				if (id == arg1.getAntecedenteTipo().getIdAntecedenteTipo()) {
					arg0.setValue(arg1);
					arg0.setContext(String.valueOf(arg1.getIdAntecedente()));
					Listcell list2 = new Listcell(arg1.getNombre());
					list2.setParent(arg0);
				} else {
					tipoAntecedente = true;
					id = arg1.getAntecedenteTipo().getIdAntecedenteTipo();
					arg0.setValue(arg1.getAntecedenteTipo());
					Listcell list2 = new Listcell(arg1.getAntecedenteTipo()
							.getNombre());
					list2.setParent(arg0);
					arg0.setCheckable(false);
					list2.setStyle("text-align:center; font-weight:bold; background:#FDFCDB; color:black");
					arg0.setStyle("text-align:center; font-weight:bold; background:#FDFCDB; color:black");
				}

				Listcell list3 = new Listcell();
				Textbox tex = new Textbox("");
				tex.setPlaceholder("Ingrese una Observacion");
				tex.setWidth("100%");
				tex.setParent(list3);
				list3.setParent(arg0);

				if (tipoAntecedente) {
					list3.setVisible(false);
					list3.setStyle("text-align:center; font-weight:bold; background:#FDFCDB; color:black");
					arg0.setStyle("text-align:center; font-weight:bold; background:#FDFCDB; color:black");
				}

			}
		};
		return renderer;
	}

	protected void guardarHistoria(Paciente paciente) {
		long idHistoria = 0;
		Historia historia = servicioHistoria.buscarPorPaciente(paciente);
		if (historia != null) {
			idHistoria = historia.getIdHistoria();
			servicioHistoriaAccidente.limpiar(historia);
			servicioHistoriaVacuna.limpiar(historia);
			servicioHistoriaIntervencion.limpiar(historia);
		}
		boolean valorPeso = false;
		if (rdoSiPeso.isChecked())
			valorPeso = true;
		boolean accidenteAlcohol = false;
		if (rdoSiAccidenteAlcohol.isChecked())
			accidenteAlcohol = true;
		boolean alcohol = false;
		if (rdoSiAlcohol.isChecked())
			alcohol = true;
		boolean anticonceptivos = false;
		if (rdoSiAnticonceptivos.isChecked())
			anticonceptivos = true;
		boolean borracho = false;
		if (rdoSiBorracho.isChecked())
			borracho = true;
		boolean cabeza = false;
		if (rdoSiCabeza.isChecked())
			cabeza = true;
		boolean cafe = false;
		if (rdoSiCafe.isChecked())
			cafe = true;
		boolean cigarro = false;
		if (rdoSiCigarro.isChecked())
			cigarro = true;
		boolean dolorSexo = false;
		if (rdoSiDolor.isChecked())
			dolorSexo = true;
		boolean drogas = false;
		if (rdoSiDrogas.isChecked())
			drogas = true;
		boolean dormilon = false;
		if (rdoSiDuerme.isChecked())
			dormilon = true;
		boolean eco = false;
		if (rdoSiEco.isChecked())
			eco = true;
		boolean embarazo = false;
		if (rdoSiEmbarazada.isChecked())
			embarazo = true;
		boolean endurecimiento = false;
		if (rdoSiEndurecimiento.isChecked())
			endurecimiento = true;
		boolean enfermedad = false;
		if (rdoSiEnfermedad.isChecked())
			enfermedad = true;
		boolean esterilizacion = false;
		if (rdoSiEsterilizacion.isChecked())
			esterilizacion = true;
		boolean ets = false;
		if (rdoSiETS.isChecked())
			ets = true;
		boolean extra = false;
		if (rdoSiExtra.isChecked())
			extra = true;
		boolean fisica = false;
		if (rdoSiFisica.isChecked())
			fisica = true;
		boolean flujo = false;
		if (rdoSiFlujo.isChecked())
			flujo = true;
		boolean fumaActual = false;
		if (rdoSiFumaActualmente.isChecked())
			fumaActual = true;
		boolean infeccion = false;
		if (rdoSiInfeccion.isChecked())
			infeccion = true;
		boolean intra = false;
		if (rdoSiIntra.isChecked())
			intra = true;
		boolean mamografia = false;
		if (rdoSiMamografia.isChecked())
			mamografia = true;
		boolean medicamento = false;
		if (rdoSiMedicamentoDrogas.isChecked())
			medicamento = true;
		boolean medico = false;
		if (rdoSiMedico.isChecked())
			medico = true;
		boolean menstrual = false;
		if (rdoSiMenstrual.isChecked())
			menstrual = true;
		boolean alcoholActual = false;
		if (rdoSiMesAlcohol.isChecked())
			alcoholActual = true;
		boolean ovarios = false;
		if (rdoSiOvarios.isChecked())
			ovarios = true;
		boolean pezones = false;
		if (rdoSiPezones.isChecked())
			pezones = true;
		boolean rehabilitacion = false;
		if (rdoSiRehabilitacion.isChecked())
			rehabilitacion = true;
		boolean rehabilitacionAlcohol = false;
		if (rdoSiRehabilitacionAlcohol.isChecked())
			rehabilitacionAlcohol = true;
		boolean transfusion = false;
		if (rdoSiTransfusiones.isChecked())
			transfusion = true;
		boolean tratamiento = false;
		if (rdoSiTratamiento.isChecked())
			tratamiento = true;
		boolean tratamientoAlcochol = false;
		if (rdoSiTratamientoAlcohol.isChecked())
			tratamientoAlcochol = true;
		boolean tratamientoDrogas = false;
		if (rdoSiTratamientoDrogas.isChecked())
			tratamientoDrogas = true;
		boolean vih = false;
		if (rdoSiVIH.isChecked())
			vih = true;
		boolean colores = false;
		if (rdoSiColores.isChecked())
			colores = true;
		String carta = cmbCarta.getValue();
		String telefonoOdontologo = txtTelefonoDoc.getValue();
		String a = cmbDiente1.getValue();
		String b = cmbDiente2.getValue();
		String c = cmbDiente3.getValue();
		String d = cmbDiente4.getValue();
		String e = cmbDiente5.getValue();
		String f = cmbDiente6.getValue();
		String g = cmbDiente7.getValue();
		String h = cmbDiente8.getValue();
		String i = cmbDiente9.getValue();
		String j = cmbDiente10.getValue();
		String k = cmbDiente11.getValue();
		String l = cmbDiente12.getValue();
		String m = cmbDiente13.getValue();
		String n = cmbDiente14.getValue();
		String o = cmbDiente15.getValue();
		String p = cmbDiente16.getValue();
		String q = cmbDiente17.getValue();
		String r = cmbDiente18.getValue();
		String s = cmbDiente19.getValue();
		String t = cmbDiente20.getValue();
		String u = cmbDiente21.getValue();
		String v = cmbDiente22.getValue();
		String w = cmbDiente23.getValue();
		String x = cmbDiente24.getValue();
		String y = cmbDiente25.getValue();
		String z = cmbDiente26.getValue();
		String za = cmbDiente27.getValue();
		String zb = cmbDiente28.getValue();
		String zc = cmbDiente29.getValue();
		String zd = cmbDiente32.getValue();
		String ze = cmbDiente30.getValue();
		String zf = cmbDiente31.getValue();
		double alturaHombro = spnAlturaHombro.getValue();
		double alturaCodo = spnAlturaCodo.getValue();
		double anchuraHombro = spnAnchuraHombro.getValue();
		double manoPiso = spnManoPiso.getValue();
		double derecho = spnLongDerecho.getValue();
		double izquierdo = spnLongIzquierdo.getValue();
		double indice = spnCaderaAbdominal.getValue();
		double circunferenciaAbdominal = spnCircunferenciaA.getValue();
		double circunferenciaCadera = spnCircunferenciaC.getValue();
		double codoSilla = spnCodoSilla.getValue();
		double ojo = spnSillaOjo.getValue();
		double alturaPop = spnPoplitea.getValue();
		int abortos = spnAbortos.getValue();
		int cantidadAlcohol = spnCantidadAlcohol.getValue();
		int tazas = spnCantidadCafe.getValue();
		int cesareas = spnCesareas.getValue();
		int edadDesarrollo = spnEdadDesarrollo.getValue();
		int embarazos = spnEmbarazos.getValue();
		int semanasGestando = spnGestacion.getValue();
		int cantidadCigarros = spnNumeroCigarro.getValue();
		int partos = spnPartos.getValue();
		String vihResultado = txtVIH.getValue();
		String algunaEnfermedad = txtAlgunaEnfermedad.getValue();
		String cantidadMedicamento = txtCantidadMedicamento.getValue();
		String cantidadPeso = txtCantidadPeso.getValue();
		String causasPeso = txtCausasPeso.getValue();
		String explicacionDrogas = txtExplicacionDrogas.getValue();
		String frecuenciaFisica = txtFrecuenciaFisica.getValue();
		String medicamentoDroga = txtMedicamentoDroga.getValue();
		String razonCigarro = txtRazonCigarro.getValue();
		String resultadoEco = txtResultadoEco.getValue();
		String resultadoMamografia = txtResultadoMamografia.getValue();
		String tiempoFisica = txtTiempoFisica.getValue();
		String tipoAlcohol = txtTipoAlcohol.getValue();
		String tipoFisica = txtTipoFisica.getValue();
		Date fechaFinC = dtbFechaFinCigarro.getValue();
		Timestamp fechaFinCigarro = new Timestamp(fechaFinC.getTime());
		Date fechaInicioC = dtbFechaInicioCigarro.getValue();
		Timestamp fechaInicioCigarro = new Timestamp(fechaInicioC.getTime());
		Date fechaMed = dtbFechaMedicamento.getValue();
		Timestamp fechaMedicamento = new Timestamp(fechaMed.getTime());
		Date fechaUltimaM = dtbFechaUltima.getValue();
		Timestamp fechaUltimaMenstruacion = new Timestamp(
				fechaUltimaM.getTime());
		Date fechaUltimaC = dtbFechaUltimaCito.getValue();
		Timestamp fechaUltimaCitologia = new Timestamp(fechaUltimaC.getTime());
		String actividadExtra = cmbExtra.getValue();
		String frecuenciaAlcohol = "";
		if (rdgFrecuenciaAlcohol.getSelectedItem() != null)
			frecuenciaAlcohol = rdgFrecuenciaAlcohol.getSelectedItem()
					.getLabel();

		int edadPediatrica = spnEdadPediatrica.getValue();
		int gestacionPediatrica = spnGestacionPediatrica.getValue();
		int semanasPediatrica = spnSemanasPediatrica.getValue();
		double talla = spnTallaPediatrica.getValue();
		double peso = spnPesoPediatrica.getValue();
		boolean complicacionEmbarazo = false;
		if (rdoSiComplicacion.isChecked())
			complicacionEmbarazo = true;
		String complicacionEmbarazoDescripcion = txtResultadoComplicacion
				.getValue();
		boolean vihPediatrico = false;
		if (chkVih.isChecked())
			vihPediatrico = true;
		boolean vdrlPediatrico = false;
		if (chkVdrl.isChecked())
			vdrlPediatrico = true;
		boolean toxoPediatrico = false;
		if (chkTox.isChecked())
			toxoPediatrico = true;
		String resultadoSero = txtSerologia.getValue();
		boolean parido = false;
		if (rdoSiVagina.isChecked())
			parido = true;
		String resultadoCesarea = txtCausaCesarea.getValue();
		String complicacionNeoResultado = txtResultadoComplicacionNeo
				.getValue();
		String observacionPrenatal = txtObservacionPrenatal.getValue();
		boolean complicacionNeo = false;
		if (rdoSiComplicacionNeo.isChecked())
			complicacionNeo = true;
		Historia historiaGuardada = new Historia(idHistoria, paciente,
				valorPeso, cantidadPeso, causasPeso, cafe, tazas, dormilon,
				cabeza, fisica, tipoFisica, frecuenciaFisica, tiempoFisica,
				extra, actividadExtra, cigarro, fumaActual, cantidadCigarros,
				fechaInicioCigarro, fechaFinCigarro, razonCigarro, alcohol,
				alcoholActual, frecuenciaAlcohol, tipoAlcohol, cantidadAlcohol,
				borracho, tratamientoAlcochol, rehabilitacionAlcohol,
				accidenteAlcohol, drogas, explicacionDrogas, tratamientoDrogas,
				rehabilitacion, medicamento, medicamentoDroga,
				fechaMedicamento, cantidadMedicamento, enfermedad,
				algunaEnfermedad, medico, tratamiento, transfusion, ets, vih,
				vihResultado, flujo, pezones, menstrual, endurecimiento,
				infeccion, anticonceptivos, dolorSexo, esterilizacion, intra,
				edadDesarrollo, fechaUltimaMenstruacion, embarazos, partos,
				cesareas, abortos, fechaUltimaCitologia, ovarios, embarazo,
				semanasGestando, eco, resultadoEco, mamografia,
				resultadoMamografia, metodoHora(), metodoFecha(),
				nombreUsuarioSesion(), a, b, c, d, e, f, g, h, i, j, k, l, m,
				n, o, p, q, r, s, t, u, v, w, x, y, z, za, zb, zc, zd, ze, zf,
				carta, colores, telefonoOdontologo, alturaHombro,
				anchuraHombro, alturaCodo, izquierdo, derecho, alturaPop, ojo,
				codoSilla, circunferenciaAbdominal, circunferenciaCadera,
				manoPiso, indice, edadPediatrica, gestacionPediatrica,
				semanasPediatrica, complicacionEmbarazo,
				complicacionEmbarazoDescripcion, vdrlPediatrico, vihPediatrico,
				toxoPediatrico, resultadoSero, parido, resultadoCesarea, peso,
				talla, complicacionNeo, complicacionNeoResultado,
				observacionPrenatal);
		servicioHistoria.guardar(historiaGuardada);
		if (idHistoria != 0)
			historiaGuardada = servicioHistoria.buscar(idHistoria);
		else
			historiaGuardada = servicioHistoria.buscarUltima();
		guardarAccidentes(historiaGuardada);
		guardarIntervenciones(historiaGuardada);
		guardarVacunas(historiaGuardada);
		guardarAntecedentes(paciente);

	}

	private void guardarAccidentes(Historia historiaGuardada) {
		List<HistoriaAccidente> historialAccidentes = new ArrayList<HistoriaAccidente>();
		for (int i = 0; i < ltbAccidentesComunesAgregados.getItemCount(); i++) {
			Listitem listItem = ltbAccidentesComunesAgregados.getItemAtIndex(i);
			long id = ((Spinner) ((listItem.getChildren().get(6)))
					.getFirstChild()).getValue();
			Accidente accidente = servicioAccidente.buscar(id);
			Date fechaA = ((Datebox) ((listItem.getChildren().get(1)))
					.getFirstChild()).getValue();
			Timestamp fechaAccidente = new Timestamp(fechaA.getTime());
			String lugar = ((Textbox) ((listItem.getChildren().get(2)))
					.getFirstChild()).getValue();
			String tipoAccidente = ((Textbox) ((listItem.getChildren().get(3)))
					.getFirstChild()).getValue();
			int reposo = ((Spinner) ((listItem.getChildren().get(4)))
					.getFirstChild()).getValue();
			String secuelas = ((Textbox) ((listItem.getChildren().get(5)))
					.getFirstChild()).getValue();
			HistoriaAccidente historiaAccidente = new HistoriaAccidente(
					historiaGuardada, accidente, fechaAccidente, lugar,
					tipoAccidente, reposo, secuelas, "Accidente Comun");
			historialAccidentes.add(historiaAccidente);
		}

		for (int i = 0; i < ltbAccidentesLaboralesAgregados.getItemCount(); i++) {
			Listitem listItem = ltbAccidentesLaboralesAgregados
					.getItemAtIndex(i);
			long id = ((Spinner) ((listItem.getChildren().get(6)))
					.getFirstChild()).getValue();
			Accidente accidente = servicioAccidente.buscar(id);
			Date fechaA = ((Datebox) ((listItem.getChildren().get(1)))
					.getFirstChild()).getValue();
			Timestamp fechaAccidente = new Timestamp(fechaA.getTime());
			String lugar = ((Textbox) ((listItem.getChildren().get(2)))
					.getFirstChild()).getValue();
			String tipoAccidente = ((Textbox) ((listItem.getChildren().get(3)))
					.getFirstChild()).getValue();
			int reposo = ((Spinner) ((listItem.getChildren().get(4)))
					.getFirstChild()).getValue();
			String secuelas = ((Textbox) ((listItem.getChildren().get(5)))
					.getFirstChild()).getValue();
			HistoriaAccidente historiaAccidente = new HistoriaAccidente(
					historiaGuardada, accidente, fechaAccidente, lugar,
					tipoAccidente, reposo, secuelas, "Accidente Laboral");
			historialAccidentes.add(historiaAccidente);
		}
		servicioHistoriaAccidente.guardar(historialAccidentes);
	}

	private void guardarIntervenciones(Historia historiaGuardada) {
		List<HistoriaIntervencion> historialIntervenciones = new ArrayList<HistoriaIntervencion>();
		for (int i = 0; i < ltbIntervencionesAgregadas.getItemCount(); i++) {
			Listitem listItem = ltbIntervencionesAgregadas.getItemAtIndex(i);
			long id = ((Spinner) ((listItem.getChildren().get(6)))
					.getFirstChild()).getValue();
			Intervencion intervencion = servicioIntervencion.buscar(id);
			Date fechaI = ((Datebox) ((listItem.getChildren().get(1)))
					.getFirstChild()).getValue();
			Timestamp fechaIntervencion = new Timestamp(fechaI.getTime());
			String motivo = ((Textbox) ((listItem.getChildren().get(2)))
					.getFirstChild()).getValue();
			String diagnostico = ((Textbox) ((listItem.getChildren().get(3)))
					.getFirstChild()).getValue();
			int reposo = ((Spinner) ((listItem.getChildren().get(4)))
					.getFirstChild()).getValue();
			String secuelas = ((Textbox) ((listItem.getChildren().get(5)))
					.getFirstChild()).getValue();
			HistoriaIntervencion historiaIntervencion = new HistoriaIntervencion(
					historiaGuardada, intervencion, fechaIntervencion, motivo,
					diagnostico, reposo, secuelas);
			historialIntervenciones.add(historiaIntervencion);
		}
		servicioHistoriaIntervencion.guardar(historialIntervenciones);
	}

	private void guardarVacunas(Historia historiaGuardada) {
		List<HistoriaVacuna> vacunas = new ArrayList<HistoriaVacuna>();
		if (ltbVacunas.getItemCount() != 0) {
			for (int i = 0; i < ltbVacunas.getItemCount(); i++) {
				Listitem listItem = ltbVacunas.getItemAtIndex(i);
				if (listItem.isSelected()) {
					Vacuna vacuna = listItem.getValue();
					Datebox text = (Datebox) listItem.getChildren().get(1)
							.getChildren().get(0);
					Date fechas = text.getValue();
					Timestamp fechaVacuna = new Timestamp(fechas.getTime());
					HistoriaVacuna vacunaHistoria = new HistoriaVacuna(
							historiaGuardada, vacuna, fechaVacuna);
					vacunas.add(vacunaHistoria);
				}
			}
			servicioHistoriaVacuna.guardar(vacunas);
		}
	}

	private void guardarAntecedentes(Paciente paciente) {
		List<PacienteAntecedente> antecedentes = new ArrayList<PacienteAntecedente>();
		servicioPacienteAntecedente.borrarAntecedentesPaciente(paciente);
		if (ltbLaborales.getItemCount() != 0) {
			for (int i = 0; i < ltbLaborales.getItemCount(); i++) {
				Listitem listItem = ltbLaborales.getItemAtIndex(i);
				if (listItem.isSelected() && listItem.getContext() != null) {
					Antecedente antecedente = listItem.getValue();
					Textbox text = (Textbox) listItem.getChildren().get(1)
							.getChildren().get(0);
					String observacion = text.getValue();
					PacienteAntecedente pacienteAntecedente = new PacienteAntecedente(
							paciente, antecedente, observacion);
					antecedentes.add(pacienteAntecedente);
				}
			}
		}

		if (ltbFamiliares.getItemCount() != 0) {
			for (int i = 0; i < ltbFamiliares.getItemCount(); i++) {
				Listitem listItem = ltbFamiliares.getItemAtIndex(i);
				if (listItem.isSelected() && listItem.getContext() != null) {
					Antecedente antecedente = listItem.getValue();
					Textbox text = (Textbox) listItem.getChildren().get(1)
							.getChildren().get(0);
					String observacion = text.getValue();
					PacienteAntecedente pacienteAntecedente = new PacienteAntecedente(
							paciente, antecedente, observacion);
					antecedentes.add(pacienteAntecedente);
				}
			}
		}

		if (ltbMedicos.getItemCount() != 0) {
			for (int i = 0; i < ltbMedicos.getItemCount(); i++) {
				Listitem listItem = ltbMedicos.getItemAtIndex(i);
				if (listItem.isSelected() && listItem.getContext() != null) {
					Antecedente antecedente = listItem.getValue();
					Textbox text = (Textbox) listItem.getChildren().get(1)
							.getChildren().get(0);
					String observacion = text.getValue();
					PacienteAntecedente pacienteAntecedente = new PacienteAntecedente(
							paciente, antecedente, observacion);
					antecedentes.add(pacienteAntecedente);
				}
			}
		}
		servicioPacienteAntecedente.guardar(antecedentes);
	}

	private void buscadorIntervenciones() {
		buscarIntervencion = new Buscar<Intervencion>(ltbIntervenciones,
				txtBuscadorIntervencion) {
			@Override
			protected List<Intervencion> buscar(String valor) {
				List<Intervencion> accidentesFiltrados = new ArrayList<Intervencion>();
				List<Intervencion> accidentes = servicioIntervencion
						.filtroNombre(valor);
				for (int i = 0; i < intervencionesDisponibles.size(); i++) {
					Intervencion intervencion = intervencionesDisponibles
							.get(i);
					for (int j = 0; j < accidentes.size(); j++) {
						if (intervencion.getIdIntervencion() == accidentes.get(
								j).getIdIntervencion())
							accidentesFiltrados.add(intervencion);
					}
				}
				return accidentesFiltrados;
			}
		};
	}

	private void buscadorComunes() {
		buscarAccidenteComun = new Buscar<Accidente>(ltbAccidentesComunes,
				txtBuscadorAccidenteComun) {
			@Override
			protected List<Accidente> buscar(String valor) {
				List<Accidente> accidentesFiltrados = new ArrayList<Accidente>();
				List<Accidente> accidentes = servicioAccidente
						.filtroNombre(valor);
				for (int i = 0; i < accidentesComunesDisponibles.size(); i++) {
					Accidente accidente = accidentesComunesDisponibles.get(i);
					for (int j = 0; j < accidentes.size(); j++) {
						if (accidente.getIdAccidente() == accidentes.get(j)
								.getIdAccidente())
							accidentesFiltrados.add(accidente);
					}
				}
				return accidentesFiltrados;
			}
		};
	}

	private void buscadorLaborales() {
		buscarAccidenteLaboral = new Buscar<Accidente>(ltbAccidentesLaborales,
				txtBuscadorAccidenteLaboral) {
			@Override
			protected List<Accidente> buscar(String valor) {
				List<Accidente> accidentesFiltrados = new ArrayList<Accidente>();
				List<Accidente> accidentes = servicioAccidente
						.filtroNombre(valor);
				for (int i = 0; i < accidentesLaboralesDisponibles.size(); i++) {
					Accidente accidente = accidentesLaboralesDisponibles.get(i);
					for (int j = 0; j < accidentes.size(); j++) {
						if (accidente.getIdAccidente() == accidentes.get(j)
								.getIdAccidente())
							accidentesFiltrados.add(accidente);
					}
				}
				return accidentesFiltrados;
			}
		};
	}

	private void listasMultiples() {
		for (int i = 0; i < listas.size(); i++) {
			listas.get(i).setMultiple(false);
			listas.get(i).setCheckmark(false);
			listas.get(i).setMultiple(true);
			listas.get(i).setCheckmark(true);
		}
	}

	private void llenarListas() {
		Paciente paciente = servicioPaciente.buscarPorCedula(String
				.valueOf(idPaciente));
		//
		Historia historia = servicioHistoria.buscarPorPaciente(paciente);
		intervencionesDisponibles = servicioIntervencion
				.buscarDisponibles(historia);
		ltbIntervenciones.setModel(new ListModelList<Intervencion>(
				intervencionesDisponibles));
		intervencionesAgregadas = servicioHistoriaIntervencion
				.buscarPorHistoria(historia);
		ltbIntervencionesAgregadas
				.setModel(new ListModelList<HistoriaIntervencion>(
						intervencionesAgregadas));

		accidentesComunesDisponibles = servicioAccidente.buscarDisponibles(
				historia, "Accidente Comun");
		ltbAccidentesComunes.setModel(new ListModelList<Accidente>(
				accidentesComunesDisponibles));
		accidentesComunesAgregadas = servicioHistoriaAccidente
				.buscarPorHistoria(historia, "Accidente Comun");
		ltbAccidentesComunesAgregados
				.setModel(new ListModelList<HistoriaAccidente>(
						accidentesComunesAgregadas));

		accidentesLaboralesDisponibles = servicioAccidente.buscarDisponibles(
				historia, "Accidente Laboral");
		ltbAccidentesLaborales.setModel(new ListModelList<Accidente>(
				accidentesLaboralesDisponibles));
		accidentesLaboralesAgregadas = servicioHistoriaAccidente
				.buscarPorHistoria(historia, "Accidente Laboral");
		ltbAccidentesLaboralesAgregados
				.setModel(new ListModelList<HistoriaAccidente>(
						accidentesLaboralesAgregadas));

		List<PacienteAntecedente> laboralesPaciente = servicioPacienteAntecedente
				.buscarAntecedentesPaciente(paciente, "Laboral");

		List<PacienteAntecedente> medicosPaciente = servicioPacienteAntecedente
				.buscarAntecedentesPaciente(paciente, "Medico");

		List<PacienteAntecedente> familiaresPaciente = servicioPacienteAntecedente
				.buscarAntecedentesPaciente(paciente, "Familiar");

		List<HistoriaVacuna> vacunasHistoricas = servicioHistoriaVacuna
				.buscarPorHistoria(historia);

		listasMultiples();

		if (!vacunasHistoricas.isEmpty()) {
			for (int i = 0; i < vacunasHistoricas.size(); i++) {
				long id = vacunasHistoricas.get(i).getVacuna().getIdVacuna();
				for (int j = 0; j < ltbVacunas.getItemCount(); j++) {
					Listitem listItem = ltbVacunas.getItemAtIndex(j);
					Vacuna a = listItem.getValue();
					long id2 = a.getIdVacuna();
					if (id == id2) {
						listItem.setSelected(true);
						Datebox tex = (Datebox) listItem.getChildren().get(1)
								.getChildren().get(0);
						tex.setValue(vacunasHistoricas.get(i).getFecha());
						j = ltbVacunas.getItemCount();
					}
				}

			}
		}

		if (!familiaresPaciente.isEmpty()) {
			for (int i = 0; i < familiaresPaciente.size(); i++) {
				long id = familiaresPaciente.get(i).getAntecedente()
						.getIdAntecedente();
				for (int j = 0; j < ltbFamiliares.getItemCount(); j++) {
					Listitem listItem = ltbFamiliares.getItemAtIndex(j);
					if (listItem.getContext() != null) {
						Antecedente a = listItem.getValue();
						long id2 = a.getIdAntecedente();
						if (id == id2) {
							listItem.setSelected(true);
							Textbox tex = (Textbox) listItem.getChildren()
									.get(1).getChildren().get(0);
							tex.setValue(familiaresPaciente.get(i)
									.getObservacion());
							j = ltbFamiliares.getItemCount();
						}
					}
				}
			}
		}

		if (!laboralesPaciente.isEmpty()) {
			for (int i = 0; i < laboralesPaciente.size(); i++) {
				long id = laboralesPaciente.get(i).getAntecedente()
						.getIdAntecedente();
				for (int j = 0; j < ltbLaborales.getItemCount(); j++) {
					Listitem listItem = ltbLaborales.getItemAtIndex(j);
					if (listItem.getContext() != null) {
						Antecedente a = listItem.getValue();
						long id2 = a.getIdAntecedente();
						if (id == id2) {
							listItem.setSelected(true);
							Textbox tex = (Textbox) listItem.getChildren()
									.get(1).getChildren().get(0);
							tex.setValue(laboralesPaciente.get(i)
									.getObservacion());
							j = ltbLaborales.getItemCount();
						}
					}
				}
			}
		}

		if (!medicosPaciente.isEmpty()) {
			for (int i = 0; i < medicosPaciente.size(); i++) {
				long id = medicosPaciente.get(i).getAntecedente()
						.getIdAntecedente();
				for (int j = 0; j < ltbMedicos.getItemCount(); j++) {
					Listitem listItem = ltbMedicos.getItemAtIndex(j);
					if (listItem.getContext() != null) {
						Antecedente a = listItem.getValue();
						long id2 = a.getIdAntecedente();
						if (id == id2) {
							listItem.setSelected(true);
							Textbox tex = (Textbox) listItem.getChildren()
									.get(1).getChildren().get(0);
							tex.setValue(medicosPaciente.get(i)
									.getObservacion());
							j = ltbMedicos.getItemCount();
						}
					}
				}
			}
		}
	}

	public ListModelList<String> getDientes() {
		dientes = new ListModelList<String>(tipoDiente);
		return dientes;
	}

	public ListModelList<Vacuna> getModelVacunas() {
		modelVacunas = new ListModelList<Vacuna>(servicioVacuna.buscarTodos());
		return modelVacunas;
	}

	/* Muestra un catalogo de Pacientes */
	@Listen("onClick = #btnBuscarPaciente")
	public void mostrarCatalogoPaciente(Event evento) throws IOException {
		List<Paciente> pacientesBuscar = servicioPaciente.buscarTodosActivos();
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
		Listbox lsita = (Listbox) catalogoPaciente.getChildren().get(3);
		lsita.setEmptyMessage("Utilice el filtro para buscar el paciente que desea buscar");
		catalogoPaciente.doModal();
	}

	@Listen("onSeleccion = #divCatalogoPacientes")
	public void seleccionarPaciente() {
		limpiarCampos();
		Paciente paciente = catalogoPaciente.objetoSeleccionadoDelCatalogo();
		llenarCampos(paciente);
		idPaciente = paciente.getCedula();
		llenarListas();
		catalogoPaciente.setParent(null);
	}

	private void llenarCampos(Paciente paciente) {

		if (paciente.getCargoReal() != null)
			lblCargo1.setValue(paciente.getCargoReal().getNombre());
		if (paciente.getArea() != null)
			lblArea.setValue(paciente.getArea().getNombre());
		Historia historia = paciente.getHistoria();
		if (historia != null) {
			llenarCamposHistoria(historia);
		}
		txtCedula.setValue(paciente.getCedula());
		lblNombres.setValue(paciente.getPrimerNombre() + " "
				+ paciente.getSegundoNombre());
		lblApellidos.setValue(paciente.getPrimerApellido() + " "
				+ paciente.getSegundoApellido());
		if (paciente.getEmpresa() != null)
			lblEmpresa.setValue(paciente.getEmpresa().getNombre());
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
		lblNombresE.setValue(paciente.getNombresEmergencia());
		lblApellidosE.setValue(paciente.getApellidosEmergencia());
		lblTelefono1E.setValue(paciente.getTelefono1Emergencia());
		lblTelefono2E.setValue(paciente.getTelefono2Emergencia());
		lblParentesco.setValue(paciente.getParentescoEmergencia());
		// lblParentescoFamiliar.setValue(paciente.getParentescoFamiliar());
		lblEdad.setValue(String.valueOf(calcularEdad(paciente
				.getFechaNacimiento())));
		lblEstatura.setValue(String.valueOf(paciente.getEstatura()));
		lblPeso.setValue(String.valueOf(paciente.getPeso()));
		// lblCiudad.setValue(paciente.getCiudadVivienda().getNombre());

		if (paciente.isAlergia())
			lblAlergico.setValue("SI");
		else
			lblAlergico.setValue("NO");

		if (paciente.isTrabajador()) {
			lblTrabajador.setValue("SI");
			lblCargo.setValue(paciente.getCargoReal().getNombre());
			lblArea.setValue(paciente.getArea().getNombre());
		} else
			lblTrabajador.setValue("NO");

		if (paciente.isDiscapacidad())
			lblDiscapacidad.setValue("SI");
		else
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
	}

	private void llenarCamposHistoria(Historia historia) {
		boolean valorPeso = historia.getVarioPeso();
		if (valorPeso)
			rdoSiPeso.setChecked(true);
		else
			rdoNoPeso.setChecked(true);
		boolean accidenteAlcohol = historia.getAlcoholAccidente();
		if (accidenteAlcohol)
			rdoSiAccidenteAlcohol.setChecked(true);
		else
			rdoNoAccidenteAlcohol.setChecked(true);
		boolean alcohol = historia.getAlcoholConsume();
		if (alcohol)
			rdoSiAlcohol.setChecked(true);
		else
			rdoNoAlcohol.setChecked(true);
		boolean anticonceptivos = historia.getAnticonceptivo();
		if (anticonceptivos)
			rdoSiAnticonceptivos.setChecked(true);
		else
			rdoNoAnticonceptivos.setChecked(true);
		boolean borracho = historia.getAlcoholEmbriagado();
		if (borracho)
			rdoSiBorracho.setChecked(true);
		else
			rdoNoBorracho.setChecked(true);
		boolean cabeza = historia.getDolorCafe();
		if (cabeza)
			rdoSiCabeza.setChecked(true);
		else
			rdoNoCabeza.setChecked(true);
		boolean cafe = historia.getCafe();
		if (cafe)
			rdoSiCafe.setChecked(true);
		else
			rdoNoCafe.setChecked(true);
		boolean cigarro = historia.getCigarroConsume();
		if (cigarro)
			rdoSiCigarro.setChecked(true);
		else
			rdoNoCigarro.setChecked(true);
		boolean dolorSexo = historia.getDolorRelacion();
		if (dolorSexo)
			rdoSiDolor.setChecked(true);
		else
			rdoNoDolor.setChecked(true);
		boolean drogas = historia.getDrogaConsume();
		if (drogas)
			rdoSiDrogas.setChecked(true);
		else
			rdoNoDrogas.setChecked(true);
		boolean dormilon = historia.getDificultadDormir();
		if (dormilon)
			rdoSiDuerme.setChecked(true);
		else
			rdoNoDuerme.setChecked(true);
		boolean eco = historia.getEco();
		if (eco)
			rdoSiEco.setChecked(true);
		else
			rdoNoEco.setChecked(true);
		boolean embarazo = historia.getEmbarazo();
		if (embarazo)
			rdoSiEmbarazada.setChecked(true);
		else
			rdoNoEmbarazada.setChecked(true);
		boolean endurecimiento = historia.getEndurecimiento();
		if (endurecimiento)
			rdoSiEndurecimiento.setChecked(true);
		else
			rdoNoEndurecimiento.setChecked(true);
		boolean enfermedad = historia.getEnfermedadPosee();
		if (enfermedad)
			rdoSiEnfermedad.setChecked(true);
		else
			rdoNoEnfermedad.setChecked(true);
		boolean esterilizacion = historia.getEsterilizacion();
		if (esterilizacion)
			rdoSiEsterilizacion.setChecked(true);
		else
			rdoNoEsterilizacion.setChecked(true);
		boolean ets = historia.getEts();
		if (ets)
			rdoSiETS.setChecked(true);
		else
			rdoNoETS.setChecked(true);
		boolean extra = historia.getActividadExtra();
		if (extra)
			rdoSiExtra.setChecked(true);
		else
			rdoNoExtra.setChecked(true);
		boolean fisica = historia.getActividadFisica();
		if (fisica)
			rdoSiFisica.setChecked(true);
		else
			rdoNoFisica.setChecked(true);
		boolean flujo = historia.getFlujo();
		if (flujo)
			rdoSiFlujo.setChecked(true);
		else
			rdoNoFlujo.setChecked(true);
		boolean fumaActual = historia.getCigarroActual();
		if (fumaActual)
			rdoSiFumaActualmente.setChecked(true);
		else
			rdoNoFumaActualmente.setChecked(true);
		boolean infeccion = historia.getInfeccion();
		if (infeccion)
			rdoSiInfeccion.setChecked(true);
		else
			rdoNoInfeccion.setChecked(true);
		boolean intra = historia.getAparato();
		if (intra)
			rdoSiIntra.setChecked(true);
		else
			rdoNoIntra.setChecked(true);
		boolean mamografia = historia.getMamografia();
		if (mamografia)
			rdoSiMamografia.setChecked(true);
		else
			rdoNoMamografia.setChecked(true);
		boolean medicamento = historia.getMedicamentoConsume();
		if (medicamento)
			rdoSiMedicamentoDrogas.setChecked(true);
		else
			rdoNoMedicamentoDrogas.setChecked(true);
		boolean medico = historia.getMedico();
		if (medico)
			rdoSiMedico.setChecked(true);
		else
			rdoNoMedico.setChecked(true);
		boolean menstrual = historia.getDolor();
		if (menstrual)
			rdoSiMenstrual.setChecked(true);
		else
			rdoNoMenstrual.setChecked(true);
		boolean alcoholActual = historia.getAlcoholActual();
		if (alcoholActual)
			rdoSiMesAlcohol.setChecked(true);
		else
			rdoNoMesAlcohol.setChecked(true);
		boolean ovarios = historia.getPoliquistico();
		if (ovarios)
			rdoSiOvarios.setChecked(true);
		else
			rdoNoOvarios.setChecked(true);
		boolean pezones = historia.getSecrecion();
		if (pezones)
			rdoSiPezones.setChecked(true);
		else
			rdoNoPezones.setChecked(true);
		boolean rehabilitacion = historia.getDrogaRehabilitacion();
		if (rehabilitacion)
			rdoSiRehabilitacion.setChecked(true);
		else
			rdoNoRehabilitacion.setChecked(true);
		boolean rehabilitacionAlcohol = historia.getAlcoholRehabilitacion();
		if (rehabilitacionAlcohol)
			rdoSiRehabilitacionAlcohol.setChecked(true);
		else
			rdoNoRehabilitacionAlcohol.setChecked(true);
		boolean transfusion = historia.getTransfusion();
		if (transfusion)
			rdoSiTransfusiones.setChecked(true);
		else
			rdoNoTransfusiones.setChecked(true);
		boolean tratamiento = historia.getTratamiento();
		if (tratamiento)
			rdoSiTratamiento.setChecked(true);
		else
			rdoNoTratamiento.setChecked(true);
		boolean tratamientoAlcochol = historia.getAlcoholTratamiento();
		if (tratamientoAlcochol)
			rdoSiTratamientoAlcohol.setChecked(true);
		else
			rdoNoTratamientoAlcohol.setChecked(true);
		boolean tratamientoDrogas = historia.getDrogaTratamiento();
		if (tratamientoDrogas)
			rdoSiTratamientoDrogas.setChecked(true);
		else
			rdoNoTratamientoDrogas.setChecked(true);
		boolean vih = historia.getVih();
		if (vih)
			rdoSiVIH.setChecked(true);
		else
			rdoNoVIH.setChecked(true);
		if (historia.getVisionColor() != null) {
			boolean colores = historia.getVisionColor();
			if (colores)
				rdoSiColores.setChecked(true);
			else
				rdoNoColores.setChecked(true);
		}
		spnAlturaCodo.setValue(historia.getAlturaCodo());
		spnAlturaHombro.setValue(historia.getAlturaHombro());
		spnSillaOjo.setValue(historia.getAlturaOjo());
		spnCodoSilla.setValue(historia.getAlturaCodoSilla());
		spnLongDerecho.setValue(historia.getMiembroDerecho());
		spnLongIzquierdo.setValue(historia.getMiembroIzquierdo());
		spnAnchuraHombro.setValue(historia.getAnchuraHombro());
		spnCaderaAbdominal.setValue(historia.getIndiceCadera());
		spnCircunferenciaA.setValue(historia.getCircunferenciaAbdominal());
		spnCircunferenciaC.setValue(historia.getCircunferenciaCadera());
		spnPoplitea.setValue(historia.getAlturaPoplitea());
		spnManoPiso.setValue(historia.getManoPiso());
		cmbCarta.setValue(historia.getCarta());
		txtTelefonoDoc.setValue(historia.getTelefonoOdontologo());
		cmbDiente1.setValue(historia.getDientea());
		cmbDiente2.setValue(historia.getDienteb());
		cmbDiente3.setValue(historia.getDientec());
		cmbDiente4.setValue(historia.getDiented());
		cmbDiente5.setValue(historia.getDientee());
		cmbDiente6.setValue(historia.getDientef());
		cmbDiente7.setValue(historia.getDienteg());
		cmbDiente8.setValue(historia.getDienteh());
		cmbDiente9.setValue(historia.getDientei());
		cmbDiente10.setValue(historia.getDientej());
		cmbDiente11.setValue(historia.getDientek());
		cmbDiente12.setValue(historia.getDientel());
		cmbDiente13.setValue(historia.getDientem());
		cmbDiente14.setValue(historia.getDienten());
		cmbDiente15.setValue(historia.getDienteo());
		cmbDiente16.setValue(historia.getDientep());
		cmbDiente17.setValue(historia.getDienteq());
		cmbDiente18.setValue(historia.getDienter());
		cmbDiente19.setValue(historia.getDientes());
		cmbDiente20.setValue(historia.getDientet());
		cmbDiente21.setValue(historia.getDienteu());
		cmbDiente22.setValue(historia.getDientev());
		cmbDiente23.setValue(historia.getDientew());
		cmbDiente24.setValue(historia.getDientex());
		cmbDiente25.setValue(historia.getDientey());
		cmbDiente26.setValue(historia.getDientez());
		cmbDiente27.setValue(historia.getDienteza());
		cmbDiente28.setValue(historia.getDientezb());
		cmbDiente29.setValue(historia.getDientezc());
		cmbDiente30.setValue(historia.getDientezd());
		cmbDiente31.setValue(historia.getDienteze());
		cmbDiente32.setValue(historia.getDientezf());
		spnAbortos.setValue(historia.getNumeroAbortos());
		spnCantidadAlcohol.setValue(historia.getAlcoholCantidad());
		spnCantidadCafe.setValue(historia.getCantidadCafe());
		spnCesareas.setValue(historia.getNumeroCesareas());
		spnEdadDesarrollo.setValue(historia.getEdadDesarrollo());
		spnEmbarazos.setValue(historia.getNumeroEmbarazos());
		spnGestacion.setValue(historia.getEmbarazoSemanas());
		spnNumeroCigarro.setValue(historia.getCigarroCantidad());
		spnPartos.setValue(historia.getNumeroPartos());
		txtVIH.setValue(historia.getVihResultado());
		txtAlgunaEnfermedad.setValue(historia.getEnfermedad());
		txtCantidadMedicamento.setValue(historia.getMedicamentoCantidad());
		txtCantidadPeso.setValue(historia.getPesoCambiado());
		txtCausasPeso.setValue(historia.getPesoCausa());
		txtExplicacionDrogas.setValue(historia.getDrogaExplicacion());
		txtFrecuenciaFisica.setValue(historia.getActividadFrecuencia());
		txtMedicamentoDroga.setValue(historia.getMedicamentoCantidad());
		txtRazonCigarro.setValue(historia.getCigarroRazon());
		txtResultadoEco.setValue(historia.getEcoResultado());
		txtResultadoMamografia.setValue(historia.getMamografiaResultado());
		txtTiempoFisica.setValue(historia.getActividadTiempo());
		txtTipoAlcohol.setValue(historia.getAlcoholTipo());
		txtTipoFisica.setValue(historia.getActividadTipo());
		dtbFechaFinCigarro.setValue(historia.getCigarroFin());
		dtbFechaInicioCigarro.setValue(historia.getCigarroInicio());
		dtbFechaMedicamento.setValue(historia.getMedicamentoInicio());
		dtbFechaUltima.setValue(historia.getUltimaMenstruacion());
		dtbFechaUltimaCito.setValue(historia.getUltimaCitologia());
		cmbExtra.setValue(historia.getTipoExtra());
		Radio radio = new Radio();
		switch (historia.getAlcoholFrecuencia()) {
		case "DIARIA":
			radio = (Radio) rdgFrecuenciaAlcohol.getChildren().get(0);
			radio.setChecked(true);
			break;
		case "SEMANAL":
			radio = (Radio) rdgFrecuenciaAlcohol.getChildren().get(1);
			radio.setChecked(true);
			break;
		case "QUINCENAL":
			radio = (Radio) rdgFrecuenciaAlcohol.getChildren().get(2);
			radio.setChecked(true);
			break;
		case "MENSUAL":
			radio = (Radio) rdgFrecuenciaAlcohol.getChildren().get(3);
			radio.setChecked(true);
			break;
		default:
			break;
		}
		spnSemanasPediatrica.setValue(historia.getEmbarazoSemanasPediatrica());
		spnGestacionPediatrica
				.setValue(historia.getGestacionNumeroPediatrica());
		spnEdadPediatrica.setValue(historia.getEdadMaternaPediatrica());
		boolean comlicacion = historia.getComplicacionesPediatrica();
		if (comlicacion)
			rdoSiComplicacion.setChecked(true);
		else
			rdoNoComplicacion.setChecked(true);
		txtResultadoComplicacion.setValue(historia
				.getComplicacionesDescripcionPediatrica());
		boolean vihP = historia.getVihPediatrica();
		if (vihP)
			chkVih.setChecked(true);
		boolean vdrP = historia.getVdrlPediatrica();
		if (vdrP)
			chkVdrl.setChecked(true);
		boolean tx = historia.getToxoplasmaPediatrica();
		if (tx)
			chkTox.setChecked(true);
		txtSerologia.setValue(historia.getSerologia());
		boolean parto = historia.getPartoPediatrica();
		if (parto)
			rdoSiVagina.setChecked(true);
		else
			rdoNoVagina.setChecked(true);
		txtCausaCesarea.setValue(historia.getCausaPartoPediatrica());
		spnPesoPediatrica.setValue(historia.getPesoPediatrica());
		spnTallaPediatrica.setValue(historia.getTallaPediatrica());
		boolean compliNeo = historia.getComplicacionesPediatricaParto();
		if (compliNeo)
			rdoSiComplicacionNeo.setChecked(true);
		else
			rdoNoComplicacionNeo.setChecked(true);
		txtObservacionPrenatal.setValue(historia.getDescripcionPediatrica());
		txtResultadoComplicacionNeo.setValue(historia
				.getDescripcionComplicacionParto());

	}

	private void limpiarCampos() {
		idPaciente = "";
		txtCedula.setValue("");
		limpiarColores(txtCedula);
		limpiarListas();
		llenarListas();
		for (int i = 0; i < ltbLaborales.getItemCount(); i++) {
			Listitem listItem = ltbLaborales.getItemAtIndex(i);
			if (listItem.isSelected() && listItem.getContext() != null) {
				Textbox tex = (Textbox) listItem.getChildren().get(1)
						.getChildren().get(0);
				tex.setValue("");
				tex.setPlaceholder("Ingrese una Observacion");
				listItem.setSelected(false);
			}
		}
		for (int i = 0; i < ltbMedicos.getItemCount(); i++) {
			Listitem listItem = ltbMedicos.getItemAtIndex(i);
			if (listItem.isSelected() && listItem.getContext() != null) {
				Textbox tex = (Textbox) listItem.getChildren().get(1)
						.getChildren().get(0);
				tex.setValue("");
				tex.setPlaceholder("Ingrese una Observacion");
				listItem.setSelected(false);
			}
		}
		for (int i = 0; i < ltbFamiliares.getItemCount(); i++) {
			Listitem listItem = ltbFamiliares.getItemAtIndex(i);
			if (listItem.isSelected() && listItem.getContext() != null) {
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
		lblEmpresa.setValue("");
		imagenPaciente.setVisible(false);
		lblFicha.setValue("");
		lblAlergico.setValue("");
		lblLugarNac.setValue("");
		lblSexo.setValue("");
		lblEstadoCivil.setValue("");
		lblGrupoSanguineo.setValue("");
		lblMano.setValue("");
		lblOrigen.setValue("");
		lblTipoDiscapacidad.setValue("");
		lblObservacionDiscapacidad.setValue("");
		lblCargo.setValue("");
		lblDireccion.setValue("");
		lblTelefono1.setValue("");
		lblTelefono2.setValue("");
		lblCorreo.setValue("");
		lblNombresE.setValue("");
		lblApellidosE.setValue("");
		lblTelefono1E.setValue("");
		lblTelefono2E.setValue("");
		lblParentesco.setValue("");
		lblPeso.setValue("");
		lblEdad.setValue("");
		lblEstatura.setValue("");
		lblCiudad.setValue("");
		lblAlergias.setValue("");
		lblTrabajador.setValue("");
		lblDiscapacidad.setValue("");
		lblLentes.setValue("");
		if (rdoSiPeso.isChecked())
			rdoSiPeso.setChecked(false);
		if (rdoNoPeso.isChecked())
			rdoNoPeso.setChecked(false);
		if (rdoSiAccidenteAlcohol.isChecked())
			rdoSiAccidenteAlcohol.setChecked(false);
		if (rdoNoAccidenteAlcohol.isChecked())
			rdoNoAccidenteAlcohol.setChecked(false);
		if (rdoSiAlcohol.isChecked())
			rdoSiAlcohol.setChecked(false);
		if (rdoNoAlcohol.isChecked())
			rdoNoAlcohol.setChecked(false);
		if (rdoSiAnticonceptivos.isChecked())
			rdoSiAnticonceptivos.setChecked(false);
		if (rdoNoAnticonceptivos.isChecked())
			rdoNoAnticonceptivos.setChecked(false);
		if (rdoSiBorracho.isChecked())
			rdoSiBorracho.setChecked(false);
		if (rdoNoBorracho.isChecked())
			rdoNoBorracho.setChecked(false);
		if (rdoSiCabeza.isChecked())
			rdoSiCabeza.setChecked(false);
		if (rdoNoCabeza.isChecked())
			rdoNoCabeza.setChecked(false);
		if (rdoSiCafe.isChecked())
			rdoSiCafe.setChecked(false);
		if (rdoNoCafe.isChecked())
			rdoNoCafe.setChecked(false);
		if (rdoSiCigarro.isChecked())
			rdoSiCigarro.setChecked(false);
		if (rdoNoCigarro.isChecked())
			rdoNoCigarro.setChecked(false);
		if (rdoSiDolor.isChecked())
			rdoSiDolor.setChecked(false);
		if (rdoNoDolor.isChecked())
			rdoNoDolor.setChecked(false);
		if (rdoSiDrogas.isChecked())
			rdoSiDrogas.setChecked(false);
		if (rdoNoDrogas.isChecked())
			rdoNoDrogas.setChecked(false);
		if (rdoSiDuerme.isChecked())
			rdoSiDuerme.setChecked(false);
		if (rdoNoDuerme.isChecked())
			rdoNoDuerme.setChecked(false);
		if (rdoSiEco.isChecked())
			rdoSiEco.setChecked(false);
		if (rdoNoEco.isChecked())
			rdoNoEco.setChecked(false);
		if (rdoSiEmbarazada.isChecked())
			rdoSiEmbarazada.setChecked(false);
		if (rdoNoEmbarazada.isChecked())
			rdoNoEmbarazada.setChecked(false);
		if (rdoSiEndurecimiento.isChecked())
			rdoSiEndurecimiento.setChecked(false);
		if (rdoNoEndurecimiento.isChecked())
			rdoNoEndurecimiento.setChecked(false);
		if (rdoSiEnfermedad.isChecked())
			rdoSiEnfermedad.setChecked(false);
		if (rdoNoEnfermedad.isChecked())
			rdoNoEnfermedad.setChecked(false);
		if (rdoSiEsterilizacion.isChecked())
			rdoSiEsterilizacion.setChecked(false);
		if (rdoNoEsterilizacion.isChecked())
			rdoNoEsterilizacion.setChecked(false);
		if (rdoSiETS.isChecked())
			rdoSiETS.setChecked(false);
		if (rdoNoETS.isChecked())
			rdoNoETS.setChecked(false);
		if (rdoSiExtra.isChecked())
			rdoSiExtra.setChecked(false);
		if (rdoNoExtra.isChecked())
			rdoNoExtra.setChecked(false);
		if (rdoSiFisica.isChecked())
			rdoSiFisica.setChecked(false);
		if (rdoNoFisica.isChecked())
			rdoNoFisica.setChecked(false);
		if (rdoSiFlujo.isChecked())
			rdoSiFlujo.setChecked(false);
		if (rdoNoFlujo.isChecked())
			rdoNoFlujo.setChecked(false);
		if (rdoSiFumaActualmente.isChecked())
			rdoSiFumaActualmente.setChecked(false);
		if (rdoNoFumaActualmente.isChecked())
			rdoNoFumaActualmente.setChecked(false);
		if (rdoSiInfeccion.isChecked())
			rdoSiInfeccion.setChecked(false);
		if (rdoNoInfeccion.isChecked())
			rdoNoInfeccion.setChecked(false);
		if (rdoSiIntra.isChecked())
			rdoSiIntra.setChecked(false);
		if (rdoNoIntra.isChecked())
			rdoNoIntra.setChecked(false);
		if (rdoSiMamografia.isChecked())
			rdoSiMamografia.setChecked(false);
		if (rdoNoMamografia.isChecked())
			rdoNoMamografia.setChecked(false);
		if (rdoSiMedicamentoDrogas.isChecked())
			rdoSiMedicamentoDrogas.setChecked(false);
		if (rdoNoMedicamentoDrogas.isChecked())
			rdoNoMedicamentoDrogas.setChecked(false);
		if (rdoSiMedico.isChecked())
			rdoSiMedico.setChecked(false);
		if (rdoNoMedico.isChecked())
			rdoNoMedico.setChecked(false);
		if (rdoSiMenstrual.isChecked())
			rdoSiMenstrual.setChecked(false);
		if (rdoNoMenstrual.isChecked())
			rdoNoMenstrual.setChecked(false);
		if (rdoSiMesAlcohol.isChecked())
			rdoSiMesAlcohol.setChecked(false);
		if (rdoNoMesAlcohol.isChecked())
			rdoNoMesAlcohol.setChecked(false);
		if (rdoSiOvarios.isChecked())
			rdoSiOvarios.setChecked(false);
		if (rdoNoOvarios.isChecked())
			rdoNoOvarios.setChecked(false);
		if (rdoSiPezones.isChecked())
			rdoSiPezones.setChecked(false);
		if (rdoNoPezones.isChecked())
			rdoNoPezones.setChecked(false);
		if (rdoSiRehabilitacion.isChecked())
			rdoSiRehabilitacion.setChecked(false);
		if (rdoNoRehabilitacion.isChecked())
			rdoNoRehabilitacion.setChecked(false);
		if (rdoSiRehabilitacionAlcohol.isChecked())
			rdoSiRehabilitacionAlcohol.setChecked(false);
		if (rdoNoRehabilitacionAlcohol.isChecked())
			rdoNoRehabilitacionAlcohol.setChecked(false);
		if (rdoSiTransfusiones.isChecked())
			rdoSiTransfusiones.setChecked(false);
		if (rdoNoTransfusiones.isChecked())
			rdoNoTransfusiones.setChecked(false);
		if (rdoSiTratamiento.isChecked())
			rdoSiTratamiento.setChecked(false);
		if (rdoNoTratamiento.isChecked())
			rdoNoTratamiento.setChecked(false);
		if (rdoSiTratamientoAlcohol.isChecked())
			rdoSiTratamientoAlcohol.setChecked(false);
		if (rdoNoTratamientoAlcohol.isChecked())
			rdoNoTratamientoAlcohol.setChecked(false);
		if (rdoSiTratamientoDrogas.isChecked())
			rdoSiTratamientoDrogas.setChecked(false);
		if (rdoNoTratamientoDrogas.isChecked())
			rdoNoTratamientoDrogas.setChecked(false);
		if (rdoSiVIH.isChecked())
			rdoSiVIH.setChecked(false);
		if (rdoNoVIH.isChecked())
			rdoNoVIH.setChecked(false);
		if (rdoSiColores.isChecked())
			rdoSiColores.setChecked(false);
		if (rdoNoColores.isChecked())
			rdoNoColores.setChecked(false);
		if (rdgFrecuenciaAlcohol.getSelectedItem() != null) {
			Radio radio = rdgFrecuenciaAlcohol.getSelectedItem();
			radio.setChecked(false);
		}
		spnAbortos.setValue(0);
		spnCantidadAlcohol.setValue(0);
		spnCantidadCafe.setValue(0);
		spnCesareas.setValue(0);
		spnEdadDesarrollo.setValue(0);
		spnEmbarazos.setValue(0);
		spnGestacion.setValue(0);
		spnNumeroCigarro.setValue(0);
		spnPartos.setValue(0);
		txtVIH.setValue("");
		txtAlgunaEnfermedad.setValue("");
		txtCantidadMedicamento.setValue("");
		txtCantidadPeso.setValue("");
		txtCausasPeso.setValue("");
		txtExplicacionDrogas.setValue("");
		txtFrecuenciaFisica.setValue("");
		txtMedicamentoDroga.setValue("");
		txtRazonCigarro.setValue("");
		txtResultadoEco.setValue("");
		txtResultadoMamografia.setValue("");
		txtTiempoFisica.setValue("");
		txtTipoAlcohol.setValue("");
		txtTipoFisica.setValue("");
		dtbFechaFinCigarro.setValue(fecha);
		dtbFechaInicioCigarro.setValue(fecha);
		dtbFechaMedicamento.setValue(fecha);
		dtbFechaUltima.setValue(fecha);
		dtbFechaUltimaCito.setValue(fecha);
		cmbExtra.setValue("");
		spnAlturaCodo.setValue((double) 0);
		spnAlturaHombro.setValue((double) 0);
		spnSillaOjo.setValue((double) 0);
		spnCodoSilla.setValue((double) 0);
		spnLongDerecho.setValue((double) 0);
		spnLongIzquierdo.setValue((double) 0);
		spnAnchuraHombro.setValue((double) 0);
		spnCaderaAbdominal.setValue((double) 0);
		spnCircunferenciaA.setValue((double) 0);
		spnCircunferenciaC.setValue((double) 0);
		spnPoplitea.setValue((double) 0);
		spnManoPiso.setValue((double) 0);
		cmbCarta.setValue("");
		txtTelefonoDoc.setValue("");
		cmbDiente1.setValue("Normal");
		cmbDiente2.setValue("Normal");
		cmbDiente3.setValue("Normal");
		cmbDiente4.setValue("Normal");
		cmbDiente5.setValue("Normal");
		cmbDiente6.setValue("Normal");
		cmbDiente7.setValue("Normal");
		cmbDiente8.setValue("Normal");
		cmbDiente9.setValue("Normal");
		cmbDiente10.setValue("Normal");
		cmbDiente11.setValue("Normal");
		cmbDiente12.setValue("Normal");
		cmbDiente13.setValue("Normal");
		cmbDiente14.setValue("Normal");
		cmbDiente15.setValue("Normal");
		cmbDiente16.setValue("Normal");
		cmbDiente17.setValue("Normal");
		cmbDiente18.setValue("Normal");
		cmbDiente19.setValue("Normal");
		cmbDiente20.setValue("Normal");
		cmbDiente21.setValue("Normal");
		cmbDiente22.setValue("Normal");
		cmbDiente23.setValue("Normal");
		cmbDiente24.setValue("Normal");
		cmbDiente25.setValue("Normal");
		cmbDiente26.setValue("Normal");
		cmbDiente27.setValue("Normal");
		cmbDiente28.setValue("Normal");
		cmbDiente29.setValue("Normal");
		cmbDiente30.setValue("Normal");
		cmbDiente31.setValue("Normal");
		cmbDiente32.setValue("Normal");
		spnSemanasPediatrica.setValue(0);
		spnGestacionPediatrica.setValue(0);
		spnEdadPediatrica.setValue(0);
		if (rdoSiComplicacion.isChecked())
			rdoSiComplicacion.setChecked(false);
		if (rdoNoComplicacion.isChecked())
			rdoNoComplicacion.setChecked(false);
		txtResultadoComplicacion.setValue("");
		if (rdoSiComplicacion.isChecked())
			rdoSiComplicacion.setChecked(false);
		if (rdoNoComplicacion.isChecked())
			rdoNoComplicacion.setChecked(false);
		if (rdoSiComplicacion.isChecked())
			rdoSiComplicacion.setChecked(false);
		if (chkVih.isChecked())
			chkVih.setChecked(false);
		if (chkVdrl.isChecked())
			chkVdrl.setChecked(false);
		if (chkTox.isChecked())
			chkTox.setChecked(false);
		txtSerologia.setValue("");
		if (rdoSiVagina.isChecked())
			rdoSiVagina.setChecked(false);
		if (rdoNoVagina.isChecked())
			rdoNoVagina.setChecked(false);
		txtCausaCesarea.setValue("");
		spnPesoPediatrica.setValue((double) 0);
		spnTallaPediatrica.setValue((double) 0);
		if (rdoSiComplicacionNeo.isChecked())
			rdoSiComplicacionNeo.setChecked(false);
		if (rdoNoComplicacionNeo.isChecked())
			rdoNoComplicacionNeo.setChecked(false);
		txtObservacionPrenatal.setValue("");
		txtResultadoComplicacionNeo.setValue("");
		tabIdentificacion.setSelected(true);
	}

	private void limpiarListas() {
		List<List<?>> limpiador = new ArrayList<List<?>>();
		limpiador.add(accidentesComunesAgregadas);
		limpiador.add(accidentesComunesDisponibles);
		limpiador.add(accidentesLaboralesAgregadas);
		limpiador.add(accidentesLaboralesDisponibles);
		limpiador.add(intervencionesAgregadas);
		limpiador.add(intervencionesDisponibles);
		for (int q = 0; q < limpiador.size(); q++) {
			limpiador.get(q).clear();
		}
	}

	private boolean validarLaborales() {
		for (int i = 0; i < ltbAccidentesLaboralesAgregados.getItemCount(); i++) {
			Listitem listItem = ltbAccidentesLaboralesAgregados
					.getItemAtIndex(i);
			if (((Datebox) ((listItem.getChildren().get(1))).getFirstChild())
					.getValue() == null)
				return false;
		}
		return true;
	}

	private boolean validarComunes() {
		for (int i = 0; i < ltbAccidentesComunesAgregados.getItemCount(); i++) {
			Listitem listItem = ltbAccidentesComunesAgregados.getItemAtIndex(i);
			if (((Datebox) ((listItem.getChildren().get(1))).getFirstChild())
					.getValue() == null)
				return false;
		}
		return true;
	}

	private boolean validarIntervencion() {
		for (int i = 0; i < ltbIntervencionesAgregadas.getItemCount(); i++) {
			Listitem listItem = ltbIntervencionesAgregadas.getItemAtIndex(i);
			if (((Datebox) ((listItem.getChildren().get(1))).getFirstChild())
					.getValue() == null)
				return false;
		}
		return true;
	}

	private boolean validarHistoria() {
		if (idPaciente.equals("")) {
			Mensaje.mensajeError("Debe seleccionar un Paciente");
			aplicarColores(txtCedula);
			return false;
		} else {
			if (ltbIntervencionesAgregadas.getItemCount() != 0
					&& !validarIntervencion()) {
				Mensaje.mensajeError("Debe seleccionar al menos la Fecha de la lista de Intervenciones Agregadas");
				return false;
			} else {
				if (ltbAccidentesComunesAgregados.getItemCount() != 0
						&& !validarComunes()) {
					Mensaje.mensajeError("Debe seleccionar al menos la Fecha de la lista de Accidentes Comunes Agregados");
					return false;
				} else {
					if (ltbAccidentesLaboralesAgregados.getItemCount() != 0
							&& !validarLaborales()) {
						Mensaje.mensajeError("Debe seleccionar al menos la Fecha de la lista de Accidentes Laborales Agregados");
						return false;
					} else
						return true;
				}
			}
		}
	}

}
