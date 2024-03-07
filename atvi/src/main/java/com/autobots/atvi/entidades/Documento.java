package com.autobots.atvi.entidades;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class Documento {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column
    private String tipo;
    @Column(unique = true)
    private String numero;
}
