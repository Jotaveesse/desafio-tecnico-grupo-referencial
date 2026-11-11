package com.desafiotecnico.gruporeferencial.model;

import java.util.List;

public record ResultadoParser(List<LinhaValida> validas, List<LinhaInvalida> invalidas, int totalLinhasLidas) {
}
