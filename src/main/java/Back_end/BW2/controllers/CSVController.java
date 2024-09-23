package Back_end.BW2.controllers;

import Back_end.BW2.services.ComuneService;
import Back_end.BW2.services.ProvinciaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.web.bind.annotation.*;

import java.io.File;

@RestController
@RequestMapping("/importData")
public class CSVController {

    @Autowired
    private ProvinciaService provinciaService;

    @Autowired
    private ComuneService comuneService;

    @PostMapping()
    public String importCSVFiles() {
        try {
            File provinciaFile = new ClassPathResource("allgaticsv/province-italiane.csv").getFile();
            File comuniFile = new ClassPathResource("allgaticsv/comuni-italiani.csv").getFile();

            provinciaService.importProvinceCSV(provinciaFile.getAbsolutePath());
            comuneService.importComuniCSV(comuniFile.getAbsolutePath());

            return "L'import dei Dati di Province and Comuni è andato a buon fine!";
        } catch (Exception e) {
            return "Errore nell'import dai file CSV: " + e.getMessage();
        }
    }
}