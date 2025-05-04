package it.epicode.eventbooking.service;

import it.epicode.eventbooking.auth.JwtTokenUtil;
import it.epicode.eventbooking.dto.request.RegisterRequest;
import it.epicode.eventbooking.modelli.AppUser;
import it.epicode.eventbooking.repository.AppUserRepository;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import lombok.extern.slf4j.Slf4j;
import java.util.Optional;
import java.util.Set;

@Slf4j
@Service
@RequiredArgsConstructor
public class AppUserService {
    private final AppUserRepository appUserRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtTokenUtil jwtTokenUtil;

    public AppUser registerUser(RegisterRequest request) {
        log.debug("Inizio registrazione nuovo utente: {}", request.getUsername());

        if (appUserRepository.existsByUsername(request.getUsername())) {
            log.warn("Username {} già in uso", request.getUsername());
            throw new EntityExistsException("Username già in uso");
        }

        if (appUserRepository.existsByEmail(request.getEmail())) {
            log.warn("Email {} già in uso", request.getEmail());
            throw new EntityExistsException("Email già in uso");
        }

        log.debug("Creazione nuovo utente {}...", request.getUsername());
        AppUser appUser = new AppUser();
        appUser.setUsername(request.getUsername());
        appUser.setEmail(request.getEmail());
        appUser.setPassword(passwordEncoder.encode(request.getPassword()));
        appUser.setRoles(Set.of(request.getRuolo()));

        AppUser savedUser = appUserRepository.save(appUser);
        log.info("Utente {} registrato con successo con ID {}",
                savedUser.getUsername(),
                savedUser.getId());

        return savedUser;
    }
    public Optional<AppUser> findByUsername(String username) {
        return appUserRepository.findByUsername(username);
    }

    public String authenticate(String username, String password) {
        log.debug("Tentativo di login per username: {}", username);
        try {
            // DEBUG: Verifica preliminare
            AppUser user = appUserRepository.findByUsername(username)
                    .orElseThrow(() -> {
                        log.error("Utente {} non trovato", username);
                        return new UsernameNotFoundException("Utente non trovato");
                    });

            log.debug("Hash password DB per {}: {}", username, user.getPassword());
            log.debug("Password raw input: {}", password);

            // Autenticazione
            Authentication auth = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(username, password)
            );

            return jwtTokenUtil.generateToken((UserDetails) auth.getPrincipal());
        } catch (Exception e) {
            log.error("ERRORE durante login per {}: {}", username, e.toString());
            throw new SecurityException("Errore autenticazione", e);
        }
    }

    public AppUser loadUserByUsername(String username) {
        return appUserRepository.findByUsername(username)
                .orElseThrow(() -> new EntityNotFoundException("Utente non trovato con username: " + username));
    }
}