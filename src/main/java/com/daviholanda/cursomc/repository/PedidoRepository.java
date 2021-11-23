package com.daviholanda.cursomc.repository;

import com.daviholanda.cursomc.domain.Categoria;
import com.daviholanda.cursomc.domain.Pedido;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PedidoRepository extends JpaRepository<Pedido,Long> {

}