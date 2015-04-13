package servicio.security;

import interfaceDAO.security.IArbolDAO;

import java.util.ArrayList;
import java.util.List;

import modelo.security.Arbol;
import modelo.security.Grupo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("SArbol")
public class SArbol {

	@Autowired
	private IArbolDAO arbolDAO;

	public void guardar(Arbol arbol) {
		arbolDAO.save(arbol);
	}

	public Arbol buscar(long id) {
		return arbolDAO.findOne(id);
	}

	public List<Arbol> listarArbol() {
		return arbolDAO.buscarTodos();
	}

	public List<Arbol> buscarPorNombreArbol(String nombre) {
		return arbolDAO.findByNombre(nombre);
	}

	public List<Arbol> ordenarPorID(ArrayList<Long> ids) {
		List<Arbol> arboles;
		arboles = arbolDAO.buscar(ids);
		return arboles;
	}

	public List<Arbol> buscarporGrupo(Grupo grupo) {
		List<Arbol> arboles;
		arboles = arbolDAO.findByGruposArbol(grupo);
		return arboles;
	}

	public void eliminar(long clave) {
		arbolDAO.delete(clave);
	}

	public List<Arbol> filtroNombre(String valor) {
		return arbolDAO.findByNombreStartingWithAllIgnoreCase(valor);
	}

	public List<Arbol> filtroPadre(String valor) {
		return arbolDAO.findByPadreStartingWithAllIgnoreCase(valor);
	}

	public List<Arbol> filtroUrl(String valor) {
		return arbolDAO.findByUrlStartingWithAllIgnoreCase(valor);
	}

	public Arbol buscarPorNombre(String value) {
		List<Arbol> lista = arbolDAO.findByNombre(value);
		Arbol arbol = null;
		if (!lista.isEmpty())
			arbol = lista.get(0);
		return arbol;
	}
}
