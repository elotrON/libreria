package com.example.libreria.usuario.service;

import com.example.libreria.usuario.dto.*;
import com.example.libreria.usuario.entity.Usuario;
import com.example.libreria.usuario.repository.UsuarioRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    public UsuarioService(UsuarioRepository usuarioRepository)  {
        this.usuarioRepository = usuarioRepository;
    }

    public UsuarioResponse crearUsuario(UsuarioRequest usuarioRequest){
        Usuario usuario = new Usuario();

        // El usuario se define por el dni
        // comprobar si existe un usuario con ese dni
        if(usuarioRequest.getNombre() == null || usuarioRequest.getNombre().isBlank())
            throw new RuntimeException ("Falta nombre de usuario");

        if(usuarioRequest.getApellidos() == null || usuarioRequest.getApellidos().isBlank()){
            throw new RuntimeException("Falta el apellido del usuario");
        }

        if(usuarioRequest.getEmail() == null)
            throw new RuntimeException("Falta el email");

        if(usuarioRequest.getEmail().isBlank())
            throw  new RuntimeException("Email proporcionado en blanco");

        if (comprobarSiExisteUsuario(usuarioRequest.getEmail()) == true)
            throw new RuntimeException("el usuario ya existe");

        usuario.setNombre(usuarioRequest.getNombre());
        usuario.setApellidos(usuarioRequest.getApellidos());
        usuario.setEmail(usuarioRequest.getEmail());
        usuario.setEstaActivo(true);
        usuario.setEstaPenalizado(false);
        usuario.setFechaAlta(LocalDate.now());

        usuarioRepository.save(usuario);
        return toResponse(usuario);
    }

    public List<UsuarioResponse> listarUsuarios(){
        List<UsuarioResponse> listadoUsuarios = new ArrayList<>();
        for(Usuario u : usuarioRepository.findAll()){
            listadoUsuarios.add(toResponse(u));
        }
        return listadoUsuarios;
    }


    public boolean comprobarSiExisteUsuario(String email){

        boolean existeUsuario = false;
        for(Usuario u : usuarioRepository.findAll()){
            if(u.getEmail().equals(email))
                return true;
        }
        return existeUsuario;
    }


    // todo: Obtener usuario por id
    // todo: Actualizar usuario
    // todo: borrar usuario
    private UsuarioResponse toResponse(Usuario usuario){

        UsuarioResponse usuarioResponse = new UsuarioResponse();

        usuarioResponse.setId(usuario.getId());
        usuarioResponse.setNombre(usuario.getNombre());
        usuarioResponse.setApellidos(usuario.getApellidos());
        usuarioResponse.setEmail(usuario.getEmail());
        usuarioResponse.setFechaAlta(usuario.getFechaAlta());
        usuarioResponse.setEstaActivo(usuario.getEstaActivo());
        usuarioResponse.setEstaPenalizado(usuario.getEstaPenalizado());

        return  usuarioResponse;
    }
}
