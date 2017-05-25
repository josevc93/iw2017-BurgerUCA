package com.proyecto.security;

import org.apache.tomcat.util.net.openssl.ciphers.Authentication;
import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

public final class SecurityUtils {

    private SecurityUtils() {
    }

    public static boolean isLoggedIn() {
        org.springframework.security.core.Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication != null && authentication.isAuthenticated();
    }

    public static boolean hasRole(String role) {
    	
        org.springframework.security.core.Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(authentication != null)
        	System.out.println("role to check"+role+ "--"+authentication.getAuthorities());
        else 
        	System.out.println("no hay session");
        	
		return authentication != null && authentication.getAuthorities().contains(new SimpleGrantedAuthority(role));
    }
    
    public static Collection<? extends GrantedAuthority> roles() {
        org.springframework.security.core.Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        
        if(authentication != null ){
        	return authentication.getAuthorities();
        } else{
        	return null;
        }
    }

}
