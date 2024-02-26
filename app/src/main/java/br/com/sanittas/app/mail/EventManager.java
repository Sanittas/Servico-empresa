package br.com.sanittas.app.mail;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class EventManager {
    List<EventListener> listeners;

    public EventManager() {
        this.listeners = new ArrayList<>();
    }

    public void subscribe(EventListener listener) {
        this.listeners.add(listener);
    }

    public void unsubscribe(EventListener listener) {
        this.listeners.remove(listener);
    }

    public void notificar() {
        for (EventListener listener : listeners) {
            listener.update();
        }
    }
}
