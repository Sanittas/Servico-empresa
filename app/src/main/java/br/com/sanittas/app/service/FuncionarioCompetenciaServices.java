package br.com.sanittas.app.service;

import br.com.sanittas.app.model.Competencia;
import br.com.sanittas.app.model.Funcionario;
import br.com.sanittas.app.model.FuncionarioCompetencia;
import br.com.sanittas.app.repository.CompetenciaRepository;
import br.com.sanittas.app.repository.FuncionarioCompetenciaRepository;
import br.com.sanittas.app.repository.FuncionarioRepository;
import br.com.sanittas.app.service.funcionario.dto.FuncionarioCompetenciaDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class FuncionarioCompetenciaServices {
    @Autowired
    private FuncionarioCompetenciaRepository repository;
    @Autowired
    private FuncionarioRepository funcionarioRepository;
    @Autowired
    private CompetenciaRepository competenciaRepository;

    public List<FuncionarioCompetencia> listar() {
        try {
            return repository.findAll();
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
    }

    public FuncionarioCompetencia cadastrar(FuncionarioCompetenciaDto funcionarioCompetenciaDto) {
        try {
            Funcionario funcionario = funcionarioRepository.findById(funcionarioCompetenciaDto.getFk_funcionario()).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
            Competencia competencia = competenciaRepository.findById(funcionarioCompetenciaDto.getFk_competencia()).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
            FuncionarioCompetencia funcionarioCompetencia = new FuncionarioCompetencia();
            funcionarioCompetencia.setFuncionario(funcionario);
            funcionarioCompetencia.setCompetencia(competencia);
            funcionarioCompetencia.setEspecializacao(funcionarioCompetenciaDto.getEspecializacao());
            funcionarioCompetencia.setExperiencia(funcionarioCompetenciaDto.getExperiencia());
            funcionarioCompetencia.setNivel_proficiencia(funcionarioCompetenciaDto.getNivel_proficiencia());
            return repository.save(funcionarioCompetencia);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
    }

    public FuncionarioCompetencia atualizar(Integer id, FuncionarioCompetenciaDto funcionarioCompetenciaDto) {
        Funcionario funcionario = funcionarioRepository.findById(funcionarioCompetenciaDto.getFk_funcionario()).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        Competencia competencia = competenciaRepository.findById(funcionarioCompetenciaDto.getFk_competencia()).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        FuncionarioCompetencia funcionarioCompetencia = repository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        funcionarioCompetencia.setFuncionario(funcionario);
        funcionarioCompetencia.setCompetencia(competencia);
        funcionarioCompetencia.setEspecializacao(funcionarioCompetenciaDto.getEspecializacao());
        funcionarioCompetencia.setExperiencia(funcionarioCompetenciaDto.getExperiencia());
        funcionarioCompetencia.setNivel_proficiencia(funcionarioCompetenciaDto.getNivel_proficiencia());
        return repository.save(funcionarioCompetencia);
    }

    public void deletar(Integer id) {
        try {
            repository.deleteById(id);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
    }
}
