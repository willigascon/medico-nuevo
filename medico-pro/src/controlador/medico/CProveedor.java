package controlador.medico;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import modelo.medico.consulta.ConsultaExamen;
import modelo.medico.consulta.ConsultaServicioExterno;
import modelo.medico.maestro.Examen;
import modelo.medico.maestro.Proveedor;
import modelo.medico.maestro.ProveedorExamen;
import modelo.medico.maestro.ProveedorServicio;
import modelo.medico.maestro.ServicioExterno;
import modelo.organizacion.Ciudad;
import modelo.security.Arbol;

import org.zkoss.util.media.Media;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.event.UploadEvent;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.A;
import org.zkoss.zul.Button;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Comboitem;
import org.zkoss.zul.Div;
import org.zkoss.zul.Doublespinner;
import org.zkoss.zul.Filedownload;
import org.zkoss.zul.Include;
import org.zkoss.zul.Label;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Spinner;
import org.zkoss.zul.Tab;
import org.zkoss.zul.Tabbox;
import org.zkoss.zul.Textbox;

import com.csvreader.CsvReader;

import componente.Botonera;
import componente.Buscar;
import componente.Catalogo;
import componente.Mensaje;
import componente.Validador;
import controlador.security.CArbol;
import controlador.utils.CGenerico;

public class CProveedor extends CGenerico {

	private static final long serialVersionUID = -6178756189105805846L;

	@Wire
	private Listbox ltbEstudios;
	@Wire
	private Listbox ltbEstudiosAgregados;
	@Wire
	private Listbox ltbExamen;
	@Wire
	private Listbox ltbExamenesAgregados;
	@Wire
	private Div divProveedor;
	@Wire
	private Div botoneraProveedor;
	@Wire
	private Div catalogoProveedor;
	@Wire
	private Textbox txtNombreProveedor;
	@Wire
	private Textbox txtTelefonoProveedor;
	@Wire
	private Textbox txtDireccionProveedor;
	@Wire
	private Button btnBuscarProveedor;
	@Wire
	private Combobox cmbCiudadProveedor;
	@Wire
	private Textbox txtBuscadorEstudio;
	@Wire
	private Textbox txtBuscadorExamen;
	@Wire
	private Tab tabBasicos;

	private CArbol cArbol = new CArbol();
	long id = 0;
	Catalogo<Proveedor> catalogo;
	Buscar<Examen> buscadorExamen;
	Buscar<ServicioExterno> buscadorEstudio;
	List<Examen> examenesDisponibles = new ArrayList<Examen>();
	List<ProveedorExamen> examenesUsados = new ArrayList<ProveedorExamen>();
	List<ServicioExterno> estudiosDisponibles = new ArrayList<ServicioExterno>();
	List<ProveedorServicio> estudiosUsados = new ArrayList<ProveedorServicio>();

