package controlador.utils;

import java.io.IOException;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;

import javax.mail.Address;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import modelo.medico.maestro.Paciente;
import modelo.security.Usuario;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.VariableResolver;
import org.zkoss.zk.ui.select.annotation.WireVariable;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Div;
import org.zkoss.zul.Include;
import org.zkoss.zul.Tab;
import org.zkoss.zul.Tabbox;
import org.zkoss.zul.West;
import org.zkoss.zul.impl.InputElement;

import servicio.medico.consulta.SConsulta;
import servicio.medico.consulta.SConsultaDiagnostico;
import servicio.medico.consulta.SConsultaEspecialista;
import servicio.medico.consulta.SConsultaExamen;
import servicio.medico.consulta.SConsultaMedicina;
import servicio.medico.consulta.SConsultaParteCuerpo;
import servicio.medico.consulta.SConsultaServicioExterno;
import servicio.medico.consulta.SRecipe;
import servicio.medico.historia.SHistoria;
import servicio.medico.historia.SHistoriaAccidente;
import servicio.medico.historia.SHistoriaIntervencion;
import servicio.medico.historia.SHistoriaVacuna;
import servicio.medico.maestro.SCategoriaDiagnostico;
import servicio.medico.maestro.SCita;
import servicio.medico.maestro.SClasificacionDiagnostico;
import servicio.medico.maestro.SDiagnostico;
import servicio.medico.maestro.SDoctorInterno;
import servicio.medico.maestro.SEspecialidad;
import servicio.medico.maestro.SEspecialista;
import servicio.medico.maestro.SEstadoCivil;
import servicio.medico.maestro.SExamen;
import servicio.medico.maestro.SIntervencion;
import servicio.medico.maestro.SMedicina;
import servicio.medico.maestro.SMotivoCita;
import servicio.medico.maestro.SPaciente;
import servicio.medico.maestro.SParteCuerpo;
import servicio.medico.maestro.SPeriodo;
import servicio.medico.maestro.SPeriodoPaciente;
import servicio.medico.maestro.SProveedor;
import servicio.medico.maestro.SProveedorExamen;
import servicio.medico.maestro.SProveedorServicio;
import servicio.medico.maestro.SServicioExterno;
import servicio.medico.maestro.SVacuna;
import servicio.organizacion.SArea;
import servicio.organizacion.SCargo;
import servicio.organizacion.SCiudad;
import servicio.organizacion.SEmpresa;
import servicio.organizacion.SEstado;
import servicio.organizacion.SPais;
import servicio.security.SArbol;
import servicio.security.SGrupo;
import servicio.security.SUsuario;
import servicio.seguridad.SAccidente;
import servicio.seguridad.SClasificacionAccidente;
import servicio.seguridad.SCondicion;
import servicio.seguridad.SGrupoInspectores;
import servicio.seguridad.SInforme;
import servicio.seguridad.SPlanAccion;
import componente.Mensaje;

@VariableResolver(org.zkoss.zkplus.spring.DelegatingVariableResolver.class)
public abstract class CGenerico extends SelectorComposer<Component> {

