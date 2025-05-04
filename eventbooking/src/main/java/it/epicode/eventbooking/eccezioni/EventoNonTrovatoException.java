package it.epicode.eventbooking.eccezioni;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class EventoNonTrovatoException extends RuntimeException {
    public EventoNonTrovatoException(String message) {
        super(message);
    }
    }