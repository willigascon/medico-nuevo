package servicio.seguridad;

import interfaceDAO.medico.historia.IHistoriaAccidenteDAO;
import interfaceDAO.seguridad.IAccidenteDAO;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import modelo.medico.historia.Historia;
import modelo.medico.historia.HistoriaAccidente;
import modelo.seguridad.Accidente;
import modelo.seguridad.ClasificacionAccidente;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("SAccidente")
public class SAccidente {

	@Autowired
	private IAccidenteDAO accidenteDAO;
	@Autowired
	private IHistoriaAccidenteDAO accidenteHistoriaDAO;

	public Accidente buscar(long parseLong) {
		return accidenteDAO.findOne(parseLong);
	}

	public List<Accidente> buscarDisponibles(Historia historia, String string) {
		List<HistoriaAccidente> accidentesHistoricos = accidenteHistoriaDAO
				.findByHistoriaAndTipoAccidente(historia, string);
		List<Long> ids = new ArrayList<Long>();
		if (accidentesHistoricos.isEmpty())
			return accidenteDAO.findAllOrderByCodigoAsc();
		else {
			for (int i = 0; i < accidentesHistoricos.size(); i++) {
				ids.add(accidentesHistoricos.get(i).getAccidente()
						.getIdAccidente());
			}
			return accidenteDAO.findByIdAccidenteNotIn(ids);
		}
	}

	public List<Accidente> filtroNombre(String valor) {
		return accidenteDAO.findByNombreStartingWithAllIgnoreCase(valor);
	}

	public List<Accidente> buscarTodos() {
		return accidenteDAO.findAllOrderByCodigoAsc();
	}

	public List<Accidente> filtroCodigo(String valor) {
		return accidenteDAO.findByIdAccidenteStartingWithAllIgnoreCase(valor);
	}

	public List<Accidente> filtroClasificacion(String valor) {
		return accidenteDAO
				.findByClasificacionNombreStartingWithAllIgnoreCase(valor);
	}

	public void guardar(Accidente accidente) {
		accidenteDAO.saveAndFlush(accidente);
	}

	public void eliminar(Accidente accidente) {
		accidenteDAO.delete(accidente);
	}

	public Accidente buscarUltimo() {
		long id = accidenteDAO.findMaxIdDiagnostico();
		if (id != 0)
			return accidenteDAO.findOne(id);
		return null;
	}

	public List<Accidente> buscarPorClasificacion(
			ClasificacionAccidente clasificacionAccidente) {
		// TODO Auto-generated method stub
		return accidenteDAO.findByClasificacion(clasificacionAccidente);
	}

	public List<Accidente> buscarPorIdClasificacion(Long valueOf) {
		return accidenteDAO
				.findByClasificacionIdClasificacionAccidente(valueOf);
	}
}
