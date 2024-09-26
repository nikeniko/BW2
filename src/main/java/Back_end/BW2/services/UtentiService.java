package Back_end.BW2.services;

import Back_end.BW2.entities.Ruolo;
import Back_end.BW2.entities.Utente;
import Back_end.BW2.exceptions.BadRequestException;
import Back_end.BW2.exceptions.NotFoundException;
import Back_end.BW2.payloads.UtenteDTO;
import Back_end.BW2.payloads.UtenteRespDTO;
import Back_end.BW2.repositories.RuoloRepository;
import Back_end.BW2.repositories.UtentiRepository;
import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

@Service
public class UtentiService {
    @Autowired
    private UtentiRepository utentiRepository;

    @Autowired
    private RuoloRepository ruoloRepository;
    @Autowired
    private PasswordEncoder bcrypt;

    @Autowired
    private Cloudinary cloudinary;

    public Utente findByEmail(String email) {
        return utentiRepository.findByEmail(email).orElseThrow(() -> new NotFoundException("L'utente con l'email " + email + " non è stato trovato."));
    }

    public UtenteRespDTO saveUtente(UtenteDTO body) {

        this.utentiRepository.findByEmail(body.email()).ifPresent(author -> {
            throw new BadRequestException("L'email " + body.email() + " è già in uso.");
        });

        Ruolo ruoloUtente = ruoloRepository.findByRuolo("UTENTE").orElseThrow(() -> new BadRequestException("Il ruolo non esiste!"));

        Utente newUtente = new Utente(body.username(), body.email(), bcrypt.encode(body.password()), body.nome(), body.cognome(),
                "https://ui-avatars.com/api/?name=" + body.nome() + "+" + body.cognome());
        newUtente.aggiungiRuolo(ruoloUtente);
        // salvo il nuovo record
        return new UtenteRespDTO(this.utentiRepository.save(newUtente).getId());
    }

    public UtenteRespDTO save(Utente body) {

        return new UtenteRespDTO(this.utentiRepository.save(body).getId());
    }

    // cerco tutti gli utenti
    public Page<Utente> findAll(int page, int size, String sortBy) {
        if (page > 20) page = 20;
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy));
        return this.utentiRepository.findAll(pageable);
    }

    public List<Utente> findAllUtenti() {

        return this.utentiRepository.findAll();
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


        Utente trovato = this.findById(utenteId);
        trovato.setUsername(body.username());
        trovato.setEmail(body.email());
        trovato.setPassword(bcrypt.encode(body.password()));
        trovato.setNome(body.nome());
        trovato.setCognome(body.cognome());


        // salvo il nuovo record
        return new UtenteRespDTO(this.utentiRepository.save(trovato).getId());

    }

    // upload immagine
    public Utente uploadImagine(MultipartFile file, UUID utenteId) throws IOException {
        Utente trovato = this.findById(utenteId);
        String url = (String) cloudinary.uploader().upload(file.getBytes(), ObjectUtils.emptyMap()).get("url");

        trovato.setAvatar(url);

        return utentiRepository.save(trovato);
    }
}
