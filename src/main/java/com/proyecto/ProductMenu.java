package com.proyecto;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity 
public class ProductMenu {
	public ProductMenu(Long id_menu, Long id_product) {
		super();
		this.id_menu = id_menu;
		this.id_product = id_product;
	}

	@Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;
    
	private Long id_menu;
	
	private Long id_product;

    protected ProductMenu() {}
    
    public Long get_id(){
    	return id;
    }
    
    public void set_id(Long id){
    	this.id = id;
    }

	public Long getId_menu() {
		return id_menu;
	}

	public void setId_menu(Long id_menu) {
		this.id_menu = id_menu;
	}

	public Long getId_product() {
		return id_product;
	}

	public void setId_product(Long id_product) {
		this.id_product = id_product;
	}

	
}