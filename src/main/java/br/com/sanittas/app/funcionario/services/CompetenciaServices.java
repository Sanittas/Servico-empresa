package br.com.sanittas.app.funcionario.services;

import br.com.sanittas.app.funcionario.model.Competencia;
import br.com.sanittas.app.funcionario.repository.CompetenciaRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@AllArgsConstructor
public class CompetenciaServices {
    private final CompetenciaRepository competenciaRepository;

    public List<Competencia> listar() {
        try {
            return competenciaRepository.findAll();
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
    }

    public Competencia cadastrar(Competencia competencia) {
        try {
            return competenciaRepository.save(competencia);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
    }

    public void deletar(Integer id) {
        try {
            competenciaRepository.deleteById(id);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
    }
}
