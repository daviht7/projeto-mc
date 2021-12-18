package com.daviholanda.cursomc.resources;

import com.daviholanda.cursomc.domain.Produto;
import com.daviholanda.cursomc.dto.ProdutoDTO;
import com.daviholanda.cursomc.resources.utils.URL;
import com.daviholanda.cursomc.service.ProdutoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/produtos")
public class ProdutoResource {

    @Autowired
    private ProdutoService produtoService;

    @GetMapping("/{id}")
    public ResponseEntity<?> find(@PathVariable Long id) {
        Produto produto = produtoService.find(id);
        return ResponseEntity.ok(produto);
    }

    @GetMapping()
    public ResponseEntity<Page<ProdutoDTO>> findPage(
            @RequestParam(value = "nome", defaultValue = "") String nome,
            @RequestParam(value = "categorias", defaultValue = "") String ids,
            @RequestParam(value = "page", defaultValue = "0") Integer page,
            @RequestParam(value = "linesPerPage", defaultValue = "2") Integer linesPerPage,
            @RequestParam(value = "orderBy", defaultValue = "id") String orderBy,
            @RequestParam(value = "direction", defaultValue = "ASC") String direction) {

        String nomeDecoded = URL.decodeParam(nome);
        List<Long> categorias = URL.decodeIntList(ids);

        Page<ProdutoDTO> produtosPageDTO = produtoService.search(nomeDecoded, categorias, page, linesPerPage, orderBy, direction);
        return ResponseEntity.ok().body(produtosPageDTO);
    }

}
