package br.com.sanittas.app.service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Observable;

@Component
@Slf4j
public class EmailServices {

    @Autowired
    private JavaMailSender emailSender;
    private FilaObj<Map<String,String>> fila = new FilaObj<>(20);

    public void enviarEmailComToken(String email, String token) {
        fila.insert(Map.of(email,token));
    }
        EmailThread thread = new EmailThread(emailSender);
}