	@Override
	public void inicializar() throws IOException {
		contenido = (Include) divProveedor.getParent();
		Tabbox tabox = (Tabbox) divProveedor.getParent().getParent()
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
		llenarComboCiudad();
		llenarListaExamenes(null);
		llenarListaEstudios(null);
		buscarExamen();
		buscarEstudio();
		Botonera botonera = new Botonera() {

			@Override
			public void salir() {
				cerrarVentana(divProveedor, titulo, tabs);
			}

			@Override
			public void limpiar() {
				txtDireccionProveedor.setValue("");
				txtNombreProveedor.setValue("");
				txtTelefonoProveedor.setValue("");
				cmbCiudadProveedor.setValue("");
				cmbCiudadProveedor.setPlaceholder("Seleccione una Ciudad");
				id = 0;
				ltbEstudios.getItems().clear();
				ltbEstudiosAgregados.getItems().clear();
				llenarListaEstudios(null);
				estudiosDisponibles.clear();
				estudiosUsados.clear();
				ltbExamen.getItems().clear();
				ltbExamenesAgregados.getItems().clear();
				llenarListaExamenes(null);
				examenesDisponibles.clear();
				examenesUsados.clear();
				tabBasicos.setSelected(true);
				limpiarColores(txtDireccionProveedor, txtNombreProveedor,
						txtTelefonoProveedor, cmbCiudadProveedor);
			}

			@Override
			public void guardar() {
				if (validar()) {
					List<ProveedorServicio> listaEstudios = new ArrayList<ProveedorServicio>();
					List<ProveedorExamen> listaExamen = new ArrayList<ProveedorExamen>();

					/* Verifica los estudios asociados */
					boolean campoNuloEstudio = false;
					ltbEstudiosAgregados.renderAll();
					for (int i = 0; i < ltbEstudiosAgregados.getItemCount(); i++) {
						Listitem listItem = ltbEstudiosAgregados
								.getItemAtIndex(i);
						Double costo = ((Doublespinner) ((listItem
								.getChildren().get(1))).getFirstChild())
								.getValue();
						long idEstudio = ((Spinner) ((listItem.getChildren()
								.get(2))).getFirstChild()).getValue();
						if (String.valueOf(idEstudio) == "" || costo == null) {
							campoNuloEstudio = true;
						} else {
							ServicioExterno servicioExterno = servicioServicioExterno
									.buscar(idEstudio);
							ProveedorServicio proveedorServicio = new ProveedorServicio(
									null, servicioExterno, costo);
							listaEstudios.add(proveedorServicio);
						}
					}

					/* Verifica los examenes asociados */
					boolean campoNuloExamen = false;
					ltbExamenesAgregados.renderAll();
					for (int i = 0; i < ltbExamenesAgregados.getItemCount(); i++) {
						Listitem listItem = ltbExamenesAgregados
								.getItemAtIndex(i);
						Double costo = ((Doublespinner) ((listItem
								.getChildren().get(1))).getFirstChild())
								.getValue();
						long idExamen = ((Spinner) ((listItem.getChildren()
								.get(2))).getFirstChild()).getValue();
						if (String.valueOf(idExamen) == "" || costo == null) {
							campoNuloExamen = true;
						} else {
							Examen examen = servicioExamen.buscar(idExamen);
							ProveedorExamen proveedorExamen = new ProveedorExamen(
									null, examen, costo);
							listaExamen.add(proveedorExamen);
						}
					}
					if (!campoNuloEstudio && !campoNuloExamen) {
						String nombre, direccion, telefono;
						nombre = txtNombreProveedor.getValue();
						direccion = txtDireccionProveedor.getValue();
						telefono = txtTelefonoProveedor.getValue();
						Ciudad ciudad = servicioCiudad.buscar(Long
								.parseLong(cmbCiudadProveedor.getSelectedItem()
										.getContext()));
						Double costo = 0.0;
						Proveedor proveedor = new Proveedor(id, direccion,
								nombre, telefono, fechaHora, horaAuditoria,
								nombreUsuarioSesion(), ciudad, costo);
						servicioProveedor.guardar(proveedor);

						if (id != 0)
							proveedor = servicioProveedor.buscar(id);
						else
							proveedor = servicioProveedor.buscarUltimo();

						List<ProveedorServicio> estudios = servicioProveedorServicio
								.buscarEstudiosUsados(proveedor);
						if (!estudios.isEmpty())
							servicioProveedorServicio.eliminar(estudios);
						for (int i = 0; i < listaEstudios.size(); i++) {
							listaEstudios.get(i).setProveedor(proveedor);
						}
						servicioProveedorServicio.guardar(listaEstudios);

						List<ProveedorExamen> examenes = servicioProveedorExamen
								.buscarExamenesUsados(proveedor);
						if (!examenes.isEmpty())
							servicioProveedorExamen.eliminar(examenes);
						for (int i = 0; i < listaExamen.size(); i++) {
							listaExamen.get(i).setProveedor(proveedor);
						}
						servicioProveedorExamen.guardar(listaExamen);
						limpiar();
						Mensaje.mensajeInformacion(Mensaje.guardado);
					} else
						Mensaje.mensajeError(Mensaje.listaVacia);
				}
			}

			@Override
			public void eliminar() {
				if (id != 0) {
					Messagebox.show("�Esta Seguro de Eliminar el Proveedor?",
							"Alerta", Messagebox.OK | Messagebox.CANCEL,
							Messagebox.QUESTION,
							new org.zkoss.zk.ui.event.EventListener<Event>() {
								public void onEvent(Event evt)
										throws InterruptedException {
									if (evt.getName().equals("onOK")) {
										Proveedor proveedor = servicioProveedor
												.buscar(id);
										List<ConsultaServicioExterno> consultas1 = servicioConsultaServicioExterno
												.buscarPorProveedor(proveedor);
										List<ConsultaExamen> consultas2 = servicioConsultaExamen
												.buscarPorProveedor(proveedor);
										if (!consultas1.isEmpty()
												|| !consultas2.isEmpty())
											Mensaje.mensajeError(Mensaje.noEliminar);
										else {
											servicioProveedor
													.eliminar(proveedor);
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
		botoneraProveedor.appendChild(botonera);
	}

	/* Llena el combo de Ciudades cada vez que se abre */
	@Listen("onOpen = #cmbCiudadProveedor")
	public void llenarComboCiudad() {
		List<Ciudad> ciudades = servicioCiudad.buscarTodas();
		cmbCiudadProveedor.setModel(new ListModelList<Ciudad>(ciudades));
	}

	/* Permite validar que todos los campos esten completos */
	public boolean validar() {
		if (txtDireccionProveedor.getText().compareTo("") == 0
				|| txtNombreProveedor.getText().compareTo("") == 0
				|| txtTelefonoProveedor.getText().compareTo("") == 0
				|| cmbCiudadProveedor.getText().compareTo("") == 0) {
			aplicarColores(txtDireccionProveedor, txtNombreProveedor,
					txtTelefonoProveedor, cmbCiudadProveedor);
			Mensaje.mensajeError(Mensaje.camposVacios);
			return false;
		} else {
			if (!Validador.validarTelefono(txtTelefonoProveedor.getValue())) {
				Mensaje.mensajeError(Mensaje.telefonoInvalido);
				return false;
			} else
				return true;
		}
	}

	/* Muestra el catalogo de los servicios externos */
	@Listen("onClick = #btnBuscarProveedor")
	public void mostrarCatalogo() {
		final List<Proveedor> proveedores = servicioProveedor.buscarTodos();
		catalogo = new Catalogo<Proveedor>(catalogoProveedor,
				"Catalogo de Proveedores", proveedores, false, "Nombre",
				"Direccion", "Telefono", "Ciudad") {

			@Override
			protected List<Proveedor> buscar(String valor, String combo) {

				switch (combo) {
				case "Nombre":
					return servicioProveedor.filtroNombre(valor);
				case "Direccion":
					return servicioProveedor.filtroDireccion(valor);
				case "Telefono":
					return servicioProveedor.filtroTelefono(valor);
				case "Ciudad":
					return servicioProveedor.filtroCiudad(valor);
				default:
					return proveedores;
				}
			}

			@Override
			protected String[] crearRegistros(Proveedor objeto) {
				String[] registros = new String[4];
				registros[0] = objeto.getNombre();
				registros[1] = objeto.getDireccion();
				registros[2] = objeto.getTelefono();
				registros[3] = objeto.getCiudad().getNombre();
				return registros;
			}

		};
		catalogo.setParent(catalogoProveedor);
		catalogo.doModal();
	}

	/* Valida el numero telefonico */
	@Listen("onChange = #txtTelefonoProveedor")
	public void validarTelefono() {
		if (!Validador.validarTelefono(txtTelefonoProveedor.getValue())) {
			Mensaje.mensajeAlerta(Mensaje.telefonoInvalido);
		}
	}

	/* Permite la seleccion de un item del catalogo */
	@Listen("onSeleccion = #catalogoProveedor")
	public void seleccinar() {
		Proveedor proveedor = catalogo.objetoSeleccionadoDelCatalogo();
		llenarCampos(proveedor);
		catalogo.setParent(null);
	}

	/* Busca si existe un servicio externo con el mismo nombre escrito */
	@Listen("onChange = #txtNombreProveedor")
	public void buscarPorNombre() {
		Proveedor proveedor = servicioProveedor
				.buscarPorNombre(txtNombreProveedor.getValue());
		if (proveedor != null)
			llenarCampos(proveedor);
	}

	/* LLena los campos del formulario dado un servicio externo */
	private void llenarCampos(Proveedor proveedor) {
		txtDireccionProveedor.setValue(proveedor.getDireccion());
		txtNombreProveedor.setValue(proveedor.getNombre());
		txtTelefonoProveedor.setValue(proveedor.getTelefono());
		cmbCiudadProveedor.setValue(proveedor.getCiudad().getNombre());
		Comboitem item = cmbCiudadProveedor.appendItem(proveedor.getCiudad()
				.getNombre());
		item.setContext(String.valueOf(proveedor.getCiudad().getIdCiudad()));
		cmbCiudadProveedor.setSelectedItem(item);
		llenarListaEstudios(proveedor);
		llenarListaExamenes(proveedor);
		id = proveedor.getIdProveedor();
	}

	/* Abre la vista de Ciudad */
	@Listen("onClick = #btnAbrirCiudad")
	public void abrirCiudad() {
		List<Arbol> arboles = servicioArbol.buscarPorNombreArbol("Ciudad");
		final HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("titulo", "Ciudad");
		Sessions.getCurrent().setAttribute("itemsCatalogo", map);
		if (!arboles.isEmpty()) {
			Arbol arbolItem = arboles.get(0);
			cArbol.abrirVentanas(arbolItem, tabBox, contenido, tab, tabs);
		}
	}

	/* Cosas relacionadas con los grid */
	/* Llena la lista al iniciar con todas las presentaciones existentes */
	private void llenarListaEstudios(Proveedor proveedor) {
		if (proveedor == null) {
			estudiosDisponibles = servicioServicioExterno.buscarTodas();
			ltbEstudios.setModel(new ListModelList<ServicioExterno>(
					estudiosDisponibles));
		} else {
			estudiosUsados = servicioProveedorServicio
					.buscarEstudiosUsados(proveedor);
			ltbEstudiosAgregados.setModel(new ListModelList<ProveedorServicio>(
					estudiosUsados));
			if (!estudiosUsados.isEmpty()) {
				List<Long> ids = new ArrayList<Long>();
				for (int i = 0; i < estudiosUsados.size(); i++) {
					long id = estudiosUsados.get(i).getServicioExterno()
							.getIdServicioExterno();
					ids.add(id);
				}
				estudiosDisponibles = servicioServicioExterno
						.buscarEstudiosDisponibles(ids);
				ltbEstudios.setModel(new ListModelList<ServicioExterno>(
						estudiosDisponibles));
			}
		}
		ltbEstudiosAgregados.setMultiple(false);
		ltbEstudiosAgregados.setCheckmark(false);
		ltbEstudiosAgregados.setMultiple(true);
		ltbEstudiosAgregados.setCheckmark(true);

		ltbEstudios.setMultiple(false);
		ltbEstudios.setCheckmark(false);
		ltbEstudios.setMultiple(true);
		ltbEstudios.setCheckmark(true);
	}

	/* Llena la lista al iniciar con todas las presentaciones existentes */
	private void llenarListaExamenes(Proveedor proveedor) {
		if (proveedor == null) {
			examenesDisponibles = servicioExamen.buscarTodos();
			ltbExamen.setModel(new ListModelList<Examen>(examenesDisponibles));
		} else {
			examenesUsados = servicioProveedorExamen
					.buscarExamenesUsados(proveedor);
			ltbExamenesAgregados.setModel(new ListModelList<ProveedorExamen>(
					examenesUsados));
			if (!examenesUsados.isEmpty()) {
				List<Long> ids = new ArrayList<Long>();
				for (int i = 0; i < examenesUsados.size(); i++) {
					long id = examenesUsados.get(i).getExamen().getIdExamen();
					ids.add(id);
				}
				examenesDisponibles = servicioExamen
						.buscarExamenesDisponibles(ids);
				ltbExamen.setModel(new ListModelList<Examen>(
						examenesDisponibles));
			}
		}
		ltbExamenesAgregados.setMultiple(false);
		ltbExamenesAgregados.setCheckmark(false);
		ltbExamenesAgregados.setMultiple(true);
		ltbExamenesAgregados.setCheckmark(true);

		ltbExamen.setMultiple(false);
		ltbExamen.setCheckmark(false);
		ltbExamen.setMultiple(true);
		ltbExamen.setCheckmark(true);
	}

	/*
	 * Permite mover uno o varios elementos seleccionados desde la lista de la
	 * izquierda a la lista de la derecha (ESTUDIO)
	 */
	@Listen("onClick = #pasar1")
	public void moverDerecha() {
		List<Listitem> listitemEliminar = new ArrayList<Listitem>();
		List<Listitem> listItem = ltbEstudios.getItems();
		if (listItem.size() != 0) {
			for (int i = 0; i < listItem.size(); i++) {
				if (listItem.get(i).isSelected()) {
					ServicioExterno servicio = listItem.get(i).getValue();
					estudiosDisponibles.remove(servicio);
					ProveedorServicio proveedorServicio = new ProveedorServicio();
					proveedorServicio.setServicioExterno(servicio);
					estudiosUsados.clear();
					ltbEstudiosAgregados.renderAll();
					for (int j = 0; j < ltbEstudiosAgregados.getItemCount(); j++) {
						Listitem listItemj = ltbEstudiosAgregados
								.getItemAtIndex(j);
						double costo = ((Doublespinner) ((listItemj
								.getChildren().get(1))).getFirstChild())
								.getValue();
						long idEstudio = ((Spinner) ((listItemj.getChildren()
								.get(2))).getFirstChild()).getValue();
						ServicioExterno servicioExterno = servicioServicioExterno
								.buscar(idEstudio);
						ProveedorServicio proveedorServicioj = new ProveedorServicio(
								null, servicioExterno, costo);
						estudiosUsados.add(proveedorServicioj);
					}
					estudiosUsados.add(proveedorServicio);
					ltbEstudiosAgregados
							.setModel(new ListModelList<ProveedorServicio>(
									estudiosUsados));
					ltbEstudiosAgregados.renderAll();
					listitemEliminar.add(listItem.get(i));
				}
			}
		}
		for (int i = 0; i < listitemEliminar.size(); i++) {
			ltbEstudios.renderAll();
			ltbEstudios.removeItemAt(listitemEliminar.get(i).getIndex());
		}
		ltbEstudiosAgregados.setMultiple(false);
		ltbEstudiosAgregados.setCheckmark(false);
		ltbEstudiosAgregados.setMultiple(true);
		ltbEstudiosAgregados.setCheckmark(true);
	}

	/*
	 * Permite mover uno o varios elementos seleccionados desde la lista de la
	 * derecha a la lista de la izquierda
	 */
	@Listen("onClick = #pasar2")
	public void moverIzquierda() {
		List<Listitem> listitemEliminar = new ArrayList<Listitem>();
		List<Listitem> listItem2 = ltbEstudiosAgregados.getItems();
		if (listItem2.size() != 0) {
			for (int i = 0; i < listItem2.size(); i++) {
				if (listItem2.get(i).isSelected()) {
					ProveedorServicio proveedorServicio = listItem2.get(i)
							.getValue();
					estudiosUsados.remove(proveedorServicio);
					estudiosDisponibles.add(proveedorServicio
							.getServicioExterno());
					ltbEstudios.setModel(new ListModelList<ServicioExterno>(
							estudiosDisponibles));
					listitemEliminar.add(listItem2.get(i));
				}
			}
		}
		for (int i = 0; i < listitemEliminar.size(); i++) {
			ltbEstudiosAgregados.renderAll();
			ltbEstudiosAgregados.removeItemAt(listitemEliminar.get(i)
					.getIndex());
		}
		ltbEstudios.setMultiple(false);
		ltbEstudios.setCheckmark(false);
		ltbEstudios.setMultiple(true);
		ltbEstudios.setCheckmark(true);
	}

	public void buscarEstudio() {
		buscadorEstudio = new Buscar<ServicioExterno>(ltbEstudios,
				txtBuscadorEstudio) {
			@Override
			protected List<ServicioExterno> buscar(String valor) {
				List<ServicioExterno> estudiosFiltrados = new ArrayList<ServicioExterno>();
				List<ServicioExterno> estudios = servicioServicioExterno
						.filtroNombre(valor);
				for (int i = 0; i < estudiosDisponibles.size(); i++) {
					ServicioExterno estudio = estudiosDisponibles.get(i);
					for (int j = 0; j < estudios.size(); j++) {
						if (estudio.getIdServicioExterno() == estudios.get(j)
								.getIdServicioExterno())
							estudiosFiltrados.add(estudio);
					}
				}
				return estudiosFiltrados;
			}
		};
	}

	/*
	 * Permite mover uno o varios elementos seleccionados desde la lista de la
	 * izquierda a la lista de la derecha (EXAMEN)
	 */
	@Listen("onClick = #pasar11")
	public void moverDerecha2() {
		List<Listitem> listitemEliminar = new ArrayList<Listitem>();
		List<Listitem> listItem = ltbExamen.getItems();
		if (listItem.size() != 0) {
			for (int i = 0; i < listItem.size(); i++) {
				if (listItem.get(i).isSelected()) {
					Examen examen = listItem.get(i).getValue();
					examenesDisponibles.remove(examen);
					ProveedorExamen proveedorExamen = new ProveedorExamen();
					proveedorExamen.setExamen(examen);
					ltbExamenesAgregados.renderAll();
					examenesUsados.clear();
					for (int j = 0; j < ltbExamenesAgregados.getItemCount(); j++) {
						Listitem listItemj = ltbExamenesAgregados
								.getItemAtIndex(j);
						double costo = ((Doublespinner) ((listItemj
								.getChildren().get(1))).getFirstChild())
								.getValue();
						long idExamen = ((Spinner) ((listItemj.getChildren()
								.get(2))).getFirstChild()).getValue();
						Examen examenj = servicioExamen.buscar(idExamen);
						ProveedorExamen proveedorExamenj = new ProveedorExamen(
								null, examenj, costo);
						examenesUsados.add(proveedorExamenj);
					}
					examenesUsados.add(proveedorExamen);
					ltbExamenesAgregados
							.setModel(new ListModelList<ProveedorExamen>(
									examenesUsados));
					ltbExamenesAgregados.renderAll();
					listitemEliminar.add(listItem.get(i));
				}
			}
		}
		for (int i = 0; i < listitemEliminar.size(); i++) {
			ltbExamen.renderAll();
			ltbExamen.removeItemAt(listitemEliminar.get(i).getIndex());
		}
		ltbExamenesAgregados.setMultiple(false);
		ltbExamenesAgregados.setCheckmark(false);
		ltbExamenesAgregados.setMultiple(true);
		ltbExamenesAgregados.setCheckmark(true);
	}

	/*
	 * Permite mover uno o varios elementos seleccionados desde la lista de la
	 * derecha a la lista de la izquierda
	 */
	@Listen("onClick = #pasar22")
	public void moverIzquierda2() {
		List<Listitem> listitemEliminar = new ArrayList<Listitem>();
		List<Listitem> listItem2 = ltbExamenesAgregados.getItems();
		if (listItem2.size() != 0) {
			for (int i = 0; i < listItem2.size(); i++) {
				if (listItem2.get(i).isSelected()) {
					ProveedorExamen proveedorExamen = listItem2.get(i)
							.getValue();
					examenesUsados.remove(proveedorExamen);
					examenesDisponibles.add(proveedorExamen.getExamen());
					ltbExamen.setModel(new ListModelList<Examen>(
							examenesDisponibles));
					listitemEliminar.add(listItem2.get(i));
				}
			}
		}
		for (int i = 0; i < listitemEliminar.size(); i++) {
			ltbExamenesAgregados.renderAll();
			ltbExamenesAgregados.removeItemAt(listitemEliminar.get(i)
					.getIndex());
		}
		ltbExamen.setMultiple(false);
		ltbExamen.setCheckmark(false);
		ltbExamen.setMultiple(true);
		ltbExamen.setCheckmark(true);
	}

	public void buscarExamen() {
		buscadorExamen = new Buscar<Examen>(ltbExamen, txtBuscadorExamen) {
			@Override
			protected List<Examen> buscar(String valor) {
				List<Examen> examenesFiltrados = new ArrayList<Examen>();
				List<Examen> examenes = servicioExamen.filtroNombre(valor);
				for (int i = 0; i < examenesDisponibles.size(); i++) {
					Examen examen = examenesDisponibles.get(i);
					for (int j = 0; j < examenes.size(); j++) {
						if (examen.getIdExamen() == examenes.get(j)
								.getIdExamen())
							examenesFiltrados.add(examen);
					}
				}
				return examenesFiltrados;
			}
		};
	}

	/* Abre la vista de Estudios */
	@Listen("onClick = #btnAbrirEstudio")
	public void abrirEstudio() {
		final HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("id", "proveedor");
		map.put("lista", estudiosDisponibles);
		map.put("listbox", ltbEstudios);
		map.put("titulo", "Estudios");
		Sessions.getCurrent().setAttribute("itemsCatalogo", map);
		List<Arbol> arboles = servicioArbol.buscarPorNombreArbol("Estudios");
		if (!arboles.isEmpty()) {
			Arbol arbolItem = arboles.get(0);
			cArbol.abrirVentanas(arbolItem, tabBox, contenido, tab, tabs);
		}
	}

	/* Abre la vista de Examenes */
	@Listen("onClick = #btnAbrirExamen")
	public void abrirExamen() {
		final HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("id", "proveedor");
		map.put("lista", examenesDisponibles);
		map.put("listbox", ltbExamen);
		map.put("titulo", "Examen");
		Sessions.getCurrent().setAttribute("itemsCatalogo", map);
		List<Arbol> arboles = servicioArbol.buscarPorNombreArbol("Examen");
		if (!arboles.isEmpty()) {
			Arbol arbolItem = arboles.get(0);
			cArbol.abrirVentanas(arbolItem, tabBox, contenido, tab, tabs);
		}
	}

	public void recibirExamen(List<Examen> lista, Listbox l) {
		ltbExamen = l;
		examenesDisponibles = lista;
		ltbExamen.setModel(new ListModelList<Examen>(examenesDisponibles));
		ltbExamen.setMultiple(false);
		ltbExamen.setCheckmark(false);
		ltbExamen.setMultiple(true);
		ltbExamen.setCheckmark(true);
	}

	public void recibirServicio(List<ServicioExterno> lista, Listbox l) {
		ltbEstudios = l;
		estudiosDisponibles = lista;
		ltbEstudios.setModel(new ListModelList<ServicioExterno>(
				estudiosDisponibles));
		ltbEstudios.setMultiple(false);
		ltbEstudios.setCheckmark(false);
		ltbEstudios.setMultiple(true);
		ltbEstudios.setCheckmark(true);
	}
}
