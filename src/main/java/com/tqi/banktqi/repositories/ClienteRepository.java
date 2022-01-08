package com.tqi.banktqi.repositories;

import com.tqi.banktqi.model.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * A interface <b>ClienteRepository</b> Repositório da classe Cliente
 * @author Franco Canizo Brasil Sobrinho
 * @since jan 2022
 * @version 1.0
 */

public interface ClienteRepository extends JpaRepository<Cliente, Long> {

    Cliente findByEmail(String email);
}
