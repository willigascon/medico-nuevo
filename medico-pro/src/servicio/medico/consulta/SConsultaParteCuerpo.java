package servicio.medico.consulta;

import interfaceDAO.medico.consulta.IConsultaParteCuerpoDAO;

import java.util.List;

import modelo.medico.consulta.Consulta;
import modelo.medico.consulta.ConsultaParteCuerpo;
import modelo.medico.maestro.ParteCuerpo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("SConsultaParteCuerpo")
public class SConsultaParteCuerpo {

	@Autowired
	private IConsultaParteCuerpoDAO consultaParteDAO;

	public List<ConsultaParteCuerpo> buscarPorConsulta(Consulta consulta) {
		return consultaParteDAO.findByConsulta(consulta);
	}

	public void borrarExamenAnterior(Consulta consultaDatos) {
		List<ConsultaParteCuerpo> borrados = consultaParteDAO.findByConsulta(consultaDatos);
		if(!borrados.isEmpty())
			consultaParteDAO.delete(borrados);
	}

	public void guardar(List<ConsultaParteCuerpo> examenFisico) {
		consultaParteDAO.save(examenFisico);
	}

	public List<ConsultaParteCuerpo> buscarPorParte(ParteCuerpo parte) {
		return consultaParteDAO.findByParte(parte);
	}
}
