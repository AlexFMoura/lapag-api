package com.labella.lapag.api.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
public class ParcelaVencidaDTO {
    private Integer vendaId;
    private String nome;
    private String telefone;
    private Integer parcela;
    private BigDecimal valorParcela;
    private LocalDate dataVencimento;
}
