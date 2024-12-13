package com.alurachallenge.forohub.infra.security;

import com.alurachallenge.forohub.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class AutenticacionService implements UserDetailsService {

    private static final Logger logger = LoggerFactory.getLogger(AutenticacionService.class);

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        logger.info("Intentando cargar usuario con login: {}", username);
        return usuarioRepository.findByLogin(username)
            .orElseThrow(() -> {
                logger.error("Usuario no encontrado: {}", username);
                return new UsernameNotFoundException("Usuario no encontrado");
            });
    }
}