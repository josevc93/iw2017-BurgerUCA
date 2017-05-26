package com.proyecto;

import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

@Entity 
public class Zona {
  
	public Zona(String name, Long numMesas, boolean state) {
		super();
		this.name = name;
		this.numMesas = numMesas;
		this.state = state;
	}

	protected Zona(){}
	@Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;
    
    private String name;
    
    private boolean state;

    private Long numMesas;
    
    @ManyToOne
    private Restaurant restaurante;

    
    public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public boolean isState() {
		return state;
	}

	public void setState(boolean state) {
		this.state = state;
	}
	
	@Override
	public String toString() {
		return "Zona [id=" + id + ", name=" + name + ", state=" + state + "]";
	}

	public Restaurant getRestaurante() {
		return restaurante;
	}

	public void setRestaurante(Restaurant restaurante) {
		this.restaurante = restaurante;
	}

	public Long getNumMesas() {
		return numMesas;
	}

	public void setNumMesas(Long numMesas) {
		this.numMesas = numMesas;
	}
	
    
}