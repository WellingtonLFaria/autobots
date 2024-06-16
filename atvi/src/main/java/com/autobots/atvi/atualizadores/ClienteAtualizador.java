package com.autobots.atvi.atualizadores;

import com.autobots.atvi.entidades.Cliente;

public class ClienteAtualizador {
    private DocumentoAtualizador documentoAtualizador = new DocumentoAtualizador();
    private EnderecoAtualizador enderecoAtualizador = new EnderecoAtualizador();
    private TelefoneAtualizador telefoneAtualizador = new TelefoneAtualizador();

    public Cliente atualizar(Cliente cliente, Cliente atualizacao) {
        boolean nomeNulo = atualizacao.getNome() == null || atualizacao.getNome() == "";
        boolean nomeSocialNulo = atualizacao.getNomeSocial() == null || atualizacao.getNomeSocial() == "";
        boolean dataNascimentoNula = atualizacao.getDataNascimento() == null;
        boolean documentosNulo = atualizacao.getDocumentos() == null;
        boolean enderecoNulo = atualizacao.getEndereco() == null;
        boolean telefonesNulo = atualizacao.getTelefones() == null;

        if (!nomeNulo) cliente.setNome(atualizacao.getNome());
        if (!nomeSocialNulo) cliente.setNomeSocial(atualizacao.getNomeSocial());
        if (!dataNascimentoNula) cliente.setDataNascimento(atualizacao.getDataNascimento());
        if (!documentosNulo) cliente.setDocumentos(documentoAtualizador.atualizar(cliente.getDocumentos(), atualizacao.getDocumentos()));
        if (!enderecoNulo) cliente.setEndereco(enderecoAtualizador.atualizar(cliente.getEndereco(), atualizacao.getEndereco()));
        if (!telefonesNulo) cliente.setTelefones(telefoneAtualizador.atualizar(cliente.getTelefones(), atualizacao.getTelefones()));

        return cliente;
    }
}
