package com.labella.lapag.domain.repository;

import com.labella.lapag.domain.model.Rota;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RotaRepository extends JpaRepository<Rota, Long> {
    Rota findByNome(String nome);
}
