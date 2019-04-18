package com.somhodoce.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.somhodoce.api.model.Cidade;
import com.somhodoce.api.repository.cidade.CidadeRepositoryQuery;

public interface CidadeRepository extends JpaRepository<Cidade, Long>, CidadeRepositoryQuery{

}
