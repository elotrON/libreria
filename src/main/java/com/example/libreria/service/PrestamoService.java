package com.example.libreria.service;

import com.example.libreria.dto.PrestamoResponse;
import com.example.libreria.entity.EstadoPrestamo;
import com.example.libreria.entity.Libro;
import com.example.libreria.entity.Prestamo;
import com.example.libreria.entity.Usuario;
import com.example.libreria.repository.LibroRepository;
import com.example.libreria.repository.PrestamoRepository;
import com.example.libreria.repository.UsuarioRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Optional;

@Service
public class PrestamoService {

    private final LibroRepository libroRepository;
    private final UsuarioRepository usuarioRepository;
    private final PrestamoRepository prestamoRepository;

    public PrestamoService(
               LibroRepository libroRepository,
               UsuarioRepository usuarioRepository,
               PrestamoRepository prestamoRepository)
    {
        this.libroRepository = libroRepository;
        this.usuarioRepository = usuarioRepository;
        this.prestamoRepository = prestamoRepository;
    }

    /**
     * Crea un nuevo prestamo
     * @param usuarioId
     * @param libroId
     * @param fechaPrestamo
     * @return
     */
    public PrestamoResponse prestarLibro(Long usuarioId, Long libroId, LocalDate fechaPrestamo) {

        // Guardamos la referencia del usuarioId en un optional
        Optional<Usuario> usuarioOptional = usuarioRepository.findById(usuarioId);

        // si el usuarioId no existe, el usuarioOptional estara vacio
        if (usuarioOptional.isEmpty()) {
            throw new RuntimeException("El usuario no existe");
        }

        // si existe el usuario, lo cargamos en usuario
        Usuario usuario = usuarioOptional.get();

        if (usuario.getEstaActivo() == false)
            throw new RuntimeException("El usuario no esta activo");

        if (usuario.getEstaPenalizado() == true)
            throw new RuntimeException("El usuario esta penalizado");

        // comprobamos si existe el libro solicitado
        Optional<Libro> libroOptional = libroRepository.findById(libroId);
        if(libroOptional.isEmpty()){
            throw new RuntimeException("El libro no existe");
        }

        // si el libro existe, continuamos
        Libro libro = libroOptional.get();

        if (libro.getEstaDisponible() == false)
            throw new RuntimeException("El libro no esta disponible");

        Prestamo prestamo = new Prestamo();
        prestamo.setUsuario(usuario);


        prestamo.setLibro(libro);
        prestamo.setEstado(EstadoPrestamo.ACTIVO);
        prestamo.setFechaPrestamo(fechaPrestamo);
        prestamo.setFechaPrevistaDevolucion(fechaPrestamo.plusDays(30));
        libro.setEstaDisponible(false);

        prestamoRepository.save(prestamo);
        return toResponse(prestamo);
    }


    private PrestamoResponse toResponse(Prestamo prestamo){

        PrestamoResponse prestamoResponse = new PrestamoResponse();

        prestamoResponse.setEstado(prestamo.getEstado());
        prestamoResponse.setFechaPrestamo(prestamo.getFechaPrestamo());
        prestamoResponse.setUsuario(prestamo.getUsuario());
        prestamoResponse.setLibro(prestamo.getLibro());
        prestamoResponse.setFechaPrevistaDevolucion(prestamo.getFechaPrevistaDevolucion());

        return prestamoResponse;
    }









}
