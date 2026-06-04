package com.example.libreria.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.libreria.entity.Prestamo;

public interface PrestamoRepository extends JpaRepository<Prestamo, Long> {

}
