package Back_end.BW2.controllers;

import Back_end.BW2.exceptions.BadRequestException;
import Back_end.BW2.payloads.UtenteDTO;
import Back_end.BW2.payloads.UtenteLoginDTO;
import Back_end.BW2.payloads.UtenteLoginRespDTO;
import Back_end.BW2.payloads.UtenteRespDTO;
import Back_end.BW2.services.AuthService;
import Back_end.BW2.services.UtentiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.stream.Collectors;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private UtentiService utentiService;

    @Autowired
    private AuthService authService;

    @PostMapping("/login")
    public UtenteLoginRespDTO login(@RequestBody @Validated UtenteLoginDTO body) {
        return new UtenteLoginRespDTO(this.authService.checkCredenzialiAndGeneraToken(body));
    }

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public UtenteRespDTO creaUtente(@RequestBody @Validated UtenteDTO body, BindingResult validation) {
        if (validation.hasErrors()) {
            String messages = validation.getAllErrors().stream().map(error -> error.getDefaultMessage()).collect(Collectors.joining(". "));
            throw new BadRequestException(("Errori nel Payload. " + messages));
        } else {
            return new UtenteRespDTO(this.utentiService.saveUtente(body).utenteId());
        }
    }


}
