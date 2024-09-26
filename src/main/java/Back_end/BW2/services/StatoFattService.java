package Back_end.BW2.services;

import Back_end.BW2.entities.StatoFattura;
import Back_end.BW2.exceptions.BadRequestException;
import Back_end.BW2.exceptions.NotFoundException;
import Back_end.BW2.payloads.StatoFattDTO;
import Back_end.BW2.payloads.StatoFattRespDTO;
import Back_end.BW2.repositories.StatoFattRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Service
public class StatoFattService {

    @Autowired
    private StatoFattRepository statoFattRepository;

    // METODI

    public boolean isDatabasePopulated() {
        return statoFattRepository.count() > 0;
    }

    // 1 --> GET ALL

    public Page<StatoFattura> findAllStati(int page, int size, String sortBy) {
        if (page > 100) page = 100;

        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy));
        return this.statoFattRepository.findAll(pageable);
    }

    // 2 --> GET ID

    public StatoFattura findIdFatture(UUID statoId) {
        return this.statoFattRepository.findById(statoId).orElseThrow(() -> new NotFoundException(statoId));
    }

    // 5 --> SAVE

    public StatoFattRespDTO save(StatoFattDTO body) {

        List<StatoFattura> stati = this.statoFattRepository.findAll();

        for (StatoFattura stato : stati) {

            if (Objects.equals(stato.toString(), body.stato().toUpperCase())) {

                throw new BadRequestException("Stato fattura già esistente.");
            }

        }
        StatoFattura stato = new StatoFattura(body.stato().toUpperCase()); // salvo

        return new StatoFattRespDTO(this.statoFattRepository.save(stato).getId());
    }


}
