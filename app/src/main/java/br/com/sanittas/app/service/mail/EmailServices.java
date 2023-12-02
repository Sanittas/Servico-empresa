package br.com.sanittas.app.service.mail;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
@Slf4j
public class EmailServices {
    private FilaObj fila;

    public EmailServices() {
        this.fila = new FilaObj(20);
    }

    public void enviarEmailComToken(String email, String token) {
        fila.insert(new EmailEmpresa(email, token));
        log.info("Email adicionado a fila");
    }
}
