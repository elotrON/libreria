package com.example.libreria.usuario.service;

import com.example.libreria.usuario.dto.*;
import com.example.libreria.usuario.entity.Usuario;
import com.example.libreria.usuario.repository.UsuarioRepository;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UsuarioServiceTest {

    @Mock
    private UsuarioRepository usuarioRepository;

    @InjectMocks
    private UsuarioService usuarioService;


    //#######################################
    // NOMBRE
    //#######################################
    @Test
    void noCrearUsuarioSinNombre(){

        UsuarioRequest usuarioRequest = crearUsuarioValido();
        usuarioRequest.setNombre(null);

        RuntimeException ex;
        ex = Assertions.assertThrows(RuntimeException.class, () -> usuarioService.crearUsuario(usuarioRequest));

        Assertions.assertEquals("Falta nombre de usuario", ex.getMessage());
    }

    @Test
    void noCrearUsuarioConNombreBlanco(){
        UsuarioRequest usuarioRequest = crearUsuarioValido();
        usuarioRequest.setNombre("  ");

        RuntimeException ex = Assertions.assertThrows(RuntimeException.class, () -> usuarioService.crearUsuario(usuarioRequest));
        Assertions.assertEquals("Falta nombre de usuario", ex.getMessage());
    }

    //#######################################
    // APELLIDOS
    //#######################################
    @Test
    void noCrearUsuarioSinApellidos(){
        UsuarioRequest usuarioRequest = crearUsuarioValido();
        usuarioRequest.setApellidos(null);

        RuntimeException e = Assertions.assertThrows(RuntimeException.class, () -> usuarioService.crearUsuario(usuarioRequest));
        Assertions.assertEquals("Falta el apellido del usuario", e.getMessage());
    }

    @Test
    void noCrearUsuariosConApellidosEnBlanco(){
        UsuarioRequest usuarioRequest = crearUsuarioValido();
        usuarioRequest.setApellidos("   ");

        RuntimeException ex = Assertions.assertThrows(RuntimeException.class, () -> usuarioService.crearUsuario(usuarioRequest));
        Assertions.assertEquals("Falta el apellido del usuario", ex.getMessage());
    }


    //#######################################
    // EMAIL
    //#######################################
    @Test
    void noCrearUsuarioSinEmail(){
        UsuarioRequest usuarioRequest = crearUsuarioValido();
        usuarioRequest.setEmail(null);

        RuntimeException ex = Assertions.assertThrows(RuntimeException.class, ()-> usuarioService.crearUsuario(usuarioRequest));
        Assertions.assertEquals("Falta el email", ex.getMessage());
    }

    @Test
    void noCrearUsuarioConEmailEnBlanco(){
        UsuarioRequest usuarioRequest = crearUsuarioValido();
        usuarioRequest.setEmail(" ");

        RuntimeException ex = Assertions.assertThrows(RuntimeException.class, () -> usuarioService.crearUsuario(usuarioRequest));
        Assertions.assertEquals("Email proporcionado en blanco", ex.getMessage());
    }


    @Test
    void noCrearUsuarioSiEmailYaExiste(){

        Usuario usuarioExistente = new Usuario();
        usuarioExistente.setId(1L);
        usuarioExistente.setEmail("pepe@pepe.com");

        UsuarioRequest nuevoUsuario = crearUsuarioValido();
        nuevoUsuario.setEmail("pepe@pepe.com");

        when(usuarioRepository.findAll()).thenReturn(List.of(usuarioExistente));

        RuntimeException e =  Assertions.assertThrows(RuntimeException.class, ()-> usuarioService.crearUsuario(nuevoUsuario));
        Assertions.assertEquals("el usuario ya existe", e.getMessage());
    }


    //#######################################
    // DATOS VALIDOS
    //#######################################
    @Test
    void crearUsuarioConDatosValidos(){

        UsuarioRequest usuarioRequest = crearUsuarioValido();
        UsuarioResponse usuarioResponse = usuarioService.crearUsuario(usuarioRequest);

        Assertions.assertEquals(usuarioResponse.getNombre(),usuarioRequest.getNombre());
        Assertions.assertEquals(usuarioResponse.getApellidos(), usuarioRequest.getApellidos());
        Assertions.assertEquals(usuarioRequest.getEmail(),usuarioResponse.getEmail());
        Assertions.assertTrue(usuarioResponse.getEstaActivo());
        Assertions.assertFalse(usuarioResponse.getEstaPenalizado());
    }

    private UsuarioRequest crearUsuarioValido(){
        UsuarioRequest usuarioRequest = new UsuarioRequest();
        
        usuarioRequest.setNombre("Pepe Juan");
        usuarioRequest.setApellidos("Garcia Vaquero");
        usuarioRequest.setEmail("pepe@juan.com");

        return usuarioRequest;
    }
}
