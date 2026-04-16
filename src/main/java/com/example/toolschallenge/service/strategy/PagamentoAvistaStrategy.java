package com.example.toolschallenge.service.strategy;

import com.example.toolschallenge.model.enums.StatusTransacao;
import com.example.toolschallenge.model.enums.TipoPagamento;
import org.springframework.stereotype.Component;

@Component
public class PagamentoAvistaStrategy implements PagamentoStrategy {
    @Override
    public StatusTransacao processar() {
        return StatusTransacao.AUTORIZADO;
    }

    @Override
    public TipoPagamento getTipoPagamento() {
        return TipoPagamento.AVISTA;
    }
}