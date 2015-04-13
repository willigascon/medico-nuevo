package servicio.medico.historia;

import interfaceDAO.medico.historia.IPacienteAntecedenteDAO;

import java.util.List;

import modelo.medico.historia.Antecedente;
import modelo.medico.historia.PacienteAntecedente;
import modelo.medico.maestro.Paciente;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("SPacienteAntecedente")
public class SPacienteAntecedente {

	@Autowired
	private IPacienteAntecedenteDAO pacienteAntecedenteDAO;

	public List<PacienteAntecedente> buscarAntecedentesPaciente(
			Paciente paciente, String valor) {
		return pacienteAntecedenteDAO
				.findByPacienteAndAntecedenteAntecedenteTipoTipoOrderByAntecedenteAntecedenteTipoNombreAsc(
						paciente, valor);
	}

	public void guardar(List<PacienteAntecedente> antecedentes) {
		pacienteAntecedenteDAO.save(antecedentes);
	}

	public void borrarAntecedentesPaciente(Paciente paciente) {
		List<PacienteAntecedente> borrados = pacienteAntecedenteDAO
				.findByPaciente(paciente);
		if (!borrados.isEmpty())
			pacienteAntecedenteDAO.delete(borrados);
	}

	public List<PacienteAntecedente> buscarPorAntecedente(
			Antecedente antecedente) {
		return pacienteAntecedenteDAO.findByAntecedente(antecedente);
	}

	public List<PacienteAntecedente> buscarAntecedentesPorPaciente(
			Paciente pacienteAModificar) {
		return pacienteAntecedenteDAO.findByPaciente(pacienteAModificar);
	}
}
