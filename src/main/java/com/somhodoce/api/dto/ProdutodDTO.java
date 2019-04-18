package com.somhodoce.api.dto;

import com.somhodoce.api.model.Produto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProdutodDTO {

	private Integer codigo;
	private String nome;
	private Double valor;

	public ProdutodDTO() {

	}

	public ProdutodDTO(Produto obj) {
      codigo = obj.getCodigo();
      nome = obj.getNome();
      valor = obj.getValor();
	}

}
