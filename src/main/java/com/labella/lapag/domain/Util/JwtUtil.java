package com.labella.lapag.domain.Util;

import io.jsonwebtoken.*;

import java.util.Date;

public class JwtUtil {
    // Chave secreta para assinar o JWT
    private String secretKey = "minha-chave-secreta";

    // Método para gerar o JWT
    public String gerarToken(String usuarioEmail) {
        Claims claims = (Claims) Jwts.claims().setSubject(usuarioEmail);

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(new Date())  // Data de criação
                .setExpiration(new Date(System.currentTimeMillis() + 86400000))  // Expiração de 1 dia
                .signWith(SignatureAlgorithm.HS256, secretKey)  // Algoritmo e chave secreta
                .compact();
    }

    public Claims obterClaims(String token) {
        JwtParser parser = Jwts.parser() // Usando o novo método para construir o parser
                .setSigningKey(secretKey)
                .build();
        return parser.parseClaimsJws(token).getBody();
    }

    public String obterEmail(String token) {
        return obterClaims(token).getSubject();
    }

    public boolean validarToken(String token) {
        try {
            Claims claims = obterClaims(token);
            return !claims.getExpiration().before(new Date());
        } catch (Exception e) {
            return false;
        }
    }
}
