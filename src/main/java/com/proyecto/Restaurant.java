package com.proyecto;

import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import com.proyecto.User.User;

@Entity 
public class Restaurant {
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;

    private String name;
    
    private String address;
    
    @OneToMany(targetEntity=Zona.class)
    private Set<Zona> zonasList;
    
    @OneToMany( targetEntity=User.class )
    private Set<User> usersList;
    
    public Restaurant() {}

	public Restaurant(String name, String address) {
		this.name = name;
		this.address = address;
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

	public Set<User> getUsersList() {
		return usersList;
	}

	public void setUsersList(Set<User> usersList) {
		this.usersList = usersList;
	}

}