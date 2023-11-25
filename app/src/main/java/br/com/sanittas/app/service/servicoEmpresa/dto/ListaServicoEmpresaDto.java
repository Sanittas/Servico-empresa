package br.com.sanittas.app.service.servicoEmpresa.dto;

import br.com.sanittas.app.model.Empresa;
import br.com.sanittas.app.model.Servico;
import br.com.sanittas.app.service.empresa.dto.ListaEmpresa;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ListaServicoEmpresaDto {
    Integer id;
    ListaEmpresa empresa;
    Servico servico;
    Double valorServico;
    String duracaoEstimada;
    String equipeResponsavel;

}
