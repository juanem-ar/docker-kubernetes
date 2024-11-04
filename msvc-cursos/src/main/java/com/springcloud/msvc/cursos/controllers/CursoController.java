package com.springcloud.msvc.cursos.controllers;

import com.springcloud.msvc.cursos.models.entities.Curso;
import com.springcloud.msvc.cursos.services.ICursoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
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

    @PostMapping
    public ResponseEntity<?> crear(@RequestBody Curso curso){
        return ResponseEntity.status(HttpStatus.CREATED).body(iCursoService.guardar(curso));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> editar(@RequestBody Curso curso, @PathVariable Long id ){
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
}
