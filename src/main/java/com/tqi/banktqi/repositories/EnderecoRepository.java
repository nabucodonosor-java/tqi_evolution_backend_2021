package com.tqi.banktqi.repositories;

import com.tqi.banktqi.model.Endereco;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * A interface <b>EnderecoRepository</b> Repositório da classe Endereço
 * @author Franco Canizo Brasil Sobrinho
 * @since jan 2022
 * @version 1.0
 */

public interface EnderecoRepository extends JpaRepository<Endereco, Long> {
}
