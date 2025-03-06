package com.labella.lapag.api.controller;

import com.labella.lapag.api.mapper.UsuarioMapper;
import com.labella.lapag.api.model.LoginDTO;
import com.labella.lapag.api.model.LoginResponseDTO;
import com.labella.lapag.api.model.UsuarioDTO;
import com.labella.lapag.domain.model.Rota;
import com.labella.lapag.domain.model.Usuario;
import com.labella.lapag.domain.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    private UsuarioMapper usuarioMapper;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginDTO loginDTO) {
       Usuario usuario = usuarioService.buscaPorEmail(loginDTO.getEmail()).orElseThrow(null);

       if (usuario == null || !usuario.isLoginCorrect(loginDTO, bCryptPasswordEncoder)) {
           throw new BadCredentialsException("usuário ou senha inválida!");
       }

       var now = Instant.now();
       var expirar = 300L;

       var scopes = usuario.getRotas()
               .stream()
               .map(Rota::getNome)
               .collect(Collectors.joining(" "));

       var claims = JwtClaimsSet.builder()
               .issuer("mybackend")
               .subject(usuario.getId().toString())
               .issuedAt(now)
               .expiresAt(now.plusSeconds(expirar))
               .claim("scope", scopes)
               .build();

       var jwtValue = jwtEncoder.encode(JwtEncoderParameters.from(claims)).getTokenValue();

        UsuarioDTO usuarioDTO = usuarioMapper.toModel(usuario);
        var response = new LoginResponseDTO(jwtValue, usuarioDTO);

       return ResponseEntity.ok(response);

    }
}
