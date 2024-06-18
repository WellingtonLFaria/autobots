package com.autobots.automanager.modelos.atualizadores;

import org.springframework.stereotype.Service;

import com.autobots.automanager.entidades.Credencial;
import com.autobots.automanager.entidades.Documento;
import com.autobots.automanager.entidades.Email;
import com.autobots.automanager.entidades.Mercadoria;
import com.autobots.automanager.entidades.Telefone;
import com.autobots.automanager.entidades.Usuario;
import com.autobots.automanager.entidades.Veiculo;
import com.autobots.automanager.entidades.Venda;
import com.autobots.automanager.enumeracoes.PerfilUsuario;
import com.autobots.automanager.modelos.StringVerificadorNulo;

@Service
public class UsuarioAtualizador {
	private StringVerificadorNulo verificador = new StringVerificadorNulo();

	public void atualizar(Usuario usuario, Usuario atualizacao) {
		if (atualizacao != null) {
			if (!verificador.verificar(atualizacao.getNome())) {
				usuario.setNome(atualizacao.getNome());
			}
			if (!verificador.verificar(atualizacao.getNomeSocial())) {
				usuario.setNomeSocial(atualizacao.getNomeSocial());
			}
			if (atualizacao.getPerfis() != null) {
				if (atualizacao.getPerfis().isEmpty()) {
					usuario.getPerfis().clear();
				} else {
					usuario.getPerfis().clear();
					for (PerfilUsuario objeto : atualizacao.getPerfis()) {
						usuario.getPerfis().add(objeto);
					}
				}
			}
			if (atualizacao.getTelefones() != null) {
				if (atualizacao.getTelefones().isEmpty()) {
					usuario.getTelefones().clear();
				} else {
					usuario.getTelefones().clear();
					for (Telefone objeto : atualizacao.getTelefones()) {
						usuario.getTelefones().add(objeto);
					}
				}
			}
			if (atualizacao.getEndereco() != null) {
				usuario.setEndereco(atualizacao.getEndereco());
			}
			if (atualizacao.getDocumentos() != null) {
				if (atualizacao.getDocumentos().isEmpty()) {
					usuario.getDocumentos().clear();
				} else {
					usuario.getDocumentos().clear();
					for (Documento documento : atualizacao.getDocumentos()) {
						usuario.getDocumentos().add(documento);
					}
				}
			}
			if (atualizacao.getEmails() != null) {
				if (atualizacao.getEmails().isEmpty()) {
					usuario.getEmails().clear();
				} else {
					usuario.getEmails().clear();
					for (Email objeto : atualizacao.getEmails()) {
						usuario.getEmails().add(objeto);
					}
				}
			}
			if (atualizacao.getCredenciais() != null) {
				if (atualizacao.getCredenciais().isEmpty()) {
					usuario.getCredenciais().clear();
				} else {
					usuario.getCredenciais().clear();
					for (Credencial credencial : atualizacao.getCredenciais()) {
						usuario.getCredenciais().add(credencial);
					}
				}
			}
			if (atualizacao.getMercadorias() != null) {
				if (atualizacao.getMercadorias().isEmpty()) {
					usuario.getMercadorias().clear();
				} else {
					usuario.getMercadorias().clear();
					for (Mercadoria objeto : atualizacao.getMercadorias()) {
						usuario.getMercadorias().add(objeto);
					}
				}
			}
			if (atualizacao.getVendas() != null) {
				if (atualizacao.getVendas().isEmpty()) {
					usuario.getVendas().clear();
				} else {
					usuario.getVendas().clear();
					for (Venda objeto : atualizacao.getVendas()) {
						usuario.getVendas().add(objeto);
					}
				}
			}
			if (atualizacao.getVeiculos() != null) {
				if (atualizacao.getVeiculos().isEmpty()) {
					usuario.getVeiculos().clear();
				} else {
					usuario.getVeiculos().clear();
					for (Veiculo objeto : atualizacao.getVeiculos()) {
						usuario.getVeiculos().add(objeto);
					}
				}
			}
		}
	}
}
