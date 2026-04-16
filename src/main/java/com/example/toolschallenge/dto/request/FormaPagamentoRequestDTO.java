package com.example.toolschallenge.dto.request;

import com.example.toolschallenge.model.enums.TipoPagamento;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record FormaPagamentoRequestDTO(
        @NotNull TipoPagamento tipo,
        @NotBlank String parcelas
) {}
