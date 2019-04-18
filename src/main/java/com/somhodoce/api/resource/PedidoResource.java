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
import com.somhodoce.api.model.Pedido;
import com.somhodoce.api.repository.PedidoRepository;
import com.somhodoce.api.repository.filter.PedidoFilter;
import com.somhodoce.api.service.PedidoService;

@RestController
@RequestMapping("/pedidos")
public class PedidoResource {

	@Autowired
	private PedidoRepository pedidoRepository;
	
	@Autowired
	private PedidoService pedidoService;
		
	@Autowired
	private ApplicationEventPublisher publisher;
	
	//@Autowired
	//private MessageSource messageSource;
	
	@GetMapping
	public Page<Pedido> pesquisar(PedidoFilter pedidoFilter, Pageable pageable){
		return pedidoRepository.filtrar(pedidoFilter, pageable);
	}
	
	@GetMapping("/{codigo}")
	public ResponseEntity<Pedido> buscarPeloCodigo(@PathVariable Integer codigo){
		Pedido pedido = pedidoRepository.findOne(codigo);
		return pedido != null ? ResponseEntity.ok(pedido) : ResponseEntity.notFound().build();
	}
	
	@PostMapping
	public ResponseEntity<Pedido> criar(@Valid @RequestBody Pedido pedido, HttpServletResponse response){
		Pedido pedidoSalvo = pedidoService.salvar(pedido);
		publisher.publishEvent(new RecursoCriadoEvent(this, response, pedidoSalvo.getCodigo()));
		return ResponseEntity.status(HttpStatus.CREATED).body(pedidoSalvo);
	}
	/*
	@DeleteMapping("/{codigo}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void remover(@PathVariable Long codigo) {
		produtoRepository.delete(codigo);		
	}
	
	@ExceptionHandler({clienteInesistenteOuInativoException.class})
	public ResponseEntity<Object> handleclienteInesistenteOuInativoException(clienteInesistenteOuInativoException ex){
		String mensagemUsuario = messageSource.getMessage("cliente.inexistente-ou-inativo", null, LocaleContextHolder.getLocale());
		String mensagemDesenvolvedor = ex.toString();
		List<Erro> erros = Arrays.asList(new Erro(mensagemUsuario, mensagemDesenvolvedor));
		return ResponseEntity.badRequest().body(erros);
	}*/
	
		
	
}
