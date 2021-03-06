package interfaceDAO.medico.maestro;

import java.util.List;

import modelo.medico.maestro.Examen;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface IExamenDAO extends JpaRepository<Examen, Long>{

	List<Examen> findByNombreStartingWithAllIgnoreCase(String valor);

	List<Examen> findByTipoStartingWithAllIgnoreCase(String valor);

	List<Examen> findByMinimoStartingWithAllIgnoreCase(String valor);

	List<Examen> findByMaximoStartingWithAllIgnoreCase(String valor);

	Examen findByNombre(String value);

	List<Examen> findByIdExamenNotIn(List<Long> ids);

	@Query("select coalesce(max(consulta.idExamen), '0') from Examen consulta")
	long findMaxIdExamen();

	Examen findByIdReferencia(long idRefD);

}
