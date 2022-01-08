package com.tqi.banktqi.model;

import java.io.Serializable;

import javax.persistence.*;

import com.tqi.banktqi.dto.EnderecoDto;
import com.tqi.banktqi.model.enums.TipoEndereco;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * A classe <b>Endereco</b> Modelo para criação do objeto endereço que está atrelado a um objeto Cliente
 * @author Franco Canizo Brasil Sobrinho
 * @since jan 2022
 * @version 1.0
 */

@Entity
@Table(name = "Enderecos")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Endereco implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private TipoEndereco tipoEndereco;
    @Column(nullable = false)
    private String cep;
    @Column(nullable = false)
    private String logradouro;
    @Column(nullable = false)
    private String complemento;
    @Column(nullable = false)
    private String bairro;
    @Column(nullable = false)
    private String localidade;
    @Column(nullable = false)
    private String uf;

    public Endereco(EnderecoDto dto) {
        id = dto.getId();
        tipoEndereco = dto.getTipoEndereco();
        cep = dto.getCep();
        logradouro = dto.getLogradouro();
        complemento = dto.getComplemento();
        bairro = dto.getBairro();
        localidade = dto.getLocalidade();
        uf = dto.getUf();
    }
}
