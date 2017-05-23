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
	
	public OrderLineMenu(int cantidad, double precio, Order orderObj, Menu menuObj) {
		super();
		this.cantidad = cantidad;
		this.precio = precio;
		this.orderObj = orderObj;
		this.menuObj = menuObj;
	}

	@Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;
    
    private int cantidad;
    
    private double precio;
    
    @ManyToOne (fetch=FetchType.LAZY)
    @JoinColumn(name="order_ID")
    private Order orderObj;
    
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

	public Order getOrderObj() {
		return orderObj;
	}

	public void setOrderObj(Order orderObj) {
		this.orderObj = orderObj;
	}

	public Menu getMenuObj() {
		return menuObj;
	}

	public void setMenuObj(Menu menuObj) {
		this.menuObj = menuObj;
	}


}