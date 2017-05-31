package com.proyecto;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.GregorianCalendar;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.util.StringUtils;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.pdf.draw.LineSeparator;
import com.proyecto.security.SecurityUtils;
import com.vaadin.data.Binder;
import com.vaadin.data.converter.StringToLongConverter;
import com.vaadin.event.ShortcutAction;
import com.vaadin.server.FileResource;
import com.vaadin.server.Sizeable.Unit;
import com.vaadin.shared.ui.ValueChangeMode;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.spring.annotation.UIScope;
import com.vaadin.ui.Button;
import com.vaadin.ui.CheckBox;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.Grid;
import com.vaadin.ui.Grid.Column;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Image;
import com.vaadin.ui.JavaScript;
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
	
	private final GridTicketRepository repoGT;
	
	private final CustomerRepository repoC;
	
	private final OrderLineMenuRepository repoOLM;
	
	private final OrderLineProductRepository repoOLP;
	
	private final NativeSelect<String> zonasSelect;
	
	protected List<OrderLineProduct> orderLPlist = new ArrayList<>();
	
	protected List<OrderLineMenu> orderLMlist = new ArrayList<>();

	private OrderP orderp;
	private OrderLineProduct orderLP;
	
	final Grid<Customer> gridCustomer = new Grid<Customer>(Customer.class);
	final Grid<GridTicket> gridTicket = new Grid<GridTicket>(GridTicket.class);
	
	private GridTicket gridTicketObj;
	
	private List<GridTicket> gtList = new ArrayList<GridTicket>();
	private List<OrderLineProduct> deleteOrderLineProduct = new ArrayList<OrderLineProduct>();
	private List<OrderLineMenu> deleteOrderLineMenu = new ArrayList<OrderLineMenu>();
	
	private TextField filter = new TextField();
	VerticalLayout gridCliente = new VerticalLayout(filter, gridCustomer);
	
	CheckBox takeAway = new CheckBox("Para llevar");
	CheckBox state = new CheckBox("Finalizado");
	TextField numMesa = new TextField("Numero de mesa (*)");
	
	Button save = new Button("Guardar");
	Button cancel = new Button("Cancelar");
	Button delete = new Button("Eliminar");
	CssLayout actions = new CssLayout(save, cancel, delete);
	
	TabSheet alimentos = new TabSheet();

	GridLayout comidasLayout = new GridLayout();
	GridLayout bebidasLayout = new GridLayout();
	GridLayout postresLayout = new GridLayout();
	GridLayout menusLayout = new GridLayout();
	
	Button deleteProduct = new Button("Eliminar");
