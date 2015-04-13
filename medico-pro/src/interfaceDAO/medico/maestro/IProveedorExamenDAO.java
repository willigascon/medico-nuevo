package interfaceDAO.medico.maestro;

import java.util.List;

import modelo.medico.maestro.Examen;
import modelo.medico.maestro.Proveedor;
import modelo.medico.maestro.ProveedorExamen;
import modelo.pk.ProveedorExamenId;

import org.springframework.data.jpa.repository.JpaRepository;

public interface IProveedorExamenDAO extends JpaRepository<ProveedorExamen, ProveedorExamenId> {

	List<ProveedorExamen> findByExamen(Examen examen);

	List<ProveedorExamen> findByProveedor(Proveedor proveedor);

	ProveedorExamen findByProveedorAndExamen(Proveedor proveedor, Examen examen);

}
