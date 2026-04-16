package com.example.toolschallenge.dto.response;

import com.example.toolschallenge.model.enums.StatusTransacao;

public record DescricaoResponseDTO(
        String valor,
        String dataHora,
        String estabelecimento,
        String nsu,
        String codigoAutorizacao,
        StatusTransacao status
) {}
