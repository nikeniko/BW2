package Back_end.BW2.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;
import java.util.UUID;


@Entity
@Table(name = "stati")
@Getter
@Setter
@NoArgsConstructor
@ToString
public class StatoFattura {
    @Id
    @GeneratedValue
    @Setter(AccessLevel.NONE)
    private UUID id;
    private String stato;

    @OneToMany(mappedBy = "statoFattura", fetch = FetchType.EAGER)
    @JsonIgnore
    private List<Fattura> fatturaList;

    // COSTRUTTORI

    public StatoFattura(String stato) {
        this.stato = stato;
    }
}
