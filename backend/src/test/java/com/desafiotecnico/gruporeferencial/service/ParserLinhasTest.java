package com.desafiotecnico.gruporeferencial.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDateTime;
import java.util.List;

import org.junit.jupiter.api.Test;

import com.desafiotecnico.gruporeferencial.model.ResultadoParser;

class ParserLinhasTest {

    @Test
    void PlacaEhNormalizada() {
        ResultadoParser r = ParserLinhas.parse(List.of("abc1d23 ;2026-10-05T08:00:00;72;60"));

        assertThat(r.validas()).hasSize(1);
        assertThat(r.validas().get(0).getPlaca()).isEqualTo("ABC1D23");
        assertThat(r.validas().get(0).getDataHora()).isEqualTo(LocalDateTime.of(2026, 10, 5, 8, 0));
        assertThat(r.invalidas()).isEmpty();
    }

    @Test
    void campoFaltandoEhReportadoComONumeroDaLinhaDoArquivo() {
        ResultadoParser r = ParserLinhas.parse(List.of(
                "ABC1D23;2026-10-05T08:00:00;72;60",
                "ABC1D23;2026-10-05T08:01:00;72"));

        assertThat(r.validas()).hasSize(1);
        assertThat(r.invalidas()).hasSize(1);
        assertThat(r.invalidas().get(0).getNumeroLinha()).isEqualTo(3); // cabeçalho = 1
    }

    @Test
    void dataInvalidaEVelocidadeNaoNumericaSaoRejeitadas() {
        ResultadoParser r = ParserLinhas.parse(List.of(
                "ABC1D23;ontem;72;60",
                "ABC1D23;2026-10-05T08:00:00;abc;60"));

        assertThat(r.validas()).isEmpty();
        assertThat(r.invalidas()).hasSize(2);
    }

    @Test
    void velocidadeNegativaEhInvalidaMasZeroEhValida() {
        ResultadoParser r = ParserLinhas.parse(List.of(
                "ABC1D23;2026-10-05T08:00:00;-1;60",
                "ABC1D23;2026-10-05T08:01:00;0;60"));

        assertThat(r.invalidas()).hasSize(1);
        assertThat(r.invalidas().get(0).getNumeroLinha()).isEqualTo(2);
        assertThat(r.validas()).hasSize(1);
    }

    @Test
    void linhasLidasIgualValidasMaisInvalidas() {
        ResultadoParser r = ParserLinhas.parse(List.of(
                "ABC1D23;2026-10-05T08:00:00;72;60",
                "lasdlj aldjasdja",
                "ABC1D23;2026-10-05T08:02:00;80;60"));

        assertThat(r.totalLinhasLidas())
                .isEqualTo(r.validas().size() + r.invalidas().size());
    }
}