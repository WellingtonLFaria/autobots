package com.autobots.atvi.repositorios;

import org.springframework.data.jpa.repository.JpaRepository;

import com.autobots.atvi.entidades.Cliente;

public interface RepositorioCliente extends JpaRepository<Cliente, Long> {
    
}
