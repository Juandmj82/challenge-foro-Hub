package com.alurachallenge.forohub.controller;

import com.alurachallenge.forohub.dto.LoginDTO;
import com.alurachallenge.forohub.infra.security.TokenService;
import com.alurachallenge.forohub.model.Usuario;
import com.alurachallenge.forohub.repository.UsuarioRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Optional;

@RestController
@RequestMapping("/login")
public class AutenticacionController {

    private static final Logger logger = LoggerFactory.getLogger(AutenticacionController.class);

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private TokenService tokenService;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping
    public ResponseEntity<?> login(@RequestBody @Valid LoginDTO loginDTO) {
        logger.info("Intento de login con: {}", loginDTO.login());
        logger.info("Contraseña recibida: {}", loginDTO.contrasena());
        
        try {
            Optional<Usuario> usuarioOptional = usuarioRepository.findByLogin(loginDTO.login());
            if (usuarioOptional.isPresent()) {
                Usuario usuario = usuarioOptional.get();
                logger.info("Usuario encontrado: {}", usuario.getNombre());
                logger.info("Login: {}", usuario.getLogin());
                logger.info("Email: {}", usuario.getEmail());
                logger.info("Contraseña almacenada: {}", usuario.getContrasena());
                
                // Verificación manual de contraseña
                logger.info("Iniciando verificación de contraseña...");
                boolean passwordMatch = passwordEncoder.matches(loginDTO.contrasena(), usuario.getContrasena());
                logger.info("¿Contraseña coincide?: {}", passwordMatch);
                
                if (!passwordMatch) {
                    logger.error("Contraseña no coincide para el usuario: {}", loginDTO.login());
                    return ResponseEntity.status(403).body("Contraseña incorrecta");
                }
            } else {
                logger.error("No se encontró usuario con login: {}", loginDTO.login());
                return ResponseEntity.status(403).body("Usuario no encontrado");
            }

            var authToken = new UsernamePasswordAuthenticationToken(loginDTO.login(), loginDTO.contrasena());
            Authentication authentication = authenticationManager.authenticate(authToken);
            
            logger.info("Autenticación exitosa: {}", authentication.isAuthenticated());
            
            var tokenJWT = tokenService.generarToken((Usuario) authentication.getPrincipal());

            return ResponseEntity.ok(new DatosJWTToken(tokenJWT));
        } catch (Exception e) {
            logger.error("Error en el proceso de autenticación: ", e);
            return ResponseEntity.status(403).body("Error de autenticación: " + e.getMessage());
        }
    }

    private record DatosJWTToken(String token) {
    }
}