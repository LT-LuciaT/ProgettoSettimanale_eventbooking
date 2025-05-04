package it.epicode.eventbooking.controller;

import it.epicode.eventbooking.dto.request.LoginRequest;
import it.epicode.eventbooking.dto.request.RegisterRequest;
import it.epicode.eventbooking.dto.response.AuthResponse;
import it.epicode.eventbooking.service.AppUserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AppUserService appUserService;

    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register(@Valid @RequestBody RegisterRequest request) {
        // Registra l'utente e genera direttamente il token
        var user = appUserService.registerUser(request);
        String token = appUserService.authenticate(
                request.getUsername(),
                request.getPassword()
        );

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new AuthResponse(token, "Registrazione avvenuta con successo"));
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@Valid @RequestBody LoginRequest request) {
        String token = appUserService.authenticate(
                request.getUsername(),
                request.getPassword()
        );
        return ResponseEntity.ok(new AuthResponse(token, "Login effettuato con successo"));
    }
}