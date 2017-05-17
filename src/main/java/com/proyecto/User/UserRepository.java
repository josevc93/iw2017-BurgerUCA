package com.proyecto.User;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;



public interface UserRepository extends JpaRepository<User, Long> {

	//List<Worker> findBySurnameStartsWithIgnoreCase(String name);
	
	public List<User> findByLastNameStartsWithIgnoreCase(String lastName);
	
	public User findByUsername(String username);
	
	

}

