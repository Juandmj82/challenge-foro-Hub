package com.alurachallenge.forohub.dto;

import com.alurachallenge.forohub.model.Topico;
import java.time.LocalDateTime;

public record TopicoResponseDTO(
    Long id,
    String titulo, 
    String mensaje, 
    LocalDateTime fechaCreacion, 
    Topico.Status status,
    String autorNombre,
    String cursoNombre
) {
    public TopicoResponseDTO(Topico topico) {
        this(
            topico.getId(),
            topico.getTitulo(), 
            topico.getMensaje(), 
            topico.getFechaCreacion(), 
            topico.getStatus(),
            topico.getAutor().getNombre(),
            topico.getCurso().getNombre()
        );
    }
}