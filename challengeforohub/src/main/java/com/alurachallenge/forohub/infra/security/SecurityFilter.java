package com.alurachallenge.forohub.infra.security;

import com.alurachallenge.forohub.repository.UsuarioRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class SecurityFilter extends OncePerRequestFilter {

    @Autowired
    private TokenService tokenService;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) 
            throws ServletException, IOException {
        
        // Obtener el token del header
        String tokenJWT = recuperarToken(request);

        if (tokenJWT != null) {
            try {
                String subject = tokenService.getSubject(tokenJWT);
                System.out.println("DEBUG: Token Subject - " + subject);

                var usuarioOptional = usuarioRepository.findByLogin(subject);

                if (usuarioOptional.isPresent()) {
                    var usuario = usuarioOptional.get();
                    var authentication = new UsernamePasswordAuthenticationToken(
                        usuario, null, usuario.getAuthorities()
                    );

                    System.out.println("DEBUG: Authorities - " + usuario.getAuthorities());
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                } else {
                    System.out.println("DEBUG: Usuario no encontrado para el token");
                }
            } catch (RuntimeException e) {
                System.out.println("DEBUG: Error de autenticación - " + e.getMessage());
            }
        }

        filterChain.doFilter(request, response);
    }

    private String recuperarToken(HttpServletRequest request) {
        var authorizationHeader = request.getHeader("Authorization");
        
        if (authorizationHeader != null) {
            return authorizationHeader.replace("Bearer ", "");
        }

        return null;
    }
}