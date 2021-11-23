package com.daviholanda.cursomc.resources;

import com.daviholanda.cursomc.domain.Categoria;
import com.daviholanda.cursomc.domain.Pedido;
import com.daviholanda.cursomc.repository.CategoriaRepository;
import com.daviholanda.cursomc.repository.PedidoRepository;
import com.daviholanda.cursomc.service.CategoriaService;
import com.daviholanda.cursomc.service.PedidoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value="/pedidos")
public class PedidoResource {
	
	@Autowired
	private PedidoService pedidoService;

	@GetMapping("/{id}")
	public ResponseEntity<?> find(@PathVariable Long id) {
		
		Pedido pedido = pedidoService.buscar(id);
		
		return ResponseEntity.ok(pedido);
	}
	
}
