package com.proyecto;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.proyecto.Product;

public interface OrderLineProductRepository extends JpaRepository<OrderLineProduct, Long> {
	//List<Product> findByNameStartsWithIgnoreCase(String name);
	@Query("select i from OrderLineProduct i where i.orderpObj.id = :id")
	List<OrderLineProduct> findByIdProduct(@Param("id") Long id);
	
	//@Query("select i from OrderLineProduct i where i.")
//	List<OrderLineProduct> findByIdProduct(@Param("id") Long id);

	//List<OrderLineProduct> findByNameStartsWithIgnoreCase(String nombre); //FALLA PQ NOMBRE NO EXISTE EN ORDERLINEPRODUCT
}