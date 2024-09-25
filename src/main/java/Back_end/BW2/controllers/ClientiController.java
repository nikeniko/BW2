package Back_end.BW2.controllers;

import Back_end.BW2.entities.Cliente;
import Back_end.BW2.exceptions.BadRequestException;
import Back_end.BW2.payloads.ClienteDTO;
import Back_end.BW2.payloads.ClienteRespDTO;
import Back_end.BW2.services.ClientiService;
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
@RequestMapping("/clienti")
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
    @PostMapping("/crea")
    @ResponseStatus(HttpStatus.CREATED)
    public ClienteRespDTO saveCliente(@RequestBody @Validated ClienteDTO body, BindingResult validationResult) {
        if (validationResult.hasErrors()) {
            String messages = validationResult.getAllErrors().stream()
                    .map(objectError -> objectError.getDefaultMessage())
                    .collect(Collectors.joining(". "));

            throw new BadRequestException("Ci sono stati errori nel payload. " + messages);
        } else {


            return new ClienteRespDTO(this.clientiService.save(body).getId());
        }
    }

    // 3 --> GET ID

    @GetMapping("/{clienteId}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public Cliente findIdCliente(@PathVariable UUID clienteId) {
        return this.clientiService.findIdCliente(clienteId);
    }

    // 4 --> PUT

    @PutMapping("/{clienteId}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ClienteRespDTO findIdAndUpdateCliente(@PathVariable UUID clienteId, @RequestBody @Validated ClienteDTO body) {
        return this.clientiService.findIdAndUpdateClienti(clienteId, body);
    }

    // 5 --> DELETE

    @DeleteMapping("/{clienteId}")
    @PreAuthorize("hasAuthority('ADMIN')")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void findIdClienteAndDelete(@PathVariable UUID clienteId) {
        this.clientiService.findIdClienteAndDelete(clienteId);
    }

}
