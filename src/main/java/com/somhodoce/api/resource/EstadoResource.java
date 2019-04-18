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
import com.somhodoce.api.model.Estado;
import com.somhodoce.api.repository.EstadoRepository;
import com.somhodoce.api.repository.filter.EstadoFilter;

@RestController
@RequestMapping("/estados")
public class EstadoResource {
	
	@Autowired
	private EstadoRepository estadoRepository;
	
	@Autowired
	private ApplicationEventPublisher publisher;
	
	@GetMapping
	public Page<Estado> listar(EstadoFilter estadoFilter, Pageable pageable){
		return estadoRepository.filtrar(estadoFilter, pageable);
	}
	
	@GetMapping("/{codigo}")
	public ResponseEntity<Estado> listarPeloCodigo(@PathVariable Long codigo){
		Estado estado = estadoRepository.findOne(codigo);
		return estado != null ? ResponseEntity.ok(estado) : ResponseEntity.notFound().build();
	}
	
	@PostMapping
	public ResponseEntity<Estado> criar(@Valid @RequestBody Estado estado, HttpServletResponse response){
		Estado estadoSalvo = estadoRepository.save(estado);		
		publisher.publishEvent(new RecursoCriadoEvent(this, response, estadoSalvo.getId()));		
		return ResponseEntity.status(HttpStatus.CREATED).body(estadoSalvo);
	}

}
