package servicio.medico.maestro;

import interfaceDAO.medico.maestro.ICitaDAO;
import interfaceDAO.medico.maestro.IPacienteDAO;
import interfaceDAO.medico.maestro.IPeriodoPacienteDAO;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import modelo.medico.maestro.Cita;
import modelo.medico.maestro.DoctorInterno;
import modelo.medico.maestro.EstadoCivil;
import modelo.medico.maestro.Paciente;
import modelo.medico.maestro.Periodo;
import modelo.medico.maestro.PeriodoPaciente;
import modelo.organizacion.Area;
import modelo.organizacion.Cargo;
import modelo.organizacion.Ciudad;
import modelo.organizacion.Empresa;
import modelo.security.Usuario;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service("SPaciente")
public class SPaciente {

	@Autowired
	private IPacienteDAO pacienteDAO;
	@Autowired
	private ICitaDAO citaDAO;
	@Autowired
	private IPeriodoPacienteDAO periodoPacienteDAO;

	public List<Paciente> buscarTodos() {
		Pageable topTen = new PageRequest(0, 10);
		return pacienteDAO.findAllOrderByCedula(topTen);
	}

	public List<Paciente> filtroNombre1(String valor) {
		return pacienteDAO.findByPrimerNombreStartingWithAllIgnoreCase(valor);
	}

	public List<Paciente> filtroCedula(String valor) {
		return pacienteDAO.findByCedulaStartingWithAllIgnoreCase(valor);
	}

	public List<Paciente> filtroApellido1(String valor) {
		return pacienteDAO.findByPrimerApellidoStartingWithAllIgnoreCase(valor);
	}

	public List<Paciente> filtroEmpresa(String valor) {
		return pacienteDAO.findByEmpresaNombreStartingWithAllIgnoreCase(valor);
	}

	public Paciente buscarPorCedula(String value) {
		return pacienteDAO.findByCedula(value);
	}

	public void guardar(Paciente paciente) {
		pacienteDAO.save(paciente);
	}

	public void eliminar(Paciente paciente) {
		pacienteDAO.delete(paciente);

	}

	public List<Paciente> buscarPorEmpresa(Empresa empresa) {
		return pacienteDAO.findByEmpresa(empresa);
	}

	public List<Paciente> buscarParientes(String valueOf) {
		return pacienteDAO.findByCedulaFamiliar(valueOf);
	}

	public List<Paciente> buscarPorCargo(Cargo cargo) {
		return pacienteDAO.findByCargoReal(cargo);
	}

	public List<Paciente> buscarPorCiudad(Ciudad ciudad) {
		// TODO Auto-generated method stub
		return pacienteDAO.findByCiudadVivienda(ciudad);
	}

	public List<Paciente> buscarPorArea(Area area) {
		return pacienteDAO.findByArea(area);
	}

	public List<Paciente> buscarPorFicha(String value) {

		return pacienteDAO.findByFicha(value);
	}

	public List<Paciente> filtroFicha(String valor) {
		return pacienteDAO.findByFichaStartingWithAllIgnoreCase(valor);
	}

	public List<Paciente> buscarTodosActivos() {
		Pageable topTen = new PageRequest(0, 10);
		return pacienteDAO.findByEstatusTrue(topTen);
	}

	public void guardarVarios(List<Paciente> inactivos) {
		pacienteDAO.save(inactivos);
	}

	public List<Paciente> filtroParentesco(String valor) {
		return pacienteDAO
				.findByParentescoFamiliarStartingWithAllIgnoreCase(valor);
	}

	public List<Paciente> buscarTodosTrabajadores() {
		Pageable topTen = new PageRequest(0, 10);
		return pacienteDAO.findByTrabajadorTrueAndEstatusTrue(topTen);
	}

	public List<Paciente> filtroNombre1C(String valor, String value) {
		return pacienteDAO
				.findByCedulaFamiliarAndPrimerNombreStartingWithAllIgnoreCase(
						value, valor);
	}

