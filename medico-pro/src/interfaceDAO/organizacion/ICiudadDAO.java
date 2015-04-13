package interfaceDAO.organizacion;

import java.util.List;

import modelo.organizacion.Ciudad;
import modelo.organizacion.Estado;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ICiudadDAO extends JpaRepository<Ciudad, Long> {

	List<Ciudad> findByEstado(Estado estado);

	List<Ciudad> findByNombreStartingWithAllIgnoreCase(String valor);

	List<Ciudad> findByEstadoNombreStartingWithAllIgnoreCase(String valor);

	Ciudad findByNombre(String value);

	List<Ciudad> findByEstadoPaisNombreStartingWithAllIgnoreCase(String valor);

}
