package it.epicode.eventbooking.dto;


import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class EventoDTO {
    @NotBlank
    private String titolo;

    @NotBlank
    private String descrizione;

    @Future
    private LocalDateTime data;

    @NotBlank
    private String luogo;

    @Min(1)
    private Integer postiTotali;
}