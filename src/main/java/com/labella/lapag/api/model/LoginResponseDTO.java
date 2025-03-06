package com.labella.lapag.api.model;

import com.labella.lapag.domain.model.Usuario;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class LoginResponseDTO {

    private String token;
    private UsuarioDTO usuario;

}
