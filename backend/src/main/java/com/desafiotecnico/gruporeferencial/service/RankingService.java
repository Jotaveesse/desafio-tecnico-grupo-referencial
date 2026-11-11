package com.desafiotecnico.gruporeferencial.service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.desafiotecnico.gruporeferencial.model.Episodio;
import com.desafiotecnico.gruporeferencial.model.RankingVeiculo;

public class RankingService {

    public static List<RankingVeiculo> gerar(List<Episodio> episodios) {
        Map<String, Integer> pontos = new HashMap<>();
        Map<String, Double> maiorVelocidade = new HashMap<>();

        for (Episodio ep : episodios) {
            String placa = ep.getPlaca();
            pontos.merge(placa, ep.getGravidade().getPontos(), Integer::sum);
            maiorVelocidade.merge(placa, ep.getVelocidadeMaximaKmh(), Math::max);
        }

        List<RankingVeiculo> ranking = new ArrayList<>();

        for (String placa : pontos.keySet()) {
            ranking.add(new RankingVeiculo(placa, pontos.get(placa),
                    maiorVelocidade.get(placa)));
        }

        // orderna pelos pontos mais altos, depois pela maior velocidade, depois pela
        // ordem alfabetica da placa
        ranking.sort(Comparator
                .comparingInt(RankingVeiculo::totalPontos).reversed()
                .thenComparing(Comparator.comparingDouble(RankingVeiculo::maiorVelocidadeKmh).reversed())
                .thenComparing(RankingVeiculo::placa));

        return ranking;
    }
}