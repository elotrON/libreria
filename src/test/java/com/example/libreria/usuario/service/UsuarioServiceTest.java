package com.example.libreria.usuario.service;

import com.example.libreria.usuario.dto.UsuarioRequest;
import com.example.libreria.usuario.dto.UsuarioResponse;
import com.example.libreria.usuario.entity.Usuario;
import com.example.libreria.usuario.repository.UsuarioRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
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

        try {
            usuarioService.crearUsuario(usuarioRequest);
            Assertions.fail("Error, creado usuario sin nombre");
        }catch(RuntimeException ex){
            Assertions.assertEquals("Falta nombre de usuario", ex.getMessage());
        }
    }

    @Test
    void noCrearUsuarioConNombreBlanco(){
        UsuarioRequest usuarioRequest = crearUsuarioValido();
        usuarioRequest.setNombre("  ");

        try{
            usuarioService.crearUsuario(usuarioRequest);
            Assertions.fail("Error, se creo usuario con nombre en blanco");
        }catch(RuntimeException ex){
            Assertions.assertEquals("Falta nombre de usuario", ex.getMessage());
        }
    }

    //#######################################
    // APELLIDOS
    //#######################################
    @Test
    void noCrearUsuarioSinApellidos(){
        UsuarioRequest usuarioRequest = crearUsuarioValido();
        usuarioRequest.setApellidos(null);

        try{
            usuarioService.crearUsuario(usuarioRequest);
            Assertions.fail("Se creo un usuario sin apellidos");
        }catch(RuntimeException e)
        {
            Assertions.assertEquals("Falta el apellido del usuario", e.getMessage());
        }
    }

    @Test
    void noCrearUsuariosConApellidosEnBlanco(){
        UsuarioRequest usuarioRequest = crearUsuarioValido();
        usuarioRequest.setApellidos("   ");

        try{
            usuarioService.crearUsuario(usuarioRequest);
            Assertions.fail("Se ha creado un usuario sin apellidos");
        }catch(RuntimeException ex){
            Assertions.assertEquals("Falta el apellido del usuario", ex.getMessage());
        }
    }


    //#######################################
    // EMAIL
    //#######################################
    @Test
    void noCrearUsuarioSinEmail(){
        UsuarioRequest usuarioRequest = crearUsuarioValido();
        usuarioRequest.setEmail(null);

        try{
            usuarioService.crearUsuario(usuarioRequest);
            Assertions.fail();
        }catch(RuntimeException e){
            Assertions.assertEquals("Falta el email", e.getMessage());
        }
    }

    @Test
    void noCrearUsuarioConEmailEnBlanco(){
        UsuarioRequest usuarioRequest = crearUsuarioValido();
        usuarioRequest.setEmail(" ");

        try{
            usuarioService.crearUsuario(usuarioRequest);
            Assertions.fail("Usuario creado sin email");
        }catch(RuntimeException e){
            Assertions.assertEquals("Email proporcionado en blanco", e.getMessage());
        }
    }


    @Test
    void noCrearUsuarioSiEmailYaExiste(){

        Usuario usuarioExistente = new Usuario();
        usuarioExistente.setId(1L);
        usuarioExistente.setEmail("pepe@pepe.com");

        UsuarioRequest nuevoUsuario = crearUsuarioValido();
        nuevoUsuario.setEmail("pepe@pepe.com");

        when(usuarioRepository.findAll()).thenReturn(List.of(usuarioExistente));

        try{
            usuarioService.crearUsuario(nuevoUsuario);
            Assertions.fail("Se creo un usuario existente");
        }catch (RuntimeException ex){
            Assertions.assertEquals("el usuario ya existe", ex.getMessage());
        }
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
