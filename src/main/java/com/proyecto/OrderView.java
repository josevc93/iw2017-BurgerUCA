package com.proyecto;

import javax.annotation.PostConstruct;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;

@SuppressWarnings("serial")
@SpringView(name = OrderView.VIEW_NAME)
public class OrderView extends VerticalLayout implements View {
    public static final String VIEW_NAME = "pedidos";

    @PostConstruct
    void init() {
        addComponent(new Label("Bienvenido a la gestión de pedidos."));
    }

	@Override
    public void enter(ViewChangeEvent event) {
        // This view is constructed in the init() method()
    }

}