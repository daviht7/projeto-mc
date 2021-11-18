package com.daviholanda.cursomc.resources;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.daviholanda.cursomc.domain.Categoria;
import com.daviholanda.cursomc.service.CategoriaService;

@RestController
@RequestMapping(value="/categorias")
public class CategoriaResource {
	
	@Autowired
	private CategoriaService categoriaService;

	@GetMapping()
	public List<Categoria> listar() {
		
		Categoria categoria = new Categoria(1,"Informática");

		Categoria categoria2 = new Categoria(2,"Escritório");
		
		List<Categoria> categorias = new ArrayList<>();
		
		categorias.add(categoria);

		categorias.add(categoria2);
		
		return categorias;
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<?> find(@PathVariable Long id) {
		
		Categoria categoria = categoriaService.buscar(id);
		
		return ResponseEntity.ok(categoria);
	}
	
}
