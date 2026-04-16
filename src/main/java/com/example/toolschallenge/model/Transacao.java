package com.example.toolschallenge.model;

public record Transacao(
        String cartao,
        String id,
        Descricao descricao,
        FormaPagamento formaPagamento
) {}