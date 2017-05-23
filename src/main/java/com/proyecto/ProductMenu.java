package com.proyecto;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity 
public class ProductMenu {
	public ProductMenu(Long cantidad, Product productObj, Menu menuObj) {
		super();
		this.setCantidad(cantidad);
		this.menuObj = menuObj;
		this.productObj = productObj;
	}

	@Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;
    
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name = "product_id")
	private Product productObj;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name = "menu_id")
	private Menu menuObj;

	private Long cantidad;
	
    protected ProductMenu() {}
    
    public Long get_id(){
    	return id;
    }
    
    public void set_id(Long id){
    	this.id = id;
    }

	public Long getCantidad() {
		return cantidad;
	}

	public void setCantidad(Long cantidad) {
		this.cantidad = cantidad;
	}

	public Product getProductObj() {
		return productObj;
	}

	public void setProductObj(Product productObj) {
		this.productObj = productObj;
	}

	public Menu getMenuObj() {
		return menuObj;
	}

	public void setMenuObj(Menu menuObj) {
		this.menuObj = menuObj;
	}	
}