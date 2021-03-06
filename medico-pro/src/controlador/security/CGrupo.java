package controlador.security;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import modelo.security.Arbol;
import modelo.security.Grupo;
import modelo.security.MArbol;
import modelo.security.Nodos;
import modelo.security.Usuario;

import org.zkoss.zk.ui.Sessions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.SelectEvent;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Div;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Tab;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Tree;
import org.zkoss.zul.TreeModel;
import org.zkoss.zul.Treecell;
import org.zkoss.zul.Treechildren;
import org.zkoss.zul.Treeitem;

import componente.Botonera;
import componente.Catalogo;
import componente.Mensaje;

import controlador.utils.CGenerico;

public class CGrupo extends CGenerico {

	@Wire
	private Tree treeGrupo;
	@Wire
	private Textbox txtNombreGrupo;
	@Wire
	private Listbox ltbFuncionalidadesSeleccionados;
	@Wire
	private Div botoneraGrupo;
	@Wire
	private Div catalogoGrupo;
	@Wire
	private Div divGrupo;
	long id = 0;
	TreeModel _model;
	Catalogo<Grupo> catalogo;
	public static List<String> funcionalidades = new ArrayList<String>();

	private static final long serialVersionUID = 3771289676166008495L;

	private boolean usuario = false;
	private CUsuario cUsuario = new CUsuario();
	List<Grupo> grupoUsuario = new ArrayList<Grupo>();
	Listbox listas;

	@Override
	public void inicializar() throws IOException {
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
		
		treeGrupo.setModel(getModel());
		treeGrupo.setCheckmark(true);
		treeGrupo.setMultiple(true);
		ltbFuncionalidadesSeleccionados.getItems().clear();
		HashMap<String, Object> map = (HashMap<String, Object>) Sessions
				.getCurrent().getAttribute("itemsCatalogo");
		if (map != null) {
			if (map.get("id") != null) {
				usuario = true;
				grupoUsuario = (List<Grupo>) map.get("lista");
				listas = (Listbox) map.get("listbox");
				titulo = (String) map.get("titulo");
				map.clear();
				map = null;
			}
		}
		Botonera botonera = new Botonera() {

			@Override
			public void salir() {
				cerrarVentana(divGrupo, titulo, tabs);
			}

			@Override
			public void limpiar() {
				metodoLimpiar();
			}

			@Override
			public void guardar() {
				if (validar()) {
					List<Arbol> listaArbol = servicioArbol.listarArbol();
					Set<Arbol> arboles = new HashSet<Arbol>();
					Treechildren treeChildren = treeGrupo.getTreechildren();
					Collection<Treeitem> lista = treeChildren.getItems();
					String nombreGrupo = txtNombreGrupo.getValue();
					for (int i = 0; i < listaArbol.size(); i++) {
						for (Iterator<?> iterator = lista.iterator(); iterator
								.hasNext();) {
							Treeitem treeitem = (Treeitem) iterator.next();
							if (listaArbol.get(i).getNombre()
									.equals(treeitem.getLabel())) {
								if (treeitem.isSelected()) {

									Arbol arbol = listaArbol.get(i);
									arboles.add(arbol);
								}
							}
						}
					}
					Boolean estatus = true;
					String nombre = txtNombreGrupo.getValue();
					Grupo grupo1 = new Grupo(id, estatus, nombre, arboles);
					servicioGrupo.guardarGrupo(grupo1);
					if (usuario) {
						if (id != 0)
							grupo1 = servicioGrupo.buscarGrupo(id);
						else {
							grupo1 = servicioGrupo.buscarUltimo();
							grupoUsuario.add(grupo1);
						}
						cUsuario.recibirGrupo(grupoUsuario, listas);
					}
					msj.mensajeInformacion(Mensaje.guardado);
					limpiar();
				}
			}

			@Override
			public void eliminar() {
				if (id != 0) {
					Messagebox.show("�Esta Seguro de Eliminar el Grupo?",
							"Alerta", Messagebox.OK | Messagebox.CANCEL,
							Messagebox.QUESTION,
							new org.zkoss.zk.ui.event.EventListener<Event>() {
								public void onEvent(Event evt)
										throws InterruptedException {
									if (evt.getName().equals("onOK")) {
										Grupo grupo = servicioGrupo
												.buscarGrupo(id);

										List<Usuario> usuario = servicioUsuario
												.buscarPorGrupo(grupo);
										if (!usuario.isEmpty())
											msj.mensajeError(Mensaje.noEliminar);
										else {
											servicioGrupo.eliminar(grupo);
											limpiar();
											msj.mensajeInformacion(Mensaje.eliminado);
										}
									}
								}
							});
				} else
					msj.mensajeAlerta(Mensaje.noSeleccionoRegistro);
			}
		};
		botoneraGrupo.appendChild(botonera);
	}

