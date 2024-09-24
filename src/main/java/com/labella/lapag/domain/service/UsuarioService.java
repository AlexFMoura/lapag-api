package com.labella.lapag.domain.service;

import com.labella.lapag.domain.exception.NegocioException;
import com.labella.lapag.domain.model.Usuario;
import com.labella.lapag.domain.repository.UsuarioRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;

    public List<Usuario> listar() {
        return usuarioRepository.findAll();
    }

    public Usuario buscar(Integer id) {
        return usuarioRepository.findById(id).orElseThrow(() -> new NegocioException("Usuário não encontrado"));
    }
}
