package servicio.medico.maestro;

import interfaceDAO.medico.consulta.IConsultaServicioExternoDAO;
import interfaceDAO.medico.maestro.IServicioExternoDAO;

import java.util.ArrayList;
import java.util.List;

import modelo.medico.consulta.Consulta;
import modelo.medico.consulta.ConsultaServicioExterno;
import modelo.medico.maestro.ServicioExterno;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("SServicioExterno")
public class SServicioExterno {

	@Autowired
	private IServicioExternoDAO servicioExternoDAO;
	@Autowired
	private IConsultaServicioExternoDAO consultaServicioExternoDAO;

	public void guardar(ServicioExterno servicioExterno) {
		servicioExternoDAO.save(servicioExterno);
	}

	public ServicioExterno buscar(long id) {
		return servicioExternoDAO.findOne(id);
	}

	public void eliminar(ServicioExterno servicioExterno) {
		servicioExternoDAO.delete(servicioExterno);
	}

	public ServicioExterno buscarPorNombre(String value) {
		return servicioExternoDAO.findByNombre(value);
	}

	public List<ServicioExterno> buscarTodas() {
		return servicioExternoDAO.findAll();
	}

	public List<ServicioExterno> filtroNombre(String valor) {
		return servicioExternoDAO.findByNombreStartingWithAllIgnoreCase(valor);
	}

	public List<ServicioExterno> buscarDisponibles(Consulta consulta) {
		List<ConsultaServicioExterno> consultasServicios = consultaServicioExternoDAO
				.findByConsulta(consulta);
		List<Long> ids = new ArrayList<Long>();
		if (consultasServicios.isEmpty())
			return servicioExternoDAO.findAll();
		else {
			for (int i = 0; i < consultasServicios.size(); i++) {
				ids.add(consultasServicios.get(i).getServicioExterno()
						.getIdServicioExterno());
			}
			return servicioExternoDAO.findByIdServicioExternoNotIn(ids);
		}
	}

	public List<ServicioExterno> buscarEstudiosDisponibles(List<Long> ids) {
		return servicioExternoDAO.findByIdServicioExternoNotIn(ids);
	}

	public ServicioExterno buscarUltimo() {
		long id = servicioExternoDAO.findMaxIdServicio();
		if (id != 0)
			return servicioExternoDAO.findOne(id);
		return null;
	}
}
