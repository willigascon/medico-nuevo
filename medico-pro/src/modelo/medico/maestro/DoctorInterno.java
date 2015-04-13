package modelo.medico.maestro;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import modelo.medico.consulta.Consulta;
import modelo.security.Usuario;

@Entity
@Table(name="doctor_interno")
public class DoctorInterno implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name="cedula", length=12, unique=true, nullable=false)
	private String cedula;

	@Column(length=500)
	private String direccion;

	@Column(length=50)
	private String ficha;

	@Column(name="licencia_cm", length=50)
	private String licenciaCm;

	@Column(name="licencia_inpsasel")
	private long licenciaInpsasel;

	@Column(name="licencia_msds", length=50)
	private String licenciaMsds;
	
	@Column(name="primer_apellido", length=100)
	private String primerApellido;

	@Column(name="primer_nombre", length=100)
	private String primerNombre;
	
	@Column(name="segundo_apellido", length=100)
	private String segundoApellido;

	@Column(name="segundo_nombre", length=100)
	private String segundoNombre;

	@Column(length=1)
	private String sexo;

	@Column(length=50)
	private String telefono;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="id_especialidad")
	private Especialidad especialidad;

	@Column(name="citas_diarias")
	private long citasDiarias;

	@Column(name="fecha_auditoria")
	private Timestamp fechaAuditoria;

	@Column(name="hora_auditoria", length=10)
	private String horaAuditoria;

	@Column(name="usuario_auditoria", length=50)
	private String usuarioAuditoria;

	@OneToMany(mappedBy="doctorInterno")
	private List<Usuario> usuarios;

	@OneToMany(mappedBy="doctorInterno")
	private List<Consulta> consultas;

	public DoctorInterno() {
		super();
		// TODO Auto-generated constructor stub
	}

	public DoctorInterno(String cedula, String direccion, String ficha,
			String licenciaCm, long licenciaInpsasel, String licenciaMsds,
			String primerApellido, String primerNombre, String segundoApellido,
			String segundoNombre, String sexo, String telefono,
			Especialidad especialidad, Timestamp fechaAuditoria,
			String horaAuditoria, String usuarioAuditoria,
			long citas) {
		super();
		this.cedula = cedula;
		this.direccion = direccion;
		this.ficha = ficha;
		this.licenciaCm = licenciaCm;
		this.licenciaInpsasel = licenciaInpsasel;
		this.licenciaMsds = licenciaMsds;
		this.primerApellido = primerApellido;
		this.primerNombre = primerNombre;
		this.segundoApellido = segundoApellido;
		this.segundoNombre = segundoNombre;
		this.sexo = sexo;
		this.telefono = telefono;
		this.especialidad = especialidad;
		this.fechaAuditoria = fechaAuditoria;
		this.horaAuditoria = horaAuditoria;
		this.usuarioAuditoria = usuarioAuditoria;
		this.citasDiarias = citas;
	}

	public String getCedula() {
		return cedula;
	}

	public void setCedula(String cedula) {
		this.cedula = cedula;
	}

	public String getDireccion() {
		return direccion;
	}

	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}

	public String getFicha() {
		return ficha;
	}

	public void setFicha(String ficha) {
		this.ficha = ficha;
	}

	public String getLicenciaCm() {
		return licenciaCm;
	}

	public void setLicenciaCm(String licenciaCm) {
		this.licenciaCm = licenciaCm;
	}

	public long getLicenciaInpsasel() {
		return licenciaInpsasel;
	}

	public void setLicenciaInpsasel(long licenciaInpsasel) {
		this.licenciaInpsasel = licenciaInpsasel;
	}

	public String getLicenciaMsds() {
		return licenciaMsds;
	}

	public void setLicenciaMsds(String licenciaMsds) {
		this.licenciaMsds = licenciaMsds;
	}

	public String getPrimerApellido() {
		return primerApellido;
	}

	public void setPrimerApellido(String primerApellido) {
		this.primerApellido = primerApellido;
	}

	public String getPrimerNombre() {
		return primerNombre;
	}

	public void setPrimerNombre(String primerNombre) {
		this.primerNombre = primerNombre;
	}

	public String getSegundoApellido() {
		return segundoApellido;
	}

	public void setSegundoApellido(String segundoApellido) {
		this.segundoApellido = segundoApellido;
	}

	public String getSegundoNombre() {
		return segundoNombre;
	}

	public void setSegundoNombre(String segundoNombre) {
		this.segundoNombre = segundoNombre;
	}

	public String getSexo() {
		return sexo;
	}

	public void setSexo(String sexo) {
		this.sexo = sexo;
	}

	public String getTelefono() {
		return telefono;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

	public Especialidad getEspecialidad() {
		return especialidad;
	}

	public void setEspecialidad(Especialidad especialidad) {
		this.especialidad = especialidad;
	}

	public Timestamp getFechaAuditoria() {
		return fechaAuditoria;
	}

	public void setFechaAuditoria(Timestamp fechaAuditoria) {
		this.fechaAuditoria = fechaAuditoria;
	}

	public String getHoraAuditoria() {
		return horaAuditoria;
	}

	public void setHoraAuditoria(String horaAuditoria) {
		this.horaAuditoria = horaAuditoria;
	}

	public String getUsuarioAuditoria() {
		return usuarioAuditoria;
	}

	public void setUsuarioAuditoria(String usuarioAuditoria) {
		this.usuarioAuditoria = usuarioAuditoria;
	}

	public List<Usuario> getUsuarios() {
		return usuarios;
	}

	public void setUsuarios(List<Usuario> usuarios) {
		this.usuarios = usuarios;
	}

	public List<Consulta> getConsultas() {
		return consultas;
	}

	public void setConsultas(List<Consulta> consultas) {
		this.consultas = consultas;
	}

	public long getCitasDiarias() {
		return citasDiarias;
	}

	public void setCitasDiarias(long citasDiarias) {
		this.citasDiarias = citasDiarias;
	}

}
