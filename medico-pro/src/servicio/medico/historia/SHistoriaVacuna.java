package servicio.medico.historia;

import interfaceDAO.medico.historia.IHistoriaVacunaDAO;

import java.util.List;

import modelo.medico.historia.Historia;
import modelo.medico.historia.HistoriaVacuna;
import modelo.medico.maestro.Vacuna;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("SHistoriaVacuna")
public class SHistoriaVacuna {

	@Autowired
	private IHistoriaVacunaDAO historiaVacunaDAO;

	public List<HistoriaVacuna> buscarPorHistoria(Historia historia) {
		return historiaVacunaDAO.findByHistoria(historia);
	}

	public void limpiar(Historia historia) {
		List<HistoriaVacuna> borrados = historiaVacunaDAO.findByHistoria(historia);
		if(!borrados.isEmpty())
			historiaVacunaDAO.delete(borrados);
	}

	public void guardar(List<HistoriaVacuna> vacunas) {
		historiaVacunaDAO.save(vacunas);
	}

	public List<HistoriaVacuna> buscarPorVacuna(Vacuna vacuna) {
		return historiaVacunaDAO.findByVacuna(vacuna);
	}
}
