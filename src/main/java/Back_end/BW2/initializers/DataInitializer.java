package Back_end.BW2.initializers;

import Back_end.BW2.controllers.CSVController;
import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.io.FileReader;
import java.io.IOException;
import java.util.List;

@Component
public class DataInitializer implements CommandLineRunner {

    private final CSVController csvController;
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    public DataInitializer(CSVController csvController) {
        this.csvController = csvController;
    }

    @Override
    public void run(String... args) throws Exception {
        populateDatabaseFromCSV("src/main/resources/allgaticsv/comuni-italiani.csv", "src/main/resources/allgaticsv/province-italiane.csv");

    }

    private void populateDatabaseFromCSV(String comuniPath, String provincePath) throws IOException, CsvException {
        // Popola il database con i dati dei comuni
        try (CSVReader reader = new CSVReader(new FileReader(comuniPath))) {
            List<String[]> rows = reader.readAll();
            for (String[] row : rows) {
                // Supponendo che il CSV dei comuni abbia le colonne: id, nome, provincia
                jdbcTemplate.update("INSERT INTO comuni (id, nome, provincia) VALUES (?, ?, ?)",
                        row[0], row[1], row[2]);
            }
        }

        // Popola il database con i dati delle province
        try (CSVReader reader = new CSVReader(new FileReader(provincePath))) {
            List<String[]> rows = reader.readAll();
            for (String[] row : rows) {
                // Supponendo che il CSV delle province abbia le colonne: id, nome
                jdbcTemplate.update("INSERT INTO province (id, nome) VALUES (?, ?)",
                        row[0], row[1]);
            }
        } catch (IOException | CsvException e) {
            throw new RuntimeException(e);
        }
    }


}