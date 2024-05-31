package br.com.sanittas.app.funcionario.repository;

import br.com.sanittas.app.empresa.model.Empresa;
import br.com.sanittas.app.funcionario.model.Funcionario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FuncionarioRepository extends JpaRepository<Funcionario, Integer> {

    List<Funcionario> findAllByfkEmpresa(Empresa idEmpresa);

    Optional<Funcionario> findByCpf(String cpf);

    boolean existsByCpf(String cpf);

    Integer countByfkEmpresa(Empresa empresa);

    boolean existsByFuncional(String funcional);
    @Query("""
    SELECT f FROM Funcionario f JOIN Competencia c ON f.id = c.funcionario.id
    WHERE c.especializacao = :especializacao
    """)
    List<Funcionario> findByServico(String especializacao);
}
