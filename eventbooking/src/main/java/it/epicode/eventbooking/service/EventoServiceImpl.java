package it.epicode.eventbooking.service;

import it.epicode.eventbooking.dto.request.CreateEventoRequest;
import it.epicode.eventbooking.dto.request.UpdateEventoRequest;
import it.epicode.eventbooking.dto.response.EventoResponse;
import it.epicode.eventbooking.eccezioni.AccessoNonAutorizzatoException;
import it.epicode.eventbooking.eccezioni.EventoNonTrovatoException;
import it.epicode.eventbooking.modelli.Evento;
import it.epicode.eventbooking.modelli.Utente;
import it.epicode.eventbooking.repository.EventoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EventoServiceImpl implements EventoService {
    private final EventoRepository eventoRepository;
    private final UtenteService utenteService;

    @Override
    public EventoResponse creaEvento(CreateEventoRequest request) {
        Utente utenteCorrente = utenteService.getUtenteCorrente();

        if (!utenteService.isOrganizzatore(utenteCorrente)) {
            throw new AccessoNonAutorizzatoException("Solo gli organizzatori possono creare eventi");
        }

        Evento evento = new Evento();
        evento.setTitolo(request.getTitolo());
        evento.setDescrizione(request.getDescrizione());
        evento.setDataOra(request.getDataOra());
        evento.setLuogo(request.getLuogo());
        evento.setPostiTotali(request.getPostiTotali());
        evento.setPostiDisponibili(request.getPostiTotali());
        evento.setOrganizzatore(utenteCorrente);

        Evento savedEvento = eventoRepository.save(evento);
        return new EventoResponse(savedEvento);
    }

    @Override
    public EventoResponse aggiornaEvento(Long eventoId, UpdateEventoRequest request) {
        Utente utenteCorrente = utenteService.getUtenteCorrente();
        Evento evento = eventoRepository.findById(eventoId)
                .orElseThrow(() -> new EventoNonTrovatoException("Evento non trovato"));

        if (!evento.getOrganizzatore().getId().equals(utenteCorrente.getId())) {
            throw new AccessoNonAutorizzatoException("Puoi modificare solo i tuoi eventi");
        }

        evento.setTitolo(request.getTitolo());
        evento.setDescrizione(request.getDescrizione());
        evento.setDataOra(request.getDataOra());
        evento.setLuogo(request.getLuogo());

        Evento updatedEvento = eventoRepository.save(evento);
        return new EventoResponse(updatedEvento);
    }

    @Override
    public void eliminaEvento(Long eventoId) {
        Utente utenteCorrente = utenteService.getUtenteCorrente();
        Evento evento = eventoRepository.findById(eventoId)
                .orElseThrow(() -> new EventoNonTrovatoException("Evento non trovato"));

        if (!evento.getOrganizzatore().getId().equals(utenteCorrente.getId())) {
            throw new AccessoNonAutorizzatoException("Puoi eliminare solo i tuoi eventi");
        }

        eventoRepository.delete(evento);
    }

    @Override
    public List<EventoResponse> getAllEventi() {
        return eventoRepository.findAll()
                .stream()
                .map(EventoResponse::new)
                .collect(Collectors.toList());
    }

    @Override
    public List<EventoResponse> getEventiByOrganizzatore(Long organizzatoreId) {
        return eventoRepository.findByOrganizzatoreId(organizzatoreId)
                .stream()
                .map(EventoResponse::new)
                .collect(Collectors.toList());
    }

    @Override
    public EventoResponse getEventoById(Long eventoId) {
        Evento evento = eventoRepository.findById(eventoId)
                .orElseThrow(() -> new EventoNonTrovatoException("Evento non trovato"));
        return new EventoResponse(evento);
    }
}