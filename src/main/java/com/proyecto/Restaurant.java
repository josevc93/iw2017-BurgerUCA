package com.proyecto;

import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity 
public class Restaurant {
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;

    private String name;
    
    private String address;
    
    @OneToMany(targetEntity=Zona.class)
    private Set<Zona> zonasList;
    
    @OneToMany( targetEntity=Worker.class )
    private Set<Worker> workersList;
    
    protected Restaurant() {}

	public Restaurant(String name, String address, Set<Zona> zonasList) {
		this.name = name;
		this.address = address;
		this.zonasList = zonasList;
	}

	@Override
	public String toString() {
		return String.format("Restaurant[id=%d, name='%s', address='%s']", id, name, address);
	}

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

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public Set<Zona> getZonasList() {
		return zonasList;
	}

	public void setZonasList(Set<Zona> zonasList) {
		this.zonasList = zonasList;
	}

	public Set<Worker> getWorkersList() {
		return workersList;
	}

	public void setWorkersList(Set<Worker> workersList) {
		this.workersList = workersList;
	}
}