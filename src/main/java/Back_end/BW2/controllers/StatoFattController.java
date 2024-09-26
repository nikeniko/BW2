package Back_end.BW2.controllers;

import Back_end.BW2.entities.StatoFattura;
import Back_end.BW2.exceptions.BadRequestException;
import Back_end.BW2.payloads.StatoFattDTO;
import Back_end.BW2.payloads.StatoFattRespDTO;
import Back_end.BW2.services.StatoFattService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.stream.Collectors;

@RestController
@RequestMapping("/statifatture")
public class StatoFattController {

    @Autowired
    private StatoFattService statoFattService;

    // METODI

    // 1 --> GET ALL

    @GetMapping
    @PreAuthorize("hasAnyAuthority('UTENTE','ADMIN')")
    public Page<StatoFattura> findAllStatiFatture(@RequestParam(defaultValue = "0") int page,
                                                  @RequestParam(defaultValue = "10") int size,
                                                  @RequestParam(defaultValue = "id") String sortBy) {
        return this.statoFattService.findAllStati(page, size, sortBy);
    }

    // 2 --> POST

    @PostMapping("/crea")
    @PreAuthorize("hasAnyAuthority('UTENTE','ADMIN')")
    @ResponseStatus(HttpStatus.CREATED)
    public StatoFattRespDTO save(@RequestBody @Validated StatoFattDTO body, BindingResult validationResult) {
        if (validationResult.hasErrors()) {
            String messages = validationResult.getAllErrors().stream()
                    .map(objectError -> objectError.getDefaultMessage())
                    .collect(Collectors.joining(". "));

            throw new BadRequestException("Ci sono stati errori nel payload. " + messages);
        } else {
            return new StatoFattRespDTO(this.statoFattService.save(body).statoId());
        }
    }
}
