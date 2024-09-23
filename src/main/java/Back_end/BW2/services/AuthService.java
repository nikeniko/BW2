package Back_end.BW2.services;

import Back_end.BW2.entities.Utente;
import Back_end.BW2.exceptions.UnauthorizedException;
import Back_end.BW2.payloads.UtenteLoginDTO;
import Back_end.BW2.security.JWTTools;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
    @Autowired
    private UtentiService utentiService;
    @Autowired
    private JWTTools jwtTools;
    @Autowired
    private PasswordEncoder bcrypt;

    public String checkCredenzialiAndGeneraToken(UtenteLoginDTO body) {

        Utente trovato = this.utentiService.findByEmail(body.email());
        if (bcrypt.matches(body.password(), trovato.getPassword())) {
            return jwtTools.createToken(trovato);
        } else {
            throw new UnauthorizedException("Credenziali errate");
        }
    }
}
