package Back_end.BW2.entities;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "province")
@Getter
@Setter
@ToString
@NoArgsConstructor
public class Provincia {

    @Id
    @GeneratedValue
    @Setter(AccessLevel.NONE)
    private UUID id;

    private String nome;
    private String sigla;
    private String regione;

    @OneToOne(mappedBy = "provincia")
    @JsonIgnore
    private List<Comune> comune;


}

