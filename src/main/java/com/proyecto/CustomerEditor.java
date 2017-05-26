package com.proyecto;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.List;
//import java.util.stream.Collectors;
//import java.util.stream.IntStream;
import java.util.ArrayList;
import java.util.Collection;

import javax.swing.JOptionPane;
import com.vaadin.data.Binder;
import com.vaadin.data.converter.StringToLongConverter;
import com.vaadin.event.ShortcutAction;
import com.vaadin.server.FileResource;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.spring.annotation.UIScope;
import com.vaadin.ui.Button;
import com.vaadin.ui.CheckBox;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.NativeSelect;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;


@SuppressWarnings("serial")
@SpringComponent
@UIScope
public class CustomerEditor extends VerticalLayout{
	
	private final CustomerRepository repo;

	private Customer customer;
	
	TextField telefono = new TextField("Teléfono");
	TextField direccion = new TextField("Dirección");
	
	Button save = new Button("Guardar");
	Button cancel = new Button("Cancelar");
	Button delete = new Button("Eliminar");
	CssLayout actions = new CssLayout(save, cancel, delete);
	
	Binder<Customer> binder = new Binder<>(Customer.class);
	
	public CustomerEditor(CustomerRepository repo) {
		
		this.repo = repo;
		
		addComponents(direccion, telefono, actions);
		
		binder.bindInstanceFields(this);
		
		setSpacing(true);
		actions.setStyleName(ValoTheme.LAYOUT_COMPONENT_GROUP);
		save.setStyleName(ValoTheme.BUTTON_PRIMARY);
		save.setClickShortcut(ShortcutAction.KeyCode.ENTER);
		
		save.addClickListener(e -> repo.save(customer));
		delete.addClickListener(e -> repo.delete(customer));
		cancel.addClickListener(e -> editCustomer(customer));
		setVisible(false);
	}
	
	public interface ChangeHandler {

		void onChange();
	}

	public final void editCustomer(Customer z) {
		if (z == null) {
			setVisible(false);
			return;
		}
		final boolean persisted = z.getId() != null;
		if (persisted) {
			customer = repo.findOne(z.getId());
		}
		else {
			customer = z;
		}
		cancel.setVisible(persisted);

		binder.setBean(customer);

		setVisible(true);

		save.focus();
	}

	public void setChangeHandler(ChangeHandler h) {
		// ChangeHandler is notified when either save or delete
		// is clicked
		save.addClickListener(e -> h.onChange());
		delete.addClickListener(e -> h.onChange());
	}
}
