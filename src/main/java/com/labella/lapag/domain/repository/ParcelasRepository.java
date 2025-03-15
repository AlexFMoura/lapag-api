package com.labella.lapag.domain.repository;

import com.labella.lapag.api.model.ParcelaVencidaDTO;
import com.labella.lapag.domain.model.Parcelas;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface ParcelasRepository extends JpaRepository<Parcelas, Long> {

    List<Parcelas> findByParcelamento_id(Long parcelamento_id);

    @Query("SELECT p FROM Parcelas p WHERE p.dataVencimento < :currentDate AND p.dataPagamento IS NULL")
    List<Parcelas> findVencidas(LocalDate currentDate);

    @Query("SELECT p FROM Parcelas p WHERE p.dataVencimento BETWEEN :currentDate AND :futureDate")
    List<Parcelas> findVencemEm30Dias(LocalDate currentDate, LocalDate futureDate);

    @Query("SELECT p FROM Parcelas p WHERE p.dataVencimento < :dataVencimento AND p.dataPagamento IS NULL")
    Optional<Parcelas> findParcelaVencidaNaoPaga(@Param("dataVencimento") LocalDate dataVencimento);

    @Query("""
        SELECT new com.labella.lapag.api.model.ParcelaVencidaDTO(
            p.parcelamento.vendaId,
            c.nome,
            c.telefone,
            p.parcela,
            p.valorParcela,
            p.dataVencimento
        )
        FROM Parcelas p
        JOIN p.parcelamento pr
        JOIN pr.cliente c
        WHERE p.dataVencimento < CURRENT_DATE
          AND p.dataPagamento IS NULL
    """)
    List<ParcelaVencidaDTO> findParcelasVencidas();

    @Query("""
        SELECT new com.labella.lapag.api.model.ParcelaVencidaDTO(
            p.parcelamento.vendaId,
            c.nome,
            c.telefone,
            p.parcela,
            p.valorParcela,
            p.dataVencimento
        )
        FROM Parcelas p
        JOIN p.parcelamento pr
        JOIN pr.cliente c
        WHERE p.dataVencimento BETWEEN :currentDate AND :futureDate
          AND p.dataPagamento IS NULL
    """)
    List<ParcelaVencidaDTO> findParcelasVencendoEm30Dias(
            @Param("currentDate") LocalDate currentDate,
            @Param("futureDate") LocalDate futureDate
    );
}