	public List<Paciente> filtroCedulaC(String valor, String value) {
		return pacienteDAO
				.findByCedulaFamiliarAndCedulaStartingWithAllIgnoreCase(value,
						valor);
	}

	public List<Paciente> filtroApellido1C(String valor, String value) {
		return pacienteDAO
				.findByCedulaFamiliarAndPrimerApellidoStartingWithAllIgnoreCase(
						value, valor);
	}

	public List<Paciente> filtroCedulaT(String valor) {
		return pacienteDAO
				.findByTrabajadorTrueAndCedulaStartingWithAllIgnoreCase(valor);
	}

	public List<Paciente> filtroNombre1T(String valor) {
		return pacienteDAO
				.findByTrabajadorTrueAndPrimerNombreStartingWithAllIgnoreCase(valor);
	}

	public List<Paciente> filtroApellido1T(String valor) {
		return pacienteDAO
				.findByTrabajadorTrueAndPrimerApellidoStartingWithAllIgnoreCase(valor);
	}

	public List<Paciente> filtroParentescoC(String valor, String valor2) {
		return pacienteDAO
				.findByCedulaFamiliarAndParentescoFamiliarStartingWithAllIgnoreCase(
						valor2, valor);
	}

	public List<Paciente> filtroNombrePariente(String valor) {
		return pacienteDAO
				.findByTrabajadorFalseAndPrimerNombreStartingWithAllIgnoreCase(valor);
	}

	public List<Paciente> filtroCedulaPariente(String valor) {
		return pacienteDAO
				.findByTrabajadorFalseAndCedulaStartingWithAllIgnoreCase(valor);
	}

	public List<Paciente> filtroApellidoPariente(String valor) {
		return pacienteDAO
				.findByTrabajadorFalseAndPrimerApellidoStartingWithAllIgnoreCase(valor);
	}

	public List<Paciente> filtroNombre1Activos(String valor) {
		return pacienteDAO
				.findByPrimerNombreStartingWithAndEstatusTrueAllIgnoreCase(valor);
	}

	public List<Paciente> filtroCedulaActivos(String valor) {
		return pacienteDAO
				.findByCedulaStartingWithAndEstatusTrueAllIgnoreCase(valor);
	}

	public List<Paciente> filtroApellido1Activos(String valor) {
		return pacienteDAO
				.findByPrimerApellidoStartingWithAndEstatusTrueAllIgnoreCase(valor);
	}

	public List<Paciente> filtroNombreParienteActivos(String valor) {
		return pacienteDAO
				.findByTrabajadorFalseAndEstatusTrueAndPrimerNombreStartingWithAllIgnoreCase(valor);
	}

	public List<Paciente> filtroCedulaParienteActivos(String valor) {
		return pacienteDAO
				.findByTrabajadorFalseAndEstatusTrueAndCedulaStartingWithAllIgnoreCase(valor);
	}

	public List<Paciente> filtroApellidoParienteActivos(String valor) {
		return pacienteDAO
				.findByTrabajadorFalseAndEstatusTrueAndPrimerApellidoStartingWithAllIgnoreCase(valor);
	}

	public List<Paciente> filtroFichaActivos(String valor) {
		return pacienteDAO
				.findByFichaStartingWithAndEstatusTrueAllIgnoreCase(valor);
	}

	public List<Paciente> filtroFichaParienteActivos(String valor) {
		return pacienteDAO
				.findByTrabajadorFalseAndEstatusTrueAndFichaStartingWithAllIgnoreCase(valor);
	}

	public Paciente buscarPorCedulaActivo(String value) {
		return pacienteDAO.findByCedulaAndEstatusTrue(value);
	}

	public List<Paciente> filtroFichaT(String valor) {
		return pacienteDAO
				.findByTrabajadorTrueAndFichaStartingWithAllIgnoreCase(valor);
	}

