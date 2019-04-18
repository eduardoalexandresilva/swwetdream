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

import com.somhodoce.api.dto.ClienteDTO;
import com.somhodoce.api.event.RecursoCriadoEvent;
import com.somhodoce.api.model.Cliente;
import com.somhodoce.api.repository.ClienteRepository;
import com.somhodoce.api.service.ClienteService;

@RestController
@RequestMapping("/clientes")
public class ClienteResource {

	@Autowired
	private ClienteRepository clienteRepository;
	
	@Autowired
	private ClienteService clienteService;
	
	@Autowired
	private ApplicationEventPublisher publisher;
	
	@GetMapping
	public ResponseEntity<List<ClienteDTO>> listarTodos(){
		List<Cliente> lista = clienteService.findAll();
		List<ClienteDTO> listDto = lista.stream().map(obj -> new ClienteDTO(obj)).collect(Collectors.toList());
		return ResponseEntity.ok().body(listDto);
	}
	
	@GetMapping("/page")
	public ResponseEntity<Page<ClienteDTO>> listaPaginacao(
			@RequestParam(value="page", defaultValue="0") Integer page, 
			@RequestParam(value="linesPerPage", defaultValue="24") Integer linesPerPage, 
			@RequestParam(value="oderBy", defaultValue="nome") String oderBy, 
			@RequestParam(value="direction", defaultValue="ASC") String direction){
		Page<Cliente> lista = clienteService.findPage(page, linesPerPage, oderBy, direction);
		Page<ClienteDTO> listDto = lista.map(obj -> new ClienteDTO(obj));
		return ResponseEntity.ok().body(listDto);
	}	
	
	@GetMapping("/{codigo}")
	public ResponseEntity<Cliente> buscarPeloCodigo(@PathVariable Integer codigo){
		Cliente cliente = clienteRepository.findOne(codigo);
		return cliente != null ? ResponseEntity.ok(cliente) : ResponseEntity.notFound().build();
	}
	
	@PostMapping
	public ResponseEntity<Cliente> criar(@Valid @RequestBody Cliente cliente, HttpServletResponse response){
		Cliente clienteSalvo = clienteService.buscaEmail(cliente.getEmail());
		clienteSalvo = clienteRepository.save(cliente);		
		publisher.publishEvent(new RecursoCriadoEvent(this, response, clienteSalvo.getCodigo()));		
		return ResponseEntity.status(HttpStatus.CREATED).body(clienteSalvo);
	}
	
	@DeleteMapping("/{codigo}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void remover(@PathVariable Integer codigo) {
		clienteRepository.delete(codigo); 		
	}
	
	@PutMapping("/{codigo}")
	public ResponseEntity<Cliente> atualizar(@PathVariable Integer codigo, @Valid @RequestBody Cliente cliente){
		Cliente clienteSalvo = clienteService.atualizar(codigo, cliente);
		return ResponseEntity.ok(clienteSalvo);
	}	
	
	@PutMapping("/{codigo}/ativo")
	public void atualizarPropriedadeAtivo(@PathVariable Integer codigo, @RequestBody Boolean ativo) {
		clienteService.atualizaPropriedadeAtivo(codigo, ativo);
	}
	
	
	
	
	
}
