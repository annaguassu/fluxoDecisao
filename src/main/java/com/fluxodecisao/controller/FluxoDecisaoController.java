package com.fluxodecisao.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fluxodecisao.model.Requisicao;
import com.fluxodecisao.model.Resposta;
import com.fluxodecisao.service.GerenciadorDeRegrasService;

@RestController 
@RequestMapping(value="/fluxo-decisao")
public class FluxoDecisaoController {
		
	@Autowired
	private GerenciadorDeRegrasService gerenciadorDeRegrasService;
	
	@PostMapping("/executar")
	public ResponseEntity<Resposta> executarFluxo(@RequestBody Requisicao requisicao){
		
		Resposta resposta = gerenciadorDeRegrasService.executaRegras(requisicao);
		
		return new ResponseEntity<>(resposta, HttpStatus.OK);
	}
	
}
