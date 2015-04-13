package servicio.medico.maestro;

import interfaceDAO.medico.maestro.ICitaDAO;

import java.util.Date;
import java.util.List;

import modelo.medico.maestro.Cita;
import modelo.medico.maestro.DoctorInterno;
import modelo.medico.maestro.MotivoCita;
import modelo.medico.maestro.Paciente;
import modelo.security.Usuario;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("SCita")
public class SCita {

	@Autowired
	private ICitaDAO citaDAO;

	public List<Cita> buscarPorMotivo(MotivoCita motivoCita) {
		return citaDAO.findByMotivoCita(motivoCita);
	}

	public List<Cita> buscarPorPaciente(Paciente paciente) {
		return citaDAO.findByPaciente(paciente);
	}

	public void guardar(Cita cita) {
		citaDAO.save(cita);

	}

	public List<Cita> buscarPorUsuarioYEstado(DoctorInterno usuario, String estado) {
		return citaDAO.findByDoctorInternoAndEstado(usuario, estado);
	}

	public List<Cita> filtroPaciente(String valor) {
		return citaDAO
				.findByPacientePrimerNombreStartingWithAllIgnoreCase(valor);
	}

	public List<Cita> filtroEmpresa(String valor) {
		return citaDAO
				.findByPacienteEmpresaNombreStartingWithAllIgnoreCase(valor);
	}

	public List<Cita> filtroFecha(String valor) {
		return citaDAO.findByFechaCitaStartingWithAllIgnoreCase(valor);
	}

	public List<Cita> filtroMotivo(String valor) {
		return citaDAO
				.findByMotivoCitaDescripcionStartingWithAllIgnoreCase(valor);
	}

	public List<Cita> buscarPorDoctor(DoctorInterno usuario) {
		return citaDAO.findByDoctorInterno(usuario);
	}

	public int buscarPorUsuarioYFechaYEstado(DoctorInterno usuario, Date value,
			String string) {
		List<Cita> citas = citaDAO.findByDoctorInternoAndEstadoAndFechaCita(usuario,
				string, value);
		if (!citas.isEmpty())
			return citas.size();
		else
			return 0;
	}

	public Cita buscar(long idCita) {
		Cita cita = citaDAO.findOne(idCita);
		return cita;
	}

	public void eliminarCitas(List<Cita> citas) {
		citaDAO.delete(citas);
	}

	public void guardarVarias(List<Cita> citasNuevas) {
		citaDAO.save(citasNuevas);
	}
}
