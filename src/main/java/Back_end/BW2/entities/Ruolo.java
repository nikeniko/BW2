package Back_end.BW2.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "ruoli")
@Getter
@Setter
@NoArgsConstructor
@ToString
public class Ruolo {

    @ManyToMany(mappedBy = "ruoli")
    List<Utente> utenteList;
    @Id
    @GeneratedValue
    @Setter(AccessLevel.NONE)
    private UUID id;
    private String ruolo;

    public Ruolo(String ruolo) {
        this.ruolo = ruolo;
    }
}
