package interfaceDAO.medico.maestro;

import java.util.List;

import modelo.medico.maestro.CategoriaDiagnostico;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ICategoriaDiagnosticoDAO extends JpaRepository<CategoriaDiagnostico, Long> {

	CategoriaDiagnostico findByNombre(String value);

	List<CategoriaDiagnostico> findByNombreStartingWithAllIgnoreCase(String valor);

	List<CategoriaDiagnostico> findByClasificacionIdClasificacion(long id);

}
