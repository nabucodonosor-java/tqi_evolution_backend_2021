package com.tqi.banktqi.dto;

import com.tqi.banktqi.model.Endereco;
import com.tqi.banktqi.model.enums.TipoEndereco;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.domain.Page;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.io.Serializable;

/**
 * A classe <b>EnderecoDto</b> DTO da classe Endereco
 * @author Franco Canizo Brasil Sobrinho
 * @since jan 2022
 * @version 1.0
 */

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class EnderecoDto implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long id;
    @NotBlank(message = "Campo obrigatório")
    private TipoEndereco tipoEndereco;
    @NotBlank(message = "Campo obrigatório")
    private String cep;
    @NotBlank(message = "Campo obrigatório")
    private String logradouro;
    @NotBlank(message = "Campo obrigatório")
    private String complemento;
    @NotBlank(message = "Campo obrigatório")
    private String bairro;
    @NotBlank(message = "Campo obrigatório")
    private String localidade;
    @NotBlank(message = "Campo obrigatório")
    @Size(max = 2)
    private String uf;

    public EnderecoDto(Endereco entity) {
        id = entity.getId();
        tipoEndereco = entity.getTipoEndereco();
        cep = entity.getCep();
        logradouro = entity.getLogradouro();
        complemento = entity.getComplemento();
        bairro = entity.getBairro();
        localidade = entity.getLocalidade();
        uf = entity.getUf();
    }

    public static Page<EnderecoDto> converter(Page<Endereco> page) {
        return page.map(EnderecoDto::new);
    }
}
