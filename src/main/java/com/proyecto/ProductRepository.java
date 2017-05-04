package com.proyecto;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import com.proyecto.Product;

public interface ProductRepository extends JpaRepository<Product, Long> {
	List<Product> findByNameStartsWithIgnoreCase(String name);
}