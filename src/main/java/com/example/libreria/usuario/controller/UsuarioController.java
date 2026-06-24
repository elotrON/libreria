package com.example.libreria.usuario.controller;

import com.example.libreria.usuario.dto.UsuarioRequest;
import com.example.libreria.usuario.dto.UsuarioResponse;
import com.example.libreria.usuario.service.UsuarioService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

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




}
