package com.somhodoce.api.repository.estado;

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

import com.somhodoce.api.model.Estado;
import com.somhodoce.api.repository.filter.EstadoFilter;

public class EstadoRepositoryImpl implements EstadoRepositoryQuery {

	@PersistenceContext
	private EntityManager manager;
	
	
	@Override
	public Page<Estado> filtrar(EstadoFilter estadoFilter, Pageable pageable) {
		CriteriaBuilder builder = manager.getCriteriaBuilder();
		CriteriaQuery<Estado> criteria = builder.createQuery(Estado.class);
		Root<Estado> root = criteria.from(Estado.class);
		
		Predicate[] predicates = criarRestricoes(estadoFilter, builder, root);
		criteria.where(predicates);
						
		TypedQuery<Estado> query = manager.createQuery(criteria);
		adicionarRestricoesDePaginacao(query, pageable);
		return new PageImpl<>(query.getResultList(), pageable, total(estadoFilter));
	}
	
	private Long total(EstadoFilter estadoFilter) {
		  CriteriaBuilder builder = manager.getCriteriaBuilder();
		  CriteriaQuery<Long> criteria = builder.createQuery(Long.class);
		  Root<Estado> root = criteria.from(Estado.class);
		  
		  Predicate[] predicates = criarRestricoes(estadoFilter, builder, root);
		  criteria.where(predicates);
		  
		  criteria.select(builder.count(root));
			return manager.createQuery(criteria).getSingleResult();
		}

		private void adicionarRestricoesDePaginacao(TypedQuery<Estado> query, Pageable pageable) {
			int paginaAtual = pageable.getPageNumber();
			int totalRegistrosPorPagina = pageable.getPageSize();
			int primeiroRegistroDaPagina = paginaAtual * totalRegistrosPorPagina;
			
			query.setFirstResult(primeiroRegistroDaPagina);
			query.setMaxResults(totalRegistrosPorPagina);
			
		}

		private Predicate[] criarRestricoes(EstadoFilter estadoFilter, CriteriaBuilder builder, Root<Estado> root) {
			
			List<Predicate> predicates = new ArrayList<>();
			
			if(!StringUtils.isEmpty(estadoFilter.getNome())) {
			  predicates.add(builder.like(
					  builder.lower(root.get("nome")), "%" + estadoFilter.getNome().toLowerCase() + "%"));	
			}
			
					
			
			return predicates.toArray(new Predicate[predicates.size()]);
		}

}
