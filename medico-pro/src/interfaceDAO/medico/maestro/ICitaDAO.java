package interfaceDAO.medico.maestro;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import modelo.medico.maestro.Cita;
import modelo.medico.maestro.DoctorInterno;
import modelo.medico.maestro.MotivoCita;
import modelo.medico.maestro.Paciente;
import modelo.security.Usuario;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ICitaDAO extends JpaRepository<Cita, Long> {

	List<Cita> findByMotivoCita(MotivoCita motivoCita);

	List<Cita> findByPaciente(Paciente paciente);

	List<Cita> findByDoctorInternoAndEstado(DoctorInterno usuario, String string);

	List<Cita> findByPacientePrimerNombreStartingWithAllIgnoreCase(String valor);

	List<Cita> findByPacienteEmpresaNombreStartingWithAllIgnoreCase(String valor);

	List<Cita> findByMotivoCitaDescripcionStartingWithAllIgnoreCase(String valor);

	List<Cita> findByDoctorInternoAndEstadoAndFechaCita(DoctorInterno usuario,
			String string, Date value);

	List<Cita> findByDoctorInternoAndEstadoAndFechaCitaAndPacienteCedulaAndPacienteEstatusTrue(
			DoctorInterno usuario, String string, Date date, String value);

	List<Cita> findByDoctorInternoAndPacienteFichaStartingWithAndPacienteEstatusTrueAndFechaCitaAllIgnoreCase(
			DoctorInterno usuario, String valor, Timestamp fecha);

	List<Cita> findByDoctorInternoAndPacientePrimerNombreStartingWithAndPacienteEstatusTrueAndFechaCitaAllIgnoreCase(
			DoctorInterno usuario, String valor, Timestamp fecha);

	List<Cita> findByDoctorInternoAndPacienteCedulaStartingWithAndPacienteEstatusTrueAndFechaCitaAllIgnoreCase(
			DoctorInterno usuario, String valor, Timestamp fecha);

	List<Cita> findByDoctorInternoAndPacientePrimerApellidoStartingWithAndPacienteEstatusTrueAndFechaCitaAllIgnoreCase(
			DoctorInterno usuario, String valor, Timestamp fecha);

	List<Cita> findByDoctorInternoAndPacienteFichaStartingWithAndPacienteEstatusTrueAndFechaCitaAndEstadoAllIgnoreCase(
			DoctorInterno usuario, String valor, Timestamp fecha, String string);

	List<Cita> findByDoctorInternoAndPacientePrimerNombreStartingWithAndPacienteEstatusTrueAndFechaCitaAndEstadoAllIgnoreCase(
			DoctorInterno usuario, String valor, Timestamp fecha, String string);

	List<Cita> findByDoctorInternoAndPacienteCedulaStartingWithAndPacienteEstatusTrueAndFechaCitaAndEstadoAllIgnoreCase(
			DoctorInterno usuario, String valor, Timestamp fecha, String string);

	List<Cita> findByDoctorInternoAndPacientePrimerApellidoStartingWithAndPacienteEstatusTrueAndFechaCitaAndEstadoAllIgnoreCase(
			DoctorInterno usuario, String valor, Timestamp fecha, String string);

	List<Cita> findByDoctorInternoAndPacienteCedulaFamiliarStartingWithAndPacienteEstatusTrueAndFechaCitaAndEstadoAllIgnoreCase(
			DoctorInterno usuario, String valor, Timestamp fecha, String string);

	List<Cita> findByDoctorInternoAndPacienteSegundoNombreStartingWithAndPacienteEstatusTrueAndFechaCitaAndEstadoAllIgnoreCase(
			DoctorInterno usuario, String valor, Timestamp fecha, String string);

	List<Cita> findByDoctorInternoAndPacienteSegundoApellidoStartingWithAndPacienteEstatusTrueAndFechaCitaAndEstadoAllIgnoreCase(
			DoctorInterno usuario, String valor, Timestamp fecha, String string);

	List<Cita> findByDoctorInterno(DoctorInterno usuario);

//	List<Cita> findByDoctorInternoAndEstadoLikeAndFechaCita(
//			DoctorInterno usuario, String estado, Timestamp fecha, Sort o);

	List<Cita> findByDoctorInternoCedulaAndPacientePrimerNombreStartingWithAllIgnoreCase(
			String idDoctor, String valor);

	List<Cita> findByDoctorInternoCedulaAndPacienteEmpresaNombreStartingWithAllIgnoreCase(
			String idDoctor, String valor);

	List<Cita> findByDoctorInternoCedulaAndMotivoCitaDescripcionStartingWithAllIgnoreCase(
			String idDoctor, String valor);

	List<Cita> findByDoctorInternoCedulaAndFechaCita(String idDoctor,
			Timestamp fecha);

	List<Cita> findByDoctorInternoCedulaAndEstadoAndMotivoCitaDescripcionStartingWithAllIgnoreCase(
			String idDoctor, String string, String valor);

	List<Cita> findByDoctorInternoCedulaAndFechaCitaBetweenOrderByFechaCitaAsc(String idDoctor,
			Timestamp fecha, Timestamp timestamp);

	List<Cita> findByDoctorInternoAndEstadoLikeAndFechaCitaBetween(
			DoctorInterno usuario, String estado, Timestamp fecha,
			Timestamp fecha2, Sort o);

}
