package br.com.sanittas.app.service;

import br.com.sanittas.app.model.*;
import br.com.sanittas.app.repository.AgendamentoRepository;
import br.com.sanittas.app.repository.ServicoRepository;
import br.com.sanittas.app.repository.UsuarioRepository;
import br.com.sanittas.app.service.agendamento.dto.AgendamentoCriacaoDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@Slf4j
public class AgendamentoServicoServices {
    @Autowired
    private AgendamentoRepository repository;
    @Autowired
    private UsuarioRepository usuarioRepository;
    @Autowired
    private ServicoRepository servicoRepository;

    public List<AgendamentoServico> listar() {
        try {
            return repository.findAll();
        } catch (Exception e) {
            log.error("Erro ao listar agendamentos: " + e.getMessage());
            throw new ResponseStatusException(HttpStatusCode.valueOf(400));
        }
    }

    public void cadastrar(AgendamentoCriacaoDto dados) {
        Servico servico =
                servicoRepository.findById(dados.getIdServico()).orElseThrow(() -> new ResponseStatusException(HttpStatusCode.valueOf(404)));
        Usuario usuario = usuarioRepository.findById(dados.getIdUsuario()).orElseThrow(() -> new ResponseStatusException(HttpStatusCode.valueOf(404)));
        AgendamentoServico novoAgendamento = AgendamentoServico.builder()
                .dataHoraAgendamento(dados.getDataAgendamento())
                .usuario(usuario)
                .servico(servico)
                .build();
        repository.save(novoAgendamento);
    }

    public void atualizar(Integer id, AgendamentoCriacaoDto dados) {
        var agendamento = repository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatusCode.valueOf(404)));
        agendamento.setDataHoraAgendamento(dados.getDataAgendamento());
        repository.save(agendamento);
    }

    public void deletar(Integer id) {
        repository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatusCode.valueOf(404)));
        repository.deleteById(id);
    }
}
