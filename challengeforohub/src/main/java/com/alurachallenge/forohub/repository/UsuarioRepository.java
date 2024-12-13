package com.alurachallenge.forohub.repository;

import com.alurachallenge.forohub.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    Optional<Usuario> findByNombre(String nombre);
    Optional<Usuario> findByLogin(String login);
}