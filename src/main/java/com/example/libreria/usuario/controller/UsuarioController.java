package com.example.libreria.usuario.controller;

import com.example.libreria.usuario.dto.UsuarioRequest;
import com.example.libreria.usuario.dto.UsuarioResponse;
import com.example.libreria.usuario.service.UsuarioService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class UsuarioController {

    private final UsuarioService usuarioService;
    public UsuarioController(UsuarioService usuarioService ){
        this.usuarioService = usuarioService;
    }

    @PostMapping("/usuario")
    public UsuarioResponse crearUsuario(@RequestBody UsuarioRequest usuarioRequest){
        return usuarioService.crearUsuario(usuarioRequest);
    }

    @PostMapping("/usuarios")
    public List<UsuarioResponse> listarUsuarios(){
        return usuarioService.listarUsuarios();
    }

    @GetMapping("/usuario/{idUsuario}")
    public UsuarioResponse obtenerUsuario(@PathVariable Long idUsuario){
        return usuarioService.obtenerUsuario(idUsuario);
    }

    @DeleteMapping("/usuario/{idUsuario}")
    public UsuarioResponse borrarUsuario(@PathVariable Long idUsuario){
        return usuarioService.borrarUsuario(idUsuario);
    }

    @PutMapping("/usuario/{idUsuario}")
    public UsuarioResponse actualizarUsuario(@PathVariable Long idUsuario, @RequestBody UsuarioRequest usuarioRequest){
        return usuarioService.actualizarUsuario(idUsuario, usuarioRequest);
    }

    @PatchMapping("/usuario/{idUsuario}")
    public UsuarioResponse actualizaParcialUsuario(@PathVariable Long idUsuario, @RequestBody UsuarioRequest usuarioRequest){
        return usuarioService.actualizarParcialusuario(idUsuario, usuarioRequest);
    }

}
