package com.proyecto;

import java.util.ArrayList;
import java.util.Collection;

import com.vaadin.data.Binder;
import com.vaadin.event.ShortcutAction;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.spring.annotation.UIScope;
import com.vaadin.ui.Button;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.ListSelect;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;

@SuppressWarnings("serial")
@SpringComponent
@UIScope
public class RestaurantEditor extends VerticalLayout{
	
	private final RestaurantRepository repository;
	
	private final ZonaRepository repositoryZ;

	private Restaurant restaurant;
	
	TextField name = new TextField("Nombre");
	TextField address = new TextField("Dirección");
	
	Button save = new Button("Guardar");
	Button cancel = new Button("Cancelar");
	Button delete = new Button("Eliminar");
	CssLayout actions = new CssLayout(save, cancel, delete);
	
	private final ListSelect<String> selectZonas = new ListSelect<>("Zonas");
	
	Binder<Restaurant> binder = new Binder<>(Restaurant.class);
	
	public RestaurantEditor(RestaurantRepository repository, ZonaRepository repositoryZ) {
		
		this.repository = repository;
		
		this.repositoryZ = repositoryZ;
		
		Collection<Zona> zonas = repositoryZ.findAll();
		ArrayList<String> listaAct = new ArrayList<String>();
		for (Zona z : zonas) {
			//System.out.println(z.getName());
		    listaAct.add(z.getName());
		}
		
		selectZonas.setItems(listaAct);
		
		addComponents(name, address, selectZonas, actions);
		binder.bindInstanceFields(this);
		setSpacing(true);
		actions.setStyleName(ValoTheme.LAYOUT_COMPONENT_GROUP);
		save.setStyleName(ValoTheme.BUTTON_PRIMARY);
		save.setClickShortcut(ShortcutAction.KeyCode.ENTER);
		
		save.addClickListener(e -> repository.save(restaurant));
		delete.addClickListener(e -> repository.delete(restaurant));
		cancel.addClickListener(e -> editRestaurant(restaurant));
		setVisible(false);
	}
	
	
	public interface ChangeHandler {
		void onChange();
	}

	public final void listarZonas(){
		
	}
	
	public final void editRestaurant(Restaurant r) {
		if (r == null) {
			setVisible(false);
			return;
		}
		final boolean persisted = r.getId() != null;
		if (persisted) {
			restaurant = repository.findOne(r.getId());
		}
		else {
			restaurant = r;
		}
		cancel.setVisible(persisted);

		binder.setBean(restaurant);

		setVisible(true);

		save.focus();
		name.selectAll();
	}

	public void setChangeHandler(ChangeHandler h) {
		save.addClickListener(e -> h.onChange());
		delete.addClickListener(e -> h.onChange());
	}
}