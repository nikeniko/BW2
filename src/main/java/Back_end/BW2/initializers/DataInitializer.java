package Back_end.BW2.initializers;

import Back_end.BW2.controllers.CSVController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataInitializer implements CommandLineRunner {

    private final CSVController csvController;

    @Autowired
    public DataInitializer(CSVController csvController) {
        this.csvController = csvController;
    }

    @Override
    public void run(String... args) throws Exception {
        csvController.importCSVFiles();
    }
}