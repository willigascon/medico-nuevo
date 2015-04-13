package interfaceDAO.medico.consulta;

import java.util.Date;
import java.util.List;

import modelo.medico.consulta.Consulta;
import modelo.medico.consulta.ConsultaEspecialista;
import modelo.medico.maestro.Especialista;
import modelo.pk.ConsultaEspecialistaId;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface IConsultaEspecialistaDAO  extends JpaRepository<ConsultaEspecialista, ConsultaEspecialistaId> {

	List<ConsultaEspecialista> findByConsulta(Consulta consulta);

	ConsultaEspecialista findByConsultaAndEspecialistaCedula(Consulta consuta,
			String par3);

	List<ConsultaEspecialista> findByEspecialista(Especialista especialista);

	@Query("select coalesce((SUM(c.costo)),'0') from ConsultaEspecialista c where c.consulta=?1")
	double sumByConsulta(Consulta consulta);

	List<ConsultaEspecialista> findByEspecialistaAndConsultaFechaConsultaBetween(
			Especialista especialista, Date desde, Date hasta, Sort o);

	List<ConsultaEspecialista> findByConsultaFechaConsultaBetween(Date desde,
			Date hasta, Sort o);

}
