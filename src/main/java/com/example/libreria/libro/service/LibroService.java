package com.example.libreria.libro.service;

import com.example.libreria.libro.repository.LibroRepository;

import java.util.ArrayList;
import java.util.List;

import org.aspectj.apache.bcel.classfile.annotation.RuntimeInvisTypeAnnos;
import org.springframework.stereotype.Service;
import com.example.libreria.libro.dto.LibroRequest;
import com.example.libreria.libro.dto.LibroResponse;
import com.example.libreria.libro.entity.Libro;

@Service
public class LibroService {

    private final LibroRepository libroRepository;

    LibroService(LibroRepository libroRepository) {
        this.libroRepository = libroRepository;
    }

//######################################################################
//  CREAR UN LIBRO
//######################################################################
    public LibroResponse crearLibro(LibroRequest libroRequest){
        
        // COMPROBAMOS ISBN
        if(libroRequest.getISBN() == null || libroRequest.getISBN().isBlank()){
            throw new RuntimeException("Error al crear libro: ISBN en blanco");
        }
        
        // COMPROBAMOS TITULO
        if(libroRequest.getTitulo() == null || libroRequest.getTitulo().isBlank()){
            throw new RuntimeException("Error al crear libro: Falta el Titulo");
        }

        // COMPROBAMOS AUTOR
        if(libroRequest.getAutor() == null || libroRequest.getAutor().isBlank())
            throw new RuntimeException("Error al crear libro: Falta el autor");

        // COMPROBAMOS CATEGORIA
        if(libroRequest.getCategoria() == null || libroRequest.getCategoria().isBlank())
            throw new RuntimeException("Error al crear libro: Falta categoria");

        // comprobamos si el ISBN ya existe
        List<Libro> listadoLibros = libroRepository.findAll();

        for(Libro libro : listadoLibros){
            if(libro.getISBN().equals(libroRequest.getISBN())){
                throw new RuntimeException("Este libro ya existe");
            }
        }
        
        Libro libro = new Libro();
       
        libro.setAnhoPublicacion(libroRequest.getAnhoPublicacion());
        libro.setAutor(libroRequest.getAutor());
        libro.setCategoria(libroRequest.getCategoria());
        libro.setEstaDisponible(true);
        libro.setISBN(libroRequest.getISBN());
        libro.setTitulo(libroRequest.getTitulo());

        libroRepository.save(libro);

        return toResponse(libro);
    }


//######################################################################
//  OBTENER LIBRO POR ID
//######################################################################
    public LibroResponse obtenerLibroPorId(Long id){
        Libro libro = new Libro();

        libro = libroRepository.findById(id).orElseThrow( () -> new RuntimeException("Libro no encontrado"));
        return toResponse(libro);
    }



//######################################################################
//  LIBRO TO RESPONSE
//######################################################################
    public List<LibroResponse> listarLibros(){
        List<LibroResponse> listado = new ArrayList<>();
        List<Libro> libros = libroRepository.findAll();
        

        for(Libro libro : libros){
            listado.add(toResponse(libro));
        }

        return listado;
    }

        private LibroResponse toResponse(Libro libro){
        LibroResponse libroResponse = new LibroResponse();
        libroResponse.setAnhoPublicacion(libro.getAnhoPublicacion());
        libroResponse.setAutor(libro.getAutor());
        libroResponse.setCategoria(libro.getCategoria());
        libroResponse.setEstaDisponible(libro.getEstaDisponible());
        libroResponse.setISBN(libro.getISBN());
        libroResponse.setTitulo(libro.getTitulo());

        return libroResponse;
    }
}