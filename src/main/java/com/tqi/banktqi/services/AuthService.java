package com.tqi.banktqi.services;

import com.tqi.banktqi.model.Cliente;
import com.tqi.banktqi.repositories.ClienteRepository;
import com.tqi.banktqi.services.exceptions.UnauthorizedException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * A classe <b>AuthService</b> Classe de serviço responsável por obter o cliente logado
 * @author Franco Canizo Brasil Sobrinho
 * @since jan 2022
 * @version 1.0
 */

@Service
public class AuthService {

    @Autowired
    private ClienteRepository repository;

    @Transactional(readOnly = true)
    public Cliente authenticated() {
        try {

            String username = SecurityContextHolder.getContext().getAuthentication().getName();
            return repository.findByEmail(username);

        }
        catch (Exception e) {
            throw new UnauthorizedException("Cliente Inválido!");
        }
    }
}
