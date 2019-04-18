package com.somhodoce.api.resource;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.somhodoce.api.event.RecursoCriadoEvent;
import com.somhodoce.api.model.Cidade;
import com.somhodoce.api.repository.CidadeRepository;
import com.somhodoce.api.repository.filter.CidadeFilter;

@RestController
@RequestMapping("/cidades")
public class CidadeResource {
	
	@Autowired
	private CidadeRepository cidadeRepository;
	
	@Autowired
	private ApplicationEventPublisher publisher;
	
	@GetMapping
	public Page<Cidade> listar(CidadeFilter cidadeFilter, Pageable pageable){
		return cidadeRepository.filtrar(cidadeFilter, pageable);
	}
	
	@GetMapping("/{codigo}")
	public ResponseEntity<Cidade> listarPeloCodigo(@PathVariable Long codigo){
		Cidade cidade = cidadeRepository.findOne(codigo);
		return cidade != null ? ResponseEntity.ok(cidade) : ResponseEntity.notFound().build();
	}
	
	@PostMapping
	public ResponseEntity<Cidade> criar(@Valid @RequestBody Cidade cidade, HttpServletResponse response){
		Cidade cidadeSalva = cidadeRepository.save(cidade);		
		publisher.publishEvent(new RecursoCriadoEvent(this, response, cidadeSalva.getId()));		
		return ResponseEntity.status(HttpStatus.CREATED).body(cidadeSalva);
	}

}
