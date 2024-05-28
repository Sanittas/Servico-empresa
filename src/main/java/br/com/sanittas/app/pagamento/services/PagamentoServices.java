package br.com.sanittas.app.pagamento.services;

import br.com.sanittas.app.pagamento.model.Pagamento;
import br.com.sanittas.app.pagamento.repository.PagamentoRepository;
import br.com.sanittas.app.pagamento.services.dto.PagamentoReponse;
import br.com.sanittas.app.pagamento.services.dto.PagamentoRequest;
import br.com.sanittas.app.pagamento.services.dto.SalvarPagamentoDto;
import br.com.sanittas.app.servicos.model.AgendamentoServico;
import br.com.sanittas.app.servicos.repository.AgendamentoRepository;
import com.mercadopago.MercadoPagoConfig;
import com.mercadopago.client.common.IdentificationRequest;
import com.mercadopago.client.payment.PaymentClient;
import com.mercadopago.client.payment.PaymentCreateRequest;
import com.mercadopago.client.payment.PaymentPayerRequest;
import com.mercadopago.core.MPRequestOptions;
import com.mercadopago.exceptions.MPApiException;
import com.mercadopago.exceptions.MPException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.HashMap;
import java.util.Map;

@Service
@Slf4j
@AllArgsConstructor
public class PagamentoServices {
    private final PagamentoRepository repository;
    private final AgendamentoRepository agendamentoRepository;

    public PagamentoReponse criarPagamento(PagamentoRequest pagamentoRequest) throws MPException, MPApiException {

        MercadoPagoConfig.
                setAccessToken(
                        "APP_USR-1461285240365389-041212-a6b30b0222df26656b3536d338ec3dc9-825045004"
                );

        Map<String, String> customHeaders = new HashMap<>();
        customHeaders.put("x-idempotency-key", "0d5020ed-1af6-469c-ae06-c3bec19954bb");

        MPRequestOptions requestOptions = MPRequestOptions.builder()
                .customHeaders(customHeaders)
                .build();

        PaymentClient client = new PaymentClient();

        PaymentCreateRequest paymentCreateRequest =
                PaymentCreateRequest.builder()
                        .transactionAmount(BigDecimal.valueOf(pagamentoRequest.getValor()))
                        .description(pagamentoRequest.getNomeProduto())
                        .paymentMethodId("pix")
                        .dateOfExpiration(OffsetDateTime.now(ZoneOffset.UTC).plusMinutes(15))
                        .payer(
                                PaymentPayerRequest.builder()
                                        .email(pagamentoRequest.getEmail())
                                        .firstName(pagamentoRequest.getNome())
                                        .identification(
                                                IdentificationRequest.builder().type("CPF").number(pagamentoRequest.getCpf()).build())
                                        .build())
                        .build();

        var response = client.create(paymentCreateRequest, requestOptions);
        return new PagamentoReponse(response.getId(), response.getStatus(), response.getPointOfInteraction().getTransactionData().getQrCodeBase64(), response.getPointOfInteraction().getTransactionData().getTicketUrl(), response.getTransactionAmount());
    }


    public Pagamento salvarPagamento(SalvarPagamentoDto pagamento) {
        AgendamentoServico agendamento =
                agendamentoRepository.findById(pagamento.idAgendamento()).orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Agendamento não encontrado"));
        Pagamento novoPagamento = Pagamento.builder()
                .dataPagamento(pagamento.dataPagamento())
                .valor(pagamento.valor())
                .build();
        agendamento.setPagamento(novoPagamento);
        repository.save(novoPagamento);
        agendamentoRepository.save(agendamento);
        return novoPagamento;
    }
}
