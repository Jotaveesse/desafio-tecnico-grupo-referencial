package com.desafiotecnico.gruporeferencial.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import com.desafiotecnico.gruporeferencial.model.Episodio;
import com.desafiotecnico.gruporeferencial.model.Gravidade;
import com.desafiotecnico.gruporeferencial.model.LinhaValida;

class DetectorEpisodiosTest {

    private static final LocalDateTime BASE = LocalDateTime.of(2026, 10, 5, 8, 0, 0);

    private LinhaValida linhaCSV(String placa, LocalDateTime dataHora, double velocidade, double limite) {
        LinhaValida linha = new LinhaValida();
        linha.setPlaca(placa);
        linha.setDataHora(dataHora);
        linha.setVelocidade(velocidade);
        linha.setLimiteVelocidade(limite);
        return linha;
    }

    @Test
    void exatamente5kmhAcimaDoLimiteNaoEhExcesso() {
        List<Episodio> eps = DetectorEpisodios.detectar(List.of(
                linhaCSV("ABC1D23", BASE, 65, 60)));

        assertThat(eps).isEmpty();
    }

    @Test
    void maisDe5kmhAcimaDoLimiteEhExcesso() {
        List<Episodio> eps = DetectorEpisodios.detectar(List.of(
                linhaCSV("ABC1D23", BASE, 66, 60)));

        assertThat(eps).hasSize(1);
    }

    @Test
    void intervaloDeExatamente5MinutosNaoEncerraEpisodio() {
        List<Episodio> eps = DetectorEpisodios.detectar(List.of(
                linhaCSV("ABC1D23", BASE, 80, 60),
                linhaCSV("ABC1D23", BASE.plusMinutes(5), 80, 60)));

        assertThat(eps).hasSize(1);
        assertThat(eps.get(0).getDuracaoSegundos()).isEqualTo(300);
    }

    @Test
    void intervaloMaiorQue5MinutosEncerraEpisodio() {
        List<Episodio> eps = DetectorEpisodios.detectar(List.of(
                linhaCSV("ABC1D23", BASE, 80, 60),
                linhaCSV("ABC1D23", BASE.plusMinutes(5).plusSeconds(1), 80, 60)));

        assertThat(eps).hasSize(2);
    }

    @Test
    void leituraSemExcessoSeparaDoisEpisodios() {
        List<Episodio> eps = DetectorEpisodios.detectar(List.of(
                linhaCSV("ABC1D23", BASE, 80, 60),
                linhaCSV("ABC1D23", BASE.plusMinutes(1), 60, 60),
                linhaCSV("ABC1D23", BASE.plusMinutes(2), 80, 60)));

        assertThat(eps).hasSize(2);
    }

    @Test
    void arquivoForaDeOrdemDaMesmoResultadoQueOrdenado() {
        List<LinhaValida> foraDeOrdem = new ArrayList<>(List.of(
                linhaCSV("ABC1D23", BASE.plusMinutes(2), 80, 60),
                linhaCSV("ABC1D23", BASE, 80, 60),
                linhaCSV("ABC1D23", BASE.plusMinutes(1), 80, 60)));

        List<Episodio> eps = DetectorEpisodios.detectar(foraDeOrdem);

        assertThat(eps).hasSize(1);
        assertThat(eps.get(0).getInicio()).isEqualTo(BASE);
        assertThat(eps.get(0).getFim()).isEqualTo(BASE.plusMinutes(2));
        assertThat(eps.get(0).getDuracaoSegundos()).isEqualTo(120);
    }

    @Test
    void velocidadeMaximaEExcessoMaximoPodemVirDeLeiturasDiferentes() {
        List<Episodio> eps = DetectorEpisodios.detectar(new ArrayList<>(List.of(
                linhaCSV("ABC1D23", BASE, 100, 90),
                linhaCSV("ABC1D23", BASE.plusMinutes(1), 70, 50))));

        assertThat(eps).hasSize(1);
        assertThat(eps.get(0).getVelocidadeMaximaKmh()).isEqualTo(100);
        assertThat(eps.get(0).getExcessoMaximoPercentual()).isEqualTo(40.0);
        assertThat(eps.get(0).getGravidade()).isEqualTo(Gravidade.GRAVE);
    }

    @Test
    void veiculosIntercaladosNaoSeMisturam() {
        List<Episodio> eps = DetectorEpisodios.detectar(new ArrayList<>(List.of(
                linhaCSV("AAA1A11", BASE, 80, 60),
                linhaCSV("BBB2B22", BASE.plusMinutes(1), 80, 60),
                linhaCSV("AAA1A11", BASE.plusMinutes(2), 80, 60))));

        assertThat(eps).hasSize(2);
        assertThat(eps).extracting(Episodio::getPlaca).containsExactlyInAnyOrder("AAA1A11", "BBB2B22");
    }

    @Test
    void gravidadeNosLimitesDasFaixas() {
        assertThat(Gravidade.fromExcesso(20.0)).isEqualTo(Gravidade.LEVE);
        assertThat(Gravidade.fromExcesso(20.1)).isEqualTo(Gravidade.GRAVE);
        assertThat(Gravidade.fromExcesso(50.0)).isEqualTo(Gravidade.GRAVE);
        assertThat(Gravidade.fromExcesso(50.1)).isEqualTo(Gravidade.GRAVISSIMA);
    }
}