package com.desafiotecnico.gruporeferencial.model;

import java.time.LocalDateTime;

public class LinhaValida {
    String placa;
    LocalDateTime dataHora;
    double velocidade;
    double limiteVelocidade;

    public String getPlaca() {
        return placa;
    }

    public void setPlaca(String placa) {
        this.placa = placa;
    }

    public LocalDateTime getDataHora() {
        return dataHora;
    }

    public void setDataHora(LocalDateTime dataHora) {
        this.dataHora = dataHora;
    }

    public double getVelocidade() {
        return velocidade;
    }

    public void setVelocidade(double velocidade) {
        this.velocidade = velocidade;
    }

    public double getLimiteVelocidade() {
        return limiteVelocidade;
    }

    public void setLimiteVelocidade(double limiteVelocidade) {
        this.limiteVelocidade = limiteVelocidade;
    }

}
