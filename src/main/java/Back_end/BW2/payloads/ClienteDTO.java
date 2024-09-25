package Back_end.BW2.payloads;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;

public record ClienteDTO(@NotEmpty(message = "Devi inserire la ragione sociale")
                         @Size(min = 6, max = 10, message = "La ragione sociale")
                         String ragioneSociale,
                         @NotNull(message = "Devi inserire la partita iva ")
                         @Size(min = 11, max = 11, message = "La partita iva deve avere 11 cifre")
                         long partitaIva,
                         @NotEmpty(message = "Inserire la data d'inserimento ")
                         @Size(min = 6, max = 10, message = "La data deve essere compresa tra 6 e 10 caratteri")
                         LocalDate dataInserimento,
                         @NotEmpty(message = "Inserire il cliente per la fattura")
                         @Size(min = 6, max = 10, message = "La data deve essere compresa tra 6 e 10 caratteri")
                         LocalDate dataUltimoContatto,
                         @NotNull(message = "Inserisci il numero della fattura")
                         @Size(min = 6, max = 10, message = "La data deve essere compresa tra 6 e 10 caratteri")
                         long fatturatoAnnuale,
                         @NotNull(message = "Inserire il numero di telefono")
                         @Size(min = 9, max = 9, message = "Il numero di telefono deve essere di 9 cifre")
                         long telefono,
                         @NotEmpty(message = "Inserisci l'email")
                         @Size(min = 9, max = 18, message = "L'email deve essere compresa tra 9 e 18 caratteri")
                         String emailContatto,
                         @NotEmpty(message = "Inserisci il nome")
                         @Size(min = 3, max = 12, message = "Il nome deve essere compreso tra 3 e 12 caratteri")
                         String nomeContatto,
                         @NotEmpty(message = "Inserisci il cognome")
                         @Size(min = 3, max = 12, message = "Il cognome deve essere compreso tra 3 e 12 caratteri")
                         String cognomeContatto,
                         @NotEmpty(message = "Inserisci il Logo aziendale")
                         @Size(min = 3, max = 14, message = "Il logo aziendale deve essere compreso tra 3 e 14 caratteri")
                         String logoAziendale
) {
}
