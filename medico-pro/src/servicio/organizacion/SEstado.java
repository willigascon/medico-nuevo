package servicio.organizacion;

import interfaceDAO.organizacion.IEstadoDAO;

import java.util.List;

import modelo.organizacion.Estado;
import modelo.organizacion.Pais;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("SEstado")
public class SEstado {

	@Autowired
	private IEstadoDAO estadoDAO;

	public void guardar(Estado estado) {
		estadoDAO.save(estado);
	}

	public Estado buscar(long id) {
		return estadoDAO.findOne(id);
	}

	public void eliminar(Estado estado) {
		estadoDAO.delete(estado);
	}

	public List<Estado> buscarTodos() {
		return estadoDAO.findAll();
	}

	public List<Estado> filtroNombre(String valor) {
		return estadoDAO.findByNombreStartingWithAllIgnoreCase(valor);
	}

	public Estado buscarPorNombre(String value) {
		return estadoDAO.findByNombre(value);
	}

	public List<Estado> buscarPorPais(Pais pais) {
		return estadoDAO.findByPais(pais);
	}

	public List<Estado> filtroPais(String valor) {
		return estadoDAO.findByPaisNombreStartingWithAllIgnoreCase(valor);
	}
}
