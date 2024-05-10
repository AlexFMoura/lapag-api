package com.labella.lapag.api.model;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
public class ParcelamentoDTO {

    private Long id;
    private String nomeCliente;
    private Integer vendaId;
    private String descricao;
    private BigDecimal valorTotal;
    private Integer qtdParcelas;
    private LocalDate primeiroVencimento;
}
