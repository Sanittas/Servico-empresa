package br.com.sanittas.app.service;

import br.com.sanittas.app.model.CategoriaServico;
import br.com.sanittas.app.repository.CategoriaServicoRepository;
import br.com.sanittas.app.service.categoria.dto.CategoriaServicoCriacaoDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@Slf4j
public class CategoriaServicoServices {

    @Autowired
    private CategoriaServicoRepository categoriaServicoRepository;

    public void cadastrar(CategoriaServicoCriacaoDto dados) {
        try {
            CategoriaServico novaCategoria = new CategoriaServico();
            novaCategoria.setAreaSaude(dados.getAreaSaude());
            categoriaServicoRepository.save(novaCategoria);
            log.info("Categoria de serviço cadastrada com sucesso");
        } catch (Exception e) {
            log.error("Erro ao cadastrar categoria de serviço: " + e.getMessage());
            throw new ResponseStatusException(HttpStatusCode.valueOf(400));
        }
    }

    public List<CategoriaServico> listar() {
        return categoriaServicoRepository.findAll();
    }

    public void atualizar(Integer id, CategoriaServicoCriacaoDto categoriaServico) {
        try {
            CategoriaServico categoriaServicoAtualizada = categoriaServicoRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatusCode.valueOf(404)));
            categoriaServicoAtualizada.setAreaSaude(categoriaServico.getAreaSaude());
            categoriaServicoRepository.save(categoriaServicoAtualizada);
        } catch (Exception e) {
            log.error("Erro ao atualizar categoria de serviço: " + e.getMessage());
            throw new ResponseStatusException(HttpStatusCode.valueOf(400));
        }
    }

    public void deletar(Integer id) {
        try {
            var categoriaServico = categoriaServicoRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatusCode.valueOf(404)));
            categoriaServicoRepository.deleteById(id);
        } catch (Exception e) {
            log.error("Erro ao deletar categoria de serviço: " + e.getMessage());
            throw new ResponseStatusException(HttpStatusCode.valueOf(400));
        }
    }
}
