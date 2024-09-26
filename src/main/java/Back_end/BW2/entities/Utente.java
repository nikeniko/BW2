package Back_end.BW2.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Entity
@Table(name = "utenti")
@Getter
@Setter
@NoArgsConstructor
@JsonIgnoreProperties({"password", "clientiList", "enabled", "accountNonLocked", "accountNonExpired", "credentialsNonExpired", "authorities"})
public class Utente implements UserDetails {
    @Id
    @GeneratedValue
    @Setter(AccessLevel.NONE)
    private UUID id;
    private String username;
    private String email;

    private String password;
    private String nome;
    private String cognome;
    private String avatar;
//    @Enumerated(EnumType.STRING)
//    @Column(name = "ruolo_utente")
//    private RuoloUtente ruoloUtente;

    @OneToMany(mappedBy = "utenteId")
    @JsonIgnore
    private List<Cliente> clientiList;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "ruoli_utenti",
            joinColumns = @JoinColumn(name = "utente_id"),
            inverseJoinColumns = @JoinColumn(name = "ruolo_id"))
    private List<Ruolo> ruoli = new ArrayList<>();

    public Utente(String username, String email, String password, String nome, String cognome, String avatar) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.nome = nome;
        this.cognome = cognome;
        this.avatar = avatar;
    }

    /*public Utente(String username, String email, String password, String nome, String cognome, String avatar, RuoloUtente ruoloUtente*//*, List<Ruolo> ruoli*//*) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.nome = nome;
        this.cognome = cognome;
        this.avatar = avatar;
        this.ruoloUtente = ruoloUtente;
//        this.ruoli = ruoli;
    }*/

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return ruoli.stream().map(ruolo -> new SimpleGrantedAuthority(ruolo.getRuolo())).collect(Collectors.toList());
    }

    @Override
    public String getUsername() {
        return this.email;
    }

    public void aggiungiRuolo(Ruolo ruolo) {
        ruoli.add(ruolo);
        ruolo.getUtenteList().add(this); // mantiene sincronizzata la relazione bidirezionale
    }

    public void rimuoviRuolo(Ruolo ruolo) {
        ruoli.remove(ruolo);
        ruolo.getUtenteList().remove(this); // mantiene sincronizzata la relazione bidirezionale
    }
}