	public TreeModel getModel() {
		if (_model == null) {
			_model = new MArbol(getFooRoot());
		}
		return _model;
	}

	private Nodos getFooRoot() {
		Nodos root = new Nodos(null, 0, "");
		List<Arbol> arboles = servicioArbol.listarArbol();
		List<Long> idsPadre = new ArrayList<Long>();
		List<Nodos> nodos = new ArrayList<Nodos>();
		return crearArbol(root, nodos, arboles, 0, idsPadre);
	}

	private Nodos crearArbol(Nodos roote, List<Nodos> nodos,
			List<Arbol> arboles, int i, List<Long> idsPadre) {
		for (int z = 0; z < arboles.size(); z++) {
			Nodos oneLevelNode = new Nodos(null, 0, "");
			Nodos two = new Nodos(null, 0, "");
			if (arboles.get(z).getPadre() == 0) {
				oneLevelNode = new Nodos(roote, (int) arboles.get(z)
						.getIdArbol(), arboles.get(z).getNombre());
				roote.appendChild(oneLevelNode);
				idsPadre.add(arboles.get(z).getIdArbol());
				nodos.add(oneLevelNode);
			} else {
				for (int j = 0; j < idsPadre.size(); j++) {
					if (idsPadre.get(j) == arboles.get(z).getPadre()) {
						oneLevelNode = nodos.get(j);
						two = new Nodos(oneLevelNode, (int) arboles.get(z)
								.getIdArbol(), arboles.get(z).getNombre());
						oneLevelNode.appendChild(two);
						idsPadre.add(arboles.get(z).getIdArbol());
						nodos.add(two);
					}
				}
			}
		}
		return roote;
	}

