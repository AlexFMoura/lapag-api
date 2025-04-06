package com.labella.lapag.domain.repository;

import com.labella.lapag.domain.model.Taxa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TaxaRepository extends JpaRepository<Taxa, Integer> {

    Optional<Taxa> findByDataInativoIsNull();
}
