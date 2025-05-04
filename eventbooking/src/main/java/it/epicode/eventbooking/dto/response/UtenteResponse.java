package it.epicode.eventbooking.dto.response;



import it.epicode.eventbooking.modelli.Utente;
import lombok.Data;
@Data
public class UtenteResponse {
    private Long id;
    private String username;
    private String email;
    private Utente.Ruolo ruolo;

    public UtenteResponse(Utente utente) {
        this.id = utente.getId();
        this.username = utente.getUsername();
        this.email = utente.getEmail();
        this.ruolo = utente.getRuolo();
    }
}