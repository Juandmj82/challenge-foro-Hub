package com.alurachallenge.forohub.controller;

import com.alurachallenge.forohub.dto.TopicoResponseDTO;
import com.alurachallenge.forohub.dto.TopicoUpdateDTO;
import com.alurachallenge.forohub.model.Topico;
import com.alurachallenge.forohub.repository.TopicoRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.domain.Sort;

@RestController
@RequestMapping("/topicos")
public class TopicoController {

    @Autowired
    private TopicoRepository topicoRepository;

    @GetMapping
    public ResponseEntity<Page<TopicoResponseDTO>> listarTopicos(
        @PageableDefault(size = 10, sort = "fechaCreacion", direction = Sort.Direction.ASC) Pageable paginacion,
        @RequestParam(required = false) String curso,
        @RequestParam(required = false) Integer año
    ) {
        Page<TopicoResponseDTO> topicos;

        if (curso != null && año != null) {
            topicos = topicoRepository.findByCursoAndYear(curso, año, paginacion)
                .map(TopicoResponseDTO::new);
        } else {
            topicos = topicoRepository.findAll(paginacion)
                .map(TopicoResponseDTO::new);
        }

        return ResponseEntity.ok(topicos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TopicoResponseDTO> obtenerTopicoPorId(@PathVariable Long id) {
        return topicoRepository.findById(id)
            .map(topico -> ResponseEntity.ok(new TopicoResponseDTO(topico)))
            .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<TopicoResponseDTO> actualizarTopico(
        @PathVariable Long id, 
        @RequestBody @Valid TopicoUpdateDTO datosTopico
    ) {
        return topicoRepository.findById(id)
            .map(topico -> {
                topico.setTitulo(datosTopico.titulo());
                topico.setMensaje(datosTopico.mensaje());
                Topico topicoActualizado = topicoRepository.save(topico);
                return ResponseEntity.ok(new TopicoResponseDTO(topicoActualizado));
            })
            .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarTopico(@PathVariable Long id) {
        if (topicoRepository.existsById(id)) {
            topicoRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}