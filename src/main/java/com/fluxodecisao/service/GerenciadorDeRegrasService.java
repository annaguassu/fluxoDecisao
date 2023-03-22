package com.fluxodecisao.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fluxodecisao.model.Decisao;
import com.fluxodecisao.model.EtapaEvento;
import com.fluxodecisao.model.Evento;
import com.fluxodecisao.model.Pessoa;
import com.fluxodecisao.model.Requisicao;
import com.fluxodecisao.model.Resposta;
import com.fluxodecisao.repository.FluxoDecisaoRepository;
import com.fluxodecisao.repository.RestRepository;
import com.fluxodecisao.service.regras.RegraComprometimentoRenda;
import com.fluxodecisao.service.regras.RegraIdade;
import com.fluxodecisao.util.JsonUtils;

@Service
public class GerenciadorDeRegrasService {

	@Autowired
	private FluxoDecisaoRepository fluxoDecisaoRepository;

	@Autowired
	private RestRepository restRepository;

	public Resposta executaRegras(Requisicao requisicao) {

		criaESalvaEvento(requisicao, null, EtapaEvento.INICIO_FLUXO);

		Pessoa pessoa = restRepository.requisitaDadosPessoa(requisicao.getDocumento());

		criaESalvaEvento(requisicao, pessoa, EtapaEvento.BUSCANDO_DADOS_PESSOA);

		RegraIdade regraIdade = new RegraIdade();

		RegraComprometimentoRenda pcComprometimento = new RegraComprometimentoRenda();

		double pcCompromentimentoDeRenda = pcComprometimento.comprometimendoDaRenda(requisicao.getValorVeiculo(),
				requisicao.getQtParcelas(), pessoa.getRenda());

		int idade = regraIdade.calculaIdade(pessoa.getDtnasc());

		criaESalvaEvento(requisicao, pessoa.getCidade(), EtapaEvento.VALIDANDO_CIDADE_PESSOA);
		
		criaESalvaEvento(requisicao, pessoa, EtapaEvento.VALIDANDO_IDADE_PESSOA);
		if (!pessoa.getCidade().equals("Matao")) {
			Resposta resposta = new Resposta();
			criaDecisao(pessoa, pcCompromentimentoDeRenda, idade, resposta, Decisao.Negado);
			resposta.setMotivoRecusa("Pessoa nao eh da cidade de Matao");

			return resposta;

		} else if (idade < 18 || idade > 60) {

			Resposta resposta = new Resposta();
			criaDecisao(pessoa, pcCompromentimentoDeRenda, idade, resposta, Decisao.Negado);
			resposta.setMotivoRecusa("Pessoa nao tem idade entre 18 e 25");

			criaESalvaEvento(requisicao, pessoa, EtapaEvento.VALIDANDO_IDADE_PESSOA);
			
			return resposta;

		} else if (pcCompromentimentoDeRenda >= 30.0) {
			Resposta resposta = new Resposta();
			criaDecisao(pessoa, pcCompromentimentoDeRenda, idade, resposta, Decisao.Negado);
			resposta.setMotivoRecusa("Comprometimento da renda é acima de 30%");

			criaESalvaEvento(requisicao, pessoa, EtapaEvento.VALIDANDO_COMPROMETIMENTO_RENDA);
			return resposta;

		} else {
			
			Resposta resposta = new Resposta();
			
			criaDecisao(pessoa, pcCompromentimentoDeRenda, idade, resposta, Decisao.Aprovado);

			criaESalvaEvento(requisicao, resposta, EtapaEvento.FIM_FLUXO);
			
			return resposta;

		}

		// return new Resposta();

	}

	private void criaDecisao(Pessoa pessoa, double pcCompromentimentoDeRenda, int idade, Resposta resposta, Decisao decisao) {
		resposta.setDecisao(decisao);// como posso mudar tanto pra aprovado ou negado?
		resposta.setRenda(pessoa.getRenda());
		resposta.setPcComprometimentoRenda(pcCompromentimentoDeRenda);
		resposta.setCidade(pessoa.getCidade());
		resposta.setIdade(idade);
	}

	private void criaESalvaEvento(Object requisicao, Object resposta, EtapaEvento etapa) {
		Evento evento = new Evento();
		evento.setEtapa(etapa.toString());
		evento.setDadoEntrada(JsonUtils.objToJson(requisicao));
		evento.setDadoSaida(JsonUtils.objToJson(resposta));

		fluxoDecisaoRepository.save(evento);
	}
}
