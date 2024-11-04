package com.springcloud.msvc.cursos.repositories;

import com.springcloud.msvc.cursos.models.entities.Curso;
import org.springframework.data.repository.CrudRepository;

public interface ICursoRepository extends CrudRepository<Curso, Long> {
}
