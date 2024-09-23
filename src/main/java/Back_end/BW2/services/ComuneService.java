package Back_end.BW2.services;

import Back_end.BW2.entities.Comune;
import Back_end.BW2.entities.Provincia;
import Back_end.BW2.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ComuneService {

    @Autowired
    private ComuneRepository comuneRepository;

    @Autowired
    private ProvinciaRepository provinciaRepository;


    public void importComuniCSV(String filePath) {
        List<Comune> comuni = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            br.readLine();

            while ((line = br.readLine()) != null) {
                String[] riga = line.split(";");

                Comune comune = new Comune();
                comune.setNome(riga[2]);
                Optional<Provincia> provinciaAssociazione = Optional.ofNullable(provinciaRepository.findByNome(riga[3]));
                provinciaAssociazione.ifPresent(comune::setProvincia);

                comuni.add(comune);
            }

            comuneRepository.saveAll(comuni);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}