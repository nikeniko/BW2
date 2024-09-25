package Back_end.BW2.services;

import Back_end.BW2.entities.Comune;
import Back_end.BW2.repositories.ComuneRepository;
import Back_end.BW2.repositories.ProvinciaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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
                String[] valori = line.split(";");
                int id = Integer.parseInt(valori[0]);   // ID del comune
                String nome = valori[1];                // Nome del comune
                String provincia = valori[2];           // Nome della provincia

                // Crea un nuovo oggetto Comune e aggiungilo alla lista
                Comune comune = new Comune(nome);
                comuni.add(comune);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return comuni;
    }
}
