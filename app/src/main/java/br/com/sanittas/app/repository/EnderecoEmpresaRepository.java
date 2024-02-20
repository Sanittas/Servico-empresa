package br.com.sanittas.app.repository;

import br.com.sanittas.app.model.EnderecoEmpresa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repositório para a entidade Endereco, responsável por operações de CRUD no banco de dados.
 */
@Repository
public interface EnderecoEmpresaRepository extends JpaRepository<EnderecoEmpresa, Integer> {

    // Pode adicionar consultas personalizadas usando consultas JPQL ou nativas SQL usando a anotação @Query se necessário.
    // Por exemplo, uma consulta JPQL personalizada:
    // @Query("SELECT e FROM Endereco e WHERE e.cidade = :cidade")
    // List<Endereco> findByCidade(String cidade);
}
