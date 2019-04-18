package com.somhodoce.api.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "categoria")
public class Categoria implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id	
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer codigo;
	
	@NotNull
	@Size(min = 3, max = 40)
	private String nome;	
	
	@ManyToMany(mappedBy = "categorias")
	private List<Produto> produtos = new ArrayList<>();


	public Categoria(Integer codigo, String nome) {
		super();
		this.codigo = codigo;
		this.nome = nome;
	}
	
	public Categoria() {
		
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
		Categoria other = (Categoria) obj;
		if (codigo == null) {
			if (other.codigo != null)
				return false;
		} else if (!codigo.equals(other.codigo))
			return false;
		return true;
	}
	
	
}
