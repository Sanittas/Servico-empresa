package br.com.sanittas.app.service;

import br.com.sanittas.app.model.Empresa;
import br.com.sanittas.app.model.Servico;
import br.com.sanittas.app.model.ServicoEmpresa;
import br.com.sanittas.app.repository.EmpresaRepository;
import br.com.sanittas.app.repository.ServicoEmpresaRepository;
import br.com.sanittas.app.repository.ServicoRepository;
import br.com.sanittas.app.service.servicoEmpresa.dto.ServicoEmpresaCriacaoDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class ServicoEmpresaServices {
    @Autowired
    private ServicoEmpresaRepository servicoEmpresaRepository;
    @Autowired
    private EmpresaRepository empresaRepository;
    @Autowired
    private ServicoRepository servicoRepository;

    public List<ServicoEmpresa> listar() {
        return servicoEmpresaRepository.findAll();
    }

    public void cadastrar(ServicoEmpresaCriacaoDto dados) {
        try {
            Empresa empresa = empresaRepository.findById(dados.getIdEmpresa()).orElseThrow(() -> new ResponseStatusException(HttpStatusCode.valueOf(404)));
            Servico servico = servicoRepository.findById(dados.getIdServico()).orElseThrow(() -> new ResponseStatusException(HttpStatusCode.valueOf(404)));
            ServicoEmpresa novoServico = new ServicoEmpresa();
            novoServico.setEmpresa(empresa);
            novoServico.setServico(servico);
            novoServico.setValorServico(dados.getValorServico());
            novoServico.setDuracaoEstimada(dados.getDuracaoEstimada());
            novoServico.setEquipeResponsavel(dados.getEquipeResponsavel());
            servicoEmpresaRepository.save(novoServico);
        } catch (Exception e) {
            throw new RuntimeException(e.getLocalizedMessage());
        }
    }
}
