package com.alurachallenge.forohub.dto;

import jakarta.validation.constraints.NotBlank;

public record LoginDTO(
    @NotBlank(message = "El login no puede estar vacío")
    String login, 
    
    @NotBlank(message = "La contraseña no puede estar vacía")
    String contrasena
) {
}