package com.alurachallenge.forohub.controller;

import com.alurachallenge.forohub.dto.UsuarioRequestDTO;
import com.alurachallenge.forohub.dto.UsuarioResponseDTO;
import com.alurachallenge.forohub.model.Usuario;
import com.alurachallenge.forohub.repository.TopicoRepository;
import com.alurachallenge.forohub.repository.UsuarioRepository;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private TopicoRepository topicoRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    private static final Logger logger = LoggerFactory.getLogger(UsuarioController.class);

    @GetMapping
    public ResponseEntity<Page<UsuarioResponseDTO>> listarUsuarios(
        @PageableDefault(size = 10) Pageable paginacion
    ) {
        Page<UsuarioResponseDTO> usuarios = usuarioRepository.findAll(paginacion)
            .map(UsuarioResponseDTO::new);
        return ResponseEntity.ok(usuarios);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UsuarioResponseDTO> obtenerUsuario(@PathVariable Long id) {
        return usuarioRepository.findById(id)
            .map(usuario -> ResponseEntity.ok(new UsuarioResponseDTO(usuario)))
            .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<UsuarioResponseDTO> crearUsuario(
        @RequestBody @Valid UsuarioRequestDTO datosUsuario,
        UriComponentsBuilder uriBuilder
    ) {
        Usuario nuevoUsuario = new Usuario();
        nuevoUsuario.setLogin(datosUsuario.login());
        nuevoUsuario.setNombre(datosUsuario.nombre());
        nuevoUsuario.setEmail(datosUsuario.email());
        nuevoUsuario.setContrasena(passwordEncoder.encode(datosUsuario.contrasena()));

        Usuario usuarioGuardado = usuarioRepository.save(nuevoUsuario);
        
        URI uri = uriBuilder.path("/usuarios/{id}")
            .buildAndExpand(usuarioGuardado.getId()).toUri();
        
        return ResponseEntity.created(uri)
            .body(new UsuarioResponseDTO(usuarioGuardado));
    }

    @PutMapping("/{id}")
    public ResponseEntity<UsuarioResponseDTO> actualizarUsuario(
        @PathVariable Long id,
        @RequestBody @Valid UsuarioRequestDTO datosUsuario
    ) {
        return usuarioRepository.findById(id)
            .map(usuario -> {
                usuario.setLogin(datosUsuario.login());
                usuario.setNombre(datosUsuario.nombre());
                usuario.setEmail(datosUsuario.email());
                usuario.setContrasena(passwordEncoder.encode(datosUsuario.contrasena()));

                Usuario usuarioActualizado = usuarioRepository.save(usuario);
                return ResponseEntity.ok(new UsuarioResponseDTO(usuarioActualizado));
            })
            .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarUsuario(@PathVariable Long id) {
        if (usuarioRepository.existsById(id)) {
            usuarioRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/eliminar-por-id/{id}")
    public ResponseEntity<Void> eliminarUsuarioPorId(@PathVariable Long id) {
        if (!usuarioRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }

        try {
            // Verificar si hay tópicos asociados
            long topicosAsociados = topicoRepository.countByAutor_Id(id);
            logger.info("Número de tópicos asociados al usuario {}: {}", id, topicosAsociados);

            // Primero intentar eliminar los tópicos asociados
            topicoRepository.deleteByAutor_Id(id);
            
            // Luego eliminar el usuario
            usuarioRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            // Loguear el error completo para diagnóstico
            logger.error("Error al eliminar usuario " + id, e);
            return ResponseEntity.badRequest().body(null);
        }
    }
}