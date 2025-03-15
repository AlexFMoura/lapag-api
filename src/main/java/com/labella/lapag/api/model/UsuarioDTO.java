package com.labella.lapag.api.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
public class UsuarioDTO {
    private Long id;
    private String nome;
    private String email;
    private LocalDate data_inativo;
    private String rotaNome;
}
