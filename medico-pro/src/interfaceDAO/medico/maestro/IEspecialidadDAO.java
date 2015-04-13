package interfaceDAO.medico.maestro;

import java.util.List;

import modelo.medico.maestro.Especialidad;

import org.springframework.data.jpa.repository.JpaRepository;

public interface IEspecialidadDAO extends JpaRepository<Especialidad, Long> {

	Especialidad findByDescripcion(String value);

	List<Especialidad> findByDescripcionStartingWithAllIgnoreCase(String valor);

}
