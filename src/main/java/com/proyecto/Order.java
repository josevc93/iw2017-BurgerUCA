package com.proyecto;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

/*Pedido añadir un boolean para saber si es a domicilio
 * Solo debería tener cliente si es a domicilio*/

@Entity 
public class Order {

	public Order(String name, boolean state, Long numMesas, Worker worker, Customer customer,
			List<OrderLineProduct> orderLineProductList, List<OrderLineMenu> orderLineMenuList) {
		super();
		this.name = name;
		this.state = state;
		this.numMesas = numMesas;
		this.worker = worker;
		this.customer = customer;
		this.orderLineProductList = orderLineProductList;
		this.orderLineMenuList = orderLineMenuList;
	}

	protected Order(){}
	
	@Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;
    
    private String name;
    
    private boolean state;

    private Long numMesas;
    
    
    
    @ManyToOne (fetch=FetchType.LAZY)
    @JoinColumn(name="worker_ID")
    private Worker worker;
    
    @ManyToOne (fetch=FetchType.LAZY)
    @JoinColumn(name="customer_ID")
    private Customer customer;
    
    @OneToMany(mappedBy = "orderObj", fetch = FetchType.LAZY)
    private List<OrderLineProduct> orderLineProductList = new ArrayList<OrderLineProduct>();
    
    @OneToMany(mappedBy = "menuObj", fetch = FetchType.LAZY)
    private List<OrderLineMenu> orderLineMenuList = new ArrayList<OrderLineMenu>();

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

	public Long getNumMesas() {
		return numMesas;
	}

	public void setNumMesas(Long numMesas) {
		this.numMesas = numMesas;
	}

	public Worker getWorker() {
		return worker;
	}

	public void setWorker(Worker worker) {
		this.worker = worker;
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public List<OrderLineProduct> getOrderLineProductList() {
		return orderLineProductList;
	}

	public void setOrderLineProductList(List<OrderLineProduct> orderLineProductList) {
		this.orderLineProductList = orderLineProductList;
	}

	public List<OrderLineMenu> getOrderLineMenuList() {
		return orderLineMenuList;
	}

	public void setOrderLineMenuList(List<OrderLineMenu> orderLineMenuList) {
		this.orderLineMenuList = orderLineMenuList;
	}

}