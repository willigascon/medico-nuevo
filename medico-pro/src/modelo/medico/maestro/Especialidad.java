package modelo.medico.maestro;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import modelo.security.Usuario;


/**
 * The persistent class for the especialidad database table.
 * 
 */
@Entity
@Table(name="especialidad")
public class Especialidad implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id_especialidad", unique=true, nullable=false)
	private long idEspecialidad;

	@Column(length=500)
	private String descripcion;

	@Column(name="fecha_auditoria")
	private Timestamp fechaAuditoria;

	@Column(name="hora_auditoria", length=10)
	private String horaAuditoria;

	@Column(name="usuario_auditoria", length=50)
	private String usuarioAuditoria;

	//bi-directional many-to-one association to Usuario
	@OneToMany(mappedBy="especialidad")
	private Set<DoctorInterno> usuarios;

	@OneToMany(mappedBy="especialidad")
	private Set<Especialista> especialistas;
	
	public Especialidad() {
	}
	
	public Especialidad(long idEspecialidad, String descripcion,
			Timestamp fechaAuditoria, String horaAuditoria,
			String usuarioAuditoria) {
		super();
		this.idEspecialidad = idEspecialidad;
		this.descripcion = descripcion;
		this.fechaAuditoria = fechaAuditoria;
		this.horaAuditoria = horaAuditoria;
		this.usuarioAuditoria = usuarioAuditoria;
	}



	public long getIdEspecialidad() {
		return this.idEspecialidad;
	}

	public void setIdEspecialidad(long idEspecialidad) {
		this.idEspecialidad = idEspecialidad;
	}

	public String getDescripcion() {
		return this.descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public Timestamp getFechaAuditoria() {
		return this.fechaAuditoria;
	}

	public void setFechaAuditoria(Timestamp fechaAuditoria) {
		this.fechaAuditoria = fechaAuditoria;
	}

	public String getHoraAuditoria() {
		return this.horaAuditoria;
	}

	public void setHoraAuditoria(String horaAuditoria) {
		this.horaAuditoria = horaAuditoria;
	}

	public String getUsuarioAuditoria() {
		return this.usuarioAuditoria;
	}

	public void setUsuarioAuditoria(String usuarioAuditoria) {
		this.usuarioAuditoria = usuarioAuditoria;
	}

	public Set<Especialista> getEspecialistas() {
		return especialistas;
	}

	public void setEspecialistas(Set<Especialista> especialistas) {
		this.especialistas = especialistas;
	}

	public Set<DoctorInterno> getUsuarios() {
		return usuarios;
	}

	public void setUsuarios(Set<DoctorInterno> usuarios) {
		this.usuarios = usuarios;
	}

}