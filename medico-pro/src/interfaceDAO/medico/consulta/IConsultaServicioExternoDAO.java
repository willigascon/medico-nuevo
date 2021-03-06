package interfaceDAO.medico.consulta;

import java.util.Date;
import java.util.List;

import modelo.medico.consulta.Consulta;
import modelo.medico.consulta.ConsultaServicioExterno;
import modelo.medico.maestro.Proveedor;
import modelo.medico.maestro.ServicioExterno;
import modelo.pk.ConsultaServicioExternoId;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface IConsultaServicioExternoDAO extends JpaRepository<ConsultaServicioExterno, ConsultaServicioExternoId> {

	List<ConsultaServicioExterno> findByConsulta(Consulta consulta);

	ConsultaServicioExterno findByConsultaAndServicioExternoIdServicioExterno(
			Consulta consuta, Long part4);

	List<ConsultaServicioExterno> findByProveedor(Proveedor proveedor);

	List<ConsultaServicioExterno> findByServicioExterno(
			ServicioExterno servicioExterno);

	@Query("select coalesce((SUM(c.costo)),'0') from ConsultaServicioExterno c where c.consulta=?1")
	double sumByConsulta(Consulta consulta);

	List<ConsultaServicioExterno> findByConsultaAndProveedorIdProveedor(
			Consulta consuta, Long part5);

	List<ConsultaServicioExterno> findByProveedorAndConsultaFechaConsultaBetween(
			Proveedor proveedor, Date desde, Date hasta, Sort o);

	List<ConsultaServicioExterno> findByConsultaFechaConsultaBetweenAndProveedorNotNull(
			Date desde, Date hasta, Sort o);

}