	public List<Paciente> buscarFamiliaresActivos() {
		Pageable topTen = new PageRequest(0, 10);
		return pacienteDAO
				.findByTrabajadorFalseAndEstatusTrueOrderByCedulaAsc(topTen);
	}

	public Paciente buscarPorCedulaFamiliarActivo(String value) {
		return pacienteDAO.findByCedulaAndEstatusTrueAndTrabajadorFalse(value);
	}

	public List<Paciente> buscarPorEdadesTrabajador(int dea, int aa, boolean b) {
		List<String> ordenar = new ArrayList<String>();
		ordenar.add("cedulaFamiliar");
		ordenar.add("cedula");
		Sort o = new Sort(Sort.Direction.ASC, ordenar);
		return pacienteDAO.findByEdadBetweenAndTrabajadorAndEstatusTrue(dea,
				aa, b, o);

	}

	public List<Paciente> buscarPorEdadesParentescoySexo(int dea, int aa,
			String parentesco, String sexo) {
		List<String> ordenar = new ArrayList<String>();
		ordenar.add("cedulaFamiliar");
		ordenar.add("cedula");
		Sort o = new Sort(Sort.Direction.ASC, ordenar);
		return pacienteDAO
				.findByEdadBetweenAndTrabajadorFalseAndSexoAndParentescoFamiliarAndEstatusTrue(
						dea, aa, sexo, parentesco, o);
	}

	public List<Paciente> buscarPorEdadesySexo(int dea, int aa, String sexo) {
		List<String> ordenar = new ArrayList<String>();
		ordenar.add("cedulaFamiliar");
		ordenar.add("cedula");
		Sort o = new Sort(Sort.Direction.ASC, ordenar);
		return pacienteDAO
				.findByEdadBetweenAndTrabajadorFalseAndSexoAndEstatusTrue(dea,
						aa, sexo, o);
	}

	public List<Paciente> buscarPorEdadesyParentesco(int dea, int aa,
			String parentesco) {
		List<String> ordenar = new ArrayList<String>();
		ordenar.add("cedulaFamiliar");
		ordenar.add("cedula");
		Sort o = new Sort(Sort.Direction.ASC, ordenar);
		return pacienteDAO
				.findByEdadBetweenAndTrabajadorFalseAndParentescoFamiliarAndEstatusTrue(
						dea, aa, parentesco, o);
	}

	public List<Paciente> buscarPorEdadesyTrabajador(int dea, int aa,
			String idTrabajador) {
		List<String> ordenar = new ArrayList<String>();
		ordenar.add("cedulaFamiliar");
		ordenar.add("cedula");
		Sort o = new Sort(Sort.Direction.ASC, ordenar);
		return pacienteDAO
				.findByEdadBetweenAndTrabajadorFalseAndCedulaFamiliarAndEstatusTrue(
						dea, aa, idTrabajador, o);
	}

	public List<Paciente> buscarCono(int dea, int aa, String sexo,
			String idTrabajador) {
		List<String> ordenar = new ArrayList<String>();
		ordenar.add("cedulaFamiliar");
		ordenar.add("cedula");
		Sort o = new Sort(Sort.Direction.ASC, ordenar);
		return pacienteDAO
				.findByEdadBetweenAndSexoAndCedulaFamiliarAndEstatusTrue(dea,
						aa, sexo, idTrabajador, o);
	}

	public List<Paciente> buscarPorEdadesTrabajadoryParentesco(int dea, int aa,
			String idTrabajador, String parentesco) {
		List<String> ordenar = new ArrayList<String>();
		ordenar.add("cedulaFamiliar");
		ordenar.add("cedula");
		Sort o = new Sort(Sort.Direction.ASC, ordenar);
		return pacienteDAO
				.findByEdadBetweenAndTrabajadorFalseAndCedulaFamiliarAndParentescoFamiliarAndEstatusTrue(
						dea, aa, idTrabajador, parentesco, o);
	}

