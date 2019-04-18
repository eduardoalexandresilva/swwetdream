package com.somhodoce.api.service;

import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.somhodoce.api.model.Cliente;
import com.somhodoce.api.repository.ClienteRepository;
import com.somhodoce.api.service.exception.EmailJaCadastradoException;

@Service
public class ClienteService {

	@Autowired
	ClienteRepository clienteRepository;
		
	public Cliente atualizar(Integer codigo,Cliente cliente) {
		Cliente clienteSalvo = buscarClientePeloCodigo(codigo);		
		BeanUtils.copyProperties(cliente, clienteSalvo, "codigo");
		return clienteRepository.save(clienteSalvo);
	}
	
	public List<Cliente> findAll() {
		return	clienteRepository.findAll();
		
	}
	
	public Page<Cliente> findPage(Integer page, Integer linesPerPage, String oderBy, String direction){
		PageRequest pageRequest = new PageRequest(page, linesPerPage, Direction.valueOf(direction), oderBy);
		return clienteRepository.findAll(pageRequest);
	}

	public void atualizaPropriedadeAtivo(Integer codigo, Boolean ativo) {
		Cliente clienteSalvo = buscarClientePeloCodigo(codigo);
		clienteSalvo.setAtivo(ativo);
		clienteRepository.save(clienteSalvo);
	}
	
	public Cliente buscarClientePeloCodigo(Integer codigo) {
		Cliente clienteSalvo = clienteRepository.findOne(codigo);
		if(clienteSalvo == null) {
			throw new EmptyResultDataAccessException(1);
		}
		return clienteSalvo;
	}
	
	public Cliente buscaEmail(String email) {		
		
		Cliente aux = clienteRepository.findByEmail(email);
		if(aux != null) {
			throw new EmailJaCadastradoException();
		}
		
		return aux;
		
	}	
	
	
}
