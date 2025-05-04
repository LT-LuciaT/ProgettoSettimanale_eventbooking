package it.epicode.eventbooking.dto.request;


import it.epicode.eventbooking.modelli.Utente;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RegisterRequest {
    @NotBlank(message = "Username obbligatorio")
    private String username;

    @NotBlank(message = "Email obbligatoria")
    @Email(message = "Formato email non valido")
    private String email;

    @NotBlank(message = "Password obbligatoria")
    @Size(min = 6, message = "La password deve avere almeno 6 caratteri")
    private String password;

    @NotNull(message = "Ruolo obbligatorio")
    private Utente.Ruolo ruolo;
}