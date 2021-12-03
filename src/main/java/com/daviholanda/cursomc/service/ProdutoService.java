package com.daviholanda.cursomc.service;

import com.daviholanda.cursomc.domain.Categoria;
import com.daviholanda.cursomc.domain.Produto;
import com.daviholanda.cursomc.dto.CategoriaDTO;
import com.daviholanda.cursomc.dto.ProdutoDTO;
import com.daviholanda.cursomc.repository.CategoriaRepository;
import com.daviholanda.cursomc.repository.ProdutoRepository;
import com.daviholanda.cursomc.service.exceptions.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProdutoService {

    @Autowired
    private ProdutoRepository produtoRepository;

    @Autowired
    private CategoriaRepository categoriaRepository;

    public Produto find(Long id) {
        Optional<Produto> produto = produtoRepository.findById(id);
        return produto.orElseThrow(() -> new ObjectNotFoundException(String.format("Objeto não encontrado! Id : %s, do tipo: %s", id, Produto.class.getName())));
    }

    public Page<ProdutoDTO> search(String nome, List<Long> ids, Integer page, Integer linesPerPage, String orderBy, String direction) {
        PageRequest pageRequest = PageRequest.of(page, linesPerPage, Sort.Direction.valueOf(direction), orderBy);

        List<Categoria> categorias = categoriaRepository.findAllById(ids);

        Page<Produto> produtosPage = produtoRepository.search(nome, categorias, pageRequest);
        Page<ProdutoDTO> produtosDTO = produtosPage.map(x -> new ProdutoDTO(x));

        return produtosDTO;
    }

}
