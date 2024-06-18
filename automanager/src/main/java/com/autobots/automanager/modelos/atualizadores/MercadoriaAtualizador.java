package com.autobots.automanager.modelos.atualizadores;

import java.util.Set;

import org.springframework.stereotype.Service;

import com.autobots.automanager.entidades.Mercadoria;
import com.autobots.automanager.modelos.StringVerificadorNulo;

@Service
public class MercadoriaAtualizador {
    private StringVerificadorNulo verificador = new StringVerificadorNulo();

    public void atualizar(Mercadoria mercadoria, Mercadoria atualizacao) {
        if (atualizacao != null) {
            if (atualizacao.getValidade() != null) {
                mercadoria.setValidade(atualizacao.getValidade());
            }
            if (atualizacao.getFabricao() != null) {
                mercadoria.setFabricao(atualizacao.getFabricao());
            }
            if (!verificador.verificar(atualizacao.getNome())) {
                mercadoria.setNome(atualizacao.getNome());
            }
            Long quantidade = atualizacao.getQuantidade();
            if (quantidade != null) {
                mercadoria.setQuantidade(atualizacao.getQuantidade());
            }
            Double valor = atualizacao.getValor();
            if (valor != null) {
                mercadoria.setValor(valor);
            }
            if (atualizacao.getDescricao() == null) {
                mercadoria.setDescricao("");
                return;
            }
            mercadoria.setDescricao(atualizacao.getDescricao());
        }
    }

    public void atualizar(Set<Mercadoria> mercadorias, Set<Mercadoria> atualizacoes) {
        for (Mercadoria atualizacao : atualizacoes) {
            for (Mercadoria mercadoria : mercadorias) {
                if (atualizacao.getId() != null) {
                    if (atualizacao.getId() == mercadoria.getId()) {
                        atualizar(mercadoria, atualizacao);
                    }
                }
            }
        }
    }
}
