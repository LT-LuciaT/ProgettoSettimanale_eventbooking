package it.epicode.eventbooking.dto.request;


import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class PrenotazioneRequest {
    @NotNull(message = "L'ID evento è obbligatorio")
    private Long eventoId;

    @Min(value = 1, message = "Deve prenotare almeno un posto")
    private int posti;
}
