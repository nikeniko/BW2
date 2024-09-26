package Back_end.BW2.payloads;

import jakarta.validation.constraints.NotEmpty;

public record StatoFattDTO(
        @NotEmpty(message = "Inserisci lo stato della fattura.")
        String stato

) {
}
