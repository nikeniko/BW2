package Back_end.BW2.services;

import Back_end.BW2.entities.Provincia;
import Back_end.BW2.repositories.ProvinciaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

@Service
public class ProvinciaService {

    @Autowired
    private ProvinciaRepository provinciaRepository;

    public boolean isDatabasePopulated() {
        return provinciaRepository.count() > 0;
    }

    public void importProvinceCSV(String filePath) {
        List<Provincia> province = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String riga;
            reader.readLine();

            while ((riga = reader.readLine()) != null) {
                String[] casella = riga.split(";");

                switch (casella[1]) {
                    case "Verbania" -> casella[1] = "Verbano-Cusio-Ossola";
                    case "Aosta" -> casella[1] = "Valle d'Aosta/Vallée d'Aoste";
                    case "Monza-Brianza" -> casella[1] = "Monza e della Brianza";
                    case "Bolzano" -> casella[1] = "Bolzano/Bozen";
                    case "La-Spezia" -> casella[1] = "La Spezia";
                    case "Reggio-Emilia" -> casella[1] = "Reggio nell'Emilia";
                    case "Forli-Cesena" -> casella[1] = "Forlì-Cesena";
                    case "Pesaro-Urbino" -> casella[1] = "Pesaro e Urbino";
                    case "Ascoli-Piceno" -> casella[1] = "Ascoli Piceno";
                    case "Reggio-Calabria" -> casella[1] = "Reggio Calabria";
                    case "Vibo-Valentia" -> casella[1] = "Vibo Valentia";
                    case "Carbonia Iglesias", "Medio Campidano" -> casella[1] = "Sud Sardegna";
                    case "Olbia Tempio" -> casella[1] = "Sassari";
                    case "Ogliastra" -> casella[1] = "Nuoro";
                }

                Provincia provincia = new Provincia();
                provincia.setSigla(casella[0]);
                provincia.setNome(casella[1]);
                provincia.setRegione(casella[2]);
                if (province.stream().anyMatch(obj -> obj.getNome().equals(provincia.getNome()))) {
                    System.out.println(provincia.getNome() + " esiste già");
                } else {
                    province.add(provincia);
                }
            }

            provinciaRepository.saveAll(province);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
