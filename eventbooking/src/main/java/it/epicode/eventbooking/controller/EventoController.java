package it.epicode.eventbooking.controller;

import it.epicode.eventbooking.dto.request.CreateEventoRequest;
import it.epicode.eventbooking.dto.request.UpdateEventoRequest;
import it.epicode.eventbooking.dto.response.EventoResponse;
import it.epicode.eventbooking.service.EventoService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/eventi")
public class EventoController {
    private final EventoService eventoService;

    public EventoController(EventoService eventoService) {
        this.eventoService = eventoService;
    }

    @GetMapping
    public ResponseEntity<List<EventoResponse>> getAllEventi() {
        return ResponseEntity.ok(eventoService.getAllEventi());
    }

    @GetMapping("/{id}")
    public ResponseEntity<EventoResponse> getEventoById(@PathVariable Long id) {
        return ResponseEntity.ok(eventoService.getEventoById(id));
    }

    @GetMapping("/organizzatore/{organizzatoreId}")
    public ResponseEntity<List<EventoResponse>> getEventiByOrganizzatore(@PathVariable Long organizzatoreId) {
        return ResponseEntity.ok(eventoService.getEventiByOrganizzatore(organizzatoreId));
    }

    @PostMapping
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_ORGANIZER')")
    public ResponseEntity<?> creaEvento(
            @RequestBody @Valid CreateEventoRequest request,
            @AuthenticationPrincipal UserDetails userDetails) {

        try {
            // Imposta l'organizzatore dall'utente autenticato
            request.setOrganizzatoreUsername(userDetails.getUsername());
            EventoResponse response = eventoService.creaEvento(request);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.badRequest().body("Organizzatore non trovato");
        } catch (Exception e) {
            return ResponseEntity.internalServerError()
                    .body("Errore durante la creazione dell'evento");
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<EventoResponse> aggiornaEvento(
            @PathVariable Long id,
            @RequestBody UpdateEventoRequest request) {
        return ResponseEntity.ok(eventoService.aggiornaEvento(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminaEvento(@PathVariable Long id) {
        eventoService.eliminaEvento(id);
        return ResponseEntity.noContent().build();
    }
}