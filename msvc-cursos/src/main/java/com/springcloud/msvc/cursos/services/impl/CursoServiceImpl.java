package com.springcloud.msvc.cursos.services.impl;

import com.springcloud.msvc.cursos.models.entities.Curso;
import com.springcloud.msvc.cursos.repositories.ICursoRepository;
import com.springcloud.msvc.cursos.services.ICursoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class CursoServiceImpl implements ICursoService {

    @Autowired
    private ICursoRepository iCursoRepository;

    @Override
    @Transactional(readOnly = true)
    public List<Curso> listar() {
        return (List<Curso>) iCursoRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Curso> porId(Long id) {
        return iCursoRepository.findById(id);
    }

    @Override
    @Transactional
    public Curso guardar(Curso curso) {
        return iCursoRepository.save(curso);
    }

    @Override
    @Transactional
    public void eliminar(Long id) {
        iCursoRepository.deleteById(id);
    }
}
