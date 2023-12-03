package br.com.sanittas.app.service.mail;

import lombok.Getter;

@Getter
public class FilaObj {
    // Atributos
    private int tamanho;
    private EmailEmpresa[] fila;
    private EventManager events;

    // Construtor
    public FilaObj(int capacidade) {
    this.tamanho = 0;
    this.fila = new EmailEmpresa[capacidade];
    this.events = new EventManager();
    this.events.subscribe(new EmailThread(this));
    }

    // Métodos

    /* Método isEmpty() - retorna true se a fila está vazia e false caso contrário */
    public boolean isEmpty() {
        return tamanho == 0;
    }

    /* Método isFull() - retorna true se a fila está cheia e false caso contrário */
    public boolean isFull() {
        return tamanho == fila.length;
    }

    /* Método insert - recebe um elemento e insere esse elemento na fila
                       no índice tamanho, e incrementa tamanho
                       Lançar IllegalStateException caso a fila esteja cheia
     */
    public void insert(EmailEmpresa emailEmpresa) {
        if (isFull()) {
            throw new IllegalStateException("Fila cheia");
        }
        fila[tamanho] = emailEmpresa;
        tamanho++;
        events.notificar();
    }

    /* Método peek - retorna o primeiro elemento da fila, sem removê-lo */
    public EmailEmpresa peek() {
        return fila[0];
    }

    /* Método poll - remove e retorna o primeiro elemento da fila, se a fila não estiver
       vazia. Quando um elemento é removido, a fila "anda", e tamanho é decrementado
       Depois que a fila andar, "limpar" o ex-último elemento da fila, atribuindo null
     */
    public EmailEmpresa poll() {
        if (isEmpty()){
            throw new IllegalStateException("Fila vazia");
        }
        EmailEmpresa primeiro = fila[0];
        for (int i = 0; i < tamanho - 1; i++) {
            fila[i] = fila[i + 1];
        }
        fila[tamanho - 1] = null;
        tamanho--;
        return primeiro;
    }

    /* Método exibe() - exibe o conteúdo da fila */
    public void exibe() {
        for (int i = 0; i < tamanho; i++) {
            System.out.println(fila[i]);
        }
    }
}

