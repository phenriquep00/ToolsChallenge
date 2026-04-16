package com.example.toolschallenge.dto.request;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

public record PaymentRequestDTO(
        @Valid @NotNull TransacaoRequestDTO transacao
) {}