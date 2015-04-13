package interfaceDAO.medico.maestro;

import java.util.List;

import modelo.medico.maestro.DoctorInterno;
import modelo.medico.maestro.Especialidad;

import org.springframework.data.jpa.repository.JpaRepository;

public interface IDoctorInternoDAO extends JpaRepository<DoctorInterno, String> {

	List<DoctorInterno> findByCedulaStartingWithAllIgnoreCase(String valor);

	List<DoctorInterno> findByFichaStartingWithAllIgnoreCase(String valor);

	List<DoctorInterno> findByPrimerNombreStartingWithAllIgnoreCase(String valor);

	List<DoctorInterno> findByPrimerApellidoStartingWithAllIgnoreCase(
			String valor);

	List<DoctorInterno> findByEspecialidad(Especialidad especialidad);

}