//	Button addMenu = new Button("Añadir");
//	Button addProduct = new Button("Añadir");
	
	private File file;
	
	Binder<OrderP> binder = new Binder<>(OrderP.class);
	
	public OrderPEditor(OrderPRepository repository, ProductRepository repoProduct
						, ZonaRepository repoZona, MenuRepository repoM, CustomerRepository repoC, 
						OrderLineMenuRepository repoOLM, OrderLineProductRepository repoOLP, GridTicketRepository repogt){
		this.repoP = repoProduct;
		this.repository = repository;
		this.repoZona = repoZona;
		this.repoC = repoC;
		this.repoM = repoM;
		this.repoOLM = repoOLM;
		this.repoOLP = repoOLP;
		this.repoGT = repogt;
		
		menusLayout.setColumns(5);
	    comidasLayout.setColumns(5);
		bebidasLayout.setColumns(5);
		postresLayout.setColumns(5);
			
		List<Customer> listCustomer = repoC.findAll();
		gridCustomer.setColumns();
		gridCustomer.addColumn(customer -> { return customer.getDireccion(); }).setCaption("Cliente");
		gridCustomer.addColumn(customer -> { return customer.getTelefono(); }).setCaption("Telefono");
		gridCustomer.setItems(listCustomer);
		gridCliente.setVisible(false);
		
		filter.setPlaceholder("Filtrar por telefono");

		filter.setValueChangeMode(ValueChangeMode.LAZY);
		filter.addValueChangeListener(e -> listPhones(e.getValue()));
		
		gridTicket.setColumns();
		gridTicket.addColumn(ticket -> { return ticket.getNombre();}).setCaption("Nombre");
		gridTicket.addColumn(ticket -> { return ticket.getCantidad(); }).setCaption("Cantidad");
		gridTicket.addColumn(ticket -> { return ticket.getPrecio(); }).setCaption("Precio");
		
		gridTicket.asSingleSelect().addValueChangeListener(e -> { editGridTicket(e.getValue()); });
		
		//Lista de zonas existentes
		List<Zona> zonasList = repoZona.findAll();
		ArrayList<String> zonasName = new ArrayList<>();
		for(Zona z: zonasList)
			zonasName.add(z.getName());
		zonasSelect = new NativeSelect<>("Selecciona zona", zonasName);
		
		//Lista de comidas existentes
		Collection<Product> comidas = repoP.findByFamily("comidas");
		for(Product p: comidas){
			file = new File(p.getProductImage());
			Image image = new Image(p.getName());
			image.setWidth(100, Unit.PIXELS);
			image.setHeight(100, Unit.PIXELS);
			image.setSource(new FileResource(file));
			image.addClickListener(e -> insertarProducto(image.getCaption(),orderp));
			comidasLayout.addComponent(image);
		}
		alimentos.addTab(comidasLayout, "Comidas");
		
		//Lista de bebidas existentes
		Collection<Product> bebidas = repoP.findByFamily("bebidas");
		for(Product p: bebidas){
			file = new File(p.getProductImage());
			Image image = new Image(p.getName());
			image.setWidth(100, Unit.PIXELS);
			image.setHeight(100, Unit.PIXELS);
			image.setSource(new FileResource(file));
			image.addClickListener(e -> insertarProducto(image.getCaption(),orderp));
			bebidasLayout.addComponent(image);
		}
		alimentos.addTab(bebidasLayout, "Bebidas");
		
		//Lista de postres existentes
		Collection<Product> postres = repoP.findByFamily("postres");
		for(Product p: postres){
			file = new File(p.getProductImage());
			Image image = new Image(p.getName());
			image.setWidth(100, Unit.PIXELS);
			image.setHeight(100, Unit.PIXELS);
			image.setSource(new FileResource(file));
			image.addClickListener(e -> insertarProducto(image.getCaption(), orderp));
			postresLayout.addComponent(image);
		}
		alimentos.addTab(postresLayout, "Postres");
		
		//Lista de menus existentes
		Collection<Menu> menus = repoM.findAll();
		for(Menu m: menus){
			file = new File(m.getMenuImage());
			Image image = new Image(m.getName());
			image.setWidth(100, Unit.PIXELS);
			image.setHeight(100, Unit.PIXELS);
			image.setSource(new FileResource(file));
			image.addClickListener(e -> insertarMenu(image.getCaption(), orderp));
			menusLayout.addComponent(image);
		}
		alimentos.addTab(menusLayout, "Menus");
		addComponents(state, takeAway, gridCliente, numMesa, zonasSelect, deleteProduct, gridTicket, alimentos, actions);
		
		binder.forField(numMesa)
		  .withNullRepresentation("")
		  .withConverter(
		    new StringToLongConverter("Por favor introduce un número"))
		  .bind("numMesa");
		
		binder.bindInstanceFields(this);
		
		setSpacing(true);
		actions.setStyleName(ValoTheme.LAYOUT_COMPONENT_GROUP);
		save.setStyleName(ValoTheme.BUTTON_PRIMARY);
		save.setClickShortcut(ShortcutAction.KeyCode.ENTER);
		
		takeAway.addValueChangeListener(e -> {
			if(e.getValue() == false){
				gridCliente.setVisible(false);
				zonasSelect.setVisible(true);
				numMesa.setVisible(true);
			}
			else{
				gridCliente.setVisible(true);
				zonasSelect.setVisible(false);
				numMesa.setVisible(false);
			}
		});
		
		listPhones(null);
		
		gridCustomer.asSingleSelect().addValueChangeListener(e -> { orderp.setCustomer(e.getValue()); });
		
		save.addClickListener(e -> guardarPedido(orderp));
		delete.addClickListener(e -> eliminarPedido(orderp));
		deleteProduct.addClickListener(e -> eliminarProducto(gridTicketObj, orderp));
		setVisible(false);
	}
	
	
	public final void insertarMenu(String name, OrderP orderp){
		List<Menu> menuList = repoM.findByNameStartsWithIgnoreCase(name);
		gtList = orderp.getGridTicketList();
		
		boolean existe = false;
		int pos = 0;
		while(!existe && pos<gtList.size()){
			if(name == gtList.get(pos).getNombre())
				existe = true;
			pos++;
		}
		pos--;
		
		if(existe){
			gtList.get(pos).setCantidad(gtList.get(pos).getCantidad()+1);
			gtList.get(pos).setPrecio(Double.parseDouble(menuList.get(0).getPrice()) * gtList.get(pos).getCantidad());
			orderLMlist.add(new OrderLineMenu(1, Double.parseDouble(menuList.get(0).getPrice()), 
					orderp, menuList.get(0)));
    	}else{
			gtList.add(new GridTicket(name, 1L, Double.parseDouble(menuList.get(0).getPrice()), true, "", "menu")); //MENU
			orderLMlist.add(new OrderLineMenu(1, Double.parseDouble(menuList.get(0).getPrice()), 
					orderp, menuList.get(0)));
    	}
		
		gridTicket.setItems(gtList);
		orderp.setOrderLineMenuList(orderLMlist);
	}
	
	public final void eliminarProducto(GridTicket gt, OrderP orderp){
     	for(int i=0; i<orderp.getOrderLineProductList().size(); i++)
     		if(orderp.getOrderLineProductList().get(i).getProductObj().getName().equals(gt.getNombre())){
     			deleteOrderLineProduct.add(orderp.getOrderLineProductList().get(i));
     			orderLPlist.remove(orderp.getOrderLineProductList().get(i));
     		}
     	
     	for(int i=0; i<orderp.getOrderLineMenuList().size(); i++)
     		if(orderp.getOrderLineMenuList().get(i).getMenuObj().getName().equals(gt.getNombre())){
     			deleteOrderLineMenu.add(orderp.getOrderLineMenuList().get(i));
     			orderLMlist.remove(orderp.getOrderLineMenuList().get(i));
     		}
     	
     	List<GridTicket> gtL = new ArrayList<GridTicket>();
     	for(int i=0; i<gtList.size(); i++)
     		if(gtList.get(i).getNombre() != gt.getNombre())
     			gtL.add(gtList.get(i));

		gridTicket.setItems(gtL);
	}
	
	public final void insertarProducto(String name, OrderP orderp){
		List<Product> productList = repoP.findByNameStartsWithIgnoreCase(name);
		gtList = orderp.getGridTicketList();
		boolean existe = false;
		int pos = 0;
		while(!existe && pos<gtList.size()){
			if(name.equals(gtList.get(pos).getNombre()))
				existe = true;
			pos++;
		}
		pos--;
		
		if(existe){
			gtList.get(pos).setCantidad(gtList.get(pos).getCantidad()+1);
			gtList.get(pos).setPrecio(Double.parseDouble(productList.get(0).getPrice()) * gtList.get(pos).getCantidad());
			orderLPlist.get(pos).setCantidad(orderLPlist.get(pos).getCantidad()+1);
			orderLPlist.get(pos).setPrecio(orderLPlist.get(pos).getPrecio()*orderLPlist.get(pos).getCantidad());
    	}else{
			gtList.add(new GridTicket(name, 1L, Double.parseDouble(productList.get(0).getPrice()), true, productList.get(0).getIva(),productList.get(0).getFamily()));
			OrderLineProduct o = new OrderLineProduct(1, Double.parseDouble(productList.get(0).getPrice()), orderp, productList.get(0));
			orderLPlist.add(o);
    	}
		
		gridTicket.setItems(gtList);
		orderp.setOrderLineProductList(orderLPlist);
	}
	
	/*Esta función genera un ticket para cocina.*/
	public void imprimirTicketCocina(OrderP p, List<GridTicket> gtList){
		boolean soloBebidas = true;
		for(GridTicket item: gtList)
			if(!item.getFamily().equals("bebidas"))
				soloBebidas = false;
		if(!soloBebidas){
			try{
				Document documento = new Document();
				Calendar calendario = new GregorianCalendar();
				FileOutputStream ficheroPdf = new FileOutputStream("TicketCocina/ticket_" + calendario.get(Calendar.HOUR_OF_DAY) + 
						calendario.get(Calendar.MINUTE) + calendario.get(Calendar.SECOND) +  ".pdf");
				PdfWriter.getInstance(documento,ficheroPdf).setInitialLeading(20);
				documento.open();
				documento.add(new Paragraph("Ticket cocina",
						FontFactory.getFont("arial",  22, Font.BOLD, BaseColor.BLUE)));		
				documento.add(new Paragraph(" "));
				documento.add(new Paragraph(""));
				
				for(GridTicket item: gtList)
					if(!item.getFamily().equals("bebidas"))
						documento.add(new Paragraph(item.getNombre() + " " + "(" + item.getCantidad() + ")"));
				
				documento.close();
			}catch(Exception e){System.out.println(e);}
		}
	}
	
	
	/*Esta función genera un ticket con la cuenta total, una vez cerrado el pedido.*/
	public void imprimirTicketFinal(OrderP p, boolean paraLlevar){
		try{
			Document documento = new Document();
			Calendar calendario = new GregorianCalendar();
			FileOutputStream ficheroPdf = new FileOutputStream("TicketFinal/ticket_" + calendario.get(Calendar.HOUR_OF_DAY) + 
					calendario.get(Calendar.MINUTE) + calendario.get(Calendar.SECOND) +  ".pdf");
			PdfWriter.getInstance(documento,ficheroPdf).setInitialLeading(20);
			documento.open();
			documento.add(new Paragraph("BurguerUCA",
					FontFactory.getFont("arial",  22, Font.BOLD, BaseColor.BLUE)));		
			documento.add(new Paragraph(" "));
			documento.add(new Paragraph(""));
			
			if(paraLlevar)
				documento.add(new Paragraph("Pedido para llevar",
						FontFactory.getFont("arial",  12)));
			else
				documento.add(new Paragraph("Pedido para consumir en el local",
						FontFactory.getFont("arial",  12)));
			
			documento.add(new Paragraph(" "));
			
			for(OrderLineProduct item: p.getOrderLineProductList())
				documento.add(new Paragraph(item.getProductObj().getName() + " " + "(" + item.getCantidad() + ") (" + item.getPrecio() + " €)"));
			
			for(OrderLineMenu item: p.getOrderLineMenuList())
				documento.add(new Paragraph(item.getMenuObj().getName() + " " + "(" + item.getCantidad() + ") (" + item.getPrecio() + " €)"));
			
			documento.add(new Paragraph(" "));
			DecimalFormat f = new DecimalFormat("##.00");
			
			documento.add(new Paragraph("Coste : " + f.format(costeTotalsinIVA(p.getOrderLineProductList(), p.getOrderLineMenuList())) + " €",
					FontFactory.getFont("arial",  12)));
			documento.add(new Paragraph("Coste total con IVA: " + f.format(costeTotalIVA(p.getOrderLineProductList(), p.getOrderLineMenuList())) + " €",
					FontFactory.getFont("arial",  12)));
			documento.add(new Paragraph(" "));
			documento.add(new Paragraph("***El precio de los productos se muestran sin iva***",
					FontFactory.getFont("arial",  10, Font.ITALIC)));
			documento.close();
		}catch(Exception e){System.out.println(e);}
	}
	
	public void guardarPedido(OrderP p){
		boolean guardar = true;
		String cad = zonasSelect.getValue();
		String errores = "alert('No se ha podido guardar, debido a los siguientes errores:";
		if(takeAway.getValue()){
			if(orderp.getCustomer() == null){
				errores = errores.concat("\\n - Debes seleccionar un cliente.");
				guardar = false;
			}
		}else{
			try{
				int numero = Integer.parseInt(numMesa.getValue());
				if(numero < 0 ){
			    	 errores = errores.concat("\\n - El numero de mesas debe ser un numero positivo.");
					 guardar = false;
			     }
			}catch(NumberFormatException e){ 
				errores = errores.concat("\\n - El numero de mesas debe ser numerico.");
				guardar = false;
			} 			
			if(cad == null && p.getZona() == null){
				errores = errores.concat("\\n - Debes elegir zona.");
				guardar = false;
			}
		}
		
		if(guardar){
			boolean ticketFinal = state.getValue();
			p.setUser(SecurityUtils.getUserLogin());
			costeTotal(gtList);
			//primero controlamos si el pedido es para llevar o no
			if(takeAway.getValue()){
				p.setState(true);
				p.setOrderLineProductList(orderLPlist);
				repository.save(p);
				for(OrderLineMenu item: deleteOrderLineMenu)
					repoOLM.delete(item);
				for(OrderLineProduct item: deleteOrderLineProduct)
					repoOLP.delete(item);
				for(OrderLineProduct item: orderLPlist)
					repoOLP.save(item);
				for(OrderLineMenu item: orderLMlist)
					repoOLM.save(item);
			}
			else{
				if(cad==null){ //Si no se selecciona zona, coge la ya existente
					orderp.setZona(orderp.getZona());
				}else{ //Si se selecciona, la reemplaza
					List<Zona> z = repoZona.findByNameStartsWithIgnoreCase(cad);
					orderp.setZona(z.get(0));
				}
				orderp.setNumMesa(Long.parseLong(numMesa.getValue()));
				p.setOrderLineProductList(orderLPlist);
				repository.save(p);
				for(OrderLineMenu item: deleteOrderLineMenu)
					repoOLM.delete(item);
				for(OrderLineProduct item: deleteOrderLineProduct)
					repoOLP.delete(item);
				for(OrderLineProduct item: orderLPlist)
					repoOLP.save(item);
				for(OrderLineMenu item: orderLMlist)
					repoOLM.save(item);
			}
			imprimirTicketCocina(p, gtList);
			if(ticketFinal)
				imprimirTicketFinal(p, takeAway.getValue());	
		}else{
			errores = errores.concat("');");
			JavaScript.getCurrent().execute(errores);
		}
	}
	
	public void eliminarPedido(OrderP orderp){
		repository.delete(orderp);
	}
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
			orderp = repository.findOne(or.getId());
			orderp.setOrderLineMenuList(repoOLM.findByIdMenu(orderp.getId()));
			orderp.setOrderLineProductList(repoOLP.findByIdProduct(orderp.getId()));
			orderLPlist = orderp.getOrderLineProductList();
			orderLMlist = orderp.getOrderLineMenuList();
			List<GridTicket> items = new ArrayList<>();
			for(OrderLineProduct i: orderp.getOrderLineProductList()){
				items.add(new GridTicket(i.getProductObj().getName(), new Long(i.getCantidad()), i.getPrecio(), true, i.getProductObj().getIva(), i.getProductObj().getFamily()));
				gtList.add(new GridTicket(i.getProductObj().getName(), new Long(i.getCantidad()), i.getPrecio(), true, i.getProductObj().getIva(), i.getProductObj().getFamily()));
			}
			for(OrderLineMenu i: orderp.getOrderLineMenuList()){
				items.add(new GridTicket(i.getMenuObj().getName(), new Long(i.getCantidad()), i.getPrecio(), true, "", "menu")); //MENU
				gtList.add(new GridTicket(i.getMenuObj().getName(), new Long(i.getCantidad()), i.getPrecio(), true, "", "menu"));
			}
			gridTicket.setItems(items);
			orderp.setGridTicketList(items);
		}
		else {
			gridTicket.setItems();
			orderp = or;
		}
		cancel.setVisible(persisted);

		binder.setBean(orderp);

		setVisible(true);
		save.focus();
	}

	public void setChangeHandler(ChangeHandler h) {
		save.addClickListener(e -> h.onChange());
		delete.addClickListener(e -> h.onChange());
	}
	
	 void listPhones(String filterText) {
		if (StringUtils.isEmpty(filterText)) {
			gridCustomer.setItems((Collection<Customer>) repoC.findAll());
		}
		else {
			gridCustomer.setItems(repoC.findBytelefonoStartsWithIgnoreCase(filterText));
		}
	}
	 
	 
	 public final void editGridTicket(GridTicket gt){
			if(gt != null){
					gridTicketObj = gt;
			}
		}
	 
	 public void costeTotal(List<GridTicket> gtList){
		 double precioTotal = 0.0;
		 for(GridTicket item: gtList)
			 precioTotal += item.getPrecio();
		 
		 orderp.setCoste(precioTotal);
	 }
	 
	 public double costeTotalsinIVA(List<OrderLineProduct> pList, List<OrderLineMenu> mList){
		 double precioTotal = 0.0;
		 for(OrderLineProduct item: pList)
			 precioTotal += item.getPrecio();
		 
		 for(OrderLineMenu item: mList)
			 precioTotal += item.getPrecio();
		 
		return precioTotal;
	 }
	 
	 public double costeTotalIVA(List<OrderLineProduct> pList,  List<OrderLineMenu> mList){
		 double precioTotal = 0.0;
		 double precioMenu = 0.0;
		 for(OrderLineProduct item: pList)
			 precioTotal += item.getPrecio() + ( item.getPrecio() * (Double.parseDouble(item.getProductObj().getIva())/100 ) );
		 
		 for(OrderLineMenu item: mList){
			 precioMenu = 0.0;
			 for(ProductMenu pm : item.getMenuObj().getProductMenuList()){
				 precioMenu += ((Double.parseDouble(pm.getProductObj().getPrice()) * 
						 Double.parseDouble(pm.getProductObj().getIva()) / 100)) ;
			 }
			 precioTotal += (item.getPrecio() + precioMenu) * item.getCantidad();
		 }
		 
		return precioTotal;
	 }
}