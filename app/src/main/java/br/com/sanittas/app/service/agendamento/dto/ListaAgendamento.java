package br.com.sanittas.app.service.agendamento.dto;

import br.com.sanittas.app.service.servicoEmpresa.dto.ListaServicoEmpresaDto;
import br.com.sanittas.app.service.usuario.dto.ListaUsuario;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ListaAgendamento {
    Integer id;
    LocalDateTime dataHoraAgendamento;
    ListaUsuario usuario;
    ListaServicoEmpresaDto servicoEmpresa;
}
