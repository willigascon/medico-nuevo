package interfaceDAO.security;

import java.util.ArrayList;
import java.util.List;

import modelo.security.Arbol;
import modelo.security.Grupo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface IArbolDAO extends JpaRepository<Arbol, Long> {
	
	 public List<Arbol> findByNombre(String nombre);

	 @Query("select a from Arbol a order by a.idArbol asc")
	public List<Arbol> buscarTodos();
	
	 @Query("select a from Arbol a where a.idArbol = ?1 order by a.idArbol")
	public List<Arbol> buscar(ArrayList<Long> ids);

	public List<Arbol> findByGruposArbol(Grupo grupo);

	public List<Arbol> findByNombreStartingWithAllIgnoreCase(String valor);

	public List<Arbol> findByPadreStartingWithAllIgnoreCase(String valor);

	public List<Arbol> findByUrlStartingWithAllIgnoreCase(String valor);

		
	
}
