package Back_end.BW2.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;
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

    @OneToMany(mappedBy = "comune")
    @JsonIgnore
    private List<Indirizzo> indirizzoList;


}
