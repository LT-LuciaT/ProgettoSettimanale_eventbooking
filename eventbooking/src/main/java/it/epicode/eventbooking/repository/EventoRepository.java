package it.epicode.eventbooking.repository;

import it.epicode.eventbooking.modelli.Evento;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface EventoRepository extends JpaRepository<Evento, Long> {
    List<Evento> findByDataOraAfter(LocalDateTime dataOra);
    List<Evento> findByOrganizzatoreId(Long organizzatoreId);
}