package interfaceDAO.medico.maestro;

import java.util.List;

import modelo.medico.maestro.Especialidad;
import modelo.medico.maestro.Especialista;

import org.springframework.data.jpa.repository.JpaRepository;

public interface IEspecialistaDAO  extends JpaRepository<Especialista, String>{

	List<Especialista> findByCedulaStartingWithAllIgnoreCase(String valor);

	List<Especialista> findByNombreStartingWithAllIgnoreCase(String valor);

	List<Especialista> findByApellidoStartingWithAllIgnoreCase(String valor);

	List<Especialista> findByCostoStartingWithAllIgnoreCase(String valor);

	List<Especialista> findByEspecialidadDescripcionStartingWithAllIgnoreCase(
			String valor);

	List<Especialista> findByEspecialidad(Especialidad especialidad);

	List<Especialista> findByCedulaNotIn(List<String> ids);

	List<Especialista> findByEspecialidadDescripcionStartingWithOrApellidoStartingWithOrNombreStartingWithAllIgnoreCase(
			String valor, String valor2, String valor3);

	Especialista findByRif(String value);

}
