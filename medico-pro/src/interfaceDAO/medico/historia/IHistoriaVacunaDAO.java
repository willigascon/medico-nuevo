package interfaceDAO.medico.historia;

import java.util.List;

import modelo.medico.historia.Historia;
import modelo.medico.historia.HistoriaVacuna;
import modelo.medico.maestro.Vacuna;
import modelo.pk.HistoriaVacunaId;

import org.springframework.data.jpa.repository.JpaRepository;

public interface IHistoriaVacunaDAO extends JpaRepository<HistoriaVacuna, HistoriaVacunaId> {

	List<HistoriaVacuna> findByHistoria(Historia historia);

	List<HistoriaVacuna> findByVacuna(Vacuna vacuna);

}