	public boolean validarNodoHijo(SelectEvent<Treeitem, String> event) {
		Treeitem itemSeleccionado = event.getReference();
		if (itemSeleccionado.isSelected()) {
			itemSeleccionado.setOpen(true);
			Treecell celda = (Treecell) itemSeleccionado.getChildren().get(0)
					.getChildren().get(0);
			long item = Long.valueOf(celda.getContext());
			if (itemSeleccionado.getChildren().size() > 1) {
				Treechildren treeChildren = (Treechildren) itemSeleccionado
						.getChildren().get(1);
				Collection<Treeitem> listaItems = treeChildren.getItems();
				for (Iterator<?> iterator = listaItems.iterator(); iterator
						.hasNext();) {
					Treeitem itemTree = (Treeitem) iterator.next();
					celda = (Treecell) itemTree.getChildren().get(0)
							.getChildren().get(0);
					long itemHijo = Long.valueOf(celda.getContext());
					Arbol arbol = servicioArbol.buscar(itemHijo);
					if (item == arbol.getPadre()) {
						itemTree.setSelected(true);
						// for (Iterator<?> iteratortra = listaItems.iterator();
						// iterator
						// .hasNext();) {
						// Treeitem itemTree2 = (Treeitem) iteratortra.next();
						// celda = (Treecell) itemTree2.getChildren().get(0)
						// .getChildren().get(0);
						// long itemHijo2 = Long.valueOf(celda.getContext());
						// Arbol arbol2 = servicioArbol.buscarPorId(itemHijo2);
						// if (itemHijo == arbol2.getPadre()) {
						// itemTree2.setSelected(true);
						//
						// }
						// }
					}
					// Treeitem itemSeleccionado = event.getReference();
					// Arbol arbol = servicioArbol.buscarPorId(item);
					// long padre = arbol.getIdArbol();
					// boolean encontrado = false;
					// List<Arbol> listaArbol = servicioArbol.listarArbol();
					// for (int i = 0; i < listaArbol.size(); i++) {
					// if (padre == listaArbol.get(i).getPadre()) {
					//
					// if (itemSeleccionado.isSelected()) {
					// msj.mensajeAlerta(Mensaje.seleccioneFuncionalidades);
					// itemSeleccionado.setSelected(false);
					// i = listaArbol.size();
					// encontrado = true;
					// } else {
					// msj.mensajeAlerta(Mensaje.seleccioneFuncionalidades);
					// itemSeleccionado.setSelected(true);
					// i = listaArbol.size();
					// encontrado = true;
					// }
					// return encontrado;
					// }
					// }
				}

			}
		} else {
			itemSeleccionado.setOpen(true);
			Treecell celda = (Treecell) itemSeleccionado.getChildren().get(0)
					.getChildren().get(0);
			long item = Long.valueOf(celda.getContext());
			if (itemSeleccionado.getChildren().size() > 1) {
				Treechildren treeChildren = (Treechildren) itemSeleccionado
						.getChildren().get(1);
				Collection<Treeitem> listaItems = treeChildren.getItems();
				for (Iterator<?> iterator = listaItems.iterator(); iterator
						.hasNext();) {
					Treeitem itemTree = (Treeitem) iterator.next();
					celda = (Treecell) itemTree.getChildren().get(0)
							.getChildren().get(0);
					long itemHijo = Long.valueOf(celda.getContext());
					Arbol arbol = servicioArbol.buscar(itemHijo);
					if (item == arbol.getPadre()) {
						itemTree.setSelected(false);
						// for (Iterator<?> iteratortra = listaItems.iterator();
						// iterator
						// .hasNext();) {
						// Treeitem itemTree2 = (Treeitem) iteratortra.next();
						// celda = (Treecell) itemTree2.getChildren().get(0)
						// .getChildren().get(0);
						// long itemHijo2 = Long.valueOf(celda.getContext());
						// Arbol arbol2 = servicioArbol.buscarPorId(itemHijo2);
						// if (itemHijo == arbol2.getPadre()) {
						// itemTree2.setSelected(true);
						//
						// }
						// }
					}
				}
			}
		}
		return false;
	}

	public void llenarFuncionalidadesSeleccionadas() {
		Grupo grupo = servicioGrupo.buscarGrupo(id);
		List<Arbol> listaArbol = servicioArbol.buscarporGrupo(grupo);
		int ItemEncontrado = 0;
		for (int i = 0; i < listaArbol.size(); i++) {
			long padre = listaArbol.get(i).getIdArbol();
			ItemEncontrado = 0;
			for (int j = 0; j < listaArbol.size(); j++) {
				long hijo = listaArbol.get(j).getPadre();
				if (padre == hijo) {
					ItemEncontrado = 1;
					j = listaArbol.size();
				}
			}
			if (ItemEncontrado == 0) {
				funcionalidades.add(listaArbol.get(i).getNombre());
			}
		}
		ltbFuncionalidadesSeleccionados.setModel(new ListModelList<String>(
				funcionalidades));
	}

