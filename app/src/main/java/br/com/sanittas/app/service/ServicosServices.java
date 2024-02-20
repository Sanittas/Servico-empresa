package br.com.sanittas.app.service;

import br.com.sanittas.app.model.Servico;
import br.com.sanittas.app.repository.ServicoRepository;
import br.com.sanittas.app.service.servico.dto.ServicoCriacaoDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@Slf4j
public class ServicosServices {
    @Autowired
    private ServicoRepository servicoRepository;

    public List<Servico> listar() {
        try {
            return servicoRepository.findAll();
        } catch (Exception e) {
            log.error("Erro ao buscar servicos: " + e.getLocalizedMessage());
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
    }

    public List<Servico> listarServicoCategoria() {
        return null;
//                servicoRepository.findAlllJoinServicoCategoria();
    }

    public void cadastrar(ServicoCriacaoDto dados) {
        Servico novoServico = Servico.builder()
                .descricao(dados.getDescricao())
                .areaSaude(dados.getAreaSaude())
                .valor(dados.getValor())
                .duracaoEstimada(dados.getDuracaoEstimada())
                .build();
        servicoRepository.save(novoServico);
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


}
