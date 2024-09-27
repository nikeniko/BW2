package Back_end.BW2.controllers;

import Back_end.BW2.entities.Fattura;
import Back_end.BW2.entities.StatoFattura;
import Back_end.BW2.exceptions.BadRequestException;
import Back_end.BW2.payloads.NewFatturaDTO;
import Back_end.BW2.payloads.NewFatturaRespDTO;
import Back_end.BW2.payloads.StatoFattDTO;
import Back_end.BW2.services.FattureService;
import Back_end.BW2.services.StatoFattService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/fatture")
public class FattureController {

    // IMPORTI

    @Autowired
    private FattureService fattureService;
    @Autowired
    private StatoFattService statoFattService;

    // METODI

    // 1 --> GET ALL

    @GetMapping
    @PreAuthorize("hasAuthority('ADMIN')")
    public Page<Fattura> findAllFatture(@RequestParam(defaultValue = "0") int page,
                                        @RequestParam(defaultValue = "10") int size,
                                        @RequestParam(defaultValue = "id") String sortBy) {
        return this.fattureService.findAllFatture(page, size, sortBy);
    }

    @GetMapping("/cliente/{clienteID}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public List<Fattura> findByCliente(@PathVariable("clienteID") UUID clienteID) {
        return this.fattureService.findByCliente(clienteID);
    }

    @GetMapping("/stato/{statoFattura}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public List<Fattura> findByStatoFattura(@PathVariable("statoFattura") String statoFattura) {
        return this.fattureService.findByStatoFattura(statoFattura);
    }

    @GetMapping("/data/{data}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public List<Fattura> findByData(@PathVariable("data") String data) {
        return this.fattureService.findByData(data);
    }

    @GetMapping("/anno/{anno}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public List<Fattura> findByAnno(@PathVariable("anno") int anno) {
        return this.fattureService.findByAnno(anno);
    }

    @GetMapping("/between/{minImporto}/{maxImporto}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public List<Fattura> findByImportoBetween(@PathVariable("minImporto") Double minImporto, @PathVariable("maxImporto") Double maxImporto) {
        return this.fattureService.findByImportoBetween(minImporto, maxImporto);
    }


    // 2 --> POST

    @PostMapping("/crea")
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

    // 3 --> GET ID

    @GetMapping("/{fattureId}")
    @PreAuthorize("hasAnyAuthority('UTENTE','ADMIN')")
    public Fattura findIdFatture(@PathVariable UUID fattureId) {
        return this.fattureService.findIdFatture(fattureId);
    }


    // 4 --> PUT

    @PutMapping("/{fattureId}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public NewFatturaRespDTO findIdAndUpdateFatture(@PathVariable UUID fattureId, @RequestBody @Validated NewFatturaDTO body) {
        return this.fattureService.findIdAndUpdateFatture(fattureId, body);
    }

    // 5 --> DELETE

    @DeleteMapping("/{fattureId}")
    @PreAuthorize("hasAuthority('ADMIN')")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void findIdFattureAndDelete(@PathVariable UUID fattureId) {
        this.fattureService.findIdFattureAndDelete(fattureId);
    }

    @PostMapping("/{fatturaId}/nuovostato")
    @PreAuthorize("hasAnyAuthority('ADMIN','UTENTE')")
    @ResponseStatus(HttpStatus.CREATED)
    public NewFatturaRespDTO creaStatoFatt(@PathVariable UUID fatturaId, @RequestBody @Validated StatoFattDTO body, BindingResult validation) {
        if (validation.hasErrors()) {
            String messages = validation.getAllErrors().stream().map(error -> error.getDefaultMessage()).collect(Collectors.joining(". "));
            throw new BadRequestException(("Errori nel Payload. " + messages));
        } else {
            Fattura fattura = this.fattureService.findIdFatture(fatturaId);
            StatoFattura statoFattura = this.statoFattService.findByStato(body.stato().toUpperCase());
            fattura.setStatoFattura(statoFattura);
            return new NewFatturaRespDTO(this.fattureService.saveFattObj(fattura).getId());
        }
    }


}
