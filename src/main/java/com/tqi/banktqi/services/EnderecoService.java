package com.tqi.banktqi.services;

import java.util.Optional;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tqi.banktqi.dto.EnderecoDto;
import com.tqi.banktqi.model.Endereco;
import com.tqi.banktqi.repositories.EnderecoRepository;
import com.tqi.banktqi.services.exceptions.ResourceNotFoundException;

/**
 * A classe <b>EnderecoService</b> Classe de serviço responsável pelos métodos de CRUD de Endereço
 * @author Franco Canizo Brasil Sobrinho
 * @since jan 2022
 * @version 1.0
 */

@Service
public class EnderecoService {

    @Autowired
    private EnderecoRepository repository;

    @Transactional(readOnly = true)
    public Page<EnderecoDto> findAllPaged(Pageable pageable) {
        Page<Endereco> page = repository.findAll(pageable);
        return EnderecoDto.converter(page);
    }

    @Transactional(readOnly = true)
    public EnderecoDto findById(Long id) {
        Optional<Endereco> optional = repository.findById(id);
        Endereco entity = optional.orElseThrow(() -> new ResourceNotFoundException("Endereço não encontrado!"));
        return new EnderecoDto(entity);
    }

    @Transactional
    public EnderecoDto insert(EnderecoDto dto) {
        Endereco entity = new Endereco();
        copyDtoToEntity(dto, entity);
        entity = repository.save(entity);
        return new EnderecoDto(entity);
    }

    @Transactional
    public EnderecoDto update(Long id, EnderecoDto dto) {

        try {

            Endereco entity = repository.getOne(id);
            copyDtoToEntity(dto, entity);
            entity = repository.save(entity);
            return new EnderecoDto(entity);

        } catch (EntityNotFoundException e) {
            throw new ResourceNotFoundException("Endereço não enconrado!");
        }
    }

    private void copyDtoToEntity(EnderecoDto dto, Endereco entity) {

        entity.setCep(dto.getCep());
        entity.setLogradouro(dto.getLogradouro());
        entity.setComplemento(dto.getComplemento());
        entity.setBairro(dto.getBairro());
        entity.setLocalidade(dto.getLocalidade());
        entity.setUf(dto.getUf());

    }

}
