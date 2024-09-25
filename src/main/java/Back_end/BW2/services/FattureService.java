package Back_end.BW2.services;

import Back_end.BW2.entities.Cliente;
import Back_end.BW2.entities.Fattura;
import Back_end.BW2.enums.StatoFatture;
import Back_end.BW2.exceptions.NotFoundException;
import Back_end.BW2.payloads.NewFatturaDTO;
import Back_end.BW2.payloads.NewFatturaRespDTO;
import Back_end.BW2.repositories.ClientiRepository;
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
    @Autowired
    private ClientiRepository clientiRepository;

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

    public NewFatturaRespDTO findIdAndUpdateFatture(UUID fattureId, NewFatturaDTO newUserData) {
        Fattura found = this.findIdFatture(fattureId);
        found.setData(newUserData.data());
        found.setImporto(newUserData.importo());
        found.setNumeroFattura(newUserData.numeroFattura());
        found.setStatoFatture(StatoFatture.valueOf(newUserData.statoFatture()));
        return new NewFatturaRespDTO(this.fattureRepository.save(found).getId());
    }

    // 4 --> DELETE

    public void findIdFattureAndDelete(UUID fattureId) {
        Fattura found = this.findIdFatture(fattureId);
        this.fattureRepository.delete(found);
    }

    // 5 --> SAVE

    public Fattura save(NewFatturaDTO body) {
        Cliente foundCliente = this.clientiRepository.findById(UUID.fromString(body.cliente())).orElseThrow(() -> new NotFoundException(UUID.fromString(body.cliente())));
        StatoFatture statoFatture = StatoFatture.valueOf(body.statoFatture());
        Fattura newFattura = new Fattura(body.data(), body.importo(), body.numeroFattura(), statoFatture, foundCliente);

        return this.fattureRepository.save(newFattura);
    }


}