	@Listen("onSelect = #treeGrupo")
	public void selectedNode(SelectEvent<Treeitem, String> event) {
		if (!validarNodoHijo(event)) {
			List<Arbol> listaArbol2 = servicioArbol.listarArbol();
			Treechildren treeChildren = treeGrupo.getTreechildren();
			Collection<Treeitem> listaItems = treeChildren.getItems();
			Treeitem itemSeleccionado = event.getReference();
			Treecell celda = (Treecell) itemSeleccionado.getChildren().get(0)
					.getChildren().get(0);
			long idArbol = Long.valueOf(celda.getContext());
			List<Long> ids = new ArrayList<Long>();
			for (int o = 0; o < listaArbol2.size(); o++) {
				long id = listaArbol2.get(o).getIdArbol();
				ids.add(id);
			}
			Collections.sort(ids);
			List<Arbol> listaArbol = new ArrayList<Arbol>();
			for (int y = 0; y < ids.size(); y++) {
				listaArbol.add(servicioArbol.buscar(ids.get(y)));
			}
			String nombreItem = String.valueOf(itemSeleccionado.getLabel());
			if (itemSeleccionado.isSelected()) {
				funcionalidades.add(nombreItem);
				ltbFuncionalidadesSeleccionados
						.setModel(new ListModelList<String>(funcionalidades));
			} else {
				List<Listitem> listaFuncionalidadesSeleccionadas = ltbFuncionalidadesSeleccionados
						.getItems();
				for (int i = 0; i < listaFuncionalidadesSeleccionadas.size(); i++) {
					if (listaFuncionalidadesSeleccionadas.get(i).getLabel()
							.equals(nombreItem)) {
						ltbFuncionalidadesSeleccionados
								.removeItemAt(listaFuncionalidadesSeleccionadas
										.get(i).getIndex());
					}
				}
				funcionalidades.remove(nombreItem);
				ltbFuncionalidadesSeleccionados
						.setModel(new ListModelList<String>(funcionalidades));
			}
			Arbol arbolItem = servicioArbol.buscar(idArbol);
			listaArbol.remove(arbolItem);
			long temp = arbolItem.getPadre();
			long temp2 = 0;
			long temp3 = temp;
			boolean encontradoHermanoHijo = false;
			boolean encontradoHermanoPadre = false;
			for (int i = 0; i < listaArbol.size(); i++) {
				if (temp == listaArbol.get(i).getIdArbol()) {
					for (Iterator<?> iterator = listaItems.iterator(); iterator
							.hasNext();) {
						Treeitem item = (Treeitem) iterator.next();
						if (listaArbol.get(i).getNombre()
								.equals(item.getLabel())) {
							temp2 = listaArbol.get(i).getPadre();
							for (int j = 0; j < listaArbol.size(); j++) {
								if (temp3 == listaArbol.get(j).getPadre()) {
									for (Iterator<?> iterator2 = listaItems
											.iterator(); iterator2.hasNext();) {
										Treeitem item2 = (Treeitem) iterator2
												.next();
										if (listaArbol.get(j).getNombre()
												.equals(item2.getLabel())) {
											if (item2.isSelected()) {
												encontradoHermanoHijo = true;
											}
										}
									}
								}
								if (temp2 == listaArbol.get(j).getPadre()
										&& listaArbol.get(i).getIdArbol() != listaArbol
												.get(j).getIdArbol()) {
									for (Iterator<?> iterator2 = listaItems
											.iterator(); iterator2.hasNext();) {
										Treeitem item2 = (Treeitem) iterator2
												.next();
										if (listaArbol.get(j).getNombre()
												.equals(item2.getLabel())) {
											if (item2.isSelected()) {
												encontradoHermanoPadre = true;
											}
										}
									}
								}
							}
							if (!item.isSelected()) {
								item.setSelected(true);
							} else {
								if (!encontradoHermanoHijo
										&& !encontradoHermanoPadre) {
									item.setSelected(false);
								}
								if (!encontradoHermanoHijo
										&& encontradoHermanoPadre
										&& !itemSeleccionado.isSelected()) {
									item.setSelected(false);
									encontradoHermanoHijo = true;
									encontradoHermanoPadre = false;
								}
							}
						}
					}
					temp = listaArbol.get(i).getPadre();
					i = -1;
				}
			}
		}
	}

