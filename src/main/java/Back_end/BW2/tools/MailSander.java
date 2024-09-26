package Back_end.BW2.tools;

import Back_end.BW2.entities.Utente;
import kong.unirest.core.HttpResponse;
import kong.unirest.core.JsonNode;
import kong.unirest.core.Unirest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class MailSander {

    @Value("${mailgun.key}")
    private String apiKey;
    @Value("${mailgun.domain}")
    private String domainName;
    @Value("${email.from}")
    private String emailFrom;

    public void sendRegistrationEmail(Utente recipient) {
        HttpResponse<JsonNode> response = Unirest.post("https://api.mailgun.net/v3/" + this.domainName + "/messages")
                .basicAuth("api", this.apiKey)
                .queryString("from", this.emailFrom)
                .queryString("to", recipient.getEmail())
                .queryString("subject", "Registrazione avvenuta con successo!")
                .queryString("text", "Salve " + recipient.getNome() + " " + recipient.getCognome() + ", grazie per esserti registrato nella nostra piattaforma. \n Buon lavoro.")
                .asJson();

        System.out.println(response.getBody()); // stampo il messaggio in risposta per rilevare eventuali errori

    }
}
