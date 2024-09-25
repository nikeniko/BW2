package Back_end.BW2.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Entity
@Table(name = "comuni")
@Getter
@Setter
@ToString
@NoArgsConstructor
public class Comune {

    @Id

    @Setter(AccessLevel.NONE)
    @GeneratedValue
    private UUID id;

    private String codiceProvincia;
    private String progressivoComune;
    private String nome;

    @ManyToOne
    @JoinColumn(name = "provincia_id")
    private Provincia provincia;


}
