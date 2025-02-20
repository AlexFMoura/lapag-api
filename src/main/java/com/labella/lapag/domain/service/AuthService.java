package com.labella.lapag.domain.service;

import com.labella.lapag.domain.Util.JwtUtil;
import com.labella.lapag.domain.exception.NegocioException;
import com.labella.lapag.domain.model.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    private final UsuarioService usuarioService;
    private final JwtUtil jwtUtil;

    public AuthService(UsuarioService usuarioService, JwtUtil jwtUtil) {
        this.usuarioService = usuarioService;
        this.jwtUtil = jwtUtil;
    }

    public String login(String email, String senha) {
        // Verifica se o usuário existe
        Usuario usuario = usuarioService.buscaPorEmail(email)
                .orElseThrow(() -> new NegocioException("Usuário não encontrado"));

        // Valida a senha (compara a senha fornecida com a senha armazenada no banco, normalmente criptografada)
        if (!passwordEncoder.matches(senha, usuario.getSenha())) {
            throw new NegocioException("Credenciais inválidas");
        }

        // Gera o token JWT
        return jwtUtil.gerarToken(email);
    }
}
