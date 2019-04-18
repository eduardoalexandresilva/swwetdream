package com.somhodoce.api.model;

import javax.persistence.Entity;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonTypeName;
import com.somhodoce.api.model.enums.EstadoPagamento;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "pagamentoEmDinheiro")
@JsonTypeName("pagamentoEmDinheiro")
public class PagamentoEmDinheiro extends Pagamento{
	
	@Getter
	@Setter
	private Double valor;
	
	public PagamentoEmDinheiro() {
		
	}

	public PagamentoEmDinheiro(Integer codigo, EstadoPagamento estado, Pedido pedido, Double valor) {
		super(codigo, estado, pedido);
		this.valor = valor;
	}
	
	
}
