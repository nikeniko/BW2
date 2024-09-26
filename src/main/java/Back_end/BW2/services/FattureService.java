package Back_end.BW2.services;

import Back_end.BW2.entities.Cliente;
import Back_end.BW2.entities.Fattura;
import Back_end.BW2.enums.StatoFatture;
import Back_end.BW2.exceptions.BadRequestException;
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
        if (fattureId == null) {
            throw new BadRequestException("L'ID della fattura non può essere nullo.");
        }
        return this.fattureRepository.findById(fattureId)
                .orElseThrow(() -> new NotFoundException("Fattura con ID " + fattureId + " non trovata."));
    }

    // 3 --> PUT

    public NewFatturaRespDTO findIdAndUpdateFatture(UUID fattureId, NewFatturaDTO newUserData) {
        if (newUserData == null) {
            throw new BadRequestException("I dati della nuova fattura non possono essere nulli.");
        }
        Fattura found = this.findIdFatture(fattureId);
        if (newUserData.importo() == null || Double.parseDouble(newUserData.importo()) < 0) {
            throw new BadRequestException("L'importo deve essere un valore positivo.");
        }
        try {
            found.setImporto(Double.parseDouble(newUserData.importo()));
            found.setNumeroFattura(newUserData.numeroFattura());
            found.setStatoFatture(StatoFatture.valueOf(newUserData.statoFatture()));
        } catch (IllegalArgumentException e) {
            throw new BadRequestException("Stato della fattura non valido: " + newUserData.statoFatture());
        }
        return new NewFatturaRespDTO(this.fattureRepository.save(found).getId());

    }
    // 4 --> DELETE

    public void findIdFattureAndDelete(UUID fattureId) {
        if (fattureId == null) {
            throw new BadRequestException("L'ID della fattura non può essere nullo.");
        }
        Fattura found = this.findIdFatture(fattureId);
        this.fattureRepository.delete(found);
    }

    // 5 --> SAVE

    public Fattura save(NewFatturaDTO body) {
        if (body.cliente() == null || body.cliente().isEmpty()) {
            throw new BadRequestException("L'ID del cliente non può essere nullo o vuoto.");
        }
        if (body.importo() == null || Double.parseDouble(body.importo()) <= 0) {
            throw new BadRequestException("L'importo deve essere un valore positivo.");
        }
        if (body.statoFatture() == null || body.statoFatture().isEmpty()) {
            throw new BadRequestException("Lo stato della fattura non può essere nullo o vuoto.");
        }
        Cliente foundCliente = this.clientiRepository.findById(UUID.fromString(body.cliente()))
                .orElseThrow(() -> new NotFoundException("Cliente con ID " + body.cliente() + " non trovato."));

        StatoFatture statoFatture;
        try {
            statoFatture = StatoFatture.valueOf(body.statoFatture());
        } catch (IllegalArgumentException e) {
            throw new BadRequestException("Stato della fattura non valido: " + body.statoFatture());
        }

        Fattura newFattura = new Fattura(Double.parseDouble(body.importo()), body.numeroFattura(), statoFatture, foundCliente);
        return this.fattureRepository.save(newFattura);
    }


}
