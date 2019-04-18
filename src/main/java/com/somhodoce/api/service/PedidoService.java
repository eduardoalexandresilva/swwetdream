package com.somhodoce.api.service;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.somhodoce.api.model.ItemPedido;
import com.somhodoce.api.model.PagamentoComBoleto;
import com.somhodoce.api.model.Pedido;
import com.somhodoce.api.model.enums.EstadoPagamento;
import com.somhodoce.api.repository.ItemPredidoRepository;
import com.somhodoce.api.repository.PagamentoRepository;
import com.somhodoce.api.repository.PedidoRepository;

@Service
public class PedidoService {

	@Autowired
	public boletoService boletoService;
	
	@Autowired
	public PedidoRepository repo;
	
	@Autowired
	private ClienteService clienteService;
	
	@Autowired
	public PagamentoRepository pagamentoRepository;
	
	@Autowired
	public ProdutoService produtoService;
	
	@Autowired
	public ItemPredidoRepository itemPredidoRepository;
	
	@Transactional
	public Pedido salvar(Pedido obj) {
		obj.setCodigo(null);
		obj.setInstante(new Date());
		obj.setCliente(clienteService.buscarClientePeloCodigo(obj.getCliente().getCodigo()));
		obj.getPagamento().setEstado(EstadoPagamento.PENDENTE);
		obj.getPagamento().setPedido(obj);
		if (obj.getPagamento() instanceof PagamentoComBoleto) {
			PagamentoComBoleto pagto = (PagamentoComBoleto) obj.getPagamento();
			boletoService.preecherPagamentoComBoleto(pagto, obj.getInstante());
		}
		obj = repo.save(obj);
		pagamentoRepository.save(obj.getPagamento());
		for (ItemPedido ip : obj.getItens()) {
			ip.setDesconto(0.0);
			ip.setProduto(produtoService.find(ip.getProduto().getCodigo()));
			ip.setPreco(ip.getProduto().getValor());
			ip.setPedido(obj);
		}
		itemPredidoRepository.save(obj.getItens());		
		return obj;
	}

}
