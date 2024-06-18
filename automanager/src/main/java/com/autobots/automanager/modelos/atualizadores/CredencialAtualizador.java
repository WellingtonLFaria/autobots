package com.autobots.automanager.modelos.atualizadores;

import java.util.Set;

import org.springframework.stereotype.Service;

import com.autobots.automanager.entidades.Credencial;

@Service
public class CredencialAtualizador {
    public void atualizar(Credencial credencial, Credencial atualizacao) {
        if (atualizacao != null) {
            if (atualizacao.getNomeUsuario() != null && !atualizacao.getNomeUsuario().isBlank()) {
                credencial.setNomeUsuario(atualizacao.getNomeUsuario());
            }
            if (atualizacao.getSenha() != null && !atualizacao.getSenha().isBlank()) {
                credencial.setSenha(atualizacao.getSenha());
            }
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
