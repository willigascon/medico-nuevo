package interfaceDAO.medico.historia;

import java.util.List;

import modelo.medico.historia.Historia;
import modelo.medico.historia.HistoriaAccidente;
import modelo.pk.HistoriaAccidenteId;
import modelo.seguridad.Accidente;

import org.springframework.data.jpa.repository.JpaRepository;

public interface IHistoriaAccidenteDAO extends JpaRepository<HistoriaAccidente, HistoriaAccidenteId> {

//	List<HistoriaAccidente> findByHistoriaAndAccidenteTipo(Historia historia,
//			String string);

	List<HistoriaAccidente> findByHistoria(Historia historia);

	List<HistoriaAccidente> findByHistoriaAndTipoAccidente(Historia historia,
			String string);

	List<HistoriaAccidente> findByAccidente(Accidente accidente);

}
