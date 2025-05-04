package it.epicode.eventbooking.eccezioni;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT) // HTTP 409
public class UtenteEsistenteException extends RuntimeException {
    public UtenteEsistenteException(String message) {
        super(message);
    }
}