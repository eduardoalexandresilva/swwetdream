package com.somhodoce.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.somhodoce.api.model.Categoria;

public interface CategoriaRepository extends JpaRepository<Categoria, Integer> {
	

}
