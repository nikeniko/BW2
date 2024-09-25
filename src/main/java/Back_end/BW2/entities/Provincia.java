package Back_end.BW2.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

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

    private UUID id;

    private String nome;
    private String sigla;
    private String regione;

    @JsonIgnore
    @OneToMany(mappedBy = "provincia")
    private List<Comune> comune;


}