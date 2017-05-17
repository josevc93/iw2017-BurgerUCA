package com.proyecto.User;

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

//@SuppressWarnings("serial")
//@Theme("valo")
@SpringView(name = UserView.VIEW_NAME)
public class UserView extends VerticalLayout implements View {
    public static final String VIEW_NAME = "userView";

    //private final UserRepository repo;

	private final UserEditor editor;

	final Grid<User> grid;

	final TextField filter;

	private final Button addNewBtn;

	private final UserService service;
	
	@Autowired
	public UserView(UserService service,  UserEditor editor){
		this.editor = editor;
		this.service = service;
		this.grid = new Grid<>(User.class);
		this.filter = new TextField();
		this.addNewBtn = new Button("Nuevo trabajador");
		addNewBtn.addStyleName(ValoTheme.BUTTON_PRIMARY);
	}
    
    @PostConstruct
    void init() {
        HorizontalLayout actions = new HorizontalLayout(filter, addNewBtn);
        HorizontalLayout mainLayout = new HorizontalLayout(actions, grid, editor);
        VerticalLayout all = new VerticalLayout(actions, mainLayout);
		addComponent(all);

		grid.setColumns("firstName", "lastName", "email", "position");

		filter.setPlaceholder("Filtrar por apellido");

		filter.setValueChangeMode(ValueChangeMode.LAZY);
		filter.addValueChangeListener(e -> listWorkers(e.getValue()));

		grid.asSingleSelect().addValueChangeListener(e -> {
			editor.editUser(e.getValue());
		});

		addNewBtn.addClickListener(e -> editor.editUser(new User("", "", "", "", "", "", "", "")));

		editor.setChangeHandler(() -> {
			editor.setVisible(false);
			listWorkers(filter.getValue());
		});

		listWorkers(null);
    }

    void listWorkers(String filterText) {
		if (StringUtils.isEmpty(filterText)) {
			grid.setItems((Collection<User>) service.findAll());
		}
		else {
			grid.setItems(service.findByLastNameStartsWithIgnoreCase(filterText));
		}
	}
    
	@Override
    public void enter(ViewChangeEvent event) {
        // This view is constructed in the init() method()
    }

}