package com.somhodoce.api.model;

import javax.persistence.Column;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
@Table(name = "contatos")
public class Contatos {	
	
	@Column(name = "celular_um")
	private String celularUm;
	
	@Column(name = "celular_dois")
	private String celularDois;
}
