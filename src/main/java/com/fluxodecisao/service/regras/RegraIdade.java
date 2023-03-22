package com.fluxodecisao.service.regras;

import org.joda.time.DateTime;
import org.joda.time.Years;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

public class RegraIdade {
 
	public int calculaIdade(String dtString) {
		
		DateTimeFormatter parserData = DateTimeFormat.forPattern("yyyy-MM-dd");//desse jeito? ou "dd/MM/yyyy"?
		DateTime dataFormatada = DateTime.parse(dtString, parserData);
		
		DateTime dataAtual = new DateTime();
		
		Years idade = Years.yearsBetween(dataFormatada, dataAtual);
		
		return(idade.getYears());
	}
}
