package it.epicode.eventbooking.repository;

import it.epicode.eventbooking.modelli.Evento;
import it.epicode.eventbooking.modelli.Utente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EventoRepository extends JpaRepository<Evento, Long> {
    List<Evento> findAllByOrderByDataAsc();
    List<Evento> findByOrganizzatore(Utente organizzatore);
    boolean existsByIdAndOrganizzatore(Long id, Utente organizzatore);
}