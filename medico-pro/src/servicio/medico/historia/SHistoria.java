package servicio.medico.historia;

import interfaceDAO.medico.historia.IHistoriaDAO;
import modelo.medico.historia.Historia;
import modelo.medico.maestro.Paciente;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("SHistoria")
public class SHistoria {

	@Autowired
	private IHistoriaDAO historiaDAO;

	public Historia buscarPorPaciente(Paciente paciente) {
		return historiaDAO.findByPaciente(paciente);
	}

	public void guardar(Historia historia) {
		historiaDAO.save(historia);
	}

	public Historia buscar(long idHistoria) {
		return historiaDAO.findOne(idHistoria);
	}

	public Historia buscarUltima() {
		long id = historiaDAO.findMaxIdHistoria();
		if (id != 0)
			return historiaDAO.findOne(id);
		return null;
	}
}
