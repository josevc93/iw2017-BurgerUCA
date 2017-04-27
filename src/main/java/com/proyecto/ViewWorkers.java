package com.proyecto;

import java.util.Collection;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.shared.ui.ValueChangeMode;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.spring.annotation.UIScope;
import com.vaadin.spring.annotation.ViewScope;
import com.vaadin.ui.Button;
import com.vaadin.ui.Grid;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;

@SuppressWarnings("serial")
@SpringView(name = ViewWorkers.VIEW_NAME)
public class ViewWorkers extends VerticalLayout implements View {
    public static final String VIEW_NAME = "trabajadores";

    private final WorkerRepository repo;

	private final WorkerEditor editor;

	final Grid<Worker> grid;

	final TextField filter;

	private final Button addNewBtn;

	@Autowired
	public ViewWorkers(WorkerRepository repo,  WorkerEditor editor){
		this.editor = editor;
		this.repo = repo;
		this.grid = new Grid<>(Worker.class);
		this.filter = new TextField();
		this.addNewBtn = new Button("Nuevo trabajador");
	}
    
    @PostConstruct
    void init() {
        HorizontalLayout actions = new HorizontalLayout(filter, addNewBtn);
		VerticalLayout mainLayout = new VerticalLayout(actions, grid, editor);
		addComponent(mainLayout);

		grid.setHeight(300, Unit.PIXELS);
		grid.setColumns("name", "surname", "email");

		filter.setPlaceholder("Filtrar por apellido");

		filter.setValueChangeMode(ValueChangeMode.LAZY);
		filter.addValueChangeListener(e -> listWorkers(e.getValue()));

		grid.asSingleSelect().addValueChangeListener(e -> {
			editor.editWorker(e.getValue());
		});

		addNewBtn.addClickListener(e -> editor.editWorker(new Worker("", "", "", "","","", "")));

		editor.setChangeHandler(() -> {
			editor.setVisible(false);
			listWorkers(filter.getValue());
		});

		listWorkers(null);
    }

    void listWorkers(String filterText) {
		if (StringUtils.isEmpty(filterText)) {
			grid.setItems((Collection<Worker>) repo.findAll());
		}
		else {
			grid.setItems(repo.findBySurnameStartsWithIgnoreCase(filterText));
		}
	}
    
	@Override
    public void enter(ViewChangeEvent event) {
        // This view is constructed in the init() method()
    }

}