package com.tqi.banktqi.dto;

import com.tqi.banktqi.model.Cliente;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

/**
 * A classe <b>ClienteInsertDto</b> DTO sub-classe de ClienteDto responsável por encapsular a senha do cliente
 * @author Franco Canizo Brasil Sobrinho
 * @since jan 2022
 * @version 1.0
 */

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ClienteInsertDto extends ClienteDto {
    private static final long serialVersionUID = 1L;

    @NotBlank(message = "Campo obrigatório!")
    private String senha;

    public ClienteInsertDto(Cliente entity) {
        super(entity);
        senha = entity.getPassword();
    }

    /*
    public String getPassword() {
        return senha;
    }
     */
}