	public List<Paciente> buscarPorEdadesTrabajadorParentescoSexo(int dea,
			int aa, String idTrabajador, String parentesco, String sexo) {
		List<String> ordenar = new ArrayList<String>();
		ordenar.add("cedulaFamiliar");
		ordenar.add("cedula");
		Sort o = new Sort(Sort.Direction.ASC, ordenar);
		return pacienteDAO
				.findByEdadBetweenAndTrabajadorFalseAndCedulaFamiliarAndParentescoFamiliarAndSexoAndEstatusTrue(
						dea, aa, idTrabajador, parentesco, sexo, o);
	}

	public List<Paciente> buscarPorEdadesTodos(int dea, int aa) {
		List<String> ordenar = new ArrayList<String>();
		ordenar.add("cedulaFamiliar");
		ordenar.add("cedula");
		Sort o = new Sort(Sort.Direction.ASC, ordenar);
		return pacienteDAO.findByEdadBetweenAndEstatusTrue(dea, aa, o);
	}

	public List<Paciente> buscarPorEdadesTrabajadorSexo(int dea, int aa,
			boolean b, String sexo) {
		List<String> ordenar = new ArrayList<String>();
		ordenar.add("cedulaFamiliar");
		ordenar.add("cedula");
		Sort o = new Sort(Sort.Direction.ASC, ordenar);
		return pacienteDAO.findByEdadBetweenAndTrabajadorAndSexoAndEstatusTrue(
				dea, aa, b, sexo, o);
	}

	public List<Paciente> buscarPorEdadesTrabajadorDiscapacidad(int dea,
			int aa, boolean b, boolean c) {
		List<String> ordenar = new ArrayList<String>();
		ordenar.add("cedulaFamiliar");
		ordenar.add("cedula");
		Sort o = new Sort(Sort.Direction.ASC, ordenar);
		return pacienteDAO
				.findByEdadBetweenAndTrabajadorAndDiscapacidadAndEstatusTrue(
						dea, aa, b, c, o);
	}

	public List<Paciente> buscarPorEdadesTrabajadorDiscapacidadSexo(int dea,
			int aa, boolean b, boolean c, String sexo) {
		List<String> ordenar = new ArrayList<String>();
		ordenar.add("cedulaFamiliar");
		ordenar.add("cedula");
		Sort o = new Sort(Sort.Direction.ASC, ordenar);
		return pacienteDAO
				.findByEdadBetweenAndTrabajadorAndDiscapacidadAndSexoAndEstatusTrue(
						dea, aa, b, c, sexo, o);
	}

	public List<Paciente> buscarPorEdadesDiscapacidad(int dea, int aa, boolean b) {
		List<String> ordenar = new ArrayList<String>();
		ordenar.add("cedulaFamiliar");
		ordenar.add("cedula");
		Sort o = new Sort(Sort.Direction.ASC, ordenar);
		return pacienteDAO.findByEdadBetweenAndDiscapacidadAndEstatusTrue(dea,
				aa, b, o);
	}

	public List<Paciente> buscarPorEdadesDiscapacidadSexo(int dea, int aa,
			boolean b, String sexo) {
		List<String> ordenar = new ArrayList<String>();
		ordenar.add("cedulaFamiliar");
		ordenar.add("cedula");
		Sort o = new Sort(Sort.Direction.ASC, ordenar);
		return pacienteDAO
				.findByEdadBetweenAndDiscapacidadAndSexoAndEstatusTrue(dea, aa,
						b, sexo, o);
	}

