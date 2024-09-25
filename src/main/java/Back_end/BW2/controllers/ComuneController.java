package Back_end.BW2.controllers;

import Back_end.BW2.entities.Comune;
import Back_end.BW2.entities.Indirizzo;
import Back_end.BW2.exceptions.BadRequestException;
import Back_end.BW2.payloads.IndirizzoDTO;
import Back_end.BW2.payloads.IndirizzoRespDTO;
import Back_end.BW2.services.ComuneService;
import Back_end.BW2.services.IndirizzoService;
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
@RequestMapping("/comuni")
public class ComuneController {
    @Autowired
    private ComuneService comuneService;

    @Autowired
    private IndirizzoService indirizzoService;

    // 1 --> GET ALL
    @GetMapping
    @PreAuthorize("hasAnyAuthority('ADMIN','UTENTE')")
    public Page<Comune> findAllComuni(@RequestParam(defaultValue = "0") int page,
                                      @RequestParam(defaultValue = "10") int size,
                                      @RequestParam(defaultValue = "id") String sortBy) {
        return this.comuneService.findAllComune(page, size, sortBy);
    }

    // 3 --> GET ID

    @GetMapping("/{comuneId}")
    @PreAuthorize("hasAnyAuthority('ADMIN','UTENTE')")
    public Comune findIdComune(@PathVariable UUID comuneId) {
        return this.comuneService.findIdComune(comuneId);
    }

    // 1 --> GET ALL

    @GetMapping("/{comuneId}/indirizzi")
    @PreAuthorize("hasAnyAuthority('ADMIN','UTENTE')")
    public List<Indirizzo> findAllIndirizzi(@PathVariable UUID comuneId) {
        Comune comune = this.comuneService.findIdComune(comuneId);
        return this.indirizzoService.findByComune(comune);
    }

    @PostMapping("/indirizzi/crea")
    @PreAuthorize("hasAnyAuthority('ADMIN','UTENTE')")
    @ResponseStatus(HttpStatus.CREATED)
    public IndirizzoRespDTO saveIndirizzo(@RequestBody @Validated IndirizzoDTO body, BindingResult validationResult) {
        if (validationResult.hasErrors()) {
            String messages = validationResult.getAllErrors().stream()
                    .map(objectError -> objectError.getDefaultMessage())
                    .collect(Collectors.joining(". "));

            throw new BadRequestException("Ci sono stati errori nel payload. " + messages);
        } else {


            return new IndirizzoRespDTO(this.indirizzoService.save(body).getId());
        }
    }


}
