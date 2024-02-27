package br.com.sanittas.app.mail;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.LinkedList;
import java.util.Queue;

@Component
@Slf4j
public class EmailServices {
    private Queue<EmailEmpresa> fila;

    public EmailServices() {
        this.fila = new LinkedList<>();
    }

    public void enviarEmailComToken(String email, String token) {
        fila.add(new EmailEmpresa(email, token));
        log.info("Email adicionado a fila");
    }
}
