package com.somhodoce.api.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.somhodoce.api.model.Categoria;
import com.somhodoce.api.model.Cliente;
import com.somhodoce.api.model.Produto;
import com.somhodoce.api.repository.CategoriaRepository;
import com.somhodoce.api.repository.ClienteRepository;
import com.somhodoce.api.repository.ProdutoRepository;
import com.somhodoce.api.service.exception.clienteInesistenteOuInativoException;

@Service
public class ProdutoService {

	@Autowired
	private ProdutoRepository produtoRepository;
    
	@Autowired
	private ClienteRepository clienteRepository;
	
	@Autowired
	private CategoriaRepository categoriaRepository;

	public Produto salvar(Produto produto) {
	  Cliente cliente = clienteRepository.findOne(produto.getCliente().getCodigo()); 
	  if(cliente == null || cliente.isInativo()) {
		  throw new clienteInesistenteOuInativoException();
	  }
		return produtoRepository.save(produto);
	}
	
	public Page<Produto> search(String nome, List<Integer> ids, Integer page, Integer linesPerPage, String oderBy, String direction){
		PageRequest pageRequest = new PageRequest(page, linesPerPage, Direction.valueOf(direction), oderBy);
		List<Categoria> categorias = categoriaRepository.findAll(ids);
		return produtoRepository.findDistinctByNomeContainingAndCategoriasIn(nome, categorias, pageRequest);
		
	}

	public Produto find(Integer id) {
		Produto produtoSalvo = produtoRepository.findOne(id);
		if(produtoSalvo == null) {
			throw new EmptyResultDataAccessException(1);
		}
		
		return produtoSalvo;
		
	}
	
	
	
	
	
	
	
	
}
