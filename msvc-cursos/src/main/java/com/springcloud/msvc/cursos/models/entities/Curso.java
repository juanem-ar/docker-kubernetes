package com.springcloud.msvc.cursos.models.entities;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "cursos")
@Data
public class Curso {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre;

}
