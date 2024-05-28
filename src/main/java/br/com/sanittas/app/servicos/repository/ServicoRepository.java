package br.com.sanittas.app.servicos.repository;

import br.com.sanittas.app.servicos.model.Servico;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ServicoRepository extends JpaRepository<Servico, Integer> {
    List<Servico> findByEmpresaId(Integer empresaId);
//    @Query("""
//            select s from Servico s
//            join fetch s.servicoEmpresa se
//            join fetch s.categoriaServico cs
//""")
//    List<Servico> findAlllJoinServicoCategoria();
}
