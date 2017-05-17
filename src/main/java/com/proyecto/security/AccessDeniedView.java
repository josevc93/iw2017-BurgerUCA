package com.proyecto.security;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;

public class AccessDeniedView extends VerticalLayout implements View {

    public AccessDeniedView() {
        setMargin(true);
        Label lbl = new Label("You don't have access to this view.");
        lbl.addStyleName(ValoTheme.LABEL_FAILURE);
        lbl.setSizeUndefined();
        addComponent(lbl);
    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {
    }
}
