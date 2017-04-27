package com.proyecto;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.spring.annotation.UIScope;
import com.vaadin.spring.annotation.ViewScope;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;

@SuppressWarnings("serial")
@SpringView(name = ViewOrder.VIEW_NAME)
public class ViewOrder extends VerticalLayout implements View {
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