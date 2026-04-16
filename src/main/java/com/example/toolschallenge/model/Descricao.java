package com.example.toolschallenge.model;

import com.example.toolschallenge.model.enums.StatusTransacao;
import java.time.LocalDateTime;

public record Descricao(
        String valor,
        LocalDateTime dataHora,
        String estabelecimento,
        String nsu,
        String codigoAutorizacao,
        StatusTransacao status
) {}