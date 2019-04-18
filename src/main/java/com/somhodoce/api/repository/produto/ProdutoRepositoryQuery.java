package com.somhodoce.api.repository.produto;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.somhodoce.api.model.Produto;
import com.somhodoce.api.repository.filter.ProdutoFilter;

public interface ProdutoRepositoryQuery {

	public Page<Produto> filtrar(ProdutoFilter produtoFilter, Pageable page);
}
