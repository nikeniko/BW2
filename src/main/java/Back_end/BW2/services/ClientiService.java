package Back_end.BW2.services;

import Back_end.BW2.entities.Cliente;
import Back_end.BW2.exceptions.NotFoundException;
import Back_end.BW2.payloads.ClienteDTO;
import Back_end.BW2.payloads.ClienteRespDTO;
import Back_end.BW2.repositories.ClientiRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class ClientiService {

    // IMPORTI

    @Autowired
    private ClientiRepository clientiRepository;

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
        found.setDataInserimento(newUserData.dataInserimento());
        found.setDataUltimoContatto(newUserData.dataUltimoContatto());
        found.setFatturatoAnnuale(newUserData.fatturatoAnnuale());
        found.setTelefono(newUserData.telefono());
        found.setEmailContatto(newUserData.emailContatto());
        found.setNomeContatto(newUserData.nomeContatto());
        found.setCognomeContatto(newUserData.cognomeContatto());
        found.setLogoAziendale(newUserData.logoAziendale());

        return new ClienteDTO(this.clientiRepository.save(found).getId());
    }

    // 4 --> DELETE

    public void findIdClienteAndDelete(UUID fattureId) {
        Cliente found = this.findIdCliente(fattureId);
        this.clientiRepository.delete(found);
    }


}
