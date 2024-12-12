package com.alurachallenge.forohub.repository;

import com.alurachallenge.forohub.model.Topico;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface TopicoRepository extends JpaRepository<Topico, Long> {
    // Método para buscar por curso y año
    @Query("SELECT t FROM Topico t WHERE t.curso.nombre = :cursoNombre AND YEAR(t.fechaCreacion) = :año")
    Page<Topico> findByCursoAndYear(
        @Param("cursoNombre") String cursoNombre, 
        @Param("año") int año, 
        Pageable pageable
    );

    // Método para buscar tópico por título y mensaje
    Optional<Topico> findByTituloAndMensaje(String titulo, String mensaje);
}