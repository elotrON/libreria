package com.example.libreria.prestamo.service;

import com.example.libreria.prestamo.entity.EstadoPrestamo;
import com.example.libreria.libro.entity.Libro;
import com.example.libreria.prestamo.entity.Prestamo;
import com.example.libreria.prestamo.entity.Usuario;
import com.example.libreria.libro.repository.LibroRepository;
import com.example.libreria.prestamo.repository.PrestamoRepository;
import com.example.libreria.usuario.repository.UsuarioRepository;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Optional;

import static org.mockito.Mockito.*;




@ExtendWith(MockitoExtension.class)
public class PrestamoServiceTest {

    // @Mock indica que no se use el repo real 'LibroRepository'. Crea uno falso para hacer el test.
    @Mock
    private LibroRepository libroRepository;

    @Mock
    private UsuarioRepository usuarioRepository;

    @Mock
    private PrestamoRepository prestamoRepository;

    // InjectMocks se usa en la clase real que vamos a provar.
    // Indica a Mockito que injecte los @Mock aqui
    @InjectMocks
    private PrestamoService prestamoService;

//#############################################################################################################
//    usuario inactivo
//#############################################################################################################
    @Test
    void noDebePrestarLibroSiUsuarioEstaInactivo(){

        Long usuarioId = 1L;
        Long libroId = 1L;
        LocalDate fechaPrestamo = LocalDate.of(2026,5,31);

        //creamos un usuario 'inactivo' y 'no penalizado'
        Usuario pepe = new Usuario();
        pepe.setEstaActivo(false);
        pepe.setEstaPenalizado(false);

        // simula una respuesta. Cuando se pregunte por el usuario 'usuarioId' se devuelve el usuario falso 'usuario'
        when(usuarioRepository.findById(usuarioId)).thenReturn(Optional.of(pepe));

        // intenta crear el prestamo
        RuntimeException ex =  Assertions.assertThrows(RuntimeException.class, () -> prestamoService.prestarLibro(usuarioId, libroId, fechaPrestamo));

        Assertions.assertEquals("El usuario no esta activo", ex.getMessage());

    }

//#############################################################################################################
//   usuario penalizado
//#############################################################################################################
    @Test
    void noDebePrestarLibroSiUsuarioEstaPenalizado(){

        // GIVEN: datos iniciales. Creamos variables para simular la llamada al repository
        Long usuarioId = 1L;
        Long libroId = 1L;
        LocalDate fechaPrestamo = LocalDate.of(2026,5,12);

        // Creamos un usuario activo y penalizado
        Usuario usuario = new Usuario();
        usuario.setEstaActivo(true);
        usuario.setEstaPenalizado(true);

        Libro libro = new Libro();
        libro.setEstaDisponible(true);

        // simulamos la llamada a los repositorys
        when(usuarioRepository.findById(usuarioId)).thenReturn(Optional.of(usuario));

        // WHEN: acción que pruebo
        RuntimeException ex = Assertions.assertThrows(RuntimeException.class, () -> prestamoService.prestarLibro(usuarioId, libroId, fechaPrestamo));

        // THEN: comprobación esperada
        Assertions.assertEquals("El usuario esta penalizado", ex.getMessage());

    }


//#############################################################################################################
//  usuario no existe
//#############################################################################################################
    @Test
    void noDebePrestarLibroSiUsuarioNoExiste(){



    }



//#############################################################################################################
//  libro no disponible
//#############################################################################################################
@Test
void noDebePrestarLibroSiLibroNoEstaDisponible(){

    // GIVEN
    Long usuarioId = 1L;
    Long libroId = 1L;
    LocalDate fechaPrestamo = LocalDate.of(2025,4,7);

    Usuario usuario = new Usuario();
    usuario.setEstaActivo(true);
    usuario.setEstaPenalizado(false);

    Libro libro = new Libro();
    libro.setEstaDisponible(false);

    when(usuarioRepository.findById(usuarioId)).thenReturn(Optional.of(usuario));
    when(libroRepository.findById(libroId)).thenReturn(Optional.of(libro));

    // WHEN + THEN
    RuntimeException ex = Assertions.assertThrows(RuntimeException.class, () -> { prestamoService.prestarLibro(usuarioId, libroId, fechaPrestamo); });
    Assertions.assertEquals("El libro no esta disponible", ex.getMessage());
}

//#############################################################################################################
//  libro no existe
//#############################################################################################################


//#############################################################################################################
//
//#############################################################################################################
    @Test
    void debeCrearPrestamoActivoConFechaPrevista(){
        Prestamo prestamo = new Prestamo();
        Libro libro = new Libro();
        LocalDate fechaPrestamo = LocalDate.of(2026, 5, 31);
        libro.setEstaDisponible(true);
        libro.setISBN("ABC123");
        libro.setAutor("Pepe Carlo");
        libro.setAnhoPublicacion(2000);
        libro.setCategoria("Ficcion");

        prestamo.setEstado(EstadoPrestamo.ACTIVO);
        prestamo.setFechaPrestamo(fechaPrestamo);
        prestamo.setFechaPrevistaDevolucion(fechaPrestamo.plusDays(30));
        prestamo.setLibro(libro);

        Assertions.assertEquals(EstadoPrestamo.ACTIVO, prestamo.getEstado());
        Assertions.assertEquals(fechaPrestamo, prestamo.getFechaPrestamo()  );
        Assertions.assertEquals(LocalDate.of(2026, 6, 30), prestamo.getFechaPrevistaDevolucion());

        Assertions.assertEquals(prestamo.getLibro(), libro);
        Assertions.assertEquals(true, libro.getEstaDisponible());
        Assertions.assertEquals(2000, libro.getAnhoPublicacion());
        Assertions.assertEquals("Pepe Carlo", libro.getAutor());
    }

//#############################################################################################################
//#############################################################################################################
    @Test
    void debePrestarLibroSiUsuarioValidoYLibroDisponible(){

        // GIVEN
        // un usuario valido
        // un libro disponible
        Usuario usuario = new Usuario();
        usuario.setEstaPenalizado(false);
        usuario.setEstaActivo(true);

        Libro libro = new Libro();
        libro.setEstaDisponible(true);

        Long idUsuarioSimulado = 1L;
        Long idLibroSimulado = 1L;

        LocalDate fecha = LocalDate.of(2025,2,4);

        when(usuarioRepository.findById(idUsuarioSimulado)).thenReturn(Optional.of(usuario));
        when(libroRepository.findById(idLibroSimulado)).thenReturn(Optional.of(libro));

        // WHEN
        // cuando pida un prestamo
        var resultado = prestamoService.prestarLibro(idUsuarioSimulado, idLibroSimulado, fecha);


        // THEN
        Assertions.assertNotNull(resultado);
        Assertions.assertEquals(EstadoPrestamo.ACTIVO, resultado.getEstado());
        Assertions.assertEquals(usuario, resultado.getUsuario());
        Assertions.assertEquals(fecha, resultado.getFechaPrestamo());
        Assertions.assertEquals(fecha.plusDays(30), resultado.getFechaPrevistaDevolucion() );
//        Assertions.assertEquals(null, resultado.getFechaRealDevolucion());
        Assertions.assertEquals(libro, resultado.getLibro());
        Assertions.assertFalse(libro.getEstaDisponible());
    }




}
