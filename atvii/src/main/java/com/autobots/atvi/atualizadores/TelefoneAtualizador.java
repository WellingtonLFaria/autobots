package com.autobots.atvi.atualizadores;

import java.util.List;

import com.autobots.atvi.entidades.Telefone;

public class TelefoneAtualizador {
    public Telefone atualizar(Telefone telefone, Telefone atualizacao) {
        boolean dddNulo = atualizacao.getDdd() == null || atualizacao.getDdd() == "";
        boolean numeroNulo = atualizacao.getNumero() == null || atualizacao.getNumero() == "";

        if (!dddNulo) telefone.setDdd(atualizacao.getDdd());
        if (!numeroNulo) telefone.setNumero(atualizacao.getNumero());

        return telefone;
    }

    public List<Telefone> atualizar(List<Telefone> telefones, List<Telefone> atualizacoes) {
        for (int i = 0; i < telefones.size(); i++) {
            telefones.set(i, atualizar(telefones.get(i), atualizacoes.get(i)));
        }
        return telefones;
    }
}
