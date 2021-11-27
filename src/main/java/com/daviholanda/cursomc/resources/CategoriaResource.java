package com.daviholanda.cursomc.resources;

import com.daviholanda.cursomc.domain.Categoria;
import com.daviholanda.cursomc.dto.CategoriaDTO;
import com.daviholanda.cursomc.repository.CategoriaRepository;
import com.daviholanda.cursomc.service.CategoriaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping(value = "/categorias")
public class CategoriaResource {

    @Autowired
    private CategoriaService categoriaService;

    @GetMapping()
    public ResponseEntity<List<CategoriaDTO>> findAll() {
        var categorias = categoriaService.findAll();
        return ResponseEntity.ok().body(categorias);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Categoria> find(@PathVariable Long id) {
        Categoria categoria = categoriaService.find(id);
        return ResponseEntity.ok(categoria);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Categoria> delete(@PathVariable Long id) {
        categoriaService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping
    public ResponseEntity<Void> insert(@RequestBody Categoria obj) {

        obj = categoriaService.insert(obj);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(obj.getId()).toUri();

        return ResponseEntity.created(uri).build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> update(@PathVariable Long id, @RequestBody Categoria categoria) {

        categoria.setId(id);
        categoria = categoriaService.update(categoria);

        return ResponseEntity.noContent().build();
    }

}
