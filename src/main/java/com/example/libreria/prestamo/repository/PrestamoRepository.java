package com.example.libreria.prestamo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.libreria.prestamo.entity.Prestamo;

public interface PrestamoRepository extends JpaRepository<Prestamo, Long> {

}
