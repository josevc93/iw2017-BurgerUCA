package com.proyecto;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import com.proyecto.Product;

public interface OrderPRepository extends JpaRepository<OrderP, Long> {
	//List<Product> findByNameStartsWithIgnoreCase(String name);
}