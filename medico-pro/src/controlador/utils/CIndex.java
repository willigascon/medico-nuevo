package controlador.utils;

import java.io.IOException;

import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zul.Window;

public class CIndex extends CGenerico {

	private static final long serialVersionUID = -2911126596875079981L;

	@Override
	public void inicializar() throws IOException {

	}
	
	@Listen("onClick = #lblOlvidoClave")
	public void abrirVentana(){
		Window window = (Window) Executions.createComponents(				
				"/vistas/security/VReinicioPassword.zul", null, null);
				window.doModal();
	}
}
