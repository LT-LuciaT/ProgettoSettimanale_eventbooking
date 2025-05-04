package it.epicode.eventbooking.controller;


import io.swagger.v3.oas.annotations.parameters.RequestBody;
import it.epicode.eventbooking.dto.request.PrenotazioneRequest;
import it.epicode.eventbooking.dto.response.PrenotazioneResponse;
import it.epicode.eventbooking.service.PrenotazioneService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/prenotazioni")
public class PrenotazioneController {
    private final PrenotazioneService prenotazioneService;

    public PrenotazioneController(PrenotazioneService prenotazioneService) {
        this.prenotazioneService = prenotazioneService;
    }

    @GetMapping
    public ResponseEntity<List<PrenotazioneResponse>> getPrenotazioniUtente() {
        return ResponseEntity.ok(prenotazioneService.getPrenotazioniUtente());
    }

    @PostMapping
    public ResponseEntity<PrenotazioneResponse> prenotaEvento(
            @RequestBody PrenotazioneRequest request) {
        return ResponseEntity.ok(prenotazioneService.prenotaEvento(request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> cancellaPrenotazione(@PathVariable Long id) {
        prenotazioneService.cancellaPrenotazione(id);
        return ResponseEntity.noContent().build();
    }
}
