package Back_end.BW2.runners;

import Back_end.BW2.services.ComuneService;
import Back_end.BW2.services.ProvinciaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class MyRunner implements CommandLineRunner {
    @Autowired
    private ProvinciaService provinciaService;

    @Autowired
    private ComuneService comuneService;

    @Override
    public void run(String... args) throws Exception {


    }
}
