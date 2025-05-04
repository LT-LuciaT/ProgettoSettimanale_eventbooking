package it.epicode.eventbooking.service;


import it.epicode.eventbooking.dto.request.RegisterRequest;
import it.epicode.eventbooking.modelli.Utente;

public interface UtenteService {
    Utente registraUtente(RegisterRequest request);
    Utente getUtenteCorrente();
    boolean isOrganizzatore(Utente utente);
}