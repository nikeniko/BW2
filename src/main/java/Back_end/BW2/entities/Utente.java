package Back_end.BW2.entities;

import Back_end.BW2.enums.RuoloUtente;
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

import java.util.Collection;
import java.util.List;
import java.util.UUID;

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
    @Enumerated(EnumType.STRING)
    @Column(name = "ruolo_utente")
    private RuoloUtente ruoloUtente;

    @OneToMany(mappedBy = "utenteId")
    @JsonIgnore
    private List<Cliente> clientiList;

//    @ManyToMany(mappedBy = "utenteList")
//    private List<Ruolo> ruoli;

    public Utente(String username, String email, String password, String nome, String cognome, String avatar,
                  RuoloUtente ruoloUtente) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.nome = nome;
        this.cognome = cognome;
        this.avatar = avatar;
        this.ruoloUtente = ruoloUtente;
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
        return List.of(new SimpleGrantedAuthority(this.ruoloUtente.name()));
    }

    @Override
    public String getUsername() {
        return this.email;
    }
}
