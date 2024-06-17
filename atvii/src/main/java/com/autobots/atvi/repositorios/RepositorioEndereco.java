package com.autobots.atvi.repositorios;

import org.springframework.data.jpa.repository.JpaRepository;

import com.autobots.atvi.entidades.Endereco;

public interface RepositorioEndereco extends JpaRepository<Endereco, Long>{
    
}
