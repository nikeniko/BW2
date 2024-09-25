package Back_end.BW2.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Table(name = "indirizzi")

public class Indirizzo {
    @Id
    @GeneratedValue
    @Setter(AccessLevel.NONE)
    UUID id;

    private String via;
    private String civico;
    private String localita;
    private String cap;

    @ManyToOne
    private Comune comune;

    @ManyToOne
    private Cliente cliente;
}