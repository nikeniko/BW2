package Back_end.BW2.services;

import Back_end.BW2.entities.Cliente;
import Back_end.BW2.entities.Indirizzo;
import Back_end.BW2.entities.Utente;
import Back_end.BW2.enums.TipoAzienda;
import Back_end.BW2.exceptions.NotFoundException;
import Back_end.BW2.payloads.ClienteDTO;
import Back_end.BW2.payloads.ClienteRespDTO;
import Back_end.BW2.repositories.ClientiRepository;
import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDate;
import java.util.UUID;

@Service
public class ClientiService {

    // IMPORTI

    @Autowired
    private ClientiRepository clientiRepository;

    @Autowired
    private IndirizzoService indirizzoService;

    @Autowired
    private Cloudinary cloudinary;

    // METODI

    // 1 --> GET ALL

    public Page<Cliente> findAllClienti(int page, int size, String sortBy) {
        if (page > 100) page = 100;

        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy));
        return this.clientiRepository.findAll(pageable);
    }

    // 2 --> GET ID

    public Cliente findIdCliente(UUID clienteId) {
        return this.clientiRepository.findById(clienteId).orElseThrow(() -> new NotFoundException(clienteId));
    }

    // 3 --> PUT

    public ClienteRespDTO findIdAndUpdateClienti(UUID clientiId, ClienteDTO newUserData) {
        Cliente found = this.findIdCliente(clientiId);
        found.setRagioneSociale(newUserData.ragioneSociale());
        found.setPartitaIva(newUserData.partitaIva());
        found.setDataInserimento(LocalDate.parse(newUserData.dataInserimento()));
        found.setDataUltimoContatto(LocalDate.parse(newUserData.dataUltimoContatto()));
        found.setFatturatoAnnuale(Integer.parseInt(newUserData.fatturatoAnnuale()));
        found.setTelefono(Integer.parseInt(newUserData.telefono()));
        found.setEmailContatto(newUserData.emailContatto());
        found.setNomeContatto(newUserData.nomeContatto());
        found.setCognomeContatto(newUserData.cognomeContatto());
//        found.setLogoAziendale(newUserData.logoAziendale());

        return new ClienteRespDTO(this.clientiRepository.save(found).getId());
    }

    // 4 --> DELETE

    public void findIdClienteAndDelete(UUID fattureId) {
        Cliente found = this.findIdCliente(fattureId);
        this.clientiRepository.delete(found);
    }

    // 5 --> SAVE

    public Cliente save(ClienteDTO body) {
        Indirizzo indirizzoSedeLegale = this.indirizzoService.findIdIndirizzo(UUID.fromString(body.indirizzoSedeLegale()));
        Indirizzo indirizzoSedeOperativa = this.indirizzoService.findIdIndirizzo(UUID.fromString(body.indirizzoSedeOperativa()));
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        Utente utente = (Utente) authentication.getPrincipal();

        Cliente newCliente = new Cliente();
        newCliente.setRagioneSociale(body.ragioneSociale());
        newCliente.setPartitaIva(body.partitaIva());
        newCliente.setDataInserimento(LocalDate.parse(body.dataInserimento()));
        newCliente.setDataUltimoContatto(LocalDate.parse(body.dataUltimoContatto()));
        newCliente.setFatturatoAnnuale(Integer.parseInt(body.fatturatoAnnuale()));
        newCliente.setPec(body.pec());
        newCliente.setTelefono(Integer.parseInt(body.telefono()));
        newCliente.setEmailContatto(body.emailContatto());
        newCliente.setNomeContatto(body.nomeContatto());
        newCliente.setCognomeContatto(body.cognomeContatto());
        newCliente.setTipoAzienda(TipoAzienda.valueOf(body.tipoAzienda()));
        newCliente.setIndirizzoSedeLegale(indirizzoSedeLegale);
        newCliente.setIndirizzoSedeOperativa(indirizzoSedeOperativa);
        newCliente.setUtenteId(utente);
        newCliente.setLogoAziendale(("https://ui-avatars.com/api/?name=" + body.nomeContatto() + body.cognomeContatto()));

        return this.clientiRepository.save(newCliente);
    }

    // upload immagine
    public Cliente uploadImagine(MultipartFile file, UUID clienteId) throws IOException {
        Cliente trovato = this.findIdCliente(clienteId);
        String url = (String) cloudinary.uploader().upload(file.getBytes(), ObjectUtils.emptyMap()).get("url");

        trovato.setLogoAziendale(url);

        return clientiRepository.save(trovato);
    }


}
