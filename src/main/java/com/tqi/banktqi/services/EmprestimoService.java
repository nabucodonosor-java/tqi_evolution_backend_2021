package com.tqi.banktqi.services;

import java.time.LocalDate;
import java.util.Optional;

import com.tqi.banktqi.services.exceptions.UnauthorizedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tqi.banktqi.dto.DetalhesEmprestimoDto;
import com.tqi.banktqi.dto.EmprestimoDto;
import com.tqi.banktqi.model.Cliente;
import com.tqi.banktqi.model.Emprestimo;
import com.tqi.banktqi.repositories.ClienteRepository;
import com.tqi.banktqi.repositories.EmprestimoRepository;
import com.tqi.banktqi.services.exceptions.InstallmentsPaymentException;
import com.tqi.banktqi.services.exceptions.InvalidDateException;
import com.tqi.banktqi.services.exceptions.ResourceNotFoundException;
import org.springframework.web.bind.MethodArgumentNotValidException;

/**
 * A classe <b>EmprestimoService</b> Classe de serviço responsável pelos métodos de CRUD de Empréstimos
 * @author Franco Canizo Brasil Sobrinho
 * @since jan 2022
 * @version 1.0
 */

@Service
public class EmprestimoService {

    @Autowired
    private EmprestimoRepository repository;

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private AuthService authService;

    @Transactional(readOnly = true)
    public Page<EmprestimoDto> findAllForCurrentUser(PageRequest pageRequest) {
        Cliente cliente = authService.authenticated();
        Page<Emprestimo> page = repository.find(cliente, pageRequest);
        return EmprestimoDto.converter(page);
    }

    @Transactional(readOnly = true)
    public DetalhesEmprestimoDto findById(Long id) {
        Cliente cliente = clienteRepository.getOne(authService.authenticated().getId());
        Optional<Emprestimo> optional = repository.findById(id);
        if (optional.get().getId() != cliente.getId()) {
            throw new UnauthorizedException("Acesso Negado!");
        }
        Emprestimo entity = optional.orElseThrow(() -> new ResourceNotFoundException("Empréstimo não encontrado!"));
        return new DetalhesEmprestimoDto(entity);
    }

    @Transactional
    public EmprestimoDto insert(EmprestimoDto dto) {
            Emprestimo entity = new Emprestimo();
            copyDtoToEntity(dto, entity);
            entity = repository.save(entity);
            return new EmprestimoDto(entity);
        }

    private void copyDtoToEntity(EmprestimoDto dto, Emprestimo entity) {

        Cliente cliente = clienteRepository.getOne(authService.authenticated().getId());
        LocalDate dtAtual = LocalDate.now();
        LocalDate dtMaxPrimeiraParcela = dtAtual.plusMonths(3);

        if (entity.getDataEmprestimo() == null) {
            entity.setDataEmprestimo(LocalDate.now());
        }
        if (dtMaxPrimeiraParcela.compareTo(dto.getDataPrimeiraParcela()) < 0 && dto.getDataPrimeiraParcela() != null) {
            throw new InvalidDateException("Data da primeira parcela deve ser no máximo 3 meses após o dia atual");
        } else {
            entity.setDataPrimeiraParcela(dto.getDataPrimeiraParcela());
        }
        entity.setValor(dto.getValor());
        if (dto.getQtdeParcelas() > 60) {
            throw new InstallmentsPaymentException("Máximo de 60 parcelas");
        } else {
            entity.setQtdeParcelas(dto.getQtdeParcelas());
        }
        entity.setCliente(cliente);
    }

}
