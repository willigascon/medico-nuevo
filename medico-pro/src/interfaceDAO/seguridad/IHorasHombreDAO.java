package interfaceDAO.seguridad;

import java.util.List;

import modelo.seguridad.HorasHombre;

import org.springframework.data.jpa.repository.JpaRepository;

public interface IHorasHombreDAO extends JpaRepository<HorasHombre, Long>{

	List<HorasHombre> findByHorasStartingWithAllIgnoreCase(String valor);

	List<HorasHombre> findByFechaStartingWithAllIgnoreCase(String valor);

}
