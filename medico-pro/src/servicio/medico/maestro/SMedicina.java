package servicio.medico.maestro;

import interfaceDAO.medico.consulta.IConsultaMedicinaDAO;
import interfaceDAO.medico.maestro.IMedicinaDAO;

import java.util.ArrayList;
import java.util.List;

import modelo.medico.consulta.Consulta;
import modelo.medico.consulta.ConsultaMedicina;
import modelo.medico.maestro.CategoriaMedicina;
import modelo.medico.maestro.Laboratorio;
import modelo.medico.maestro.Medicina;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("SMedicina")
public class SMedicina {

	@Autowired
	private IMedicinaDAO medicinaDAO;
	@Autowired
	private IConsultaMedicinaDAO consultaMedicinaDAO;

	public Medicina buscar(long idMedicina) {

		return medicinaDAO.findOne(idMedicina);
	}

	public List<Medicina> buscarTodas() {
		return medicinaDAO.findAll();
	}

	public void guardar(Medicina medicina) {
		medicinaDAO.save(medicina);
	}

	public Medicina buscarPorNombre(String value) {
		List<Medicina> objetos = medicinaDAO.findByNombre(value);
		if (!objetos.isEmpty())
			return objetos.get(0);
		else
			return null;
	}

	public List<Medicina> buscarPorLaboratorio(Laboratorio laboratorio) {
		return medicinaDAO.findByLaboratorio(laboratorio);
	}

	public void eliminar(Medicina medicina) {
		medicinaDAO.delete(medicina);
	}

	public List<Medicina> filtroNombre(String valor) {
		return medicinaDAO.findByNombreStartingWithAllIgnoreCase(valor);
	}

	public List<Medicina> filtroLaboratorio(String valor) {
		return medicinaDAO
				.findByLaboratorioNombreStartingWithAllIgnoreCase(valor);
	}

	public List<Medicina> filtroPosologia(String valor) {
		return medicinaDAO.findByPosologiaStartingWithAllIgnoreCase(valor);
	}

	public List<Medicina> buscarPorCategoria(CategoriaMedicina categoriaMedicina) {
		return medicinaDAO.findByCategoriaMedicina(categoriaMedicina);
	}

	public Medicina buscarUltima() {
		long id = medicinaDAO.findMaxIdMedicina();
		if (id != 0)
			return medicinaDAO.findOne(id);
		else
			return null;
	}

	public List<Medicina> buscarDisponibles(Consulta consulta) {
		List<ConsultaMedicina> consultasMedicina = consultaMedicinaDAO
				.findByConsulta(consulta);
		List<Long> ids = new ArrayList<Long>();
		if (consultasMedicina.isEmpty())
			return medicinaDAO.findAll();
		else {
			for (int i = 0; i < consultasMedicina.size(); i++) {
				ids.add(consultasMedicina.get(i).getMedicina().getIdMedicina());
			}
			return medicinaDAO.findByIdMedicinaNotIn(ids);
		}
	}

	public void guardarVarios(List<Medicina> medicinas) {
		medicinaDAO.save(medicinas);
	}

	public Medicina buscarPorReferencia(long idRefD) {
		return medicinaDAO.findByIdReferencia(idRefD);
	}

	public List<Medicina> filtroDenominacion(String valor) {
		return medicinaDAO
				.findByDenominacionGenericaStartingWithAllIgnoreCase(valor);
	}

	public long buscarUltimaReferencia() {
		long id = medicinaDAO.findMaxIdReferencia();
		return id;
	}
}
