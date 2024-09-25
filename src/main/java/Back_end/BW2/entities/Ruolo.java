package Back_end.BW2.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

import java.util.UUID;

@Entity
@Table(name = "ruoli")
@Getter
@Setter
@NoArgsConstructor
@ToString
public class Ruolo {

    //    @ManyToMany(mappedBy = "ruoli")
//    List<Utente> utenteList;
    @Id
    @GeneratedValue
    @Setter(AccessLevel.NONE)
    private UUID id;
    private String ruolo;

    public Ruolo(String ruolo) {
        this.ruolo = ruolo;
    }
}
