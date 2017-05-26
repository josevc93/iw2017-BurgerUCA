package com.proyecto;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.swing.JOptionPane;

import com.vaadin.data.Binder;
import com.vaadin.event.ShortcutAction;
import com.vaadin.server.FileResource;
import com.vaadin.server.Sizeable.Unit;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.spring.annotation.UIScope;
import com.vaadin.ui.Button;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.Grid;
import com.vaadin.ui.Grid.Column;
import com.vaadin.ui.Upload.Receiver;
import com.vaadin.ui.Upload.SucceededEvent;
import com.vaadin.ui.Upload.SucceededListener;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Image;
import com.vaadin.ui.ListSelect;
import com.vaadin.ui.NativeSelect;
import com.vaadin.ui.TextField;
import com.vaadin.ui.Upload;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;

@SuppressWarnings("serial")
@SpringComponent
@UIScope
public class MenuEditor extends VerticalLayout{
	
	private final MenuRepository repository;
	
	private final ProductRepository repoP;
	
	private final ProductMenuRepository repoPM;
	
	private final NativeSelect<String> productSelect;
	private final TextField productCantidad = new TextField("Cantidad");

	private Menu menu;
	private ProductMenu productMenu;
	
	private List<ProductMenu> pmList = new ArrayList<ProductMenu>();
	
	final Grid<ProductMenu> gridProdAct = new Grid<ProductMenu>(ProductMenu.class);
	
	TextField name = new TextField("Nombre");
	TextField price = new TextField("Precio");
	TextField menuImage = new TextField("Image");
	
	Button save = new Button("Guardar");
	Button cancel = new Button("Cancelar");
	Button delete = new Button("Eliminar Menu");
	CssLayout actions = new CssLayout(save, cancel, delete);
	
	Button newProduct = new Button("Añadir producto");
	Button deleteProduct = new Button("Eliminar");
	VerticalLayout actionsProducts = new VerticalLayout(gridProdAct, newProduct, deleteProduct);
	
	
	Binder<Menu> binder = new Binder<>(Menu.class);
	
	public MenuEditor(MenuRepository repository, ProductRepository repoProduct
						, ProductMenuRepository repoPM){
		this.repoP = repoProduct;
		this.repository = repository;
		this.repoPM = repoPM;
		
		final Image image = new Image();
		
		class ImageUploader implements Receiver, SucceededListener {
			public File file;
			public OutputStream receiveUpload(String filename, String mimeType){
				FileOutputStream fos = null;
				try{
					file = new File("src/img/"+filename);
					fos = new FileOutputStream(file);
				}catch(final java.io.FileNotFoundException e){
					JOptionPane.showMessageDialog(null, "Error al subir el fichero");
					return null;
				}
				return fos;
			}
	
			public void uploadSucceeded(SucceededEvent event) {
				System.out.println("Fichero subido correctamente");
				image.setVisible(true);
				image.setSource(new FileResource(file));
				image.setWidth(200, Unit.PIXELS);
				image.setHeight(200, Unit.PIXELS);
				menuImage.setValue(file.toString());
			}
		};
		ImageUploader receiver = new ImageUploader();
		Upload upload = new Upload("Upload it here", receiver);
		upload.setImmediateMode(false);
		upload.addSucceededListener(receiver);
		menuImage.setVisible(false);
		
		gridProdAct.setColumns();
		gridProdAct.addColumn(pmList -> { return pmList.getProductObj().getName(); })
					.setCaption("Producto");
		gridProdAct.addColumn(pmList -> { return pmList.getCantidad(); }).setCaption("Cantidad");
			
		//Lista de productos existentes
		Collection<Product> productos = repoP.findAll();
		ArrayList<String> lista = new ArrayList<String>();
		for(Product p: productos)
			lista.add(p.getName());
		
		//select.setItems(lista);
		productSelect = new NativeSelect<>("Selecciona producto", lista);
		
		HorizontalLayout h = new HorizontalLayout(productSelect, productCantidad, newProduct); 
		VerticalLayout actionsProducts = new VerticalLayout(h, gridProdAct, deleteProduct);
		
		addComponents(name, price, upload, image, actionsProducts, actions, menuImage);
		
		binder.bindInstanceFields(this);
		
		gridProdAct.asSingleSelect().addValueChangeListener(e -> { editProdAct(e.getValue()); });
		
		setSpacing(true);
		actions.setStyleName(ValoTheme.LAYOUT_COMPONENT_GROUP);
		save.setStyleName(ValoTheme.BUTTON_PRIMARY);
		save.setClickShortcut(ShortcutAction.KeyCode.ENTER);
		
		save.addClickListener(e -> guardarMenu(menu, productMenu));
		delete.addClickListener(e -> repository.delete(menu));
		cancel.addClickListener(e -> editMenu(menu));
		newProduct.addClickListener(e -> insertarProducto(menu));
		deleteProduct.addClickListener(e -> eliminarProducto(productMenu));
		setVisible(false);
	}
	
