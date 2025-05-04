package it.epicode.eventbooking.dto.request;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class CreateEventoRequest {
    @NotBlank(message = "Il titolo è obbligatorio")
    private String titolo;

    @NotBlank(message = "La descrizione è obbligatoria")
    private String descrizione;

    @Future(message = "La data deve essere futura")
    private LocalDateTime dataOra;

    @NotBlank(message = "Il luogo è obbligatorio")
    private String luogo;

    @Min(value = 1, message = "Deve esserci almeno un posto")
    private int postiTotali;
}
