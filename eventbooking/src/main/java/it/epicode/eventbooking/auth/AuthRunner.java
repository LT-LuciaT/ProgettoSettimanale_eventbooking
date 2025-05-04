package it.epicode.eventbooking.auth;

import it.epicode.eventbooking.dto.request.RegisterRequest;

import it.epicode.eventbooking.modelli.Utente;
import it.epicode.eventbooking.service.AppUserService;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import static it.epicode.eventbooking.modelli.Utente.Ruolo.ROLE_USER;

@Component
public class AuthRunner implements ApplicationRunner {
    private final AppUserService appUserService;

    public AuthRunner(AppUserService appUserService) {
        this.appUserService = appUserService;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        // Admin
        if (appUserService.findByUsername("admin").isEmpty()) {
            RegisterRequest adminRequest = new RegisterRequest();
            adminRequest.setUsername("admin");
            adminRequest.setEmail("admin@example.com");
            adminRequest.setPassword("adminpassword");
            adminRequest.setRuolo(Utente.Ruolo.ROLE_ADMIN);
            appUserService.registerUser(adminRequest);
        }

        // User normale
        if (appUserService.findByUsername("user").isEmpty()) {
            RegisterRequest userRequest = new RegisterRequest();
            userRequest.setUsername("user");
            userRequest.setEmail("user@example.com");
            userRequest.setPassword("userpassword");
            userRequest.setRuolo(Utente.Ruolo.ROLE_USER);
            appUserService.registerUser(userRequest);
        }

        // Seller/Organizzatore
        if (appUserService.findByUsername("organizzatore").isEmpty()) {
            RegisterRequest sellerRequest = new RegisterRequest();
            sellerRequest.setUsername("organizzatore");
            sellerRequest.setEmail("organizzatore@example.com");
            sellerRequest.setPassword("organizzatorepassword");
            sellerRequest.setRuolo(Utente.Ruolo.ROLE_ORGANIZER);
            appUserService.registerUser(sellerRequest);
        }
    }
}