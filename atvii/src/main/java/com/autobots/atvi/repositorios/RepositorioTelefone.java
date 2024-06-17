package com.autobots.atvi.repositorios;

import org.springframework.data.jpa.repository.JpaRepository;

import com.autobots.atvi.entidades.Telefone;

public interface RepositorioTelefone extends JpaRepository<Telefone, Long> {
    
}
