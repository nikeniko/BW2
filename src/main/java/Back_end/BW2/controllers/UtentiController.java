package Back_end.BW2.controllers;

import Back_end.BW2.entities.Utente;
import Back_end.BW2.payloads.UtenteDTO;
import Back_end.BW2.payloads.UtenteRespDTO;
import Back_end.BW2.services.UtentiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/utenti")
public class UtentiController {

    @Autowired
    private UtentiService utentiService;

    @GetMapping("/{utenteId}")
    public Utente getById(@PathVariable UUID utenteId) {
        return this.utentiService.findById(utenteId);
    }

    @GetMapping
    public Page<Utente> getAll(@RequestParam(defaultValue = "0") int page,
                               @RequestParam(defaultValue = "5") int size,
                               @RequestParam(defaultValue = "id") String sortBy) {
        return this.utentiService.findAll(page, size, sortBy);
    }

    @DeleteMapping("/{utenteId}")
    @PreAuthorize("hasAuthority('ADMIN')")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void findByIdAndDelete(@PathVariable UUID utenteId) {
        this.utentiService.findByIdAndDelete(utenteId);
    }

    @PutMapping("/{utenteId}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public UtenteRespDTO findByIdAndUpdate(@PathVariable UUID utenteId, @RequestBody @Validated UtenteDTO body) {
        return this.utentiService.findByIdAndUpdate(utenteId, body);
    }

    @GetMapping("/me")
    public Utente getProfile(@AuthenticationPrincipal Utente utenteCorrenteAutenticato) {
        return utenteCorrenteAutenticato;
    }

    @DeleteMapping("/me")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteProfile(@AuthenticationPrincipal Utente utenteCorrenteAutenticato) {
        this.utentiService.findByIdAndDelete(utenteCorrenteAutenticato.getId());
    }

    @PutMapping("/me")
    public UtenteRespDTO updateProfile(@AuthenticationPrincipal Utente utenteCorrenteAutenticato, @RequestBody @Validated UtenteDTO body) {
        return this.utentiService.findByIdAndUpdate(utenteCorrenteAutenticato.getId(), body);
    }
}
