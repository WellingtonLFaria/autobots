package com.autobots.automanager.modelos.atualizadores;

import java.util.Set;

import org.springframework.stereotype.Service;

import com.autobots.automanager.entidades.Mercadoria;
import com.autobots.automanager.entidades.Servico;
import com.autobots.automanager.entidades.Venda;
import com.autobots.automanager.modelos.StringVerificadorNulo;

@Service
public class VendaAtualizador {
    private StringVerificadorNulo verificador = new StringVerificadorNulo();

    public void atualizar(Venda venda, Venda atualizacao) {
        if (atualizacao != null) {
            if (atualizacao.getCadastro() != null) {
                venda.setCadastro(atualizacao.getCadastro());
            }
            if (!verificador.verificar(atualizacao.getIdentificacao())) {
                venda.setIdentificacao(atualizacao.getIdentificacao());
            }
            if (atualizacao.getCliente() != null) {
                venda.setCliente(atualizacao.getCliente());
            }
            if (atualizacao.getFuncionario() != null) {
                venda.setFuncionario(atualizacao.getFuncionario());
            }
            if (atualizacao.getMercadorias() != null) {
                if (atualizacao.getMercadorias() != null) {
                    if (atualizacao.getMercadorias().isEmpty()) {
                        venda.getMercadorias().clear();
                    } else {
                        venda.getMercadorias().clear();
                        for (Mercadoria objeto : atualizacao.getMercadorias()) {
                            venda.getMercadorias().add(objeto);
                        }
                    }
                }
            }
            if (atualizacao.getServicos() != null) {
                if (atualizacao.getServicos() != null) {
                    if (atualizacao.getServicos().isEmpty()) {
                        venda.getServicos().clear();
                    } else {
                        venda.getServicos().clear();
                        for (Servico objeto : atualizacao.getServicos()) {
                            venda.getServicos().add(objeto);
                        }
                    }
                }
            }
            if (atualizacao.getVeiculo() != null) {
                venda.setVeiculo(atualizacao.getVeiculo());
            }
        }
    }

    public void atualizar(Set<Venda> vendas, Set<Venda> atualizacoes) {
        for (Venda atualizacao : atualizacoes) {
            for (Venda venda : vendas) {
                if (atualizacao.getId() != null) {
                    if (atualizacao.getId() == venda.getId()) {
                        atualizar(venda, atualizacao);
                    }
                }
            }
        }
    }
}
