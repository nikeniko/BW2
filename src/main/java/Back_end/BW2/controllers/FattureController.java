package Back_end.BW2.controllers;

import Back_end.BW2.entities.Fattura;
import Back_end.BW2.services.FattureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

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

    // 2 --> GET ID

    @GetMapping("/{fattureId}")
    public Fattura findIdFatture(@PathVariable UUID fattureId) {
        return this.fattureService.findIdFatture(fattureId);
    }


    // 3 --> PUT

    public Fattura findIdAndUpdate(@PathVariable UUID fattureId, @RequestBody Fattura body) {
        return this.fattureService.findIdAndUpdate(fattureId, body);
    }

    // 4 --> DELETE

    @DeleteMapping("/{fattureId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public Fattura findIdFattureAndDelete(@PathVariable UUID fattureId) {
        return this.fattureService.findIdFattureAndDelete(fattureId);
    }

}
