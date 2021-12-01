package com.daviholanda.cursomc.repository;

import com.daviholanda.cursomc.domain.Categoria;
import com.daviholanda.cursomc.domain.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente,Long> {

    @Transactional(readOnly = true)
    Cliente findByEmail(String email);

}
