package com.labella.lapag.domain.repository;

import com.labella.lapag.domain.model.Parcelamento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ParcelamentoRepository extends JpaRepository<Parcelamento, Long> {

    List<Parcelamento> findByCliente_Id(Long clienteId);
}
