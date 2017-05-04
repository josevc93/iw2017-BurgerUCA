package com.proyecto;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
//import java.util.List;
//import java.util.stream.Collectors;
//import java.util.stream.IntStream;

import javax.swing.JOptionPane;
import com.vaadin.data.Binder;
import com.vaadin.event.ShortcutAction;
import com.vaadin.server.FileResource;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.spring.annotation.UIScope;
import com.vaadin.ui.Button;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.Image;
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
public class WorkerEditor extends VerticalLayout{
	
	private final WorkerRepository repository;

	private Worker worker;
	
	TextField name = new TextField("Nombre");
	TextField surname = new TextField("Apellido");
	TextField email = new TextField("Email");
	TextField address = new TextField("Dirección");
	TextField telephone_number = new TextField("Numero");
	TextField position = new TextField("Cargo");
	TextField urlAvatar = new TextField();
	

	Button save = new Button("Guardar");
	Button cancel = new Button("Cancelar");
	Button delete = new Button("Eliminar");
	CssLayout actions = new CssLayout(save, cancel, delete);
	
	Binder<Worker> binder = new Binder<>(Worker.class);
	
	public WorkerEditor(WorkerRepository repository) {
		
		this.repository = repository;
		final Image image = new Image();
		
		
		class ImageUploader implements Receiver, SucceededListener {
			
			public File file;
			public OutputStream receiveUpload(String filename, String mimeType){
				FileOutputStream fos = null;
				try{
					file = new File("src/img/"+filename);
					//System.out.println("Nombre fichero "+file);
					fos = new FileOutputStream(file);
				}catch(final java.io.FileNotFoundException e){
					JOptionPane.showMessageDialog(null, "Error al subir el fichero");
					 /*Notification.show("No se pudo abrir el fichero", 
									Notification.Type.ERROR_MESSAGE);*/
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

		addComponents(name, surname, email, address, telephone_number, position, upload, image, actions, urlAvatar);
		
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