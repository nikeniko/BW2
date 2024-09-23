package Back_end.BW2.services;

import Back_end.BW2.entities.Fattura;
import Back_end.BW2.exceptions.NotFoundException;
import Back_end.BW2.playloads.NewFatturaDTO;
import Back_end.BW2.repositories.FattureRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class FattureService {

    // IMPORTI

    @Autowired
    private FattureRepository fattureRepository;

    // METODI

    // 1 --> GET ALL

    public Page<Fattura> findAllFatture(int page, int size, String sortBy) {
        if (page > 100) page = 100;

        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy));
        return this.fattureRepository.findAll(pageable);
    }

    // 2 --> GET ID

    public Fattura findIdFatture(UUID fattureId) {
        return this.fattureRepository.findById(fattureId).orElseThrow(() -> new NotFoundException(fattureId));
    }

    // 3 --> PUT

    public Fattura findIdAndUpdate(UUID fattureId, Fattura newUserData) {
        Fattura found = this.findIdFatture(fattureId);
        found.setData(newUserData.getData());
        found.setImporto(newUserData.getImporto());
        found.setNumeroFattura(newUserData.getNumeroFattura());
        found.setStatoFatture(newUserData.getStatoFatture());
        return this.fattureRepository.save(found);
    }

    // 4 --> DELETE

    public void findIdFattureAndDelete(UUID fattureId) {
        Fattura found = this.findIdFatture(fattureId);
        this.fattureRepository.delete(found);
    }

    // 5 --> SAVE

    public Fattura save(NewFatturaDTO body) {
        Fattura newFattura = new Fattura(body.data(), body.importo(), body.statoFatture());

        return this.fattureRepository.save(newFattura);
    }


}
