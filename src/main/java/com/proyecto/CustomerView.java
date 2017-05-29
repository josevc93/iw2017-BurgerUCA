package com.proyecto;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.shared.ui.ValueChangeMode;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.Button;
import com.vaadin.ui.Grid;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.ListSelect;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;

@SuppressWarnings("serial")
@SpringView(name = CustomerView.VIEW_NAME)
public class CustomerView extends VerticalLayout implements View {
    public static final String VIEW_NAME = "clientes";

    private final CustomerRepository repo;

	private final CustomerEditor editor;
	
	final Grid<Customer> grid;

	final TextField filter;

	private final Button addNewBtn;
	

	@Autowired
	public CustomerView(CustomerRepository repo, CustomerEditor editor){
		this.editor = editor;
		this.repo = repo;
		this.grid = new Grid<>(Customer.class);
		this.filter = new TextField();
		this.addNewBtn = new Button("Nuevo cliente");
		addNewBtn.addStyleName(ValoTheme.BUTTON_PRIMARY);
	}
    
    @PostConstruct
    void init() {
        HorizontalLayout actions = new HorizontalLayout(filter, addNewBtn);
        HorizontalLayout mainLayout = new HorizontalLayout(actions, grid, editor);
        VerticalLayout all = new VerticalLayout(actions, mainLayout);
		addComponent(all);

		grid.setColumns("telefono", "direccion");
		
		filter.setPlaceholder("Filtrar por teléfono");

		filter.setValueChangeMode(ValueChangeMode.LAZY);
		filter.addValueChangeListener(e -> listCustomers(e.getValue()));

		grid.asSingleSelect().addValueChangeListener(e -> {
			editor.editCustomer(e.getValue());
		});

		addNewBtn.addClickListener(e -> editor.editCustomer(new Customer("", "")));

		editor.setChangeHandler(() -> {
			editor.setVisible(false);
			listCustomers(filter.getValue());
		});

		listCustomers(null);
		
    }

    void listCustomers(String filterText) {
		if (StringUtils.isEmpty(filterText)) {
			grid.setItems((Collection<Customer>) repo.findAll());
		}
		else {
			grid.setItems(repo.findBytelefonoStartsWithIgnoreCase(filterText));
		}
	}
    
	@Override
    public void enter(ViewChangeEvent event) {
        // This view is constructed in the init() method()
		//grid.getDataProvider().refreshAll();
    }

}