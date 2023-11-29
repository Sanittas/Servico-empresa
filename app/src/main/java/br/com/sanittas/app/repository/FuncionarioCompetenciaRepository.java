package br.com.sanittas.app.repository;

import br.com.sanittas.app.model.FuncionarioCompetencia;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FuncionarioCompetenciaRepository extends JpaRepository<FuncionarioCompetencia, Integer> {

}
