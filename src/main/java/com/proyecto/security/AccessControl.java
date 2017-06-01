package com.proyecto.security;

import org.springframework.stereotype.Component;

import com.vaadin.spring.access.ViewAccessControl;
import com.vaadin.ui.UI;

@Component
public class AccessControl implements ViewAccessControl {

	@Override
	public boolean isAccessGranted(UI ui, String beanName) {
		// TODO Auto-generated method stub
		System.out.println("COMPROBANDO " + beanName + " PARA USUARIO CON ROLES: "+SecurityUtils.roles());

    	if(SecurityUtils.hasRole("Gerente")){
    		return true;
    	} else if(beanName.equals("defaultView")) {
    		return true;
    	} else if(beanName.equals("orderPView")){
    		return SecurityUtils.hasRole("Camarero");
    	} else if(beanName.equals("customerView")){
    		return SecurityUtils.hasRole("Camarero");
    	} else {
    		return false;
    	}
		
		/*if(SecurityUtils.hasRole("Camarero") && beanName.equals("pedidos")){// || beanName.equals("defaultView"))){
			return true;
		} else if(SecurityUtils.hasRole("Camarero") && beanName.equals("defaultView")){
			return true;
		} else if(SecurityUtils.hasRole("Gerente")){
			return true;
		} else {
			return false;
		}*/
		
		/*if(SecurityUtils.hasRole("Camarero")){
			if(beanName.equals("menus")){
				return false;
			}/* else if(beanName.equals("userView")){
				return false;
			}*/ /*else if(beanName.equals("restaurantes")){
				return false;
			} else if(beanName.equals("productos")){
				return false;
			} else {
				return true;
			}
		} else {
			return true;
		}*/
		
        
	}

}
