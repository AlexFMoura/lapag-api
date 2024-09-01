package com.labella.lapag.api.model;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
public class ParcelamentoDTO {

    private Long id;
    private String nomeCliente;
    private Integer vendaId;
    private String status;
    private BigDecimal valorTotal;
    private Integer qtdParcelas;
    private LocalDate primeiroVencimento;
    private List<ParcelasDTO> parcelas;
    private Integer usuarioId;
}
