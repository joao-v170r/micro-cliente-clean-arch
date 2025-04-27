package br.com.microservice.cliente.controler;

import br.com.microservice.cliente.dto.ClienteDTO;
import br.com.microservice.cliente.usecase.ReadClienteUserCase;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

public class ReadClienteController {
    final ReadClienteUserCase useCase;

    public ReadClienteController(ReadClienteUserCase useCase) {
        this.useCase = useCase;
    }

    @GetMapping("/{id}")
    @Operation(
            summary = "Encontrar cliente"
    )
    public ResponseEntity<ClienteDTO> findById(@PathVariable Long id) {
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
