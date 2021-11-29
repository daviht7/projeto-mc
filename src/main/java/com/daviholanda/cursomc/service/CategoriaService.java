package com.daviholanda.cursomc.service;

import com.daviholanda.cursomc.domain.Categoria;
import com.daviholanda.cursomc.domain.Cliente;
import com.daviholanda.cursomc.dto.CategoriaDTO;
import com.daviholanda.cursomc.repository.CategoriaRepository;
import com.daviholanda.cursomc.service.exceptions.DataIntegrityException;
import com.daviholanda.cursomc.service.exceptions.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CategoriaService {

    @Autowired
    private CategoriaRepository categoriaRepository;

    public Categoria find(Long id) {
        Optional<Categoria> categoria = categoriaRepository.findById(id);
        return categoria.orElseThrow(() -> new ObjectNotFoundException(String.format("Objeto não encontrado! Id : %s, do tipo: %s", id, Categoria.class.getName())));
    }

    public List<CategoriaDTO> findAll() {
        List<CategoriaDTO> categoriasDTO = categoriaRepository.findAll().stream().map(x -> new CategoriaDTO(x)).collect(Collectors.toList());
        return categoriasDTO;
    }

    public Categoria insert(Categoria obj) {
        obj.setId(null);
        return categoriaRepository.save(obj);
    }

    public Categoria update(Categoria obj) {
        Categoria newObj = find(obj.getId());
        updateData(newObj, obj);
        return categoriaRepository.save(newObj);
    }

    public void delete(Long id) {
        find(id);
        try {
            categoriaRepository.deleteById(id);
        } catch (DataIntegrityViolationException e) {
            throw new DataIntegrityException("Não é possível excluir uma categoria que possui produtos.");
        }
    }

    public Page<CategoriaDTO> findPage(Integer page, Integer linesPerPage, String orderBy, String direction) {
        PageRequest pageRequest = PageRequest.of(page, linesPerPage, Sort.Direction.valueOf(direction), orderBy);
        Page<Categoria> categoriaPage = categoriaRepository.findAll(pageRequest);
        Page<CategoriaDTO> categoriasDTO = categoriaPage.map(x -> new CategoriaDTO(x));
        return categoriasDTO;
    }

    public Categoria fromDTO(CategoriaDTO objDTO) {
        return new Categoria(objDTO.getId(), objDTO.getNome());
    }

    private void updateData(Categoria newObj, Categoria obj) {
        newObj.setNome(obj.getNome());
    }

}
