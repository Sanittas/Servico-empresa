package br.com.sanittas.app.mail;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class EmailEmpresa {
    private String email;
    private String token;
}
