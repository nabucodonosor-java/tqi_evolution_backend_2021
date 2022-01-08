package com.tqi.banktqi.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * A classe <b>Emprestimo</b> Modelo para criação do objeto empréstimo que está atrelado a um objeto Cliente
 * @author Franco Canizo Brasil Sobrinho
 * @since jan 2022
 * @version 1.0
 */

@Entity
@Table(name = "Emprestimos")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Emprestimo implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(columnDefinition = "TIMESTAMP WITHOUT TIME ZONE", nullable = false)
    private LocalDate dataEmprestimo;

    @Column(columnDefinition = "TIMESTAMP WITHOUT TIME ZONE", nullable = false)
    private LocalDate dataPrimeiraParcela;
    @Column(nullable = false)
    private BigDecimal valor;
    @Column(nullable = false)
    private Integer qtdeParcelas;

    @ManyToOne
    @JoinColumn(name = "cliente_id")
    private Cliente cliente;

}
