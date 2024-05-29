package com.autobots.atvi.atualizadores;

import com.autobots.atvi.dto.ClienteDTO;
import com.autobots.atvi.entidades.Cliente;

public class ClienteAtualizador {
    public Cliente atualizar(Cliente cliente, ClienteDTO atualizacao) {
        cliente.setNome(atualizacao.nome());
        cliente.setNomeSocial(atualizacao.nomeSocial());
        cliente.setDataNascimento(atualizacao.dataNascimento());
        return cliente;
    }
}
