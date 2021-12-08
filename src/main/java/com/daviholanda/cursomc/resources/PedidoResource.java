package com.daviholanda.cursomc.resources;

import com.daviholanda.cursomc.domain.Categoria;
import com.daviholanda.cursomc.domain.Pedido;
import com.daviholanda.cursomc.dto.CategoriaDTO;
import com.daviholanda.cursomc.repository.CategoriaRepository;
import com.daviholanda.cursomc.repository.PedidoRepository;
import com.daviholanda.cursomc.service.CategoriaService;
import com.daviholanda.cursomc.service.PedidoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping(value="/pedidos")
public class PedidoResource {
	
	@Autowired
	private PedidoService pedidoService;

	@GetMapping("/{id}")
	public ResponseEntity<?> find(@PathVariable Long id) {
		Pedido pedido = pedidoService.find(id);
		return ResponseEntity.ok(pedido);
	}

	@PostMapping
	public ResponseEntity<Void> insert(@RequestBody Pedido pedido) {

		pedido = pedidoService.insert(pedido);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(pedido.getId()).toUri();

		return ResponseEntity.created(uri).build();
	}
	
}
