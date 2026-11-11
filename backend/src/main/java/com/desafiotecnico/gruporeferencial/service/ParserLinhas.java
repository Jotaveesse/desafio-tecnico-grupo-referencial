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

            try {
                String[] campos = linhaCSV.split(";");

                if (campos.length != 4) {
                    adicionarLinhaInvalida(linhasInvalidas, totalLinhasLidas, "Quantidade de campos inválido");
                    continue;
                }

                LinhaValida linha = new LinhaValida();
                linha.setPlaca(campos[0].toUpperCase().trim());
                linha.setDataHora(LocalDateTime.parse(campos[1]));

                try {
                    linha.setVelocidade(format.parse(campos[2]).doubleValue());
                } catch (ParseException e) {
                    adicionarLinhaInvalida(linhasInvalidas, totalLinhasLidas, "Erro ao parsear velocidade");
                    continue;
                }

                try {
                    linha.setLimiteVelocidade(format.parse(campos[3]).doubleValue());
                } catch (ParseException e) {
                    adicionarLinhaInvalida(linhasInvalidas, totalLinhasLidas, "Erro ao parsear limite de velocidade");
                    continue;
                }

                if (linha.getLimiteVelocidade() <= 0) {
                    adicionarLinhaInvalida(linhasInvalidas, totalLinhasLidas, "Limite de velocidade é inválido");
                    continue;
                }
                if (linha.getVelocidade() < 0) {
                    adicionarLinhaInvalida(linhasInvalidas, totalLinhasLidas, "Velocidade é inválida");
                    continue;
                }

                linhasValidas.add(linha);

            } catch (DateTimeParseException e) {
                adicionarLinhaInvalida(linhasInvalidas, totalLinhasLidas, "Erro ao parsear data e hora");

            } catch (Exception e) {
                adicionarLinhaInvalida(linhasInvalidas, totalLinhasLidas, "Erro desconhecido ao parsear linha");

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
