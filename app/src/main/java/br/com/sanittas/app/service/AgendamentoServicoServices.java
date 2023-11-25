package br.com.sanittas.app.service;

import br.com.sanittas.app.model.*;
import br.com.sanittas.app.repository.AgendamentoRepository;
import br.com.sanittas.app.repository.ServicoEmpresaRepository;
import br.com.sanittas.app.repository.UsuarioRepository;
import br.com.sanittas.app.service.agendamento.dto.AgendamentoCriacaoDto;
import br.com.sanittas.app.service.agendamento.dto.ListaAgendamento;
import br.com.sanittas.app.service.empresa.dto.ListaEmpresa;
import br.com.sanittas.app.service.endereco.dto.ListaEndereco;
import br.com.sanittas.app.service.servicoEmpresa.dto.ListaServicoEmpresaDto;
import br.com.sanittas.app.service.usuario.dto.ListaUsuario;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
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

    public List<ListaAgendamento> listar() {
        try{
        List<AgendamentoServico> agendamentoServicoList = repository.findAll();
        List<ListaServicoEmpresaDto> listaServicoEmpresaDtos = servicoEmpresaServices.listar();
        List<ListaAgendamento> listaAgendamentos = new ArrayList<>();
        for (AgendamentoServico agendamento: agendamentoServicoList) {
            ListaAgendamento listaAgendamento = new ListaAgendamento();
            listaAgendamento.setId(agendamento.getId());
            listaAgendamento.setDataHoraAgendamento(agendamento.getDataHoraAgendamento());
            listaAgendamento.setUsuario(new ListaUsuario(agendamento.getUsuario().getId(), agendamento.getUsuario().getNome(), agendamento.getUsuario().getEmail(), agendamento.getUsuario().getCpf()));
            listaAgendamento.setServicoEmpresa(listaServicoEmpresaDtos.stream().filter(servicoEmpresa -> servicoEmpresa.getId().equals(agendamento.getServicoEmpresa().getId())).findFirst().orElseThrow(() -> new ResponseStatusException(HttpStatusCode.valueOf(404))));
            listaAgendamentos.add(listaAgendamento);
        }
        return listaAgendamentos;
        }catch (Exception e){
            log.error("Erro ao listar agendamentos: " + e.getMessage());
            throw new ResponseStatusException(HttpStatusCode.valueOf(400));
        }
    }

    private static void extrairEndereco(Empresa empresa, List<ListaEndereco> listaEnderecos) {
        for (Endereco endereco : empresa.getEnderecos()) {
            var enderecoDto = new ListaEndereco(
                    endereco.getId(),
                    endereco.getLogradouro(),
                    endereco.getNumero(),
                    endereco.getComplemento(),
                    endereco.getEstado(),
                    endereco.getCidade()
            );
            listaEnderecos.add(enderecoDto);
        }
    }


    public void cadastrar(AgendamentoCriacaoDto dados) {
        try{
            ServicoEmpresa servico = servicoEmpresaRepository.findById(dados.getIdServicoEmpresa()).orElseThrow(() -> new ResponseStatusException(HttpStatusCode.valueOf(404)));
            Usuario usuario = usuarioRepository.findById(dados.getIdUsuario()).orElseThrow(() -> new ResponseStatusException(HttpStatusCode.valueOf(404)));
            AgendamentoServico novoAgendamento = new AgendamentoServico();
            novoAgendamento.setDataHoraAgendamento(dados.getDataAgendamento());
            novoAgendamento.setServicoEmpresa(servico);
            novoAgendamento.setUsuario(usuario);
            repository.save(novoAgendamento);
        } catch (Exception e) {
            log.error("Erro ao cadastrar agendamento: " + e.getMessage());
            throw new ResponseStatusException(HttpStatusCode.valueOf(400));
        }
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
