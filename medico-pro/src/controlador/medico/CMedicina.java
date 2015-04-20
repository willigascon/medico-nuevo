package controlador.medico;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import modelo.medico.consulta.ConsultaMedicina;
import modelo.medico.maestro.Medicina;
import modelo.security.Arbol;

import org.zkoss.zk.ui.Sessions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Button;
import org.zkoss.zul.Div;
import org.zkoss.zul.Doublespinner;
import org.zkoss.zul.Include;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Tab;
import org.zkoss.zul.Tabbox;
import org.zkoss.zul.Textbox;

import componente.Botonera;
import componente.Catalogo;
import componente.Mensaje;

import controlador.security.CArbol;
import controlador.utils.CGenerico;

public class CMedicina extends CGenerico {

	private static final long serialVersionUID = -6022315737961269719L;

	@Wire
	private Textbox txtNombre;
	@Wire
	private Doublespinner spnPrecio;
	@Wire
	private Div botoneraMedicina;
	@Wire
	private Div catalogoMedicina;
	@Wire
	private Div divMedicina;
	@Wire
	private Button btnBuscarMedicina;
	@Wire
	private Textbox txtLaboratorio;
	@Wire
	private Textbox txtCategoria;
	@Wire
	private Textbox txtDenominacionGenerica;
	@Wire
	private Textbox txtComposicion;
	@Wire
	private Textbox txtPosologia;
	@Wire
	private Textbox txtIndicaciones;
	@Wire
	private Textbox txtEfectos;
	@Wire
	private Textbox txtPrecauciones;
	@Wire
	private Textbox txtContraindicaciones;
	@Wire
	private Textbox txtEmbarazo;
	Catalogo<Medicina> catalogo;
	long id = 0;
	private boolean consulta = false;
	private CConsulta cConsulta = new CConsulta();
	private CArbol cArbol = new CArbol();
	private boolean paciente = false;
	private CPaciente cPaciente = new CPaciente();
	List<Medicina> medicinaConsulta = new ArrayList<Medicina>();
	Listbox listaConsulta;

	@Override
	public void inicializar() throws IOException {
		contenido = (Include) divMedicina.getParent();
		Tabbox tabox = (Tabbox) divMedicina.getParent().getParent().getParent()
				.getParent();
		tabBox = tabox;
		tab = (Tab) tabox.getTabs().getLastChild();
		HashMap<String, Object> mapa = (HashMap<String, Object>) Sessions
				.getCurrent().getAttribute("mapaGeneral");
		if (mapa != null) {
			if (mapa.get("tabsGenerales") != null) {
				tabs = (List<Tab>) mapa.get("tabsGenerales");
				titulo = (String) mapa.get("titulo");
				mapa.clear();
				mapa = null;
			}
		}
		HashMap<String, Object> map = (HashMap<String, Object>) Sessions
				.getCurrent().getAttribute("itemsCatalogo");
		if (map != null) {
			if (map.get("id") != null) {
				if (map.get("id").equals("consulta"))
					consulta = true;
				if (map.get("id").equals("paciente"))
					paciente = true;
				medicinaConsulta = (List<Medicina>) map.get("lista");
				titulo = (String) mapa.get("titulo");
				listaConsulta = (Listbox) map.get("listbox");
				map.clear();
				map = null;
			}
		}
		txtDenominacionGenerica.setFocus(true);
		Botonera botonera = new Botonera() {
			@Override
			public void guardar() {
				if (validar()) {
					String nombre = txtNombre.getValue();
					Double precio = spnPrecio.getValue();
					String denominacionGenerica = txtDenominacionGenerica
							.getValue();
					String composicion = txtComposicion.getValue();
					String posologia = txtPosologia.getValue();
					String indicaciones = txtIndicaciones.getValue();
					String efectos = txtEfectos.getValue();
					String precauciones = txtPrecauciones.getValue();
					String contraindicaciones = txtContraindicaciones
							.getValue();
					String embarazo = txtEmbarazo.getValue();
					String laboratorio = txtLaboratorio.getValue();
					String ca = txtCategoria.getValue();
					Medicina medicina = new Medicina(id, composicion,
							contraindicaciones, denominacionGenerica, efectos,
							embarazo, fechaHora, horaAuditoria, indicaciones,
							nombre, posologia, precauciones,
							nombreUsuarioSesion(), laboratorio, ca, precio);
					servicioMedicina.guardar(medicina);
					if (id != 0)
						medicina = servicioMedicina.buscar(id);
					else
						medicina = servicioMedicina.buscarUltima();

					if (consulta) {
						if (id == 0)
							medicinaConsulta.add(medicina);
						cConsulta.recibirMedicina(medicinaConsulta,
								listaConsulta);
					}
					Mensaje.mensajeInformacion(Mensaje.guardado);
					limpiar();
				}

			}

			@Override
			public void limpiar() {
				spnPrecio.setValue((double) 0);
				txtNombre.setText("");
				txtLaboratorio.setText("");
				txtCategoria.setText("");
				txtDenominacionGenerica.setText("");
				txtComposicion.setText("");
				txtPosologia.setText("");
				txtIndicaciones.setText("");
				txtEfectos.setText("");
				txtPrecauciones.setText("");
				txtContraindicaciones.setText("");
				txtEmbarazo.setText("");
				id = 0;
				limpiarColores(txtNombre, txtCategoria, txtLaboratorio,
						spnPrecio);
			}

			@Override
			public void salir() {
				cerrarVentana(divMedicina, titulo, tabs);
			}

			@Override
			public void eliminar() {

				if (id != 0) {
					Messagebox.show("¿Esta Seguro de Eliminar la Medicina?",
							"Alerta", Messagebox.OK | Messagebox.CANCEL,
							Messagebox.QUESTION,
							new org.zkoss.zk.ui.event.EventListener<Event>() {
								public void onEvent(Event evt)
										throws InterruptedException {
									if (evt.getName().equals("onOK")) {
										Medicina medicina = servicioMedicina
												.buscar(id);
										List<ConsultaMedicina> consultas = servicioConsultaMedicina
												.buscarPorMedicina(medicina);
										if (!consultas.isEmpty()) {
											Mensaje.mensajeError(Mensaje.noEliminar);
										} else {
											servicioMedicina.eliminar(medicina);
											limpiar();
											Mensaje.mensajeInformacion(Mensaje.eliminado);
										}
									}
								}
							});
				} else
					Mensaje.mensajeAlerta(Mensaje.noSeleccionoRegistro);

			}
		};
		/* Dibuja el componente botonera en el div botoneraPresentacionr */
		botoneraMedicina.appendChild(botonera);

	}

