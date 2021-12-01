package com.daviholanda.cursomc.service.validation;

import com.daviholanda.cursomc.domain.enums.TipoCliente;
import com.daviholanda.cursomc.dto.ClienteNewDTO;
import com.daviholanda.cursomc.resources.exception.FieldMessage;
import com.daviholanda.cursomc.service.validation.utils.BR;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.ArrayList;
import java.util.List;

public class ClienteInsertValidator implements ConstraintValidator<ClienteInsert, ClienteNewDTO> {

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

        for(FieldMessage e: list) {

            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(e.getMessage()).addPropertyNode(e.getFieldName())
                    .addConstraintViolation();

        }

        return list.isEmpty();

    }
}
