package com.example.libreria.usuario.dto;


import java.time.LocalDate;

public class UsuarioResponse {
    private Long id;
    private String nombre;
    private String apellidos;
    private String email;
    private LocalDate fechaAlta;
    private Boolean estaActivo;
    private Boolean estaPenalizado;


    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Boolean getEstaActivo() {
        return estaActivo;
    }

    public void setEstaActivo(Boolean estaActivo) {
        this.estaActivo = estaActivo;
    }

    public Boolean getEstaPenalizado() {
        return estaPenalizado;
    }

    public void setEstaPenalizado(Boolean estaPenalizado) {
        this.estaPenalizado = estaPenalizado;
    }

    public LocalDate getFechaAlta() {
        return fechaAlta;
    }

    public void setFechaAlta(LocalDate fechaAlta) {
        this.fechaAlta = fechaAlta;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }


}
