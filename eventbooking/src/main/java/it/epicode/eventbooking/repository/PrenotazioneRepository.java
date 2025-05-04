package it.epicode.eventbooking.repository;


import it.epicode.eventbooking.modelli.Prenotazione;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PrenotazioneRepository extends JpaRepository<Prenotazione, Long> {
    List<Prenotazione> findByUtenteId(Long utenteId);
    List<Prenotazione> findByEventoId(Long eventoId);
    boolean existsByUtenteIdAndEventoId(Long utenteId, Long eventoId);
}