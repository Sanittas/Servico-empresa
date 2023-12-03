package br.com.sanittas.app.service.mail;

import jakarta.mail.internet.MimeMessage;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class EmailThreadTest {
    @Mock
    private JavaMailSender javaMailSender;

    @Test
    public void testEnvioAssincronoDeEmail() throws InterruptedException {
        // Mock da fila para retornar um email ao ser chamado
        FilaObj fila = mock(FilaObj.class);
        MimeMessage mimeMessage = mock(MimeMessage.class);
        MimeMessageHelper mimeMessageHelper = mock(MimeMessageHelper.class);
        when(javaMailSender.createMimeMessage()).thenReturn(mimeMessage);
        EmailEmpresa emailEmpresa = new EmailEmpresa("test@example.com", "token");
        when(fila.poll()).thenReturn(emailEmpresa);

        // Mock do envio de email
        doNothing().when(javaMailSender).send(any(MimeMessage.class));

        // Cria uma instância de EmailThread com a fila mockada
        EmailThread emailThread = new EmailThread(fila, javaMailSender, mimeMessageHelper);
        try {
            doNothing().when(mimeMessageHelper).setFrom(any(String.class));
            doNothing().when(mimeMessageHelper).setTo(any(String.class));
            doNothing().when(mimeMessageHelper).setSubject(any(String.class));
            doNothing().when(mimeMessageHelper).setText(any(String.class), any(Boolean.class));
        } catch (Exception e) {
            e.printStackTrace();
        }
        // CountDownLatch para aguardar a conclusão da execução da thread
        CountDownLatch latch = new CountDownLatch(1);

        // Executa a thread e aguarda sua conclusão
        new Thread(() -> {
            emailThread.update();
            latch.countDown();
        }).start();

        // Aguarda a conclusão da thread assíncrona
        latch.await();

        // Verifica se o método send() foi chamado
        verify(javaMailSender, times(1)).send(any(MimeMessage.class));
    }

    @Test
    public void testEnvioAssincronoDeEmailsEmEscala() throws InterruptedException {
        int numberOfEmails = 10; // Defina o número desejado de e-mails para teste
        FilaObj fila = mock(FilaObj.class);
        MimeMessage mimeMessage = mock(MimeMessage.class);
        MimeMessageHelper mimeMessageHelper = mock(MimeMessageHelper.class);

        // Configuração dos mocks e comportamento esperado
        when(fila.poll()).thenReturn(new EmailEmpresa("test@example.com", "token")); // Configuração padrão do mock para fila

        when(javaMailSender.createMimeMessage()).thenReturn(mimeMessage);

        // Configuração do mock de MimeMessageHelper
        try {
            doNothing().when(mimeMessageHelper).setFrom(any(String.class));
            doNothing().when(mimeMessageHelper).setTo(any(String.class));
            doNothing().when(mimeMessageHelper).setSubject(any(String.class));
            doNothing().when(mimeMessageHelper).setText(any(String.class), any(Boolean.class));
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Configuração do comportamento do javaMailSender
        doNothing().when(javaMailSender).send(any(MimeMessage.class));

        // Criação da instância de EmailThread com os mocks
        EmailThread emailThread = new EmailThread(fila, javaMailSender, mimeMessageHelper); // Use o primeiro helper

        // Lista para guardar as threads criadas
        List<Thread> threads = new ArrayList<>();

        // Lista para guardar os tempos de execução das threads
        List<Long> executionTimes = new ArrayList<>();

        // Execução das threads para enviar os e-mails
        for (int i = 0; i < numberOfEmails; i++) {
            long startTime = System.currentTimeMillis(); // Tempo inicial da execução da thread
            Thread thread = new Thread(() -> {
                emailThread.update();
                long endTime = System.currentTimeMillis(); // Tempo final da execução da thread
                long executionTime = endTime - startTime; // Tempo de execução da thread
                executionTimes.add(executionTime); // Adiciona o tempo de execução à lista
            });
            threads.add(thread);
            thread.start();
        }

        // Aguarda a conclusão de todas as threads assíncronas
        for (Thread thread : threads) {
            thread.join();
        }

        Thread.sleep(500);

        // Verifica se o método send() foi chamado para cada e-mail na fila
        verify(javaMailSender, times(numberOfEmails)).send(any(MimeMessage.class));

        // Imprime os tempos de execução de cada thread
        for (int i = 0; i < numberOfEmails; i++) {
            System.out.println("Tempo de execução da thread " + i + ": " + executionTimes.get(i) + " ms");
        }
    }

}
