package com.somhodoce.api.repository.estado;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.somhodoce.api.model.Estado;
import com.somhodoce.api.repository.filter.EstadoFilter;

public interface EstadoRepositoryQuery {

	public Page<Estado> filtrar(EstadoFilter estadoFilter, Pageable pageable);
}
