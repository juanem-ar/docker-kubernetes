package com.springcloud.msvc.cursos.models.entities;

import com.springcloud.msvc.cursos.models.Usuario;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "cursos")
@Data
public class Curso {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty
    private String nombre;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "curso_id")
    private List<CursoUsuario> cursoUsuarios;

    @Transient
    private List<Usuario> usuarioList;

    public Curso() {
        cursoUsuarios = new ArrayList<>();
        usuarioList = new ArrayList<>();
    }

    public void agregarCursoUsuarios(CursoUsuario cursoUsuario){
        this.cursoUsuarios.add(cursoUsuario);
    }
    public void removeCursoUsuarios(CursoUsuario cursoUsuario){
        this.cursoUsuarios.remove(cursoUsuario);
    }
}
