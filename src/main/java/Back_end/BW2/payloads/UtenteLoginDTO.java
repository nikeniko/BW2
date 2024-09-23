package Back_end.BW2.payloads;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;

public record UtenteLoginDTO(
        @NotEmpty(message = "Email obbligatoria.")
        @Email(message = "Email non valida.")
        String email,
        @NotEmpty(message = "Password obbligatoria.")
        String password
) {
}
