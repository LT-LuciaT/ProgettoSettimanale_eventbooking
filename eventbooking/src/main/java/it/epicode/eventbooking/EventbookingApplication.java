package it.epicode.eventbooking;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication

@EntityScan(basePackages = "it.epicode.eventbooking.modelli")
@EnableJpaRepositories(basePackages = "it.epicode.eventbooking.repository")
public class EventbookingApplication {

	public static void main(String[] args) {
		SpringApplication.run(EventbookingApplication.class, args);
	}

}
