package com.example.libreria.prestamo.dto;


import com.example.libreria.prestamo.entity.EstadoPrestamo;
import com.example.libreria.libro.entity.Libro;
import com.example.libreria.usuario.entity.Usuario;

import java.time.LocalDate;

public class PrestamoResponse {

    EstadoPrestamo estado;
    LocalDate fechaPrestamo;
    LocalDate fechaPrevistaDevolucion;
    Libro libro;
    Usuario usuario;

    public EstadoPrestamo isEstado() {
        return estado;
    }

    public void setEstado(EstadoPrestamo estado) {
        this.estado = estado;
    }

    public LocalDate getFechaPrestamo() {
        return fechaPrestamo;
    }

    public EstadoPrestamo getEstado() {
        return estado;
    }

    public void setFechaPrestamo(LocalDate fechaPrestamo) {
        this.fechaPrestamo = fechaPrestamo;
    }

    public LocalDate getFechaPrevistaDevolucion() {
        return fechaPrevistaDevolucion;
    }

    public void setFechaPrevistaDevolucion(LocalDate fechaPrevistaDevolucion) {
        this.fechaPrevistaDevolucion = fechaPrevistaDevolucion;
    }

    public Libro getLibro() {
        return libro;
    }

    public void setLibro(Libro libro) {
        this.libro = libro;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }
}
