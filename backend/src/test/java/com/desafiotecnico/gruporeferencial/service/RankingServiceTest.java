package com.desafiotecnico.gruporeferencial.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDateTime;
import java.util.List;

import org.junit.jupiter.api.Test;

import com.desafiotecnico.gruporeferencial.model.Episodio;
import com.desafiotecnico.gruporeferencial.model.LinhaValida;
import com.desafiotecnico.gruporeferencial.model.RankingVeiculo;

class RankingServiceTest {

    private Episodio episodio(String placa, double velocidade) {
        LinhaValida linha = new LinhaValida();
        linha.setPlaca(placa);
        linha.setDataHora(LocalDateTime.of(2026, 10, 5, 8, 0));
        linha.setVelocidade(velocidade);
        linha.setLimiteVelocidade(60);

        Episodio ep = new Episodio();
        ep.setPlaca(placa);
        ep.adicionar(linha);
        return ep;
    }

    @Test
    void maisPontosFicaNaFrente() {
        List<RankingVeiculo> ranking = RankingService.gerar(List.of(
                episodio("AAA1A11", 66), // 1 ponto
                episodio("BBB2B22", 100))); // 5 pontos

        assertThat(ranking).extracting(RankingVeiculo::placa).containsExactly("BBB2B22", "AAA1A11");
    }

    @Test
    void pontosDosEpisodiosDoMesmoVeiculoSomam() {
        List<RankingVeiculo> ranking = RankingService.gerar(List.of(
                episodio("AAA1A11", 66), // 1
                episodio("AAA1A11", 80))); // 3

        assertThat(ranking).hasSize(1);
        assertThat(ranking.get(0).totalPontos()).isEqualTo(4);
    }

    @Test
    void empateEmPontosVenceMaiorVelocidade() {
        List<RankingVeiculo> ranking = RankingService.gerar(List.of(
                episodio("AAA1A11", 80),
                episodio("BBB2B22", 85)));

        assertThat(ranking).extracting(RankingVeiculo::placa).containsExactly("BBB2B22", "AAA1A11");
    }

    @Test
    void empateEmPontosEVelocidadeVenceOrdemAlfabeticaDaPlaca() {
        List<RankingVeiculo> ranking = RankingService.gerar(List.of(
                episodio("BBB2B22", 80),
                episodio("AAA1A11", 80)));

        assertThat(ranking).extracting(RankingVeiculo::placa).containsExactly("AAA1A11", "BBB2B22");
    }

    @Test
    void semEpisodiosORankingFicaVazio() {
        assertThat(RankingService.gerar(List.of())).isEmpty();
    }
}