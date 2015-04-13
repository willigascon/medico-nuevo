package servicio.medico.maestro;

import interfaceDAO.medico.maestro.IEstadoCivilDAO;

import java.util.List;

import modelo.medico.maestro.EstadoCivil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("SEstadoCivil")
public class SEstadoCivil {

	@Autowired
	private IEstadoCivilDAO estadoCivilDAO;
	
	public void guardar(EstadoCivil categ) {
		estadoCivilDAO.save(categ);
	}

	public List<EstadoCivil> buscarTodas() {
		return estadoCivilDAO.findAll();
	}

	public EstadoCivil buscar(long id) {
		return estadoCivilDAO.findOne(id);
	}

	public void eliminar(EstadoCivil categoria) {
		estadoCivilDAO.delete(categoria);
	}

	public List<EstadoCivil> filtroNombre(String valor) {
		return estadoCivilDAO.findByNombreStartingWithAllIgnoreCase(valor);
	}

	public EstadoCivil buscarPorNombre(String value) {
		return estadoCivilDAO.findByNombre(value);
	}

}
