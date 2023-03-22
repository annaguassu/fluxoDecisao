package com.fluxodecisao.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.RestTemplate;

import com.fluxodecisao.model.Pessoa;


@Repository
public class RestRepository {
	
	@Autowired
	private RestTemplate restTemplate;
	
	public Pessoa requisitaDadosPessoa(String numeroCpf) {
		ResponseEntity<Pessoa> resposta = restTemplate.getForEntity("http://localhost:8081/variaveis/" + numeroCpf, Pessoa.class);
		return resposta.getBody();
	}
}

