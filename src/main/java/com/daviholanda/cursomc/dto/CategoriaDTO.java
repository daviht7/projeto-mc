package com.daviholanda.cursomc.dto;

import com.daviholanda.cursomc.domain.Categoria;

import java.io.Serializable;

public class CategoriaDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;
    private String nome;

    public CategoriaDTO() {

    }

    public CategoriaDTO(Categoria c) {
        this.id = c.getId();
        this.nome =c.getNome();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }
}
