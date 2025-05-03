package it.epicode.eventbooking.modelli;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

@Entity
@Table(name = "ruoli")
@Data
@NoArgsConstructor
public class Ruolo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(length = 20)
    private ERuolo nome;

    public Ruolo(ERuolo nome) {
        this.nome = nome;
    }
}