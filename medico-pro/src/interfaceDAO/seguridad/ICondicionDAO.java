package interfaceDAO.seguridad;

import java.util.List;

import modelo.seguridad.Condicion;
import modelo.seguridad.Informe;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ICondicionDAO extends JpaRepository<Condicion, Long> {

	@Query("select c from Condicion c order by c.nombre asc")
	List<Condicion> findAllOrderByNombre();

	List<Condicion> findByInformesAAndTipo(Informe informe, String string);

	List<Condicion> findByTipo(String string);

	Condicion findByIdCondicion(long id);

	@Query("select c from Condicion c order by c.tipo asc")
	List<Condicion> findAllOrderByTipo();

	List<Condicion> findByNombreStartingWithAllIgnoreCase(String valor);

	List<Condicion> findByTipoStartingWithAllIgnoreCase(String valor);

	Condicion findByNombre(String value);

	List<Condicion> findByInformesFAndTipo(Informe informe, String string);

	List<Condicion> findByInformesEAndTipo(Informe informe, String string);

	List<Condicion> findByInformesDAndTipo(Informe informe, String string);

	List<Condicion> findByInformesCAndTipo(Informe informe, String string);

	List<Condicion> findByInformesBAndTipo(Informe informe, String string);


}
