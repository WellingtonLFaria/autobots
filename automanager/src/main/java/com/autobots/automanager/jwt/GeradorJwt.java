package com.autobots.automanager.jwt;

import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
class GeradorJwt {
	private String assinatura;
	private Date expiracao;

	public GeradorJwt(@Value("${jwt.secret") String assinatura, @Value("${jwt.expiration}") long duracao) {
		this.assinatura = assinatura;
		this.expiracao = new Date(System.currentTimeMillis() + duracao);
	}

	public String gerarJwt(String nomeUsuario) {
		String jwt = Jwts.builder().setSubject(nomeUsuario).setExpiration(this.expiracao)
				.signWith(SignatureAlgorithm.HS512, this.assinatura.getBytes()).compact();
		return jwt;
	}
}