	public List<Paciente> buscarPacientesDeCita(DoctorInterno usuario, Date fechaT) {
		Calendar calendario = Calendar.getInstance();
		calendario.setTime(fechaT);
		calendario.set(Calendar.HOUR, 0);
		calendario.set(Calendar.HOUR_OF_DAY, 0);
		calendario.set(Calendar.SECOND, 0);
		calendario.set(Calendar.MILLISECOND, 0);
		calendario.set(Calendar.MINUTE, 0);
		fechaT = calendario.getTime();
		Timestamp fecha = new Timestamp(fechaT.getTime());
		List<Cita> citas = citaDAO.findByDoctorInternoAndEstadoAndFechaCita(usuario,
				"Pendiente", fecha);
		List<Paciente> pacientes = new ArrayList<Paciente>();
		if (!citas.isEmpty())
			for (Iterator<Cita> iterator = citas.iterator(); iterator.hasNext();) {
				Cita cita = (Cita) iterator.next();
				cita.getPaciente().setUsuarioAuditoria(
						String.valueOf(cita.getIdCita()));
				pacientes.add(cita.getPaciente());
			}
		return pacientes;
	}

	public List<Paciente> filtroFichaCitaActivos(DoctorInterno usuario, String valor,
			Date fechaT) {
		Calendar calendario = Calendar.getInstance();
		calendario.setTime(fechaT);
		calendario.set(Calendar.HOUR, 0);
		calendario.set(Calendar.HOUR_OF_DAY, 0);
		calendario.set(Calendar.SECOND, 0);
		calendario.set(Calendar.MILLISECOND, 0);
		calendario.set(Calendar.MINUTE, 0);
		fechaT = calendario.getTime();
		Timestamp fecha = new Timestamp(fechaT.getTime());
		List<Cita> citas = citaDAO
				.findByDoctorInternoAndPacienteFichaStartingWithAndPacienteEstatusTrueAndFechaCitaAndEstadoAllIgnoreCase(
						usuario, valor, fecha, "Pendiente");
		List<Paciente> pacientes = new ArrayList<Paciente>();
		if (!citas.isEmpty())
			for (Iterator<Cita> iterator = citas.iterator(); iterator.hasNext();) {
				Cita cita = (Cita) iterator.next();
				cita.getPaciente().setUsuarioAuditoria(
						String.valueOf(cita.getIdCita()));
				pacientes.add(cita.getPaciente());
			}
		return pacientes;
	}

	public List<Paciente> filtroNombreCitaActivos(DoctorInterno usuario,
			String valor, Date fechaT) {
		Calendar calendario = Calendar.getInstance();
		calendario.setTime(fechaT);
		calendario.set(Calendar.HOUR, 0);
		calendario.set(Calendar.HOUR_OF_DAY, 0);
		calendario.set(Calendar.SECOND, 0);
		calendario.set(Calendar.MILLISECOND, 0);
		calendario.set(Calendar.MINUTE, 0);
		fechaT = calendario.getTime();
		Timestamp fecha = new Timestamp(fechaT.getTime());
		List<Cita> citas = citaDAO
				.findByDoctorInternoAndPacientePrimerNombreStartingWithAndPacienteEstatusTrueAndFechaCitaAndEstadoAllIgnoreCase(
						usuario, valor, fecha, "Pendiente");
		List<Paciente> pacientes = new ArrayList<Paciente>();
		if (!citas.isEmpty())
			for (Iterator<Cita> iterator = citas.iterator(); iterator.hasNext();) {
				Cita cita = (Cita) iterator.next();
				cita.getPaciente().setUsuarioAuditoria(
						String.valueOf(cita.getIdCita()));
				pacientes.add(cita.getPaciente());
			}
		return pacientes;
	}

