package com.example.libreria.usuario.repository;

import com.example.libreria.usuario.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;


public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    
}
