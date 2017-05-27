package com.proyecto;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.util.ArrayList;

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
	private final NativeSelect<String> familySelect;
	TextField productImage = new TextField("Image");
	
	Button save = new Button("Guardar");
	Button cancel = new Button("Cancelar");
	Button delete = new Button("Eliminar");
	CssLayout actions = new CssLayout(save, cancel, delete);
	
	Binder<Product> binder = new Binder<>(Product.class);
	
	public ProductEditor(ProductRepository repository) {
		
		this.repository = repository;
		final Image image = new Image();
		
		ArrayList<String> lista = new ArrayList<String>();
		lista.add("comidas");
		lista.add("bebidas");
		lista.add("postres");
		familySelect = new NativeSelect<>("Selecciona producto", lista);
		
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
		addComponents(name, price, iva, familySelect, upload, image, actions, productImage);
		binder.bindInstanceFields(this);
		setSpacing(true);
		actions.setStyleName(ValoTheme.LAYOUT_COMPONENT_GROUP);
		save.setStyleName(ValoTheme.BUTTON_PRIMARY);
		save.setClickShortcut(ShortcutAction.KeyCode.ENTER);
		
		save.addClickListener(e -> insertar(Product));
		delete.addClickListener(e -> repository.delete(Product));
		cancel.addClickListener(e -> editProduct(Product));
		setVisible(false);
	}
	
	public final void insertar(Product p){
		boolean guardar = true;
		String errores = "alert('No se ha podido guardar, debido a los siguientes errores:";
		
		
		if(familySelect.getValue() == null && p.getFamily() == ""){
			errores = errores.concat("\\n - La familia de producto no puede estar vacía.");
			guardar = false;
		}

		if(name.getValue() == ""){
			errores = errores.concat("\\n - El nombre no puede estar vacío.");
			guardar = false;
		}
		
		try {
		    BigDecimal monto = new BigDecimal(price.getValue());
		    if (monto.compareTo(BigDecimal.ZERO) < 0){
		    	errores = errores.concat("\\n - El precio no puede ser negativo.");
		    	guardar = false;
		    }
		} catch (NumberFormatException e) {
			errores = errores.concat("\\n - El precio debe ser numerico.");
			guardar = false;
		}
		
		if(price.getValue() == ""){
			errores = errores.concat("\\n - El precio no puede estar vacío.");
			guardar = false;
		}
		
		try{ 
		     int numero = Integer.parseInt(iva.getValue());
		     if(numero < 0 || numero>100){
		    	 errores = errores.concat("\\n - El iva debe estar entre 0-100.");
				 guardar = false;
		     }
		}catch(NumberFormatException e){ 
			errores = errores.concat("\\n - El iva debe ser numerico.");
			guardar = false;
		} 
		
		if(iva.getValue() == ""){
			errores = errores.concat("\\n - El iva no puede estar vacío.");
			guardar = false;
		}
		
		if(guardar){
			if(familySelect.getValue() != null)
				p.setFamily(familySelect.getValue());
			else
				p.setFamily(p.getFamily());
			repository.save(p);
		}
		else{
			errores = errores.concat("');");
			JavaScript.getCurrent().execute(errores);
		}
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
		delete.addClickListener(e -> h.onChange());
		save.addClickListener(e -> h.onChange());
	}
}