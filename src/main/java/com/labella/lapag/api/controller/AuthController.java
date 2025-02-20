package com.labella.lapag.api.controller;

import com.labella.lapag.api.model.LoginDTO;
import com.labella.lapag.domain.exception.NegocioException;
import com.labella.lapag.domain.model.Usuario;
import com.labella.lapag.domain.service.AuthService;
import com.labella.lapag.domain.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;
    @Autowired
    private AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginDTO loginDTO) {
        // Verificar se o e-mail existe

        String token = authService.login(loginDTO.getEmail(), loginDTO.getSenha());
        return ResponseEntity.ok(new LoginResponse(token));

//        Usuario usuario = usuarioService.buscaPorEmail(loginDTO.getEmail()).orElseThrow(
//                () -> new NegocioException("Usuário não encontrato")
//        );
//
//        if (usuario == null) {
//            return ResponseEntity.badRequest().body("Usuário não encontrado");
//        }
//
//        // Verificar se a senha está correta
//        if (!passwordEncoder.matches(loginDTO.getSenha(), usuario.getSenha())) {
//            return ResponseEntity.badRequest().body("Senha incorreta");
//        }
//
//        // Aqui você pode gerar um JWT ou outro tipo de token
//        String token = "fake-jwt-token"; // Coloque aqui a lógica de geração de token
//
//        return ResponseEntity.ok("Login bem-sucedido, token: " + token);
    }

    public class LoginResponse {
        private String token;

        public LoginResponse(String token) {
            this.token = token;
        }

        public String getToken() {
            return token;
        }
    }
}

