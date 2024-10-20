package com.labella.lapag.domain.repository;

import com.labella.lapag.domain.model.Parcelas;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;

public interface ParcelasRepository extends JpaRepository<Parcelas, Long> {

    List<Parcelas> findByParcelamento_id(Long parcelamento_id);

    @Query("SELECT p FROM Parcelas p WHERE p.dataVencimento < :currentDate AND p.dataPagamento IS NULL")
    List<Parcelas> findVencidas(LocalDate currentDate);

    @Query("SELECT p FROM Parcelas p WHERE p.dataVencimento BETWEEN :currentDate AND :futureDate")
    List<Parcelas> findVencemEm30Dias(LocalDate currentDate, LocalDate futureDate);
}
