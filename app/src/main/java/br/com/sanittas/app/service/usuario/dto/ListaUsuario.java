package br.com.sanittas.app.service.usuario.dto;

import br.com.sanittas.app.service.endereco.dto.ListaEndereco;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * DTO que representa as informações de usuário em uma lista.
 */
@AllArgsConstructor
@Getter
@Setter
public class ListaUsuario {

        /**
         * Identificador único do usuário.
         */
        private Integer id;

        /**
         * Nome do usuário.
         */
        private String nome;

        /**
         * E-mail do usuário.
         */
        private String email;

        /**
         * CPF do usuário.
         */
        private String cpf;
}
