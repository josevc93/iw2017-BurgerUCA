package com.proyecto;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity // This tells Hibernate to make a table out of this class
public class Worker {
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Integer id;

    private String name;
    
    private String surname;

    private String email;
    
    private String address;
    
    private long telephone_number;
    
    private boolean position;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
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

	public long getTelephone_number() {
		return telephone_number;
	}

	public void setTelephone_number(long telephone_number) {
		this.telephone_number = telephone_number;
	}

	public boolean isPosition() {
		return position;
	}

	public void setPosition(boolean position) {
		this.position = position;
	}


}