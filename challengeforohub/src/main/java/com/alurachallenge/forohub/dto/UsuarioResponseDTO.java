package com.alurachallenge.forohub.dto;

import com.alurachallenge.forohub.model.Usuario;

public record UsuarioResponseDTO(
    Long id,
    String login,
    String nombre,
    String email
) {
    public UsuarioResponseDTO(Usuario usuario) {
        this(
            usuario.getId(),
            usuario.getLogin(),
            usuario.getNombre(),
            usuario.getEmail()
        );
    }
}