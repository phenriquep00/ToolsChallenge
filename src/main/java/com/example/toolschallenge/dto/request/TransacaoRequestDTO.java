package com.example.toolschallenge.dto.request;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record TransacaoRequestDTO(
        @NotBlank String cartao,
        @NotBlank String id,
        @Valid @NotNull DescricaoRequestDTO descricao,
        @Valid @NotNull FormaPagamentoRequestDTO formaPagamento
) {}