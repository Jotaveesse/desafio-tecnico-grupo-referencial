package com.desafiotecnico.gruporeferencial.model;

import java.time.Duration;
import java.time.LocalDateTime;

public class Episodio {
    private String placa;
    private LocalDateTime inicio;
    private LocalDateTime fim;
    private double velocidadeMaximaKmh;
    private double excessoMaximoPercentual;
    private Gravidade gravidade;

    public String getPlaca() {
        return placa;
    }

    public void setPlaca(String placa) {
        this.placa = placa;
    }

    public LocalDateTime getInicio() {
        return inicio;
    }

    private void setInicio(LocalDateTime inicio) {
        this.inicio = inicio;
    }

    public LocalDateTime getFim() {
        return fim;
    }

    private void setFim(LocalDateTime fim) {
        this.fim = fim;
    }

    public long getDuracaoSegundos() {
        return Duration.between(inicio, fim).getSeconds();
    }

    public double getVelocidadeMaximaKmh() {
        return velocidadeMaximaKmh;
    }

    private void setVelocidadeMaximaKmh(double velocidadeMaximaKmh) {
        this.velocidadeMaximaKmh = velocidadeMaximaKmh;
    }

    public double getExcessoMaximoPercentual() {
        return excessoMaximoPercentual;
    }

    public Gravidade getGravidade() {
        return gravidade;
    }

    private void setGravidade(Gravidade doExcesso) {
        this.gravidade = doExcesso;
    }

    public void adicionar(LinhaValida linha) {
        double excessoPercentual = getExcessoPercentual(linha);

        if (excessoPercentual > excessoMaximoPercentual) {
            excessoMaximoPercentual = excessoPercentual;
            setGravidade(Gravidade.fromExcesso(excessoMaximoPercentual));
        }

        if (velocidadeMaximaKmh < linha.getVelocidade()) {
            setVelocidadeMaximaKmh(linha.getVelocidade());
        }

        if (inicio == null || linha.getDataHora().isBefore(inicio)) {
            setInicio(linha.getDataHora());
        }

        if (fim == null || linha.getDataHora().isAfter(fim)) {
            setFim(linha.getDataHora());
        }
    }

    private double getExcessoPercentual(LinhaValida linha) {
        double percentual = (linha.getVelocidade() - linha.getLimiteVelocidade())
                / linha.getLimiteVelocidade() * 100;
        return Math.round(percentual * 10) / 10.0;
    }

}