	public List<Paciente> filtroCedulaCitaActivos(DoctorInterno usuario,
			String valor, Date fechaT) {
		Calendar calendario = Calendar.getInstance();
		calendario.setTime(fechaT);
		calendario.set(Calendar.HOUR, 0);
		calendario.set(Calendar.HOUR_OF_DAY, 0);
		calendario.set(Calendar.SECOND, 0);
		calendario.set(Calendar.MILLISECOND, 0);
		calendario.set(Calendar.MINUTE, 0);
		fechaT = calendario.getTime();
		Timestamp fecha = new Timestamp(fechaT.getTime());
		List<Cita> citas = citaDAO
				.findByDoctorInternoAndPacienteCedulaStartingWithAndPacienteEstatusTrueAndFechaCitaAndEstadoAllIgnoreCase(
						usuario, valor, fecha, "Pendiente");
		List<Paciente> pacientes = new ArrayList<Paciente>();
		if (!citas.isEmpty())
			for (Iterator<Cita> iterator = citas.iterator(); iterator.hasNext();) {
				Cita cita = (Cita) iterator.next();
				cita.getPaciente().setUsuarioAuditoria(
						String.valueOf(cita.getIdCita()));
				pacientes.add(cita.getPaciente());
			}
		return pacientes;
	}

	public List<Paciente> filtroApellidoCitaActivos(DoctorInterno usuario,
			String valor, Date fechaT) {
		Calendar calendario = Calendar.getInstance();
		calendario.setTime(fechaT);
		calendario.set(Calendar.HOUR, 0);
		calendario.set(Calendar.HOUR_OF_DAY, 0);
		calendario.set(Calendar.SECOND, 0);
		calendario.set(Calendar.MILLISECOND, 0);
		calendario.set(Calendar.MINUTE, 0);
		fechaT = calendario.getTime();
		Timestamp fecha = new Timestamp(fechaT.getTime());
		List<Cita> citas = citaDAO
				.findByDoctorInternoAndPacientePrimerApellidoStartingWithAndPacienteEstatusTrueAndFechaCitaAndEstadoAllIgnoreCase(
						usuario, valor, fecha, "Pendiente");
		List<Paciente> pacientes = new ArrayList<Paciente>();
		if (!citas.isEmpty())
			for (Iterator<Cita> iterator = citas.iterator(); iterator.hasNext();) {
				Cita cita = (Cita) iterator.next();
				cita.getPaciente().setUsuarioAuditoria(
						String.valueOf(cita.getIdCita()));
				pacientes.add(cita.getPaciente());
			}
		return pacientes;
	}

	public Paciente buscarPorCitaActivo(DoctorInterno usuario, String value,
			Date fechaT) {
		Calendar calendario = Calendar.getInstance();
		calendario.setTime(fechaT);
		calendario.set(Calendar.HOUR, 0);
		calendario.set(Calendar.HOUR_OF_DAY, 0);
		calendario.set(Calendar.SECOND, 0);
		calendario.set(Calendar.MILLISECOND, 0);
		calendario.set(Calendar.MINUTE, 0);
		fechaT = calendario.getTime();
		Timestamp fecha = new Timestamp(fechaT.getTime());
		List<Cita> citas = citaDAO
				.findByDoctorInternoAndEstadoAndFechaCitaAndPacienteCedulaAndPacienteEstatusTrue(
						usuario, "Pendiente", fecha, value);
		if (!citas.isEmpty())
			for (Iterator<Cita> iterator = citas.iterator(); iterator.hasNext();) {
				Cita cita = (Cita) iterator.next();
				cita.getPaciente().setUsuarioAuditoria(
						String.valueOf(cita.getIdCita()));
				return cita.getPaciente();
			}
		return null;
	}

	public List<Paciente> buscarDisponiblesPeriodo(Periodo periodo) {
		List<PeriodoPaciente> periodoPaciente = periodoPacienteDAO
				.findByPeriodo(periodo);
		List<String> ids = new ArrayList<String>();
		if (periodoPaciente.isEmpty())
			return pacienteDAO.findByTrabajadorTrueAndEstatusTrue();
		else {
			for (int i = 0; i < periodoPaciente.size(); i++) {
				ids.add(periodoPaciente.get(i).getPaciente().getCedula());
			}
			return pacienteDAO.findByCedulaNotInAndTrabajadorTrueAndEstatusTrue(ids);
		}
	}

