package com.example.libreria.libro.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

// Cuando damos de alta un libro, se considera que siempre va a estar disponible
public class LibroRequest {

    private String ISBN;
    private String titulo;
    private String autor;
    private Integer anhoPublicacion;
    private String categoria;

    public Integer getAnhoPublicacion() {
        return anhoPublicacion;
    }

    public void setAnhoPublicacion(Integer anhoPublicacion) {
        this.anhoPublicacion = anhoPublicacion;
    }

    public String getAutor() {
        return autor;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public String getISBN() {
        return ISBN;
    }

    public void setISBN(String ISBN) {
        this.ISBN = ISBN;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }
}