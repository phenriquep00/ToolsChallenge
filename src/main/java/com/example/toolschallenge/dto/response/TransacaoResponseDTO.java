package com.example.toolschallenge.dto.response;

public record TransacaoResponseDTO(
        String cartao,
        String id,
        DescricaoResponseDTO descricao,
        FormaPagamentoResponseDTO formaPagamento
) {}
