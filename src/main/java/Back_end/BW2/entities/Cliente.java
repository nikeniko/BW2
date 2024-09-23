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
    private long partitaIva;
    @Column(name = "data_inserimento")
    private LocalDate dataInserimento;
    @Column(name = "data_ultimo_contatto")
    private LocalDate dataUltimoContatto;
    @Column(name = "fatturato_annuale")
    private long fatturatoAnnuale;
    private String pec;
    private long telefono;
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
    @OneToMany(mappedBy = "clienteId")
    private List<Fattura> fattureList;

   /* @OneToOne
    private Indirizzo indirizzoSedeLegale;

    @OneToOne
    private Indirizzo indirizzoSedeOperativa;*/

    public Cliente(String ragioneSociale, long partitaIva, LocalDate dataInserimento, LocalDate dataUltimoContatto,
                   long fatturatoAnnuale, String pec, long telefono, String emailContatto, String nomeContatto, String cognomeContatto,
                   String logoAziendale, TipoAzienda tipoAzienda/*, Indirizzo indirizzoSedeLegale, Indirizzo indirizzoSedeOperativa*/) {
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
        this.logoAziendale = logoAziendale;
        this.tipoAzienda = tipoAzienda;
//        this.indirizzoSedeLegale = indirizzoSedeLegale;
//        this.indirizzoSedeOperativa = indirizzoSedeOperativa;
    }
}
