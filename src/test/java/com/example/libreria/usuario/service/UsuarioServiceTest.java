package com.example.libreria.usuario.service;

import com.example.libreria.usuario.dto.*;
import com.example.libreria.usuario.entity.Usuario;
import com.example.libreria.usuario.repository.UsuarioRepository;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UsuarioServiceTest {

    @Mock
    private UsuarioRepository usuarioRepository;

    @InjectMocks
    private UsuarioService usuarioService;

    // #################################################
    //     CREAR USUARIO
    // #################################################
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
    void crearUsuarioDevuelveUsuarioSiExiste(){

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
    void crearUsuarioLanzaExcepcionSiUsuarioNoExiste(){
        RuntimeException ex = Assertions.assertThrows(RuntimeException.class, () -> usuarioService.obtenerUsuario(3L));
        Assertions.assertEquals("Usuario no encontrado", ex.getMessage());
    }


    //#######################################
    //  actualizarUsuario
    //#######################################
    @Test
    void actualizarUsuarioLanzaExcepcionSiUsuarioNoExiste(){
        UsuarioRequest usuarioRequest = crearUsuarioRequestValido();
        Long idUsuario = 3L;

        RuntimeException ex = Assertions.assertThrows(RuntimeException.class, () -> usuarioService.actualizarUsuario(idUsuario,usuarioRequest)  );
        Assertions.assertEquals("Usuario no encontrado", ex.getMessage());
    }

    @Test
    void noDebeActualizarUsuarioSiEmailNuevoYaExiste(){
        // GIVEN
        Long idUsuario = 2L;

        Usuario usuarioActualizar = new Usuario();
        usuarioActualizar.setId(idUsuario);
        usuarioActualizar.setEmail("juan@abc.com");

        Usuario usuarioExistente = new Usuario();
        usuarioExistente.setId(1L);
        usuarioExistente.setEmail("pepe@abc.com");

        UsuarioRequest usuarioRequest = new UsuarioRequest();
        usuarioRequest.setEmail("pepe@abc.com");

        when(usuarioRepository.findById(idUsuario))
                .thenReturn(Optional.of(usuarioActualizar));

        when(usuarioRepository.findAll())
                .thenReturn(List.of(usuarioActualizar, usuarioExistente));
        RuntimeException ex = Assertions.assertThrows(RuntimeException.class, () -> usuarioService.actualizarUsuario(idUsuario, usuarioRequest));
        Assertions.assertEquals("Ya existe este usuario", ex.getMessage());
    }

    @Test
    void noDebeActualizarUsuarioSiNombreYaExiste(){

        // Usuarios simulados
        Usuario u1 = new Usuario();
        u1.setId(1L);
        u1.setNombre("pepe");
        u1.setApellidos("gomez");
        u1.setEmail("pepe@mail.com");
        u1.setEstaActivo(true);
        u1.setEstaPenalizado(false);

        Usuario u2 = new Usuario();
        u2.setId(2L);
        u2.setNombre("juan");
        u2.setApellidos("garrido");
        u2.setEmail("juan@mail.com");
        u2.setEstaActivo(true);
        u2.setEstaPenalizado(false);

        Usuario u3 = new Usuario();
        u3.setId(3L);
        u3.setNombre("antonio");
        u3.setApellidos("Corrillo");
        u3.setEmail("antonio@mail.com");
        u3.setEstaActivo(true);
        u3.setEstaPenalizado(false);

        List<Usuario> usuarios = new ArrayList<>();
        usuarios.add(u1);
        usuarios.add(u2);
        usuarios.add(u3);

        // Datos nuevos a escribir
        UsuarioRequest usuarioRequest = crearUsuarioRequestValido();
        usuarioRequest.setNombre("juan");

        // Id del usuario a editar
        Long idUsuario = 1L;

        when(usuarioRepository.findAll()).thenReturn(usuarios);
        when(usuarioRepository.findById(idUsuario)).thenReturn(Optional.of(u1));
        RuntimeException ex = Assertions.assertThrows(RuntimeException.class, () -> usuarioService.actualizarUsuario(idUsuario, usuarioRequest));
        Assertions.assertEquals("nombre ya existe", ex.getMessage());

    }

    @Test
    void debeActualizarUsuarioSiDatosSonValidos(){
        // todo

    }

    @Test
    void noDebeBorrarUsuarioSiNoExiste(){
        //todo
    }

    @Test
    void DebeBorrarUsuarioSiUsuarioExiste(){
        //todo
    }


}
