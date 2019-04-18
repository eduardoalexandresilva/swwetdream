package com.somhodoce.api.model;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "pedido")
public class Pedido {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)	
	private Integer codigo;
	
	@JsonFormat(pattern="dd/MM/yyyy HH:mm")
	private Date instante;
	
	
	@OneToOne(cascade=CascadeType.ALL, mappedBy="pedido")
	private Pagamento pagamento;
	
	
	@ManyToOne
	@JoinColumn(name="cliente_id")	
	private Cliente cliente;
	
	@ManyToOne
	@JoinColumn(name="endereco_de_entrega_id")	
	private Endereco enderecoDeEntrega;
	
	@OneToMany(mappedBy="id.pedido")
	private Set<ItemPedido> itens = new HashSet<>();
	
	public Pedido() {
		
	}

	public Pedido(Integer codigo, Date instante, Cliente cliente, Endereco enderecoDeEntrega) {
		super();
		this.codigo = codigo;
		this.instante = instante;		
		this.cliente = cliente;
		this.enderecoDeEntrega = enderecoDeEntrega;
	}
	
	public double getValorTotal() {
		double soma = 0.0;
		for (ItemPedido ip : itens) {
			soma += ip.getSubTotal();
		}
		return soma;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((codigo == null) ? 0 : codigo.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Pedido other = (Pedido) obj;
		if (codigo == null) {
			if (other.codigo != null)
				return false;
		} else if (!codigo.equals(other.codigo))
			return false;
		return true;
	}
	
	
	
}
