package Back_end.BW2.controllers;

import Back_end.BW2.entities.Cliente;
import Back_end.BW2.exceptions.BadRequestException;
import Back_end.BW2.payloads.ClienteDTO;
import Back_end.BW2.payloads.ClienteRespDTO;
import Back_end.BW2.payloads.NewFatturaDTO;
import Back_end.BW2.payloads.NewFatturaRespDTO;
import Back_end.BW2.services.ClientiService;
import Back_end.BW2.services.FattureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/clienti")
public class ClientiController {

    // IMPORTI

    @Autowired
    private ClientiService clientiService;

    @Autowired
    private FattureService fattureService;

    // 1 --> GET ALL
    @GetMapping
    @PreAuthorize("hasAnyAuthority('ADMIN','UTENTE')")
    public Page<Cliente> findAllClienti(@RequestParam(defaultValue = "0") int page,
                                        @RequestParam(defaultValue = "10") int size,
                                        @RequestParam(defaultValue = "id") String sortBy) {
        return this.clientiService.findAllClienti(page, size, sortBy);
    }

    @GetMapping("/fatturatoAnnuale/{fatturatoAnnuale}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public List<Cliente> findByFatturatoAnnuale(@PathVariable("fatturatoAnnuale") int fatturatoAnnuale) {
        return this.clientiService.findByFatturatoAnnuale(fatturatoAnnuale);
    }

    @GetMapping("/dataInserimento/{dataInserimento}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public List<Cliente> findByDataInserimento(@PathVariable("dataInserimento") String dataInserimento) {
        return this.clientiService.findByDataInserimento(dataInserimento);
    }

    @GetMapping("/dataUltimoContatto/{dataUltimoContatto}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public List<Cliente> findByDataUltimoContatto(@PathVariable("dataUltimoContatto") String dataUltimoContatto) {
        return this.clientiService.findByDataUltimoContatto(dataUltimoContatto);
    }

    @GetMapping("/ragioneSociale/{ragioneSociale}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public List<Cliente> findByRagioneSociale(@PathVariable("ragioneSociale") String ragioneSociale) {
        return this.clientiService.findByRagioneSociale(ragioneSociale);
    }


    // 2 --> POST
    @PostMapping("/crea")
    @PreAuthorize("hasAnyAuthority('ADMIN','UTENTE')")
    @ResponseStatus(HttpStatus.CREATED)
    public ClienteRespDTO saveCliente(@RequestBody @Validated ClienteDTO body, BindingResult validationResult) {
        if (validationResult.hasErrors()) {
            String messages = validationResult.getAllErrors().stream()
                    .map(objectError -> objectError.getDefaultMessage())
                    .collect(Collectors.joining(". "));

            throw new BadRequestException("Ci sono stati errori nel payload. " + messages);
        } else {


            return new ClienteRespDTO(this.clientiService.save(body).getId());
        }
    }

    // 3 --> GET ID

    @GetMapping("/{clienteId}")
    @PreAuthorize("hasAnyAuthority('ADMIN','UTENTE')")
    public Cliente findIdCliente(@PathVariable UUID clienteId) {
        return this.clientiService.findIdCliente(clienteId);
    }

    // 4 --> PUT

    @PutMapping("/{clienteId}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ClienteRespDTO findIdAndUpdateCliente(@PathVariable UUID clienteId, @RequestBody @Validated ClienteDTO body) {
        return this.clientiService.findIdAndUpdateClienti(clienteId, body);
    }

    // 5 --> DELETE

    @DeleteMapping("/{clienteId}")
    @PreAuthorize("hasAuthority('ADMIN')")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void findIdClienteAndDelete(@PathVariable UUID clienteId) {
        this.clientiService.findIdClienteAndDelete(clienteId);
    }

    // 2 --> POST

    @PostMapping("/fatture/crea")
    @PreAuthorize("hasAnyAuthority('UTENTE','ADMIN')")
    @ResponseStatus(HttpStatus.CREATED)
    public NewFatturaRespDTO save(@RequestBody @Validated NewFatturaDTO body, BindingResult validationResult) {

        if (validationResult.hasErrors()) {
            String messages = validationResult.getAllErrors().stream()
                    .map(objectError -> objectError.getDefaultMessage())
                    .collect(Collectors.joining(". "));

            throw new BadRequestException("Ci sono stati errori nel payload. " + messages);
        } else {
            return new NewFatturaRespDTO(this.fattureService.save(body).getId());
        }
    }

    // CLOUDINARY

    // UPLOAD IMMAGINE
    @PostMapping("/{clienteId}/avatar")
    public Cliente uploadImage(@RequestParam("avatar") MultipartFile img, @PathVariable UUID clienteId) throws IOException {
        try {
            return this.clientiService.uploadImagine(img, clienteId);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
