package com.alurachallenge.forohub.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record TopicoRequestDTO(
    @NotBlank(message = "El título es obligatorio")
    @Size(min = 5, max = 255, message = "El título debe tener entre 5 y 255 caracteres")
    String titulo,

    @NotBlank(message = "El mensaje es obligatorio")
    @Size(min = 10, max = 1000, message = "El mensaje debe tener entre 10 y 1000 caracteres")
    String mensaje,

    @NotNull(message = "El ID del autor es obligatorio")
    Long autorId,

    @NotBlank(message = "El curso es obligatorio")
    String curso
) {}