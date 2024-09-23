package Back_end.BW2.services;

import Back_end.BW2.entities.Utente;
import Back_end.BW2.enums.RuoloUtente;
import Back_end.BW2.exceptions.BadRequestException;
import Back_end.BW2.exceptions.NotFoundException;
import Back_end.BW2.payloads.UtenteDTO;
import Back_end.BW2.payloads.UtenteRespDTO;
import Back_end.BW2.repositories.UtentiRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class UtentiService {
    @Autowired
    private UtentiRepository utentiRepository;

    @Autowired
    private PasswordEncoder bcrypt;

    public Utente findByEmail(String email) {
        return utentiRepository.findByEmail(email).orElseThrow(() -> new NotFoundException("L'utente con l'email " + email + " non è stato trovato."));
    }

    public UtenteRespDTO saveUtente(UtenteDTO body) {

        this.utentiRepository.findByEmail(body.email()).ifPresent(author -> {
            throw new BadRequestException("L'email " + body.email() + " è già in uso.");
        });

        RuoloUtente ruoloUtente;

        try {
            ruoloUtente = RuoloUtente.valueOf(body.ruoloUtente().toUpperCase());
            if (ruoloUtente == RuoloUtente.ADMIN)
                throw new Error("Errore. Nessuno può inserirsi come ADMIN");
        } catch (Exception e) {
            throw new BadRequestException("Errore. Il ruolo inserito non esiste.");
        }

        Utente newUtente = new Utente(body.username(), body.email(), bcrypt.encode(body.password()), body.nome(), body.cognome(),
                "https://ui-avatars.com/api/?name=" + body.nome() + "+" + body.cognome(), ruoloUtente);

        // salvo il nuovo record
        return new UtenteRespDTO(this.utentiRepository.save(newUtente).getId());
    }

    // cerco tutti gli utenti
    public Page<Utente> findAll(int page, int size, String sortBy) {
        if (page > 20) page = 20;
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy));
        return this.utentiRepository.findAll(pageable);
    }

    // cerco utenti byId
    public Utente findById(UUID utenteId) {
        return this.utentiRepository.findById(utenteId).orElseThrow(() -> new NotFoundException(utenteId));
    }

    // delete utente
    public void findByIdAndDelete(UUID utenteId) {
        Utente trovato = this.findById(utenteId);
        this.utentiRepository.delete(trovato);
    }

    public UtenteRespDTO findByIdAndUpdate(UUID utenteId, UtenteDTO body) {

        this.utentiRepository.findByEmail(body.email()).ifPresent(author -> {
            if (!author.getId().equals(utenteId)) {
                throw new BadRequestException("L'email " + body.email() + " è già in uso.");
            }
        });

        RuoloUtente ruoloUtente;

        try {
            String ruoloInput = body.ruoloUtente().toUpperCase(); // Rimuove spazi e converte in maiuscolo
            System.out.println(ruoloInput);
            ruoloUtente = RuoloUtente.valueOf(ruoloInput);  // Corrisponde all'enum
            if (ruoloUtente == RuoloUtente.ADMIN) {
                throw new Error("Errore. Nessuno può inserirsi come ADMIN");
            }
        } catch (Exception e) {
            throw new BadRequestException("Errore. Il ruolo inserito non esiste.");
        }

        Utente trovato = this.findById(utenteId);
        trovato.setUsername(body.username());
        trovato.setEmail(body.email());
        trovato.setPassword(bcrypt.encode(body.password()));
        trovato.setNome(body.nome());
        trovato.setCognome(body.cognome());
        trovato.setRuoloUtente(ruoloUtente);


        // salvo il nuovo record
        return new UtenteRespDTO(this.utentiRepository.save(trovato).getId());

    }
}
