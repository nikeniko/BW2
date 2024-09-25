package Back_end.BW2;

import Back_end.BW2.exceptions.NotFoundException;
import Back_end.BW2.services.ComuneService;
import Back_end.BW2.services.ProvinciaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import java.io.File;

@Component
public class MyRunner implements CommandLineRunner {
    @Autowired
    private ProvinciaService provinciaService;

    @Autowired
    private ComuneService comuneService;

    @Override
    public void run(String... args) throws Exception {


        try {
            File provinciaFile = new ClassPathResource("allgaticsv/province-italiane.csv").getFile();
            File comuniFile = new ClassPathResource("allgaticsv/comuni-italiani.csv").getFile();

            provinciaService.importProvinceCSV(provinciaFile.getAbsolutePath());
            comuneService.importComuniCSV(comuniFile.getAbsolutePath());


        } catch (Exception e) {
            throw new NotFoundException("Errore nell'import dai file CSV: " + e.getMessage());
        }
    }
}
