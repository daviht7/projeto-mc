package com.daviholanda.cursomc.resources;

import com.daviholanda.cursomc.domain.Pedido;
import com.daviholanda.cursomc.service.PedidoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping(value = "/pedidos")
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

    @GetMapping
    public ResponseEntity<Page<Pedido>> findPage(@RequestParam(value = "page", defaultValue = "0") Integer page,
                                                 @RequestParam(value = "linesPerPage", defaultValue = "20") Integer linesPerPage,
                                                 @RequestParam(value = "orderBy", defaultValue = "id") String orderBy,
                                                 @RequestParam(value = "direction", defaultValue = "DESC") String direction) {
        Page<Pedido> pedidos = pedidoService.findPage(page, linesPerPage, orderBy, direction);
        return ResponseEntity.ok().body(pedidos);
    }

}
