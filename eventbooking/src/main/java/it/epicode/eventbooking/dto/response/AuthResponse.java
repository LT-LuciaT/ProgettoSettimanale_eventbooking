package it.epicode.eventbooking.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

@Getter
@Setter
@AllArgsConstructor
public class AuthResponse {
    private String token;
    private String message;
    private Instant timestamp = Instant.now();

    public AuthResponse(String token, String message) {
        this.token = token;
        this.message = message;
    }
}