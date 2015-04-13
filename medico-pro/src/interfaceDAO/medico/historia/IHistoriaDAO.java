package interfaceDAO.medico.historia;

import modelo.medico.historia.Historia;
import modelo.medico.maestro.Paciente;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface IHistoriaDAO extends JpaRepository<Historia, Long> {

	Historia findByPaciente(Paciente paciente);

	@Query("select coalesce(max(h.idHistoria), '0') from Historia h")
	long findMaxIdHistoria();

}
