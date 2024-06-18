package com.autobots.automanager.modelos.atualizadores;

import java.util.Set;

import org.springframework.stereotype.Service;

import com.autobots.automanager.entidades.Credencial;

@Service
public class CredencialAtualizador {
    public void atualizar(Credencial credencial, Credencial atualizacao) {
        if (atualizacao != null) {
            if (atualizacao.getCriacao() != null) {
                credencial.setCriacao(atualizacao.getCriacao());
            }
            if (atualizacao.getUltimoAcesso() != null) {
                credencial.setUltimoAcesso(atualizacao.getUltimoAcesso());
            }
            credencial.setInativo(atualizacao.isInativo());
        }
    }

    public void atualizar(Set<Credencial> credenciais, Set<Credencial> atualizacoes) {
        for (Credencial atualizacao : atualizacoes) {
            for (Credencial credencial : credenciais) {
                if (atualizacao.getId() != null) {
                    if (atualizacao.getId() == credencial.getId()) {
                        atualizar(credencial, atualizacao);
                    }
                }
            }
        }
    }
}
