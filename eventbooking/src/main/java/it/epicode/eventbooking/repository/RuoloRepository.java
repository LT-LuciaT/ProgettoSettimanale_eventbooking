package it.epicode.eventbooking.repository;

import it.epicode.eventbooking.modelli.ERuolo;
import it.epicode.eventbooking.modelli.Ruolo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RuoloRepository extends JpaRepository<Ruolo, Long> {
    Optional<Ruolo> findByNome(ERuolo nome);
}