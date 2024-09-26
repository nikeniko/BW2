package Back_end.BW2.entities;

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

//    @Column(name = "stato_fatture")
//    @Enumerated(EnumType.STRING)
//    private StatoFatture statoFatture;

    @ManyToOne
    @JoinColumn(name = "cliente")
    private Cliente cliente;

    @ManyToOne
    @JoinColumn(name = "stato_id")
    private StatoFattura statoFattura;

    // COSTRUTTORI

    public Fattura(double importo, int numeroFattura, Cliente cliente, StatoFattura statoFattura) {
        this.data = LocalDate.now();
        this.importo = importo;
        this.numeroFattura = numeroFattura;
        this.cliente = cliente;
        this.statoFattura = statoFattura;
    }

    // TO STRING

    @Override
    public String toString() {
        return "Fattura{" +
                "id=" + id +
                ", data=" + data +
                ", importo=" + importo +
                ", numeroFattura=" + numeroFattura +
                ", cliente=" + cliente +
                ", statoFattura=" + statoFattura +
                '}';
    }
}
