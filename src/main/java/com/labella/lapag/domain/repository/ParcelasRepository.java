package com.labella.lapag.domain.repository;

import com.labella.lapag.domain.model.Parcelas;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ParcelasRepository extends JpaRepository<Parcelas, Long> {

    List<Parcelas> findByParcelamento_id(Long parcelamento_id);
}