	private static final long serialVersionUID = -2264423023637489596L;
	private static ApplicationContext applicationContext = new ClassPathXmlApplicationContext(
			"/META-INF/AppContext.xml");
	@WireVariable("SEstadoCivil")
	protected SEstadoCivil servicioEstadoCivil;
	@WireVariable("SAccidente")
	protected SAccidente servicioAccidente;
	@WireVariable("SArbol")
	protected SArbol servicioArbol;
	@WireVariable("SCargo")
	protected SCargo servicioCargo;
	@WireVariable("SDoctorInterno")
	protected SDoctorInterno servicioDoctor;
	@WireVariable("SCategoriaDiagnostico")
	protected SCategoriaDiagnostico servicioCategoriaDiagnostico;
	@WireVariable("SCita")
	protected SCita servicioCita;
	@WireVariable("SCiudad")
	protected SCiudad servicioCiudad;
	@WireVariable("SCondicion")
	protected SCondicion servicioCondicion;
	@WireVariable("SClasificacionDiagnostico")
	protected SClasificacionDiagnostico servicioClasificacion;
	@WireVariable("SConsulta")
	protected SConsulta servicioConsulta;
	@WireVariable("SConsultaParteCuerpo")
	protected SConsultaParteCuerpo servicioConsultaParteCuerpo;
	@WireVariable("SConsultaDiagnostico")
	protected SConsultaDiagnostico servicioConsultaDiagnostico;
	@WireVariable("SConsultaEspecialista")
	protected SConsultaEspecialista servicioConsultaEspecialista;
	@WireVariable("SConsultaExamen")
	protected SConsultaExamen servicioConsultaExamen;
	@WireVariable("SConsultaMedicina")
	protected SConsultaMedicina servicioConsultaMedicina;
	@WireVariable("SConsultaServicioExterno")
	protected SConsultaServicioExterno servicioConsultaServicioExterno;
	@WireVariable("SDiagnostico")
	protected SDiagnostico servicioDiagnostico;
	@WireVariable("SEmpresa")
	protected SEmpresa servicioEmpresa;
	@WireVariable("SEspecialidad")
	protected SEspecialidad servicioEspecialidad;
	@WireVariable("SEspecialista")
	protected SEspecialista servicioEspecialista;
	@WireVariable("SEstado")
	protected SEstado servicioEstado;
	@WireVariable("SExamen")
	protected SExamen servicioExamen;
	@WireVariable("SHistoria")
	protected SHistoria servicioHistoria;
	@WireVariable("SHistoriaAccidente")
	protected SHistoriaAccidente servicioHistoriaAccidente;
	@WireVariable("SHistoriaIntervencion")
	protected SHistoriaIntervencion servicioHistoriaIntervencion;
	@WireVariable("SHistoriaVacuna")
	protected SHistoriaVacuna servicioHistoriaVacuna;
	@WireVariable("SInforme")
	protected SInforme servicioInforme;
	@WireVariable("SIntervencion")
	protected SIntervencion servicioIntervencion;
	@WireVariable("SMedicina")
	protected SMedicina servicioMedicina;
	@WireVariable("SMotivoCita")
	protected SMotivoCita servicioMotivoCita;
	@WireVariable("SPaciente")
	protected SPaciente servicioPaciente;
	@WireVariable("SPais")
	protected SPais servicioPais;
	@WireVariable("SParteCuerpo")
	protected SParteCuerpo servicioParteCuerpo;
	@WireVariable("SPeriodo")
	protected SPeriodo servicioPeriodo;
	@WireVariable("SPeriodoPaciente")
	protected SPeriodoPaciente servicioPeriodoPaciente;
	@WireVariable("SPlanAccion")
	protected SPlanAccion servicioPlanAccion;
	@WireVariable("SProveedor")
	protected SProveedor servicioProveedor;
	@WireVariable("SProveedorExamen")
	protected SProveedorExamen servicioProveedorExamen;
	@WireVariable("SProveedorServicio")
	protected SProveedorServicio servicioProveedorServicio;
	@WireVariable("SRecipe")
	protected SRecipe servicioRecipe;
	@WireVariable("SServicioExterno")
	protected SServicioExterno servicioServicioExterno;
	@WireVariable("SGrupo")
	protected SGrupo servicioGrupo;
	@WireVariable("SUsuario")
	protected SUsuario servicioUsuario;
	@WireVariable("SVacuna")
	protected SVacuna servicioVacuna;
	@WireVariable("SArea")
	protected SArea servicioArea;
	@WireVariable("SClasificacionAccidente")
	protected SClasificacionAccidente servicioClasificacionAccidente;
	@WireVariable("SGrupoInspectores")
	protected SGrupoInspectores servicioGrupoInspectores;
	public Mensaje msj = new Mensaje();
	public Tabbox tabBox;
	public Include contenido;
	public Tab tab;
	public West west;
	protected DateFormat formatoFecha = new SimpleDateFormat("dd/MM/yyyy");
	protected DateFormat formatoHorasHombre = new SimpleDateFormat("MM/yyyy");
	protected DateFormat formatoReporte = new SimpleDateFormat("dd-MM-yyyy");
	protected DateFormat formatoYear = new SimpleDateFormat("yyyy");
	public List<Tab> tabs = new ArrayList<Tab>();
	protected DateFormat df = new SimpleDateFormat("HH:mm:ss");
	protected DateFormat formatoImportar = new SimpleDateFormat("yyyy-MM-dd");
	public java.util.Date fecha = new Date();
	public String cod = formatoYear.format(fecha);
	public Calendar calendario2 = Calendar.getInstance();
	public Calendar calendario = Calendar.getInstance();
	public String horaAuditoria = String.valueOf(calendario
			.get(Calendar.HOUR_OF_DAY))
			+ ":"
			+ String.valueOf(calendario.get(Calendar.MINUTE))
			+ ":"
			+ String.valueOf(calendario.get(Calendar.SECOND));
	public Timestamp fechaHora = new Timestamp(fecha.getTime());
	public String titulo;
	
