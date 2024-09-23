package Back_end.BW2.entities;

import jakarta.persistence.*;
import lombok.*;

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

    private UUID id;

    private String nome;
    private String sigla;
    private String regione;

    @ManyToOne
    @JoinColumn(name = "comune_id", referencedColumnName = "id")
    private Comune comune;


}