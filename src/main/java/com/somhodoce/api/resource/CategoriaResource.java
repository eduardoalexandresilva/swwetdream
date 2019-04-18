package com.somhodoce.api.resource;

import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.somhodoce.api.dto.CategoriaDTO;
import com.somhodoce.api.event.RecursoCriadoEvent;
import com.somhodoce.api.model.Categoria;
import com.somhodoce.api.repository.CategoriaRepository;
import com.somhodoce.api.service.CategoriaService;

@RestController
@RequestMapping("/categorias")
public class CategoriaResource {
	
	@Autowired
	private CategoriaRepository categoriaRepository;
	
	@Autowired
	private CategoriaService categoriaService;
	
	@Autowired
	private ApplicationEventPublisher publisher;
	
	
	@GetMapping
	public ResponseEntity<List<CategoriaDTO>> listar(){
		List<Categoria> lista = categoriaService.findAll();
		List<CategoriaDTO> listDto = lista.stream().map(obj -> new CategoriaDTO(obj)).collect(Collectors.toList());
		return ResponseEntity.ok().body(listDto);
	}
	
	@GetMapping("/page")
	public ResponseEntity<Page<CategoriaDTO>> listaPaginacao(
			@RequestParam(value="page", defaultValue="0") Integer page, 
			@RequestParam(value="linesPerPage", defaultValue="24") Integer linesPerPage, 
			@RequestParam(value="oderBy", defaultValue="nome") String oderBy, 
			@RequestParam(value="direction", defaultValue="ASC") String direction){
		Page<Categoria> lista = categoriaService.findPage(page, linesPerPage, oderBy, direction);
		Page<CategoriaDTO> listDto = lista.map(obj -> new CategoriaDTO(obj));
		return ResponseEntity.ok().body(listDto);
	}	
	
	@PostMapping	
	public ResponseEntity<Categoria> ciar(@Valid @RequestBody Categoria categoria, HttpServletResponse response) {
		Categoria categoriaSalva = categoriaRepository.save(categoria);		
		publisher.publishEvent(new RecursoCriadoEvent(this, response, categoriaSalva.getCodigo()));		
		return ResponseEntity.status(HttpStatus.CREATED).body(categoriaSalva);
	}
	
	@PutMapping("/{codigo}")
	public ResponseEntity<Categoria> atualizar(@Valid @RequestBody Categoria categoria, @PathVariable Integer codigo ){
		Categoria categoriaSalva = categoriaService.update(categoria, codigo); 
		return ResponseEntity.ok(categoriaSalva);
	}
	
	@GetMapping("/{codigo}")
	public ResponseEntity<Categoria> buscarPeloCodigo(@PathVariable Integer codigo) {
		Categoria categoria = categoriaRepository.findOne(codigo);
		return categoria != null ? ResponseEntity.ok(categoria) : ResponseEntity.notFound().build();
	}
	
	@DeleteMapping("/{codigo}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void remover(@PathVariable Integer codigo) {
		categoriaRepository.delete(codigo); 		
	}
	
	

}
