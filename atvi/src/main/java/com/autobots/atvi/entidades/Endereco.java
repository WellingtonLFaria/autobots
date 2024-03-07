package com.autobots.atvi.entidades;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class Endereco {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column
    private String estado;
    @Column
    private String cidade;
    @Column
    private String bairro;
    @Column
    private String rua;
    @Column
    private String numero;
    @Column
    private String codigoPostal;
    @Column
    private String informacoesAdicionais;
}
