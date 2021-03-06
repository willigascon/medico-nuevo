package modelo.pk;

import java.io.Serializable;

import modelo.medico.consulta.Consulta;
import modelo.medico.maestro.Diagnostico;

public class ConsultaDiagnosticoId implements Serializable {

	private static final long serialVersionUID = 575072839321496846L;
	
	private Consulta consulta;
	private Diagnostico diagnostico;
	
	public Consulta getConsulta() {
		return consulta;
	}
	public void setConsulta(Consulta consulta) {
		this.consulta = consulta;
	}
	public Diagnostico getDiagnostico() {
		return diagnostico;
	}
	public void setDiagnostico(Diagnostico diagnostico) {
		this.diagnostico = diagnostico;
	}
	
	
}
