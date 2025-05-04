package it.epicode.eventbooking.eccezioni;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class EventoAlCompletoException extends RuntimeException {
    public EventoAlCompletoException(String message) {
        super(message);
    }
}