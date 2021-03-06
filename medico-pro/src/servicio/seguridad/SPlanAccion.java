package servicio.seguridad;

import interfaceDAO.seguridad.IPlanAccionDAO;

import java.util.List;

import modelo.seguridad.Informe;
import modelo.seguridad.PlanAccion;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("SPlanAccion")
public class SPlanAccion {

	@Autowired
	private IPlanAccionDAO planDAO;

	public void guardar(PlanAccion plan) {
		planDAO.save(plan);
	}

	public void eliminar(long id) {
		planDAO.delete(id);
	}

	public List<PlanAccion> buscarTodos() {
		return planDAO.findAll();
	}

	public List<PlanAccion> filtroDescripcion(String valor) {
		return planDAO.findByDescripcionStartingWithAllIgnoreCase(valor);
	}

	public List<PlanAccion> filtroQuien(String valor) {
		return planDAO.findByQuienStartingWithAllIgnoreCase(valor);
	}

	public List<PlanAccion> filtroDonde(String valor) {
		return planDAO.findByDondeStartingWithAllIgnoreCase(valor);
	}

	public List<PlanAccion> filtroCuando(String valor) {
		return planDAO.findByCuandoStartingWithAllIgnoreCase(valor);
	}

	public List<PlanAccion> filtroComo(String valor) {
		return planDAO.findByComoStartingWithAllIgnoreCase(valor);
	}

	public PlanAccion buscarPorNombre(String value) {
		List<PlanAccion> lista = planDAO.findByDescripcion(value);
		if (lista.isEmpty())
			return null;
		else
			return lista.get(0);
	}

	public PlanAccion buscar(Long idPlan) {
		return planDAO.findOne(idPlan);
	}

	public void guardarVarios(List<PlanAccion> planesAccion) {
		planDAO.save(planesAccion);
	}

	public List<PlanAccion> buscarPorInforme(Informe informe) {
		return planDAO.findByInforme(informe);
	}

	public void eliminarVarios(List<PlanAccion> planesAccion) {
		planDAO.delete(planesAccion);
	}

	public List<PlanAccion> buscarPorInformeYEstado(Informe informe,
			String string) {
		return planDAO.findByInformeAndEstado(informe, string);
	}
	
	public Boolean buscarPorActivos(Informe informe) {
		if (planDAO.findByInforme(informe).isEmpty()) {
			return false;
		} else {
			if (planDAO.findByInformeAndEstado(informe, "Programado").isEmpty())
				return true;
			else
				return false;
		}
	}

	public List<PlanAccion> buscarPorInformeyTipo(Informe informe, String string) {
		// TODO Auto-generated method stub
		return  planDAO.findByInformeAndTipo(informe, string);
	}

	public List<PlanAccion> buscarPorInformeEstadoyTipo(Informe informe,
			String string, String string2) {
		return planDAO.findByInformeAndEstadoAndTipo(informe, string,string2);
	}

}
