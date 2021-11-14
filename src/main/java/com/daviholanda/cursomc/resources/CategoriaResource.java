package com.daviholanda.cursomc.resources;

import java.util.ArrayList;
import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.daviholanda.cursomc.domain.Categoria;

@RestController
@RequestMapping(value="/categorias")
public class CategoriaResource {

	@GetMapping
	public List<Categoria> listart() {
		
		Categoria categoria = new Categoria(1,"Informática");

		Categoria categoria2 = new Categoria(2,"Escritório");
		
		List<Categoria> categorias = new ArrayList<>();
		
		categorias.add(categoria);

		categorias.add(categoria2);
		
		return categorias;
	}
	
}
