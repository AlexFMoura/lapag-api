package com.labella.lapag.api.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class AlterarSenhaDTO {
    private String senhaAtual;
    private String novaSenha;

}
