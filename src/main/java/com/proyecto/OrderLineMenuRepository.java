package com.proyecto;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import com.proyecto.Product;

public interface OrderLineMenuRepository extends JpaRepository<OrderLineMenu, Long> {
	//List<Product> findByNameStartsWithIgnoreCase(String name);
}