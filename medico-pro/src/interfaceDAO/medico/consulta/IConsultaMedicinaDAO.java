package interfaceDAO.medico.consulta;

import java.util.List;

import modelo.medico.consulta.Consulta;
import modelo.medico.consulta.ConsultaMedicina;
import modelo.medico.maestro.Medicina;
import modelo.pk.ConsultaMedicinaId;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface IConsultaMedicinaDAO extends
		JpaRepository<ConsultaMedicina, ConsultaMedicinaId> {

	List<ConsultaMedicina> findByConsulta(Consulta consulta);

	List<ConsultaMedicina> findByMedicina(Medicina medicina);

	@Query("select coalesce(c.cantidad,'0') * coalesce(c.precio,'0') from ConsultaMedicina c where c.consulta=?1")
	Double costByConsulta(Consulta consulta);

}
