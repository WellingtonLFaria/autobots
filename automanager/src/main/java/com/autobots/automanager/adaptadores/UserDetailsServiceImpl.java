package com.autobots.automanager.adaptadores;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.autobots.automanager.entidades.Usuario;
import com.autobots.automanager.repositorios.RepositorioUsuario;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

	@Autowired
	private RepositorioUsuario repositorio;

	private Usuario obterPorNome(String nomeUsuario) {
		List<Usuario> usuarios = repositorio.findAll();
		Usuario selecionado = null;
		for (Usuario usuario : usuarios) {
			if (usuario.getCredencial().getNomeUsuario().equals(nomeUsuario)) {
				selecionado = usuario;
				break;
			}
		}
		return selecionado;
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Usuario selecionado = this.obterPorNome(username);
		if (selecionado == null) {
			throw new UsernameNotFoundException(username);
		}
		UserDetailsImpl usuario = new UserDetailsImpl(selecionado);
		return usuario;
	}
}