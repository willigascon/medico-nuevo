package servicio.medico.maestro;

import interfaceDAO.medico.maestro.ICitaDAO;

import java.sql.Timestamp;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import modelo.medico.maestro.Cita;
import modelo.medico.maestro.DoctorInterno;
import modelo.medico.maestro.MotivoCita;
import modelo.medico.maestro.Paciente;
import modelo.security.Usuario;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
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

	public List<Cita> buscarPorUsuarioYEstado(DoctorInterno usuario,
			String estado) {
		return citaDAO.findByDoctorInternoAndEstado(usuario, estado);
	}

	public List<Cita> filtroPaciente(String valor, String idDoctor) {
		return citaDAO
				.findByDoctorInternoCedulaAndPacientePrimerNombreStartingWithAllIgnoreCase(
						idDoctor, valor);
	}

	public List<Cita> filtroEmpresa(String valor, String idDoctor) {
		return citaDAO
				.findByDoctorInternoCedulaAndPacienteEmpresaNombreStartingWithAllIgnoreCase(
						idDoctor, valor);
	}

	public List<Cita> filtroMotivo(String valor, String idDoctor) {
		return citaDAO
				.findByDoctorInternoCedulaAndEstadoAndMotivoCitaDescripcionStartingWithAllIgnoreCase(
						idDoctor, "Pendiente", valor);
	}

	public List<Cita> buscarPorDoctor(DoctorInterno usuario) {
		return citaDAO.findByDoctorInterno(usuario);
	}

	public int buscarPorUsuarioYFechaYEstado(DoctorInterno usuario, Date value,
			String string) {
		List<Cita> citas = citaDAO.findByDoctorInternoAndEstadoAndFechaCita(
				usuario, string, value);
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

	public List<Cita> buscarPorUsuarioYEstadoYFecha(DoctorInterno usuario,
			String estado, Date value) {
		Calendar calendario = Calendar.getInstance();
		calendario.setTime(value);
		calendario.set(Calendar.HOUR, 0);
		calendario.set(Calendar.HOUR_OF_DAY, 0);
		calendario.set(Calendar.SECOND, 0);
		calendario.set(Calendar.MILLISECOND, 0);
		calendario.set(Calendar.MINUTE, 0);
		value = calendario.getTime();
		Timestamp fecha = new Timestamp(value.getTime());
		calendario.set(Calendar.HOUR, 11);
		calendario.set(Calendar.HOUR_OF_DAY, 23);
		calendario.set(Calendar.SECOND, 59);
		calendario.set(Calendar.MILLISECOND, 0);
		calendario.set(Calendar.MINUTE, 59);
		value = calendario.getTime();
		Timestamp fecha2 = new Timestamp(value.getTime());
		List<String> ordenar = new ArrayList<String>();
		ordenar.add("estado");
		ordenar.add("pacienteCedula");
		Sort o = new Sort(Sort.Direction.ASC, ordenar);
		return citaDAO.findByDoctorInternoAndEstadoLikeAndFechaCitaBetween(
				usuario, estado, fecha, fecha2, o);
	}

	public List<Cita> filtroFecha(Timestamp fecha, Date date, String idDoctor) {
		Calendar calendario = Calendar.getInstance();
		calendario.setTime(date);
		calendario.set(Calendar.HOUR, 11);
		calendario.set(Calendar.HOUR_OF_DAY, 23);
		calendario.set(Calendar.SECOND, 59);
		calendario.set(Calendar.MILLISECOND, 0);
		calendario.set(Calendar.MINUTE, 59);
		date = calendario.getTime();
		return citaDAO
				.findByDoctorInternoCedulaAndFechaCitaBetweenOrderByFechaCitaAsc(
						idDoctor, fecha, new Timestamp(date.getTime()));
	}
}
