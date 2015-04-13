package interfaceDAO.medico.historia;

import java.util.List;

import modelo.medico.historia.Historia;
import modelo.medico.historia.HistoriaIntervencion;
import modelo.medico.maestro.Intervencion;
import modelo.pk.HistoriaIntervencionId;

import org.springframework.data.jpa.repository.JpaRepository;

public interface IHistoriaIntervencionDAO extends JpaRepository<HistoriaIntervencion, HistoriaIntervencionId> {

	List<HistoriaIntervencion> findByHistoria(Historia historia);

	List<HistoriaIntervencion> findByIntervencion(Intervencion intervencion);

}
