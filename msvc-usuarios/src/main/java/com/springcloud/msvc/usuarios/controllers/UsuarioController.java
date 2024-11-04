package com.springcloud.msvc.usuarios.controllers;

import com.springcloud.msvc.usuarios.models.entities.Usuario;
import com.springcloud.msvc.usuarios.services.IUsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class UsuarioController {

    @Autowired
    private IUsuarioService iUsuarioService;

    @GetMapping
    public List<Usuario> listar(){
        return iUsuarioService.listar();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> detalle(@PathVariable Long id){
        Optional<Usuario> usuarioOpt = iUsuarioService.porId(id);
        if (usuarioOpt.isEmpty())
            return ResponseEntity.ok(usuarioOpt.get());
        return ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<?> crear(@RequestBody Usuario usuario){
        return ResponseEntity.status(HttpStatus.CREATED).body(iUsuarioService.guardar(usuario));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> editar(@RequestBody Usuario usuario, @PathVariable Long id ){
        Optional<Usuario> usuarioOpt = iUsuarioService.porId(id);
        if(usuarioOpt.isPresent()){
            Usuario usuarioDb = usuarioOpt.get();
            usuarioDb.setNombre(usuario.getNombre());
            usuarioDb.setEmail(usuario.getEmail());
            usuario.setPassword(usuario.getPassword());
            return ResponseEntity.status(HttpStatus.CREATED).body(iUsuarioService.guardar(usuarioDb));
        }else{
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminar(@PathVariable Long id){
        Optional<Usuario> usuarioOpt = iUsuarioService.porId(id);
        if(usuarioOpt.isPresent()){
            iUsuarioService.eliminar(id);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }else{
            return ResponseEntity.notFound().build();
        }
    }
}
