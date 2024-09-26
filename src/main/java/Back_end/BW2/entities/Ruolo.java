package Back_end.BW2.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
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


    @Id
    @GeneratedValue
    @Setter(AccessLevel.NONE)
    private UUID id;
    private String ruolo;

    @ManyToMany(mappedBy = "ruoli")
    @JsonIgnore
    private List<Utente> utenteList;

    public Ruolo(String ruolo) {
        this.ruolo = ruolo;
    }

    public Ruolo getRuolo() {
        return new Ruolo(ruolo);
    }
}
