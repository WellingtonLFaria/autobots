package com.autobots.atvi.atualizadores;

import com.autobots.atvi.entidades.Endereco;

public class EnderecoAtualizador {
    public Endereco atualizar(Endereco endereco, Endereco atualizacao) {
        boolean estadoNulo = atualizacao.getEstado() == null || atualizacao.getEstado() == "";
        boolean cidadeNula = atualizacao.getCidade() == null || atualizacao.getCidade() == "";
        boolean bairroNulo = atualizacao.getBairro() == null || atualizacao.getBairro() == "";
        boolean ruaNula = atualizacao.getRua() == null || atualizacao.getRua() == "";
        boolean numeroNulo = atualizacao.getNumero() == null || atualizacao.getNumero() == "";
        boolean codigoPostalNulo = atualizacao.getCodigoPostal() == null || atualizacao.getCodigoPostal() == "";
        boolean informacoesAdicionaisNulas = atualizacao.getInformacoesAdicionais() == null;

        if (!estadoNulo) endereco.setEstado(atualizacao.getEstado());
        if (!cidadeNula) endereco.setCidade(atualizacao.getCidade());
        if (!bairroNulo) endereco.setBairro(atualizacao.getBairro());
        if (!ruaNula) endereco.setRua(atualizacao.getRua());
        if (!numeroNulo) endereco.setNumero(atualizacao.getNumero());
        if (!codigoPostalNulo) endereco.setCodigoPostal(atualizacao.getCodigoPostal());
        if (!informacoesAdicionaisNulas) endereco.setInformacoesAdicionais(atualizacao.getInformacoesAdicionais());

        return endereco;
    }
}
