package com.fullstack.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.fullstack.entity.Todo;

public interface TodoRepository extends JpaRepository<Todo, Long>{
	
	
	
}
