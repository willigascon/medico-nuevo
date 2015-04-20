package servicio.seguridad;

import interfaceDAO.seguridad.IInformeDAO;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import modelo.medico.maestro.Paciente;
import modelo.organizacion.Area;
import modelo.organizacion.Empresa;
import modelo.seguridad.Accidente;
import modelo.seguridad.ClasificacionAccidente;
import modelo.seguridad.Condicion;
import modelo.seguridad.Informe;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("SInforme")
public class SInforme {

	@Autowired
	private IInformeDAO informeDAO;

	public void guardar(Informe informe) {
		informeDAO.save(informe);

	}

	public List<Informe> buscarTodos() {
		return informeDAO.findAll();
	}

	public List<Informe> filtroCodigo(String valor) {
		return informeDAO.findByCodigoStartingWithAllIgnoreCase(valor);
	}

	public List<Informe> filtroNombreTrabajador(String valor) {
		return informeDAO
				.findByPacientePrimerNombreStartingWithAllIgnoreCase(valor);
	}

	public List<Informe> filtroApellidoTrabajador(String valor) {
		return informeDAO
				.findByPacientePrimerApellidoStartingWithAllIgnoreCase(valor);
	}

	public List<Informe> filtroEmpresa(String valor) {
		return informeDAO.findByEmpresaNombreStartingWithAllIgnoreCase(valor);
	}

	public List<Informe> buscarPorCondicion(Condicion condicion) {
		return informeDAO
				.findByCondicionAOrCondicionBOrCondicionCOrCondicionDOrCondicionEOrCondicionF(
						condicion, condicion, condicion, condicion, condicion,
						condicion);
	}

	public List<Informe> buscarPorArea(Area area) {
		return informeDAO.findByArea(area);
	}

	public List<Informe> buscarPorPaciente(Paciente paciente) {
		return informeDAO.findByPaciente(paciente);
	}

	public List<Informe> buscarPorEmpresaTrabajador(Empresa empresa) {
		return informeDAO.findByEmpresa(empresa);
	}

	public List<Informe> buscarPorEmpresaBeneficiaria(Empresa empresa) {
		return informeDAO.findByEmpresaB(empresa);
	}

	public Informe buscar(long parseLong) {
		return informeDAO.findByIdInforme(parseLong);
	}

	public void guardarVarios(List<Informe> informes) {
		informeDAO.save(informes);
	}

	public List<Informe> buscarPorPacienteB(Paciente pacienteAModificar) {
		return informeDAO.findByPacienteB(pacienteAModificar);
	}

	public List<Informe> buscarPorPacienteC(Paciente pacienteAModificar) {
		return informeDAO.findByPacienteC(pacienteAModificar);
	}

	public List<Informe> buscarPorPacienteD(Paciente pacienteAModificar) {
		return informeDAO.findByPacienteD(pacienteAModificar);
	}

	public List<Informe> buscarPorPacienteE(Paciente pacienteAModificar) {
		return informeDAO.findByPacienteE(pacienteAModificar);
	}

	public List<Informe> buscarPorPacienteF(Paciente pacienteAModificar) {
		return informeDAO.findByPacienteF(pacienteAModificar);
	}

	public List<Informe> buscarPorPacienteG(Paciente pacienteAModificar) {
		return informeDAO.findByPacienteG(pacienteAModificar);
	}

	public List<Informe> buscarPorPacienteH(Paciente pacienteAModificar) {
		return informeDAO.findByPacienteH(pacienteAModificar);
	}

	public List<Informe> buscarPorPacienteI(Paciente pacienteAModificar) {
		return informeDAO.findByPacienteI(pacienteAModificar);
	}

	public List<Informe> buscarPorPacienteJ(Paciente pacienteAModificar) {
		return informeDAO.findByPacienteJ(pacienteAModificar);
	}

	public List<Informe> buscarPorPacienteK(Paciente pacienteAModificar) {
		return informeDAO.findByPacienteK(pacienteAModificar);
	}

	public List<Informe> buscarPorPacienteL(Paciente pacienteAModificar) {
		return informeDAO.findByPacienteL(pacienteAModificar);
	}

	public List<Informe> buscarPorPacienteM(Paciente pacienteAModificar) {
		return informeDAO.findByPacienteM(pacienteAModificar);
	}

	public Informe buscarUltimo() {
		long id = informeDAO.findMaxIdInforme();
		if (id != 0)
			return informeDAO.findOne(id);
		return null;
	}

	public List<Informe> buscarConCodigo() {
		return informeDAO.findByCodigoNotNull();
	}

	public List<Informe> filtroFecha(Timestamp valor, Date date) {
		Calendar calendario = Calendar.getInstance();
		calendario.setTime(date);
		calendario.set(Calendar.HOUR, 11);
		calendario.set(Calendar.HOUR_OF_DAY, 23);
		calendario.set(Calendar.SECOND, 59);
		calendario.set(Calendar.MILLISECOND, 0);
		calendario.set(Calendar.MINUTE, 59);
		date = calendario.getTime();
		return informeDAO.findByFaBetween(valor, new Timestamp(date.getTime()));
	}

	public String buscarMaxCodigo() {
		return informeDAO.buscarMaxCodigo();
	}

	public List<Informe> buscarPorAccidente(Accidente accidente) {
		return informeDAO.findByAccidente(accidente);
	}

}
