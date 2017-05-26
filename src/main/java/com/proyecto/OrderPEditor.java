package com.proyecto;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.vaadin.data.Binder;
import com.vaadin.data.converter.StringToLongConverter;
import com.vaadin.event.ShortcutAction;
import com.vaadin.server.FileResource;
import com.vaadin.server.Sizeable.Unit;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.spring.annotation.UIScope;
import com.vaadin.ui.Button;
import com.vaadin.ui.CheckBox;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.Grid;
import com.vaadin.ui.Grid.Column;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Image;
import com.vaadin.ui.ListSelect;
import com.vaadin.ui.NativeSelect;
import com.vaadin.ui.TabSheet;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;

@SuppressWarnings("serial")
@SpringComponent
@UIScope
public class OrderPEditor extends VerticalLayout{
	
	private final OrderPRepository repository;
	
	private final ProductRepository repoP;
	
	private final ZonaRepository repoZona;
	
	private final MenuRepository repoM;
	
	private final NativeSelect<String> zonasSelect;

	private OrderP orderp;
	private Menu menu;
	private ProductMenu productMenu;
	private Customer customer;
	
	//private List<ProductMenu> pmList = new ArrayList<ProductMenu>();
	
	//final Grid<ProductMenu> gridProdAct = new Grid<ProductMenu>(ProductMenu.class);
	final Grid<Customer> gridCustomer = new Grid<Customer>(Customer.class);
	
	CheckBox takeAway = new CheckBox("Para llevar");
	CheckBox state = new CheckBox("Finalizado");
	TextField numMesa = new TextField("Numero de mesa");
	
	Button save = new Button("Guardar");
	Button cancel = new Button("Cancelar");
	Button delete = new Button("Eliminar");
	CssLayout actions = new CssLayout(save, cancel, delete);
	
	TabSheet alimentos = new TabSheet();
	HorizontalLayout comidasLayout = new HorizontalLayout();
	HorizontalLayout bebidasLayout = new HorizontalLayout();
	HorizontalLayout postresLayout = new HorizontalLayout();
	HorizontalLayout menusLayout = new HorizontalLayout();
	
	Button addMenu = new Button("Añadir");
	Button addProduct = new Button("Añadir");
	
	private File file;
	
	Binder<OrderP> binder = new Binder<>(OrderP.class);
	