	public final void guardarMenu(Menu m, ProductMenu pm){
		//añadir productos al menu
		m.setProductMenuList(pmList);
		repository.save(m);
	}
	
	public final void insertarProducto(Menu m){
		String nombreProducto = productSelect.getValue();
		Long cantidadProducto = Long.parseLong(productCantidad.getValue());
		List<Product> productList = repoP.findByNameStartsWithIgnoreCase(nombreProducto);
		pmList = m.getProductMenuList();
		if(!pmList.isEmpty()){
			String a, b;
			boolean pertenece = false;
			int i = 0;
			b = productList.get(0).getName();
			while(!pertenece && i < pmList.size()){
				a = pmList.get(i).getProductObj().getName();
				if(b.equals(a)){
					pmList.get(i).setCantidad(pmList.get(i).getCantidad()+cantidadProducto);
					pertenece = true;
				}
				i++;
			}
			if(!pertenece){
				ProductMenu pm = new ProductMenu(cantidadProducto, productList.get(0), m);
				pmList.add(pm);
			}
		}
		else{
			ProductMenu pm = new ProductMenu(cantidadProducto, productList.get(0), m);
			pmList.add(pm);
		}
		gridProdAct.setItems(pmList);
	}
	
	public final void eliminarProducto(ProductMenu pm){
		pmList.remove(pm);
		repoPM.delete(pm);
		gridProdAct.setItems(pmList);
	}
	
	public interface ChangeHandler {
		void onChange();
	}

	public final void editMenu(Menu m) {
		if (m == null) {
			System.out.println("Zona1");
			setVisible(false);
			return;
		}
		final boolean persisted = m.getId() != null;
		if (persisted) {
			//System.out.println("Zona2");
			gridProdAct.setItems();
			menu = repository.findOne(m.getId());
			menu.setProductMenuList(repoPM.findByIdMenu(menu.getId()));
			for(ProductMenu pm: menu.getProductMenuList())
				System.out.println(pm.getProductObj().getName());
			if(!menu.getProductMenuList().isEmpty())
				gridProdAct.setItems(menu.getProductMenuList());
			//System.out.println(m.toString());
		}
		else {
			System.out.println("Zona3");
			menu = m;
		}
		//System.out.println("Zona4");
		cancel.setVisible(persisted);

		binder.setBean(menu);

		setVisible(true);
		//System.out.println("Zona5");
		save.focus();
		name.selectAll();
	}

	public final void editProdAct(ProductMenu pm){
		System.out.println("El produto es "+pm);
		if(pm != null){
				productMenu = pm;
				pmList = menu.getProductMenuList();
		}
	}
	
	public void setChangeHandler(ChangeHandler h) {
		save.addClickListener(e -> h.onChange());
		delete.addClickListener(e -> h.onChange());
	}
}