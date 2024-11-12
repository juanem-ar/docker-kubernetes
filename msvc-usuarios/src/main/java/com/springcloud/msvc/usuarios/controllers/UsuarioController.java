package com.springcloud.msvc.usuarios.controllers;

import com.springcloud.msvc.usuarios.models.entities.Usuario;
import com.springcloud.msvc.usuarios.services.IUsuarioService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
public class UsuarioController {

    @Autowired
    private IUsuarioService iUsuarioService;

    @GetMapping
    public ResponseEntity<List<Usuario>> listar(){
        return ResponseEntity.ok(iUsuarioService.listar());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> detalle(@PathVariable Long id){
        Optional<Usuario> usuarioOpt = iUsuarioService.porId(id);
        if (usuarioOpt.isPresent())
            return ResponseEntity.ok(usuarioOpt.get());
        return ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<?> crear(@Valid @RequestBody Usuario usuario, BindingResult result){

        ResponseEntity<Map<String, String>> BAD_REQUEST = getMapResponseEntity(usuario);
        if (BAD_REQUEST != null) return BAD_REQUEST;

        if(result.hasErrors()) {
            validarErrores(result);
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(iUsuarioService.guardar(usuario));
    }

    private ResponseEntity<Map<String, String>> getMapResponseEntity(Usuario usuario) {
        if(iUsuarioService.porEmail(usuario.getEmail()).isPresent()){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Collections
                            .singletonMap("mensaje", "Ya existe un usuario con ese email."));
        }
        return null;
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> editar(@Valid @RequestBody Usuario usuario, BindingResult result, @PathVariable Long id ){

        ResponseEntity<Map<String, String>> BAD_REQUEST = getMapResponseEntity(usuario);
        if (BAD_REQUEST != null) return BAD_REQUEST;

        if(result.hasErrors()) {
            //BindingResult debe ser el segundo parametro
            validarErrores(result);
        }
        Optional<Usuario> usuarioOpt = iUsuarioService.porId(id);
        if(usuarioOpt.isPresent()){
            Usuario usuarioDb = usuarioOpt.get();

            if (!usuario.getEmail().equalsIgnoreCase(usuarioDb.getEmail()) && iUsuarioService.porEmail(usuario.getEmail()).isPresent()){
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(Collections
                                .singletonMap("mensaje", "Ya existe un usuario con ese email."));
            }

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

    public ResponseEntity<Map<String, String>> validarErrores(BindingResult result){
            Map<String, String> errores = new HashMap<>();
            result.getFieldErrors().forEach( err -> {
                errores.put(err.getField(), "El campo " + err.getField() + ":" + err.getDefaultMessage());
            });
            return ResponseEntity.badRequest().body(errores);
    }
    @GetMapping("/usuarios-por-curso")
    public ResponseEntity<?> obtenerAlumnosPorCurso(@RequestParam List<Long> ids){
        return ResponseEntity.ok(iUsuarioService.listarPorIds(ids));
    }
}
