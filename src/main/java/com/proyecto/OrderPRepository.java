package com.proyecto;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.proyecto.Product;

public interface OrderPRepository extends JpaRepository<OrderP, Long> {
	@Query("select op from OrderP op where op.numMesa = :numMesa")
	OrderP findByNumMesa(@Param("numMesa") Long NumMesa);
	
	@Query("select op from OrderP op where op.state = 0")
	List<OrderP> findOrdersOpen();
	
	
}