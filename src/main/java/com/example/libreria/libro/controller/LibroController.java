package com.example.libreria.libro.controller;

import com.example.libreria.libro.dto.LibroRequest;
import com.example.libreria.libro.dto.LibroResponse;
import com.example.libreria.libro.entity.Libro;
import com.example.libreria.libro.service.LibroService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LibroController {

    private final LibroService libroService;

    public LibroController(LibroService libroService){
        this.libroService = libroService;
    }

    @PostMapping("/libro")
    public LibroResponse CrearLibro(@RequestBody LibroRequest libroRequest){
        return (libroService.crearLibro(libroRequest));
    }





}
