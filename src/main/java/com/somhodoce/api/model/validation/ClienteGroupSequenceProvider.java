package com.somhodoce.api.model.validation;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.validator.spi.group.DefaultGroupSequenceProvider;

import com.somhodoce.api.model.Cliente;

public class ClienteGroupSequenceProvider implements DefaultGroupSequenceProvider<Cliente> {

	@Override
	public List<Class<?>> getValidationGroups(Cliente cliente) {
		List<Class<?>> grupos = new ArrayList<>();
		grupos.add(Cliente.class);
		
		if (isPessoaSelecionada(cliente)) {
			grupos.add(cliente.getTipo().getGrupo());
		}
		
		return grupos;
	}

	private boolean isPessoaSelecionada(Cliente cliente) {
		return cliente != null && cliente.getTipo() != null;
	}

}
