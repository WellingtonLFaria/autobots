package com.autobots.atvi.repositorios;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.autobots.atvi.entidades.Documento;

public interface RepositorioDocumento extends JpaRepository<Documento, Long>{
    Optional<Documento> findByNumero(String numero);
}
