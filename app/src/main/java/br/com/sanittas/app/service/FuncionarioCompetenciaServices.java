package br.com.sanittas.app.service;

import br.com.sanittas.app.model.FuncionarioCompetencia;
import br.com.sanittas.app.repository.FuncionarioCompetenciaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class FuncionarioCompetenciaServices {
    @Autowired
    private FuncionarioCompetenciaRepository repository;

    public List<FuncionarioCompetencia> listar() {
        try {
            return repository.findAll();
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
    }

    public FuncionarioCompetencia cadastrar(FuncionarioCompetencia funcionarioCompetencia) {
        try {
            return repository.save(funcionarioCompetencia);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
    }

    public FuncionarioCompetencia atualizar(Integer id, FuncionarioCompetencia funcionarioCompetencia) {
        FuncionarioCompetencia funcionarioCompetenciaAtualizado = repository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        funcionarioCompetenciaAtualizado.setFuncionario(funcionarioCompetencia.getFuncionario());
        funcionarioCompetenciaAtualizado.setCompetencia(funcionarioCompetencia.getCompetencia());
        return repository.save(funcionarioCompetenciaAtualizado);
    }

    public void deletar(Integer id) {
        try {
            repository.deleteById(id);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
    }
}
