package servicio.medico.maestro;

import interfaceDAO.medico.maestro.IProveedorServicioDAO;

import java.util.ArrayList;
import java.util.List;

import modelo.medico.maestro.Proveedor;
import modelo.medico.maestro.ProveedorServicio;
import modelo.medico.maestro.ServicioExterno;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("SProveedorServicio")
public class SProveedorServicio {

	@Autowired
	private IProveedorServicioDAO proveedorServicioDAO;

	public List<ProveedorServicio> buscarEstudiosUsados(Proveedor proveedor) {
		return proveedorServicioDAO.findByProveedor(proveedor);
	}

	public void eliminar(List<ProveedorServicio> estudios) {
		proveedorServicioDAO.delete(estudios);

	}

	public void guardar(List<ProveedorServicio> listaEstudios) {
		proveedorServicioDAO.save(listaEstudios);

	}

	public ProveedorServicio buscarPorCodigoDeAmbos(long parseLong, long id) {
		return proveedorServicioDAO
				.findByProveedorIdProveedorAndServicioExternoIdServicioExterno(
						parseLong, id);
	}

	public List<Proveedor> buscarPorCodigoDeServicio(long id) {
		List<ProveedorServicio> lista = proveedorServicioDAO
				.findByServicioExternoIdServicioExterno(id);
		List<Proveedor> proveedores = new ArrayList<Proveedor>();
		if (!lista.isEmpty()) {
			for (int i = 0; i < lista.size(); i++) {
				proveedores.add(lista.get(i).getProveedor());
			}
		}
		return proveedores;
	}

	public List<ProveedorServicio> buscarPorServicio(
			ServicioExterno servicioExterno) {
		// TODO Auto-generated method stub
		return proveedorServicioDAO.findByServicioExterno(servicioExterno);
	}
}
