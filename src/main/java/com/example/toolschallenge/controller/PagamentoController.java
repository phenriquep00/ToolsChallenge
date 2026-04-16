package com.example.toolschallenge.controller;

import com.example.toolschallenge.dto.mapper.TransacaoMapper;
import com.example.toolschallenge.dto.request.PaymentRequestDTO;
import com.example.toolschallenge.dto.response.PaymentResponseDTO;
import com.example.toolschallenge.service.PagamentoService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/pagamentos")
public class PagamentoController {

    private final PagamentoService service;
    private final TransacaoMapper mapper;

    public PagamentoController(PagamentoService service, TransacaoMapper mapper) {
        this.service = service;
        this.mapper = mapper;
    }

    @PostMapping
    public ResponseEntity<PaymentResponseDTO> realizarPagamento(@RequestBody @Valid PaymentRequestDTO request) {
        var transacaoParaProcessar = mapper.toModel(request);

        var transacaoFinalizada = service.realizarPagamento(transacaoParaProcessar);

        return ResponseEntity.status(HttpStatus.CREATED).body(mapper.toResponse(transacaoFinalizada));
    }

    @PostMapping("/{id}/estorno")
    public ResponseEntity<PaymentResponseDTO> estornar(@PathVariable String id) {
        var estornada = service.estornar(id);
        return ResponseEntity.ok(mapper.toResponse(estornada));
    }

    @GetMapping("/{id}")
    public ResponseEntity<PaymentResponseDTO> buscarPorId(@PathVariable String id) {
        var transacao = service.buscarPorId(id);

        return ResponseEntity.ok(mapper.toResponse(transacao));
    }

    @GetMapping
    public ResponseEntity<List<PaymentResponseDTO>> buscarTodos() {
        var todos = service.buscarTodos().stream()
                .map(mapper::toResponse)
                .toList();
        return ResponseEntity.ok(todos);
    }
}