package com.proyecto;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import com.proyecto.Product;

public interface OrderLineProductRepository extends JpaRepository<OrderLineProduct, Long> {
	//List<Product> findByNameStartsWithIgnoreCase(String name);
}