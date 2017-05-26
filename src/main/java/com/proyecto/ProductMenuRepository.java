package com.proyecto;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ProductMenuRepository extends JpaRepository<ProductMenu, Long> {
	@Query("select pm from ProductMenu pm where pm.menuObj.id = :id")
	List<ProductMenu> findByIdMenu(@Param("id") Long id);
}