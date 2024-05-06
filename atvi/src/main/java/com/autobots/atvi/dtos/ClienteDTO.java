package com.autobots.atvi.dtos;

import java.util.Date;
import java.util.List;

import com.autobots.atvi.entidades.Documento;
import com.autobots.atvi.entidades.Endereco;
import com.autobots.atvi.entidades.Telefone;

public record ClienteDTO(String nome, String nomeSocial, Date dataNascimento, Date dataCadastro, List<Documento> documentos, Endereco endereco, List<Telefone> telefones) {
    
}
