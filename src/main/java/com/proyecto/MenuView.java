package com.proyecto;

import java.util.ArrayList;
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
import com.vaadin.ui.ListSelect;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;

@SuppressWarnings("serial")
@SpringView(name = MenuView.VIEW_NAME)
public class MenuView extends VerticalLayout implements View {
    public static final String VIEW_NAME = "menus";

    private final MenuRepository repo;

	private final MenuEditor editor;
	
	final Grid<Menu> grid;

	final TextField filter;

	private final Button addNewBtn;

	@Autowired
	public MenuView(MenuRepository repo, MenuEditor editor){
		this.editor = editor;
		this.repo = repo;
		
		this.grid = new Grid<>(Menu.class);
		this.filter = new TextField();
		this.addNewBtn = new Button("Nuevo menu");
		addNewBtn.addStyleName(ValoTheme.BUTTON_PRIMARY);
	}
    
    @PostConstruct
    void init() {
        HorizontalLayout actions = new HorizontalLayout(filter, addNewBtn);
        HorizontalLayout mainLayout = new HorizontalLayout(actions, grid, editor);
        VerticalLayout all = new VerticalLayout(actions, mainLayout);
		addComponent(all);

		grid.setColumns("name", "price");

		filter.setPlaceholder("Filtrar por nombre");

		filter.setValueChangeMode(ValueChangeMode.LAZY);
		filter.addValueChangeListener(e -> listmenus(e.getValue()));

		grid.asSingleSelect().addValueChangeListener(e -> {
			editor.editMenu(e.getValue());
		});

		addNewBtn.addClickListener(e -> editor.editMenu(new Menu("", "")));

		editor.setChangeHandler(() -> {
			editor.setVisible(false);
			listmenus(filter.getValue());
		});

		listmenus(null);
		
    }

    void listmenus(String filterText) {
		if (StringUtils.isEmpty(filterText)) {
			grid.setItems((Collection<Menu>) repo.findAll());
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