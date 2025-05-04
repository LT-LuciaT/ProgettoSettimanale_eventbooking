package it.epicode.eventbooking.service;


import it.epicode.eventbooking.dto.request.PrenotazioneRequest;
import it.epicode.eventbooking.dto.response.PrenotazioneResponse;

import java.util.List;

public interface PrenotazioneService {
    PrenotazioneResponse prenotaEvento(PrenotazioneRequest request);
    List<PrenotazioneResponse> getPrenotazioniUtente();
    void cancellaPrenotazione(Long prenotazioneId);
}