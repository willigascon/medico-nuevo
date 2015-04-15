package servicio.medico.consulta;

import interfaceDAO.medico.consulta.IConsultaMedicinaDAO;

import java.util.List;

import modelo.medico.consulta.Consulta;
import modelo.medico.consulta.ConsultaMedicina;
import modelo.medico.maestro.Medicina;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("SConsultaMedicina")
public class SConsultaMedicina {

	@Autowired
	private IConsultaMedicinaDAO consultaMedicinaDAO;

	public List<ConsultaMedicina> buscarPorConsulta(Consulta consulta) {
		return consultaMedicinaDAO.findByConsulta(consulta);
	}

	public void borrarMedicinasDeConsulta(Consulta consulta) {
		List<ConsultaMedicina> lista = consultaMedicinaDAO.findByConsulta(consulta);
		if(!lista.isEmpty()){
			consultaMedicinaDAO.delete(lista);
		}
	}

	public void guardar(List<ConsultaMedicina> listaMedicina) {
		consultaMedicinaDAO.save(listaMedicina);
	}

	public List<ConsultaMedicina> buscarPorMedicina(Medicina medicina) {
		// TODO Auto-generated method stub
		return consultaMedicinaDAO.findByMedicina(medicina);
	}

	public Double costoPorConsulta(Consulta consulta) {
		return consultaMedicinaDAO.costByConsulta(consulta);
	}
}
