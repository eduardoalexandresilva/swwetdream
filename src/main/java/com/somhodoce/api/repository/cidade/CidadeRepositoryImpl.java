package com.somhodoce.api.repository.cidade;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.util.StringUtils;

import com.somhodoce.api.model.Cidade;
import com.somhodoce.api.repository.filter.CidadeFilter;

public class CidadeRepositoryImpl implements CidadeRepositoryQuery {

	@PersistenceContext
	private EntityManager manager;
	
	
	@Override
	public Page<Cidade> filtrar(CidadeFilter cidadeFilter, Pageable pageable) {
		CriteriaBuilder builder = manager.getCriteriaBuilder();
		CriteriaQuery<Cidade> criteria = builder.createQuery(Cidade.class);
		Root<Cidade> root = criteria.from(Cidade.class);
		
		Predicate[] predicates = criarRestricoes(cidadeFilter, builder, root);
		criteria.where(predicates);
						
		TypedQuery<Cidade> query = manager.createQuery(criteria);
		adicionarRestricoesDePaginacao(query, pageable);
		return new PageImpl<>(query.getResultList(), pageable, total(cidadeFilter));
	}
	
	private Long total(CidadeFilter cidadeFilter) {
		  CriteriaBuilder builder = manager.getCriteriaBuilder();
		  CriteriaQuery<Long> criteria = builder.createQuery(Long.class);
		  Root<Cidade> root = criteria.from(Cidade.class);
		  
		  Predicate[] predicates = criarRestricoes(cidadeFilter, builder, root);
		  criteria.where(predicates);
		  
		  criteria.select(builder.count(root));
			return manager.createQuery(criteria).getSingleResult();
		}

		private void adicionarRestricoesDePaginacao(TypedQuery<Cidade> query, Pageable pageable) {
			int paginaAtual = pageable.getPageNumber();
			int totalRegistrosPorPagina = pageable.getPageSize();
			int primeiroRegistroDaPagina = paginaAtual * totalRegistrosPorPagina;
			
			query.setFirstResult(primeiroRegistroDaPagina);
			query.setMaxResults(totalRegistrosPorPagina);
			
		}

		private Predicate[] criarRestricoes(CidadeFilter cidadeFilter, CriteriaBuilder builder, Root<Cidade> root) {
			
			List<Predicate> predicates = new ArrayList<>();
			
			if(!StringUtils.isEmpty(cidadeFilter.getNome())) {
			  predicates.add(builder.like(
					  builder.lower(root.get("nome")), "%" + cidadeFilter.getNome().toLowerCase() + "%"));	
			}
			
					
			
			return predicates.toArray(new Predicate[predicates.size()]);
		}

}
