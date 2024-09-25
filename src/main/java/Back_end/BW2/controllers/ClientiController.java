package Back_end.BW2.controllers;

import Back_end.BW2.entities.Cliente;
import Back_end.BW2.payloads.NewFatturaDTO;
import Back_end.BW2.payloads.NewFatturaRespDTO;
import Back_end.BW2.services.ClientiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/cliente")
public class ClientiController {

    // IMPORTI

    @Autowired
    private ClientiService clientiService;

    // 1 --> GET ALL
    @GetMapping
    @PreAuthorize("hasAuthority('ADMIN')")
    public Page<Cliente> findAllClienti(@RequestParam(defaultValue = "0") int page,
                                        @RequestParam(defaultValue = "10") int size,
                                        @RequestParam(defaultValue = "id") String sortBy) {
        return this.clientiService.findAllClienti(page, size, sortBy);
    }

    // 2 --> POST

    // 3 --> GET ID

    @GetMapping("/{fattureId}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public Cliente findIdCliente(@PathVariable UUID clienteId) {
        return this.clientiService.findIdCliente(clienteId);
    }

    // 4 --> PUT

    @PreAuthorize("hasAuthority('ADMIN')")
    public NewFatturaRespDTO findIdAndUpdateCliente(@PathVariable UUID clienteId, @RequestBody @Validated NewFatturaDTO body) {
        return this.clientiService.findIdAndUpdateCliente(clienteId, body);
    }

    // 5 --> DELETE

}
