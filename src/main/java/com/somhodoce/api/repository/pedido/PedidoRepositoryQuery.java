package com.somhodoce.api.repository.pedido;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.somhodoce.api.model.Pedido;
import com.somhodoce.api.repository.filter.PedidoFilter;

public interface PedidoRepositoryQuery {

	public Page<Pedido> filtrar(PedidoFilter pedidoFilter, Pageable pageable);
}
