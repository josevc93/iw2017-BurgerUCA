package com.proyecto;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity 
public class Menu {
    public Menu(String name, String price) {
		super();
		this.name = name;
		this.price = price;
	}

	@Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;

    protected Menu() {}
    
    private String name;
    
    private String price;

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

	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
	}

	@Override
	public String toString() {
		return "Menu [id=" + id + ", name=" + name + ", price=" + price + "]";
	}
    
}