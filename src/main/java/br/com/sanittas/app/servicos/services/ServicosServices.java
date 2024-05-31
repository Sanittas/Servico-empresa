package br.com.sanittas.app.servicos.services;

import br.com.sanittas.app.empresa.model.Empresa;
import br.com.sanittas.app.empresa.repository.EmpresaRepository;
import br.com.sanittas.app.funcionario.model.AreaSaude;
import br.com.sanittas.app.funcionario.repository.AreaSaudeRepository;
import br.com.sanittas.app.funcionario.repository.FuncionarioRepository;
import br.com.sanittas.app.servicos.model.AgendamentoServico;
import br.com.sanittas.app.servicos.model.Servico;
import br.com.sanittas.app.servicos.repository.AgendamentoRepository;
import br.com.sanittas.app.servicos.repository.ServicoRepository;
import br.com.sanittas.app.servicos.services.dto.AgendaFuncionarioDto;
import br.com.sanittas.app.servicos.services.dto.ServicoCriacaoDto;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@AllArgsConstructor
public class ServicosServices {
    private final EmpresaRepository empresaRepository;
    private final ServicoRepository servicoRepository;
    private final AgendamentoRepository agendamentoRepository;
    private final AreaSaudeRepository areaSaudeRepository;

    public List<Servico> listar() {
        try {
            return servicoRepository.findAll();
        } catch (Exception e) {
            log.error("Erro ao buscar servicos: " + e.getLocalizedMessage());
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
    }

    public List<String> listarAgendaFuncionario(AgendaFuncionarioDto dados) {
        List<AgendamentoServico> agendamentos = agendamentoRepository.findByFuncionarioAndDate(
                dados.idFuncionario(), dados.data());

        List<LocalTime> horariosAgendados = agendamentos.stream()
                .map(a -> a.getDataHoraAgendamento().toLocalTime())
                .collect(Collectors.toList());

        List<String> horariosDisponiveis = new ArrayList<>();
        LocalTime horaInicial = LocalTime.of(8, 0);
        LocalTime horaFinal = LocalTime.of(18, 0);
        while (horaInicial.isBefore(horaFinal)) {
            if (!horariosAgendados.contains(horaInicial)) {
                horariosDisponiveis.add(horaInicial.toString());
            }
            horaInicial = horaInicial.plusMinutes(30);
        }

        return horariosDisponiveis;
    }

    public Servico buscarPorId(Integer id) {
        return servicoRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatusCode.valueOf(404), "Servico não encontrado"));
    }

    public Servico cadastrar(ServicoCriacaoDto dados) {
        Empresa empresa = empresaRepository.findById(dados.getEmpresaId()).orElseThrow(() -> new ResponseStatusException(HttpStatusCode.valueOf(404), "Empresa não encontrada"));
        if (verificarEspecializacao(dados.getAreaSaude())) {
            throw new ResponseStatusException(
                    HttpStatusCode.valueOf(404), "Especialização não encontrada");
        }
        Servico novoServico = Servico.builder()
                .descricao(dados.getDescricao())
                .areaSaude(dados.getAreaSaude())
                .valor(dados.getValor())
                .duracaoEstimada(dados.getDuracaoEstimada())
                .empresa(empresa)
                .build();
        return servicoRepository.save(novoServico);
    }

    private boolean verificarEspecializacao(String especializacao) {
        List<AreaSaude> areaSaude = areaSaudeRepository.findAll();
        for (AreaSaude area : areaSaude) {
            if (area.getEspecializacao().equals(especializacao)) {
                return false;
            }
        }
        return true;
    }

    public void atualizar(Integer id, ServicoCriacaoDto dados) {
        Servico servicoAtualizado = servicoRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatusCode.valueOf(404), "Servico não encontrado"));
        servicoAtualizado.setDescricao(dados.getDescricao());
        servicoAtualizado.setAreaSaude(dados.getAreaSaude());
        servicoAtualizado.setValor(dados.getValor());
        servicoAtualizado.setDuracaoEstimada(dados.getDuracaoEstimada());
        servicoRepository.save(servicoAtualizado);
    }

    public void deletar(Integer id) {
        servicoRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatusCode.valueOf(404), "Servico não encontrado"));
        servicoRepository.deleteById(id);
    }

    public List<Servico> getServicosPorEmpresa(Integer empresaId) {
        return servicoRepository.findByEmpresaId(empresaId);
    }
}
