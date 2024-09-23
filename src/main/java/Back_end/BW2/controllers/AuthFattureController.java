package Back_end.BW2.controllers;

import Back_end.BW2.exceptions.BadRequestException;
import Back_end.BW2.playloads.NewFatturaDTO;
import Back_end.BW2.playloads.NewFatturaRespDTO;
import Back_end.BW2.services.FattureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.stream.Collectors;

@RestController
@RequestMapping("/authFatture")
public class AuthFattureController {

    // IMPORTI

    @Autowired
    private FattureService fattureService;

    // 1 --> POST

    @PostMapping("/crea")
    @ResponseStatus(HttpStatus.CREATED)
    public NewFatturaRespDTO save(@RequestBody @Validated NewFatturaDTO body, BindingResult validationResult) {
        if (validationResult.hasErrors()) {
            String messages = validationResult.getAllErrors().stream()
                    .map(objectError -> objectError.getDefaultMessage())
                    .collect(Collectors.joining(". "));

            throw new BadRequestException("Ci sono stati errori nel payload. " + messages);
        } else {


            return new NewFatturaRespDTO(this.fattureService.save(body).getId());
        }
    }
}
