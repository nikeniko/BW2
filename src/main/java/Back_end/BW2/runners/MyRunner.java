package Back_end.BW2.runners;

import Back_end.BW2.exceptions.NotFoundException;
import Back_end.BW2.payloads.RuoloDTO;
import Back_end.BW2.services.ComuneService;
import Back_end.BW2.services.ProvinciaService;
import Back_end.BW2.services.RuoliService;
import Back_end.BW2.services.UtentiService;
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

    @Autowired
    private UtentiService utentiService;

    @Autowired
    private RuoliService ruoliService;

    @Override
    public void run(String... args) throws Exception {
        try {

            if (!provinciaService.isDatabasePopulated() && !comuneService.isDatabasePopulated()) {
                File provinciaFile = new ClassPathResource("allegaticsv/province-italiane.csv").getFile();
                File comuniFile = new ClassPathResource("allegaticsv/comuni-italiani.csv").getFile();


                provinciaService.importProvinceCSV(provinciaFile.getAbsolutePath());
                comuneService.importComuniCSV(comuniFile.getAbsolutePath());

                System.out.println("File CSV importati.");
            } else {
                System.out.println("Database è stato già popolato.");
            }
        } catch (Exception e) {
            throw new NotFoundException("Errore nell'import dai file CSV: " + e.getMessage());
        }

        try {
            if (!ruoliService.isDatabasePopulated()) {

                ruoliService.save(new RuoloDTO("UTENTE"));
                ruoliService.save(new RuoloDTO("ADMIN"));

            }
        } catch (Exception e) {
            throw new NotFoundException("Errore nella creazione ruoli base: " + e.getMessage());
        }
    }
}
