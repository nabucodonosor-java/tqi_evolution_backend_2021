package com.tqi.banktqi.controllers;

import java.net.URI;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.tqi.banktqi.dto.DetalhesEmprestimoDto;
import com.tqi.banktqi.dto.EmprestimoDto;
import com.tqi.banktqi.services.EmprestimoService;

/**
 * A classe <b>EmprestimoController</b> RestController de Empréstimos
 * @author Franco Canizo Brasil Sobrinho
 * @since jan 2022
 * @version 1.0
 */

@RestController
@RequestMapping("/loans")
public class EmprestimoController {

    @Autowired
    private EmprestimoService service;

    @GetMapping
    public ResponseEntity<Page<EmprestimoDto>> findAllPaged(
            @RequestParam(value = "page", defaultValue = "0") Integer page,
            @RequestParam(value = "size", defaultValue = "12") Integer size,
            @RequestParam(value = "direction", defaultValue = "DESC") String direction,
            @RequestParam(value = "sort", defaultValue = "dataEmprestimo") String sort) {

        PageRequest pageRequest = PageRequest.of(page, size, Direction.valueOf(direction), sort);
        Page<EmprestimoDto> list = service.findAllForCurrentUser(pageRequest);

        return ResponseEntity.ok().body(list);
    }

    @GetMapping("/{id}")
    public ResponseEntity<DetalhesEmprestimoDto> findById(@PathVariable Long id) {
        DetalhesEmprestimoDto dto = service.findById(id);
        return ResponseEntity.ok().body(dto);
    }

    @PostMapping
    public ResponseEntity<EmprestimoDto> insert(@Valid @RequestBody EmprestimoDto dto) {
        dto = service.insert(dto);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(dto.getId()).toUri();
        return ResponseEntity.created(uri).body(dto);
    }

}
