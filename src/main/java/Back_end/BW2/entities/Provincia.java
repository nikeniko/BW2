package Back_end.BW2.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Entity
@Table(name = "province")
@Getter
@Setter
@NoArgsConstructor
public class Provincia {
    @Id
    @GeneratedValue
    @Setter(AccessLevel.NONE)
    private UUID id;

    private String sigla;
    private String nome;
    private String regione;

    public Provincia(String sigla, String nome, String regione) {
        this.sigla = sigla;
        this.nome = nome;
        this.regione = regione;
    }
}
