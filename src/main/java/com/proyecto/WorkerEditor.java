package com.proyecto;

import org.springframework.beans.factory.annotation.Autowired;

import com.vaadin.data.Binder;
import com.vaadin.data.converter.StringToIntegerConverter;
import com.vaadin.event.ShortcutAction;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.spring.annotation.UIScope;
import com.vaadin.ui.Button;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;

@SpringComponent
@UIScope
public class WorkerEditor extends VerticalLayout {

	private final WorkerRepository repository;

	private Worker worker;

	TextField name = new TextField("Nombre");
	TextField surname = new TextField("Apellido");
	TextField email = new TextField("Email");
	TextField address = new TextField("Dirección");
	TextField telephone_number = new TextField("Numero");
	TextField position = new TextField("Cargo");
	
	Button save = new Button("Guardar");
	Button cancel = new Button("Cancelar");
	Button delete = new Button("Eliminar");
	CssLayout actions = new CssLayout(save, cancel, delete);
	
	Binder<Worker> binder = new Binder<>(Worker.class);
	
	@Autowired
	public WorkerEditor(WorkerRepository repository) {
		this.repository = repository;
		
		addComponents(name, surname, email, address, telephone_number, position, actions);
		
		binder.bindInstanceFields(this);
		
		setSpacing(true);
		actions.setStyleName(ValoTheme.LAYOUT_COMPONENT_GROUP);
		save.setStyleName(ValoTheme.BUTTON_PRIMARY);
		save.setClickShortcut(ShortcutAction.KeyCode.ENTER);
		
		save.addClickListener(e -> repository.save(worker));
		delete.addClickListener(e -> repository.delete(worker));
		cancel.addClickListener(e -> editWorker(worker));
		setVisible(false);
	}

	public interface ChangeHandler {

		void onChange();
	}

	public final void editWorker(Worker c) {
		if (c == null) {
			setVisible(false);
			return;
		}
		final boolean persisted = c.getId() != null;
		if (persisted) {
			worker = repository.findOne(c.getId());
		}
		else {
			worker = c;
		}
		cancel.setVisible(persisted);

		binder.setBean(worker);

		setVisible(true);

		save.focus();
		name.selectAll();
	}

	public void setChangeHandler(ChangeHandler h) {
		// ChangeHandler is notified when either save or delete
		// is clicked
		save.addClickListener(e -> h.onChange());
		delete.addClickListener(e -> h.onChange());
	}

}