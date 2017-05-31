package com.proyecto.User;


import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

//import java.util.List;
//import java.util.stream.Collectors;
//import java.util.stream.IntStream;
import com.proyecto.*;
import javax.swing.JOptionPane;
import com.vaadin.data.Binder;
import com.vaadin.event.ShortcutAction;
import com.vaadin.server.FileResource;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.spring.annotation.UIScope;
import com.vaadin.ui.Button;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.Image;
import com.vaadin.ui.JavaScript;
import com.vaadin.ui.NativeSelect;
import com.vaadin.ui.PasswordField;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;
import com.vaadin.ui.Upload;
import com.vaadin.ui.Upload.Receiver;
import com.vaadin.ui.Upload.SucceededEvent;
import com.vaadin.ui.Upload.SucceededListener;

@SuppressWarnings("serial")
@SpringComponent
@UIScope
public class UserEditor extends VerticalLayout{
	
	private final UserService service;
	
	private final RestaurantRepository repositoryRes;

	private User user;
	
	TextField firstName = new TextField("Nombre (*)");
	TextField lastName = new TextField("Apellido (*)");
	TextField userName = new TextField("Nombre de usuario (*)");
	PasswordField password = new PasswordField("Contraseña (*)");
	TextField email = new TextField("Email (*)");
	TextField address = new TextField("Dirección");
	TextField telephone_number = new TextField("Numero");
	NativeSelect<String> cargoSelect;
	TextField urlAvatar = new TextField();
	NativeSelect<String> restaurantSelect;
	

	Button save = new Button("Guardar");
	Button cancel = new Button("Cancelar");
	Button delete = new Button("Eliminar");
	CssLayout actions = new CssLayout(save, cancel, delete);
	
	Binder<User> binder = new Binder<>(User.class);
	
	public UserEditor(UserService service, RestaurantRepository repositoryRes) {
		
		this.service = service;
		this.repositoryRes = repositoryRes;

		final Image image = new Image();
		
		List<String> cargos = new ArrayList<String>();
		cargos.add("Gerente");
		cargos.add("Camarero");
		cargoSelect = new NativeSelect<>("Cargo", cargos);
		
		
		class ImageUploader implements Receiver, SucceededListener {
			
			public File file;
			public OutputStream receiveUpload(String filename, String mimeType){
				FileOutputStream fos = null;
				try{
					file = new File("src/img/"+filename);
					fos = new FileOutputStream(file);
				}catch(final java.io.FileNotFoundException e){
					JOptionPane.showMessageDialog(null, "Error al subir el fichero");
					return null;
				}
				return fos;
			}
	
			public void uploadSucceeded(SucceededEvent event) {
				System.out.println("Fichero subido correctamente");
				image.setVisible(true);
				image.setSource(new FileResource(file));
				image.setWidth(200, Unit.PIXELS);
				image.setHeight(200, Unit.PIXELS);
				urlAvatar.setValue(file.toString());
			}
		};
		ImageUploader receiver = new ImageUploader();
		Upload upload = new Upload("Upload it here", receiver);
		upload.setImmediateMode(false);
		upload.addSucceededListener(receiver);
		urlAvatar.setVisible(false);
		
		Collection<Restaurant> restaurants = repositoryRes.findAll();
		ArrayList<String> restaurantList = new ArrayList<>();
		for(Restaurant r: restaurants)
			restaurantList.add(r.getName());
		
		restaurantSelect = new NativeSelect<>("Selecciona restaurante", restaurantList);
		restaurantSelect.setEmptySelectionAllowed(false);

		addComponents(firstName, lastName, userName , password, email, address, telephone_number,  cargoSelect, restaurantSelect, upload, image, actions, urlAvatar);
		
		binder.bindInstanceFields(this);
		
		setSpacing(true);
		actions.setStyleName(ValoTheme.LAYOUT_COMPONENT_GROUP);
		save.setStyleName(ValoTheme.BUTTON_PRIMARY);
		save.setClickShortcut(ShortcutAction.KeyCode.ENTER);
		
		save.addClickListener(e -> insertarTrabajador(user));
		delete.addClickListener(e -> service.delete(user));
		cancel.addClickListener(e -> editUser(user));
		setVisible(false);
	}
	
	public final void insertarTrabajador(User u){
		boolean guardar = true;
		String errores = "alert('No se ha podido guardar, debido a los siguientes errores:";
		
		if(cargoSelect.getValue()==null && u.getPosition()==""){
			errores = errores.concat("\\n - El cargo no puede estar vacío.");
			guardar = false;
		}
		
		if(restaurantSelect.getValue()==null && u.getRestaurant().getId()==null){
			errores = errores.concat("\\n - El restaurante no puede estar vacío.");
			guardar = false;
		}
		
		if(email.getValue() == ""){
			errores = errores.concat("\\n - El email no debe estar vacío");
			guardar = false;
		}else if(!email.getValue().contains("@")){
			errores = errores.concat("\\n - El email debe de ser de la siguiente forma: examle@example.com");
			guardar = false;
		}
		
		if(firstName.getValue() == ""){
			errores = errores.concat("\\n - El nombre no debe estar vacío.");
			guardar = false;
		}
		
		if(lastName.getValue() == ""){
			errores = errores.concat("\\n - El apellido no debe estar vacío.");
			guardar = false;
		}
		
		if(userName.getValue() == ""){
			errores = errores.concat("\\n - El nombre de usuario no debe estar vacío.");
			guardar = false;
		}
		
		if(password.getValue() == ""){
			errores = errores.concat("\\n - La contraseña no debe estar vacía.");
			guardar = false;
		}
		
		if(telephone_number.getValue() != ""){
			try{ 
			     int telephone = Integer.parseInt(telephone_number.getValue());
			     if(telephone < 0){
			    	 errores = errores.concat("\\n - El teléfono no puede ser negativo.");
					 guardar = false;
			     }
			}catch(NumberFormatException e){ 
				errores = errores.concat("\\n - El teléfono debe de ser numérico.");
				guardar = false;
			} 
		}
		
		if(guardar){
			if(cargoSelect.getValue()==null)
				u.setPosition(u.getPosition());
			else
				u.setPosition(cargoSelect.getValue().toString());
			
			if(restaurantSelect.getValue()==null)
				u.setRestaurant(u.getRestaurant());
			else{
				List<Restaurant> restaurant = repositoryRes.findByNameStartsWithIgnoreCase(restaurantSelect.getValue().toString());
				u.setRestaurant(restaurant.get(0)); 
			}
				
			service.save(u);
		}else{
			errores = errores.concat("');");
			JavaScript.getCurrent().execute(errores);
		}
	}
	
	
	public interface ChangeHandler {

		void onChange();
	}

	public final void editUser(User c) {
		if (c == null) {
			setVisible(false);
			return;
		}
		final boolean persisted = c.getId() != null;
		if (persisted) {
			user = service.findOne(c.getId());
		}
		else {
			user = c;
		}
		cancel.setVisible(persisted);

		binder.setBean(user);

		setVisible(true);

		save.focus();
		firstName.selectAll();
	}

	public void setChangeHandler(ChangeHandler h) {
		// ChangeHandler is notified when either save or delete
		// is clicked
		save.addClickListener(e -> h.onChange());
		delete.addClickListener(e -> h.onChange());
	}
}