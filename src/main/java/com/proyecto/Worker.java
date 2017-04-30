package com.proyecto;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity 
public class Worker {
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;
    
    @ManyToOne
    private Restaurant restaurant;

    private String name;
    
    private String surname;

    private String email;
    
    private String address;
    
    private String telephone_number;
    
    private String position;
    
    private String urlAvatar;
    
    protected Worker() {}

	public Worker(String name, String surname, String email, String address, 
					String telephone_number, String position, String urlAvatar) {
		this.name = name;
		this.surname = surname;
		this.email = email;
		this.address = address;
		this.telephone_number = telephone_number;
		this.position = position;
		this.urlAvatar = urlAvatar;
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

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String adOdress) {
		this.address = adOdress;
	}

	public String getTelephone_number() {
		return telephone_number;
	}

	public void setTelephone_number(String telephone_number) {
		this.telephone_number = telephone_number;
	}

	public String getPosition() {
		return position;
	}

	public void setPosition(String position) {
		this.position = position;
	}
	
	public void setUrlAvatar(String urlAvatar){
		this.urlAvatar = urlAvatar;
	}
	
	public String getUrlAvatar(){
		return urlAvatar;
	}

	@Override
	public String toString() {
		return String.format("Worker[id=%d, name='%s', surname='%s', email='%s', address='%s', telephone_number='%s', position='%s', urlAvatar='%s' ]",
				id, name, surname, email, address, telephone_number, position, urlAvatar);
	}

	public Restaurant getRestaurant() {
		return restaurant;
	}

	public void setRestaurant(Restaurant restaurant) {
		this.restaurant = restaurant;
	}

}