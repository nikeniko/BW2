package Back_end.BW2.payloads;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

public record RuoloDTO(
        @NotEmpty(message = "Devi inserire il nuovo ruolo")
        @Size(min = 1, max = 30, message = "Size min 1 max 30")
        String ruolo
) {
}
