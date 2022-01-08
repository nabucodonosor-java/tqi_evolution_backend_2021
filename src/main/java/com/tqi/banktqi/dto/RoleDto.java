package com.tqi.banktqi.dto;


import com.tqi.banktqi.model.Role;
import lombok.*;

import java.io.Serializable;

/**
 * A classe <b>RoleDto</b> DTO padrão da classe Role
 * @author Franco Canizo Brasil Sobrinho
 * @since jan 2022
 * @version 1.0
 */

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RoleDto implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long id;
    private String authority;

    public RoleDto(Role entity) {
        id = entity.getId();
        authority = entity.getAuthority();
    }
}
