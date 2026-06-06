package com.example.libreria.usuario.repository;

import com.example.libreria.prestamo.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;


public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
}