	@Listen("onClick = #btnCatalogoGrupo")
	public void buscarItem() {
		metodoLimpiar();
		List<Grupo> grupos = servicioGrupo.buscarTodos();
		catalogo = new Catalogo<Grupo>(catalogoGrupo, "Catalogo de Grupos",
				grupos, false,"Nombre") {
			@Override
			protected String[] crearRegistros(Grupo grupo) {
				String[] registros = new String[1];
				registros[0] = grupo.getNombre();
				return registros;
			}

			@Override
			protected List<Grupo> buscar(String valor, String combo) {
				if (combo.equals("Nombre"))
					return servicioGrupo.filtroNombre(valor);
				else
					return servicioGrupo.buscarTodos();
			}
		};
		catalogo.setParent(catalogoGrupo);
		catalogo.doModal();
	}

	@Listen("onSeleccion = #catalogoGrupo")
	public void seleccionGrupo() {
		Grupo grupo = catalogo.objetoSeleccionadoDelCatalogo();
		llenarCampos(grupo);
		catalogo.setParent(null);
	}

	public void llenarCampos(Grupo grupo) {
		txtNombreGrupo.setValue(grupo.getNombre());
		id = grupo.getIdGrupo();
		visualizarFuncionalidades();
	}

	/* Busca si existe un grupo con el mismo nombre escrito */
	@Listen("onChange = #txtNombreGrupo")
	public void buscarPorNombre() {
		Grupo grupo = servicioGrupo.buscarPorNombre(txtNombreGrupo.getValue());
		if (grupo != null)
			llenarCampos(grupo);
	}

	public void metodoLimpiar() {
		txtNombreGrupo.setValue("");
		txtNombreGrupo.setDisabled(false);
		limpiarColores(txtNombreGrupo);
		Treechildren treeChildren = treeGrupo.getTreechildren();
		Collection<Treeitem> lista = treeChildren.getItems();
		for (int i = 0; i < treeChildren.getItemCount(); i++) {
			for (Iterator<?> iterator = lista.iterator(); iterator.hasNext();) {
				Treeitem treeitem = (Treeitem) iterator.next();
				treeitem.setSelected(false);
			}
		}
		for (Iterator<?> iterator = lista.iterator(); iterator.hasNext();) {
			Treeitem treeitem = (Treeitem) iterator.next();
			if (treeitem.isOpen()) {
				treeitem.setOpen(false);
				lista = treeGrupo.getTreechildren().getItems();
				iterator = lista.iterator();
			}
		}
		id = 0;
		treeGrupo.setVisible(true);
		funcionalidades.clear();
		ltbFuncionalidadesSeleccionados.setModel(new ListModelList<String>(
				funcionalidades));
	}

	public void visualizarFuncionalidades() {
		llenarFuncionalidadesSeleccionadas();
		treeGrupo.setVisible(true);
		Treechildren treeChildren = treeGrupo.getTreechildren();
		Collection<Treeitem> lista = treeChildren.getItems();
		for (Iterator<?> iterator = lista.iterator(); iterator.hasNext();) {
			Treeitem treeitem = (Treeitem) iterator.next();
			if (!treeitem.isOpen()) {
				treeitem.setOpen(true);
				lista = treeChildren.getItems();
				iterator = lista.iterator();
			}
		}
		Grupo grupo = servicioGrupo.buscarGrupo(id);
		List<Arbol> listaArbol = servicioArbol.buscarporGrupo(grupo);
		for (int i = 0; i < listaArbol.size(); i++) {
			for (Iterator<?> iterator = lista.iterator(); iterator.hasNext();) {
				Treeitem treeitem = (Treeitem) iterator.next();
				if (listaArbol.get(i).getNombre().equals(treeitem.getLabel())) {
					treeitem.setSelected(true);
				}
			}
		}
		for (Iterator<?> iterator = lista.iterator(); iterator.hasNext();) {
			Treeitem treeitem = (Treeitem) iterator.next();
			if (treeitem.isOpen()) {
				treeitem.setOpen(false);
				lista = treeGrupo.getTreechildren().getItems();
				iterator = lista.iterator();
			}
		}
	}

	private boolean validar() {
		if (txtNombreGrupo.getValue().equals("")) {
			aplicarColores(txtNombreGrupo);
			msj.mensajeError(Mensaje.camposVacios);
			return false;
		}
		return true;
	}
}
