package interfaceDAO.medico.consulta;

import java.util.Date;
import java.util.List;

import modelo.medico.consulta.Consulta;
import modelo.medico.consulta.ConsultaDiagnostico;
import modelo.medico.maestro.Diagnostico;
import modelo.organizacion.Area;
import modelo.pk.ConsultaDiagnosticoId;
import modelo.seguridad.Accidente;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface IConsultaDiagnosticoDAO extends
		JpaRepository<ConsultaDiagnostico, ConsultaDiagnosticoId> {

	List<ConsultaDiagnostico> findByConsulta(Consulta consulta);

	List<ConsultaDiagnostico> findByAccidente(Accidente accidente);

	List<ConsultaDiagnostico> findByDiagnostico(Diagnostico diag);
	
	List<ConsultaDiagnostico> findByConsultaFechaConsultaBetweenAndConsultaPacienteEdadBetween(
			Date fecha1, Date fecha2, int dea, int aa, Sort o);

	List<ConsultaDiagnostico> findByConsultaFechaConsultaBetweenAndConsultaPacienteEdadBetweenAndTipo(
			Date fecha1, Date fecha2, int dea, int aa, String diagnostico,
			Sort o);

	List<ConsultaDiagnostico> findByConsultaFechaConsultaBetweenAndConsultaPacienteEdadBetweenAndConsultaPacienteTrabajador(
			Date fecha1, Date fecha2, int dea, int aa, boolean b, Sort o);

	List<ConsultaDiagnostico> findByConsultaFechaConsultaBetweenAndConsultaPacienteEdadBetweenAndTipoAndConsultaPacienteTrabajador(
			Date fecha1, Date fecha2, int dea, int aa, String diagnostico,
			boolean b, Sort o);

	List<ConsultaDiagnostico> findByConsultaFechaConsultaBetween(Date fecha1,
			Date fecha2, Sort o);

	List<ConsultaDiagnostico> findByConsultaFechaConsultaBetweenAndTipo(
			Date fecha1, Date fecha2, String diagnostico, Sort o);

	List<ConsultaDiagnostico> findByConsultaFechaConsultaBetweenAndConsultaPacienteTrabajador(
			Date desde, Date hasta, boolean estado, Sort order);

	List<ConsultaDiagnostico> findByConsultaFechaConsultaBetweenOrderByDiagnosticoNombreAsc(
			Date desde, Date hasta);

	List<ConsultaDiagnostico> findByConsultaFechaConsultaBetweenAndConsultaPacienteTrabajadorOrderByDiagnosticoNombreAsc(
			Date desde, Date hasta, boolean trabajador);

	List<ConsultaDiagnostico> findByConsultaFechaConsultaBetweenAndConsultaPacienteTrabajadorTrueAndConsultaAccidenteLaboralTrueAndTipoNotIn(
			Date desde, Date hasta, List<String> lista2, Sort order);

	List<ConsultaDiagnostico> findByConsultaFechaConsultaBetweenAndConsultaPacienteTrabajadorTrueAndConsultaAccidenteLaboralTrueAndConsultaPacienteAreaAndTipoOrderByConsultaFechaConsultaAsc(
			Date desde, Date hasta, Area area, String diagnostico);

	List<ConsultaDiagnostico> findByConsultaFechaConsultaBetweenAndConsultaPacienteTrabajadorTrueAndConsultaAccidenteLaboralTrueAndConsultaPacienteAreaAndTipoNotIn(
			Date desde, Date hasta, Area area, List<String> lista2, Sort order);

	List<ConsultaDiagnostico> findByConsultaFechaConsultaBetweenAndConsultaPacienteTrabajadorTrueAndConsultaAccidenteLaboralTrueAndTipo(
			Date desde, Date hasta, String diagnostico, Sort order);

	@Query("select distinct c.consulta.idConsulta from ConsultaDiagnostico c where c in ?1")
	List<Long> buscarConsultas(List<ConsultaDiagnostico> consutaDiag);

	List<ConsultaDiagnostico> findByConsultaFechaConsultaBetweenAndConsultaReposoAndConsultaPacienteTrabajador(
			Date fecha1, Date fecha2, boolean b, boolean c, Sort o);

	List<ConsultaDiagnostico> findByConsultaFechaConsultaBetweenAndTipoAndConsultaReposoAndConsultaPacienteTrabajador(
			Date fecha1, Date fecha2, String diagnostico, boolean b, boolean c,
			Sort o);

	@Query("select distinct(d.diagnostico) from ConsultaDiagnostico d where d.consulta.reposo = ?1 "
			+ "and d.consulta.paciente.trabajador = ?2 and d.consulta.fechaConsulta between ?3 and ?4")
	List<Diagnostico> findDiagnosticoDistinctByConsultaReposoAndConsultaPacienteTrabajador(
			boolean b, boolean c, Date date, Date date2);

	List<ConsultaDiagnostico> findByConsultaFechaConsultaBetweenAndConsultaReposoAndConsultaPacienteTrabajadorAndDiagnosticoIdDiagnosticoIn(
			Date desde, Date hasta, boolean b, boolean c,
			List<Long> diagnosticosAgregados, Sort o);

	List<ConsultaDiagnostico> findByConsultaFechaConsultaBetweenAndTipoAndConsultaReposoAndConsultaPacienteTrabajadorAndDiagnosticoIdDiagnosticoIn(
			Date desde, Date hasta, String diagnostico, boolean b, boolean c,
			List<Long> ids, Sort o);
	
	@Query("select distinct(d.diagnostico) from ConsultaDiagnostico d where d.consulta.fechaConsulta between ?1 and ?2")
	List<Diagnostico> findDiagnosticoDistinct(Date date, Date date2);

	List<ConsultaDiagnostico> findByConsultaFechaConsultaBetweenAndConsultaPacienteEdadBetweenAndDiagnosticoIdDiagnosticoIn(
			Date desde, Date hasta, int dea, int aa, List<Long> ids, Sort o);

	List<ConsultaDiagnostico> findByConsultaFechaConsultaBetweenAndConsultaPacienteEdadBetweenAndTipoAndDiagnosticoIdDiagnosticoIn(
			Date desde, Date hasta, int dea, int aa, String diagnostico,
			List<Long> ids, Sort o);

	List<ConsultaDiagnostico> findByConsultaFechaConsultaBetweenAndConsultaPacienteEdadBetweenAndConsultaPacienteTrabajadorAndDiagnosticoIdDiagnosticoIn(
			Date desde, Date hasta, int dea, int aa, boolean b, List<Long> ids,
			Sort o);

	List<ConsultaDiagnostico> findByConsultaFechaConsultaBetweenAndConsultaPacienteEdadBetweenAndTipoAndConsultaPacienteTrabajadorAndDiagnosticoIdDiagnosticoIn(
			Date desde, Date hasta, int dea, int aa, String diagnostico,
			boolean b, List<Long> ids, Sort o);

	List<ConsultaDiagnostico> findByConsultaFechaConsultaBetweenAndDiagnosticoCategoriaClasificacionIdClasificacion(
			Date desde, Date hasta, long l, Sort o);

	List<ConsultaDiagnostico> findByConsultaFechaConsultaBetweenAndDiagnosticoCategoriaIdCategoriaDiagnostico(
			Date desde, Date hasta, long l, Sort o);

}
