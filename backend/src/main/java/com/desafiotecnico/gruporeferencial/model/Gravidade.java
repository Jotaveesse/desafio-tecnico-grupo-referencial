package com.desafiotecnico.gruporeferencial.model;

public enum Gravidade {
    LEVE(1),
    GRAVE(3),
    GRAVISSIMA(5);

    private final int pontos;

    Gravidade(int pontos) {
        this.pontos = pontos;
    }

    public int getPontos() {
        return pontos;
    }

    public static Gravidade fromExcesso(double excessoPercentual) {
        if (excessoPercentual > 50)
            return GRAVISSIMA;
        if (excessoPercentual > 20)
            return GRAVE;

        return LEVE;
    }
}