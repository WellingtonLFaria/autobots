package com.autobots.automanager.modelos.atualizadores;

import java.util.Set;

import org.springframework.stereotype.Service;

import com.autobots.automanager.entidades.Servico;
import com.autobots.automanager.modelos.StringVerificadorNulo;

@Service
public class ServicoAtualizador {
    private StringVerificadorNulo verificador = new StringVerificadorNulo();

    public void atualizar(Servico servico, Servico atualizacao) {
        if (atualizacao != null) {
            if (!verificador.verificar(atualizacao.getNome())) {
                servico.setNome(atualizacao.getNome());
            }
            Double valor = atualizacao.getValor();
            if (valor != null) {
                servico.setValor(valor);
            }
            if (!verificador.verificar(atualizacao.getDescricao())) {
                servico.setDescricao(atualizacao.getDescricao());
            }
        }
    }

    public void atualizar(Set<Servico> servicos, Set<Servico> atualizacoes) {
        for (Servico atualizacao : atualizacoes) {
            for (Servico servico : servicos) {
                if (atualizacao.getId() != null) {
                    if (atualizacao.getId() == servico.getId()) {
                        atualizar(servico, atualizacao);
                    }
                }
            }
        }
    }
}
