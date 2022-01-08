package com.tqi.banktqi.model.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum TipoEndereco {

    RESIDENCIAL("Residencial"),
    COMERCIAL("Comercial");

    private final String descricao;
}
