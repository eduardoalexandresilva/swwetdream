package com.somhodoce.api.model;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.br.CNPJ;
import org.hibernate.validator.constraints.br.CPF;
import org.hibernate.validator.group.GroupSequenceProvider;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.somhodoce.api.model.enums.TipoCliente;
import com.somhodoce.api.model.group.CnpjGroup;
import com.somhodoce.api.model.group.CpfGroup;
import com.somhodoce.api.model.validation.ClienteGroupSequenceProvider;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "cliente")
@GroupSequenceProvider(ClienteGroupSequenceProvider.class)
public class Cliente {
	
	@Id	
	@GeneratedValue(strategy = GenerationType.IDENTITY)	
	private Integer codigo;	
	
	@NotNull
	@Size(min = 5, max = 50)
	private String nome;
	
	@Column(unique=true)
	@NotNull
	@Size(min = 5, max = 30)
	@Email	
	private String email;
	
	@CPF(groups = CpfGroup.class)
	@CNPJ(groups = CnpjGroup.class)
	@NotNull(message = "CPF/CNPJ é obrigatório")	
	private String cpfOuCnpj;	
	
	@NotNull(message = "Tipo pessoa é obrigatório")
	@Enumerated(EnumType.STRING)
	@Column(name = "tipo_pessoa")
	private TipoCliente tipo;	
	
	@ElementCollection
	@CollectionTable(name = "telefone")
	private Set<String> telefones = new HashSet<>();	
	
	@NotNull
	private Boolean ativo;	
	
	@OneToMany(mappedBy = "cliente")
	private List<Endereco> enderecos = new ArrayList<>();
	
	@JsonIgnore	
	@OneToMany(mappedBy="cliente")
	private List<Pedido> pedidos = new ArrayList<>();
	
	@JsonIgnore
	@Transient
	public boolean isInativo() {
		return !this.ativo;
	}	
	
	public Cliente() {
		
	}


	public Cliente(Integer codigo, String nome, String email, String cpfOuCnpj, TipoCliente tipo, Boolean ativo) {
		super();
		this.codigo = codigo;
		this.nome = nome;
		this.email = email;
		this.cpfOuCnpj = cpfOuCnpj;
		this.tipo = tipo;
		this.ativo = ativo;
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
		Cliente other = (Cliente) obj;
		if (codigo == null) {
			if (other.codigo != null)
				return false;
		} else if (!codigo.equals(other.codigo))
			return false;
		return true;
	}

	/*public TipoCliente getTipo() {
		return TipoCliente.toEnum(tipo);
	}

	public void setTipo(TipoCliente tipo) {
		this.tipo = tipo.getCod();
	}*/
	
	
	

}
