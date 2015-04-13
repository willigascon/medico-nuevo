package servicio.security;

import interfaceDAO.security.IUsuarioDAO;

import java.util.List;

import modelo.medico.maestro.Especialidad;
import modelo.security.Grupo;
import modelo.security.Usuario;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("SUsuario")
public class SUsuario {

	@Autowired
	private IUsuarioDAO usuarioDAO;

//	@Transactional
//	public Usuario buscarUsuarioPorId(String id) {
//		return usuarioDAO.findByCedula(id);
//	}

	public void guardar(Usuario usuario) {
		usuarioDAO.save(usuario);
	}

	
//	public Usuario buscarUsuarioPorNombre(String nombre) {
//		return usuarioDAO.findByLogin(nombre);
//	}

//	@Transactional
//	public List<Usuario> buscarPorEspecialidad(Especialidad especialidad) {
//		return usuarioDAO.findByEspecialidad(especialidad);
//	}

//	@Transactional
//	public Usuario buscarPorCedula(String value) {
//		return usuarioDAO.findByCedula(value);
//	}

	public List<Usuario> buscarTodos() {
		return usuarioDAO.findAll();
	}

	public void eliminar(Usuario usuario) {
		usuarioDAO.delete(usuario);
	}

	public List<Usuario> filtroNombre(String valor) {
		return usuarioDAO.findByNombreStartingWithAllIgnoreCase(valor);
	}

	public List<Usuario> filtroCorreo(String valor) {
		return usuarioDAO.findByEmailStartingWithAllIgnoreCase(valor);
	}

	public List<Usuario> filtroLogin(String valor) {
		return usuarioDAO.findByLoginStartingWithAllIgnoreCase(valor);
	}

	public List<Usuario> filtroApellido(String valor) {
		return usuarioDAO.findByApellidoStartingWithAllIgnoreCase(valor);
	}

//	public List<Usuario> filtroEspecialidad(String valor) {
//		return usuarioDAO
//				.findByEspecialidadDescripcionStartingWithAllIgnoreCase(valor);
//	}

	public List<Usuario> buscarPorGrupo(Grupo grupo) {
		return usuarioDAO.findByGrupos(grupo);
	}

//	public List<Usuario> buscarDoctores() {
//		return usuarioDAO.findByDoctor(true);
//	}
	
	@Transactional
	public Usuario buscarPorLogin(String value) {
		return usuarioDAO.findByLogin(value);
	}


	public Usuario buscarPorLoginyCorreo(String value, String value2) {
		return usuarioDAO.findByLoginAndEmail(value, value2);
	}

//	public List<Usuario> filtroDoctor(String valor) {
//		switch (valor.toLowerCase()) {
//		case "si":
//			return usuarioDAO.findByDoctor(true);
//		case "no":
//			return usuarioDAO.findByDoctor(false);
//		default:
//			return usuarioDAO.findAll();
//		}
//	}

//	public List<Usuario> filtroCedulaDoctor(String valor) {
//		return usuarioDAO.findByDoctorAndCedulaStartingWithAllIgnoreCase(true,valor);
//	}
//
//	public List<Usuario> filtroFichaDoctor(String valor) {
//		return usuarioDAO.findByDoctorAndFichaStartingWithAllIgnoreCase(true,valor);
//	}
//
//	public List<Usuario> filtroNombreDoctor(String valor) {
//		return usuarioDAO.findByDoctorAndPrimerNombreStartingWithAllIgnoreCase(true,valor);
//	}
//
//	public List<Usuario> filtroEspecialidadDoctor(String valor) {
//		return usuarioDAO
//				.findByDoctorAndEspecialidadDescripcionStartingWithAllIgnoreCase(true,valor);
//	}
//
//	public List<Usuario> filtroApellidoDoctor(String valor) {
//		return usuarioDAO.findByDoctorAndPrimerApellidoStartingWithAllIgnoreCase(true,valor);
//	}
//
//	public Usuario buscarPorCedulayCorreo(String value, String value2) {
//		return usuarioDAO.findByCedulaAndEmail(value, value2);
//	}

}