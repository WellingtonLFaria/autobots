package com.autobots.automanager.modelos.atualizadores;

import java.util.Set;

import com.autobots.automanager.entidades.Empresa;
import com.autobots.automanager.modelos.StringVerificadorNulo;

public class EmpresaAtualizador {
    private StringVerificadorNulo verificador = new StringVerificadorNulo();

    public void atualizar(Empresa empresa, Empresa atualizacao) {
        if (atualizacao != null) {
            if (!verificador.verificar(atualizacao.getRazaoSocial())) {
                empresa.setRazaoSocial(atualizacao.getRazaoSocial());
            }
            if (!verificador.verificar(atualizacao.getNomeFantasia())) {
                empresa.setNomeFantasia(atualizacao.getNomeFantasia());
            }
            if (atualizacao.getTelefones() != null) {
                empresa.setTelefones(atualizacao.getTelefones());
            }
            if (atualizacao.getEndereco() != null) {
                empresa.setEndereco(atualizacao.getEndereco());
            }
            if (atualizacao.getCadastro() != null) {
                empresa.setCadastro(atualizacao.getCadastro());
            }
            if (atualizacao.getUsuarios() != null) {
                empresa.setUsuarios(atualizacao.getUsuarios());
            }
            if (atualizacao.getMercadorias() != null) {
                empresa.setMercadorias(atualizacao.getMercadorias());
            }
            if (atualizacao.getServicos() != null) {
                empresa.setServicos(atualizacao.getServicos());
            }
            if (atualizacao.getVendas() != null) {
                empresa.setVendas(atualizacao.getVendas());
            }
        }
    }

    public void atualizar(Set<Empresa> empresas, Set<Empresa> atualizacoes) {
        for (Empresa atualizacao : atualizacoes) {
            for (Empresa empresa : empresas) {
                if (atualizacao.getId() != null) {
                    if (atualizacao.getId() == empresa.getId()) {
                        atualizar(empresa, atualizacao);
                    }
                }
            }
        }
    }
}
