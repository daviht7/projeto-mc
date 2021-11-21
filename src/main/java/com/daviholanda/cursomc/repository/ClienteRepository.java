package com.daviholanda.cursomc.repository;

import com.daviholanda.cursomc.domain.Categoria;
import com.daviholanda.cursomc.domain.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente,Long> {

}
