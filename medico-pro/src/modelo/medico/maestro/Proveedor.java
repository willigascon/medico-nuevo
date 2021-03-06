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

import modelo.medico.consulta.ConsultaExamen;
import modelo.medico.consulta.ConsultaServicioExterno;
import modelo.organizacion.Ciudad;

@Entity
@Table(name="proveedor")
public class Proveedor implements Serializable {

	private static final long serialVersionUID = 4881695044311813270L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id_proveedor", unique=true, nullable=false)
	private long idProveedor;

	@Column(length=1024)
	private String direccion;

	@Column(length=500)
	private String nombre;
	
	@Column(length=20)
	private String telefono;
	
	@Column(name="fecha_auditoria")
	private Timestamp fechaAuditoria;

	@Column(name="hora_auditoria", length=10)
	private String horaAuditoria;
	
	@Column(name="usuario_auditoria", length=50)
	private String usuarioAuditoria;

	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="id_ciudad")
	private Ciudad ciudad;
	
	@OneToMany(mappedBy = "proveedor")
	private Set<ConsultaExamen> servicios;
	
	@OneToMany(mappedBy = "proveedor")
	private Set<ConsultaServicioExterno> serviciosExternos;
	
	@OneToMany(mappedBy = "proveedor")
	private Set<ProveedorExamen> proveedoresExamenes;
	
	@OneToMany(mappedBy = "proveedor")
	private Set<ProveedorServicio> proveedoresServicios;
	
	@Column(name="costo")
	private Double costo;
	
	public Proveedor() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Proveedor(long idProveedor, String direccion, String nombre,
			String telefono, Timestamp fechaAuditoria, String horaAuditoria,
			String usuarioAuditoria, Ciudad ciudad, Double costo) {
		super();
		this.idProveedor = idProveedor;
		this.direccion = direccion;
		this.nombre = nombre;
		this.telefono = telefono;
		this.fechaAuditoria = fechaAuditoria;
		this.horaAuditoria = horaAuditoria;
		this.usuarioAuditoria = usuarioAuditoria;
		this.ciudad = ciudad;
		this.costo = costo;
	}

	public long getIdProveedor() {
		return idProveedor;
	}

	public void setIdProveedor(long idProveedor) {
		this.idProveedor = idProveedor;
	}

	public String getDireccion() {
		return direccion;
	}

	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getTelefono() {
		return telefono;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
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

	public Ciudad getCiudad() {
		return ciudad;
	}

	public void setCiudad(Ciudad ciudad) {
		this.ciudad = ciudad;
	}

	public Set<ConsultaExamen> getServicios() {
		return servicios;
	}

	public void setServicios(Set<ConsultaExamen> servicios) {
		this.servicios = servicios;
	}

	public Set<ProveedorExamen> getProveedoresExamenes() {
		return proveedoresExamenes;
	}

	public void setProveedoresExamenes(Set<ProveedorExamen> proveedoresExamenes) {
		this.proveedoresExamenes = proveedoresExamenes;
	}

	public Set<ProveedorServicio> getProveedoresServicios() {
		return proveedoresServicios;
	}

	public void setProveedoresServicios(Set<ProveedorServicio> proveedoresServicios) {
		this.proveedoresServicios = proveedoresServicios;
	}

	public Set<ConsultaServicioExterno> getServiciosExternos() {
		return serviciosExternos;
	}

	public void setServiciosExternos(Set<ConsultaServicioExterno> serviciosExternos) {
		this.serviciosExternos = serviciosExternos;
	}

	public Double getCosto() {
		return costo;
	}

	public void setCosto(Double costo) {
		this.costo = costo;
	}

}
