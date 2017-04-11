package com.proyecto;

import org.springframework.data.repository.CrudRepository;

import com.proyecto.Worker;

// This will be AUTO IMPLEMENTED by Spring into a Bean called userRepository
// CRUD refers Create, Read, Update, Delete

public interface WorkerRepository extends CrudRepository<Worker, Long> {

}