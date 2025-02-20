package com.labella.lapag.domain.securite;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.Claims;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;

import java.io.IOException;

@WebFilter("/dashboard/*") // Define as rotas que o filtro protegerá
public class JwtAuthenticationFilter implements Filter {

    private static final String SECRET_KEY = "sua-chave-secreta";

    @Override
    public void doFilter(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        String token = request.getHeader("Authorization");

        if (token != null && token.startsWith("Bearer ")) {
            token = token.substring(7); // Remove o "Bearer " do início

            try {
                // Valida o token
                Claims claims = Jwts.parser()
                        .setSigningKey(SECRET_KEY)
                        .parseClaimsJws(token)
                        .getBody();

                // A partir dos claims, você pode extrair o email, por exemplo
                String email = claims.getSubject();
                request.setAttribute("email", email); // Armazena o email no request, para ser utilizado nas rotas

            } catch (Exception e) {
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                response.getWriter().write("Token inválido ou expirado");
                return;
            }
        } else {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().write("Token não encontrado");
            return;
        }

        chain.doFilter(request, response); // Continuar a requisição
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {}

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        String token = servletRequest.getHeader("Authorization");

        if (token != null && token.startsWith("Bearer ")) {
            token = token.substring(7); // Remove o "Bearer " do início

            try {
                // Valida o token
                Claims claims = Jwts.parser()
                        .setSigningKey(SECRET_KEY)
                        .parseClaimsJws(token)
                        .getBody();

                // A partir dos claims, você pode extrair o email, por exemplo
                String email = claims.getSubject();
                request.setAttribute("email", email); // Armazena o email no request, para ser utilizado nas rotas

            } catch (Exception e) {
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                response.getWriter().write("Token inválido ou expirado");
                return;
            }
        } else {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().write("Token não encontrado");
            return;
        }

        chain.doFilter(request, response); // Continuar a requisição
    }

    @Override
    public void destroy() {}
}
