package com.example.toolschallenge.service.strategy;

import com.example.toolschallenge.model.enums.StatusTransacao;
import com.example.toolschallenge.model.enums.TipoPagamento;

public interface PagamentoStrategy {

    /**
     * Executa a regra de autorização específica para o tipo de pagamento.
     */
    StatusTransacao processar();

    /**
     * Identifica qual tipo de pagamento esta estratégia atende.
     */
    TipoPagamento getTipoPagamento();
}