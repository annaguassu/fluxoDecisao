package com.fluxodecisao.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Resposta {
	private Decisao decisao;
	private double renda;
	private double pcComprometimentoRenda;
	private int idade;
	private String cidade;
	private String motivoRecusa;
}
