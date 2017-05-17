package com.proyecto;

import javax.annotation.PostConstruct;

import com.proyecto.User.UserView;
import com.vaadin.annotations.Theme;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewDisplay;
import com.vaadin.spring.annotation.SpringViewDisplay;
import com.vaadin.ui.Button;
import com.vaadin.ui.Component;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Panel;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;

@SuppressWarnings("serial")
@Theme("valo")
@SpringViewDisplay
public class MainScreen extends VerticalLayout implements ViewDisplay {

	//private static final long serialVersionUID = 1L;
	private Panel springViewDisplay;
	
	 public void attach() {
		 super.attach();
	     this.getUI().getNavigator().navigateTo("");
	 }
	
	
	@PostConstruct
	void init() {
		
		final VerticalLayout root = new VerticalLayout();
		//final HorizontalLayout root = new HorizontalLayout();
		root.setSizeFull();
		
		// Creamos la cabecera 
		//root.addComponent(new Label("This is the session: " + VaadinSession.getCurrent()));
		//root.addComponent(new Label("This is the UI: " + this.toString()));
		
		Button logoutButton = new Button("Logout", event -> logout());
		logoutButton.setStyleName(ValoTheme.BUTTON_LINK);
		root.addComponent(logoutButton);

		// Creamos la barra de navegación
		final CssLayout navigationBar = new CssLayout();
		navigationBar.addStyleName(ValoTheme.LAYOUT_HORIZONTAL_WRAPPING);
        navigationBar.setWidth("100px");
        navigationBar.addComponent(createNavigationButton("Gestión Pedidos",  OrderView.VIEW_NAME));
        navigationBar.addComponent(createNavigationButton("Administración", AdminView.VIEW_NAME));
        navigationBar.addComponent(createNavigationButton("Trabajadores", UserView.VIEW_NAME));
        navigationBar.addComponent(createNavigationButton("Restaurantes", RestaurantView.VIEW_NAME));
        navigationBar.addComponent(createNavigationButton("Productos", ProductView.VIEW_NAME));
        navigationBar.addComponent(createNavigationButton("Menus", MenuView.VIEW_NAME));
        //navigationBar.addComponent(createNavigationButton("Login", LoginScreen.VIEW_NAME));
        root.addComponent(navigationBar);
        
        springViewDisplay = new Panel();
        springViewDisplay.setSizeFull();
        root.addComponent(springViewDisplay);
        root.setExpandRatio(springViewDisplay, 1.0f);

		addComponent(root);
		
	}

	private Button createNavigationButton(String caption, final String viewName) {
		Button button = new Button(caption);
		button.addStyleName(ValoTheme.BUTTON_SMALL);
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
