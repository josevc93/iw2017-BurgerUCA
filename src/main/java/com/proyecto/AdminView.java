package com.proyecto;

import javax.annotation.PostConstruct;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;

@SuppressWarnings("serial")
@SpringView(name = AdminView.VIEW_NAME)
public class AdminView extends VerticalLayout implements View {
    public static final String VIEW_NAME = "admin";

    @PostConstruct
    void init() {
        addComponent(new Label("Bienvenido al panel de administración."));
    }

	@Override
    public void enter(ViewChangeEvent event) {
        // This view is constructed in the init() method()
    }

}