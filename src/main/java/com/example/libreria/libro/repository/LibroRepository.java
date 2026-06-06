package com.example.libreria.libro.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.libreria.libro.entity.Libro;

public interface LibroRepository extends JpaRepository<Libro, Long> {
}
