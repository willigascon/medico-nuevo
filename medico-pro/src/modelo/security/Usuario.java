package modelo.security;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.Lob;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import modelo.medico.maestro.DoctorInterno;

import org.hibernate.annotations.Type;

/**
 * The persistent class for the usuario database table.
 * 
 */
@Entity
@Table(name = "usuario")
public class Usuario implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "login", length = 12, unique = true, nullable = false)
	private String login;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "cedula_doctor", referencedColumnName = "cedula")
	private DoctorInterno doctorInterno;

	@Column(length = 50)
	private String email;

	@Column(length = 256)
	private String password;

	@Lob
	private byte[] imagen;

	@Type(type = "org.hibernate.type.NumericBooleanType")
	private boolean estado;

	@Column(name = "nombre", length = 100)
	private String nombre;

	@Column(name = "apellido", length = 100)
	private String apellido;

	@Column(name = "fecha_auditoria")
	private Timestamp fechaAuditoria;

	@Column(name="hora_auditoria", length=10)
	private String horaAuditoria;

	@Column(name = "usuario_auditoria", length = 50)
	private String usuarioAuditoria;
	
	@ManyToMany
	@JoinTable(name = "grupo_usuario", joinColumns = { @JoinColumn(name = "id_usuario", nullable = false) },
	inverseJoinColumns = { @JoinColumn(name = "id_grupo", nullable = false) })
	private Set<Grupo> grupos;

	public Usuario() {
	}

	public Usuario(String login, DoctorInterno doctorInterno, String email,
			String password, byte[] imagen, boolean estado, String nombre,
			String apellido, Timestamp fechaAuditoria, String horaAuditoria,
			String usuarioAuditoria) {
		super();
		this.login = login;
		this.doctorInterno = doctorInterno;
		this.email = email;
		this.password = password;
		this.imagen = imagen;
		this.estado = estado;
		this.nombre = nombre;
		this.apellido = apellido;
		this.fechaAuditoria = fechaAuditoria;
		this.horaAuditoria = horaAuditoria;
		this.usuarioAuditoria = usuarioAuditoria;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public DoctorInterno getDoctorInterno() {
		return doctorInterno;
	}

	public void setDoctorInterno(DoctorInterno doctorInterno) {
		this.doctorInterno = doctorInterno;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public byte[] getImagen() {
		return imagen;
	}

	public void setImagen(byte[] imagen) {
		this.imagen = imagen;
	}

	public boolean isEstado() {
		return estado;
	}

	public void setEstado(boolean estado) {
		this.estado = estado;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getApellido() {
		return apellido;
	}

	public void setApellido(String apellido) {
		this.apellido = apellido;
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

	public Set<Grupo> getGrupos() {
		return grupos;
	}

	public void setGrupos(Set<Grupo> grupos) {
		this.grupos = grupos;
	}

	
}