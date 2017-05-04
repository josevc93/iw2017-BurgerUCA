package com.proyecto;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;

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
import com.vaadin.ui.Upload;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Upload.Receiver;
import com.vaadin.ui.Upload.SucceededEvent;
import com.vaadin.ui.Upload.SucceededListener;
import com.vaadin.ui.themes.ValoTheme;

@SuppressWarnings("serial")
@SpringComponent
@UIScope
public class ProductEditor extends VerticalLayout{
	
	private final ProductRepository repository;

	private Product Product;
	
	TextField name = new TextField("Nombre");
	TextField price = new TextField("Precio");
	TextField iva = new TextField("IVA");
	TextField family = new TextField("Familia");
	TextField productImage = new TextField("Image");
	
	Button save = new Button("Guardar");
	Button cancel = new Button("Cancelar");
	Button delete = new Button("Eliminar");
	CssLayout actions = new CssLayout(save, cancel, delete);
	
	Binder<Product> binder = new Binder<>(Product.class);
	
	public ProductEditor(ProductRepository repository) {
		
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
				productImage.setValue(file.toString());
			}
		};
		ImageUploader receiver = new ImageUploader();
		Upload upload = new Upload("Upload it here", receiver);
		upload.setImmediateMode(false);
		upload.addSucceededListener(receiver);
		productImage.setVisible(false);
		addComponents(name, price, iva, family, upload, image, actions, productImage);
		binder.bindInstanceFields(this);
		setSpacing(true);
		actions.setStyleName(ValoTheme.LAYOUT_COMPONENT_GROUP);
		save.setStyleName(ValoTheme.BUTTON_PRIMARY);
		save.setClickShortcut(ShortcutAction.KeyCode.ENTER);
		
		save.addClickListener(e -> repository.save(Product));
		delete.addClickListener(e -> repository.delete(Product));
		cancel.addClickListener(e -> editProduct(Product));
		setVisible(false);
	}
	
	
	public interface ChangeHandler {
		void onChange();
	}

	public final void editProduct(Product r) {
		if (r == null) {
			setVisible(false);
			return;
		}
		final boolean persisted = r.getId() != null;
		if (persisted) {
			Product = repository.findOne(r.getId());
		}
		else {
			Product = r;
		}
		cancel.setVisible(persisted);

		binder.setBean(Product);

		setVisible(true);

		save.focus();
		name.selectAll();
	}

	public void setChangeHandler(ChangeHandler h) {
		save.addClickListener(e -> h.onChange());
		delete.addClickListener(e -> h.onChange());
	}
}