package Back_end.BW2.controllers;

import Back_end.BW2.entities.Cliente;
import Back_end.BW2.payloads.ClienteDTO;
import Back_end.BW2.services.ClientiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
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

    @GetMapping("/{clienteId}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public Cliente findIdCliente(@PathVariable UUID clienteId) {
        return this.clientiService.findIdCliente(clienteId);
    }

    // 4 --> PUT

    @PreAuthorize("hasAuthority('ADMIN')")
    public ClienteDTO findIdAndUpdateCliente(@PathVariable UUID clienteId, @RequestBody @Validated ClienteDTO body) {
        return this.clientiService.findIdCliente(clienteId, body);
    }

    // 5 --> DELETE

    @DeleteMapping("/{clienteId}")
    @PreAuthorize("hasAuthority('ADMIN')")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void findIdClienteAndDelete(@PathVariable UUID clienteId) {
        this.clientiService.findIdClienteAndDelete(clienteId);
    }

}
