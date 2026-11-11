package com.desafiotecnico.gruporeferencial.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/analises")
@CrossOrigin(origins = "http://localhost:4200")
public class AnaliseController {

    @PostMapping
    public ResponseEntity<?> analisar(@RequestParam("arquivoCSV") MultipartFile arquivo) throws IOException {
        if (arquivo.isEmpty()) {
            return ResponseEntity.badRequest().body(Map.of("mensagem", "Arquivo vazio."));
        }

        List<String> linhas = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(arquivo.getInputStream(), StandardCharsets.UTF_8))) {
            String linha;
            while ((linha = reader.readLine()) != null) {
                linhas.add(linha);
            }
        }

        return ResponseEntity.ok(Map.of(
                "nomeArquivo", arquivo.getOriginalFilename(),
                "totalLinhas", linhas.size(),
                "cabecalho", linhas.get(0)));
    }
}