package Back_end.BW2.payloads;

import jakarta.validation.constraints.NotEmpty;

public record IndirizzoDTO(
        @NotEmpty(message = "Inserisci via")
        String via,
        @NotEmpty(message = "Inserisci civico")
        String civico,
        @NotEmpty(message = "Inserisci località")
        String localita,
        @NotEmpty(message = "Inserisci cap")
        String cap,
        @NotEmpty(message = "Inserisci comune")
        String comune
) {
}
