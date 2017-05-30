package com.proyecto;


import java.util.List;
import java.util.ArrayList;
import java.util.Collection;
import com.vaadin.data.Binder;
import com.vaadin.data.converter.StringToLongConverter;
import com.vaadin.event.ShortcutAction;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.spring.annotation.UIScope;
import com.vaadin.ui.Button;
import com.vaadin.ui.CheckBox;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.JavaScript;
import com.vaadin.ui.NativeSelect;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;


@SuppressWarnings("serial")
@SpringComponent
@UIScope
public class ZonaEditor extends VerticalLayout{
	
	private final RestaurantRepository repositoryRes;
	
	private final ZonaRepository repositoryZona;

	private Zona zona;
	
	TextField name = new TextField("Nombre");
	TextField numMesas = new TextField("Numero de mesas");
	
	CheckBox state = new CheckBox("Estado");
	NativeSelect<String> restaurantSelect;
	
	Button save = new Button("Guardar");
	Button cancel = new Button("Cancelar");
	Button delete = new Button("Eliminar");
	CssLayout actions = new CssLayout(save, cancel, delete);
	
	Binder<Zona> binder = new Binder<>(Zona.class);
	
	public ZonaEditor(ZonaRepository repositoryZona, RestaurantRepository repositoryRes) {
		
		this.repositoryZona = repositoryZona;
		
		this.repositoryRes = repositoryRes;
		
		Collection<Restaurant> restaurants = repositoryRes.findAll();
		ArrayList<String> restaurantList = new ArrayList<String>();
		for(Restaurant r: restaurants){
			System.out.println(r.getName());
			restaurantList.add(r.getName());
		}
		restaurantSelect = new NativeSelect<>("Selecciona restaurante", restaurantList);
		restaurantSelect.setEmptySelectionAllowed(false);

		addComponents(name, state, numMesas, restaurantSelect, actions);
		
		binder.forField(numMesas)
		  .withNullRepresentation("")
		  .withConverter(
		    new StringToLongConverter("Por favor introduce un número"))
		  .bind("numMesas");
		
		binder.bindInstanceFields(this);
		
		setSpacing(true);
		actions.setStyleName(ValoTheme.LAYOUT_COMPONENT_GROUP);
		save.setStyleName(ValoTheme.BUTTON_PRIMARY);
		save.setClickShortcut(ShortcutAction.KeyCode.ENTER);
		
		save.addClickListener(e -> insertarZona(zona));
		delete.addClickListener(e -> repositoryZona.delete(zona));
		cancel.addClickListener(e -> editZona(zona));
		setVisible(false);
	}
	
	public final void insertarZona(Zona z){
		boolean guardar = true;
		String errores = "alert('No se ha podido guardar, debido a los siguientes errores:";
		String cad = restaurantSelect.getValue();
		
		if(cad == null && z.getRestaurante() == null){
			errores = errores.concat("\\n - El restaurante no puede estar vacío.");
			guardar = false;
		}

		if(name.getValue() == ""){
			errores = errores.concat("\\n - El nombre no puede estar vacío.");
			guardar = false;
		}
		
		try{ 
		     int numero = Integer.parseInt(numMesas.getValue());
		     if(numero < 0){
		    	 errores = errores.concat("\\n - El numero de mesas debe ser un numero positivo.");
				 guardar = false;
		     }
		}catch(NumberFormatException e){ 
			errores = errores.concat("\\n - El numero de mesas debe ser numerico.");
			guardar = false;
		} 
		
		if(numMesas.getValue() == ""){
			errores = errores.concat("\\n - El numero de mesa no puede estar vacio.");
			guardar = false;
		}
		
		if(guardar){
			long l = Long.parseLong(numMesas.getValue());
			z.setNumMesas(l);
			
			z.setState(state.getValue());
			
			if(cad != null){
				List<Restaurant> restaurant = repositoryRes.findByNameStartsWithIgnoreCase(cad);
				z.setRestaurante(restaurant.get(0));
			}else
				z.setRestaurante(z.getRestaurante());
		
			repositoryZona.save(z);
		}
		else{
			errores = errores.concat("');");
			JavaScript.getCurrent().execute(errores);
		}
	}
	
	public interface ChangeHandler {

		void onChange();
	}

	public final void editZona(Zona z) {
		if (z == null) {
			setVisible(false);
			return;
		}
		final boolean persisted = z.getId() != null;
		if (persisted) {
			zona = repositoryZona.findOne(z.getId());
		}
		else {
			zona = z;
		}
		cancel.setVisible(persisted);

		binder.setBean(zona);

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