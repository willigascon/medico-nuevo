package interfaceDAO.medico.maestro;

import java.util.List;

import modelo.medico.maestro.Proveedor;
import modelo.medico.maestro.ProveedorServicio;
import modelo.medico.maestro.ServicioExterno;
import modelo.pk.ProveedorServicioId;

import org.springframework.data.jpa.repository.JpaRepository;

public interface IProveedorServicioDAO extends JpaRepository<ProveedorServicio, ProveedorServicioId> {

	List<ProveedorServicio> findByServicioExterno(ServicioExterno servicio);

	List<ProveedorServicio> findByProveedor(Proveedor proveedor);

	ProveedorServicio findByProveedorIdProveedorAndServicioExternoIdServicioExterno(
			long parseLong, long id);

	List<ProveedorServicio> findByServicioExternoIdServicioExterno(long id);

}
