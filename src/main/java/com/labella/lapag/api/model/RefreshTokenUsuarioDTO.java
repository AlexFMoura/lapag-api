package com.labella.lapag.api.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RefreshTokenUsuarioDTO {

    private String token;
    private UsuarioDTO usuario;

    public RefreshTokenUsuarioDTO(String newAccessToken) {
    }
}
