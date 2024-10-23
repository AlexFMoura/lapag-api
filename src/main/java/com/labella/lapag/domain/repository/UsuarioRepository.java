package com.labella.lapag.domain.repository;

import com.labella.lapag.domain.model.Cliente;
import com.labella.lapag.domain.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {

    Optional<Usuario> findByEmail(String email);
}
