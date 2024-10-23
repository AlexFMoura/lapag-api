package com.labella.lapag.domain.service;

import com.labella.lapag.domain.exception.NegocioException;
import com.labella.lapag.domain.model.Cliente;
import com.labella.lapag.domain.model.Usuario;
import com.labella.lapag.domain.repository.UsuarioRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    @Transactional
    public Usuario salvar(Usuario usuario) {
        boolean emailEmUso = usuarioRepository.findByEmail(usuario.getEmail())
                .filter(c -> !c.equals(usuario))
                .isPresent();

        if (emailEmUso) {
            throw new NegocioException("Já existe um usuário cadastrado com este e-mail");
        }

        return usuarioRepository.save(usuario);
    }
}
