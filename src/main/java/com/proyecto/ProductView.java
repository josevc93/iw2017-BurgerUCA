package com.proyecto;

import java.util.Collection;

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
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;

@SuppressWarnings("serial")
@SpringView(name = ProductView.VIEW_NAME)
public class ProductView extends VerticalLayout implements View {
    public static final String VIEW_NAME = "productos";

    private final ProductRepository repo;

	private final ProductEditor editor;
	
	final Grid<Product> grid;

	final TextField filter;

	private final Button addNewBtn;

	@Autowired
	public ProductView(ProductRepository repo, ProductEditor editor){
		this.editor = editor;
		this.repo = repo;
		this.grid = new Grid<>(Product.class);
		this.filter = new TextField();
		this.addNewBtn = new Button("Nuevo Producto");
		addNewBtn.addStyleName(ValoTheme.BUTTON_PRIMARY);
	}
    
    @PostConstruct
    void init() {
        HorizontalLayout actions = new HorizontalLayout(filter, addNewBtn);
        HorizontalLayout mainLayout = new HorizontalLayout(actions, grid, editor);
        VerticalLayout all = new VerticalLayout(actions, mainLayout);
		addComponent(all);

		grid.setColumns("name", "price", "family");

		filter.setPlaceholder("Filtrar por nombre");

		filter.setValueChangeMode(ValueChangeMode.LAZY);
		filter.addValueChangeListener(e -> listProducts(e.getValue()));

		grid.asSingleSelect().addValueChangeListener(e -> {
			editor.editProduct(e.getValue());
		});

		addNewBtn.addClickListener(e -> editor.editProduct(new Product("", "", "", "", "")));

		editor.setChangeHandler(() -> {
			editor.setVisible(false);
			listProducts(filter.getValue());
		});

		listProducts(null);
    }

    void listProducts(String filterText) {
		if (StringUtils.isEmpty(filterText)) {
			grid.setItems((Collection<Product>) repo.findAll());
		}
		else {
			grid.setItems(repo.findByNameStartsWithIgnoreCase(filterText));
		}
	}
    
	@Override
    public void enter(ViewChangeEvent event) {
        // This view is constructed in the init() method()
    }

}