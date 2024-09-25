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
    @GeneratedValue
    @Setter(AccessLevel.NONE)
    private UUID id;
    private String nome;
    @ManyToOne
    @JoinColumn(name = "provincia_id")
    private Provincia provincia;

    public Comune(String nome) {
        this.nome = nome;
    }
}