package com.springcloud.msvc.usuarios.services.impl;

import com.springcloud.msvc.usuarios.clients.CursoClienteRest;
import com.springcloud.msvc.usuarios.models.entities.Usuario;
import com.springcloud.msvc.usuarios.repositories.IUsuarioRepository;
import com.springcloud.msvc.usuarios.services.IUsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class UsuarioServiceImpl implements IUsuarioService {

    @Autowired
    private IUsuarioRepository iUsuarioRepository;

    @Autowired
    private CursoClienteRest cursoClienteRest;

    @Override
    @Transactional(readOnly = true)
    public List<Usuario> listar() {
        return (List<Usuario>) iUsuarioRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Usuario> porId(Long id) {
        return iUsuarioRepository.findById(id);
    }

    @Override
    @Transactional
    public Usuario guardar(Usuario usuario) {
        return iUsuarioRepository.save(usuario);
    }

    @Override
    @Transactional
    public void eliminar(Long id) {
        iUsuarioRepository.deleteById(id);
        cursoClienteRest.eliminarCursoUsuarioPorId(id);
    }

    @Override
    public Optional<Usuario> porEmail(String email) {
        return iUsuarioRepository.findByEmail(email);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Usuario> listarPorIds(Iterable<Long> ids) {
        return (List<Usuario>) iUsuarioRepository.findAllById(ids);
    }

    @Override
    public boolean existePorEmail(String email) {
        return iUsuarioRepository.existsByEmail(email);
    }

}
