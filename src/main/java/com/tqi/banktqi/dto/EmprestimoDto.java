package com.tqi.banktqi.dto;

import com.tqi.banktqi.model.Emprestimo;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.domain.Page;

import javax.validation.constraints.Future;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * A classe <b>EmprestimoDto</b> DTO da classe Emprestimo
 * @author Franco Canizo Brasil Sobrinho
 * @since jan 2022
 * @version 1.0
 */

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class EmprestimoDto implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long id;
    private LocalDate dataEmprestimo;
    @Future
    @NotNull(message = "CAMPO OBRIGATÓRIO! Data da primeira parcela")
    private LocalDate dataPrimeiraParcela;
    @Positive
    @NotNull(message = "CAMPO OBRIGATÓRIO! Valor do empréstimo")
    private BigDecimal valor;
    @Positive
    @NotNull(message = "CAMPO OBRIGATÓRIO! Qtde de parcelas do empréstimo")
    private Integer qtdeParcelas;

    public EmprestimoDto(Emprestimo entity) {
        id = entity.getId();
        dataEmprestimo = entity.getDataEmprestimo();
        dataPrimeiraParcela = entity.getDataPrimeiraParcela();
        valor = entity.getValor();
        qtdeParcelas = entity.getQtdeParcelas();
    }

    public static Page<EmprestimoDto> converter(Page<Emprestimo> page) {
        return page.map(EmprestimoDto::new);
    }
}
