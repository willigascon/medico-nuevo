package interfaceDAO.medico.consulta;

import java.util.List;

import modelo.medico.consulta.Consulta;
import modelo.medico.consulta.ConsultaMedicina;
import modelo.medico.maestro.Medicina;
import modelo.pk.ConsultaMedicinaId;

import org.springframework.data.jpa.repository.JpaRepository;

public interface IConsultaMedicinaDAO extends JpaRepository<ConsultaMedicina, ConsultaMedicinaId> {

	List<ConsultaMedicina> findByConsulta(Consulta consulta);

	List<ConsultaMedicina> findByMedicina(Medicina medicina);

}
