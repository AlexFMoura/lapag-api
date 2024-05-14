package com.labella.lapag.domain.model;

import com.labella.lapag.domain.validation.ValidationGroups;
import jakarta.persistence.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.groups.ConvertGroup;
import jakarta.validation.groups.Default;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Parcelamento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long id;

    @Valid
    @ConvertGroup(from = Default.class, to = ValidationGroups.ClienteId.class)
    @ManyToOne
//    @JoinColumn(name = "cliente_id")
    private Cliente cliente;

    @NotNull
    @Column(name = "venda_id")
    private Integer vendaId;
    private String descricao;

    @NotNull
    @Positive
    @Column(name = "valor_total")
    private BigDecimal valorTotal;

    @NotNull
    @Positive
    @Column(name = "qtd_parcelas")
    private Integer qtdParcelas;

    @NotNull
    @Column(name = "primeiro_vencimento")
    private LocalDate primeiroVencimento;

    @OneToMany(mappedBy = "parcelamento", fetch = FetchType.LAZY)
    @Cascade(CascadeType.ALL)
    private List<Parcelas> parcelas = new ArrayList<Parcelas>();
}
