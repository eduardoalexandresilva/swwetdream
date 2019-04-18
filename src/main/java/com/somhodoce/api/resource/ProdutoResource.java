package com.somhodoce.api.resource;

import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.somhodoce.api.dto.ProdutodDTO;
import com.somhodoce.api.event.RecursoCriadoEvent;
import com.somhodoce.api.exceptionhandler.SonhoDoceExceptionHandler.Erro;
import com.somhodoce.api.model.Produto;
import com.somhodoce.api.repository.ProdutoRepository;
import com.somhodoce.api.resource.utils.URL;
import com.somhodoce.api.service.ProdutoService;
import com.somhodoce.api.service.exception.clienteInesistenteOuInativoException;

@RestController
@RequestMapping("/produtos")
public class ProdutoResource {

	@Autowired
	private ProdutoRepository produtoRepository;
	
	@Autowired
	private ProdutoService produtoService;
		
	@Autowired
	private ApplicationEventPublisher publisher;
	
	@Autowired
	private MessageSource messageSource;
	
	@GetMapping
	public ResponseEntity<Page<ProdutodDTO>> listaPaginacao(
			@RequestParam(value="nome", defaultValue="") String nome,
			@RequestParam(value="categorias", defaultValue="0") String categorias, 
			@RequestParam(value="page", defaultValue="0") Integer page, 
			@RequestParam(value="linesPerPage", defaultValue="24") Integer linesPerPage, 
			@RequestParam(value="oderBy", defaultValue="nome") String oderBy, 
			@RequestParam(value="direction", defaultValue="ASC") String direction){
		String nomeDecode = URL.decodeParam(nome);
		List<Integer> ids = URL.decodeLongList(categorias);
		Page<Produto> lista = produtoService.search(nomeDecode, ids, page, linesPerPage, oderBy, direction);
		Page<ProdutodDTO> listDto = lista.map(obj -> new ProdutodDTO(obj));
		return ResponseEntity.ok().body(listDto);
	}	
	
	@GetMapping("/{codigo}")
	public ResponseEntity<Produto> buscarPeloCodigo(@PathVariable Integer codigo){
		Produto produto = produtoRepository.findOne(codigo);
		return produto != null ? ResponseEntity.ok(produto) : ResponseEntity.notFound().build();
	}
	
	@PostMapping
	public ResponseEntity<Produto> criar(@Valid @RequestBody Produto produto, HttpServletResponse response){
		Produto produtoSalvo = produtoService.salvar(produto);
		publisher.publishEvent(new RecursoCriadoEvent(this, response, produtoSalvo.getCodigo()));
		return ResponseEntity.status(HttpStatus.CREATED).body(produtoSalvo);
	}
	
	@DeleteMapping("/{codigo}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void remover(@PathVariable Integer codigo) {
		produtoRepository.delete(codigo);		
	}
	
	@ExceptionHandler({clienteInesistenteOuInativoException.class})
	public ResponseEntity<Object> handleclienteInesistenteOuInativoException(clienteInesistenteOuInativoException ex){
		String mensagemUsuario = messageSource.getMessage("cliente.inexistente-ou-inativo", null, LocaleContextHolder.getLocale());
		String mensagemDesenvolvedor = ex.toString();
		List<Erro> erros = Arrays.asList(new Erro(mensagemUsuario, mensagemDesenvolvedor));
		return ResponseEntity.badRequest().body(erros);
	}
	
		
	
}
