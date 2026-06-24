package com.example.libreria.libro.service;

import static org.mockito.Mockito.when;

import java.util.List;

import com.example.libreria.libro.dto.LibroResponse;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.junit.jupiter.api.*;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.example.libreria.libro.dto.LibroRequest;
import com.example.libreria.libro.entity.Libro;
import com.example.libreria.libro.repository.LibroRepository;

@ExtendWith(MockitoExtension.class)
public class LibroServiceTest {

    // Cada libro se considera diferente de otro por su ISBN, es decir,
    // puede haber dos libros con distinto ISBN pero igual titulo

    @Mock
    private LibroRepository libroRepository;

    // se injecta en la clase real que vamos a testear
    @InjectMocks
    private LibroService libroService;


// #################################################
//  ISBN
// #################################################
    // si ya existe
    @Test
    void noDebeCrearLibroSiYaExisteISBN(){
        Libro libro = new Libro();
        libro.setISBN("1234");

        LibroRequest libroRequest = crearLibroRequestValido();
        libroRequest.setISBN("1234");

        when(libroRepository.findAll()).thenReturn(List.of(libro));

        try{
            libroService.crearLibro(libroRequest);
            Assertions.fail("Se ha creado un libro que ya existe el isbn");
        }catch(RuntimeException ex){
            Assertions.assertEquals("Este libro ya existe", ex.getMessage());
        }
    }

    //  ISBN (nulo) --> NO CREAR LIBRO
    @Test
    void noDebeCrearLibroSiFaltaISBN(){

        LibroRequest libroRequest = crearLibroRequestValido();
        libroRequest.setISBN(null);

        RuntimeException ex = Assertions.assertThrows(RuntimeException.class, () -> libroService.crearLibro(libroRequest));
        Assertions.assertEquals("Error al crear libro: ISBN en blanco", ex.getMessage());
    }


    //  ISBN (blanco) --> NO CREAR LIBRO
    @Test
    void noDebeCrearLibroSiISBNestaBlanco(){
        LibroRequest libroRequest = crearLibroRequestValido();
        libroRequest.setISBN("  ");

        try{
            libroService.crearLibro(libroRequest);
            Assertions.fail("Se ha creado un libro con ISBN en blanco");
        }catch (RuntimeException ex){
            Assertions.assertEquals("Error al crear libro: ISBN en blanco", ex.getMessage());
        }
    }


// #################################################
//  TITULO
// #################################################
    // titulo nulo
    @Test
    void noDebeCrearLibroSinTitulo(){

        LibroRequest libroRequest = crearLibroRequestValido();
        libroRequest.setTitulo(null);

        try{
            libroService.crearLibro(libroRequest);  //esta linea debe fallar y lanzar excepcion
            Assertions.fail("Se ha creado un libro sin Titulo");

        } catch (RuntimeException e) {
            Assertions.assertEquals("Error al crear libro: Falta el Titulo", e.getMessage());
        }
    }

    //  TITULO (en blanco) --> NO CREAR LIBRO
    @Test
    void noDebeCrearLibroSiTituloEsVacio(){

        LibroRequest libroRequest = crearLibroRequestValido();
        libroRequest.setTitulo("   ");

        try{
            libroService.crearLibro(libroRequest);
            Assertions.fail("Se ha creado un libro sin Titulo");
        }catch(RuntimeException ex){
            Assertions.assertEquals("Error al crear libro: Falta el Titulo", ex.getMessage());
        }
    }

// #################################################
//  AUTOR
// #################################################
    // (nulo) --> NO CREAR LIBRO
    @Test
    void noDebeCrearLibroSinAutor(){

        //given
        LibroRequest libroRequest = crearLibroRequestValido();
        libroRequest.setAutor(null);

        //when
        try{
            libroService.crearLibro(libroRequest);
            Assertions.fail("Libro creado sin Autor");
        }catch (RuntimeException e){
            Assertions.assertEquals("Error al crear libro: Falta el autor", e.getMessage());
        }
    }

    @Test
    void noDebeCrearLibroSiAutorEstaBlanco(){
        LibroRequest libroRequest = crearLibroRequestValido();
        libroRequest.setAutor("    ");

        //when
        try{
            libroService.crearLibro(libroRequest);
            Assertions.fail("Libro creado sin Autor");
        }catch (RuntimeException e){
            Assertions.assertEquals("Error al crear libro: Falta el autor", e.getMessage());
        }
    }


// #################################################
//  CATEGORIA
// #################################################
    // (vacio o nulo) --> NO CREAR LIBRO
    @Test
    void noDebeCrearLibroSinCategoria(){
        LibroRequest libroRequest = crearLibroRequestValido();
        libroRequest.setCategoria(null);
        try{
            libroService.crearLibro(libroRequest);
            Assertions.fail("Se ha creado un libro sin categoria");
        }catch (RuntimeException ex){
            Assertions.assertEquals("Error al crear libro: Falta categoria", ex.getMessage());
        }

    }

    @Test
    void noDebeCrearLibroSiCategoriaEnBlanco(){
        LibroRequest libroRequest = crearLibroRequestValido();

        libroRequest.setCategoria("   ");

        try{
            libroService.crearLibro(libroRequest);
            Assertions.fail("Se ha creado un libro sin categoria");
        }catch(RuntimeException ex){
            Assertions.assertEquals("Error al crear libro: Falta categoria", ex.getMessage());
        }
    }


// #################################################
//  DATAOS VALIDOS --> SI CREAR LIBRO
// #################################################
    @Test
    void debeCrearLibroSiDatosBien(){

        LibroRequest libroRequest = crearLibroRequestValido();
        LibroResponse libroResponse = libroService.crearLibro(libroRequest);

        Assertions.assertEquals(libroRequest.getISBN(), libroResponse.getISBN());
        Assertions.assertEquals(libroRequest.getAutor(), libroResponse.getAutor());
        Assertions.assertEquals(libroRequest.getCategoria(), libroResponse.getCategoria());
        Assertions.assertEquals(libroRequest.getTitulo(), libroResponse.getTitulo());
        Assertions.assertEquals(libroRequest.getAnhoPublicacion(), libroResponse.getAnhoPublicacion());

    }

    //TODO: PTE IMPLEMENTAR TEST BORRAR LIBRO


    private LibroRequest crearLibroRequestValido(){
        LibroRequest libroRequest = new LibroRequest();

        libroRequest.setTitulo("El pianista");
        libroRequest.setISBN("123ABC");
        libroRequest.setAutor("Pepe Antonio");
        libroRequest.setAnhoPublicacion(2023);
        libroRequest.setCategoria("Suspense");
        return libroRequest;
    }

}
