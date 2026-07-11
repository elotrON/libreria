package com.example.libreria.usuario.service;

import com.example.libreria.usuario.dto.*;
import com.example.libreria.usuario.entity.Usuario;
import com.example.libreria.usuario.repository.UsuarioRepository;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UsuarioServiceTest {

    @Mock
    private UsuarioRepository usuarioRepository;

    @InjectMocks
    private UsuarioService usuarioService;


    // -------------------------------
    // NOMBRE
    // -------------------------------
    @Test
    void noCrearUsuarioSinNombre(){

        UsuarioRequest usuarioRequest = crearUsuarioRequestValido();
        usuarioRequest.setNombre(null);

        RuntimeException ex;
        ex = Assertions.assertThrows(RuntimeException.class, () -> usuarioService.crearUsuario(usuarioRequest));

        Assertions.assertEquals("Falta nombre de usuario", ex.getMessage());
    }

    @Test
    void noCrearUsuarioConNombreBlanco(){
        UsuarioRequest usuarioRequest = crearUsuarioRequestValido();
        usuarioRequest.setNombre("  ");

        RuntimeException ex = Assertions.assertThrows(RuntimeException.class, () -> usuarioService.crearUsuario(usuarioRequest));
        Assertions.assertEquals("Falta nombre de usuario", ex.getMessage());
    }

    // -------------------------------
    // APELLIDOS
    // -------------------------------
    @Test
    void noCrearUsuarioSinApellidos(){
        UsuarioRequest usuarioRequest = crearUsuarioRequestValido();
        usuarioRequest.setApellidos(null);

        RuntimeException e = Assertions.assertThrows(RuntimeException.class, () -> usuarioService.crearUsuario(usuarioRequest));
        Assertions.assertEquals("Falta el apellido del usuario", e.getMessage());
    }

    @Test
    void noCrearUsuariosConApellidosEnBlanco(){
        UsuarioRequest usuarioRequest = crearUsuarioRequestValido();
        usuarioRequest.setApellidos("   ");

        RuntimeException ex = Assertions.assertThrows(RuntimeException.class, () -> usuarioService.crearUsuario(usuarioRequest));
        Assertions.assertEquals("Falta el apellido del usuario", ex.getMessage());
    }


    // -------------------------------
    // EMAIL
    // -------------------------------
    @Test
    void noCrearUsuarioSinEmail(){
        UsuarioRequest usuarioRequest = crearUsuarioRequestValido();
        usuarioRequest.setEmail(null);

        RuntimeException ex = Assertions.assertThrows(RuntimeException.class, ()-> usuarioService.crearUsuario(usuarioRequest));
        Assertions.assertEquals("Falta el email", ex.getMessage());
    }

    @Test
    void noCrearUsuarioConEmailEnBlanco(){
        UsuarioRequest usuarioRequest = crearUsuarioRequestValido();
        usuarioRequest.setEmail(" ");

        RuntimeException ex = Assertions.assertThrows(RuntimeException.class, () -> usuarioService.crearUsuario(usuarioRequest));
        Assertions.assertEquals("Email proporcionado en blanco", ex.getMessage());
    }


    @Test
    void noCrearUsuarioSiEmailYaExiste(){

        Usuario usuarioExistente = new Usuario();
        usuarioExistente.setId(1L);
        usuarioExistente.setEmail("pepe@pepe.com");

        UsuarioRequest nuevoUsuario = crearUsuarioRequestValido();
        nuevoUsuario.setEmail("pepe@pepe.com");

        when(usuarioRepository.findAll()).thenReturn(List.of(usuarioExistente));

        RuntimeException e =  Assertions.assertThrows(RuntimeException.class, ()-> usuarioService.crearUsuario(nuevoUsuario));
        Assertions.assertEquals("el usuario ya existe", e.getMessage());
    }



    // -------------------------------
    // DATOS VALIDOS
    // -------------------------------

    @Test
    void crearUsuarioConDatosValidos(){

        UsuarioRequest usuarioRequest = crearUsuarioRequestValido();
        UsuarioResponse usuarioResponse = usuarioService.crearUsuario(usuarioRequest);

        Assertions.assertEquals(usuarioResponse.getNombre(),usuarioRequest.getNombre());
        Assertions.assertEquals(usuarioResponse.getApellidos(), usuarioRequest.getApellidos());
        Assertions.assertEquals(usuarioRequest.getEmail(),usuarioResponse.getEmail());
        Assertions.assertTrue(usuarioResponse.getEstaActivo());
        Assertions.assertFalse(usuarioResponse.getEstaPenalizado());
    }

    private UsuarioRequest crearUsuarioRequestValido(){
        UsuarioRequest usuarioRequest = new UsuarioRequest();
        
        usuarioRequest.setNombre("Pepe Juan");
        usuarioRequest.setApellidos("Garcia Vaquero");
        usuarioRequest.setEmail("pjgarcia@example.com");

        return usuarioRequest;
    }


    private Usuario crearUsuarioValido(){
        Usuario usuario = new Usuario();

        usuario.setId(8L);
        usuario.setNombre("Antonio Jose");
        usuario.setApellidos("Navarro Navarrete");
        usuario.setEmail("ajnavarro@example.com");

        return usuario;
    }

    //#######################################
    //  TEST DE <BUSCAR USUARIO POR ID>
    //#######################################
    @Test
    void devuelveUsuarioSiExiste(){

        // given
        Long idUsuario = 3L;
        Usuario usuario = crearUsuarioValido();
        usuario.setId(idUsuario);

        when(usuarioRepository.findById(idUsuario)).thenReturn(Optional.of(usuario));

        // when
        UsuarioResponse usuarioResponse =  usuarioService.obtenerUsuario(idUsuario);

        // then
        Assertions.assertEquals(3L, usuarioResponse.getId());
        Assertions.assertEquals( "Antonio Jose", usuarioResponse.getNombre());
    }

    @Test
    void lanzaExcepcionSiUsuarioNoExiste(){
        RuntimeException ex = Assertions.assertThrows(RuntimeException.class, () -> usuarioService.obtenerUsuario(3L));
        Assertions.assertEquals("Usuario no encontrado", ex.getMessage());
    }

}
