package com.springcloud.msvc.cursos.models.entities;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Objects;

@Entity
@Table(name = "cursos_usuarios")
@Data
public class CursoUsuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "usuario_id", unique = true)
    private Long idUsuario;

    //aparte de comparar objetos por instancia, se compara por id
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CursoUsuario)) return false;
        CursoUsuario that= (CursoUsuario) o;
        return this.idUsuario != null && this.idUsuario.equals(((CursoUsuario) o).idUsuario);
    }

}
