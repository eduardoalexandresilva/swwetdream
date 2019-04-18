package com.somhodoce.api.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "produto")
@Getter
@Setter
public class Produto implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)	
	private Integer codigo;
	
	@NotNull
	private String nome;
	
	@NotNull
	private Double valor;
	
	private String descricao;
	
	@JsonIgnore	
	@ManyToMany
	@JoinTable(name = "PRODUTO_CATEGORIA", joinColumns = @JoinColumn(name = "produto_id")
	,inverseJoinColumns = @JoinColumn(name = "categoria_id"))
	private List<Categoria> categorias = new ArrayList<>();
	
	
	@ManyToOne
	@JoinColumn(name = "codigo_cliente")
	private Cliente cliente;
	
	@JsonIgnore
	@OneToMany(mappedBy="id.produto")
	private Set<ItemPedido> itens = new HashSet<>();
	
	@JsonIgnore
	public List<Pedido> getPedidos(){
		List<Pedido> lista = new ArrayList<>();
		for(ItemPedido x : itens) {
			lista.add(x.getPedido());
		}
		return lista;
	}
	
	public Produto() {
		
	}


	public Produto(Integer codigo, String nome, Double valor, String descricao) {
		super();
		this.codigo = codigo;
		this.nome = nome;
		this.valor = valor;
		this.descricao = descricao;
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
		Produto other = (Produto) obj;
		if (codigo == null) {
			if (other.codigo != null)
				return false;
		} else if (!codigo.equals(other.codigo))
			return false;
		return true;
	}
	
	
	
	
}
