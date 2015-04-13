package modelo.pk;

import java.io.Serializable;

import modelo.medico.consulta.Consulta;
import modelo.medico.maestro.Medicina;

public class ConsultaMedicinaId implements Serializable {

	private static final long serialVersionUID = 5793312366181402350L;

	private Consulta consulta;
	private Medicina medicina;
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
	
	
}
