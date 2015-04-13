package interfaceDAO.medico.maestro;

import java.util.List;

import modelo.medico.maestro.CategoriaMedicina;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ICategoriaMedicinaDAO extends JpaRepository<CategoriaMedicina, Long> {

	List<CategoriaMedicina> findByNombreStartingWithAllIgnoreCase(String valor);

	CategoriaMedicina findByNombre(String value);

}
