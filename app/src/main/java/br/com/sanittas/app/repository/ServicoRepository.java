package br.com.sanittas.app.repository;

import br.com.sanittas.app.model.Servico;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ServicoRepository extends JpaRepository<Servico, Integer> {

//    @Query("""
//            select s from Servico s
//            join fetch s.servicoEmpresa se
//            join fetch s.categoriaServico cs
//""")
//    List<Servico> findAlllJoinServicoCategoria();
}
