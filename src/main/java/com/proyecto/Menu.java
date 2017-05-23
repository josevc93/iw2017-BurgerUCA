package com.proyecto;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;

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
    
    @OneToMany(mappedBy = "menuObj", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<ProductMenu> productMenuList = new ArrayList<ProductMenu>();

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

	public List<ProductMenu> getProductMenuList() {
		return productMenuList;
	}

	public void setProductMenuList(List<ProductMenu> productMenuList) {
		this.productMenuList = productMenuList;
	}
    
}