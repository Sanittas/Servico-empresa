package br.com.sanittas.app.service;

import br.com.sanittas.app.model.*;
import br.com.sanittas.app.repository.AgendamentoRepository;
import br.com.sanittas.app.repository.ServicoEmpresaRepository;
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
    private ServicoEmpresaRepository servicoEmpresaRepository;
    @Autowired
    private UsuarioRepository usuarioRepository;
    @Autowired
    private ServicoEmpresaServices servicoEmpresaServices;

    public List<AgendamentoServico> listar() {
        try{
        List<AgendamentoServico> agendamentoServicoList = repository.findAll();
        List<ServicoEmpresa>  servicoEmpresaList = servicoEmpresaServices.listar();
        return agendamentoServicoList;
        }catch (Exception e){
            log.error("Erro ao listar agendamentos: " + e.getMessage());
            throw new ResponseStatusException(HttpStatusCode.valueOf(400));
        }
    }

    public void cadastrar(AgendamentoCriacaoDto dados) {
            ServicoEmpresa servico = servicoEmpresaRepository.findById(dados.getIdServicoEmpresa()).orElseThrow(() -> new ResponseStatusException(HttpStatusCode.valueOf(404)));
            Usuario usuario = usuarioRepository.findById(dados.getIdUsuario()).orElseThrow(() -> new ResponseStatusException(HttpStatusCode.valueOf(404)));
            AgendamentoServico novoAgendamento = new AgendamentoServico();
            novoAgendamento.setDataHoraAgendamento(dados.getDataAgendamento());
            novoAgendamento.setServicoEmpresa(servico);
            novoAgendamento.setUsuario(usuario);
            repository.save(novoAgendamento);
    }

    public void atualizar(Integer id, AgendamentoCriacaoDto dados) {
        try{
         var agendamento = repository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatusCode.valueOf(404)));
            agendamento.setDataHoraAgendamento(dados.getDataAgendamento());
            repository.save(agendamento);
        }catch (Exception e){
            log.error("Erro ao atualizar agendamento: " + e.getMessage());
            throw new ResponseStatusException(HttpStatusCode.valueOf(400));
        }
    }

    public void deletar(Integer id) {
        try{
            var agendamento = repository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatusCode.valueOf(404)));
            repository.deleteById(id);
        }catch (Exception e){
            log.error("Erro ao deletar agendamento: " + e.getMessage());
            throw new ResponseStatusException(HttpStatusCode.valueOf(400));
        }
    }
}
