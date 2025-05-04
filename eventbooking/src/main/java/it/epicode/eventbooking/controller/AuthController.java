package it.epicode.eventbooking.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import it.epicode.eventbooking.dto.request.LoginRequest;
import it.epicode.eventbooking.dto.request.RegisterRequest;
import it.epicode.eventbooking.dto.response.AuthResponse;
import it.epicode.eventbooking.eccezioni.UserRegistrationException;
import it.epicode.eventbooking.service.AppUserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.Instant;
import java.util.Collections;


@Slf4j
@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@Tag(name = "Authentication", description = "Gestione autenticazione e registrazione utenti")
public class AuthController {

    private final AppUserService appUserService;

    @Operation(summary = "Registra nuovo utente", description = "Endpoint per la registrazione di nuovi utenti")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Registrazione avvenuta con successo",
                    content = @Content(schema = @Schema(implementation = AuthResponse.class))),
            @ApiResponse(responseCode = "400", description = "Dati della richiesta non validi"),
            @ApiResponse(responseCode = "409", description = "Username o email già in uso")
    })
    @PostMapping("/register")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> register(@Valid @RequestBody RegisterRequest request) {
        try {
            log.info("Registrazione in corso per username: {}", request.getUsername());

            var user = appUserService.registerUser(request);
            String token = appUserService.authenticate(request.getUsername(), request.getPassword());

            AuthResponse response = new AuthResponse(
                    token,
                    "Registrazione completata con successo",
                    Instant.now().plusSeconds(3600) // Scadenza token 1 ora
            );

            log.info("Utente {} registrato con successo", request.getUsername());
            return ResponseEntity.status(HttpStatus.CREATED)
                    .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                    .body(response);

        } catch (UserRegistrationException e) {
            log.warn("Errore registrazione utente {}: {}", request.getUsername(), e.getMessage());
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body(Collections.singletonMap("error", e.getMessage()));
        } catch (Exception e) {
            log.error("Errore imprevisto durante la registrazione", e);
            return ResponseEntity.internalServerError()
                    .body(Collections.singletonMap("error", "Errore durante la registrazione"));
        }
    }

    @Operation(summary = "Login utente", description = "Endpoint per l'autenticazione degli utenti")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Login effettuato con successo",
                    content = @Content(schema = @Schema(implementation = AuthResponse.class))),
            @ApiResponse(responseCode = "400", description = "Dati della richiesta non validi"),
            @ApiResponse(responseCode = "401", description = "Credenziali non valide")
    })
    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody LoginRequest request) {
        try {
            log.info("Tentativo di login per username: {}", request.getUsername());

            String token = appUserService.authenticate(request.getUsername(), request.getPassword());

            AuthResponse response = new AuthResponse(
                    token,
                    "Login effettuato con successo",
                    Instant.now().plusSeconds(3600) // Scadenza token 1 ora
            );

            log.info("Login effettuato per username: {}", request.getUsername());
            return ResponseEntity.ok()
                    .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                    .body(response);

        } catch (BadCredentialsException e) {
            log.warn("Credenziali non valide per username: {}", request.getUsername());
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(Collections.singletonMap("error", "Username o password non validi"));
        } catch (Exception e) {
            log.error("Errore durante il login per username: {}", request.getUsername(), e);
            return ResponseEntity.internalServerError()
                    .body(Collections.singletonMap("error", "Errore durante l'autenticazione"));
        }
    }
}