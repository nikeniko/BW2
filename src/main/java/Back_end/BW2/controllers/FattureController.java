package Back_end.BW2.controllers;

import Back_end.BW2.entities.Fattura;
import Back_end.BW2.exceptions.BadRequestException;
import Back_end.BW2.payloads.NewFatturaDTO;
import Back_end.BW2.payloads.NewFatturaRespDTO;
import Back_end.BW2.services.FattureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/fatture")
public class FattureController {

    // IMPORTI

    @Autowired
    private FattureService fattureService;

    // QUERY

    // 1 --> GET ALL

    @GetMapping
    @PreAuthorize("hasAuthority('ADMIN')")
    public Page<Fattura> findAllFatture(@RequestParam(defaultValue = "0") int page,
                                        @RequestParam(defaultValue = "10") int size,
                                        @RequestParam(defaultValue = "id") String sortBy) {
        return this.fattureService.findAllFatture(page, size, sortBy);
    }

    // 2 --> POST

    @PostMapping("/crea")
    @PreAuthorize("hasAnyAuthority('CLIENTE','ADMIN')")
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
    @PreAuthorize("hasAuthority('ADMIN')")
    public Fattura findIdFatture(@PathVariable UUID fattureId) {
        return this.fattureService.findIdFatture(fattureId);
    }


    // 4 --> PUT
    @PreAuthorize("hasAuthority('ADMIN')")
    public Fattura findIdAndUpdate(@PathVariable UUID fattureId, @RequestBody Fattura body) {
        return this.fattureService.findIdAndUpdate(fattureId, body);
    }

    // 5 --> DELETE

    @DeleteMapping("/{fattureId}")
    @PreAuthorize("hasAuthority('ADMIN')")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void findIdFattureAndDelete(@PathVariable UUID fattureId) {
        this.fattureService.findIdFattureAndDelete(fattureId);
    }

}
