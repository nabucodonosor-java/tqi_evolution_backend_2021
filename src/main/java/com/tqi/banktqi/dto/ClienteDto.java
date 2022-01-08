package com.tqi.banktqi.dto;

import com.tqi.banktqi.model.Cliente;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.domain.Page;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * A classe <b>ClienteDto</b> DTO da classe Cliente
 * @author Franco Canizo Brasil Sobrinho
 * @since jan 2022
 * @version 1.0
 */

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ClienteDto implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long id;

    @NotBlank(message = "Campo obrigatório")
    private String nome;
    @Email(message = "Digitar email válido!")
    @NotBlank(message = "Campo obrigatório")
    private String email;
    @NotBlank(message = "Campo obrigatório")
    private String rg;
    @NotBlank(message = "Campo obrigatório")
    // @CPF -> caso fosse um sistema real utilizaria esta anotação para validar CPF do cliente
    private String cpf;

    @Positive
    private BigDecimal renda;

    private List<EnderecoDto> enderecos = new ArrayList<>();

    private Set<RoleDto> roles = new HashSet<>();

    public ClienteDto(Cliente entity) {
        id = entity.getId();
        nome = entity.getNome();
        email = entity.getEmail();
        rg = entity.getRg();
        cpf = entity.getCpf();
        renda = entity.getRenda();
        entity.getEnderecos().forEach(end -> this.enderecos.add(new EnderecoDto(end)));
        entity.getRoles().forEach(r -> this.roles.add(new RoleDto(r)));
    }

    public static Page<ClienteDto> converter(Page<Cliente> page) {
        return page.map(ClienteDto::new);
    }
}
