package Back_end.BW2.services;


import Back_end.BW2.entities.Ruolo;
import Back_end.BW2.entities.Utente;
import Back_end.BW2.exceptions.BadRequestException;
import Back_end.BW2.exceptions.NotFoundException;
import Back_end.BW2.payloads.RuoloDTO;
import Back_end.BW2.repositories.RuoloRepository;
import Back_end.BW2.repositories.UtentiRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class RuoliService {

    @Autowired
    private RuoloRepository ruoloRepository;

    @Autowired
    private UtentiRepository utentiRepository;

    public boolean isDatabasePopulated() {
        return ruoloRepository.count() > 0;
    }

    public Page<Ruolo> findAllRuoli(int page, int size, String sortBy) {
        if (page > 100) page = 100;

        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy));
        return this.ruoloRepository.findAll(pageable);
    }

    public Ruolo findByRuolo(String ruolo) {
        return this.ruoloRepository.findByRuolo(ruolo).orElseThrow(() -> new NotFoundException("Il ruolo non esiste!"));
    }

    public Ruolo findIdRuolo(UUID ruoloId) {
        return this.ruoloRepository.findById(ruoloId).orElseThrow(() -> new NotFoundException(ruoloId));
    }


    public Ruolo save(RuoloDTO ruoloDTO) {
        if (ruoloRepository.findAll().stream().anyMatch(obj -> obj.getRuolo().equals(ruoloDTO.ruolo()))) {
            throw new BadRequestException("Il ruolo " + ruoloDTO.ruolo() + " esiste gia!");
        } else {
            Ruolo ruolo = new Ruolo(ruoloDTO.ruolo().toUpperCase());
            return this.ruoloRepository.save(ruolo);
        }

    }

    public Ruolo save(RuoloDTO ruoloDTO, UUID utenteId) {
        Utente utente = utentiRepository.findById(utenteId).orElseThrow(() -> new NotFoundException(utenteId));
        Ruolo ruolo;
        if (ruoloRepository.findAll().stream().anyMatch(obj -> obj.getRuolo().equals(ruoloDTO.ruolo()))) {
            Ruolo found = ruoloRepository.findByRuolo(ruoloDTO.ruolo().toUpperCase())
                    .orElseThrow(() -> new NotFoundException(ruoloDTO.ruolo().toUpperCase()));
            if (utente.getRuoli().stream().anyMatch(ruolo1 -> ruolo1.equals(found))) {
                throw new BadRequestException("L'utente possiede già questo ruolo!");
            } else {
                utente.aggiungiRuolo(found);
                utentiRepository.save(utente);
                return found;
            }

        } else {
            ruolo = new Ruolo(ruoloDTO.ruolo().toUpperCase());
            this.ruoloRepository.save(ruolo);
            utente.aggiungiRuolo(ruolo);
            utentiRepository.save(utente);
            return ruolo;
        }

    }

    public void deleteRuolo(UUID ruoloId) {
        Ruolo trovato = this.findIdRuolo(ruoloId);
        this.ruoloRepository.delete(trovato);
    }
}
