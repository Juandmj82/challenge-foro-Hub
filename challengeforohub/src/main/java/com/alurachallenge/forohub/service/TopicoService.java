package com.alurachallenge.forohub.service;

import com.alurachallenge.forohub.dto.TopicoRequestDTO;
import com.alurachallenge.forohub.model.Curso;
import com.alurachallenge.forohub.model.Topico;
import com.alurachallenge.forohub.model.Usuario;
import com.alurachallenge.forohub.repository.CursoRepository;
import com.alurachallenge.forohub.repository.TopicoRepository;
import com.alurachallenge.forohub.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class TopicoService {

    @Autowired
    private TopicoRepository topicoRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private CursoRepository cursoRepository;

    @Transactional
    public Topico crearTopico(TopicoRequestDTO requestDTO) {
        // Validación de campos
        validarCamposObligatorios(requestDTO);

        // Validación de duplicados
        validarTopicoDuplicado(requestDTO);

        // Buscar usuario y curso
        Usuario autor = usuarioRepository.findById(requestDTO.autorId())
            .orElseThrow(() -> new IllegalArgumentException("Usuario no encontrado: " + requestDTO.autorId()));

        Curso curso = cursoRepository.findByNombre(requestDTO.curso())
            .orElseThrow(() -> new IllegalArgumentException("Curso no encontrado: " + requestDTO.curso()));

        // Crear nuevo tópico
        Topico nuevoTopico = new Topico();
        nuevoTopico.setTitulo(requestDTO.titulo());
        nuevoTopico.setMensaje(requestDTO.mensaje());
        nuevoTopico.setFechaCreacion(LocalDateTime.now());
        nuevoTopico.setStatus(Topico.Status.NO_RESPONDIDO);
        nuevoTopico.setAutor(autor);
        nuevoTopico.setCurso(curso);

        return topicoRepository.save(nuevoTopico);
    }

    private void validarCamposObligatorios(TopicoRequestDTO requestDTO) {
        if (requestDTO.titulo() == null || requestDTO.titulo().trim().isEmpty()) {
            throw new IllegalArgumentException("El título es obligatorio");
        }
        if (requestDTO.mensaje() == null || requestDTO.mensaje().trim().isEmpty()) {
            throw new IllegalArgumentException("El mensaje es obligatorio");
        }
        if (requestDTO.autorId() == null) {
            throw new IllegalArgumentException("El ID del autor es obligatorio");
        }
        if (requestDTO.curso() == null || requestDTO.curso().trim().isEmpty()) {
            throw new IllegalArgumentException("El curso es obligatorio");
        }
    }

    private void validarTopicoDuplicado(TopicoRequestDTO requestDTO) {
        Optional<Topico> topicoExistente = topicoRepository.findByTituloAndMensaje(
            requestDTO.titulo(), 
            requestDTO.mensaje()
        );

        if (topicoExistente.isPresent()) {
            throw new IllegalArgumentException("Ya existe un tópico con este título y mensaje");
        }
    }
}