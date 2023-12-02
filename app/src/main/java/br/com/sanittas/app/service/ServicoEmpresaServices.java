package br.com.sanittas.app.service;

import br.com.sanittas.app.model.Empresa;
import br.com.sanittas.app.model.Servico;
import br.com.sanittas.app.model.ServicoEmpresa;
import br.com.sanittas.app.repository.EmpresaRepository;
import br.com.sanittas.app.repository.ServicoEmpresaRepository;
import br.com.sanittas.app.repository.ServicoRepository;
import br.com.sanittas.app.service.servicoEmpresa.dto.ServicoEmpresaCriacaoDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@Slf4j
public class ServicoEmpresaServices {
    @Autowired
    private ServicoEmpresaRepository servicoEmpresaRepository;
    @Autowired
    private EmpresaRepository empresaRepository;
    @Autowired
    private ServicoRepository servicoRepository;

    public List<ServicoEmpresa> listar() {
        try {
            List<ServicoEmpresa> servicoEmpresas = servicoEmpresaRepository.findAll();
            return servicoEmpresas;
        }catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
    }

    public void cadastrar(ServicoEmpresaCriacaoDto dados) {
            Empresa empresa = empresaRepository.findById(dados.getIdEmpresa()).orElseThrow(() -> new ResponseStatusException(HttpStatusCode.valueOf(404)));
            Servico servico = servicoRepository.findById(dados.getIdServico()).orElseThrow(() -> new ResponseStatusException(HttpStatusCode.valueOf(404)));
            ServicoEmpresa novoServico = new ServicoEmpresa();
            novoServico.setEmpresa(empresa);
            novoServico.setServico(servico);
            novoServico.setValorServico(dados.getValorServico());
            novoServico.setDuracaoEstimada(dados.getDuracaoEstimada());
            novoServico.setEquipeResponsavel(dados.getEquipeResponsavel());
            servicoEmpresaRepository.save(novoServico);
    }

    public List<ServicoEmpresa> listarPorEmpresa(Integer id) {
        return servicoEmpresaRepository.findByEmpresaId(id);
    }

    public void atualizar(Integer id, ServicoEmpresaCriacaoDto dados) {
        try {
            var servico = servicoEmpresaRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatusCode.valueOf(404)));
            Empresa empresa = empresaRepository.findById(dados.getIdEmpresa()).orElseThrow(() -> new ResponseStatusException(HttpStatusCode.valueOf(404)));
            Servico servico1 = servicoRepository.findById(dados.getIdServico()).orElseThrow(() -> new ResponseStatusException(HttpStatusCode.valueOf(404)));
            servico.setEmpresa(empresa);
            servico.setServico(servico1);
            servico.setValorServico(dados.getValorServico());
            servico.setDuracaoEstimada(dados.getDuracaoEstimada());
            servico.setEquipeResponsavel(dados.getEquipeResponsavel());
            servicoEmpresaRepository.save(servico);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
    }

    public void deletar(Integer id) {
        try {
            var servico = servicoEmpresaRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatusCode.valueOf(404)));
            servicoEmpresaRepository.deleteById(id);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
    }
}
