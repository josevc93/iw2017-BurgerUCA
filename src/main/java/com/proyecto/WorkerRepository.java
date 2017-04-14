package com.proyecto;

import java.util.Collection;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import com.proyecto.Worker;


public interface WorkerRepository extends JpaRepository<Worker, Long> {

	List<Worker> findBySurnameStartsWithIgnoreCase(String name);

}