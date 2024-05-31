package br.com.sanittas.app.servicos.services;

import br.com.sanittas.app.funcionario.model.Funcionario;
import br.com.sanittas.app.funcionario.repository.FuncionarioRepository;
import br.com.sanittas.app.servicos.model.AgendamentoServico;
import br.com.sanittas.app.servicos.model.Servico;
import br.com.sanittas.app.servicos.repository.AgendamentoRepository;
import br.com.sanittas.app.servicos.repository.ServicoRepository;
import br.com.sanittas.app.servicos.services.dto.AgendamentoCriacaoDto;
import br.com.sanittas.app.servicos.services.dto.VerificarDisponibilidadeDto;
import br.com.sanittas.app.usuario.model.Usuario;
import br.com.sanittas.app.usuario.repository.UsuarioRepository;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@Slf4j
@AllArgsConstructor
public class AgendamentoServicoServices {
    private final AgendamentoRepository repository;
    private final UsuarioRepository usuarioRepository;
    private final FuncionarioRepository funcionarioRepository;
    private final ServicoRepository servicoRepository;

    public List<AgendamentoServico> listar() {
        try {
            return repository.findAll();
        } catch (Exception e) {
            log.error("Erro ao listar agendamentos: " + e.getMessage());
            throw new ResponseStatusException(HttpStatusCode.valueOf(400),
                    "Erro ao listar agendamentos");
        }
    }

    public AgendamentoServico cadastrar(AgendamentoCriacaoDto dados) {
        Servico servico =
                servicoRepository.findById(dados.getIdServico())
                        .orElseThrow(() -> new ResponseStatusException(
                                HttpStatusCode.valueOf(404),
                                "Serviço não encontrado"));
        Usuario usuario =
                usuarioRepository.findById(dados.getIdUsuario())
                        .orElseThrow(() -> new ResponseStatusException(
                                HttpStatusCode.valueOf(404),
                                "Usuário não encontrado"));
        Funcionario funcionario =
                funcionarioRepository.findById(dados.getIdFuncionario())
                        .orElseThrow(() -> new ResponseStatusException(
                                HttpStatusCode.valueOf(404),
                                "Funcionário não encontrado"));
        AgendamentoServico novoAgendamento = AgendamentoServico.builder()
                .dataHoraAgendamento(dados.getDataAgendamento())
                .usuario(usuario)
                .servico(servico)
                .funcionario(funcionario)
                .build();
        return repository.save(novoAgendamento);
    }

    public void atualizar(Integer id, AgendamentoCriacaoDto dados) {
        var agendamento =
                repository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatusCode.valueOf(404),
                        "Agendamento não encontrado"));
        agendamento.setDataHoraAgendamento(dados.getDataAgendamento());
        repository.save(agendamento);
    }

    public void deletar(Integer id) {
        repository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatusCode.valueOf(404),
                "Agendamento não encontrado"));
        repository.deleteById(id);
    }

    public List<AgendamentoServico> listarAgendamentosPorUsuario(Integer id) {
        usuarioRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatusCode.valueOf(404),
                "Usuário não encontrado"));
        return repository.findAllByUsuario_Id(id);
    }

    public String verificarDisponibilidade(@NotNull VerificarDisponibilidadeDto dados) {
        Funcionario funcionario = funcionarioRepository.findById(dados.idFuncionario())
                .orElseThrow(() -> new ResponseStatusException(HttpStatusCode.valueOf(404), "Funcionário não encontrado"));
        Servico servico = servicoRepository.findById(dados.idServico())
                .orElseThrow(() -> new ResponseStatusException(HttpStatusCode.valueOf(404), "Serviço não encontrado"));
        List<AgendamentoServico> agendamentos =
                repository.findAllByDay(dados.dataHoraAgendamento().minusMinutes(61),
                        dados.dataHoraAgendamento().plusMinutes(61),
                        servico,
                        funcionario);
        return agendamentos.isEmpty() ? "Disponível": "Indisponível";
    }
}
