package com.somhodoce.api.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.somhodoce.api.model.Categoria;
import com.somhodoce.api.model.Cidade;
import com.somhodoce.api.model.Cliente;
import com.somhodoce.api.model.Endereco;
import com.somhodoce.api.model.Estado;
import com.somhodoce.api.model.ItemPedido;
import com.somhodoce.api.model.Pagamento;
import com.somhodoce.api.model.PagamentoComBoleto;
import com.somhodoce.api.model.PagamentoComCartao;
import com.somhodoce.api.model.Pedido;
import com.somhodoce.api.model.Produto;
import com.somhodoce.api.model.enums.EstadoPagamento;
import com.somhodoce.api.model.enums.TipoCliente;
import com.somhodoce.api.repository.CategoriaRepository;
import com.somhodoce.api.repository.CidadeRepository;
import com.somhodoce.api.repository.ClienteRepository;
import com.somhodoce.api.repository.EnderecoRepository;
import com.somhodoce.api.repository.EstadoRepository;
import com.somhodoce.api.repository.ItemPredidoRepository;
import com.somhodoce.api.repository.PagamentoRepository;
import com.somhodoce.api.repository.PedidoRepository;
import com.somhodoce.api.repository.ProdutoRepository;

@Service
public class DBService {
	
	@Autowired
	private CategoriaRepository categoriaRepository;
	
	@Autowired
	private ProdutoRepository produtoRepository;
	
	@Autowired
	private EstadoRepository estadoRepository;
	
	@Autowired
	private CidadeRepository cidadeRepository;
	
	@Autowired
	private ClienteRepository clienteRepository;
	
	@Autowired
	private EnderecoRepository enderecoRepository;
	
	@Autowired
	private PedidoRepository pedidoRepository;
	
	@Autowired
	private PagamentoRepository pagamentoRepository;
	
	@Autowired
	private ItemPredidoRepository itemPredidoRepository;

	public void  intatiateTestDatabase() throws ParseException {
		Categoria cat1 = new Categoria(null, "trufado");
		Categoria cat2 = new Categoria(null, "caramelado");
		Categoria cat3 = new Categoria(null, "caramelado 2");
		Categoria cat4 = new Categoria(null, "carameladooooo");
		Categoria cat5 = new Categoria(null, "tratado");
		Categoria cat6 = new Categoria(null, "caram");
		Categoria cat7 = new Categoria(null, "caram 78");
		
		
		
		Produto p1 = new Produto(null, "trufa a",14.0000, "trufado com recheio");
		Produto p2 = new Produto(null, "trufa b",15.0000, "trufado com recheio");
		Produto p3 = new Produto(null, "trufa c",16.0000, "trufado com recheio");
		Produto p4 = new Produto(null, "trufa c",16.0000, "trufado com recheiotg");
		Produto p5 = new Produto(null, "trufa c",16.0000, "trufado com recheiobgbg");
		Produto p6 = new Produto(null, "trufa c",16.0000, "trufado com recheinhnho");
		Produto p7 = new Produto(null, "trufa c",16.0000, "trufado com recheiofvf");
		Produto p8 = new Produto(null, "trufa c",16.0000, "trufado com recheicscso");
		
		cat1.getProdutos().addAll(Arrays.asList(p1, p2, p3));
		cat2.getProdutos().addAll(Arrays.asList(p2,p4));
		cat3.getProdutos().addAll(Arrays.asList(p5));
		cat4.getProdutos().addAll(Arrays.asList(p6, p7));
		cat5.getProdutos().addAll(Arrays.asList(p8));
		
		p1.getCategorias().addAll(Arrays.asList(cat1, cat4));
		p2.getCategorias().addAll(Arrays.asList(cat1, cat2, cat4));
		p3.getCategorias().addAll(Arrays.asList(cat5, cat1));
		p4.getCategorias().addAll(Arrays.asList(cat5));
		p5.getCategorias().addAll(Arrays.asList(cat5, cat3));
		p6.getCategorias().addAll(Arrays.asList(cat2, cat5));
		p7.getCategorias().addAll(Arrays.asList(cat1,cat5));
		
		categoriaRepository.save(Arrays.asList(cat1, cat2, cat3, cat4, cat5, cat6, cat7));		
		produtoRepository.save(Arrays.asList(p1, p2, p3, p4, p5, p6, p7));
		

		Estado est1 = new Estado(null, "Pernambuco");
		Estado est2 = new Estado(null, "Alagoas");
		
		Cidade c1 = new Cidade(null, "Recife", est1);
		Cidade c2 = new Cidade(null, "Maceio", est2);
		
		est1.getCidades().addAll(Arrays.asList(c1));
		est2.getCidades().addAll(Arrays.asList(c2));
		
		estadoRepository.save(Arrays.asList(est1, est2));
		cidadeRepository.save(Arrays.asList(c1, c2));
		
		Cliente cli1 = new Cliente(null, "Eduardo", "edualexandre.f@gmail", "05842333457", TipoCliente.FISICA, true);
		
		cli1.getTelefones().addAll(Arrays.asList("81 98839-1210", "81 99874-7387"));
		
		Endereco e1 = new Endereco(null, "rua a", "19", "casa", "ur 7 várzea", "50960-300", cli1, c1);
		Endereco e2 = new Endereco(null, "rua a", "19", "casa", "ur 7 várzea", "50960-300", cli1, c2);
		
		cli1.getEnderecos().addAll(Arrays.asList(e1, e2));
		
		clienteRepository.save(Arrays.asList(cli1));
		enderecoRepository.save(Arrays.asList(e1,e2));
		
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
		
		Pedido ped1 = new Pedido(null, sdf.parse("01/04/2019 12:00"), cli1, e1);
		Pedido ped2 = new Pedido(null, sdf.parse("11/04/2019 11:00"), cli1, e2);
		
		Pagamento pgto1 = new PagamentoComCartao(null, EstadoPagamento.QUITADO, ped1, 06);
		ped1.setPagamento(pgto1);
		
		Pagamento pgto2 = new PagamentoComBoleto(null, EstadoPagamento.PENDENTE, ped2, sdf.parse("01/04/2019 00:00"), sdf.parse("01/05/2019 00:00"));
		ped2.setPagamento(pgto2);
		
		cli1.getPedidos().addAll(Arrays.asList(ped1, ped2));
		
		pedidoRepository.save(Arrays.asList(ped1, ped2));
		pagamentoRepository.save(Arrays.asList(pgto1, pgto2));
		
		ItemPedido ip1 = new ItemPedido(ped1, p1, 0.00, 1, 10.00);
		ItemPedido ip2 = new ItemPedido(ped1, p3, 0.00, 2, 80.00);
		ItemPedido ip3 = new ItemPedido(ped2, p2, 100.00, 1, 20.00);
		
		ped1.getItens().addAll(Arrays.asList(ip1,ip2));
		ped2.getItens().addAll(Arrays.asList(ip3));
		
		p1.getItens().addAll(Arrays.asList(ip1));
		p2.getItens().addAll(Arrays.asList(ip3));
		p3.getItens().addAll(Arrays.asList(ip2));
		
		itemPredidoRepository.save(Arrays.asList(ip1, ip2, ip3));
	}
}
