package com.example.libreria.controller;

import com.example.libreria.prestamo.controller.PrestamoController;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class PrestamoControllerTest {



    @InjectMocks
    private PrestamoController prestamoController;

    @Test
    void noDebePrestarLibroSiUsuarioNoExiste(){

    }

    @Test
    void noDebePrestarLibroSiLibroNoExiste(){

    }

    @Test
    void debePrestarLibroSiLibroExisteYusuarioExiste(){

    }

}