	public List<Paciente> filtroCedulaAsociado(String valor) {
		return pacienteDAO.findByCedulaFamiliarStartingWithAllIgnoreCase(valor);
	}

	public List<Paciente> filtroCedulaAsociadoC(String valor, String value) {
		return pacienteDAO
				.findByCedulaFamiliarAndCedulaFamiliarStartingWithAllIgnoreCase(
						value, valor);
	}

	public List<Paciente> filtroCedulaFamiliar1Activos(String valor) {
		return pacienteDAO
				.findByCedulaFamiliarStartingWithAndEstatusTrueAllIgnoreCase(valor);
	}

	public List<Paciente> filtroCedulaFamiliarParienteActivos(String valor) {
		return pacienteDAO
				.findByTrabajadorFalseAndEstatusTrueAndCedulaFamiliarStartingWithAllIgnoreCase(valor);
	}

	public List<Paciente> filtroCedulaFamiliarCitaActivos(DoctorInterno usuario,
			String valor, Date fechaT) {
		Calendar calendario = Calendar.getInstance();
		calendario.setTime(fechaT);
		calendario.set(Calendar.HOUR, 0);
		calendario.set(Calendar.HOUR_OF_DAY, 0);
		calendario.set(Calendar.SECOND, 0);
		calendario.set(Calendar.MILLISECOND, 0);
		calendario.set(Calendar.MINUTE, 0);
		fechaT = calendario.getTime();
		Timestamp fecha = new Timestamp(fechaT.getTime());
		List<Cita> citas = citaDAO
				.findByDoctorInternoAndPacienteCedulaFamiliarStartingWithAndPacienteEstatusTrueAndFechaCitaAndEstadoAllIgnoreCase(
						usuario, valor, fecha, "Pendiente");
		List<Paciente> pacientes = new ArrayList<Paciente>();
		if (!citas.isEmpty())
			for (Iterator<Cita> iterator = citas.iterator(); iterator.hasNext();) {
				Cita cita = (Cita) iterator.next();
				cita.getPaciente().setUsuarioAuditoria(
						String.valueOf(cita.getIdCita()));
				pacientes.add(cita.getPaciente());
			}
		return pacientes;
	}

	public List<Paciente> filtroCedulaOFichaTrabajadorActivo(String valor) {
		return pacienteDAO
				.findByTrabajadorTrueAndEstatusTrueAndCedulaStartingWithOrFichaStartingWithAllIgnoreCase(
						valor, valor);
	}

	public Paciente buscarPorCedulaYTrabajadorYActivo(String idPaciente) {
		return pacienteDAO.findByCedulaAndTrabajadorTrueAndEstatusTrue(idPaciente);
	}

	public List<Paciente> filtroNombre2T(String valor) {
		return pacienteDAO
				.findByTrabajadorTrueAndSegundoNombreStartingWithAllIgnoreCase(valor);
	}

	public List<Paciente> filtroApellido2T(String valor) {
		return pacienteDAO
				.findByTrabajadorTrueAndSegundoApellidoStartingWithAllIgnoreCase(valor);
	}

	public List<Paciente> filtroNombre2C(String valor, String value) {
		return pacienteDAO
				.findByCedulaFamiliarAndSegundoNombreStartingWithAllIgnoreCase(
						value, valor);
	}

	public List<Paciente> filtroNombre2(String valor) {
		return pacienteDAO.findBySegundoNombreStartingWithAllIgnoreCase(valor);
	}

	public List<Paciente> filtroApellido2C(String valor, String value) {
		return pacienteDAO
				.findByCedulaFamiliarAndSegundoApellidoStartingWithAllIgnoreCase(
						value, valor);
	}

	public List<Paciente> filtroApellido2(String valor) {
		return pacienteDAO.findBySegundoApellidoStartingWithAllIgnoreCase(valor);
	}

