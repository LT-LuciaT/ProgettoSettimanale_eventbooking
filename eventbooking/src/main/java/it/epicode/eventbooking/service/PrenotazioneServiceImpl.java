package it.epicode.eventbooking.service;

import it.epicode.eventbooking.dto.request.PrenotazioneRequest;
import it.epicode.eventbooking.dto.response.PrenotazioneResponse;
import it.epicode.eventbooking.eccezioni.EventoAlCompletoException;
import it.epicode.eventbooking.eccezioni.EventoNonTrovatoException;
import it.epicode.eventbooking.eccezioni.AccessoNonAutorizzatoException;
import it.epicode.eventbooking.modelli.Evento;
import it.epicode.eventbooking.modelli.Prenotazione;
import it.epicode.eventbooking.modelli.Utente;
import it.epicode.eventbooking.repository.EventoRepository;
import it.epicode.eventbooking.repository.PrenotazioneRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PrenotazioneServiceImpl implements PrenotazioneService {
    private final PrenotazioneRepository prenotazioneRepository;
    private final EventoRepository eventoRepository;
    private final UtenteService utenteService;

    @Override
    public PrenotazioneResponse prenotaEvento(PrenotazioneRequest request) {
        Utente utenteCorrente = utenteService.getUtenteCorrente();
        Evento evento = eventoRepository.findById(request.getEventoId())
                .orElseThrow(() -> new EventoNonTrovatoException("Evento non trovato"));

        if (request.getPosti() <= 0) {
            throw new IllegalArgumentException("Numero di posti non valido");
        }

        if (evento.getPostiDisponibili() < request.getPosti()) {
            throw new EventoAlCompletoException("Posti insufficienti");
        }

        Prenotazione prenotazione = new Prenotazione();
        prenotazione.setUtente(utenteCorrente);
        prenotazione.setEvento(evento);
        prenotazione.setPostiPrenotati(request.getPosti());
        prenotazione.setDataPrenotazione(LocalDateTime.now());

        evento.setPostiDisponibili(evento.getPostiDisponibili() - request.getPosti());
        eventoRepository.save(evento);

        Prenotazione savedPrenotazione = prenotazioneRepository.save(prenotazione);
        return new PrenotazioneResponse(savedPrenotazione);
    }

    @Override
    public List<PrenotazioneResponse> getPrenotazioniUtente() {
        Utente utenteCorrente = utenteService.getUtenteCorrente();
        return prenotazioneRepository.findByUtenteId(utenteCorrente.getId())
                .stream()
                .map(PrenotazioneResponse::new)
                .collect(Collectors.toList());
    }

    @Override
    public void cancellaPrenotazione(Long prenotazioneId) {
        Utente utenteCorrente = utenteService.getUtenteCorrente();
        Prenotazione prenotazione = prenotazioneRepository.findById(prenotazioneId)
                .orElseThrow(() -> new EventoNonTrovatoException("Prenotazione non trovata"));

        if (!prenotazione.getUtente().getId().equals(utenteCorrente.getId())) {
            throw new AccessoNonAutorizzatoException("Puoi cancellare solo le tue prenotazioni");
        }

        Evento evento = prenotazione.getEvento();
        evento.setPostiDisponibili(evento.getPostiDisponibili() + prenotazione.getPostiPrenotati());
        eventoRepository.save(evento);

        prenotazioneRepository.delete(prenotazione);
    }
}
