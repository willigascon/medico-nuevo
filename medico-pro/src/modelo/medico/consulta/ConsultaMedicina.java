package modelo.medico.consulta;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import modelo.medico.maestro.Medicina;
import modelo.pk.ConsultaMedicinaId;

@Entity
@Table(name = "consulta_medicina")
@IdClass(ConsultaMedicinaId.class)
public class ConsultaMedicina {

	@Id
	@ManyToOne(optional = false, fetch = FetchType.LAZY)
	@JoinColumn(name = "id_consulta", referencedColumnName = "id_consulta")
	private Consulta consulta;
	
	@Id
	@ManyToOne(optional = false, fetch = FetchType.LAZY)
	@JoinColumn(name = "id_medicina", referencedColumnName = "id_medicina")
	private Medicina medicina;
	
	@ManyToOne(optional = false, fetch = FetchType.LAZY)
	@JoinColumn(name = "id_recipe", referencedColumnName = "id_recipe")
	private Recipe recipe;
	
	@Column(length=1000)
	private String dosis;
	
	@Column(name="cantidad")
	private Integer cantidad;
	
	@Column(name="precio_individual")
	private Double precio;

	public ConsultaMedicina() {
		super();
		// TODO Auto-generated constructor stub
	}

	public ConsultaMedicina(Consulta consulta, Medicina medicina, String dosis, Recipe recipe, int cantidad, double precio) {
		super();
		this.consulta = consulta;
		this.medicina = medicina;
		this.dosis = dosis;
		this.recipe = recipe;
		this.cantidad = cantidad;
		this.precio = precio;
	}

	public Consulta getConsulta() {
		return consulta;
	}

	public void setConsulta(Consulta consulta) {
		this.consulta = consulta;
	}

	public Medicina getMedicina() {
		return medicina;
	}

	public void setMedicina(Medicina medicina) {
		this.medicina = medicina;
	}

	public Recipe getRecipe() {
		return recipe;
	}

	public void setRecipe(Recipe recipe) {
		this.recipe = recipe;
	}

	public String getDosis() {
		return dosis;
	}

	public void setDosis(String dosis) {
		this.dosis = dosis;
	}

	public Integer getCantidad() {
		return cantidad;
	}

	public void setCantidad(Integer cantidad) {
		this.cantidad = cantidad;
	}

	public Double getPrecio() {
		return precio;
	}

	public void setPrecio(Double precio) {
		this.precio = precio;
	}
	
}
