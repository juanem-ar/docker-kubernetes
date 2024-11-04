package com.springcloud.msvc.usuarios.services;

import com.springcloud.msvc.usuarios.models.entities.Usuario;

import java.util.List;
import java.util.Optional;

public interface IUsuarioService {
    List<Usuario> listar();
    Optional<Usuario> porId(Long id);
    Usuario guardar(Usuario usuario);
    void eliminar(Long id);
}