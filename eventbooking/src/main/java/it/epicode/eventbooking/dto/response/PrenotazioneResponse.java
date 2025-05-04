package it.epicode.eventbooking.dto.response;

import it.epicode.eventbooking.modelli.Prenotazione;
import lombok.Data;

import java.time.LocalDateTime;


@Data
public class PrenotazioneResponse {
    private Long id;
    private Long eventoId;
    private String eventoTitolo;
    private LocalDateTime dataPrenotazione;
    private int postiPrenotati;
    private Long utenteId;
    private String utenteUsername;

    public PrenotazioneResponse(Prenotazione prenotazione) {
        this.id = prenotazione.getId();
        this.eventoId = prenotazione.getEvento().getId();
        this.eventoTitolo = prenotazione.getEvento().getTitolo();
        this.dataPrenotazione = prenotazione.getDataPrenotazione();
        this.postiPrenotati = prenotazione.getPostiPrenotati();
        this.utenteId = prenotazione.getUtente().getId();
        this.utenteUsername = prenotazione.getUtente().getUsername();
    }
}