package com.proyecto;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ProductMenuRepository extends JpaRepository<ProductMenu, Long> {
}