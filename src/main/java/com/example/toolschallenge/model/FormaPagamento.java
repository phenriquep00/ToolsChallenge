package com.example.toolschallenge.model;

import com.example.toolschallenge.model.enums.TipoPagamento;

public record FormaPagamento(
        TipoPagamento tipo,
        String parcelas
) {}