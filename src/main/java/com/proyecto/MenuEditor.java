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
public class MenuEditor extends VerticalLayout{
	
	private final MenuRepository repository;
	
	private final ProductRepository repoP;
	
	private final ListSelect<String> select = new ListSelect<>("Añadir");
	
	private final ListSelect<String> selectAct = new ListSelect<>("Actuales");

	private Menu Menu;
	
	TextField name = new TextField("Nombre");
	TextField price = new TextField("Precio");
	
	Button save = new Button("Guardar");
	Button cancel = new Button("Cancelar");
	Button delete = new Button("Eliminar");
	CssLayout actions = new CssLayout(save, cancel, delete);
	
	Binder<Menu> binder = new Binder<>(Menu.class);
	
	
	public MenuEditor(MenuRepository repository, ProductRepository repoProduct) {
		this.repoP = repoProduct;
		this.repository = repository;
		/*Collection<Menu> menus = repository.listadoMenus();
		ArrayList<String> lista = new ArrayList<String>();
		for(Menu p: menus)
			lista.add(p.getName());
		
		select.setItems(lista);*/
		addComponents(name, price, actions, select, selectAct);
		binder.bindInstanceFields(this);
		setSpacing(true);
		actions.setStyleName(ValoTheme.LAYOUT_COMPONENT_GROUP);
		save.setStyleName(ValoTheme.BUTTON_PRIMARY);
		save.setClickShortcut(ShortcutAction.KeyCode.ENTER);
		
		save.addClickListener(e -> repository.save(Menu));
		delete.addClickListener(e -> repository.delete(Menu));
		cancel.addClickListener(e -> editMenu(Menu));
		setVisible(false);
	}
	
	
	public interface ChangeHandler {
		void onChange();
	}

	public final void editMenu(Menu r) {
		if (r == null) {
			setVisible(false);
			return;
		}
		final boolean persisted = r.getId() != null;
		if (persisted) {
			Menu = repository.findOne(r.getId());
		}
		else {
			Menu = r;
		}
		cancel.setVisible(persisted);

		binder.setBean(Menu);

		setVisible(true);

		save.focus();
		name.selectAll();
	}

	public void setChangeHandler(ChangeHandler h) {
		save.addClickListener(e -> h.onChange());
		delete.addClickListener(e -> h.onChange());
	}
}