package com.proyecto;
 
import javax.annotation.PostConstruct;
 
import com.proyecto.User.UserView;
import com.proyecto.security.SecurityUtils;
import com.vaadin.annotations.Theme;
//import com.vaadin.data.Binder;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewDisplay;
import com.vaadin.server.ThemeResource;
import com.vaadin.server.VaadinSession;
import com.vaadin.server.Sizeable.Unit;
//import com.vaadin.server.VaadinSession;
import com.vaadin.spring.annotation.SpringViewDisplay;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Component;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Image;
import com.vaadin.ui.Label;
//import com.vaadin.ui.HorizontalLayout;
//import com.vaadin.ui.Label;
import com.vaadin.ui.MenuBar;
import com.vaadin.ui.MenuBar.MenuItem;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Notification.Type;
import com.vaadin.ui.Panel;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;
 
@SuppressWarnings("serial")
@Theme("valo")
@SpringViewDisplay
public class MainScreen extends VerticalLayout implements ViewDisplay {
 
	// private static final long serialVersionUID = 1L;
	private Panel springViewDisplay;
	
	private Panel panel;
	
	private MenuItem settingsItem;
 
	final CssLayout navigationBar = new CssLayout();
 
	public void attach() {
		super.attach();
		this.getUI().getNavigator().navigateTo("defaultView");
		Menu();
		
	}
	
	private void Menu()
	{
		 final CssLayout menuContent = new CssLayout();
	     menuContent.addStyleName("sidebar");
	     menuContent.addStyleName(ValoTheme.MENU_PART);
	     menuContent.addStyleName("no-vertical-drag-hints");
	     menuContent.addStyleName("no-horizontal-drag-hints");
	     menuContent.setWidth(null);
	     menuContent.setHeight("100%");
	}
 
	@PostConstruct
	void init() {
 
		final VerticalLayout root = new VerticalLayout();
		//final HorizontalLayout root = new HorizontalLayout();
		//root.addStyleName(ValoTheme.MENU_LOGO);
		root.setSizeFull();
		/*-------------------------------*/
		
 
		/*----------------------------------------*/
		
		navigationBar.addStyleName(ValoTheme.LAYOUT_COMPONENT_GROUP);
		//navigationBar.setHeight("100%");
		// navigationBar.addComponent((Component) user);
		//navigationBar.addStyleName(ValoTheme.LAYOUT_HORIZONTAL_WRAPPING);
		
		
		/*-----------------------------------------------------------------*/
		
		navigationBar.setWidth("153px");
		navigationBar.addComponent(createNavigationButton("Inicio", DefaultView.VIEW_NAME));
		navigationBar.addComponent(createNavigationButton("Trabajadores", UserView.VIEW_NAME));
		navigationBar.addComponent(createNavigationButton("Restaurantes", RestaurantView.VIEW_NAME));
		navigationBar.addComponent(createNavigationButton("Zonas", ZonaView.VIEW_NAME));
		navigationBar.addComponent(createNavigationButton("Productos", ProductView.VIEW_NAME));
		navigationBar.addComponent(createNavigationButton("Menus", MenuView.VIEW_NAME));
		navigationBar.addComponent(createNavigationButton("Clientes", CustomerView.VIEW_NAME));;
		navigationBar.addComponent(createNavigationButton("Gestión Pedidos", OrderPView.VIEW_NAME));
		
		/*---------------------*/
		MenuBar logoutMenu = new MenuBar();
		logoutMenu.addItem("Logout", VaadinIcons.SIGN_OUT, selectedItem -> {
			logout();
		});
		navigationBar.addComponent(logoutMenu);
		logoutMenu.addStyleName("user-menu");
		/*-----------------------------*/
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
		for(int i = 1; i <= 5; i++){
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
 
 
