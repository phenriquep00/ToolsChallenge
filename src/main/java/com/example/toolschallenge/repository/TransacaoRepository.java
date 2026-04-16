package com.example.toolschallenge.repository;

import com.example.toolschallenge.model.Transacao;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class TransacaoRepository {

    private final Map<String, Transacao> transacoes = new ConcurrentHashMap<>();

    /**
     * Salva ou atualiza uma transação.
     * O ID deve ser único conforme o requisito[cite: 35].
     */
    public Transacao salvar(Transacao transacao) {
        transacoes.put(transacao.id(), transacao);
        return transacao;
    }

    /**
     * Busca uma transação específica pelo ID[cite: 27, 30].
     */
    public Optional<Transacao> buscarPorId(String id) {
        return Optional.ofNullable(transacoes.get(id));
    }

    /**
     * Retorna todas as transações armazenadas[cite: 30].
     */
    public List<Transacao> buscarTodos() {
        return new ArrayList<>(transacoes.values());
    }
}