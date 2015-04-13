package interfaceDAO.medico.maestro;

import java.util.List;

import modelo.medico.maestro.MotivoCita;

import org.springframework.data.jpa.repository.JpaRepository;

public interface IMotivoCitaDAO extends JpaRepository<MotivoCita, Long> {

	List<MotivoCita> findByDescripcionStartingWithAllIgnoreCase(String valor);

	MotivoCita findByDescripcion(String value);

	List<MotivoCita> findByTipo(String value);

}
