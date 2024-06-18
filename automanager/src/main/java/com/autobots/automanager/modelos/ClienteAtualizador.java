package com.autobots.automanager.modelos;

import org.springframework.stereotype.Service;

import com.autobots.automanager.entidades.Cliente;
import com.autobots.automanager.entidades.Documento;
import com.autobots.automanager.entidades.Telefone;

@Service
public class ClienteAtualizador {
	private StringVerificadorNulo verificador = new StringVerificadorNulo();

	public void atualizar(Cliente cliente, Cliente atualizacao) {
		if (!verificador.verificar(atualizacao.getNome())) {
			cliente.setNome(atualizacao.getNome());
		}
		if (!verificador.verificar(atualizacao.getNomeSocial())) {
			cliente.setNomeSocial(atualizacao.getNomeSocial());
		}
		if (!(atualizacao.getDataCadastro() == null)) {
			cliente.setDataCadastro(atualizacao.getDataCadastro());
		}
		if (!(atualizacao.getDataNascimento() == null)) {
			cliente.setDataNascimento(atualizacao.getDataNascimento());
		}
		if (atualizacao.getEndereco() != null) {
			cliente.setEndereco(atualizacao.getEndereco());
		}
		if (atualizacao.getDocumentos() != null) {
			if (atualizacao.getDocumentos().isEmpty()) {
				cliente.getDocumentos().clear();
			} else {
				cliente.getDocumentos().clear();
				for (Documento documento: atualizacao.getDocumentos()) {
					cliente.getDocumentos().add(documento);
				}
			}
		}
		if (atualizacao.getTelefones() != null) {
			if (atualizacao.getTelefones().isEmpty()) {
				cliente.getTelefones().clear();
			} else {
				cliente.getTelefones().clear();
				for (Telefone telefone: atualizacao.getTelefones()) {
					cliente.getTelefones().add(telefone);
				}
			}
		}
	}
}
