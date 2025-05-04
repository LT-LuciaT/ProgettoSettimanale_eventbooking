package it.epicode.eventbooking.dto.response;

import it.epicode.eventbooking.modelli.Evento;
import lombok.Data;
import java.time.LocalDateTime;

@Data
public class EventoResponse {
    private Long id;
    private String titolo;
    private String descrizione;
    private LocalDateTime dataOra;
    private String luogo;
    private int postiTotali;
    private int postiDisponibili;
    private Long organizzatoreId;
    private String organizzatoreUsername;

    public EventoResponse(Evento evento) {
        this.id = evento.getId();
        this.titolo = evento.getTitolo();
        this.descrizione = evento.getDescrizione();
        this.dataOra = evento.getDataOra();
        this.luogo = evento.getLuogo();
        this.postiTotali = evento.getPostiTotali();
        this.postiDisponibili = evento.getPostiDisponibili();
        this.organizzatoreId = evento.getOrganizzatore().getId();
        this.organizzatoreUsername = evento.getOrganizzatore().getUsername();
    }
}