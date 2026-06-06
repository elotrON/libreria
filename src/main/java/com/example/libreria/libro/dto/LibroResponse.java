package com.example.libreria.libro.dto;


public class LibroResponse {

    private Long id;
    private String ISBN;
    private String titulo;
    private String autor;
    private Integer anhoPublicacion;
    private String categoria;
    private Boolean estaDisponible;

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

    public Boolean getEstaDisponible() {
        return estaDisponible;
    }

    public void setEstaDisponible(Boolean estaDisponible) {
        this.estaDisponible = estaDisponible;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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
