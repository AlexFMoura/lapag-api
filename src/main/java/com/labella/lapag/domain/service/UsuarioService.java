package com.labella.lapag.domain.service;

import com.labella.lapag.domain.exception.NegocioException;
import com.labella.lapag.domain.model.Usuario;
import com.labella.lapag.domain.repository.UsuarioRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Service
public class UsuarioService {

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    private UsuarioRepository usuarioRepository;

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

        usuario.setSenha(bCryptPasswordEncoder.encode(usuario.getSenha()));
        return usuarioRepository.save(usuario);
    }

    @Transactional
    public void salvarAdmin(Usuario usuario) {
        entityManager.merge(usuario);
    }

    public Optional<Usuario> buscaPorEmail(String email) {
        return usuarioRepository.findByEmail(email);
    }

    public Optional<Usuario> buscaPorNome(String nome) {
        return usuarioRepository.findByNome(nome);
    }

    public void alterarSenha(Integer usuarioId, String senhaAtual, String novaSenha) {
        Usuario usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        // Verifica se a senha atual fornecida é válida
        if (!bCryptPasswordEncoder.matches(senhaAtual, usuario.getSenha())) {
            throw new RuntimeException("Senha atual inválida");
        }

        // Criptografa a nova senha
        String senhaCriptografada = bCryptPasswordEncoder.encode(novaSenha);

        // Atualiza a senha do usuário no banco de dados
        usuario.setSenha(senhaCriptografada);
        usuarioRepository.save(usuario);
    }
}
