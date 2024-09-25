package Back_end.BW2.controllers;

import Back_end.BW2.entities.Fattura;
import Back_end.BW2.entities.Utente;
import Back_end.BW2.payloads.NewFatturaDTO;
import Back_end.BW2.payloads.NewFatturaRespDTO;
import Back_end.BW2.payloads.UtenteDTO;
import Back_end.BW2.payloads.UtenteRespDTO;
import Back_end.BW2.services.FattureService;
import Back_end.BW2.services.UtentiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

@RestController
@RequestMapping("/utenti")
public class UtentiController {

    @Autowired
    private UtentiService utentiService;

    @Autowired
    private FattureService fattureService;

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


    // CLOUDINARY

    // UPLOAD IMMAGINE
    @PostMapping("/{utenteId}/avatar")
    public Utente uploadImage(@RequestParam("avatar") MultipartFile img, @PathVariable UUID utenteId) throws IOException {
        try {
            return this.utentiService.uploadImagine(img, utenteId);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    @GetMapping("/me/fatture")
    public Fattura getFattura(@AuthenticationPrincipal Fattura fatturaCorrente) {
        return fatturaCorrente;
    }

    @DeleteMapping("/me/fatture/{fattureId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteFattura(@AuthenticationPrincipal Fattura fatturaCorrente, @PathVariable UUID fattureId) {           //todo VALUTARE
        this.fattureService.findIdFatture(fatturaCorrente.getId());
    }

    @PutMapping("/me/fatture/{fattureId}")
    public NewFatturaRespDTO updateFattura(@AuthenticationPrincipal Fattura fatturaCorrente, @PathVariable UUID fattureId, @RequestBody @Validated NewFatturaDTO body) {  //todo VALUTARE
        return this.fattureService.findIdAndUpdateFatture(fatturaCorrente.getId(), body);
    }
}
