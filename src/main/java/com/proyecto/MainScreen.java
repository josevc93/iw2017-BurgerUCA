package com.proyecto;

import javax.annotation.PostConstruct;

//import com.proyecto.User.User;
import com.proyecto.User.UserView;
import com.proyecto.security.SecurityUtils;
import com.vaadin.annotations.Theme;
//import com.vaadin.data.Binder;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewDisplay;
//import com.vaadin.server.VaadinSession;
import com.vaadin.spring.annotation.SpringViewDisplay;
import com.vaadin.ui.Button;
import com.vaadin.ui.Component;
import com.vaadin.ui.CssLayout;
//import com.vaadin.ui.HorizontalLayout;
//import com.vaadin.ui.Label;
import com.vaadin.ui.MenuBar;
import com.vaadin.ui.MenuBar.MenuItem;
import com.vaadin.ui.Panel;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;

@SuppressWarnings("serial")
@Theme("valo")
@SpringViewDisplay
public class MainScreen extends VerticalLayout implements ViewDisplay {

	// private static final long serialVersionUID = 1L;
	private Panel springViewDisplay;

	final CssLayout navigationBar = new CssLayout();

	public void attach() {
		super.attach();
		this.getUI().getNavigator().navigateTo("defaultView");
	}

	@PostConstruct
	void init() {

		final VerticalLayout root = new VerticalLayout();
		// root.addStyleName(ValoTheme.LABEL_H3);
		// final HorizontalLayout root = new HorizontalLayout();
		root.setSizeFull();
		// setContent(root);

		// Creamos la cabecera
		// root.addComponent(new Label("This is the session: " +
		// VaadinSession.getCurrent()));
		// root.addComponent(new Label("This is the UI: " + this.toString()));
		// root.addComponent();
		/*
		 * Button logoutButton = new Button("Logout", event -> logout());
		 * logoutButton.setStyleName(ValoTheme.BUTTON_LINK);
		 * root.addComponent(logoutButton);
		 */

		// getName();
		// String name = user.getFirstName();

		// navigationBar.addStyleName(ValoTheme.LAYOUT_COMPONENT_GROUP);
		navigationBar.addStyleName(ValoTheme.UI_WITH_MENU);
		// navigationBar.addComponent((Component) user);
		// navigationBar.addStyleName(ValoTheme.LAYOUT_HORIZONTAL_WRAPPING);
		navigationBar.setWidth("200px");
		navigationBar.addComponent(createNavigationButton("Trabajadores", UserView.VIEW_NAME));
		navigationBar.addComponent(createNavigationButton("Restaurantes", RestaurantView.VIEW_NAME));
		navigationBar.addComponent(createNavigationButton("Productos", ProductView.VIEW_NAME));
		navigationBar.addComponent(createNavigationButton("Menus", MenuView.VIEW_NAME));
		navigationBar.addComponent(createNavigationButton("Gestión Pedidos", OrderView.VIEW_NAME));
		// navigationBar.addComponent(createNavigationButton("Login",
		// LoginScreen.VIEW_NAME));
		MenuBar logoutMenu = new MenuBar();
		logoutMenu.addItem("Logout", VaadinIcons.SIGN_OUT, selectedItem -> {
			logout();
		});

		logoutMenu.addStyleName("user-menu");
		// logoutMenu.addStyleName("v-layout-margin-left");
		root.addComponent(logoutMenu);
		root.addComponent(navigationBar);

		springViewDisplay = new Panel();
		springViewDisplay.setSizeFull();
		root.addComponent(springViewDisplay);
		root.setExpandRatio(springViewDisplay, 1.0f);

		addComponent(root);

	}

	public void checkAuthorities() {
		System.out.println("Checking security");
		for(int i = 0; i < 4; i++){
			navigationBar.getComponent(i).setVisible(SecurityUtils.hasRole("Gerente"));
		}
		

	}

	private Button createNavigationButton(String caption, final String viewName) {
		Button button = new Button(caption);
		button.addStyleName(ValoTheme.BUTTON_PRIMARY);
		button.setWidth("100%");
		// If you didn't choose Java 8 when creating the project, convert this
		// to an anonymous listener class
		button.addClickListener(event -> getUI().getNavigator().navigateTo(viewName));
		return button;
	}

	@Override
	public void showView(View view) {
		springViewDisplay.setContent((Component) view);
	}

	private void logout() {
		getUI().getPage().reload();
		getSession().close();
	}

}
