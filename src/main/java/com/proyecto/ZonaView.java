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
@SpringView(name = ZonaView.VIEW_NAME)
public class ZonaView extends VerticalLayout implements View {
    public static final String VIEW_NAME = "zonas";

    private final ZonaRepository repo;

	private final ZonaEditor editor;
	
	final Grid<Zona> grid;

	final TextField filter;

	private final Button addNewBtn;
	

	@Autowired
	public ZonaView(ZonaRepository repo, ZonaEditor editor){
		this.editor = editor;
		this.repo = repo;
		this.grid = new Grid<>(Zona.class);
		this.filter = new TextField();
		this.addNewBtn = new Button("Nueva Zona");
		addNewBtn.addStyleName(ValoTheme.BUTTON_PRIMARY);
	}
    
    @PostConstruct
    void init() {
        HorizontalLayout actions = new HorizontalLayout(filter, addNewBtn);
        HorizontalLayout mainLayout = new HorizontalLayout(actions, grid, editor);
        VerticalLayout all = new VerticalLayout(actions, mainLayout);
		addComponent(all);

		grid.setColumns("name", "numMesas","state");
		
		filter.setPlaceholder("Filtrar por nombre");

		filter.setValueChangeMode(ValueChangeMode.LAZY);
		filter.addValueChangeListener(e -> listZonas(e.getValue()));

		grid.asSingleSelect().addValueChangeListener(e -> {
			editor.editZona(e.getValue());
		});

		addNewBtn.addClickListener(e -> editor.editZona(new Zona("", 0L, false)));

		editor.setChangeHandler(() -> {
			editor.setVisible(false);
			listZonas(filter.getValue());
		});

		listZonas(null);
		
    }

    void listZonas(String filterText) {
		if (StringUtils.isEmpty(filterText)) {
			grid.setItems((Collection<Zona>) repo.findAll());
		}
		else {
			grid.setItems(repo.findByNameStartsWithIgnoreCase(filterText));
		}
	}
    
	@Override
    public void enter(ViewChangeEvent event) {
        // This view is constructed in the init() method()
		//grid.getDataProvider().refreshAll();
    }

}
