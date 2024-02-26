package br.com.sanittas.app.funcionario.repository;

import br.com.sanittas.app.funcionario.model.Competencia;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CompetenciaRepository extends JpaRepository<Competencia, Integer> {
}
