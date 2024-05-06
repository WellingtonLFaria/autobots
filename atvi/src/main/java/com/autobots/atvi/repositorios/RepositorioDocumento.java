package com.autobots.atvi.repositorios;

import org.springframework.data.jpa.repository.JpaRepository;

import com.autobots.atvi.entidades.Documento;

public interface RepositorioDocumento extends JpaRepository<Documento, Long>{
    
}
