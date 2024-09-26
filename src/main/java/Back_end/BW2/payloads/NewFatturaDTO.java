package Back_end.BW2.payloads;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record NewFatturaDTO(

        @NotEmpty(message = "Devi inserire l'importo della fattura")
        @Size(min = 2, max = 9, message = "L'importo deve essere compreso tra 2 e 9 caratteri")
        String importo,
        @NotEmpty(message = "Inserire lo stato della fattura")
        @Size(min = 5, message = "Deve avere almeno di 5 caratteri")
        String statoFattura,
        @NotEmpty(message = "Inserire il cliente per la fattura")
        @Size(min = 5, message = "Deve avere almeno 5 caratteri")
        String cliente,
        @NotNull(message = "Inserisci il numero della fattura")
        int numeroFattura
) {
}
