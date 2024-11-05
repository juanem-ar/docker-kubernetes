package com.springcloud.msvc.cursos.controllers;

import com.springcloud.msvc.cursos.models.entities.Curso;
import com.springcloud.msvc.cursos.services.ICursoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
public class CursoController {

    @Autowired
    private ICursoService iCursoService;

    @GetMapping
    public ResponseEntity<List<Curso>> listar(){
        return ResponseEntity.ok(iCursoService.listar());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> detalle(@PathVariable Long id){
        Optional<Curso> cursoOpt = iCursoService.porId(id);
        if (cursoOpt.isPresent())
            return ResponseEntity.ok(cursoOpt.get());
        return ResponseEntity.notFound().build();
    }

    @PostMapping("/")
    public ResponseEntity<?> crear(@Validated @RequestBody Curso curso, BindingResult result){
        if(result.hasErrors()) {
            validarErrores(result);
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(iCursoService.guardar(curso));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> editar(@Valid @RequestBody Curso curso, BindingResult result, @PathVariable Long id ){
        if(result.hasErrors()) {
            validarErrores(result);
        }
        Optional<Curso> cursoOpt = iCursoService.porId(id);
        if(cursoOpt.isPresent()){
            Curso cursoDb = cursoOpt.get();
            cursoDb.setNombre(curso.getNombre());
            return ResponseEntity.status(HttpStatus.CREATED).body(iCursoService.guardar(cursoDb));
        }else{
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminar(@PathVariable Long id){
        Optional<Curso> cursoOpt = iCursoService.porId(id);
        if(cursoOpt.isPresent()){
            iCursoService.eliminar(id);
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
}
