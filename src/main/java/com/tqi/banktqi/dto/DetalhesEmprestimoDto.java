package com.tqi.banktqi.dto;

import com.tqi.banktqi.model.Emprestimo;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * A classe <b>DetalhesEmprestimoDto</b> DTO responsável por retornar detalhamento do empréstimo
 * @author Franco Canizo Brasil Sobrinho
 * @since jan 2022
 * @version 1.0
 */

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DetalhesEmprestimoDto implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long id;
    private BigDecimal valor;
    private Integer qtdeParcelas;
    private LocalDate dataPrimeiraParcela;
    private String emailCLiente;
    private BigDecimal rendaCliente;

    public DetalhesEmprestimoDto(Emprestimo entity) {
        id = entity.getId();
        valor = entity.getValor();
        qtdeParcelas = entity.getQtdeParcelas();
        dataPrimeiraParcela = entity.getDataPrimeiraParcela();
        emailCLiente = entity.getCliente().getEmail();
        rendaCliente = entity.getCliente().getRenda();
    }
}
