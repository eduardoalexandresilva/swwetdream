package com.somhodoce.api.service;

import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.somhodoce.api.model.Categoria;
import com.somhodoce.api.repository.CategoriaRepository;

@Service
public class CategoriaService {

	@Autowired
	private CategoriaRepository categoriaRepository;
	
	public Categoria update(Categoria categoria, Integer codigo) {
		Categoria catSalva = buscarCategoriaPeloCodigo(codigo);
		BeanUtils.copyProperties(categoria, catSalva, "codigo");
		return categoriaRepository.save(catSalva);
	}
	
	public Categoria buscarCategoriaPeloCodigo(Integer codigo) {
		Categoria categoriaSalva = categoriaRepository.findOne(codigo);
		if(categoriaSalva == null) {
			throw new EmptyResultDataAccessException(1);
		}
		return categoriaSalva;
	}

	public List<Categoria> findAll() {
		return	categoriaRepository.findAll();
		
	}
	
	public Page<Categoria> findPage(Integer page, Integer linesPerPage, String oderBy, String direction){
		PageRequest pageRequest = new PageRequest(page, linesPerPage, Direction.valueOf(direction), oderBy);
		return categoriaRepository.findAll(pageRequest);
	}

}









