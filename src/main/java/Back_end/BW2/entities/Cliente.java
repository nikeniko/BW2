package Back_end.BW2.entities;

import Back_end.BW2.enums.TipoAzienda;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "clienti")
@Getter
@Setter
@NoArgsConstructor
public class Cliente {
    @Id
    @GeneratedValue
    @Setter(AccessLevel.NONE)
    private UUID id;
    @Column(name = "ragione_sociale")
    private String ragioneSociale;
    @Column(name = "partita_iva")
    private String partitaIva;
    @Column(name = "data_inserimento")
    private LocalDate dataInserimento;
    @Column(name = "data_ultimo_contatto")
    private LocalDate dataUltimoContatto;
    @Column(name = "fatturato_annuale")
    private int fatturatoAnnuale;
    private String pec;
    private int telefono;
    @Column(name = "email_contatto")
    private String emailContatto;
    @Column(name = "nome_contatto")
    private String nomeContatto;
    @Column(name = "cognome_contatto")
    private String cognomeContatto;
    @Column(name = "logo_aziendale")
    private String logoAziendale;
    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_azienda")
    private TipoAzienda tipoAzienda;

    @ManyToOne
    @JoinColumn(name = "utente_id")
    private Utente utenteId;

    @JsonIgnore
    @OneToMany(mappedBy = "cliente")
    private List<Fattura> fattureList;

    @ManyToOne
    private Indirizzo indirizzoSedeLegale;

    @ManyToOne
    private Indirizzo indirizzoSedeOperativa;

    public Cliente(String ragioneSociale, String partitaIva, LocalDate dataInserimento, LocalDate dataUltimoContatto,
                   int fatturatoAnnuale, String pec, int telefono, String emailContatto, String nomeContatto,
                   String cognomeContatto, TipoAzienda tipoAzienda, Utente utenteId, Indirizzo indirizzoSedeLegale,
                   Indirizzo indirizzoSedeOperativa, String logoAziendale) {
        this.ragioneSociale = ragioneSociale;
        this.partitaIva = partitaIva;
        this.dataInserimento = dataInserimento;
        this.dataUltimoContatto = dataUltimoContatto;
        this.fatturatoAnnuale = fatturatoAnnuale;
        this.pec = pec;
        this.telefono = telefono;
        this.emailContatto = emailContatto;
        this.nomeContatto = nomeContatto;
        this.cognomeContatto = cognomeContatto;
        this.tipoAzienda = tipoAzienda;
        this.utenteId = utenteId;
        this.indirizzoSedeLegale = indirizzoSedeLegale;
        this.indirizzoSedeOperativa = indirizzoSedeOperativa;
        this.logoAziendale = logoAziendale;
    }

}
