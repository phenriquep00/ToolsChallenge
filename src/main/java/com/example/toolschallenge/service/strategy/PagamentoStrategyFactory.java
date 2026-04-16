package com.example.toolschallenge.service.strategy;

import com.example.toolschallenge.model.enums.TipoPagamento;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class PagamentoStrategyFactory {
    private final List<PagamentoStrategy> strategies;

    public PagamentoStrategyFactory(List<PagamentoStrategy> strategies) {
        this.strategies = strategies;
    }

    public PagamentoStrategy getStrategy(TipoPagamento tipo) {
        return strategies.stream()
                .filter(s -> s.getTipoPagamento().equals(tipo))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Estratégia não encontrada para o tipo: " + tipo));
    }
}