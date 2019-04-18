package com.somhodoce.api.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.MapsId;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.somhodoce.api.model.enums.EstadoPagamento;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "pagamento")
@Inheritance(strategy=InheritanceType.JOINED)
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "@type")
public abstract class Pagamento {
	
	@Id	
	@Getter
	@Setter
	private Integer codigo;
		
	private Integer estado;
	
	@JsonIgnore
	@Getter
	@Setter
	@OneToOne
	@JoinColumn(name="pedido_id")
	@MapsId
	private Pedido pedido;

	public Pagamento() {
		
	}

	public Pagamento(Integer codigo, EstadoPagamento estado, Pedido pedido) {
		super();
		this.codigo = codigo;
		this.estado = (estado == null) ? null : estado.getCod();
		this.pedido = pedido;
	}

	public EstadoPagamento getEstado() {
		return EstadoPagamento.toEnum(estado);
	}

	public void setEstado(EstadoPagamento estado) {
		this.estado = estado.getCod();
	}

	
	
	
}
