package Back_end.BW2.payloads;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record ClienteDTO(@NotEmpty(message = "Devi inserire la ragione sociale")
                         @Size(min = 6, max = 30, message = "La ragione sociale")
                         String ragioneSociale,
                         @NotEmpty(message = "Devi inserire la partita iva ")
                         @Size(min = 11, max = 11, message = "La partita iva deve avere 11 cifre")
                         String partitaIva,
                         @NotEmpty(message = "Inserire la data d'inserimento ")
                         @Size(min = 10, max = 10, message = "La data deve essere compresa tra 6 e 10 caratteri")
                         String dataInserimento,
                         @NotEmpty(message = "Inserire il cliente per la fattura")
                         @Size(min = 10, max = 10, message = "La data deve essere compresa tra 6 e 10 caratteri")
                         String dataUltimoContatto,
                         @NotNull(message = "Inserisci il numero della fattura")
                         @Size(min = 6, max = 10, message = "La data deve essere compresa tra 6 e 10 caratteri")
                         String fatturatoAnnuale,
                         @NotEmpty(message = "Inserisci la pec")
                         @Email(message = "Inserisci una email valida")
                         String pec,
                         @NotNull(message = "Inserire il numero di telefono")
                         @Size(min = 3, max = 10, message = "Il numero di telefono deve essere di 9 cifre")
                         String telefono,
                         @NotEmpty(message = "Inserisci l'email")
                         @Email(message = "Inserire una email valida")
                         String emailContatto,
                         @NotEmpty(message = "Inserisci il nome")
                         @Size(min = 3, max = 12, message = "Il nome deve essere compreso tra 3 e 12 caratteri")
                         String nomeContatto,
                         @NotEmpty(message = "Inserisci il cognome")
                         @Size(min = 3, max = 12, message = "Il cognome deve essere compreso tra 3 e 12 caratteri")
                         String cognomeContatto,
                         @NotEmpty(message = "Inserisci il tipo di azienda")
                         String tipoAzienda,
                         @NotEmpty(message = "Inserisci L'indirizzo della sede legale")
                         String indirizzoSedeLegale,
                         @NotEmpty(message = "Inserisci L'indirizzo della sede operativa")
                         String indirizzoSedeOperativa
//                         @NotEmpty(message = "Inserisci il Logo aziendale")
//                         @Size(min = 3, max = 54, message = "Il logo aziendale deve essere compreso tra 3 e 14 caratteri")
//                         String logoAziendale
) {
}
