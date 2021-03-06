package controlador.medico;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import modelo.medico.consulta.ConsultaDiagnostico;
import modelo.medico.maestro.CategoriaDiagnostico;
import modelo.medico.maestro.Diagnostico;
import modelo.security.Arbol;

import org.zkoss.zk.ui.Sessions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Button;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Comboitem;
import org.zkoss.zul.Div;
import org.zkoss.zul.Include;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Radio;
import org.zkoss.zul.Radiogroup;
import org.zkoss.zul.Tab;
import org.zkoss.zul.Tabbox;
import org.zkoss.zul.Textbox;

import componente.Botonera;
import componente.Catalogo;
import componente.Mensaje;
import controlador.security.CArbol;
import controlador.utils.CGenerico;

public class CDiagnostico extends CGenerico {

	private static final long serialVersionUID = -4299008134868657236L;
	@Wire
	private Div divDiagnostico;
	@Wire
	private Div botoneraDiagnostico;
	@Wire
	private Div catalogoDiagnostico;
	@Wire
	private Textbox txtNombreDiagnostico;
	@Wire
	private Textbox txtCodigoDiagnostico;
	@Wire
	private Combobox cmbCategoria;
	@Wire
	private Button btnBuscarDiagnostico;
	@Wire
	private Radio rdoSiEpi;
	@Wire
	private Radio rdoNoEpi;
	@Wire
	private Radiogroup rdgEpi;

	private CArbol cArbol = new CArbol();
	long id = 0;
	Catalogo<Diagnostico> catalogo;
	private boolean consulta = false;
	private CConsulta cConsulta = new CConsulta();
	List<Diagnostico> diagnosticoConsulta = new ArrayList<Diagnostico>();
	Listbox listaConsulta;

	@Override
	public void inicializar() throws IOException {
		contenido = (Include) divDiagnostico.getParent();
		Tabbox tabox = (Tabbox) divDiagnostico.getParent().getParent()
				.getParent().getParent();
		tabBox = tabox;
		tab = (Tab) tabox.getTabs().getLastChild();
		HashMap<String, Object> mapa = (HashMap<String, Object>) Sessions
				.getCurrent().getAttribute("mapaGeneral");
		if (mapa != null) {
			if (mapa.get("tabsGenerales") != null) {
				titulo = (String) mapa.get("titulo");
				tabs = (List<Tab>) mapa.get("tabsGenerales");
				mapa.clear();
				mapa = null;
			}
		}
		HashMap<String, Object> map = (HashMap<String, Object>) Sessions
				.getCurrent().getAttribute("itemsCatalogo");
		if (map != null) {
			if (map.get("id") != null) {
				consulta = true;
				diagnosticoConsulta = (List<Diagnostico>) map.get("lista");
				listaConsulta = (Listbox) map.get("listbox");
				titulo = (String) map.get("titulo");
				map.clear();
				map = null;
			}
		}

		llenarComboCategoria();
		Botonera botonera = new Botonera() {

			@Override
			public void salir() {
				cerrarVentana(divDiagnostico, titulo, tabs);
			}

			@Override
			public void limpiar() {
				txtNombreDiagnostico.setValue("");
				txtCodigoDiagnostico.setValue("");
				cmbCategoria.setValue("");
				cmbCategoria.setPlaceholder("Seleccione una Categoria");
				if (rdoNoEpi.isChecked())
					rdoNoEpi.setChecked(false);
				if (rdoSiEpi.isChecked())
					rdoSiEpi.setChecked(false);
				limpiarColores(txtNombreDiagnostico, txtCodigoDiagnostico,
						cmbCategoria, rdgEpi);
				id = 0;
			}

			@Override
			public void guardar() {
				if (validar()) {
					String nombre, codigo;
					nombre = txtNombreDiagnostico.getValue();
					codigo = txtCodigoDiagnostico.getValue();
					Boolean epi = false;
					if (rdoSiEpi.isChecked())
						epi = true;
					CategoriaDiagnostico categoria = servicioCategoriaDiagnostico
							.buscar(Long.parseLong(cmbCategoria
									.getSelectedItem().getContext()));
					Diagnostico diagnostico = new Diagnostico(id, codigo,
							fechaHora, horaAuditoria, nombre,
							nombreUsuarioSesion(), categoria, epi);
					servicioDiagnostico.guardar(diagnostico);
					if (consulta) {
						if (id != 0)
							diagnostico = servicioDiagnostico.buscar(id);
						else {
							diagnostico = servicioDiagnostico.buscarUltimo();
							diagnosticoConsulta.add(diagnostico);
						}
						cConsulta.recibirDiagnostico(diagnosticoConsulta,
								listaConsulta);
					}
					limpiar();
					Mensaje.mensajeInformacion(Mensaje.guardado);
				}
			}

			@Override
			public void eliminar() {
				if (id != 0) {
					Messagebox.show("�Esta Seguro de Eliminar el Diagnostico?",
							"Alerta", Messagebox.OK | Messagebox.CANCEL,
							Messagebox.QUESTION,
							new org.zkoss.zk.ui.event.EventListener<Event>() {
								public void onEvent(Event evt)
										throws InterruptedException {
									if (evt.getName().equals("onOK")) {
										Diagnostico diag = servicioDiagnostico
												.buscar(id);
										List<ConsultaDiagnostico> consultas = servicioConsultaDiagnostico
												.buscarPorDiagnostico(diag);
										if (!consultas.isEmpty())
											Mensaje.mensajeError(Mensaje.noEliminar);
										else {
											servicioDiagnostico.eliminar(diag);
											limpiar();
											Mensaje.mensajeInformacion(Mensaje.eliminado);
										}
									}
								}
							});
				} else {
					Mensaje.mensajeAlerta(Mensaje.noSeleccionoRegistro);
				}
			}
		};
		botoneraDiagnostico.appendChild(botonera);
	}

