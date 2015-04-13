package servicio.medico.maestro;

import java.util.ArrayList;
import java.util.List;

import interfaceDAO.medico.maestro.IDoctorInternoDAO;
import modelo.medico.maestro.DoctorInterno;
import modelo.medico.maestro.Especialidad;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service("SDoctorInterno")
public class SDoctorInterno {

	@Autowired
	private IDoctorInternoDAO doctorDAO;

	public void guardar(DoctorInterno doctor) {
		doctorDAO.saveAndFlush(doctor);
	}

	public DoctorInterno buscarPorCedula(String id) {
		return doctorDAO.findOne(id);
	}

	public void eliminar(DoctorInterno usuario) {
		doctorDAO.delete(usuario);
	}

	public List<DoctorInterno> buscarTodos() {
		List<String> ordenar = new ArrayList<String>();
		ordenar.add("cedula");
		ordenar.add("ficha");
		ordenar.add("primerNombre");
		ordenar.add("primerApellido");
		Sort o = new Sort(Sort.Direction.ASC, ordenar);
		return doctorDAO.findAll(o);
	}

	public List<DoctorInterno> filtroCedula(String valor) {
		return doctorDAO.findByCedulaStartingWithAllIgnoreCase(valor);
	}

	public List<DoctorInterno> filtroFicha(String valor) {
		return doctorDAO.findByFichaStartingWithAllIgnoreCase(valor);
	}

	public List<DoctorInterno> filtroNombre(String valor) {
		return doctorDAO.findByPrimerNombreStartingWithAllIgnoreCase(valor);
	}

	public List<DoctorInterno> filtroApellido(String valor) {
		return doctorDAO.findByPrimerApellidoStartingWithAllIgnoreCase(valor);
	}

	public List<DoctorInterno> buscarPorEspecialidad(Especialidad especialidad) {
		return doctorDAO.findByEspecialidad(especialidad);
	}
}
