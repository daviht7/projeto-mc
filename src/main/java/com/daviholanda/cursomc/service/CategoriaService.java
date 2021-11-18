package com.daviholanda.cursomc.service;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.daviholanda.cursomc.domain.Categoria;
import com.daviholanda.cursomc.repository.CategoriaRepository;

@Service
public class CategoriaService {

	private CategoriaRepository categoriaRepository;
	
	public Categoria buscar(Long id) {
		
		Optional<Categoria> categoria = categoriaRepository.findById(id);
		
		return categoria.orElse(null);
	}

}
