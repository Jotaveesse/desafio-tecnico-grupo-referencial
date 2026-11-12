package com.desafiotecnico.gruporeferencial.model;

import java.util.List;

public record AnaliseResposta(
        ResumoResposta resumo,
        List<Episodio> episodios,
        List<RankingVeiculo> ranking,
        List<LinhaInvalida> linhasInvalidas) {
}