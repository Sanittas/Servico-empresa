package br.com.sanittas.app.pagamento.controller;

import br.com.sanittas.app.pagamento.model.Pagamento;
import br.com.sanittas.app.pagamento.services.PagamentoServices;
import br.com.sanittas.app.pagamento.services.dto.PagamentoReponse;
import br.com.sanittas.app.pagamento.services.dto.PagamentoRequest;
import br.com.sanittas.app.pagamento.services.dto.SalvarPagamentoDto;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@AllArgsConstructor
@Slf4j
@RequestMapping("/pagamentos")
@RestController
public class PagamentoController {
    private PagamentoServices pagamentoService;

    @PostMapping("/criar-pagamento")
    public ResponseEntity<PagamentoReponse> criarPagamento(@RequestBody @Valid PagamentoRequest pagamentoRequest) {
        try {
            log.info("Recebida solicitação de criação de pagamento");
            var response = pagamentoService.criarPagamento(pagamentoRequest);
            return ResponseEntity.status(201).body(response);
        } catch (Exception e) {
            log.error("Erro ao criar pagamento: {}", e.getMessage());
            throw new RuntimeException(e.getLocalizedMessage());
        }
    }

    @PostMapping("/salvar-pagamento")
    public ResponseEntity<Pagamento> salvarPagamento(@RequestBody @Valid SalvarPagamentoDto pagamento) {
        try {
            log.info("Recebida solicitação de salvar pagamento");
            log.info("Pagamento: {}", pagamento.toString());
            var response = pagamentoService.salvarPagamento(pagamento);
            return ResponseEntity.status(201).body(response);
        } catch (Exception e) {
            log.error("Erro ao salvar pagamento: {}", e.getMessage());
            throw new RuntimeException(e.getLocalizedMessage());
        }
    }


}
