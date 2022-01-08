package com.tqi.banktqi.services;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tqi.banktqi.dto.ClienteDto;
import com.tqi.banktqi.dto.ClienteInsertDto;
import com.tqi.banktqi.model.Cliente;
import com.tqi.banktqi.model.Endereco;
import com.tqi.banktqi.model.Role;
import com.tqi.banktqi.repositories.ClienteRepository;

/**
 * A classe <b>ClienteService</b> Classe de serviço responsável pelos métodos de CRUD de Clientes
 * @author Franco Canizo Brasil Sobrinho
 * @since jan 2022
 * @version 1.0
 */

@Service
public class ClienteService implements UserDetailsService {

    private static Logger logger = org.slf4j.LoggerFactory.getLogger(ClienteService.class);

    @Autowired
    private ClienteRepository repository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Transactional
    public @Valid ClienteInsertDto insert(ClienteInsertDto dto) {
        Cliente entity = new Cliente();
        copyDtoToEntity(dto, entity);
        entity.setSenha(passwordEncoder.encode(dto.getSenha()));
        entity = repository.save(entity);
        return new ClienteInsertDto(entity);
    }


    private void copyDtoToEntity(ClienteDto dto, Cliente entity) {
        entity.setNome(dto.getNome());
        entity.setEmail(dto.getEmail());
        entity.setRg(dto.getRg());
        entity.setCpf(dto.getCpf());
        entity.setRenda(dto.getRenda());

        entity.getRoles().clear();
        entity.getRoles().add(new Role(1L, "ROLE_CLIENTE"));
        // dto.getRoles().forEach(r -> entity.getRoles().add(new Role(r)));
        entity.getEnderecos().clear();
        dto.getEnderecos().forEach(end -> entity.getEnderecos().add(new Endereco(end)));
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Cliente cliente = repository.findByEmail(username);

        if (cliente == null) {
            logger.error("Cliente não encontrado: " + username);
            throw new UsernameNotFoundException("Email não encontrado!");
        }
        logger.info("Cliente: " + username);
        return cliente;
    }

}