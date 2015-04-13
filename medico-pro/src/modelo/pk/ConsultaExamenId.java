package modelo.pk;

import java.io.Serializable;

import modelo.medico.consulta.Consulta;
import modelo.medico.maestro.Examen;

public class ConsultaExamenId implements Serializable {

	private static final long serialVersionUID = -6408534785309423469L;
	
	private Consulta consulta;
	private Examen examen;
	
	public Consulta getConsulta() {
		return consulta;
	}
	public void setConsulta(Consulta consulta) {
		this.consulta = consulta;
	}
	public Examen getExamen() {
		return examen;
	}
	public void setExamen(Examen examen) {
		this.examen = examen;
	}
	
}
