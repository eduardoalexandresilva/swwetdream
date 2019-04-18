package com.somhodoce.api.repository.cidade;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.somhodoce.api.model.Cidade;
import com.somhodoce.api.repository.filter.CidadeFilter;

public interface CidadeRepositoryQuery {

	public Page<Cidade> filtrar(CidadeFilter cidadeFilter, Pageable pageable);
}