	@Override
	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);
		inicializar();
	}

	public Timestamp metodoFecha() {
		fecha = new Date();
		return fechaHora = new Timestamp(fecha.getTime());
	}

	public String metodoHora() {
		fecha = new Date();
		calendario.setTime(fecha);
		return String.valueOf(calendario.get(Calendar.HOUR_OF_DAY)) + ":"
				+ String.valueOf(calendario.get(Calendar.MINUTE)) + ":"
				+ String.valueOf(calendario.get(Calendar.SECOND));
	}

	public abstract void inicializar() throws IOException;

	public void cerrarVentana(Div div, String id, List<Tab> tabs2) {
		div.setVisible(false);
		tabs = tabs2;
		for (int i = 0; i < tabs.size(); i++) {
			if (tabs.get(i).getLabel().equals(id)) {
				if (i == (tabs.size() - 1) && tabs.size() > 1) {
					tabs.get(i - 1).setSelected(true);
				}
				tabs.get(i).onClose();
				tabs.remove(i);
			}
		}
	}

	public static SConsulta getServicioConsulta() {
		return applicationContext.getBean(SConsulta.class);
	}

	public static SConsultaParteCuerpo getServicioConsultaParteCuerpo() {
		return applicationContext.getBean(SConsultaParteCuerpo.class);
	}

	public static SUsuario getServicioUsuario() {
		return applicationContext.getBean(SUsuario.class);
	}

	public static SDoctorInterno getServicioDoctor() {
		return applicationContext.getBean(SDoctorInterno.class);
	}

	public static SConsultaEspecialista getServicioConsultaEspecialista() {
		return applicationContext.getBean(SConsultaEspecialista.class);
	}

	public static SEspecialista getServicioEspecialista() {
		return applicationContext.getBean(SEspecialista.class);
	}

	public static SConsultaServicioExterno getServicioConsultaServicioExterno() {
		return applicationContext.getBean(SConsultaServicioExterno.class);
	}

	public static SConsultaExamen getServicioConsultaExamen() {
		return applicationContext.getBean(SConsultaExamen.class);
	}

	public static SArea getServicioArea() {
		return applicationContext.getBean(SArea.class);
	}

	public static SCita getServicioCita() {
		return applicationContext.getBean(SCita.class);
	}

	public static SHistoria getServicioHistoria() {
		return applicationContext.getBean(SHistoria.class);
	}

	public static SCategoriaDiagnostico getServicioCategoria() {
		return applicationContext.getBean(SCategoriaDiagnostico.class);
	}

	public static SClasificacionDiagnostico getServicioClasificacion() {
		return applicationContext.getBean(SClasificacionDiagnostico.class);
	}

	public static SCargo getServicioCargo() {
		return applicationContext.getBean(SCargo.class);
	}

	public static SEmpresa getServicioEmpresa() {
		return applicationContext.getBean(SEmpresa.class);
	}

	public static SInforme getServicioInforme() {
		return applicationContext.getBean(SInforme.class);
	}

	public static SConsultaDiagnostico getServicioConsultaDiagnostico() {
		return applicationContext.getBean(SConsultaDiagnostico.class);
	}

	public static SPaciente getServicioPaciente() {
		return applicationContext.getBean(SPaciente.class);
	}

	public static SCondicion getServicioCondicion() {
		return applicationContext.getBean(SCondicion.class);
	}
	
	public static SPeriodoPaciente getServicioPeriodoPaciente() {
		return applicationContext.getBean(SPeriodoPaciente.class);
	}

	public static SProveedor getServicioProveedor() {
		return applicationContext.getBean(SProveedor.class);
	}

	public static SConsultaMedicina getServicioConsultaMedicina() {
		return applicationContext.getBean(SConsultaMedicina.class);
	}

	public String nombreUsuarioSesion() {
		Authentication sesion = SecurityContextHolder.getContext()
				.getAuthentication();
		return sesion.getName();
	}

	public Usuario usuarioSesion(String valor) {
		return servicioUsuario.buscarPorLogin(valor);
	}

	/* Metodo que permite enviar un correo electronico a cualquier destinatario */
	public boolean enviarEmailNotificacion(String correo, String mensajes) {
		try {

			String cc = "NOTIFICACION SIMS";
			Properties props = new Properties();
			props.setProperty("mail.smtp.host", "172.23.20.66");
			props.setProperty("mail.smtp.starttls.enable", "true");
			props.setProperty("mail.smtp.port", "2525");
			props.setProperty("mail.smtp.auth", "true");

			Authenticator auth = new SMTPAuthenticator();
			Session session = Session.getInstance(props, auth);
			String remitente = "cdusa@dusa.com.ve";
			String destino = correo;
			String mensaje = mensajes;
			String destinos[] = destino.split(",");
			Message message = new MimeMessage(session);

			message.setFrom(new InternetAddress(remitente));

			Address[] receptores = new Address[destinos.length];
			int j = 0;
			while (j < destinos.length) {
				receptores[j] = new InternetAddress(destinos[j]);
				j++;
			}

			message.addRecipients(Message.RecipientType.TO, receptores);
			message.setSubject(cc);
			message.setText(mensaje);

			Transport.send(message);

			return true;
		}

		catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	public static int calcularEdad(Date birthDate) {
		Calendar birth = new GregorianCalendar();
		Calendar today = new GregorianCalendar();
		int age = 0;
		int factor = 0;
		Date currentDate = new Date();
		birth.setTime(birthDate);
		today.setTime(currentDate);
		if (today.get(Calendar.DAY_OF_YEAR) < birth.get(Calendar.DAY_OF_YEAR)) {
			factor = -1;
		}
		age = (today.get(Calendar.YEAR) - birth.get(Calendar.YEAR)) + factor;
		if (age == -1)
			age = 0;
		if (today.get(Calendar.YEAR) == birth.get(Calendar.YEAR))
			// age = today.get(Calendar.MONTH) - birth.get(Calendar.MONTH);
			age = 0;
		return age;
	}

	public void inhabilitarTrabajadorYTodosFamiliares(Paciente paciente) {
		List<Paciente> inactivos = new ArrayList<Paciente>();
		paciente.setEstatus(false);
		inactivos.add(paciente);
		if (paciente.isTrabajador()) {
			List<Paciente> carga = servicioPaciente.buscarParientes(paciente
					.getCedula());
			for (Iterator<Paciente> iterator = carga.iterator(); iterator
					.hasNext();) {
				Paciente paciente2 = (Paciente) iterator.next();
				if (!paciente.isMuerte()) {
					paciente2.setEstatus(false);
					inactivos.add(paciente2);
				} else {
					if (!paciente2.getParentescoFamiliar().equals("Hijo(a)")) {
						paciente2.setEstatus(false);
						inactivos.add(paciente2);
					}
				}
			}
		}
		servicioPaciente.guardarVarios(inactivos);
	}

	public String diaSemanaString(Calendar calendar) {
		int dia = calendar.get(Calendar.DAY_OF_WEEK);
		String diaSemana = "";
		switch (dia) {
		case 2:
			diaSemana = "Lunes";
			break;
		case 3:
			diaSemana = "Martes";
			break;

		case 4:
			diaSemana = "Miercoles";
			break;
		case 5:
			diaSemana = "Jueves";
			break;
		case 6:
			diaSemana = "Viernes";
			break;
		case 7:
			diaSemana = "Sabado";
			break;
		case 1:
			diaSemana = "Domingo";
			break;
		}
		return diaSemana;
	}

	public List<String> obtenerPropiedades() {
		List<String> arreglo = new ArrayList<String>();
		DriverManagerDataSource ds = (DriverManagerDataSource) applicationContext
				.getBean("dataSource");
		arreglo.add(ds.getUsername());
		arreglo.add(ds.getPassword());
		arreglo.add(ds.getUrl());
		return arreglo;
	}

	class SMTPAuthenticator extends javax.mail.Authenticator {
		public PasswordAuthentication getPasswordAuthentication() {
			return new PasswordAuthentication("cdusa", "cartucho");
		}
	}

	public String damePath() {
		return Executions.getCurrent().getContextPath() + "/";
	}

	public String traerFecha2(Timestamp fecha) {
		String valor = "";
		if (fecha != null) {
			DateFormat formatoFecha = new SimpleDateFormat("dd/MM/yyyy");
			valor = String.valueOf(formatoFecha.format(fecha));
		}
		return valor;
	}

	public Date agregarDia(Date fecha) {
		Calendar calendario = Calendar.getInstance();
		calendario.setTime(fecha);
		calendario.add(Calendar.DAY_OF_YEAR, +1);
		return fecha = calendario.getTime();
	}

	public void limpiarColores(InputElement... cajas) {
		for (int i = 0; i < cajas.length; i++) {
			cajas[i].setStyle("border-color:default");
			if (cajas[i] instanceof Combobox)
				cajas[i].setStyle("border: 1px solid default");
		}
	}

	public void aplicarColores(InputElement... cajas) {
		limpiarColores(cajas);
		for (int i = 0; i < cajas.length; i++) {
			if (cajas[i].getText().compareTo("") == 0)
				cajas[i].setStyle("border-color:red");
			if (cajas[i] instanceof Combobox)
				if (((Combobox) cajas[i]).getSelectedItem() == null)
					cajas[i].setStyle("border: 1px solid red");
		}
	}
}