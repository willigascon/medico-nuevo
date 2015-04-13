package interfaceDAO.medico.historia;

import java.util.List;

import modelo.medico.historia.AntecedenteTipo;

import org.springframework.data.jpa.repository.JpaRepository;

public interface IAntecedenteTipoDAO extends JpaRepository<AntecedenteTipo, Long> {

	List<AntecedenteTipo> findByNombreStartingWithAllIgnoreCase(String valor);

	AntecedenteTipo findByNombre(String value);

	List<AntecedenteTipo> findByTipoStartingWithAllIgnoreCase(String valor);

}
