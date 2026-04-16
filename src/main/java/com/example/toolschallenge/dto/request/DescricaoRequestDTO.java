package com.example.toolschallenge.dto.request;

import jakarta.validation.constraints.NotBlank;

public record DescricaoRequestDTO(
        @NotBlank String valor,
        @NotBlank String dataHora,
        @NotBlank String estabelecimento
) {}