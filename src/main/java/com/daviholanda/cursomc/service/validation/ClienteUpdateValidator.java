package com.daviholanda.cursomc.service.validation;

import com.daviholanda.cursomc.domain.Cliente;
import com.daviholanda.cursomc.domain.enums.TipoCliente;
import com.daviholanda.cursomc.dto.ClienteDTO;
import com.daviholanda.cursomc.repository.ClienteRepository;
import com.daviholanda.cursomc.resources.exception.FieldMessage;
import com.daviholanda.cursomc.service.validation.utils.BR;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerMapping;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ClienteUpdateValidator implements ConstraintValidator<ClienteUpdate, ClienteDTO> {

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private HttpServletRequest request;

    public void initialize(ClienteUpdate ann) {

    }

    public boolean isValid(ClienteDTO objDto, ConstraintValidatorContext context) {

        Map<String,String> map = (Map<String,String>) request.getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE);

        Long uriId =  Long.parseLong(map.get("id"));

        List<FieldMessage> list = new ArrayList<>();

        Cliente aux = clienteRepository.findByEmail(objDto.getEmail());

        if(aux != null && !aux.getId().equals(uriId)) {
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
