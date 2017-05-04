package com.proyecto;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity 
public class Product {
    public Product(String name, String price, String iva, String family, String image) {
		super();
		this.name = name;
		this.price = price;
		this.iva = iva;
		this.family = family;
		this.productImage = image;
	}

	@Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;
    
    private String name;
    
    private String price;
    
    private String iva;
    
    private String family;
    
    private String productImage;

    protected Product() {}

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


	public String getIva() {
		return iva;
	}


	public void setIva(String iva) {
		this.iva = iva;
	}


	public String getFamily() {
		return family;
	}


	public void setFamily(String family) {
		this.family = family;
	}

	@Override
	public String toString() {
		return "Product [id=" + id + ", name=" + name + ", price=" + price + ", iva=" + iva + ", family=" + family
				+ "]";
	}

	public String getProductImage() {
		return productImage;
	}

	public void setProductImage(String productImage) {
		this.productImage = productImage;
	}

	
}