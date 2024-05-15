package com.labella.lapag.api.model;

import jakarta.persistence.Column;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
public class ParcelasDTO {

    private Long id;
    private Integer parcela;
    private BigDecimal valorParcela;
    private LocalDate dataVencimento;
    private LocalDate dataPagamento;
}
