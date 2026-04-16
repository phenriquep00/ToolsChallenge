package com.example.toolschallenge.dto.response;

import com.example.toolschallenge.model.enums.TipoPagamento;

public record FormaPagamentoResponseDTO(
        TipoPagamento tipo,
        String parcelas
) {}