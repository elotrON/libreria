package com.example.libreria.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.libreria.entity.Libro;

public interface LibroRepository extends JpaRepository<Libro, Long> {
}
