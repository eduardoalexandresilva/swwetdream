package com.somhodoce.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.somhodoce.api.model.Estado;
import com.somhodoce.api.repository.estado.EstadoRepositoryQuery;

public interface EstadoRepository extends JpaRepository<Estado, Long>, EstadoRepositoryQuery {

}
