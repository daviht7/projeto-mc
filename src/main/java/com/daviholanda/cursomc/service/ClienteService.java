package com.daviholanda.cursomc.service;

import com.daviholanda.cursomc.domain.Cidade;
import com.daviholanda.cursomc.domain.Cliente;
import com.daviholanda.cursomc.domain.Endereco;
import com.daviholanda.cursomc.domain.enums.TipoCliente;
import com.daviholanda.cursomc.dto.ClienteDTO;
import com.daviholanda.cursomc.dto.ClienteNewDTO;
import com.daviholanda.cursomc.repository.ClienteRepository;
import com.daviholanda.cursomc.repository.EnderecoRepository;
import com.daviholanda.cursomc.service.exceptions.DataIntegrityException;
import com.daviholanda.cursomc.service.exceptions.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ClienteService {

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private EnderecoRepository enderecoRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    public Cliente find(Long id) {
        Optional<Cliente> cliente = clienteRepository.findById(id);
        return cliente.orElseThrow(() -> new ObjectNotFoundException(String.format("Objeto não encontrado! Id : %s, do tipo: %s", id, Cliente.class.getName())));
    }

    public List<ClienteDTO> findAll() {
        List<ClienteDTO> clientesDTO = clienteRepository.findAll().stream().map(x -> new ClienteDTO(x)).collect(Collectors.toList());
        return clientesDTO;
    }

    @Transactional
    public Cliente insert(Cliente obj) {
        obj.setId(null);
        Cliente c = clienteRepository.save(obj);
        enderecoRepository.saveAll(obj.getEnderecos());
        return c;
    }

    public Cliente update(Cliente obj) {
        Cliente newObj = find(obj.getId());
        updateData(newObj, obj);
        return clienteRepository.save(newObj);
    }

    public void delete(Long id) {
        find(id);
        try {
            clienteRepository.deleteById(id);
        } catch (DataIntegrityViolationException e) {
            throw new DataIntegrityException("Não é possível excluir um Cliente que tenha pedidos");
        }
    }

    public Page<ClienteDTO> findPage(Integer page, Integer linesPerPage, String orderBy, String direction) {
        PageRequest pageRequest = PageRequest.of(page, linesPerPage, Sort.Direction.valueOf(direction), orderBy);
        Page<Cliente> clientePage = clienteRepository.findAll(pageRequest);
        Page<ClienteDTO> clientesDTO = clientePage.map(x -> new ClienteDTO(x));
        return clientesDTO;
    }

    public Cliente fromDTO(ClienteNewDTO objDTO) {
       Cliente cli = new Cliente(null,objDTO.getNome(),objDTO.getEmail(), objDTO.getCpfOuCnpj(), TipoCliente.toEnum(objDTO.getTipo()),bCryptPasswordEncoder.encode(objDTO.getSenha()));

       Cidade cidade = new Cidade(objDTO.getCidadeId(),null,null);

       Endereco end = new Endereco(null,objDTO.getLogradouro(),objDTO.getNumero(),objDTO.getComplemento(),objDTO.getBairro(),objDTO.getCep(),cli,cidade);

       cli.getEnderecos().add(end);

       cli.getTelefones().add(objDTO.getTelefone1());

       if(objDTO.getTelefone2() != null) {
           cli.getTelefones().add(objDTO.getTelefone2());
       }

        if(objDTO.getTelefone3() != null) {
            cli.getTelefones().add(objDTO.getTelefone3());
        }

       return cli;
    }


    public Cliente fromDTO(ClienteDTO objDTO) {
        return new Cliente(objDTO.getId(), objDTO.getNome(), objDTO.getEmail(),null,null,null);
    }

    private void updateData(Cliente newObj, Cliente obj) {
        newObj.setNome(obj.getNome());
        newObj.setEmail(obj.getEmail());
        newObj.setSenha(obj.getSenha());

    }

}
