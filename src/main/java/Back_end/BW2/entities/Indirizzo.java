package Back_end.BW2.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;
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
    @JoinColumn(name = "comune")
    private Comune comune;

    @OneToMany(mappedBy = "indirizzoSedeLegale")
    @JsonIgnore
    private List<Cliente> clienteListSedeLegale;

    @OneToMany(mappedBy = "indirizzoSedeOperativa")
    @JsonIgnore
    private List<Cliente> clienteListSedeOperativa;

    public Indirizzo(String via, String civico, String localita, String cap, Comune comune) {
        this.via = via;
        this.civico = civico;
        this.localita = localita;
        this.cap = cap;
        this.comune = comune;
    }
}