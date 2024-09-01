package com.labella.lapag.domain.service;

import com.labella.lapag.domain.model.Usuarios;
import com.labella.lapag.domain.repository.UsuarioRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;

    public List<Usuarios> listar() {
        return usuarioRepository.findAll();
    }
}
