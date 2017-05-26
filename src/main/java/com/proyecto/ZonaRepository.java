package com.proyecto;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import com.proyecto.Zona;

public interface ZonaRepository extends JpaRepository<Zona, Long> {
	List<Zona> findByNameStartsWithIgnoreCase(String name);
}