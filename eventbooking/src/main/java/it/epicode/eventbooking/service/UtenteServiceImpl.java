package it.epicode.eventbooking.service;

import it.epicode.eventbooking.dto.request.RegisterRequest;
import it.epicode.eventbooking.eccezioni.UtenteEsistenteException;
import it.epicode.eventbooking.modelli.Utente;
import it.epicode.eventbooking.repository.UtenteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UtenteServiceImpl implements UtenteService {

    private final UtenteRepository utenteRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public Utente registraUtente(RegisterRequest request) {
        // Verifica esistenza username/email
        if (utenteRepository.existsByUsername(request.getUsername())) {
            throw new UtenteEsistenteException("Username già esistente");
        }
        if (utenteRepository.existsByEmail(request.getEmail())) {
            throw new UtenteEsistenteException("Email già registrata");
        }

        // Crea nuovo utente
        Utente utente = new Utente();
        utente.setUsername(request.getUsername());
        utente.setEmail(request.getEmail());
        utente.setPassword(passwordEncoder.encode(request.getPassword()));

        // Assegnazione ruolo con controllo di sicurezza
        Utente.Ruolo ruolo = (request.getRuolo() != null) ? request.getRuolo() : Utente.Ruolo.ROLE_USER;
        utente.setRuolo(ruolo);

        return utenteRepository.save(utente);
    }

    @Override
    public Utente getUtenteCorrente() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        return utenteRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Utente non trovato"));
    }

    @Override
    public boolean isOrganizzatore(Utente utente) {
        return utente.getRuolo() == Utente.Ruolo.ROLE_ORGANIZER;
    }
}