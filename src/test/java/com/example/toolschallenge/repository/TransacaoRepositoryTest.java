package com.example.toolschallenge.repository;

import com.example.toolschallenge.model.Descricao;
import com.example.toolschallenge.model.Transacao;
import com.example.toolschallenge.model.FormaPagamento;
import com.example.toolschallenge.model.enums.TipoPagamento;
import com.example.toolschallenge.model.enums.StatusTransacao;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class TransacaoRepositoryTest {

    private TransacaoRepository repository;

    @BeforeEach
    void setUp() {
        repository = new TransacaoRepository();
    }

    @Test
    @DisplayName("Deve salvar uma transação e permitir sua recuperação por ID")
    void deveSalvarERecuperarTransacao() {
        String id = "TX-123";
        Transacao transacao = criarTransacaoMock(id);

        repository.salvar(transacao);
        Optional<Transacao> resultado = repository.buscarPorId(id);

        assertTrue(resultado.isPresent(), "A transação deveria ter sido encontrada");
        assertEquals(id, resultado.get().id());
        assertEquals("Loja Teste", resultado.get().descricao().estabelecimento());
    }

    @Test
    @DisplayName("Deve retornar vazio ao buscar um ID que não existe")
    void deveRetornarVazioParaIdInexistente() {
        Optional<Transacao> resultado = repository.buscarPorId("NON-EXISTENT");

        assertTrue(resultado.isEmpty());
    }

    @Test
    @DisplayName("Deve listar todas as transações salvas no repositório")
    void deveListarTodasAsTransacoes() {
        repository.salvar(criarTransacaoMock("ID1"));
        repository.salvar(criarTransacaoMock("ID2"));

        List<Transacao> todas = repository.buscarTodos();

        assertEquals(2, todas.size(), "Deveria haver exatamente 2 transações no repositório");
    }

    private Transacao criarTransacaoMock(String id) {
        return new Transacao(
                "4444********1111",
                id,
                new Descricao("50.00", LocalDateTime.now(), "Loja Teste", "NSU", "AUTH", StatusTransacao.AUTORIZADO),
                new FormaPagamento(TipoPagamento.AVISTA, "1")
        );
    }
}