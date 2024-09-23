package Back_end.BW2.entities;

import Back_end.BW2.enums.StatoFatture;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name = "fatture")
@Setter
@Getter
@NoArgsConstructor
public class Fattura {
    @Id
    @GeneratedValue
    @Setter(AccessLevel.NONE)
    private UUID id;

    @Column(name = "data")
    private LocalDate data;
    @Column(name = "importo")
    private double importo;

    @Column(name = "numero_fattura")
    private int numeroFattura;

    @Column(name = "stato_fatture")
    @Enumerated(EnumType.STRING)
    private StatoFatture statoFatture;

    @ManyToOne
    @JoinColumn(name = "cliente")
    private Cliente cliente;

    // COSTRUTTORI

    public Fattura(LocalDate data, double importo, int numeroFattura, StatoFatture statoFatture, Cliente cliente) {
        this.data = data;
        this.importo = importo;
        this.numeroFattura = numeroFattura;
        this.statoFatture = statoFatture;
        this.cliente = cliente;
    }

    // TO STRING


    @Override
    public String toString() {
        return "Fattura{" +
                "id=" + id +
                ", data=" + data +
                ", importo=" + importo +
                ", numeroFattura=" + numeroFattura +
                ", statoFatture=" + statoFatture +
                ", cliente=" + cliente +
                '}';
    }
}
