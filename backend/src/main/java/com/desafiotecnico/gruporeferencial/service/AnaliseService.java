package com.desafiotecnico.gruporeferencial.service;

import java.util.List;

import com.desafiotecnico.gruporeferencial.model.AnaliseResposta;
import com.desafiotecnico.gruporeferencial.model.Episodio;
import com.desafiotecnico.gruporeferencial.model.LinhaInvalida;
import com.desafiotecnico.gruporeferencial.model.LinhaValida;
import com.desafiotecnico.gruporeferencial.model.RankingVeiculo;
import com.desafiotecnico.gruporeferencial.model.ResultadoParser;
import com.desafiotecnico.gruporeferencial.model.ResumoResposta;

public class AnaliseService {
    public static AnaliseResposta analisar(List<String> linhas) {
        List<String> linhasConteudo = linhas.subList(1, linhas.size());

        ResultadoParser resultadoParser = ParserLinhas.parse(linhasConteudo);

        List<LinhaValida> linhasValidas = resultadoParser.validas();
        List<LinhaInvalida> linhasInvalidas = resultadoParser.invalidas();

        List<Episodio> episodios = DetectorEpisodios.detectar(linhasValidas);

        List<RankingVeiculo> ranking = RankingService.gerar(episodios);

        ResumoResposta resumo = new ResumoResposta(linhas.size() - 1, linhasValidas.size(), episodios.size());

        return new AnaliseResposta(resumo, episodios, ranking, linhasInvalidas);
    }
}
