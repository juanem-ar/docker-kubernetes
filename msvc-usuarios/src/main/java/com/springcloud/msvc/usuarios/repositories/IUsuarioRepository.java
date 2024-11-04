package com.springcloud.msvc.usuarios.repositories;

import com.springcloud.msvc.usuarios.models.entities.Usuario;
import org.springframework.data.repository.CrudRepository;

public interface IUsuarioRepository extends CrudRepository<Usuario, Long> {
}
