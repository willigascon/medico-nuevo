package interfaceDAO.medico.consulta;

import modelo.medico.consulta.Recipe;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface IRecipeDAO extends JpaRepository<Recipe, Long> {

	@Query("select coalesce(max(recipe.idRecipe), '0') from Recipe recipe")
	long findMaxIdRecipe();

}
