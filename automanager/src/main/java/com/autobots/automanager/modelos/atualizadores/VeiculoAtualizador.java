package com.autobots.automanager.modelos.atualizadores;

import java.util.Set;

import org.springframework.stereotype.Service;

import com.autobots.automanager.entidades.Veiculo;
import com.autobots.automanager.entidades.Venda;
import com.autobots.automanager.modelos.StringVerificadorNulo;

@Service
public class VeiculoAtualizador {
    private StringVerificadorNulo verificador = new StringVerificadorNulo();

    public void atualizar(Veiculo veiculo, Veiculo atualizacao) {
        if (atualizacao != null) {
            if (atualizacao.getTipo() != null) {
                veiculo.setTipo(atualizacao.getTipo());
            }
            if (!verificador.verificar(atualizacao.getModelo())) {
                veiculo.setModelo(atualizacao.getModelo());
            }
            if (!verificador.verificar(atualizacao.getPlaca())) {
                veiculo.setPlaca(atualizacao.getPlaca());
            }
            if (atualizacao.getProprietario() != null) {
                veiculo.setProprietario(atualizacao.getProprietario());
            }
            if (atualizacao.getVendas() != null) {
                if (atualizacao.getVendas().isEmpty()) {
					veiculo.getVendas().clear();
				} else {
					veiculo.getVendas().clear();
					for (Venda objeto : atualizacao.getVendas()) {
						veiculo.getVendas().add(objeto);
					}
				}
            }
        }
    }

    public void atualizar(Set<Veiculo> veiculos, Set<Veiculo> atualizacoes) {
        for (Veiculo atualizacao : atualizacoes) {
            for (Veiculo veiculo : veiculos) {
                if (atualizacao.getId() != null) {
                    if (atualizacao.getId() == veiculo.getId()) {
                        atualizar(veiculo, atualizacao);
                    }
                }
            }
        }
    }
}