	/* Muestra un catalogo de Medicinas */
	@Listen("onClick = #btnBuscarMedicina")
	public void mostrarCatalogo() throws IOException {
		List<Medicina> medicinas = servicioMedicina.buscarTodas();
		catalogo = new Catalogo<Medicina>(catalogoMedicina,
				"Catalogo de Medicinas", medicinas, false, "Nombre",
				"Laboratorio", "Denominacion Generica") {

			@Override
			protected String[] crearRegistros(Medicina medicina) {
				String[] registros = new String[3];
				registros[0] = medicina.getNombre();
				registros[1] = medicina.getLaboratorio();
				registros[2] = medicina.getDenominacionGenerica();

				return registros;
			}

			@Override
			protected List<Medicina> buscar(String valor, String combo) {
				if (combo.equals("Nombre"))
					return servicioMedicina.filtroNombre(valor);
				else {
					if (combo.equals("Laboratorio"))
						return servicioMedicina.filtroLaboratorio(valor);
					else {
						if (combo.equals("Denominacion Generica"))
							return servicioMedicina.filtroDenominacion(valor);
						else
							return servicioMedicina.buscarTodas();
					}
				}

			}
		};
		catalogo.setParent(catalogoMedicina);
		catalogo.doModal();
	}

	/* Validaciones de pantalla para poder realizar el guardar */
	public boolean validar() {

		if (txtLaboratorio.getText().compareTo("") == 0
				|| txtCategoria.getText().compareTo("") == 0
				|| txtNombre.getText().compareTo("") == 0
				|| spnPrecio.getText().compareTo("") == 0) {
			Mensaje.mensajeError(Mensaje.camposVacios);
			aplicarColores(txtLaboratorio, txtCategoria, txtNombre, spnPrecio);
			return false;
		} else
			return true;
	}

	/* Busca si existe una medicina con el mismo nombre escrito */
	@Listen("onChange = #txtNombre")
	public void buscarPorNombre() {
		Medicina medicina = servicioMedicina.buscarPorNombre(txtNombre
				.getValue());
		if (medicina != null)
			llenarCampos(medicina);

	}

	/*
	 * Selecciona una medicina del catalogo y llena los campos con la
	 * informacion
	 */
	@Listen("onSeleccion = #catalogoMedicina")
	public void seleccion() {

		Medicina medicinaSeleccionada = catalogo
				.objetoSeleccionadoDelCatalogo();
		llenarCampos(medicinaSeleccionada);
		catalogo.setParent(null);
	}

	/* LLena los campos del formulario dada una medicina */
	public void llenarCampos(Medicina medicina) {
		txtLaboratorio.setValue(medicina.getLaboratorio());
		txtCategoria.setValue(medicina.getCategoriaMedicina());
		txtDenominacionGenerica.setValue(medicina.getDenominacionGenerica());
		txtComposicion.setValue(medicina.getComposicion());
		txtPosologia.setValue(medicina.getPosologia());
		txtIndicaciones.setValue(medicina.getIndicaciones());
		txtEfectos.setValue(medicina.getEfectos());
		txtPrecauciones.setValue(medicina.getPrecaucion());
		txtContraindicaciones.setValue(medicina.getContraindicaciones());
		txtEmbarazo.setValue(medicina.getEmbarazo());
		txtNombre.setValue(medicina.getNombre());
		if (medicina.getPrecio() != null)
			spnPrecio.setValue(medicina.getPrecio());
		else
			spnPrecio.setValue((double) 0);
		id = medicina.getIdMedicina();
	}

	/* Abre la vista de Categoria */
	@Listen("onClick = #btnAbrirCategoria")
	public void abrirCategoria() {
		List<Arbol> arboles = servicioArbol
				.buscarPorNombreArbol("Categoria Medicina");
		if (!arboles.isEmpty()) {
			Arbol arbolItem = arboles.get(0);
			cArbol.abrirVentanas(arbolItem, tabBox, contenido, tab, tabs);
		}
	}

	/* Abre la vista de Laboratorio */
	@Listen("onClick = #btnAbrirLaboratorio")
	public void abrirLaboratorio() {
		List<Arbol> arboles = servicioArbol.buscarPorNombreArbol("Laboratorio");
		if (!arboles.isEmpty()) {
			Arbol arbolItem = arboles.get(0);
			cArbol.abrirVentanas(arbolItem, tabBox, contenido, tab, tabs);
		}
	}

}
