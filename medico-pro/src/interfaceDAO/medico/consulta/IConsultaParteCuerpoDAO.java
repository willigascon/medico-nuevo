package interfaceDAO.medico.consulta;

import java.util.List;

import modelo.medico.consulta.Consulta;
import modelo.medico.consulta.ConsultaParteCuerpo;
import modelo.medico.maestro.ParteCuerpo;
import modelo.pk.ConsultaParteCuerpoId;

import org.springframework.data.jpa.repository.JpaRepository;

public interface IConsultaParteCuerpoDAO extends JpaRepository<ConsultaParteCuerpo, ConsultaParteCuerpoId> {

	List<ConsultaParteCuerpo> findByConsulta(Consulta consulta);

	List<ConsultaParteCuerpo> findByParte(ParteCuerpo parte);

}