	/* Permite validar que todos los campos esten completos */
	public boolean validar() {
		if (txtNombreDiagnostico.getText().compareTo("") == 0
				|| txtCodigoDiagnostico.getText().compareTo("") == 0
				|| cmbCategoria.getText().compareTo("") == 0
				|| (!rdoNoEpi.isChecked() && !rdoSiEpi.isChecked())) {
			aplicarColores(txtNombreDiagnostico, txtCodigoDiagnostico,
					cmbCategoria, rdgEpi);
			Mensaje.mensajeError(Mensaje.camposVacios);
			return false;
		} else
			return true;
	}

	/* Muestra el catalogo de los diagnosticos */
	@Listen("onClick = #btnBuscarDiagnostico")
	public void mostrarCatalogo() {
		final List<Diagnostico> diagnosticos = servicioDiagnostico
				.buscarTodas();
		catalogo = new Catalogo<Diagnostico>(catalogoDiagnostico,
				"Catalogo de Diagnosticos", diagnosticos, false, "Codigo",
				"Nombre", "Categoria") {

			@Override
			protected List<Diagnostico> buscar(String valor, String combo) {

				switch (combo) {
				case "Nombre":
					return servicioDiagnostico.filtroNombre(valor);
				case "Codigo":
					return servicioDiagnostico.filtroCodigo(valor);
				case "Categoria":
					return servicioDiagnostico.filtroCategoria(valor);
				default:
					return diagnosticos;
				}

			}

			@Override
			protected String[] crearRegistros(Diagnostico objeto) {
				String[] registros = new String[3];
				registros[0] = objeto.getCodigo();
				registros[1] = objeto.getNombre();
				registros[2] = objeto.getCategoria().getNombre();
				return registros;
			}

		};
		catalogo.setParent(catalogoDiagnostico);
		catalogo.doModal();
	}

	/* Llena el combo de Categorias cada vez que se abre */
	@Listen("onOpen = #cmbCategoria")
	public void llenarComboCategoria() {
		List<CategoriaDiagnostico> categorias = servicioCategoriaDiagnostico
				.buscarTodas();
		cmbCategoria.setModel(new ListModelList<CategoriaDiagnostico>(
				categorias));
	}

	/* Permite la seleccion de un item del catalogo */
	@Listen("onSeleccion = #catalogoDiagnostico")
	public void seleccinar() {
		Diagnostico diagnostico = catalogo.objetoSeleccionadoDelCatalogo();
		llenarCampos(diagnostico);
		catalogo.setParent(null);
	}

	/* Busca si existe un diagnostico con el mismo codigo escrito */
	@Listen("onChange = #txtCodigoDiagnostico")
	public void buscarPorNombre() {
		Diagnostico diagnostico = servicioDiagnostico
				.buscarPorCodigo(txtCodigoDiagnostico.getValue());
		if (diagnostico != null)
			llenarCampos(diagnostico);
	}

	/* LLena los campos del formulario dado un diagnostico */
	private void llenarCampos(Diagnostico diagnostico) {
		txtCodigoDiagnostico.setValue(diagnostico.getCodigo());
		txtNombreDiagnostico.setValue(diagnostico.getNombre());
		cmbCategoria.setValue(diagnostico.getCategoria().getNombre());
		Comboitem item = cmbCategoria.appendItem(diagnostico.getCategoria()
				.getNombre());
		item.setContext(String.valueOf(diagnostico.getCategoria()
				.getIdCategoriaDiagnostico()));
		cmbCategoria.setSelectedItem(item);
		if (diagnostico.getEpi() != null) {
			Boolean epi = diagnostico.getEpi();
			if (epi)
				rdoSiEpi.setChecked(true);
			else
				rdoNoEpi.setChecked(true);
		}
		id = diagnostico.getIdDiagnostico();
	}

	/* Abre la vista de Categoria */
	@Listen("onClick = #btnAbrirCategoria")
	public void abrirCategoria() {
		List<Arbol> arboles = servicioArbol
				.buscarPorNombreArbol("Categoria Diagnostico");
		final HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("titulo", "Categoria Diagnostico");
		Sessions.getCurrent().setAttribute("itemsCatalogo", map);
		if (!arboles.isEmpty()) {
			Arbol arbolItem = arboles.get(0);
			cArbol.abrirVentanas(arbolItem, tabBox, contenido, tab, tabs);
		}
	}
}
