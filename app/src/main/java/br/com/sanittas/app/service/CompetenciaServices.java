package br.com.sanittas.app.service;

import br.com.sanittas.app.model.Competencia;
import br.com.sanittas.app.repository.CompetenciaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class CompetenciaServices {
    @Autowired
    private CompetenciaRepository competenciaRepository;

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

    public Competencia atualizar(Integer id, Competencia competencia) {
            Competencia competenciaAtualizada = competenciaRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
            competenciaAtualizada.setDescricao(competencia.getDescricao());
            return competenciaRepository.save(competenciaAtualizada);
    }

    public void deletar(Integer id) {
        try {
            competenciaRepository.deleteById(id);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
    }
}
