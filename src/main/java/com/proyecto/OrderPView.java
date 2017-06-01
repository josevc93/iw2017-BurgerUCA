package com.proyecto;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;

import com.proyecto.security.SecurityUtils;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.server.VaadinSession;
import com.vaadin.shared.ui.ValueChangeMode;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.Button;
import com.vaadin.ui.Grid;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;

@SuppressWarnings("serial")
@SpringView(name = OrderPView.VIEW_NAME)
public class OrderPView extends VerticalLayout implements View {
    
	public static final String VIEW_NAME = "pedidos";
	
	private final OrderPRepository repo;
	
	private final OrderPEditor editor;

	final Grid<OrderP> grid;
	
	final TextField filter;

	private final Button addNewBtn;
	
	private final Button cerrarCaja;
	
	private VerticalLayout all;
	
	private OrderP cajas;
	
	//private final Button cierreCaja;
	
	private Grid<Double> gridCaja = new Grid<>(Double.class);
	
	@Autowired
	public OrderPView(OrderPRepository repo, OrderPEditor editor){
		this.repo = repo;
		this.editor = editor;
		
		this.grid = new Grid<>(OrderP.class);
		this.filter = new TextField();
		this.addNewBtn = new Button("Nuevo pedido");
		this.cerrarCaja = new Button("Cerrar caja");
		//this.cierreCaja = new Button("Cerrar");
		addNewBtn.addStyleName(ValoTheme.BUTTON_PRIMARY);
		cerrarCaja.addStyleName(ValoTheme.BUTTON_DANGER);
		//cierreCaja.addStyleName(ValoTheme.BUTTON_DANGER);
	}
		
    @PostConstruct
    void init() {
    	HorizontalLayout actions = new HorizontalLayout(filter, addNewBtn, cerrarCaja);
        VerticalLayout mainLayout = new VerticalLayout(actions, grid);
        all = new VerticalLayout(mainLayout, editor);
		addComponent(all);
		
		HorizontalLayout cajaLayout = new HorizontalLayout(gridCaja);
		gridCaja.setVisible(false);
		gridCaja.setColumns();
		gridCaja.addColumn(Integer -> { return totalPedidos(); }).setCaption("Total pedidos");
		gridCaja.addColumn(Double -> { return Ganancias(); /*getTotalGanancias()*/}).setCaption("Total ganacias");
		addComponent(cajaLayout);
		
		filter.setPlaceholder("Filtrar numero mesa");
		filter.setValueChangeMode(ValueChangeMode.LAZY);
		filter.addValueChangeListener(e -> listpedidos(Long.parseLong(e.getValue())));
		
		grid.setColumns();
		grid.addColumn(editor -> { return editor.getZona().getName(); })
					.setCaption("Zona");
		grid.addColumn(editor -> { return editor.getNumMesa(); }).setCaption("Numero mesa");
		
		grid.asSingleSelect().addValueChangeListener(e -> {
			editor.editOrderP(e.getValue());
		});
		
		List<GridTicket> gridTicketList = new ArrayList<GridTicket>();
		addNewBtn.addClickListener(e -> { editor.editOrderP(new OrderP(false, false, 0L, 0.0, null, null, null, null, null, gridTicketList, true));
		gridCaja.setVisible(false);
    	//cierreCaja.setVisible(false); 
    	grid.setVisible(true);});
		
		cerrarCaja.addClickListener(e -> listaPedidosCaja());
		//cierreCaja.addClickListener(e -> cierrePedido());
		
		editor.setChangeHandler(() -> {
			editor.setVisible(false);
			if(filter.getValue() != "")
				listpedidos(Long.parseLong(filter.getValue()));
		});

		listpedidos(1L);
    }

    void listpedidos(Long num) {
    	gridCaja.setVisible(false);
    	//cierreCaja.setVisible(false);
		if (num == 1) {
			grid.setItems((Collection<OrderP>) repo.findOrdersOpen());
		}
		else {
			grid.setItems(repo.findByNumMesa(num));
		}
	}
    
    void listaPedidosCaja(){
    	grid.setVisible(false);
    	gridCaja.setVisible(true);
    	//cierreCaja.setVisible(true);
    	gridCaja.setItems(1.0);
    }
    
    public int totalPedidos()
    {
    	int total = 0;
    	total = repo.cerrarCaja().size();
    	
    	return total;
    	
    }
    
    public double Ganancias()
    {
    	double ganancias = 0;
    	
    	for(int i = 0; i < repo.cerrarCaja().size(); i++){
    		ganancias = ganancias + repo.cerrarCaja().get(i).getCoste();
    	}
    	
    	return ganancias;
    }
    
    public void cierrePedido()
    {
    	for(int i = 0; i < repo.cerrarCaja().size(); i++){
    		repo.cerrarCaja().get(i).setCaja(false);
    	}
    	
    }
   
    
	@Override
    public void enter(ViewChangeEvent event) {
        // This view is constructed in the init() method()
    }

}