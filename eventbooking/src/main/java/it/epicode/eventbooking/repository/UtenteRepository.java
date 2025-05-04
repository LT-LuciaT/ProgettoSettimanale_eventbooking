package it.epicode.eventbooking.repository;


import it.epicode.eventbooking.modelli.Utente;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UtenteRepository extends JpaRepository<Utente, Long> {
    Optional<Utente> findByUsername(String username);
    Optional<Utente> findByEmail(String email);
    boolean existsByUsername(String username);
    boolean existsByEmail(String email);
}