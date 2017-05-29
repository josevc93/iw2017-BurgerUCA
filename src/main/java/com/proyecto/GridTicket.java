package com.proyecto;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity 
public class GridTicket {
	
	public GridTicket(String nombre, Long cantidad, Double precio, boolean tipo) {
		super();
		this.nombre = nombre;
		this.cantidad = cantidad;
		this.precio = precio;
		this.tipo = tipo;
	}

	protected GridTicket(){}
	
	@Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;
  
	private String nombre;
	
	private Long cantidad;
	
	private Double precio;
	
	boolean tipo;
	
	 @ManyToOne (fetch=FetchType.LAZY)
	 @JoinColumn(name="orderP_ID")
	 private OrderP orderPObj;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public Long getCantidad() {
		return cantidad;
	}

	public void setCantidad(Long cantidad) {
		this.cantidad = cantidad;
	}

	public Double getPrecio() {
		return precio;
	}

	public void setPrecio(Double precio) {
		this.precio = precio;
	}

	public boolean isTipo() {
		return tipo;
	}

	public void setTipo(boolean tipo) {
		this.tipo = tipo;
	}
}