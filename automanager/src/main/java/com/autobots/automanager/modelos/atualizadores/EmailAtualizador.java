package com.autobots.automanager.modelos.atualizadores;

import java.util.Set;

import org.springframework.stereotype.Service;

import com.autobots.automanager.entidades.Email;
import com.autobots.automanager.modelos.StringVerificadorNulo;

@Service
public class EmailAtualizador {
    private StringVerificadorNulo verificador = new StringVerificadorNulo();

    public void atualizar(Email email, Email atualizacao) {
        if (atualizacao != null) {
            if (!verificador.verificar(email.getEndereco())) {
                email.setEndereco(atualizacao.getEndereco());
            }
        }
    }

    public void atualizar(Set<Email> emails, Set<Email> atualizacoes) {
        for (Email atualizacao : atualizacoes) {
			for (Email email : emails) {
				if (atualizacao.getId() != null) {
					if (atualizacao.getId() == email.getId()) {
						atualizar(email, atualizacao);
					}
				}
			}
		}
    }
}
