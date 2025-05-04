package it.epicode.eventbooking.eccezioni;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.FORBIDDEN)
public class AccessoNonAutorizzatoException extends RuntimeException {
    public AccessoNonAutorizzatoException(String message) {
        super(message);
    }
}