package it.epicode.eventbooking.dto;

import lombok.Data;

@Data
public class RispostaJWT {
    private String token;
    private String tipo = "Bearer";
    private Long id;
    private String email;
    private String ruolo;

    public RispostaJWT(String token, Long id, String email, String ruolo) {
        this.token = token;
        this.id = id;
        this.email = email;
        this.ruolo = ruolo;
    }
}