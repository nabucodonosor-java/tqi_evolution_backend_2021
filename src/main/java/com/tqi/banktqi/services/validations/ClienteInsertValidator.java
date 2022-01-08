package com.tqi.banktqi.services.validations;

import java.util.ArrayList;
import java.util.List;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;

import com.tqi.banktqi.controllers.exceptions.FieldMessage;
import com.tqi.banktqi.dto.ClienteInsertDto;
import com.tqi.banktqi.model.Cliente;
import com.tqi.banktqi.repositories.ClienteRepository;

/**
 * A classe <b>ClienteInsertValidator</b> Classe responsável por validações de autenticação do cliente
 * @author Franco Canizo Brasil Sobrinho
 * @since jan 2022
 * @version 1.0
 */

public class ClienteInsertValidator implements ConstraintValidator<ClienteInsertValid, ClienteInsertDto> {

    @Autowired
    private ClienteRepository repository;

    @Override
    public void initialize(ClienteInsertValid ann) {
    }

    @Override
    public boolean isValid(ClienteInsertDto dto, ConstraintValidatorContext context) {

        List<FieldMessage> list = new ArrayList<>();

        Cliente cliente = repository.findByEmail(dto.getEmail());

        if(cliente != null) {
            list.add(new FieldMessage("email", "Email já existe!"));
        }

        for (FieldMessage e : list) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(e.getMessage()).addPropertyNode(e.getFieldName())
                    .addConstraintViolation();
        }
        return list.isEmpty();
    }
}
