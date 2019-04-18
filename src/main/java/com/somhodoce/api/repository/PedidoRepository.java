package com.somhodoce.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.somhodoce.api.model.Pedido;
import com.somhodoce.api.repository.pedido.PedidoRepositoryQuery;

public interface PedidoRepository extends JpaRepository<Pedido, Integer>, PedidoRepositoryQuery{

}
