package com.daviholanda.cursomc.resources;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.daviholanda.cursomc.domain.Categoria;
import com.daviholanda.cursomc.repository.CategoriaRepository;
import com.daviholanda.cursomc.service.CategoriaService;

@RestController
@RequestMapping(value="/categorias")
public class CategoriaResource {
	
	@Autowired
	private CategoriaService categoriaService;
	
	@Autowired
	private CategoriaRepository categoriaRepository;

	@GetMapping()
	public List<Categoria> listar() {
		
		var categorias = categoriaRepository.findAll();
		
		return categorias;
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<?> find(@PathVariable Long id) {
		
		Categoria categoria = categoriaService.buscar(id);
		
		return ResponseEntity.ok(categoria);
	}
	
}
