package com.somhodoce.api.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonTypeName;
import com.somhodoce.api.model.enums.EstadoPagamento;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "pagamentoComBoleto")
@JsonTypeName("pagamentoComBoleto")
public class PagamentoComBoleto extends Pagamento {

	@JsonFormat(pattern="dd/MM/yyyy")
	private Date dataVencimento;
	
	@JsonFormat(pattern="dd/MM/yyyy")
	private Date dataPagamento;
	
	public PagamentoComBoleto() {
		
	}

	public PagamentoComBoleto(Integer codigo, EstadoPagamento estado, Pedido pedido, Date dataVencimento, Date dataPagamento) {
		super(codigo, estado, pedido);
		this.dataPagamento = dataPagamento;
		this.dataVencimento = dataVencimento;
	}
	
	
}
