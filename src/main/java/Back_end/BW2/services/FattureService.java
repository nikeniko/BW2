package Back_end.BW2.services;

import Back_end.BW2.entities.Cliente;
import Back_end.BW2.entities.Fattura;
import Back_end.BW2.entities.StatoFattura;
import Back_end.BW2.exceptions.BadRequestException;
import Back_end.BW2.exceptions.NotFoundException;
import Back_end.BW2.payloads.NewFatturaDTO;
import Back_end.BW2.payloads.NewFatturaRespDTO;
import Back_end.BW2.repositories.ClientiRepository;
import Back_end.BW2.repositories.FattureRepository;
import Back_end.BW2.repositories.StatoFattRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Service
public class FattureService {

    // IMPORTI

    @Autowired
    private FattureRepository fattureRepository;
    @Autowired
    private ClientiRepository clientiRepository;
    @Autowired
    private StatoFattRepository statoFattRepository;


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

    public List<Fattura> findByCliente(UUID clienteID) {
        Cliente cliente = clientiRepository.findById(clienteID).orElseThrow(() -> new NotFoundException(clienteID));
        return this.fattureRepository.findByCliente(cliente);

    }

    public List<Fattura> findByStatoFattura(String statoFattura) {
        StatoFattura stato = statoFattRepository.findByStato(statoFattura.toUpperCase()).orElseThrow(() -> new NotFoundException("Non esiste lo stato " + statoFattura.toUpperCase()));
        return this.fattureRepository.findByStatoFattura(stato);

    }

    public List<Fattura> findByData(String date) {
        LocalDate data = LocalDate.parse(date);
        return this.fattureRepository.findByData(data);

    }

    public List<Fattura> findByAnno(int anno) {
        return this.fattureRepository.findByAnno(anno);
    }

    public List<Fattura> findByImportoBetween(Double minImporto, Double maxImporto) {
        return this.fattureRepository.findByImportoBetween(minImporto, maxImporto);
    }


    // 3 --> PUT

    public NewFatturaRespDTO findIdAndUpdateFatture(UUID fattureId, NewFatturaDTO newUserData) {
        if (newUserData == null) {
            throw new BadRequestException("I dati della nuova fattura non possono essere nulli.");
        }
        Fattura found = this.findIdFatture(fattureId);

        found.setImporto(Double.parseDouble(newUserData.importo()));

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

    // metodo per generare numeroFattura progressivo --> ho provato con GenerateValue ma non funziona
    // trova l'ultimo e lo incrementa
    public synchronized int generaNumeroFattura() {
        int ultimoNumeroFattura = Math.toIntExact(fattureRepository.findMaxNumeroFattura().orElse(0L));
        return ultimoNumeroFattura + 1;
    }

    public Fattura save(NewFatturaDTO body) {

        Cliente foundCliente = this.clientiRepository.findById(UUID.fromString(body.cliente())).orElseThrow(() -> new NotFoundException(UUID.fromString(body.cliente())));

        StatoFattura statoFattura = this.statoFattRepository.findByStato("EMESSA")
                .orElseGet(() -> {
                    StatoFattura stato = new StatoFattura();
                    stato.setStato("EMESSA");
                    return this.statoFattRepository.save(stato);
                });
        //.orElseThrow(() -> new NotFoundException("Stato non trovato."));

        int numeroFattura = generaNumeroFattura();

        Fattura newFattura = new Fattura(Double.parseDouble(body.importo()), numeroFattura, foundCliente, statoFattura);

        return this.fattureRepository.save(newFattura);
    }

    public Fattura saveFattObj(Fattura body) {

        return this.fattureRepository.save(body);
    }


}
