package Back_end.BW2.services;

import Back_end.BW2.entities.Comune;
import Back_end.BW2.entities.Provincia;
import Back_end.BW2.exceptions.NotFoundException;
import Back_end.BW2.repositories.ComuneRepository;
import Back_end.BW2.repositories.ProvinciaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.*;

@Service
public class ComuneService {

    @Autowired
    private ComuneRepository comuneRepository;

    @Autowired
    private ProvinciaRepository provinciaRepository;

    public boolean isDatabasePopulated() {
        return comuneRepository.count() > 0;
    }

    public void importComuniCSV(String filePath) {
        List<Comune> comuni = new ArrayList<>();
        Map<String, Integer> provinciaCounters = new HashMap<>();

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            br.readLine();
            while ((line = br.readLine()) != null) {
                String[] riga = line.split(";");

                Comune comune = new Comune();

                String codiceProvincia = riga[0];
                comune.setCodiceProvincia(codiceProvincia);


                String progressivoComune = riga[1];
                if (progressivoComune.equals("#RIF!")) {
                    provinciaCounters.putIfAbsent(codiceProvincia, 1);

                    progressivoComune = String.format("%03d", provinciaCounters.get(codiceProvincia));

                    provinciaCounters.put(codiceProvincia, provinciaCounters.get(codiceProvincia) + 1);
                }
                comune.setProgressivoComune(progressivoComune);

                comune.setNome(riga[2]);


                Provincia provinciaAssociazione = provinciaRepository.findByNome(riga[3]);
                comune.setProvincia(provinciaAssociazione);

                comuni.add(comune);
            }

            comuneRepository.saveAll(comuni);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Page<Comune> findAllComune(int page, int size, String sortBy) {
        if (page > 100) page = 100;

        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy));
        return this.comuneRepository.findAll(pageable);
    }

    // 2 --> GET ID

    public Comune findIdComune(UUID comuneId) {
        return this.comuneRepository.findById(comuneId).orElseThrow(() -> new NotFoundException(comuneId));
    }
}
