package com.labella.lapag.api.controller;

import com.labella.lapag.api.mapper.UsuarioMapper;
import com.labella.lapag.api.model.*;
import com.labella.lapag.domain.model.Rota;
import com.labella.lapag.domain.model.Usuario;
import com.labella.lapag.domain.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.jwt.*;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.time.Instant;
import java.util.stream.Collectors;

@RestController
public class LoginController {

    @Autowired
    private UsuarioService usuarioService;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    @Autowired
    private JwtEncoder jwtEncoder;
    @Autowired
    private JwtDecoder jwtDecoder;
    @Autowired
    private UsuarioMapper usuarioMapper;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginDTO loginDTO) {
       Usuario usuario = usuarioService.buscaPorEmail(loginDTO.getEmail()).orElseThrow(() ->
               new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Usuário não encontrado ou senha inválida!"));

       if (!usuario.isLoginCorrect(loginDTO, bCryptPasswordEncoder)) {
           throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "usuário ou senha inválida!");
       }

        // Gerar um novo access token
        String newAccessToken = generateAccessToken(usuario);
        UsuarioDTO usuarioDTO = usuarioMapper.toModel(usuario);
        var response = new LoginResponseDTO(newAccessToken, usuarioDTO);

       return ResponseEntity.ok(response);

    }

    @PostMapping("/refresh")
    public ResponseEntity<?> refreshToken(@RequestBody RefreshTokenDTO refreshTokenDTO) {
        try {
            // Validação do refresh token
            String refreshToken = refreshTokenDTO.getRefreshToken();

            // Decodificando o refresh token para verificar se ele é válido
            Jwt decodeJwt = jwtDecoder.decode(refreshToken);

            // Você pode usar o sub (id do usuário) ou outras informações do token
            String userId = decodeJwt.getSubject();

            // Buscar o usuário com o ID do token
            Usuario usuario = usuarioService.buscaPorId(Integer.valueOf(userId))
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Usuário não encontrado"));

            // Gerar um novo access token
            String newAccessToken = generateAccessToken(usuario);

            // Retornar o novo access token
            return ResponseEntity.ok(new RefreshTokenUsuarioDTO(newAccessToken));

        } catch (JwtException e) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Token de refresh inválido", e);
        }
    }

    private String generateAccessToken(Usuario usuario) {
        var now = Instant.now();
        var expirar = 300L; // Tempo de expiração de 5 minutos

        var claims = JwtClaimsSet.builder()
                .issuer("mybackend")
                .subject(usuario.getId().toString())
                .issuedAt(now)
                .expiresAt(now.plusSeconds(expirar))
                .claim("scope", usuario.getRotas().stream()
                        .map(Rota::getNome)
                        .collect(Collectors.joining(" ")))
                .build();

        return jwtEncoder.encode(JwtEncoderParameters.from(claims)).getTokenValue();
    }
}
