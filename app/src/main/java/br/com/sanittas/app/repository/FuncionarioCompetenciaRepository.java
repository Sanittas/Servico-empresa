package br.com.sanittas.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FuncionarioCompetenciaRepository extends JpaRepository<FuncionarioCompetencia, Integer> {
    List<FuncionarioCompetencia> findAllByFuncionarioId(Integer idFuncionario);


//    boolean existsByFuncionarioIdAndCompetencia(Funcionario funcionario, Integer fkCompetencia);
    
    
}
