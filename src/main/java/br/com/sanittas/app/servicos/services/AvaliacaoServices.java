package br.com.sanittas.app.servicos.services;

import br.com.sanittas.app.servicos.model.AgendamentoServico;
import br.com.sanittas.app.servicos.model.Avaliacao;
import br.com.sanittas.app.servicos.repository.AgendamentoRepository;
import br.com.sanittas.app.servicos.repository.AvaliacaoRepository;
import br.com.sanittas.app.servicos.services.dto.AvaliacaoDto;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
@AllArgsConstructor
public class AvaliacaoServices {
    private final AvaliacaoRepository repository;
    private final AgendamentoRepository agendamentoRepository;


    public Avaliacao salvar(AvaliacaoDto avaliacaoDto) {
        AgendamentoServico agendamento = agendamentoRepository.findById(avaliacaoDto.agendamentoServico())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Agendamento não encontrado"));
        Avaliacao avaliacao = Avaliacao.builder()
                .agendamentoServico(agendamento)
                .avaliacao(avaliacaoDto.avaliacao())
                .comentario(avaliacaoDto.comentario())
                .build();
        return repository.save(avaliacao);
    }
}
