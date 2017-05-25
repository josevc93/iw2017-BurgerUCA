package com.proyecto.User;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;



@Entity 
public class User implements UserDetails{
	@Id
	@GeneratedValue
	private Long id;

	private String firstName;

	private String lastName;
	
	private String username;

	private String email;
	
	private String address;
	
	private String telephone_number;
	
	private String position;
	
	private String urlAvatar;
	
	private String password;

	protected User() {
	}

	public User(String firstName, String lastName, String username, String email, String address, String telephone_number, String position, String urlAvatar) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.username = username;
		this.email = email;
		this.address = address;
		this.telephone_number = telephone_number;
		this.position = position;
		this.urlAvatar = urlAvatar;
	}

	public User(String firstName, String lastName) {
		this(firstName,lastName,firstName, null, null, null, null, null);
	}

	public Long getId() {
		return id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username= username;
	}
	
	
	public void setPassword(String password) {
		this.password = password;
	}
	
	public void setEmail(String email){
		this.email = email;
	}
	
	public String getEmail(){
		return email;
	}
	
	public void setAddress(String address){
		this.address = address;
	}
	
	public String getAddress(){
		return address;
	}
	
	public void setTelephone_number(String telephone_number){
		this.telephone_number = telephone_number;
	}
	
	public String getTelephone_number(){
		return telephone_number;
	}
	
	public void setPosition(String position){
		this.position = position;
	}
	
	public String getPosition(){
		return position;
	}
	
	public void setUrlavatar(String urlAvatar){
		this.urlAvatar = urlAvatar;
	}
	
	public String getUrlavatar(){
		return urlAvatar;
	}
	
	@Override
	public String toString() {
		return String.format("User[id=%d, firstName='%s', lastName='%s', username='%s', email='%s', address='%s', telephone_number='%s', position='%s' , urlAvatar='%s', password='%s']", id,
				firstName, lastName, username, email, address, telephone_number, position, urlAvatar, password);
	}

	/*@Override
	public String toString() {
		return String.format("Worker[id=%d, name='%s', surname='%s', email='%s', address='%s', telephone_number='%s', position='%s', urlAvatar='%s' ]",
				id, name, surname, email, address, telephone_number, position, urlAvatar);
	}
	*/
	public Collection<? extends GrantedAuthority> getAuthorities() {
		ArrayList<GrantedAuthority> list = new ArrayList<GrantedAuthority>();
		System.out.println("positioN"+this.getPosition());
		
		list.add(new SimpleGrantedAuthority(this.getPosition()));
		return (Collection<? extends GrantedAuthority>) list;	
	}

	public String getPassword() {
		// TODO Auto-generated method stub
		return password;
	}
		
	public boolean isAccountNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}
	
	public boolean isAccountNonLocked() {
		// TODO Auto-generated method stub
		return true;
	}
	
	public boolean isCredentialsNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	public boolean isEnabled() {
		// TODO Auto-generated method stub
		return true;
	}

}