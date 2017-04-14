package com.proyecto;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;

import com.proyecto.Worker;
import com.proyecto.WorkerRepository;
import com.vaadin.server.VaadinRequest;
import com.vaadin.shared.ui.ValueChangeMode;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.ui.Button;
import com.vaadin.ui.Grid;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;

@SpringUI
public class MainController extends UI {

	private final WorkerRepository repo;

	private final WorkerEditor editor;

	final Grid<Worker> grid;

	final TextField filter;

	private final Button addNewBtn;

	@Autowired
	
	public MainController(WorkerRepository repo,  WorkerEditor editor){
		this.editor = editor;
		this.repo = repo;
		this.grid = new Grid<>(Worker.class);
		this.filter = new TextField();
		this.addNewBtn = new Button("Nuevo trabajador");
	}

	@Override
	protected void init(VaadinRequest request) {
		HorizontalLayout actions = new HorizontalLayout(filter, addNewBtn);
		VerticalLayout mainLayout = new VerticalLayout(actions, grid, editor);
		setContent(mainLayout);

		grid.setHeight(300, Unit.PIXELS);
		grid.setColumns("name", "surname", "email");

		filter.setPlaceholder("Filtrar por apellido");

		filter.setValueChangeMode(ValueChangeMode.LAZY);
		filter.addValueChangeListener(e -> listWorkers(e.getValue()));

		grid.asSingleSelect().addValueChangeListener(e -> {
			editor.editWorker(e.getValue());
		});

		addNewBtn.addClickListener(e -> editor.editWorker(new Worker("", "", "", "","","")));

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
}