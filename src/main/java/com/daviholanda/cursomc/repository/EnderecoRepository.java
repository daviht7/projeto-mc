package com.daviholanda.cursomc.repository;

import com.daviholanda.cursomc.domain.Cliente;
import com.daviholanda.cursomc.domain.Endereco;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EnderecoRepository extends JpaRepository<Endereco,Long> {

}
