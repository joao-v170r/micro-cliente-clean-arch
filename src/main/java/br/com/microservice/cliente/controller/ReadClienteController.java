package br.com.microservice.cliente.controler;

import br.com.microservice.cliente.dto.ClienteDTO;
import br.com.microservice.cliente.usecase.ReadClienteUserCase;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/cliente")
@Tag(name = "Cliente", description = "Endpoints que modificam,controla,cria e deleta clientes")
public class ReadClienteController {
    final ReadClienteUserCase useCase;

    public ReadClienteController(ReadClienteUserCase useCase) {
        this.useCase = useCase;
    }

    @GetMapping("/{id}")
    @Operation(
            summary = "Encontrar cliente"
    )
    public ResponseEntity<ClienteDTO> findById(@PathVariable String id) {
        return ResponseEntity.ok(useCase.find(id));
    }

    @GetMapping
    @Operation(
            summary = "Listar todos os clientes"
    )
    public ResponseEntity<List<ClienteDTO>> findAll(
            @PageableDefault(page = 0, size = 10, sort = "id") Pageable page
    ) {
        return ResponseEntity.ok(useCase.findAll(page));
    }
}
