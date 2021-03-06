package interfaceDAO.medico.maestro;

import java.util.List;

import modelo.medico.maestro.CategoriaDiagnostico;
import modelo.medico.maestro.Diagnostico;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface IDiagnosticoDAO extends JpaRepository<Diagnostico, Long> {

	List<Diagnostico> findByCategoria(CategoriaDiagnostico categoria);

	Diagnostico findByCodigo(String value);

	List<Diagnostico> findByNombreStartingWithAllIgnoreCase(String valor);

	List<Diagnostico> findByCodigoStartingWithAllIgnoreCase(String valor);

	List<Diagnostico> findByCategoriaNombreStartingWithAllIgnoreCase(
			String valor);

	List<Diagnostico> findByIdDiagnosticoNotIn(List<Long> ids);

	@Query("select coalesce(max(consulta.idDiagnostico), '0') from Diagnostico consulta")
	long findMaxIdDiagnostico();

	Diagnostico findByIdReferencia(long idRefD);

}
