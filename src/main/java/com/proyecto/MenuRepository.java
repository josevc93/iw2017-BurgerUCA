package com.proyecto;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.proyecto.Menu;

public interface MenuRepository extends JpaRepository<Menu, Long> {
	List<Menu> findByNameStartsWithIgnoreCase(String name);
}