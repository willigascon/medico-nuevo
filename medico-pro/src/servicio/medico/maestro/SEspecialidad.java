package servicio.medico.maestro;

import interfaceDAO.medico.maestro.IEspecialidadDAO;

import java.util.List;

import modelo.medico.maestro.Especialidad;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("SEspecialidad")
public class SEspecialidad {

	@Autowired
	private IEspecialidadDAO especialidadDAO;

	public void guardar(Especialidad especialidad) {
		especialidadDAO.save(especialidad);
	}

	public List<Especialidad> buscarTodas() {
		return especialidadDAO.findAll();
	}

	public Especialidad buscar(long id) {
		return especialidadDAO.findOne(id);
	}

	public void eliminar(Especialidad especialidad) {
		especialidadDAO.delete(especialidad);
	}

	public Especialidad buscarPorDescripcion(String value) {
		return especialidadDAO.findByDescripcion(value);
	}

	public List<Especialidad> filtroNombre(String valor) {
		return  especialidadDAO.findByDescripcionStartingWithAllIgnoreCase(valor);
	}
}
