package com.proyecto;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity 
public class OrderLineProduct {

	public OrderLineProduct(int cantidad, double precio, OrderP orderpObj, Product productObj) {
		super();
		this.cantidad = cantidad;
		this.precio = precio;
		this.orderpObj = orderpObj;
		this.productObj = productObj;
	}

	protected OrderLineProduct(){}
	
	@Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;
    
    private int cantidad;
    
    private double precio;
    
    @ManyToOne (fetch=FetchType.LAZY)
    @JoinColumn(name="orderp_ID")
    private OrderP orderpObj;
    
    @ManyToOne (fetch=FetchType.LAZY)
    @JoinColumn(name="product_ID")
    private Product productObj;

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

	public OrderP getOrderPObj() {
		return orderpObj;
	}

	public void setOrderPObj(OrderP OrderPObj) {
		this.orderpObj = OrderPObj;
	}

	public Product getProductObj() {
		return productObj;
	}

	public void setProductObj(Product productObj) {
		this.productObj = productObj;
	}

}