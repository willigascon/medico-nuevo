package interfaceDAO.medico.historia;

import java.util.List;

import modelo.medico.historia.Antecedente;
import modelo.medico.historia.AntecedenteTipo;

import org.springframework.data.jpa.repository.JpaRepository;

public interface IAntecedenteDAO extends JpaRepository<Antecedente, Long> {

	List<Antecedente> findByNombreStartingWithAllIgnoreCase(String valor);

	Antecedente findByNombre(String value);

	List<Antecedente> findByAntecedenteTipo(AntecedenteTipo antecedenteTipo);

	List<Antecedente> findByAntecedenteTipoNombreStartingWithAllIgnoreCase(
			String valor);

	List<Antecedente> findByAntecedenteTipoTipoOrderByAntecedenteTipoNombreAsc(
			String string);

}
