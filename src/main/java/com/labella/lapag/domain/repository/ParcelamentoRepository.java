package com.labella.lapag.domain.repository;

import com.labella.lapag.domain.model.Parcelamento;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ParcelamentoRepository extends JpaRepository<Parcelamento, Long> {

    List<Parcelamento> findByCliente_Id(Long clienteId);

    Optional<Parcelamento> findByVendaId(Integer vendaId);

}
