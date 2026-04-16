package com.example.toolschallenge.dto.mapper;

import com.example.toolschallenge.dto.request.PaymentRequestDTO;
import com.example.toolschallenge.dto.response.*;
import com.example.toolschallenge.model.*;
import com.example.toolschallenge.model.enums.StatusTransacao;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Component
public class TransacaoMapper {

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");

    public Transacao toModel(PaymentRequestDTO request) {
        var transacaoReq = request.transacao();
        var descReq = transacaoReq.descricao();
        var formaReq = transacaoReq.formaPagamento();

        return new Transacao(
                transacaoReq.cartao(),
                transacaoReq.id(),
                new Descricao(
                        descReq.valor(),
                        LocalDateTime.parse(descReq.dataHora(), FORMATTER),
                        descReq.estabelecimento(),
                        null,
                        null,
                        StatusTransacao.AUTORIZADO
                ),
                new FormaPagamento(
                        formaReq.tipo(),
                        formaReq.parcelas()
                )
        );
    }

    public PaymentResponseDTO toResponse(Transacao model) {
        var desc = model.descricao();

        return new PaymentResponseDTO(
                new TransacaoResponseDTO(
                        model.cartao(),
                        model.id(),
                        new DescricaoResponseDTO(
                                desc.valor(),
                                desc.dataHora().format(FORMATTER),
                                desc.estabelecimento(),
                                desc.nsu(),
                                desc.codigoAutorizacao(),
                                desc.status()
                        ),
                        new FormaPagamentoResponseDTO(
                                model.formaPagamento().tipo(),
                                model.formaPagamento().parcelas()
                        )
                )
        );
    }
}