package interfaceDAO.security;

import java.util.List;
import java.util.Set;

import modelo.security.Arbol;
import modelo.security.Grupo;
import modelo.security.Usuario;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface IGrupoDAO extends JpaRepository<Grupo, Long> {
	
	public List<Grupo> findByUsuarios(Usuario usuario);

	public List<Grupo> findByEstadoTrue();

	public List<Grupo> findByIdGrupoNotInAndEstadoTrue(List<Long> ids);

	public Grupo findByNombre(String nombreGrupo);

	public List<Grupo> findByNombreStartingWithAllIgnoreCase(String valor);

	@Query("select coalesce(max(consulta.idGrupo), '0') from Grupo consulta")
	public long findMaxIdExamen();

	public List<Grupo> findByUsuariosOrderByNombreAsc(Usuario u);

	public List<Grupo> findByArbols(Set<Arbol> arbols);
}
