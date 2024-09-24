package com.labella.lapag.api.model;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
public class CriarParcelamentoDTO {

    private Integer codigoVenda;
    private Long clienteId;
    private BigDecimal valorVenda;
    private Integer qtdParcelas;
    private LocalDate primeiroVencimento;
    private Integer usuarioId;
}
