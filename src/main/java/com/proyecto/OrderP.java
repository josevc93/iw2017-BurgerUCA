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

import com.proyecto.User.User;

@Entity 
public class OrderP {


	public OrderP(boolean state, boolean takeAway, Long numMesa, Double coste, Zona zona, User user, Customer customer,
			List<OrderLineProduct> orderLineProductList, List<OrderLineMenu> orderLineMenuList,
			List<GridTicket> gridTicketList) {
		super();
		this.state = state;
		this.takeAway = takeAway;
		this.numMesa = numMesa;
		this.coste = coste;
		this.zona = zona;
		this.user = user;
		this.customer = customer;
		this.orderLineProductList = orderLineProductList;
		this.orderLineMenuList = orderLineMenuList;
		GridTicketList = gridTicketList;
	}

	protected OrderP(){}
	
	@Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;
    
    private boolean state;
    
    private boolean takeAway;

    private Long numMesa;
    
    private Double coste;
    
    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="zona_id")
    private Zona zona;
    
    @ManyToOne (fetch=FetchType.LAZY)
    @JoinColumn(name="worker_ID")
    private User user;
    
    @ManyToOne (fetch=FetchType.LAZY)
    @JoinColumn(name="customer_ID")
    private Customer customer;
    
    @OneToMany(mappedBy = "orderpObj", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<OrderLineProduct> orderLineProductList = new ArrayList<OrderLineProduct>();
    
    @OneToMany(mappedBy = "menuObj", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<OrderLineMenu> orderLineMenuList = new ArrayList<OrderLineMenu>();
    
    @OneToMany(mappedBy = "orderPObj", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<GridTicket> GridTicketList = new ArrayList<GridTicket>();

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public boolean isState() {
		return state;
	}

	public void setState(boolean state) {
		this.state = state;
	}

	public Long getNumMesa() {
		return numMesa;
	}

	public User getUser() {
		return user;
	}

	public void setWorker(User user) {
		this.user = user;
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

	public Zona getZona() {
		return zona;
	}

	public void setZona(Zona zona) {
		this.zona = zona;
	}

	public Double getCoste() {
		return coste;
	}

	public void setCoste(Double coste) {
		this.coste = coste;
	}

	public void setNumMesa(Long numMesa) {
		this.numMesa = numMesa;
	}

	public boolean isTakeAway() {
		return takeAway;
	}

	public void setTakeAway(boolean takeAway) {
		this.takeAway = takeAway;
	}

	public List<GridTicket> getGridTicketList() {
		return GridTicketList;
	}

	public void setGridTicketList(List<GridTicket> gridTicketList) {
		GridTicketList = gridTicketList;
	}

	public void setUser(User user) {
		this.user = user;
	}

}