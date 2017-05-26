package com.proyecto;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.proyecto.Product;

public interface ProductRepository extends JpaRepository<Product, Long> {
	List<Product> findByNameStartsWithIgnoreCase(String name);
	
	@Query("select p from Product p where p.family = :family")
	List<Product> findByFamily(@Param("family") String family);
}