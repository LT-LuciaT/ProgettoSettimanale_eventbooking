package it.epicode.eventbooking.controller;

import it.epicode.eventbooking.dto.request.CreateEventoRequest;
import it.epicode.eventbooking.dto.request.UpdateEventoRequest;
import it.epicode.eventbooking.dto.response.EventoResponse;
import it.epicode.eventbooking.service.EventoService;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<EventoResponse> creaEvento(@RequestBody CreateEventoRequest request) {
        return ResponseEntity.ok(eventoService.creaEvento(request));
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