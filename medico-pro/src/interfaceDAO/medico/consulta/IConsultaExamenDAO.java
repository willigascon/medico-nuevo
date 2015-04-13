package interfaceDAO.medico.consulta;

import java.util.Date;
import java.util.List;

import modelo.medico.consulta.Consulta;
import modelo.medico.consulta.ConsultaExamen;
import modelo.medico.maestro.Examen;
import modelo.medico.maestro.Proveedor;
import modelo.pk.ConsultaExamenId;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface IConsultaExamenDAO  extends JpaRepository<ConsultaExamen, ConsultaExamenId> {

	List<ConsultaExamen> findByConsulta(Consulta consulta);

	List<ConsultaExamen> findByProveedor(Proveedor proveedor);

	List<ConsultaExamen> findByExamen(Examen examen);

	List<ConsultaExamen> findByConsultaOrderByProveedorIdProveedorAsc(
			Consulta consulta);

	List<ConsultaExamen> findByConsultaAndProveedorIdProveedor(
			Consulta consuta, Long part5);

	@Query("select coalesce((SUM(c.costo)),'0') from ConsultaExamen c where c.consulta=?1")
	double sumByConsulta(Consulta consulta);

	List<ConsultaExamen> findByProveedorAndConsultaFechaConsultaBetween(
			Proveedor proveedor, Date desde, Date hasta, Sort o);

	List<ConsultaExamen> findByConsultaFechaConsultaBetweenAndProveedorNotNull(
			Date desde, Date hasta, Sort o);

}
