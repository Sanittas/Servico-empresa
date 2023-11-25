package br.com.sanittas.app.service;

import br.com.sanittas.app.model.Empresa;
import br.com.sanittas.app.model.Endereco;
import br.com.sanittas.app.model.Servico;
import br.com.sanittas.app.model.ServicoEmpresa;
import br.com.sanittas.app.repository.EmpresaRepository;
import br.com.sanittas.app.repository.ServicoEmpresaRepository;
import br.com.sanittas.app.repository.ServicoRepository;
import br.com.sanittas.app.service.empresa.dto.ListaEmpresa;
import br.com.sanittas.app.service.endereco.dto.ListaEndereco;
import br.com.sanittas.app.service.servicoEmpresa.dto.ListaServicoEmpresaDto;
import br.com.sanittas.app.service.servicoEmpresa.dto.ServicoEmpresaCriacaoDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;

@Service
public class ServicoEmpresaServices {
    @Autowired
    private ServicoEmpresaRepository servicoEmpresaRepository;
    @Autowired
    private EmpresaRepository empresaRepository;
    @Autowired
    private ServicoRepository servicoRepository;

    public List<ListaServicoEmpresaDto> listar() {
        try {
            List<ServicoEmpresa> servicoEmpresas = servicoEmpresaRepository.findAll();
            List<ListaServicoEmpresaDto> lista = new ArrayList<>();
            List<ListaEndereco> listaEnderecos = new ArrayList<>();
            popularDtos(servicoEmpresas, listaEnderecos, lista);
            return lista;
        }catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
    }

    private static void popularDtos(List<ServicoEmpresa> servicoEmpresas, List<ListaEndereco> listaEnderecos, List<ListaServicoEmpresaDto> lista) {
        for (ServicoEmpresa servico: servicoEmpresas) {
            ListaServicoEmpresaDto listaServicoEmpresaDto = new ListaServicoEmpresaDto();
            listaServicoEmpresaDto.setId(servico.getId());
            extrairEndereco(servico.getEmpresa(), listaEnderecos);
            listaServicoEmpresaDto.setEmpresa(new ListaEmpresa(servico.getEmpresa().getId(),servico.getEmpresa().getRazaoSocial(),servico.getEmpresa().getCnpj(), listaEnderecos));
            listaServicoEmpresaDto.setServico(servico.getServico());
            listaServicoEmpresaDto.setValorServico(servico.getValorServico());
            listaServicoEmpresaDto.setDuracaoEstimada(servico.getDuracaoEstimada());
            listaServicoEmpresaDto.setEquipeResponsavel(servico.getEquipeResponsavel());
            lista.add(listaServicoEmpresaDto);
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
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
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
