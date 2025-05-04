package it.epicode.eventbooking.service;


import it.epicode.eventbooking.dto.request.CreateEventoRequest;
import it.epicode.eventbooking.dto.request.UpdateEventoRequest;
import it.epicode.eventbooking.dto.response.EventoResponse;

import java.util.List;

public interface EventoService {
    EventoResponse creaEvento(CreateEventoRequest request);
    EventoResponse aggiornaEvento(Long eventoId, UpdateEventoRequest request);
    void eliminaEvento(Long eventoId);
    List<EventoResponse> getAllEventi();
    List<EventoResponse> getEventiByOrganizzatore(Long organizzatoreId);
    EventoResponse getEventoById(Long eventoId);
}
