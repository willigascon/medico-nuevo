package interfaceDAO.medico.historia;

import java.util.List;

import modelo.medico.historia.Antecedente;
import modelo.medico.historia.PacienteAntecedente;
import modelo.medico.maestro.Paciente;
import modelo.pk.PacienteAntecedenteId;

import org.springframework.data.jpa.repository.JpaRepository;

public interface IPacienteAntecedenteDAO extends JpaRepository<PacienteAntecedente, PacienteAntecedenteId> {

	List<PacienteAntecedente> findByPacienteAndAntecedenteAntecedenteTipoTipoOrderByAntecedenteAntecedenteTipoNombreAsc(
			Paciente paciente, String string);

	List<PacienteAntecedente> findByPaciente(Paciente paciente);

	List<PacienteAntecedente> findByAntecedente(Antecedente antecedente);

}