	public List<Paciente> filtroNombre2Activos(String valor) {
		return pacienteDAO
				.findBySegundoNombreStartingWithAndEstatusTrueAllIgnoreCase(valor);
	}

	public List<Paciente> filtroApellido2Activos(String valor) {
		return pacienteDAO
				.findBySegundoApellidoStartingWithAndEstatusTrueAllIgnoreCase(valor);
	}

	public List<Paciente> filtroNombre2ParienteActivos(String valor) {
		return pacienteDAO
				.findByTrabajadorFalseAndEstatusTrueAndSegundoNombreStartingWithAllIgnoreCase(valor);
	}

	public List<Paciente> filtroApellido2ParienteActivos(String valor) {
		return pacienteDAO
				.findByTrabajadorFalseAndEstatusTrueAndPrimerApellidoStartingWithAllIgnoreCase(valor);
	}

	public List<Paciente> filtroNombre2CitaActivos(DoctorInterno usuario,
			String valor, Date fechaT) {
		Calendar calendario = Calendar.getInstance();
		calendario.setTime(fechaT);
		calendario.set(Calendar.HOUR, 0);
		calendario.set(Calendar.HOUR_OF_DAY, 0);
		calendario.set(Calendar.SECOND, 0);
		calendario.set(Calendar.MILLISECOND, 0);
		calendario.set(Calendar.MINUTE, 0);
		fechaT = calendario.getTime();
		Timestamp fecha = new Timestamp(fechaT.getTime());
		List<Cita> citas = citaDAO
				.findByDoctorInternoAndPacienteSegundoNombreStartingWithAndPacienteEstatusTrueAndFechaCitaAndEstadoAllIgnoreCase(
						usuario, valor, fecha, "Pendiente");
		List<Paciente> pacientes = new ArrayList<Paciente>();
		if (!citas.isEmpty())
			for (Iterator<Cita> iterator = citas.iterator(); iterator.hasNext();) {
				Cita cita = (Cita) iterator.next();
				cita.getPaciente().setUsuarioAuditoria(
						String.valueOf(cita.getIdCita()));
				pacientes.add(cita.getPaciente());
			}
		return pacientes;
	}

	public List<Paciente> filtroApellido2CitaActivos(DoctorInterno usuario,
			String valor, Date fechaT) {
		Calendar calendario = Calendar.getInstance();
		calendario.setTime(fechaT);
		calendario.set(Calendar.HOUR, 0);
		calendario.set(Calendar.HOUR_OF_DAY, 0);
		calendario.set(Calendar.SECOND, 0);
		calendario.set(Calendar.MILLISECOND, 0);
		calendario.set(Calendar.MINUTE, 0);
		fechaT = calendario.getTime();
		Timestamp fecha = new Timestamp(fechaT.getTime());
		List<Cita> citas = citaDAO
				.findByDoctorInternoAndPacienteSegundoApellidoStartingWithAndPacienteEstatusTrueAndFechaCitaAndEstadoAllIgnoreCase(
						usuario, valor, fecha, "Pendiente");
		List<Paciente> pacientes = new ArrayList<Paciente>();
		if (!citas.isEmpty())
			for (Iterator<Cita> iterator = citas.iterator(); iterator.hasNext();) {
				Cita cita = (Cita) iterator.next();
				cita.getPaciente().setUsuarioAuditoria(
						String.valueOf(cita.getIdCita()));
				pacientes.add(cita.getPaciente());
			}
		return pacientes;
	}

	public List<Paciente> buscarPorEstadoCivil(EstadoCivil pais) {
		return pacienteDAO.findByEstadoCivil(pais);
	}

	public List<Paciente> buscarTodosFamiliares() {
		Pageable topTen = new PageRequest(0, 10);
		return pacienteDAO.findByTrabajadorFalse(topTen);
	}
	
	public List<Paciente> buscarPostVacacionalPendiente() {
		return pacienteDAO.findByConsultaPendiente();
	}
}
