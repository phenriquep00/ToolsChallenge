package com.example.toolschallenge.service;

import com.example.toolschallenge.model.Descricao;
import com.example.toolschallenge.model.Transacao;
import com.example.toolschallenge.model.enums.StatusTransacao;
import com.example.toolschallenge.repository.TransacaoRepository;
import com.example.toolschallenge.service.strategy.PagamentoStrategyFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class PagamentoService {

    private final TransacaoRepository repository;
    private final PagamentoStrategyFactory strategyFactory;

    public PagamentoService(TransacaoRepository repository, PagamentoStrategyFactory strategyFactory) {
        this.repository = repository;
        this.strategyFactory = strategyFactory;
    }

    public Transacao realizarPagamento(Transacao transacao) {
        var strategy = strategyFactory.getStrategy(transacao.formaPagamento().tipo());
        StatusTransacao status = strategy.processar();

        Transacao processada = criarTransacaoAutorizada(transacao, status);

        return repository.salvar(processada);
    }

    private Transacao criarTransacaoAutorizada(Transacao t, StatusTransacao status) {
        return new Transacao(
                t.cartao(),
                t.id(),
                new Descricao(
                        t.descricao().valor(),
                        t.descricao().dataHora(),
                        t.descricao().estabelecimento(),
                        gerarNsu(),
                        gerarCodigoAutorizacao(),
                        status
                ),
                t.formaPagamento()
        );
    }

    public Transacao estornar(String id) {
        Transacao transacao = repository.buscarPorId(id)
                .orElseThrow(() -> new RuntimeException("Transação não encontrada com o ID: " + id));

        Transacao estornada = new Transacao(
                transacao.cartao(),
                transacao.id(),
                new Descricao(
                        transacao.descricao().valor(),
                        transacao.descricao().dataHora(),
                        transacao.descricao().estabelecimento(),
                        transacao.descricao().nsu(),
                        transacao.descricao().codigoAutorizacao(),
                        StatusTransacao.CANCELADO
                ),
                transacao.formaPagamento()
        );

        return repository.salvar(estornada);
    }

    public Transacao buscarPorId(String id) {
        return repository.buscarPorId(id)
                .orElseThrow(() -> new RuntimeException("Transação não encontrada com o ID: " + id));
    }

    public List<Transacao> buscarTodos() {
        return repository.buscarTodos();
    }

    private String gerarNsu() {
        return UUID.randomUUID().toString().replaceAll("[^0-9]", "").substring(0, 10);
    }

    private String gerarCodigoAutorizacao() {
        return UUID.randomUUID().toString().replaceAll("[^0-9]", "").substring(0, 9);
    }
}