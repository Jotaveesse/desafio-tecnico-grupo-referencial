package com.desafiotecnico.gruporeferencial.service;

import java.text.NumberFormat;
import java.text.ParseException;
import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import com.desafiotecnico.gruporeferencial.model.LinhaValida;
import com.desafiotecnico.gruporeferencial.model.LinhaInvalida;
import com.desafiotecnico.gruporeferencial.model.ResultadoParser;

public class ParserLinhas {
    public static ResultadoParser parse(List<String> linhasCSV) {
        NumberFormat format = NumberFormat.getInstance(Locale.of("pt", "BR"));

        List<LinhaValida> linhasValidas = new ArrayList<>();
        List<LinhaInvalida> linhasInvalidas = new ArrayList<>();
        int totalLinhasLidas = 0;

        for (String linhaCSV : linhasCSV) {
            totalLinhasLidas++;

            if (linhaCSV.trim().isEmpty()) {
                adicionarLinhaInvalida(linhasInvalidas, totalLinhasLidas, "Linha vazia");
                continue;
            }

            String[] campos = linhaCSV.split(";");

            try {
                if (campos.length != 4) {
                    adicionarLinhaInvalida(linhasInvalidas, totalLinhasLidas, "Quantidade de campos inválida");
                    continue;
                }

                LinhaValida linha = new LinhaValida();
                linha.setPlaca(campos[0].toUpperCase().trim());
                linha.setDataHora(LocalDateTime.parse(campos[1]));

                try {
                    linha.setVelocidade(format.parse(campos[2]).doubleValue());
                } catch (ParseException e) {
                    adicionarLinhaInvalida(linhasInvalidas, totalLinhasLidas,
                            "Erro ao extrair velocidade: '" + campos[2] + "'");
                    continue;
                }

                try {
                    linha.setLimiteVelocidade(format.parse(campos[3]).doubleValue());
                } catch (ParseException e) {
                    adicionarLinhaInvalida(linhasInvalidas, totalLinhasLidas,
                            "Erro ao extrair limite de velocidade: '" + campos[3] + "'");
                    continue;
                }

                if (linha.getLimiteVelocidade() <= 0) {
                    adicionarLinhaInvalida(linhasInvalidas, totalLinhasLidas,
                            "Limite de velocidade inválido: '" + linha.getLimiteVelocidade() + "'");
                    continue;
                }
                if (linha.getVelocidade() < 0) {
                    adicionarLinhaInvalida(linhasInvalidas, totalLinhasLidas,
                            "Velocidade inválida: '" + linha.getVelocidade() + "'");
                    continue;
                }

                linhasValidas.add(linha);

            } catch (DateTimeParseException e) {
                adicionarLinhaInvalida(linhasInvalidas, totalLinhasLidas,
                        "Erro ao extrair data e hora: '" + campos[1] + "'");

            } catch (Exception e) {
                adicionarLinhaInvalida(linhasInvalidas, totalLinhasLidas, "Erro desconhecido ao extrair linha:");
            }
        }

        ResultadoParser resultado = new ResultadoParser(linhasValidas, linhasInvalidas, totalLinhasLidas);

        return resultado;
    }

    private static void adicionarLinhaInvalida(List<LinhaInvalida> linhasInvalidas, int numeroLinha, String motivo) {
        LinhaInvalida linhaInvalida = new LinhaInvalida();
        linhaInvalida.setNumeroLinha(numeroLinha + 1); // +1 porque considera o cabeçalho
        linhaInvalida.setMotivo(motivo);
        linhasInvalidas.add(linhaInvalida);
    }
}
