package com.example.toolschallenge.service;

import com.example.toolschallenge.model.*;
import com.example.toolschallenge.model.enums.StatusTransacao;
import com.example.toolschallenge.model.enums.TipoPagamento;
import com.example.toolschallenge.repository.TransacaoRepository;
import com.example.toolschallenge.service.strategy.PagamentoStrategy;
import com.example.toolschallenge.service.strategy.PagamentoStrategyFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PagamentoServiceTest {

    @Mock
    private TransacaoRepository repository;

    @Mock
    private PagamentoStrategyFactory strategyFactory;

    @Mock
    private PagamentoStrategy strategy;

    @InjectMocks
    private PagamentoService service;

    private Transacao transacaoExemplo;

    @BeforeEach
    void setUp() {
        transacaoExemplo = new Transacao(
                "4444********1234",
                "1001",
                new Descricao("100.00", LocalDateTime.now(), "Loja Teste", null, null, null),
                new FormaPagamento(TipoPagamento.AVISTA, "1")
        );
    }

    @Test
    @DisplayName("Deve realizar pagamento com sucesso, gerando NSU e Código de Autorização")
    void deveRealizarPagamentoComSucesso() {
        when(strategyFactory.getStrategy(any())).thenReturn(strategy);
        when(strategy.processar()).thenReturn(StatusTransacao.AUTORIZADO);
        when(repository.salvar(any())).thenAnswer(invocation -> invocation.getArgument(0));


        Transacao resultado = service.realizarPagamento(transacaoExemplo);


        assertAll("Validando campos gerados no processamento",
                () -> assertNotNull(resultado.descricao().nsu(), "NSU não deve ser nulo"),
                () -> assertNotNull(resultado.descricao().codigoAutorizacao(), "Código de autorização não deve ser nulo"),
                () -> assertEquals(StatusTransacao.AUTORIZADO, resultado.descricao().status(), "Status deve ser AUTORIZADO")
        );
        verify(repository, times(1)).salvar(any());
        verify(strategyFactory, times(1)).getStrategy(any());
    }

    @Test
    @DisplayName("Deve realizar o estorno de uma transação existente")
    void deveRealizarEstornoComSucesso() {
        String id = "1001";
        Transacao transacaoOriginal = new Transacao(
                transacaoExemplo.cartao(),
                id,
                new Descricao("100.00", LocalDateTime.now(), "Loja Teste", "NSU123", "COD456", StatusTransacao.AUTORIZADO),
                transacaoExemplo.formaPagamento()
        );

        when(repository.buscarPorId(id)).thenReturn(Optional.of(transacaoOriginal));
        when(repository.salvar(any())).thenAnswer(invocation -> invocation.getArgument(0));


        Transacao resultado = service.estornar(id);


        assertEquals(StatusTransacao.CANCELADO, resultado.descricao().status(), "O status após estorno deve ser CANCELADO");
        verify(repository, times(1)).salvar(any());
    }

    @Test
    @DisplayName("Deve lançar exceção ao tentar buscar ou estornar ID inexistente")
    void deveLancarExcecaoAoBuscarIdInexistente() {

        when(repository.buscarPorId("999")).thenReturn(Optional.empty());


        RuntimeException exception = assertThrows(RuntimeException.class, () -> service.buscarPorId("999"));
        assertTrue(exception.getMessage().contains("Transação não encontrada"));
    }
}