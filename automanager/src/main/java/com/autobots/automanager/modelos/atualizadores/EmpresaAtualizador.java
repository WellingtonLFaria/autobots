package com.autobots.automanager.modelos.atualizadores;

import java.util.Set;

import org.springframework.stereotype.Service;

import com.autobots.automanager.entidades.Empresa;
import com.autobots.automanager.entidades.Mercadoria;
import com.autobots.automanager.entidades.Servico;
import com.autobots.automanager.entidades.Telefone;
import com.autobots.automanager.entidades.Usuario;
import com.autobots.automanager.entidades.Venda;
import com.autobots.automanager.modelos.StringVerificadorNulo;

@Service
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
                if (atualizacao.getTelefones() != null) {
                    if (atualizacao.getTelefones().isEmpty()) {
                        empresa.getTelefones().clear();
                    } else {
                        empresa.getTelefones().clear();
                        for (Telefone objeto : atualizacao.getTelefones()) {
                            empresa.getTelefones().add(objeto);
                        }
                    }
                }
            }
            if (atualizacao.getEndereco() != null) {
                empresa.setEndereco(atualizacao.getEndereco());
            }
            if (atualizacao.getCadastro() != null) {
                empresa.setCadastro(atualizacao.getCadastro());
            }
            if (atualizacao.getUsuarios() != null) {
                if (atualizacao.getUsuarios() != null) {
                    if (atualizacao.getUsuarios().isEmpty()) {
                        empresa.getUsuarios().clear();
                    } else {
                        empresa.getUsuarios().clear();
                        for (Usuario objeto : atualizacao.getUsuarios()) {
                            empresa.getUsuarios().add(objeto);
                        }
                    }
                }
            }
            if (atualizacao.getMercadorias() != null) {
                if (atualizacao.getMercadorias() != null) {
                    if (atualizacao.getMercadorias().isEmpty()) {
                        empresa.getMercadorias().clear();
                    } else {
                        empresa.getMercadorias().clear();
                        for (Mercadoria objeto : atualizacao.getMercadorias()) {
                            empresa.getMercadorias().add(objeto);
                        }
                    }
                }
            }
            if (atualizacao.getServicos() != null) {
                if (atualizacao.getServicos() != null) {
                    if (atualizacao.getServicos().isEmpty()) {
                        empresa.getServicos().clear();
                    } else {
                        empresa.getServicos().clear();
                        for (Servico objeto : atualizacao.getServicos()) {
                            empresa.getServicos().add(objeto);
                        }
                    }
                }
            }
            if (atualizacao.getVendas() != null) {
                if (atualizacao.getVendas() != null) {
                    if (atualizacao.getVendas().isEmpty()) {
                        empresa.getVendas().clear();
                    } else {
                        empresa.getVendas().clear();
                        for (Venda objeto : atualizacao.getVendas()) {
                            empresa.getVendas().add(objeto);
                        }
                    }
                }
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
