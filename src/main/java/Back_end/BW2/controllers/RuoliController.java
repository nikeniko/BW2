package Back_end.BW2.controllers;

import Back_end.BW2.entities.Ruolo;
import Back_end.BW2.exceptions.BadRequestException;
import Back_end.BW2.payloads.RuoloDTO;
import Back_end.BW2.services.RuoliService;
import Back_end.BW2.services.UtentiService;
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
@RequestMapping("/ruoli")
public class RuoliController {
    @Autowired
    private RuoliService ruoliService;
    @Autowired
    private UtentiService utentiService;

    @GetMapping("/{ruoloId}")
    public Ruolo getById(@PathVariable UUID ruoloId) {
        return this.ruoliService.findIdRuolo(ruoloId);
    }

    @GetMapping
    public Page<Ruolo> getAll(@RequestParam(defaultValue = "0") int page,
                              @RequestParam(defaultValue = "5") int size,
                              @RequestParam(defaultValue = "id") String sortBy) {
        return this.ruoliService.findAllRuoli(page, size, sortBy);
    }

    @PostMapping("/crea")
    @PreAuthorize("hasAuthority('ADMIN')")
    @ResponseStatus(HttpStatus.CREATED)
    public Ruolo save(@RequestBody @Validated RuoloDTO body, BindingResult validationResult) {
        if (validationResult.hasErrors()) {
            String messages = validationResult.getAllErrors().stream()
                    .map(objectError -> objectError.getDefaultMessage())
                    .collect(Collectors.joining(". "));

            throw new BadRequestException("Ci sono stati errori nel payload. " + messages);
        } else {


            return this.ruoliService.save(body);
        }
    }

    @DeleteMapping("/deleteRuolo/{ruoloId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("hasAuthority('ADMIN')")
    public void deleteRuolo(@PathVariable UUID ruoloId) {
        Ruolo ruolo = ruoliService.findIdRuolo(ruoloId);
        utentiService.findAllUtenti().forEach(utente -> {
            utente.rimuoviRuolo(ruolo);
            utentiService.save(utente);
        });

        this.ruoliService.deleteRuolo(ruoloId);

    }
}
