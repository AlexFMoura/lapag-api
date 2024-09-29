package com.labella.lapag.api.model;

import com.labella.lapag.domain.model.Parcelas;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
public class ParcelamentoPageDTO {

    private Long id;
    private String nomeCliente;
    private Integer codigoVenda;
    private String status;
    private BigDecimal valorVenda;
    private Integer qtdParcela;
    private LocalDate primeiroVencimento;
    private List<Parcelas> parcelas;
}
