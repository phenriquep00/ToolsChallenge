package com.example.toolschallenge.service.strategy;

import com.example.toolschallenge.model.enums.TipoPagamento;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class PagamentoStrategyFactoryTest {

    private PagamentoStrategyFactory factory;

    @BeforeEach
    void setUp() {
        List<PagamentoStrategy> strategies = List.of(
                new PagamentoAvistaStrategy(),
                new PagamentoParceladoLojaStrategy(),
                new PagamentoParceladoEmissorStrategy()
        );
        factory = new PagamentoStrategyFactory(strategies);
    }

    @Test
    @DisplayName("Deve retornar a estratégia de pagamento À Vista corretamente")
    void deveRetornarStrategyAvista() {
        PagamentoStrategy strategy = factory.getStrategy(TipoPagamento.AVISTA);

        assertNotNull(strategy);
        assertTrue(strategy instanceof PagamentoAvistaStrategy);
        assertEquals(TipoPagamento.AVISTA, strategy.getTipoPagamento());
    }

    @Test
    @DisplayName("Deve retornar a estratégia de pagamento Parcelado Loja corretamente")
    void deveRetornarStrategyParceladoLoja() {
        PagamentoStrategy strategy = factory.getStrategy(TipoPagamento.PARCELADO_LOJA);

        assertNotNull(strategy);
        assertTrue(strategy instanceof PagamentoParceladoLojaStrategy);
        assertEquals(TipoPagamento.PARCELADO_LOJA, strategy.getTipoPagamento());
    }

    @Test
    @DisplayName("Deve lançar exceção quando o tipo de pagamento for nulo")
    void deveLancarExcecaoParaTipoNulo() {
        assertThrows(RuntimeException.class, () -> factory.getStrategy(null));
    }
}