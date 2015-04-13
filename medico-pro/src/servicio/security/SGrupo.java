package servicio.security;

import interfaceDAO.security.IGrupoDAO;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import modelo.security.Arbol;
import modelo.security.Grupo;
import modelo.security.Usuario;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("SGrupo")
public class SGrupo {

	@Autowired
	private IGrupoDAO grupoDAO;
	
	public void guardarGrupo(Grupo grupo){
		grupoDAO.save(grupo);
	}
	
	public List<Grupo> buscarTodos(){
		return grupoDAO.findByEstadoTrue();
	}
	public Grupo buscarGrupo(long id){
		return grupoDAO.findOne(id);
	}
	
	public List<Grupo> buscarGruposDelUsuario(Usuario usuario){
		return grupoDAO.findByUsuarios(usuario);
	}
	
	public List<Grupo> buscarGruposDisponibles(List<Long> ids){
		return grupoDAO.findByIdGrupoNotInAndEstadoTrue(ids);
	}
	
	public Grupo buscarPorNombre(String nombreGrupo){
		return grupoDAO.findByNombre(nombreGrupo);
	}

	public List<Grupo> filtroNombre(String valor) {
		return grupoDAO.findByNombreStartingWithAllIgnoreCase(valor);
	}

	public void eliminar(Grupo grupo) {
		grupoDAO.delete(grupo);
	}

	public Grupo buscarUltimo() {
		long id = grupoDAO.findMaxIdExamen();
		if (id != 0)
			return grupoDAO.findOne(id);
		return null;
	}

	public List<Grupo> buscarGruposUsuario(Usuario u) {
		return grupoDAO.findByUsuariosOrderByNombreAsc(u);
	}

	public List<Grupo> buscarArboles(List<Arbol> eliminarLista) {
		Set<Arbol> arbols = new HashSet<Arbol>(eliminarLista);
		return grupoDAO.findByArbols(arbols);
	}
}