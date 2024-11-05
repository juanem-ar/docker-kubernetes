package com.springcloud.msvc.usuarios.repositories;

import com.springcloud.msvc.usuarios.models.entities.Usuario;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface IUsuarioRepository extends CrudRepository<Usuario, Long> {
    Optional<Usuario> findByEmail(String email);
}
