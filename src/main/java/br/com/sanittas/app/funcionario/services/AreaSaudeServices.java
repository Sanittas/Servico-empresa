package br.com.sanittas.app.funcionario.services;

import br.com.sanittas.app.funcionario.model.AreaSaude;
import br.com.sanittas.app.funcionario.repository.AreaSaudeRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class AreaSaudeServices {
    private final AreaSaudeRepository repository;

    public List<AreaSaude> getAll() {
        return repository.findAll();
    }
}
