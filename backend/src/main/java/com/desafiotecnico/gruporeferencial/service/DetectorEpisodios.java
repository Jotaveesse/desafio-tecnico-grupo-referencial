package com.desafiotecnico.gruporeferencial.service;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import com.desafiotecnico.gruporeferencial.model.Episodio;
import com.desafiotecnico.gruporeferencial.model.LinhaValida;

public class DetectorEpisodios {

    private static final double MARGEM_KMH = 5;
    private static final long GAP_MAXIMO_SEGUNDOS = 5 * 60;

    public static List<Episodio> detectar(List<LinhaValida> linhasValidas) {

        // agrupa linha por placa
        Map<String, List<LinhaValida>> porPlaca = new TreeMap<>();

        for (LinhaValida linhaValida : linhasValidas) {
            if (porPlaca.containsKey(linhaValida.getPlaca())) {
                porPlaca.get(linhaValida.getPlaca()).add(linhaValida);
            } else {
                List<LinhaValida> lista = new ArrayList<>();
                lista.add(linhaValida);
                porPlaca.put(linhaValida.getPlaca(), lista);
            }
        }

        List<Episodio> episodios = new ArrayList<>();
        for (Map.Entry<String, List<LinhaValida>> entrada : porPlaca.entrySet()) {
            List<LinhaValida> linhasVeiculo = entrada.getValue();
            String placa = entrada.getKey();

            // ordena por data e hora
            linhasVeiculo.sort(Comparator.comparing(LinhaValida::getDataHora));
            episodios.addAll(detectarEpisodiosDoVeiculo(placa, linhasVeiculo));
        }
        return episodios;
    }

    private static List<Episodio> detectarEpisodiosDoVeiculo(String placa, List<LinhaValida> linhasVeiculo) {
        List<Episodio> resultado = new ArrayList<>();
        Episodio episodioAtual = null;

        for (LinhaValida linha : linhasVeiculo) {
            // leitura sem excesso encerra o episodio se houver um em andamento
            if (!emExcesso(linha)) {
                if (episodioAtual != null) {
                    resultado.add(episodioAtual);
                    episodioAtual = null;
                }
                continue;
            }

            // se for o primeiro excesso, cria um novo episodio
            if (episodioAtual == null) {
                episodioAtual = new Episodio();
                episodioAtual.setPlaca(placa);
                episodioAtual.adicionar(linha);

            } else {
                // intervalo maior que 5 minutos encerra o episodio e começa outro
                long intervalo = Duration.between(episodioAtual.getFim(), linha.getDataHora()).getSeconds();

                if (intervalo <= GAP_MAXIMO_SEGUNDOS) {
                    episodioAtual.adicionar(linha);

                } else {
                    resultado.add(episodioAtual);
                    episodioAtual = new Episodio();
                    episodioAtual.setPlaca(placa);
                    episodioAtual.adicionar(linha);
                }
            }
        }

        // o arquivo acabou com um episodio ainda aberto
        if (episodioAtual != null) {
            resultado.add(episodioAtual);
        }

        return resultado;
    }

    private static boolean emExcesso(LinhaValida linha) {
        return linha.getVelocidade() > linha.getLimiteVelocidade() + MARGEM_KMH;
    }

}