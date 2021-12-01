package com.daviholanda.cursomc.service.validation;

import com.daviholanda.cursomc.domain.Cliente;
import com.daviholanda.cursomc.domain.enums.TipoCliente;
import com.daviholanda.cursomc.dto.ClienteNewDTO;
import com.daviholanda.cursomc.repository.ClienteRepository;
import com.daviholanda.cursomc.resources.exception.FieldMessage;
import com.daviholanda.cursomc.service.validation.utils.BR;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.ArrayList;
import java.util.List;

public class ClienteInsertValidator implements ConstraintValidator<ClienteInsert, ClienteNewDTO> {

    @Autowired
    private ClienteRepository clienteRepository;

    public void initialize(ClienteInsert ann) {

    }

    public boolean isValid(ClienteNewDTO objDto, ConstraintValidatorContext context) {

        List<FieldMessage> list = new ArrayList<>();

        if(objDto.getCpfOuCnpj() == null || objDto.getCpfOuCnpj().isEmpty()) {
            return false;
        }

        if(objDto.getTipo() == TipoCliente.PESSOAFISICA.getCod() && !BR.isValidCpf(objDto.getCpfOuCnpj())) {
            list.add(new FieldMessage("cpfOuCnpj","CPF inválido"));
        }

        if(objDto.getTipo() == TipoCliente.PESSOAJURIDICA.getCod() && !BR.isValidCnpj(objDto.getCpfOuCnpj())) {
            list.add(new FieldMessage("cpfOuCnpj","CNPJ inválido inválido"));
        }

        Cliente aux = clienteRepository.findByEmail(objDto.getEmail());

        if(aux != null) {
            list.add(new FieldMessage("email","Email já existente"));
        }

        for(FieldMessage e: list) {

            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(e.getMessage()).addPropertyNode(e.getFieldName())
                    .addConstraintViolation();

        }

        return list.isEmpty();

    }
}
