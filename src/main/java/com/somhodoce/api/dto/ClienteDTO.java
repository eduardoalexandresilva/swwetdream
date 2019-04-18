package com.somhodoce.api.dto;

import java.io.Serializable;

import com.somhodoce.api.model.Cliente;

import lombok.Getter;
import lombok.Setter;

public class ClienteDTO implements Serializable{
	private static final long serialVersionUID = 1L;
	
	@Getter
	@Setter
	private Integer codigo;
	
	@Getter
	@Setter
	private String nome;
	
	@Getter
	@Setter
	private String email;
	
	public ClienteDTO() {
		
	}
	
   public ClienteDTO(Cliente obj) {
		codigo = obj.getCodigo();
		nome = obj.getNome();
		email = obj.getEmail();
	}
}
