package com.proyecto;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity 
public class OrderLineMenu {
	
	public OrderLineMenu(int cantidad, double precio, OrderP orderpObj, Menu menuObj) {
		super();
		this.cantidad = cantidad;
		this.precio = precio;
		this.orderpObj = orderpObj;
		this.menuObj = menuObj;
	}

	@Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;
    
    private int cantidad;
    
    private double precio;
    
    @ManyToOne (fetch=FetchType.LAZY)
    @JoinColumn(name="order_ID")
    private OrderP orderpObj;
    
    @ManyToOne (fetch=FetchType.LAZY)
    @JoinColumn(name="menu_ID")
    private Menu menuObj;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public int getCantidad() {
		return cantidad;
	}

	public void setCantidad(int cantidad) {
		this.cantidad = cantidad;
	}

	public double getPrecio() {
		return precio;
	}

	public void setPrecio(double precio) {
		this.precio = precio;
	}

	public OrderP getOrderObj() {
		return orderpObj;
	}

	public void setOrderObj(OrderP orderObj) {
		this.orderpObj = orderObj;
	}

	public Menu getMenuObj() {
		return menuObj;
	}

	public void setMenuObj(Menu menuObj) {
		this.menuObj = menuObj;
	}


}