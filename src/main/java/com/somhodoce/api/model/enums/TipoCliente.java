package com.somhodoce.api.model.enums;

import com.somhodoce.api.model.group.CnpjGroup;
import com.somhodoce.api.model.group.CpfGroup;

import lombok.Getter;

@Getter
public enum TipoCliente {

	FISICA("Física", "CPF", "000.000.000-00", CpfGroup.class), 
	JURIDICA("Jurídica", "CNPJ", "00.000.000/0000-00", CnpjGroup.class);

	private String descricao;
	private String documento;
	private String mascara;
	private Class<?> grupo;

	TipoCliente(String descricao, String documento, String mascara, Class<?> grupo) {
		this.descricao = descricao;
		this.documento = documento;
		this.mascara = mascara;
		this.grupo = grupo;
	}
	
}
