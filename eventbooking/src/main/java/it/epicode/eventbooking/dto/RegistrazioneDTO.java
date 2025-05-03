package it.epicode.eventbooking.dto;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class RegistrazioneDTO {
    @NotBlank
    @Size(max = 50)
    @Email
    private String email;

    @NotBlank
    @Size(min = 6, max = 40)
    private String password;

    @NotBlank
    @Size(max = 50)
    private String nome;

    @NotBlank
    @Size(max = 50)
    private String cognome;

    private String ruolo;
}