	public OrderPEditor(OrderPRepository repository, ProductRepository repoProduct
						, ZonaRepository repoZona, MenuRepository repoM){
		this.repoP = repoProduct;
		this.repository = repository;
		this.repoZona = repoZona;
		this.repoM = repoM;
	
		gridCustomer.setColumns();
		gridCustomer.addColumn(customer -> { return customer.getDireccion(); }).setCaption("Cliente");
		gridCustomer.addColumn(customer -> { return customer.getTelefono(); }).setCaption("Telefono");
		gridCustomer.setVisible(false);
		
		//Lista de zonas existentes
		List<Zona> zonasList = repoZona.findAll();
		ArrayList<String> zonasName = new ArrayList<>();
		for(Zona z: zonasList)
			zonasName.add(z.getName());
		zonasSelect = new NativeSelect<>("Selecciona zona", zonasName);
		
		//Lista de comidas existentes
		Collection<Product> comidas = repoP.findByFamily("comidas");
		for(Product p: comidas){
			//System.out.println(p.getProductImage());
			file = new File(p.getProductImage());
			Image image = new Image(p.getName());
			image.setWidth(100, Unit.PIXELS);
			image.setHeight(100, Unit.PIXELS);
			image.setSource(new FileResource(file));
			image.addClickListener(e -> System.out.println(image.getCaption()));
			comidasLayout.addComponent(image);
		}
		alimentos.addTab(comidasLayout, "Comidas");
		
		//Lista de bebidas existentes
		Collection<Product> bebidas = repoP.findByFamily("bebidas");
		for(Product p: bebidas){
			//System.out.println(p.getProductImage());
			file = new File(p.getProductImage());
			Image image = new Image(p.getName());
			image.setWidth(100, Unit.PIXELS);
			image.setHeight(100, Unit.PIXELS);
			image.setSource(new FileResource(file));
			image.addClickListener(e -> System.out.println(image.getCaption()));
			bebidasLayout.addComponent(image);
		}
		alimentos.addTab(bebidasLayout, "Bebidas");
		
		//Lista de postres existentes
		Collection<Product> postres = repoP.findByFamily("postres");
		for(Product p: postres){
			//System.out.println(p.getProductImage());
			file = new File(p.getProductImage());
			Image image = new Image(p.getName());
			image.setWidth(100, Unit.PIXELS);
			image.setHeight(100, Unit.PIXELS);
			image.setSource(new FileResource(file));
			image.addClickListener(e -> System.out.println(image.getCaption()));
			postresLayout.addComponent(image);
		}
		alimentos.addTab(postresLayout, "Postres");
		
		//Lista de menus existentes
		Collection<Menu> menus = repoM.findAll();
		for(Menu m: menus){
			//System.out.println(p.getProductImage());
			file = new File("lol");
			Image image = new Image(m.getName());
			image.setWidth(100, Unit.PIXELS);
			image.setHeight(100, Unit.PIXELS);
			image.setSource(new FileResource(file));
			//image.addClickListener(e -> { new GridTicket(m.getName(), m.get)}/*System.out.println(image.getCaption())*/);
			menusLayout.addComponent(image);
		}
		alimentos.addTab(menusLayout, "Menus");

		addComponents(state, takeAway, gridCustomer, numMesa, zonasSelect, alimentos, actions);
		
		binder.forField(numMesa)
		  .withNullRepresentation("")
		  .withConverter(
		    new StringToLongConverter("Por favor introduce un número"))
		  .bind("numMesa");
		
		binder.bindInstanceFields(this);
		
		//gridProdAct.asSingleSelect().addValueChangeListener(e -> { editProdAct(e.getValue()); });
		
		setSpacing(true);
		actions.setStyleName(ValoTheme.LAYOUT_COMPONENT_GROUP);
		save.setStyleName(ValoTheme.BUTTON_PRIMARY);
		save.setClickShortcut(ShortcutAction.KeyCode.ENTER);
		
		takeAway.addValueChangeListener(e -> {
			if(e.getValue() == false){
				gridCustomer.setVisible(false);
				zonasSelect.setVisible(true);
				numMesa.setVisible(true);
			}
			else{
				gridCustomer.setVisible(true);
				zonasSelect.setVisible(false);
				numMesa.setVisible(false);
			}
		});
		
		
		
		//save.addClickListener(e -> guardarMenu(menu, productMenu));
		//delete.addClickListener(e -> repository.delete(menu));
		//cancel.addClickListener(e -> editOrderP(orderp));
		/*newProduct.addClickListener(e -> insertarProducto(menu));
		deleteProduct.addClickListener(e -> eliminarProducto(productMenu));*/
		setVisible(false);
	}
	
	/*public final void guardarMenu(Menu m, ProductMenu pm){
		//añadir productos al menu
		m.setProductMenuList(pmList);
		repository.save(m);
	}*/
	
	/*public final void insertarProducto(Menu m){
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
	}*/
	
	/*public final void eliminarProducto(ProductMenu pm){
		pmList.remove(pm);
		repoPM.delete(pm);
		gridProdAct.setItems(pmList);
	}*/
	
	public interface ChangeHandler {
		void onChange();
	}

	public final void editOrderP(OrderP or) {
		if (or == null) {
			setVisible(false);
			return;
		}
		final boolean persisted = or.getId() != null;
		if (persisted) {
			/*gridProdAct.setItems();
			menu = repository.findOne(m.getId());
			menu.setProductMenuList(repoPM.findByIdMenu(menu.getId()));
			for(ProductMenu pm: menu.getProductMenuList())
				System.out.println(pm.getProductObj().getName());
			if(!menu.getProductMenuList().isEmpty())
				gridProdAct.setItems(menu.getProductMenuList());*/
		}
		else {
			//System.out.println("Zona3");
			orderp = or;
		}
		//System.out.println("Zona4");
		cancel.setVisible(persisted);

		binder.setBean(orderp);

		setVisible(true);
		//System.out.println("Zona5");
		save.focus();
		//name.selectAll();
	}

	/*public final void editProdAct(ProductMenu pm){
		System.out.println("El produto es "+pm);
		if(pm != null){
				productMenu = pm;
				pmList = menu.getProductMenuList();
		}
	}*/
	
	public void setChangeHandler(ChangeHandler h) {
		save.addClickListener(e -> h.onChange());
		delete.addClickListener(e -> h.onChange());
	}
}