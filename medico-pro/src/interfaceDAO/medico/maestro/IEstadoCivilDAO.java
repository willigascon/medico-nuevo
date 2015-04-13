package interfaceDAO.medico.maestro;

import java.util.List;

import modelo.medico.maestro.EstadoCivil;

import org.springframework.data.jpa.repository.JpaRepository;

public interface IEstadoCivilDAO extends JpaRepository<EstadoCivil, Long>{

	List<EstadoCivil> findByNombreStartingWithAllIgnoreCase(String valor);

	EstadoCivil findByNombre(String value);

}
