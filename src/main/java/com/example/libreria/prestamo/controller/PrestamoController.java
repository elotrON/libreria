package com.example.libreria.prestamo.controller;

import com.example.libreria.prestamo.dto.PrestamoRequest;
import com.example.libreria.prestamo.dto.PrestamoResponse;
import com.example.libreria.prestamo.service.PrestamoService;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
public class PrestamoController {

    private final PrestamoService prestamoService;

    public PrestamoController(PrestamoService prestamoService){
        this.prestamoService = prestamoService;
    }

    @PostMapping("/test")
    public String test(){
        return "hola";
    }


    @PostMapping("/prestamo")
    public PrestamoResponse prestarLibro(@RequestBody PrestamoRequest prestamoRequest){

        return prestamoService.prestarLibro(prestamoRequest.getUsuarioId(), prestamoRequest.getLibroId(), LocalDate.now());
    }

// todo 1. Probar el endpoint con Postman
/* todo 2. Añadir excepciones al service
    Por ejemplo:
     - UsuarioNoExiste
     - LibroNoExiste
     - UsuarioPenalizado
     - LibroNoDisponible

    y hacer tests con:

    assertThrows(...)

    Eso te enseñará más que testear el controller en este momento.

    Mi recomendación:

    ✔ Entidades
    ✔ Repositories
    ✔ Service
    ✔ Tests del Service
    ✔ Controller
    ⬜ Probar endpoint con Postman
    ⬜ assertThrows
    ⬜ Excepciones personalizadas
    ⬜ Tests de Controller


 */





}
