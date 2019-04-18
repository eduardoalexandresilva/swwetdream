package com.somhodoce.api.model;

import javax.persistence.Entity;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonTypeName;
import com.somhodoce.api.model.enums.EstadoPagamento;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "pagamentoComCartao")
@JsonTypeName("pagamentoComCartao")
public class PagamentoComCartao extends Pagamento{
	
	@Getter
	@Setter
	private Integer numeroDeParcelas;
	
	public PagamentoComCartao() {
		
	}

	public PagamentoComCartao(Integer codigo, EstadoPagamento estado, Pedido pedido, Integer numeroDeParcelas) {
		super(codigo, estado, pedido);
		this.numeroDeParcelas = numeroDeParcelas;
	}
	
	
}
