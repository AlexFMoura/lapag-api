package com.labella.lapag.domain.repository;

import com.labella.lapag.domain.model.Usuarios;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UsuarioRepository extends JpaRepository<Usuarios, Long> {

    List<Usuarios> findAll();
}
