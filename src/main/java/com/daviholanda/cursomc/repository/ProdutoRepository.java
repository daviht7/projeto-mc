package com.daviholanda.cursomc.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.daviholanda.cursomc.domain.Produto;

@Repository
public interface ProdutoRepository extends JpaRepository<Produto,Long> {

}
