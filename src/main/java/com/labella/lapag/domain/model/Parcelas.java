package com.labella.lapag.domain.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Parcelas {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long id;

    private Integer parcela;

    @Column(name = "valor_parcela")
    private BigDecimal valorParcela;

    @Column(name = "data_vencimento")
    private LocalDate dataVencimento;

    @Column(name = "data_pagamento")
    private LocalDate dataPagamento;

    @Column(name = "valor_juros")
    private BigDecimal valorJuros;

    @Column(name = "valor_multa")
    private BigDecimal valorMulta;

    private Double juros;

    private Double multa;

    @Column(name = "forma_pagto")
    private String formaPagto;

    @JsonIgnore
    @JoinColumn(name = "parcelamento_id")
    @ManyToOne(fetch = FetchType.EAGER)
    private Parcelamento parcelamento;

    @Transient
    private BigDecimal totalPagar;

}
