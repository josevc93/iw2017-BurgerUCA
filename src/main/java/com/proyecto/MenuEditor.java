package com.proyecto;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.vaadin.data.Binder;
import com.vaadin.event.ShortcutAction;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.spring.annotation.UIScope;
import com.vaadin.ui.Button;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.ListSelect;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;

import com.proyecto.Product;

@SuppressWarnings("serial")
@SpringComponent
@UIScope
public class MenuEditor extends VerticalLayout{
	
	private final MenuRepository repository;
	
	private final ProductRepository repoP;
	
	private final ProductMenuRepository repoMP;
	
	private final ListSelect<String> select = new ListSelect<>("Productos disponibles");
	
	private final ListSelect<String> selectAct = new ListSelect<>("Actuales");

	private Menu Menu;
	
	TextField name = new TextField("Nombre");
	TextField price = new TextField("Precio");
	
	Button save = new Button("Guardar");
	Button cancel = new Button("Cancelar");
	Button delete = new Button("Eliminar");
	CssLayout actions = new CssLayout(save, cancel, delete);
	
	Button addProduct = new Button("Añadir");
	Button deleteProduct = new Button("Quitar");
	CssLayout act = new CssLayout(addProduct, deleteProduct);
	
	Binder<Menu> binder = new Binder<>(Menu.class);
	
	public MenuEditor(MenuRepository repository, ProductRepository repoProduct, ProductMenuRepository repoProductMenu){
		this.repoP = repoProduct;
		this.repository = repository;
		this.repoMP = repoProductMenu;
		
		//Lista de productos existentes
		Collection<Product> productos = repoP.findAll();
		ArrayList<String> lista = new ArrayList<String>();
		for(Product p: productos)
			lista.add(p.getName());
		
		select.setItems(lista);
		
		addComponents(name, price, actions, select, selectAct, act);
		binder.bindInstanceFields(this);
		setSpacing(true);
		actions.setStyleName(ValoTheme.LAYOUT_COMPONENT_GROUP);
		save.setStyleName(ValoTheme.BUTTON_PRIMARY);
		addProduct.setStyleName(ValoTheme.BUTTON_PRIMARY);
		save.setClickShortcut(ShortcutAction.KeyCode.ENTER);
		
		save.addClickListener(e -> repository.save(Menu));
		delete.addClickListener(e -> repository.delete(Menu));
		cancel.addClickListener(e -> editMenu(Menu));
		addProduct.addClickListener(e -> insertaProducto(Menu));
		deleteProduct.addClickListener(e -> eliminarProducto(Menu));
		setVisible(false);
	}
	
	public final void eliminarProducto(Menu r){
		//Se obtienen los nombres de los productos seleccionados (se almacenan en el vector products)
		String cad =  selectAct.getValue().toString(); 
		System.out.println(cad);
		cad = cad.substring(1, cad.length()-1);
		String [] products = cad.split(", ");		
		
		//Se obtiene el id de los productos seleccionados (se almacenan en id_productos)
		List<Long> id_productos = new ArrayList<Long>();
		List<Product> productList;
				
		for(int i=0; i<products.length; i++){
			productList = repoP.findByNameStartsWithIgnoreCase(products[i]);
			id_productos.add(productList.get(0).getId());
		}
		
		//Se elimina el producto en el menú correspondiente
		List<ProductMenu> productMenuList = repoMP.findAll();
		for(int i=0; i<productMenuList.size(); i++){ //recorro todo ProductMenu
			if(productMenuList.get(i).getId_menu() == r.getId()){ //Si el menu coincide con el q estoy entro aquí
				for(int j=0; j<id_productos.size(); j++){ //Buscamos si el producto es uno de la lista a borrar
					if(productMenuList.get(i).getId_product() == id_productos.get(j)){
						ProductMenu pm = new ProductMenu();
						pm.set_id(productMenuList.get(i).get_id());
						repoMP.delete(pm);
					}
				}
			}
					
		}
	}
	
	
	public final void insertaProducto(Menu r){
		//Se obtienen los nombres de los productos seleccionados (se almacenan en el vector products)
		String cad =  select.getValue().toString();
		cad = cad.substring(1, cad.length()-1);
		String [] products = cad.split(", ");
		
		//Se obtiene el id de los productos seleccionados (se almacenan en id_productos)
		List<Long> id_productos = new ArrayList<Long>();
		List<Product> productList;
		
		for(int i=0; i<products.length; i++){
			productList = repoP.findByNameStartsWithIgnoreCase(products[i]);
			id_productos.add(productList.get(0).getId());
		}
		
		//Se inserta el producto en el menú correspondiente
		for(int i=0; i<id_productos.size(); i++){
			ProductMenu pm = new ProductMenu();
			pm.setId_menu(r.getId());
			pm.setId_product(id_productos.get(i));
			repoMP.save(pm);
		}
	}
	
	public interface ChangeHandler {
		void onChange();
	}

	public final void editMenu(Menu r) {
		if (r == null) {
			setVisible(false);
			return;
		}
		final boolean persisted = r.getId() != null;
		if (persisted) {
			Menu = repository.findOne(r.getId());
			
			//Se añaden los productos actuales del menú
			List<Long> id_productos = new ArrayList<Long>();
			List<String> name_products = new ArrayList<String>();
			List<ProductMenu> prodmenu = repoMP.findAll();
			List<Product> nameProducts = repoP.findAll();
			
			for(ProductMenu p: prodmenu)
				if(p.getId_menu() == r.getId())
					id_productos.add(p.getId_product());
			
			for(Product p : nameProducts)
				for(int i=0; i<id_productos.size(); i++)
					if(p.getId() == id_productos.get(i))
						name_products.add(p.getName());
			
			selectAct.setItems(name_products);
		}
		else {
			Menu = r;
		}
		cancel.setVisible(persisted);

		binder.setBean(Menu);

		setVisible(true);

		save.focus();
		name.selectAll();
	}

	public void setChangeHandler(ChangeHandler h) {
		save.addClickListener(e -> h.onChange());
		delete.addClickListener(e -> h.onChange());
	}
}