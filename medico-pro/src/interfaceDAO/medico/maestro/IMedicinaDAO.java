package interfaceDAO.medico.maestro;

import java.util.List;
import modelo.medico.maestro.Medicina;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface IMedicinaDAO extends JpaRepository<Medicina, Long> {

	List<Medicina> findByNombre(String value);

	List<Medicina> findByNombreStartingWithAllIgnoreCase(String valor);

	List<Medicina> findByLaboratorioStartingWithAllIgnoreCase(String valor);

	List<Medicina> findByPosologiaStartingWithAllIgnoreCase(String valor);

	@Query("select coalesce(max(medicina.idMedicina), '0') from Medicina medicina")
	long findMaxIdMedicina();

	List<Medicina> findByIdMedicinaNotIn(List<Long> ids);

	Medicina findByIdReferencia(long idRefD);

	List<Medicina> findByDenominacionGenericaStartingWithAllIgnoreCase(
			String valor);

	@Query("select coalesce(max(medicina.idReferencia), '0') from Medicina medicina")
	long findMaxIdReferencia();


}
