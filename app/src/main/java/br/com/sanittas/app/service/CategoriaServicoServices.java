package br.com.sanittas.app.service;

import br.com.sanittas.app.model.CategoriaServico;
import br.com.sanittas.app.repository.CategoriaServicoRepository;
import br.com.sanittas.app.service.categoria.dto.CategoriaServicoCriacaoDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class CategoriaServicoServices {

    @Autowired
    private CategoriaServicoRepository categoriaServicoRepository;

    public void cadastrar(CategoriaServicoCriacaoDto dados) {
        try {
            CategoriaServico novaCategoria = new CategoriaServico();
            novaCategoria.setAreaSaude(dados.getAreaSaude());
            categoriaServicoRepository.save(novaCategoria);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatusCode.valueOf(400),e.getLocalizedMessage());
        }
    }

    public List<CategoriaServico> listar() {
        return categoriaServicoRepository.findAll();
    }
}
