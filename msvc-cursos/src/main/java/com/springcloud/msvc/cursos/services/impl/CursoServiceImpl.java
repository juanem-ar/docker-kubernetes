package com.springcloud.msvc.cursos.services.impl;

import com.springcloud.msvc.cursos.clients.IUsuarioClientRest;
import com.springcloud.msvc.cursos.models.Usuario;
import com.springcloud.msvc.cursos.models.entities.Curso;
import com.springcloud.msvc.cursos.models.entities.CursoUsuario;
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

    @Autowired
    private IUsuarioClientRest clientRest;

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

    @Override
    @Transactional
    public Optional<Usuario> asignarUsuario(Usuario usuario, Long idCurso) {
        Optional<Curso> o = iCursoRepository.findById(idCurso);
        if (o.isPresent()) {
            Usuario usuarioMsvc = clientRest.detalle(usuario.getId());

            Curso curso = o.get();
            CursoUsuario cursoUsuario = new CursoUsuario();
            cursoUsuario.setIdUsuario(usuarioMsvc.getId());

            curso.agregarCursoUsuarios(cursoUsuario);
            iCursoRepository.save(curso);
            return Optional.of(usuarioMsvc);
        }

        return Optional.empty();

    }

    @Override
    @Transactional
    public Optional<Usuario> crearUsuario(Usuario usuario, Long idCurso) {
        Optional<Curso> o = iCursoRepository.findById(idCurso);
        if (o.isPresent()) {
            Usuario usuarioNuevoMsvc = clientRest.crear(usuario);

            Curso curso = o.get();
            CursoUsuario cursoUsuario = new CursoUsuario();
            cursoUsuario.setIdUsuario(usuarioNuevoMsvc.getId());

            curso.agregarCursoUsuarios(cursoUsuario);
            iCursoRepository.save(curso);
            return Optional.of(usuarioNuevoMsvc);
        }

        return Optional.empty();

    }

    @Override
    @Transactional
    public void eliminarCursoUsuarioPorId(Long id) {
        iCursoRepository.eliminarCursoUsuarioPorId(id);
    }


    @Override
    @Transactional
    public Optional<Usuario> eliminarUsuario(Usuario usuario, Long idCurso) {
        Optional<Curso> o = iCursoRepository.findById(idCurso);
        if (o.isPresent()) {
            Usuario usuarioMsvc = clientRest.detalle(usuario.getId());

            Curso curso = o.get();
            CursoUsuario cursoUsuario = new CursoUsuario();
            cursoUsuario.setIdUsuario(usuarioMsvc.getId());

            curso.removeCursoUsuarios(cursoUsuario);
            iCursoRepository.save(curso);
            return Optional.of(usuarioMsvc);
        }

        return Optional.empty();

    }
}
