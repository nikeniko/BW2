package Back_end.BW2.services;

import Back_end.BW2.entities.Comune;
import Back_end.BW2.entities.Indirizzo;
import Back_end.BW2.exceptions.BadRequestException;
import Back_end.BW2.exceptions.NotFoundException;
import Back_end.BW2.payloads.IndirizzoDTO;
import Back_end.BW2.repositories.IndirizzoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class IndirizzoService {

    @Autowired
    private IndirizzoRepository indirizzoRepository;

    @Autowired
    private ComuneService comuneService;

    // METODI

    // 1 --> GET ALL

    public Page<Indirizzo> findAllIndirizzo(int page, int size, String sortBy) {
        if (page > 100) page = 100;

        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy));
        return this.indirizzoRepository.findAll(pageable);
    }

    // 2 --> GET ID

    public Indirizzo findIdIndirizzo(UUID indirizzoId) {
        if (indirizzoId == null) {
            throw new BadRequestException("L'ID dell'indirizzo non può essere nullo.");
        }

        return indirizzoRepository.findById(indirizzoId)
                .orElseThrow(() -> new NotFoundException("Indirizzo con ID " + indirizzoId + " non trovato."));
    }

    // 5 --> SAVE

    public Indirizzo save(IndirizzoDTO body) {
            if (body.via() == null || body.via().isEmpty()) {
                throw new BadRequestException("La via non può essere vuota.");
            }
            if (body.civico() == null || body.civico().isEmpty()) {
                throw new BadRequestException("Il civico non può essere vuoto.");
            }
            if (body.localita() == null || body.localita().isEmpty()) {
                throw new BadRequestException("La località non può essere vuota.");
            }
            if (body.cap() == null || body.cap().isEmpty()) {
                throw new BadRequestException("Il CAP non può essere vuoto.");
            }
            if (body.comune() == null || body.comune().isEmpty()) {
                throw new BadRequestException("Il comune non può essere nullo o vuoto.");
            }

            Comune comune = comuneService.findIdComune(UUID.fromString(body.comune()));
            if (comune == null) {
                throw new BadRequestException("Comune non valido o non esistente.");
            }

        Indirizzo indirizzo = new Indirizzo(body.via(), body.civico(), body.localita(), body.cap(), comune);

        return indirizzoRepository.save(indirizzo);
    }

    public List<Indirizzo> findByComune(Comune comune) {
        if (comune == null) {
            throw new BadRequestException("Il comune non può essere nullo.");
        }

        List<Indirizzo> indirizzi = this.indirizzoRepository.findByComune(comune);
        if (indirizzi.isEmpty()) {
            throw new NotFoundException("Nessun indirizzo trovato per il comune " + comune.getNome() + ".");
        }
        return indirizzi;
    }

}
