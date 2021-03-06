package servicio.seguridad;

import interfaceDAO.seguridad.IGrupoInspectoresDAO;
import modelo.seguridad.GrupoInspectores;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("SGrupoInspectores")
public class SGrupoInspectores {
	
	@Autowired
	private IGrupoInspectoresDAO grupoInspectoresDAO;

	public void guardar(GrupoInspectores grupo) {
		grupoInspectoresDAO.save(grupo);
		
	}

	public GrupoInspectores buscar(int i) {
		// TODO Auto-generated method stub
		return grupoInspectoresDAO.findById(i);
	}
}
