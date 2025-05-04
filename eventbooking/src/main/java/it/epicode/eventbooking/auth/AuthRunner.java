package it.epicode.eventbooking.auth;

import it.epicode.eventbooking.dto.request.RegisterRequest;

import it.epicode.eventbooking.modelli.AppUser;
import it.epicode.eventbooking.modelli.Utente;
import it.epicode.eventbooking.service.AppUserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Component
@RequiredArgsConstructor
public class AuthRunner implements ApplicationRunner {
    private final AppUserService appUserService;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    @Override
    public void run(ApplicationArguments args) {
        createUserIfNotExists("admin", "admin@example.com", "adminpassword", Utente.Ruolo.ROLE_ADMIN);
        createUserIfNotExists("user", "user@example.com", "userpassword", Utente.Ruolo.ROLE_USER);
        createUserIfNotExists("organizzatore", "organizzatore@example.com", "organizzatorepassword", Utente.Ruolo.ROLE_ORGANIZER);
    }

    private void createUserIfNotExists(String username, String email, String password, Utente.Ruolo ruolo) {
        try {
            if (appUserService.findByUsername(username).isEmpty()) {
                log.info("Creating user {}...", username);
                RegisterRequest request = new RegisterRequest();
                request.setUsername(username);
                request.setEmail(email);
                request.setPassword(password);
                request.setRuolo(ruolo);

                AppUser user = appUserService.registerUser(request);
                log.info("User {} created with ID {}", username, user.getId());
            } else {
                log.info("User {} already exists", username);
            }
        } catch (Exception e) {
            log.error("Error creating user {}: {}", username, e.getMessage());
        }
    }
}