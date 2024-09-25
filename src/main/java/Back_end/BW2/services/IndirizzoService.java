package Back_end.BW2.services;

import Back_end.BW2.entities.Comune;
import Back_end.BW2.entities.Indirizzo;
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
        return this.indirizzoRepository.findById(indirizzoId).orElseThrow(() -> new NotFoundException(indirizzoId));
    }

    // 5 --> SAVE

    public Indirizzo save(IndirizzoDTO body) {

        Comune comune = this.comuneService.findIdComune(UUID.fromString(body.comune()));

        Indirizzo indirizzo = new Indirizzo(body.via(), body.civico(), body.localita(), body.cap(), comune);

        return this.indirizzoRepository.save(indirizzo);
    }

    public List<Indirizzo> findByComune(Comune comune) {
//        if (page > 100) page = 100;
//        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy));
        return this.indirizzoRepository.findByComune(comune);
    }


}
