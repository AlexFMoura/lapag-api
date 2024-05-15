package com.labella.lapag.domain.repository;

import com.labella.lapag.domain.model.Parcelas;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ParcelasRepository extends JpaRepository<Parcelas, Long> {

    @Query(value = "select p from Parcelas p" +
            " join p.parcelamento pa" +
            " join pa.cliente c" +
            " where c.id =?1")
    Optional<Parcelas> findByParcelaClienteId(Integer clienteId);
}
