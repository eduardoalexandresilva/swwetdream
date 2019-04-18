package com.somhodoce.api.dto;

import java.io.Serializable;

import com.somhodoce.api.model.Categoria;

import lombok.Getter;
import lombok.Setter;

public class CategoriaDTO implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Getter
	@Setter
	private Integer codigo;
	
	@Getter
	@Setter
	private String nome;
	
	public CategoriaDTO() {
		
	}
	
    public CategoriaDTO(Categoria obj) {
		codigo = obj.getCodigo();
		nome = obj.getNome();
	}
}
