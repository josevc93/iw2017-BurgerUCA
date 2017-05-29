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
	
	@Autowired
	public OrderPView(OrderPRepository repo, OrderPEditor editor){
		this.repo = repo;
		this.editor = editor;
		
		this.grid = new Grid<>(OrderP.class);
		this.filter = new TextField();
		this.addNewBtn = new Button("Nuevo pedido");
		addNewBtn.addStyleName(ValoTheme.BUTTON_PRIMARY);
	}
		
    @PostConstruct
    void init() {
    	addComponent(new Label("Nombre del usuario: " + SecurityUtils.getUserLogin()));
    //	addComponent(new Label("Trabaja en: " + SecurityUtils.getUserRestaurant()));
        //addComponent(new Label("Bienvenido a la gestión de pedidos."));
    	HorizontalLayout actions = new HorizontalLayout(filter, addNewBtn);
        VerticalLayout mainLayout = new VerticalLayout(actions, grid);
        VerticalLayout all = new VerticalLayout(mainLayout, editor);
		addComponent(all);
		
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
		addNewBtn.addClickListener(e -> editor.editOrderP(new OrderP(false, false, 0L, 0.0, null, null, null, null, null, gridTicketList)));
		
		editor.setChangeHandler(() -> {
			editor.setVisible(false);
			if(filter.getValue() != "")
				listpedidos(Long.parseLong(filter.getValue()));
			/*else
				listpedidos(null);*/
		});

		listpedidos(1L);
    }

    void listpedidos(Long num) {
		if (num == 1) {
			grid.setItems((Collection<OrderP>) repo.findOrdersOpen());
		}
		else {
			grid.setItems(repo.findByNumMesa(num));
		}
	}
	@Override
    public void enter(ViewChangeEvent event) {
        // This view is constructed in the init() method()
    }

}