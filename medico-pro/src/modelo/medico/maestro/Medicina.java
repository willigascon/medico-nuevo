package modelo.medico.maestro;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import modelo.medico.consulta.ConsultaMedicina;

/**
 * The persistent class for the medicina database table.
 * 
 */
@Entity
@Table(name = "medicina")
public class Medicina implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_medicina", unique = true, nullable = false)
	private long idMedicina;

	@Column(length = 1000)
	private String composicion;

	@Column(length = 1000)
	private String contraindicaciones;

	@Column(name = "denominacion_generica", length = 1000)
	private String denominacionGenerica;

	@Column(length = 1000)
	private String efectos;

	@Column(length = 1000)
	private String embarazo;

	@Column(name = "fecha_auditoria")
	private Timestamp fechaAuditoria;

	@Column(name = "hora_auditoria", length = 10)
	private String horaAuditoria;

	@Column(length = 1000)
	private String indicaciones;

	@Column(length = 500)
	private String nombre;

	@Column(length = 1000)
	private String posologia;

	@Column(length = 1000)
	private String precaucion;

	@Column(name = "usuario_auditoria", length = 50)
	private String usuarioAuditoria;

	// bi-directional many-to-one association to Laboratorio
	@Column(name = "laboratorio", length = 250)
	private String laboratorio;

	@Column(name = "categoria_medicina", length = 250)
	private String categoriaMedicina;

	@OneToMany(mappedBy = "medicina")
	private Set<ConsultaMedicina> medicinas;

	@Column(name = "id_referencia")
	private Long idReferencia;

	@Column(name = "precio")
	private Double precio;

	public Medicina() {
	}

	public Medicina(long idMedicina, String composicion,
			String contraindicaciones, String denominacionGenerica,
			String efectos, String embarazo, Timestamp fechaAuditoria,
			String horaAuditoria, String indicaciones, String nombre,
			String posologia, String precaucion, String usuarioAuditoria,
			String laboratorio, String categoriaMedicina,
			Double precio) {
		super();
		this.precio = precio;
		this.idMedicina = idMedicina;
		this.composicion = composicion;
		this.contraindicaciones = contraindicaciones;
		this.denominacionGenerica = denominacionGenerica;
		this.efectos = efectos;
		this.embarazo = embarazo;
		this.fechaAuditoria = fechaAuditoria;
		this.horaAuditoria = horaAuditoria;
		this.indicaciones = indicaciones;
		this.nombre = nombre;
		this.posologia = posologia;
		this.precaucion = precaucion;
		this.usuarioAuditoria = usuarioAuditoria;
		this.laboratorio = laboratorio;
		this.categoriaMedicina = categoriaMedicina;
	}

	public long getIdMedicina() {
		return this.idMedicina;
	}

	public void setIdMedicina(long idMedicina) {
		this.idMedicina = idMedicina;
	}

	public String getComposicion() {
		return this.composicion;
	}

	public void setComposicion(String composicion) {
		this.composicion = composicion;
	}

	public String getContraindicaciones() {
		return this.contraindicaciones;
	}

	public void setContraindicaciones(String contraindicaciones) {
		this.contraindicaciones = contraindicaciones;
	}

	public String getDenominacionGenerica() {
		return this.denominacionGenerica;
	}

	public void setDenominacionGenerica(String denominacionGenerica) {
		this.denominacionGenerica = denominacionGenerica;
	}

	public String getEfectos() {
		return this.efectos;
	}

	public void setEfectos(String efectos) {
		this.efectos = efectos;
	}

	public String getEmbarazo() {
		return this.embarazo;
	}

	public void setEmbarazo(String embarazo) {
		this.embarazo = embarazo;
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

	public String getIndicaciones() {
		return this.indicaciones;
	}

	public void setIndicaciones(String indicaciones) {
		this.indicaciones = indicaciones;
	}

	public String getNombre() {
		return this.nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getPosologia() {
		return this.posologia;
	}

	public void setPosologia(String posologia) {
		this.posologia = posologia;
	}

	public String getPrecaucion() {
		return this.precaucion;
	}

	public void setPrecaucion(String precaucion) {
		this.precaucion = precaucion;
	}

	public String getUsuarioAuditoria() {
		return this.usuarioAuditoria;
	}

	public void setUsuarioAuditoria(String usuarioAuditoria) {
		this.usuarioAuditoria = usuarioAuditoria;
	}

	public String getLaboratorio() {
		return laboratorio;
	}

	public void setLaboratorio(String laboratorio) {
		this.laboratorio = laboratorio;
	}

	public String getCategoriaMedicina() {
		return categoriaMedicina;
	}

	public void setCategoriaMedicina(String categoriaMedicina) {
		this.categoriaMedicina = categoriaMedicina;
	}

	public Set<ConsultaMedicina> getMedicinas() {
		return medicinas;
	}

	public void setMedicinas(Set<ConsultaMedicina> medicinas) {
		this.medicinas = medicinas;
	}

	public Long getIdReferencia() {
		return idReferencia;
	}

	public void setIdReferencia(Long idReferencia) {
		this.idReferencia = idReferencia;
	}

	public Double getPrecio() {
		return precio;
	}

	public void setPrecio(Double precio) {
		this.precio = precio;
	}

}