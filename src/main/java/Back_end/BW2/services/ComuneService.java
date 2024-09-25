package Back_end.BW2.services;

import Back_end.BW2.entities.Comune;
import Back_end.BW2.entities.Provincia;
import Back_end.BW2.repositories.ComuneRepository;
import Back_end.BW2.repositories.ProvinciaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ComuneService {

    @Autowired
    private ComuneRepository comuneRepository;

    @Autowired
    private ProvinciaRepository provinciaRepository;


    public List<Comune> leggiComuniDaCSV(String filePath) {
        List<Comune> comuni = new ArrayList<>();
        String line;

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            while ((line = br.readLine()) != null) {

                String[] riga = line.split(";");

                Comune comune = new Comune();
                comune.setNome(riga[2]);
                Optional<Provincia> provinciaAssociazione = Optional.ofNullable(provinciaRepository.findByNome(riga[3]));
                if (provinciaAssociazione.isPresent()) {
                    comune.setProvincia(provinciaAssociazione.get());
                }
                comuni.add(comune);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return comuni;
    }
}
