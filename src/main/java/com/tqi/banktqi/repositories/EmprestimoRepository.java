package com.tqi.banktqi.repositories;

import com.tqi.banktqi.model.Cliente;
import com.tqi.banktqi.model.Emprestimo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

/**
 * A interface <b>EmprestimoRepository</b> Repositório da classe Empréstimo
 * @author Franco Canizo Brasil Sobrinho
 * @since jan 2022
 * @version 1.0
 */

public interface EmprestimoRepository extends JpaRepository<Emprestimo, Long> {

    @Query("SELECT obj FROM Emprestimo obj WHERE "
            + "(obj.cliente = :cliente) ORDER BY obj.dataEmprestimo DESC")
    Page<Emprestimo> find(Cliente cliente, Pageable pageable);

}
