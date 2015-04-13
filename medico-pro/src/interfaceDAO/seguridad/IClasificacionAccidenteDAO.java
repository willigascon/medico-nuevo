package interfaceDAO.seguridad;

import java.util.List;

import modelo.seguridad.ClasificacionAccidente;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface IClasificacionAccidenteDAO  extends JpaRepository<ClasificacionAccidente, Long>{

	@Query("select c from ClasificacionAccidente c order by c.nombre asc")
	List<ClasificacionAccidente> findAllOrderByNombreAsc();
	
	List<ClasificacionAccidente> findByNombreStartingWithAllIgnoreCase(String valor);

	ClasificacionAccidente findByNombre(String value);

}
