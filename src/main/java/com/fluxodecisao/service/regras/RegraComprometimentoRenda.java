package com.fluxodecisao.service.regras;

import java.math.BigDecimal;
import java.math.RoundingMode;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class RegraComprometimentoRenda {

	public double comprometimendoDaRenda(double valorVeiculo, int qtParcelas, double renda) {

		double valorParcela = (valorVeiculo / qtParcelas);
		double pcComprometimento = (valorParcela / renda) * 100;
		
		//return Math.round(pcComprometimento);
		return new BigDecimal(pcComprometimento).setScale(2,RoundingMode.UP).doubleValue();
	}

}
