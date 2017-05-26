package com.proyecto;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity 
public class GridTicket {
	
	public GridTicket(String nombre, Long cantidad, Long precio, boolean tipo) {
		super();
		this.nombre = nombre;
		this.cantidad = cantidad;
		this.precio = precio;
		this.tipo = tipo;
	}

	@Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;
  
	private String nombre;
	
	private Long cantidad;
	
	private Long precio;
	
	boolean tipo;

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

	public Long getPrecio() {
		return precio;
	}

	public void setPrecio(Long precio) {
		this.precio = precio;
	}

	public boolean isTipo() {
		return tipo;
	}

	public void setTipo(boolean tipo) {
		this.tipo = tipo;
	}
}